package com.api.mercadobmxbr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class MercadoBmxBrApplication {

	public static void main(String[] args) {
		SpringApplication.run(MercadoBmxBrApplication.class, args);
	}

}
