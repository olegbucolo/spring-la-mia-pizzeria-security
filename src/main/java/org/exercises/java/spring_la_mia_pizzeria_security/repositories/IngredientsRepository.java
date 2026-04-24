package org.exercises.java.spring_la_mia_pizzeria_security.repositories;

import org.exercises.java.spring_la_mia_pizzeria_security.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientsRepository extends JpaRepository<Ingredient, Integer>{
    
}
