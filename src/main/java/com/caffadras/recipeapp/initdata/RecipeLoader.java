package com.caffadras.recipeapp.initdata;

import com.caffadras.recipeapp.model.*;
import com.caffadras.recipeapp.repositories.CategoryRepository;
import com.caffadras.recipeapp.repositories.RecipeRepository;
import com.caffadras.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    private final RecipeRepository recipeRepository;

    public RecipeLoader(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        recipeRepository.saveAll(getRecipes());
    }

    List<Recipe> getRecipes(){
        List<Recipe> recipes = new ArrayList<>();

        UnitOfMeasure tablespoonUom = unpackOptional(unitOfMeasureRepository.findByUom("Tablespoon"));
        UnitOfMeasure teaspoonUom = unpackOptional(unitOfMeasureRepository.findByUom("Teaspoon"));
        UnitOfMeasure dashUom = unpackOptional(unitOfMeasureRepository.findByUom("Dash"));
        UnitOfMeasure eachUom = unpackOptional(unitOfMeasureRepository.findByUom("Each"));

        Category americanCategory = unpackOptional(categoryRepository.findByDescription("American"));

        Recipe guacamoleRecipe = new Recipe();
        guacamoleRecipe.setDescription("A perfect guacamole");
        guacamoleRecipe.setPrepTime(10);
        guacamoleRecipe.setCookTime(1);
        guacamoleRecipe.setServings(1);
        guacamoleRecipe.setDifficulty(Difficulty.EASY);
        guacamoleRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n");

        Notes guacamoleNotes = new Notes();
        guacamoleNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.");

        guacamoleRecipe.setNotes(guacamoleNotes);

        guacamoleRecipe.addIngredient((new Ingredient("Ripe avocados", 2.0, eachUom)));
        guacamoleRecipe.addIngredient((new Ingredient("Kosher salt", 0.5, teaspoonUom)));
        guacamoleRecipe.addIngredient((new Ingredient("Fresh lime juice or lemon juice", 2.0, tablespoonUom)));
        guacamoleRecipe.addIngredient((new Ingredient("Minced red onion or thinly sliced green onion", 2.0, tablespoonUom)));
        guacamoleRecipe.addIngredient((new Ingredient("Serrano chilies, stems and seeds removed, minced", 2.0, eachUom)));
        guacamoleRecipe.addIngredient((new Ingredient("Cilantro", 2.0, tablespoonUom)));
        guacamoleRecipe.addIngredient((new Ingredient("Freshly grated black pepper", 2.0, dashUom)));
        guacamoleRecipe.addIngredient((new Ingredient("Ripe tomato, seeds and pulp removed, chopped", 0.5, eachUom)));


        guacamoleRecipe.getCategories().add(americanCategory);
        recipes.add(guacamoleRecipe);
        return recipes;
    }

    private <T> T unpackOptional(Optional<T> optional){
        if (optional.isEmpty()) {
            throw new RuntimeException("Required optional is empty!");
        }
        return optional.get();
    }
}
