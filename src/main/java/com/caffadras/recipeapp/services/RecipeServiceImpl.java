package com.caffadras.recipeapp.services;

import com.caffadras.recipeapp.exceptions.NotFoundException;
import com.caffadras.recipeapp.model.Recipe;
import com.caffadras.recipeapp.repositories.RecipeRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService{
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(@NonNull Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new NotFoundException("Recipe not found! ID: " + id));
    }

    @Override
    public void deleteById(@NonNull Long id) {
        try{
            recipeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new NotFoundException("Recipe not found! ID: " + id, e);
        }
    }

    @Override
    public Recipe save(@NonNull Recipe recipe) {
        return recipeRepository.save(recipe);
    }

}
