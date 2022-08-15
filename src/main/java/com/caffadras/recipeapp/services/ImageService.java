package com.caffadras.recipeapp.services;

import com.caffadras.recipeapp.model.Recipe;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    //todo: void -> MultiPartFile?
    void save(Recipe recipe,  MultipartFile file);
}
