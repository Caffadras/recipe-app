package com.caffadras.recipeapp.services;

import com.caffadras.recipeapp.model.Recipe;
import com.caffadras.recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @InjectMocks
    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getRecipes() {
        Recipe testRecipe =new Recipe();
        List<Recipe> recipeList = new ArrayList<>();
        recipeList.add(testRecipe);

        when(recipeRepository.findAll()).thenReturn(recipeList);
        List<Recipe> returnedList = recipeService.getRecipes();

        verify(recipeRepository, times(1)).findAll();
        assertEquals(recipeList, returnedList);
    }
}