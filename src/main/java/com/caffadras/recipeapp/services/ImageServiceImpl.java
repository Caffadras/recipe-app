package com.caffadras.recipeapp.services;

import com.caffadras.recipeapp.model.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService{

    private final RecipeService recipeService;

    public ImageServiceImpl(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public void save(@NonNull Recipe recipe, MultipartFile file) {
        log.debug("Request to save file with size: " + file.getSize() + " bytes");

        try{
            Byte[] image = new Byte[(int)file.getSize()];
            int i=0;
            for (byte aByte : file.getBytes()) {
                image[i++] = aByte;
            }
            recipe.setImage(image);
            recipeService.save(recipe);
        } catch(IOException e){
            log.error("Couldn't save the file! Recipe (id: + "+ recipe.getId()+"  " + recipe.getDescription());
        }
    }
}
