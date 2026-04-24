package org.exercises.java.spring_la_mia_pizzeria_security;

import org.exercises.java.spring_la_mia_pizzeria_security.models.Ingredient;
import org.exercises.java.spring_la_mia_pizzeria_security.repositories.IngredientsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringLaMiaPizzeriaCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringLaMiaPizzeriaCrudApplication.class, args);
	}

	@Bean
	CommandLineRunner run(IngredientsRepository ingrRepo) {
		return args -> {
			System.out.println(ingrRepo.findAll().toString());
		};
	}

}
