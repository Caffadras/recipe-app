package com.caffadras.recipeapp.controllers;

import com.caffadras.recipeapp.model.Recipe;
import com.caffadras.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Slf4j
public class RecipeController {

    public final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findById(Long.parseLong(id)));
        return "recipe/show";
    }

    @GetMapping("/recipe/new")
    public String requestForm(Model model){
        log.debug("Request to create new recipe");
        model.addAttribute("recipe", new Recipe());
        return "recipe/form";
    }

    @PostMapping("/recipe/new")
    public String saveRecipe(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult bindingResult){
        log.debug("Request to save recipe with id: " + recipe.getId()+ " (" + recipe.getDescription() + ")");
        if (bindingResult.hasErrors()){
            StringBuilder errorString = new StringBuilder("Recipe has errors: ");
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorString.append("\n").append(error.toString());
            }
            log.warn(errorString.toString());

            return "recipe/form";
        }

        recipeService.save(recipe);
        return "redirect:/index";
    }

    @GetMapping("recipe/{id}/update")
    public String requestUpdateForm(@PathVariable String id, Model model){
        log.debug("Request to update recipe with id: " + id);
        Recipe recipeToUpdate = recipeService.findById(Long.valueOf(id));
        model.addAttribute("recipe", recipeToUpdate);
        return "recipe/form";
    }
    @GetMapping("recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id){
        log.debug("Request to update recipe with id: " + id);
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/index";
    }

}
