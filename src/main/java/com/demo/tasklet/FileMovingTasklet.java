package com.demo.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;

public class FileMovingTasklet implements Tasklet, InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	// private Resource sourceDirectory = "file:src/main/resources/data/*.csv";
	// private Resource targetDirectory;
	//
	// @Override
	// public void afterPropertiesSet() throws Exception {
	// Assert.notNull(sourceDirectory, "Set the source directory");
	// Assert.notNull(targetDirectory, "Set the target directory");
	// }
	//
	// @SuppressWarnings("deprecation")
	// @Override
	// public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
	//
	// InputStream inStream = null;
	// OutputStream outStream = null;
	//
	// File[] files;
	// File dir = sourceDirectory.getFile();
	// Assert.state(dir.isDirectory());
	// files = dir.listFiles();
	// File bfile = null;
	// for (int i = 0; i < files.length; i++) {
	//
	// bfile = new File(targetDirectory.getURL().getPath() + File.separator + files[i].getName());
	//
	// inStream = new FileInputStream(files[i]);
	// outStream = new FileOutputStream(bfile);
	//
	// byte[] buffer = new byte[1024];
	//
	// int length;
	// // copy the file content in bytes
	// while ((length = inStream.read(buffer)) > 0) {
	//
	// outStream.write(buffer, 0, length);
	//
	// }
	//
	// inStream.close();
	// outStream.close();
	// }
	// return RepeatStatus.FINISHED;
	// }
}
