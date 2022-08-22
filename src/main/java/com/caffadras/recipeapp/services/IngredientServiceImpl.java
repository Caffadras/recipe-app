package com.caffadras.recipeapp.services;

import com.caffadras.recipeapp.exceptions.NotFoundException;
import com.caffadras.recipeapp.model.Ingredient;
import com.caffadras.recipeapp.model.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService{

    private final RecipeService recipeService;

    public IngredientServiceImpl(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public Ingredient findById(@NonNull Recipe recipe, @NonNull  Long id) {
        return recipe.getIngredients().stream()
                .filter(ingredient -> Objects.equals(ingredient.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Ingredient not found! ID: " + id));
    }

    @Override
    public Ingredient save(@NonNull Recipe recipe, @NonNull Ingredient ingredient) {
        if(recipe.getIngredients().remove(ingredient)){
            log.debug("Updated ingredient with id: "+ ingredient.getId()
                    + " (Description: " + ingredient.getDescription() + ")");
        } else{
            log.debug("Added new ingredient (Description: " + ingredient.getDescription() + ")");
        }

        recipe.addIngredient(ingredient);
        recipeService.save(recipe);
        return ingredient;
    }

    @Override
    public void deleteById(@NonNull Recipe recipe, @NonNull Long ingredientId) {
        Ingredient ingredient = findById(recipe, ingredientId);
        if(recipe.getIngredients().remove(ingredient)){
            ingredient.setRecipe(null);
            log.debug("Deleted ingredient with id: "+ ingredient.getId()
                    + " (Description: " + ingredient.getDescription() + ")");
        } else{
            throw new NotFoundException("Ingredient not found! ID: " + ingredientId);
        }
        recipeService.save(recipe);
    }
}
