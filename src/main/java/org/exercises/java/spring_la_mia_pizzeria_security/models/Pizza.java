package org.exercises.java.spring_la_mia_pizzeria_security.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "pizzas")
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 100, message = "La lunghezza deve essere minore di 100 lettere")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank
    @Size(max = 250, message = "La lunghezza deve essere minore di 250 lettere")
    @Column(nullable = false, length = 250)
    private String description;

    @Size(max = 500)
    private String imageUrl;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true, message = "il prezzo deve essere maggiore di 0")
    @Column(nullable = false, precision = 10, scale = 2)
    @Digits(integer = 8, fraction = 2)
    private BigDecimal price;

    @OneToMany(mappedBy = "pizza", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SpecialOffer> specialOffers = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "pizza_ingredient", joinColumns = @JoinColumn(name = "pizza_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> ingredients = new HashSet<>();

    public Pizza() {
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<SpecialOffer> getSpecialOffers() {
        return specialOffers;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    // HELPER METHODS

    // add/remove special offers helper
    public void addSpecialOffer(SpecialOffer offer) {
        if (offer == null)
            return;
        specialOffers.add(offer);
        offer.setPizza(this);
    }

    public void removeSpecialOffer(SpecialOffer offer) {
        if (offer == null || !specialOffers.contains(offer))
            return;
        specialOffers.remove(offer);
        offer.setPizza(null);
    }

    // add/remove ingredients helper

    public void addIngredient(Ingredient ingredient) {
        if (ingredient == null)
            return;

        ingredients.add(ingredient);
        ingredient.getPizzas().add(this);
    }

    public void removeIngredient(Ingredient ingredient) {
        if (ingredient == null || !ingredients.contains(ingredient))
            return;

        ingredients.remove(ingredient);
        ingredient.getPizzas().remove(this);
    }

    // Override for comparing using id instead of memory addresses
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Pizza))
            return false;
        Pizza pizza = (Pizza) o;
        return id != null && id.equals(pizza.getId());
    }

    // Override for saving
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
