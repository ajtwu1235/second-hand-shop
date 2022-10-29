package com.capstone.skone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
//@EnableRedisHttpSession
@SpringBootApplication
public class SkoneApplication {
	public static void main(String[] args) {
		SpringApplication.run(SkoneApplication.class, args);
	}
}