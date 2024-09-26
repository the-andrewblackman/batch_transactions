package com.demo.security.listener;

import org.springframework.batch.core.BatchStatus;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobResultListener implements JobExecutionListener {

	public void beforeJob(JobExecution jobExecution) {
		System.out.println("JOB STARTED");
	}

	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED ) {
	        System.out.println("JOB WAS SUCCESSFUL");
	    }
	    else if (jobExecution.getStatus() == BatchStatus.FAILED) {
	        //job failure
	    	System.out.println("JOB FAILED");
	    	
	    }
	}
}

