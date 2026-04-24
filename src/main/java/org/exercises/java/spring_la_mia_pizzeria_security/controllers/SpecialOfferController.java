package org.exercises.java.spring_la_mia_pizzeria_security.controllers;

import org.exercises.java.spring_la_mia_pizzeria_security.models.Pizza;
import org.exercises.java.spring_la_mia_pizzeria_security.models.SpecialOffer;
import org.exercises.java.spring_la_mia_pizzeria_security.repositories.PizzaRepository;
import org.exercises.java.spring_la_mia_pizzeria_security.repositories.SpecialOfferRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/offers")
public class SpecialOfferController {

    private final PizzaRepository pizzaRepo;
    private final SpecialOfferRepository offerRepo;

    public SpecialOfferController(PizzaRepository pizzaRepo, SpecialOfferRepository offerRepo) {
        this.pizzaRepo = pizzaRepo;
        this.offerRepo = offerRepo;
    }

    @GetMapping("/create")
    public String create(@RequestParam Integer pizzaId, Model model) {
        model.addAttribute("offer", new SpecialOffer());
        model.addAttribute("pizzaId", pizzaId);
        return "offers/create-offer";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        SpecialOffer offer = offerRepo.findById(id).orElseThrow();
        model.addAttribute("offer", offer);
        model.addAttribute("pizzaId", offer.getPizza().getId());
        return "offers/create-offer";
    }

    @PostMapping
    public String store(
            @RequestParam Integer pizzaId,
            @Valid @ModelAttribute("offer") SpecialOffer offer,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pizzaId", pizzaId);
            return "offers/create-offer";
        }

        Pizza pizza = pizzaRepo.findById(pizzaId).orElseThrow(() -> new RuntimeException("Pizza not found"));
        pizza.addSpecialOffer(offer);
        pizzaRepo.save(pizza);

        return "redirect:/pizzas/" + pizzaId;
    }
}
