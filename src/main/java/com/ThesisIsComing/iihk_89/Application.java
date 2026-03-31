package com.ThesisIsComing.iihk_89;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
//@SpringBootApplication(scanBasePackages = {
//		"infrastructure",
//		"application",
//		"domain",
//		"domainServices"
//})
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
