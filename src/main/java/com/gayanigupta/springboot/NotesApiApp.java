package com.gayanigupta.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages={"com.gayanigupta.springboot"})
public class NotesApiApp {

	public static void main(String[] args) {
		SpringApplication.run(NotesApiApp.class, args);
	}
}
