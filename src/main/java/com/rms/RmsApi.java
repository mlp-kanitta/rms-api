package com.rms;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.rms.model.EmployeeModel;
import com.rms.repository.EmployeeRepository;

@EnableResourceServer
@EnableAuthorizationServer
@SpringBootApplication
public class RmsApi {

	public static void main(String[] args) {
		SpringApplication.run(RmsApi.class, args);
	}

	// init bean to insert 3 employee into h2 database.
	@Bean
	CommandLineRunner initDatabase(EmployeeRepository repository) {
		return args -> {
			repository.save(new EmployeeModel("001", "Smith", "L", "004424548", new BigDecimal("1452474.41")));
			repository.save(new EmployeeModel("002", "Michel", "L", "004424548", new BigDecimal("1452474.41")));
			repository.save(new EmployeeModel("003", "John", "L", "004424548", new BigDecimal("1452474.41")));
		};
	}
}
