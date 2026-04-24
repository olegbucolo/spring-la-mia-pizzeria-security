package org.exercises.java.spring_la_mia_pizzeria_security.models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(length = 50)
    @Size(max = 50, message = "Il nome deve contenere al massimo 50 lettere")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "ingredients")
    private Set<Pizza> pizzas = new HashSet<>();

    public Ingredient() {
    };

    public Ingredient(String name) {
        this.name = name;
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(Set<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Ingredient))
            return false;
        Ingredient ingredient = (Ingredient) o;
        return id != null && id.equals(ingredient.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "\n" + " Ingredient{" + "id=" + id + ", name='" + name + "'}";
    }
}
