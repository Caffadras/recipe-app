package com.caffadras.recipeapp.controllers;

import com.caffadras.recipeapp.model.Recipe;
import com.caffadras.recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    @InjectMocks
    RecipeController recipeController;

    MockMvc mockMvc;
    @BeforeEach
    void setUp() {
         mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void showById() throws Exception {
        Long expectedId = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(expectedId);

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"));

        verify(recipeService, times(1)).findById(eq(expectedId));
    }


    @Test
    void requestForm() throws Exception{
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/form"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void requestUpdateForm() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/form"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void deleteRecipe() throws Exception{
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
    }

    @Test
    void save() throws Exception{
        mockMvc.perform(post("/recipe/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
    }
}