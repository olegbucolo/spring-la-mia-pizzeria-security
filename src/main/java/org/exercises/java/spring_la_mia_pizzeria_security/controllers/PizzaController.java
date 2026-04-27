package org.exercises.java.spring_la_mia_pizzeria_security.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.exercises.java.spring_la_mia_pizzeria_security.models.Ingredient;
import org.exercises.java.spring_la_mia_pizzeria_security.models.Pizza;
import org.exercises.java.spring_la_mia_pizzeria_security.repositories.IngredientsRepository;
import org.exercises.java.spring_la_mia_pizzeria_security.repositories.PizzaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {
    private final PizzaRepository pr;

    private final IngredientsRepository ingrRepo;

    public PizzaController(IngredientsRepository ingrRepo, PizzaRepository pr) {
        this.pr = pr;
        this.ingrRepo = ingrRepo;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("pizzas", pr.findAll());
        return "pizzas";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model) {
        Pizza pizza = pr.findById(id)
                .orElseThrow(() -> new RuntimeException("Pizza no found"));
        model.addAttribute("pizza", pizza);
        return "pizza-detail";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredients", ingrRepo.findAll());
        return "pizza-create";
    }

    @PostMapping
    public String store(@Valid @ModelAttribute Pizza pizza,
            @RequestParam(required = false) List<Integer> ingredientIds,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Errore nella creazione della pizza");
            return "pizza-create";
        }

        Set<Ingredient> ingredients = new HashSet<>();

        if (ingredientIds != null) {
            for (Integer id : ingredientIds) {
                ingrRepo.findById(id).ifPresent(ingredients::add);
            }
        }

        pizza.setIngredients(ingredients);
        pr.save(pizza);

        return "redirect:/pizzas";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        pr.deleteById(id);
        return "redirect:/pizzas";
    }

    @GetMapping("/{id}/edit")
    public String editAtId(@PathVariable int id, Model model) {
        Pizza pizza = pr.findById(id).orElseThrow();
        model.addAttribute("pizza", pizza);
        return "pizza-edit";
    }

    @PutMapping("/{id}")
    public String editAtId(
            @PathVariable int id,
            @Valid @ModelAttribute Pizza pizza,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Consollogghi");
            return "pizza-edit";
        }

        Pizza pizzaFromDb = pr.findById(id).orElseThrow();
        pizzaFromDb.setName(pizza.getName());
        pizzaFromDb.setDescription(pizza.getDescription());
        pizzaFromDb.setPrice(pizza.getPrice());
        pizzaFromDb.setId(id);
        pr.save(pizzaFromDb);
        return "redirect:/pizzas";
    }

}
