package com.okestro.omok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class OmokApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmokApplication.class, args);
	}

}
