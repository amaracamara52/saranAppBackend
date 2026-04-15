package org.sid.saranApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SaranAppApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(SaranAppApplication.class, args);
	}

}
