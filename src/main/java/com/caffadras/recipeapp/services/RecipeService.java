package com.caffadras.recipeapp.services;

import com.caffadras.recipeapp.model.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> getRecipes();

    Recipe findById(Long id);

    Recipe save(Recipe recipe);
}
