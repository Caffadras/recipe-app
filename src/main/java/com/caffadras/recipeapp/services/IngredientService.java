package com.caffadras.recipeapp.services;


import com.caffadras.recipeapp.model.Ingredient;
import com.caffadras.recipeapp.model.Recipe;

public interface IngredientService {

    Ingredient findById(Recipe recipe, Long id);

    Ingredient save(Recipe recipe, Ingredient ingredient);

    void deleteById(Recipe recipe, Long ingredientId);
}
