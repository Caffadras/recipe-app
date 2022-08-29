package com.caffadras.recipeapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    @Size(min = 3, max = 255)
    private String description;

    @NotNull
    @Min(1)
    @Max(999)
    private Integer prepTime;

    @NotNull
    @Min(1)
    @Max(999)
    private Integer cookTime;

    @NotNull
    @Min(1)
    @Max(99)
    private Integer servings;

    private String source;


    private String url;

    @Lob
    @NotEmpty
    private String directions;

    @Lob
    private Byte[] image;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Notes notes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "recipe_categories",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "categories_id")
    )
    private Set<Category> categories = new HashSet<>();

    @OneToOne
    private UnitOfMeasure uom;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;


    public void setNotes(Notes notes) {
        this.notes = notes;
        notes.setRecipe(this);
    }

    public void addIngredient(Ingredient ingredient){
        if (getIngredients() == null){
            setIngredients(new HashSet<>());
        }
        getIngredients().add(ingredient);
        ingredient.setRecipe(this);
    }

}
