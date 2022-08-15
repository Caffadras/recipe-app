package com.caffadras.recipeapp.services;

import com.caffadras.recipeapp.model.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService{

    @Override
    public void save(Recipe recipe, MultipartFile file) {
        log.debug("Request to save file with size: " + file.getSize() + " bytes");
    }
}
