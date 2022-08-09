package com.caffadras.recipeapp.repositories;

import com.caffadras.recipeapp.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeRepositoryTest {

    @Mock
    RecipeRepository recipeRepository;

    @Captor
    ArgumentCaptor<Recipe> captor;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findById() {
        Recipe recipe = new Recipe();
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        long expectedID = 10L;

        assertEquals(recipeOptional, recipeRepository.findById(expectedID));
        verify(recipeRepository, times(1)).findById(captor.capture());
        assertEquals(expectedID, captor.getValue());
    }
}