package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Exam0912Application {

	public static void main(String[] args) {
		SpringApplication.run(Exam0912Application.class, args);
	}

}
