package com.caffadras.recipeapp.controllers;

import com.caffadras.recipeapp.model.Ingredient;
import com.caffadras.recipeapp.model.Recipe;
import com.caffadras.recipeapp.services.IngredientService;
import com.caffadras.recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @InjectMocks
    IngredientController ingredientController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    void index() throws Exception {
        mockMvc.perform(get("/recipe/1/ingredient/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/index"));
    }

    @Test
    void requestForm() throws Exception{
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/form"));
    }

    @Test
    void requestUpdateForm() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setId(2L);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(4L);
        recipe.addIngredient(ingredient);

        doReturn(recipe).when(recipeService).findById(eq(2L));
        doReturn(ingredient).when(ingredientService).findById(eq(recipe), eq(4L));

        mockMvc.perform(get("/recipe/2/ingredient/4/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/form"))
                .andExpect(model().attributeExists("ingredient"));

        verify(ingredientService, times(1)).findById(any(), anyLong());

        mockMvc.perform(get("/recipe/2/ingredient/40/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/form"))
                .andExpect(model().attributeDoesNotExist("ingredient"));

    }

    @Test
    void saveIngredient() throws Exception{
        mockMvc.perform(post("/recipe/2/ingredient/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:index/"));
    }

    @Test
    void deleteIngredient() throws Exception{
        mockMvc.perform(get("/recipe/2/ingredient/4/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/{recipeId}/ingredient/index"));
    }
}