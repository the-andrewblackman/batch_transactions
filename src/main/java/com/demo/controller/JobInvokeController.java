package com.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class JobInvokeController {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;

	@RequestMapping("/invokejob")
	public String handle() throws Exception {

		try {

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-d HH:mm:ss.S");

			JobParameters jobParameters = new JobParametersBuilder()
					.addString("time", format.format(Calendar.getInstance().getTime()))
					.toJobParameters();
			jobLauncher.run(job, jobParameters);

			return "Batch job has been invoked";

		} catch (Exception e) {

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Something's gone wrong, tell the admin", e);
		}
	}
}

// ====================================================================================

// @Configuration
// @EnableScheduling
// public class JobInvokeController {
//
// @Autowired
// JobLauncher jobLauncher;
//
// @Autowired
// Job job;
//
// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
//
// @Scheduled(fixedDelay = 5000, initialDelay = 5000)
// public void scheduleByFixedRate() throws Exception {
// System.out.println("Batch job starting");
// JobParameters jobParameters = new JobParametersBuilder()
// .addString("time", format.format(Calendar.getInstance().getTime())).toJobParameters();
// jobLauncher.run(job, jobParameters);
// System.out.println("Batch job executed successfully\n");
// }
// }