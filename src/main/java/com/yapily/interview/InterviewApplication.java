package com.yapily.interview;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class InterviewApplication {
	static Logger logger = LoggerFactory.getLogger(InterviewApplication.class);

	public static void main(String[] args) throws NoSuchAlgorithmException {
		logger.info("Start of InterviewApplication Booting");
		logger.info("***** ** ******************** *******");

		SpringApplication.run(InterviewApplication.class, args);

		logger.info("End of InterviewApplication Booting");
		logger.info("*** ** ******************** *******");
	}
}
