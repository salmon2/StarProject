package com.sparta.StarProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StarProjectApplication{
	public static void main(String[] args) {
		SpringApplication.run(StarProjectApplication.class, args);
	}


}
