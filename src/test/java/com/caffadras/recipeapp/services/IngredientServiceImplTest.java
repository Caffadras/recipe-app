package com.caffadras.recipeapp.services;

import com.caffadras.recipeapp.model.Ingredient;
import com.caffadras.recipeapp.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    RecipeService recipeService;

    @InjectMocks
    IngredientServiceImpl ingredientService;

    Recipe expectedRecipe;
    Ingredient expectedIngredient;

    @BeforeEach
    void setUp() {
        expectedRecipe = new Recipe();
        expectedRecipe.setId(10L);
        expectedRecipe.setDescription("TestRecipe");
        expectedIngredient = new Ingredient();
        expectedIngredient.setId(20L);

        expectedRecipe.addIngredient(expectedIngredient);
    }

    @Test
    void findById() {
        Ingredient returnedIngredient = ingredientService.findById(expectedRecipe, expectedIngredient.getId());

        assertEquals(expectedIngredient.getId(), returnedIngredient.getId());
        assertThrows(RuntimeException.class, () -> ingredientService.findById(expectedRecipe, -1L));
        assertThrows(NullPointerException.class, () -> ingredientService.findById(null, expectedIngredient.getId()));
    }

    @Test
    void save() {
        Ingredient ingredientToSave = new Ingredient();
        ingredientToSave.setId(30L);

        ingredientService.save(expectedRecipe, ingredientToSave);
        assertEquals(2, expectedRecipe.getIngredients().size());
        assertTrue(expectedRecipe.getIngredients().contains(ingredientToSave));
        assertEquals(expectedRecipe, ingredientToSave.getRecipe());
        assertThrows(NullPointerException.class, () -> ingredientService.save(null, ingredientToSave));

        verify(recipeService, times(1)).save(expectedRecipe);
    }

    @Test
    void deleteById() {
        ingredientService.deleteById(expectedRecipe, expectedIngredient.getId());

        assertEquals(0, expectedRecipe.getIngredients().size());
        assertNull(expectedIngredient.getRecipe());
        verify(recipeService, times(1)).save(expectedRecipe);
    }
}