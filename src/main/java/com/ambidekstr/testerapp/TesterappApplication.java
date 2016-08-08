package com.ambidekstr.testerapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class TesterappApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesterappApplication.class, args);
	}
}
