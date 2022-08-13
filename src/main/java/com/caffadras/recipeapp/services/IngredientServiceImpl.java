package com.caffadras.recipeapp.services;

import com.caffadras.recipeapp.model.Ingredient;
import com.caffadras.recipeapp.model.Recipe;
import lombok.extern.slf4j.Slf4j;
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
    public Ingredient findById(Recipe recipe, Long id) {
        //todo: NPE
        return recipe.getIngredients().stream()
                .filter(ingredient -> Objects.equals(ingredient.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public Ingredient save(Recipe recipe, Ingredient ingredient) {
        //todo: NPE
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
    public void deleteById(Recipe recipe, Long ingredientId) {
        //todo: NPE
        Ingredient ingredient = findById(recipe, ingredientId);
        if(recipe.getIngredients().remove(ingredient)){
            ingredient.setRecipe(null);
            log.debug("Deleted ingredient with id: "+ ingredient.getId()
                    + " (Description: " + ingredient.getDescription() + ")");
        } else{
            log.warn("Deleting an non-existing ingredient with id:" + ingredient.getId()
                    + " (Description: " + ingredient.getDescription() + ")");
        }
        recipeService.save(recipe);
    }
}
