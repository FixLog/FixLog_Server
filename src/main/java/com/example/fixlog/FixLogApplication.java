package com.example.fixlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//Created At 설정
@EnableJpaAuditing
public class FixLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(FixLogApplication.class, args);
	}

}
