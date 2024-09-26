package com.demo.util;

import java.io.IOException;
import java.util.UUID;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.demo.entity.Transaction;
import com.demo.security.listener.JobResultListener;
import com.demo.reader.MyTransactionReader;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Value("file:src/main/resources/data/*.csv")
	private Resource[] inputFiles;

	// @Autowired
	// MyTransactionReader myTransactionReader;

	@Bean // allows for multiple files to be read in a folder
	public ItemReader<Transaction> reader(MyTransactionReader myTransactionReader) {

		Resource[] resources = null;
		ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();

		try {

			resources = patternResolver.getResources("file:src/main/resources/data/*.csv");

		} catch (IOException e) {

			e.printStackTrace();

		}

		MultiResourceItemReader<Transaction> reader = new MultiResourceItemReader<>();
		reader.setResources(resources);
		reader.setDelegate(myTransactionReader);
		return reader;

	}

	@Bean // Calls one of a collection of ItemWriters for each item, based on a router pattern implemented through the provided Classifier.
	public ClassifierCompositeItemWriter<Transaction> classifierCompositeItemWriter(ItemWriter<Transaction> processedItemWriter, ItemWriter<Transaction> suspiciousItemWriter) {

		ClassifierCompositeItemWriter<Transaction> classifierCompositeItemWriter = new ClassifierCompositeItemWriter<>();

		classifierCompositeItemWriter.setClassifier((Classifier<Transaction, ItemWriter<? super Transaction>>) transaction -> {

			if (transaction.getTransactionValue() > 20 || transaction.getUserName().matches(".*[0-9].*")) {

				System.out.println("==========================================");
				System.out.println(transaction + " ===== Suspicious Transaction");
				System.out.println("==========================================");

				return processedItemWriter;

			} else {

				System.out.println("==========================================");
				System.out.println("Processed Transaction " + transaction);
				System.out.println("==========================================");

				return suspiciousItemWriter;

			}

		});
		return classifierCompositeItemWriter;

	}

	@Bean
	public FlatFileItemWriter<Transaction> suspiciousItemWriter() {

		return new FlatFileItemWriterBuilder<Transaction>().name("suspiciousItemWriter")
				.resource(new FileSystemResource("src/main/resources/suspicious/" + UUID.randomUUID().toString() + "--suspicious.csv"))
				.lineAggregator(new PassThroughLineAggregator<>()) // Line aggregator used to build the String version of each item.
				.build();

	}

	@Bean
	public FlatFileItemWriter<Transaction> processedItemWriter() {

		return new FlatFileItemWriterBuilder<Transaction>() // A builder implementation for the FlatFileItemWriter
				.name("processedItemWriter")
				.resource(new FileSystemResource("src/main/resources/processed/" + UUID.randomUUID().toString() + "--processed.csv"))
				.lineAggregator(new PassThroughLineAggregator<>()) // Line aggregator used to build the String version of each item.
				.build();

	}

	@Bean
	public Job dataExtractionJob(Step dataExtractionStep) {

		return jobBuilderFactory.get("MyJob") // Creates a job builder and initializes its job repository
				.incrementer(new RunIdIncrementer()) // job contains an ID using the build RunIdIncrementer
				.listener(new JobResultListener()) // notifies when job is done
				.start(dataExtractionStep) // Create a new job builder that will execute a step or sequence of steps.
				// .next(deleteFile())
				.build();

	}

	@Bean
	public Step dataExtractionStep(ItemReader<Transaction> reader) {

		return stepBuilderFactory.get("MyStep") // Creates a step builder and initializes its job repository and transaction manager
				.<Transaction, Transaction>chunk(1) // receive Txn object, release as Txn object. writes 1 record at a time
				.reader(reader) // read in the transaction data using the reader method above
				.writer(classifierCompositeItemWriter(suspiciousItemWriter(), processedItemWriter())) // write each txn item to the db using either writer
				.stream(suspiciousItemWriter())
				.stream(processedItemWriter())
				.build();

	}

	// @Bean
	// public Step deleteFile() {
	//
	// FileDeletingTasklet task = new FileDeletingTasklet();
	// task.setResources(inputFiles);
	//
	// return stepBuilderFactory.get("deleteFile")
	// .tasklet(task)
	// .build();
	//
	// }

}
