package com.demo;

import com.demo.reader.MyTransactionReader;
import com.demo.util.BatchConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

 @RunWith(SpringRunner.class)
 @SpringBatchTest
 @EnableAutoConfiguration
 @ContextConfiguration(classes = {BatchConfig.class})
 @TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
 @DirtiesContext(classMode = ClassMode.AFTER_CLASS)
 @ComponentScan
 public class HighValueTxnApplicationTests<JwtAuthenticationController> {


  private static final String TEST_OUTPUT = "src/test/resources/output/actual-output.csv";

  private static final String EXPECTED_OUTPUT = "src/test/resources/output/expected-output.csv";

  private static final String TEST_INPUT = "src/test/resources/input/test-input.csv";

  private static final String EXPECTED_OUTPUT_ONE = "src/test/resources/output/expected-output-one.csv";

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;

  @Autowired

private JwtAuthenticationController controller;

  @Autowired
  BatchConfig processItemWriter;

  @Autowired
  MyTransactionReader itemReader;


  private JobParameters defaultJobParameters() {
   JobParametersBuilder paramsBuilder = new JobParametersBuilder();
   paramsBuilder.addString("file.input", TEST_INPUT);
   paramsBuilder.addString("file.output", TEST_OUTPUT);
   return paramsBuilder.toJobParameters();
  }


  @Test
  public void givenReferenceOutput_whenJobExecuted_thenSuccess() throws Exception {

   // given
   FileSystemResource expectedResult = new FileSystemResource(EXPECTED_OUTPUT);
   FileSystemResource actualResult = new FileSystemResource(TEST_OUTPUT);

   // when
   JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
   JobInstance actualJobInstance = jobExecution.getJobInstance();
   ExitStatus actualJobExitStatus = jobExecution.getExitStatus();


  }
 }


//
// // Tests from https://www.baeldung.com/spring-batch-testing-job
// @Test
// @Test
// @Test
// @Test
// public void givenReferenceOutput_whenJobExecuted_thenSuccess() throws Exception {
//
// // given
// FileSystemResource expectedResult = new FileSystemResource(EXPECTED_OUTPUT);
// FileSystemResource actualResult = new FileSystemResource(TEST_OUTPUT);
//
// // when
// JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
// JobInstance actualJobInstance = jobExecution.getJobInstance();
// ExitStatus actualJobExitStatus = jobExecution.getExitStatus();
// public void givenReferenceOutput_whenJobExecuted_thenSuccess() throws Exception {
//
// // given
// FileSystemResource expectedResult = new FileSystemResource(EXPECTED_OUTPUT);
// FileSystemResource actualResult = new FileSystemResource(TEST_OUTPUT);
//
// // when
// JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
// JobInstance actualJobInstance = jobExecution.getJobInstance();
// ExitStatus actualJobExitStatus = jobExecution.getExitStatus();
// public void givenReferenceOutput_whenJobExecuted_thenSuccess() throws Exception {
//
// // given
// FileSystemResource expectedResult = new FileSystemResource(EXPECTED_OUTPUT);
// FileSystemResource actualResult = new FileSystemResource(TEST_OUTPUT);
//
// // when
// JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
// JobInstance actualJobInstance = jobExecution.getJobInstance();
// ExitStatus actualJobExitStatus = jobExecution.getExitStatus();
// public void givenReferenceOutput_whenJobExecuted_thenSuccess() throws Exception {
//
// // given
// FileSystemResource expectedResult = new FileSystemResource(EXPECTED_OUTPUT);
// FileSystemResource actualResult = new FileSystemResource(TEST_OUTPUT);
//
// // when
// JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
// JobInstance actualJobInstance = jobExecution.getJobInstance();
// ExitStatus actualJobExitStatus = jobExecution.getExitStatus();
//
// private static final String TEST_OUTPUT = "src/test/resources/output/actual-output.csv";
//
// private static final String EXPECTED_OUTPUT = "src/test/resources/output/expected-output.csv";
//
// private static final String TEST_INPUT = "src/test/resources/input/test-input.csv";
//
// private static final String EXPECTED_OUTPUT_ONE = "src/test/resources/output/expected-output-one.csv";
//
// @Autowired
// private JobLauncherTestUtils jobLauncherTestUtils;
//
// @Autowired
// private JwtAuthenticationController controller;
//
// @Autowired
// BatchConfig processItemWriter;
//
// @Autowired
// MyTransactionReader itemReader;
//
// private JobParameters defaultJobParameters() {
// JobParametersBuilder paramsBuilder = new JobParametersBuilder();
// paramsBuilder.addString("file.input", TEST_INPUT);
// paramsBuilder.addString("file.output", TEST_OUTPUT);
// return paramsBuilder.toJobParameters();
// }
//
// @Test
// public void givenReferenceOutput_whenJobExecuted_thenSuccess() throws Exception {
//
// // given
// FileSystemResource expectedResult = new FileSystemResource(EXPECTED_OUTPUT);
// FileSystemResource actualResult = new FileSystemResource(TEST_OUTPUT);
//
// // when
// JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
// JobInstance actualJobInstance = jobExecution.getJobInstance();
// ExitStatus actualJobExitStatus = jobExecution.getExitStatus();
//
// // then
// assertThat(actualJobInstance.getJobName(), is("MyJob"));
// assertThat(actualJobExitStatus.getExitCode(), is("COMPLETED"));
// AssertFile.assertFileEquals(expectedResult, actualResult);
// }
//
// // Test Individual Steps
// @Test
// public void givenReferenceOutput_whenStepExecuted_thenSuccess() throws Exception {
// // given
// FileSystemResource expectedResult = new FileSystemResource(EXPECTED_OUTPUT);
// FileSystemResource actualResult = new FileSystemResource(TEST_OUTPUT);
//
// // when
// JobExecution jobExecution = jobLauncherTestUtils.launchStep(
// "MyStep", defaultJobParameters());
// Collection actualStepExecutions = jobExecution.getStepExecutions();
// ExitStatus actualJobExitStatus = jobExecution.getExitStatus();
//
// // then
// assertThat(actualStepExecutions.size(), is(1));
// assertThat(actualJobExitStatus.getExitCode(), is("COMPLETED"));
// AssertFile.assertFileEquals(expectedResult, actualResult);
// }
//
// @Test
// public void givenMockedStep_whenReaderCalled_thenSuccess() throws Exception {
// // given
// StepExecution stepExecution = MetaDataInstanceFactory
// .createStepExecution(defaultJobParameters());
//
// // when
// StepScopeTestUtils.doInStepScope(stepExecution, () -> {
// Transaction transaction;
// itemReader.open(stepExecution.getExecutionContext());
// while ((transaction = itemReader.read()) != null) {
//
// // then
// System.out.println(transaction.getTransactionId());
// System.out.println(transaction.getUserName());
// System.out.println(transaction.getTransactionValue());
//
// }
// itemReader.close();
// return null;
// });
// }
//
// @Test
// public void givenMockedStep_whenWriterCalled_thenSuccess() throws Exception {
// // given
// FileSystemResource expectedResult = new FileSystemResource(EXPECTED_OUTPUT_ONE);
// FileSystemResource actualResult = new FileSystemResource(TEST_OUTPUT);
//
// Transaction demoTransaction = new Transaction();
//
// demoTransaction.setTransactionId(1);
// demoTransaction.setUserName("Bill");
// demoTransaction.setTransactionValue(10);
// StepExecution stepExecution = MetaDataInstanceFactory
// .createStepExecution(defaultJobParameters());
//
// // when
// StepScopeTestUtils.doInStepScope(stepExecution, () -> {
// processItemWriter
// .processedItemWriter()
// .open(stepExecution.getExecutionContext());
// processItemWriter
// .processedItemWriter()
// .write(Arrays.asList(demoTransaction));
// processItemWriter
// .processedItemWriter()
// .close();
// return null;
// });
//
// // then
// AssertFile.assertFileEquals(expectedResult, actualResult);
// }
//
// // Controller Test
// @Test
// public void contextLoads() throws Exception {
// Assertions.assertNotNull(controller);
// }
// }

