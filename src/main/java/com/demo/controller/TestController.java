package com.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;

	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

	@RequestMapping("/user/invoke")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() throws Exception {

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

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
}