package org.exercises.java.spring_la_mia_pizzeria_security.repositories;

import java.util.Optional;

import org.exercises.java.spring_la_mia_pizzeria_security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
