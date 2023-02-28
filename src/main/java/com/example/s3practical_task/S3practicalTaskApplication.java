package com.example.s3practical_task;

import com.example.s3practical_task.controller.S3Sync;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class S3practicalTaskApplication {

	public static void main(String[] args) {
		var run = SpringApplication.run(S3practicalTaskApplication.class, args);
//		run.getBean(S3Controller.class).getPresignedUrl();
		run.getBean(S3Sync.class).sync("C:\\Users\\user\\IdeaProjects\\s3practical_task\\src\\main\\resources\\templates");
	}

}
