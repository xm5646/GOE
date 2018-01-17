package com.project.goe.projectgeodbserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProjectGeoDbserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectGeoDbserverApplication.class, args);
	}
}
