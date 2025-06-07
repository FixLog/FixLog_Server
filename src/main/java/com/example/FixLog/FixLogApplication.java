package com.example.FixLog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//Create_At 어노테이션
@EnableJpaAuditing
@SpringBootApplication
public class FixLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(FixLogApplication.class, args);
	}

}
