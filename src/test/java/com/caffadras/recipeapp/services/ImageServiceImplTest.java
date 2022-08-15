package com.caffadras.recipeapp.services;

import com.caffadras.recipeapp.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    RecipeService recipeService;
    ImageService imageService;

    @BeforeEach
    void setUp() {
        imageService = new ImageServiceImpl(recipeService);
    }

    @Test
    void save() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        String imageStr = "Image";
        Byte[] bytesBoxed = new Byte[imageStr.getBytes().length];

        int i = 0;
        for (byte aByte : imageStr.getBytes()){
            bytesBoxed[i++] = aByte;
        }

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", imageStr.getBytes(StandardCharsets.UTF_8));
        imageService.save(recipe, mockMultipartFile);

        verify(recipeService, times(1)).save(eq(recipe));
        assertArrayEquals(bytesBoxed, recipe.getImage());
    }
}