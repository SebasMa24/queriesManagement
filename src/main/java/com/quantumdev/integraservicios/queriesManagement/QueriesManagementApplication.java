package com.quantumdev.integraservicios.queriesManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication
@EnableJpaRepositories("com.quantumdev.integraservicios.database.repository")
@EntityScan("com.quantumdev.integraservicios.database.model")
public class QueriesManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(QueriesManagementApplication.class, args);
	}

}
