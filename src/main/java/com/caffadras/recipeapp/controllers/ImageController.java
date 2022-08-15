package com.caffadras.recipeapp.controllers;

import com.caffadras.recipeapp.services.ImageService;
import com.caffadras.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
public class ImageController {
    private final RecipeService recipeService;
    private final ImageService imageService;


    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("recipe/{id}/update/image")
    public String getForm(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        return "recipe/image/form";
    }

    @PostMapping("recipe/{id}/update/image")
    public String saveImage(@PathVariable String id, @RequestParam("file") MultipartFile file){
        imageService.save(recipeService.findById(Long.valueOf(id)), file);
        return "redirect:/recipe/{id}";
    }
}
