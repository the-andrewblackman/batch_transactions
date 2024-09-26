package com.demo.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.stereotype.Component;

import com.demo.entity.Transaction;

@Component
public class MyTransactionReader extends FlatFileItemReader<Transaction> implements ItemReader<Transaction>{

	// Reads the file using the FlatFileItemReader.  Each line is read, converted to Transaction object.
	
	public MyTransactionReader() {
		setLinesToSkip(1); 
		setLineMapper(getDefaultLineMapper());
	}
	
	public DefaultLineMapper<Transaction> getDefaultLineMapper() {
		
		DefaultLineMapper<Transaction> defaultLineMapper = new DefaultLineMapper<Transaction>();
		
		DelimitedLineTokenizer delimitedLineTokenizer =new DelimitedLineTokenizer();  // breaks file into lines
		delimitedLineTokenizer.setNames(new String[] { "transactionId", "userName", "transactionValue" });  // the column names
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
		
		BeanWrapperFieldSetMapper<Transaction> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<Transaction>();
		beanWrapperFieldSetMapper.setTargetType(Transaction.class);
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
		
		return defaultLineMapper;
	}
}