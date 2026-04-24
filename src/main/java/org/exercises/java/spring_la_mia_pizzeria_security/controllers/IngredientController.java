package org.exercises.java.spring_la_mia_pizzeria_security.controllers;

import org.exercises.java.spring_la_mia_pizzeria_security.models.Ingredient;
import org.exercises.java.spring_la_mia_pizzeria_security.repositories.IngredientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    IngredientsRepository ingrRepo;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("ingredients", ingrRepo.findAll());

        return "ingredients/ingredients";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "ingredients/ingredients-create";
    }

    @PostMapping
    public String store(
            @Valid @ModelAttribute Ingredient ingredient,
            BindingResult bindingResult) {
                if(bindingResult.hasErrors()){
                    System.out.println("FAHHH Ingredient create");
                    return "ingredients/ingredients-create";
                }
        ingrRepo.save(ingredient);
        return "redirect:/ingredients";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        ingrRepo.deleteById(id);
        return "redirect:/ingredients";
    }

}
