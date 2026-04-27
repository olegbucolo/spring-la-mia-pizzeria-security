package org.exercises.java.spring_la_mia_pizzeria_security;

import java.util.Set;

import org.exercises.java.spring_la_mia_pizzeria_security.models.Role;
import org.exercises.java.spring_la_mia_pizzeria_security.models.User;
import org.exercises.java.spring_la_mia_pizzeria_security.repositories.RoleRepository;
import org.exercises.java.spring_la_mia_pizzeria_security.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringLaMiaPizzeriaCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringLaMiaPizzeriaCrudApplication.class, args);
	}

	@Bean
	CommandLineRunner init(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder encoder) {
		return args -> {

			// create roles if missing
			Role adminRole = roleRepo.findByName("ROLE_ADMIN")
					.orElseGet(() -> {
						Role r = new Role();
						r.setName("ROLE_ADMIN");
						return roleRepo.save(r);
					});

			Role userRole = roleRepo.findByName("ROLE_USER")
					.orElseGet(() -> {
						Role r = new Role();
						r.setName("ROLE_USER");
						return roleRepo.save(r);
					});

			// create user only if missing (optional but recommended)
			if (userRepo.findByUsername("paolo").isEmpty()) {
				User user = new User();
				user.setUsername("paolo");
				user.setPassword(encoder.encode("pass"));
				user.setRoles(Set.of(userRole));
				userRepo.save(user);
			}
			
			// create admin only if missing (optional but recommended)
			if(userRepo.findByUsername("paolo_admin").isEmpty()){
				User admin = new User();
				admin.setUsername("paolo_admin");
				admin.setPassword(encoder.encode("pass_admin"));
				admin.setRoles(Set.of(adminRole));
				userRepo.save(admin);
			}
		};
	}

}
