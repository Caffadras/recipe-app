package com.caffadras.recipeapp.controllers;

import com.caffadras.recipeapp.model.Recipe;
import com.caffadras.recipeapp.services.ImageService;
import com.caffadras.recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    @InjectMocks
    ImageController imageController;

    MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    void getForm() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        doReturn(recipe).when(recipeService).findById(anyLong());

        mockMvc.perform(get("/recipe/1/update/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/image/form"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findById(anyLong());
    }

    @Test
    void getImage() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        String imageStr = "Image";
        Byte[] bytesBoxed = new Byte[imageStr.getBytes().length];

        int i = 0;
        for (byte aByte : imageStr.getBytes()){
            bytesBoxed[i++] = aByte;
        }

        recipe.setImage(bytesBoxed);
        doReturn(recipe).when(recipeService).findById(anyLong());


        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();

        assertArrayEquals(imageStr.getBytes(), responseBytes);
    }

    @Test
    void saveImage() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        doReturn(recipe).when(recipeService).findById(anyLong());

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "testing.txt",
                "text/plain", "Testing".getBytes(StandardCharsets.UTF_8));


        mockMvc.perform(multipart("/recipe/1/update/image").file(mockMultipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1"));

        verify(imageService, times(1)).save(any(), any());
        verify(recipeService, times(1)).findById(anyLong());
    }
}