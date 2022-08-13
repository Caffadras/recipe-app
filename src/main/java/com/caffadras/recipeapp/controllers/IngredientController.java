package com.caffadras.recipeapp.controllers;

import com.caffadras.recipeapp.model.Ingredient;
import com.caffadras.recipeapp.model.Recipe;
import com.caffadras.recipeapp.services.IngredientService;
import com.caffadras.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class IngredientController {
    private final RecipeService recipeService;

    private final IngredientService ingredientService;


    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/recipe/{recipeId}/ingredient/index")
    public String index(@PathVariable String recipeId, Model model){
        model.addAttribute("recipe", recipeService.findById(Long.parseLong(recipeId)));
        return "recipe/ingredient/index";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String requestForm(@PathVariable String recipeId, Model model){
        Ingredient ingredient = new Ingredient();
        Recipe recipe = recipeService.findById(Long.valueOf(recipeId));
        ingredient.setRecipe(recipe);
        model.addAttribute("ingredient", ingredient);
        return "recipe/ingredient/form";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String requestUpdateForm(@PathVariable String recipeId, @PathVariable String ingredientId, Model model){
        Recipe recipe = recipeService.findById(Long.valueOf(recipeId));
        model.addAttribute("ingredient",
                ingredientService.findById(recipe, Long.valueOf(ingredientId)));
        return "recipe/ingredient/form";
    }

    @PostMapping("/recipe/{recipeId}/ingredient/new")
    public String saveIngredient(@PathVariable String recipeId, @ModelAttribute Ingredient ingredient){
        log.debug("Request to save ingredient with id: " +ingredient.getId() + "(description: "+ingredient.getDescription()
                        + ") of recipe with id: " + recipeId);
        ingredientService.save(recipeService.findById(Long.valueOf(recipeId)), ingredient);
        return "redirect:index/";
    }
    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId,
                                   @PathVariable String ingredientId){
        log.debug("Request to delete ingredient with id: " + ingredientId + " of recipe with id: " + recipeId);
        ingredientService.deleteById(recipeService.findById(Long.valueOf(recipeId)), Long.valueOf(ingredientId));
        return "redirect:/recipe/{recipeId}/ingredient/index";
    }
}
