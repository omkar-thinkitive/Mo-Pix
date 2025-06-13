package com.mopix.Mopix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MopixApplication {
	public static void main(String[] args) {
		System.out.println("Running Mopix dev");
		SpringApplication.run(MopixApplication.class, args);
	}

}
