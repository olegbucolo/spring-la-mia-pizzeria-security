package org.exercises.java.spring_la_mia_pizzeria_security.repositories;

import java.util.Optional;

import org.exercises.java.spring_la_mia_pizzeria_security.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
