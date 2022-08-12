package com.caffadras.recipeapp.controllers;

import com.caffadras.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
public class IngredientController {
    private final RecipeService recipeService;

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{recipeId}/ingredient/index")
    public String index(@PathVariable String recipeId, Model model){
        log.debug("Request to index ingredients of recipe with id: " + recipeId);
        model.addAttribute("recipe", recipeService.findById(Long.parseLong(recipeId)));
        return "recipe/ingredient/index";
    }
}
