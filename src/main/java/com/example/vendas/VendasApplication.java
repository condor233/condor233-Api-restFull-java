package com.example.vendas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.example.vendas.entity"})
@EnableJpaRepositories(basePackages = {"com.example.vendas.repository"})
@ComponentScan(basePackages = {"com.example.vendas.service", "com.example.vendas.controller", "com.example.vendas.exception"})
@SpringBootApplication
public class VendasApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}
