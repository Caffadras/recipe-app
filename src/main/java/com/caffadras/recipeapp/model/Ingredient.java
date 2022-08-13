package com.caffadras.recipeapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Double amount;
    @OneToOne
    private UnitOfMeasure unitOfMeasure;
    @ManyToOne
    private Recipe recipe;


    public Ingredient(String description, Double amount, UnitOfMeasure unitOfMeasure) {
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ingredient other = (Ingredient) obj;
        return id != null && id.equals(other.getId());
    }

    //fixme: hashCode relies only on id value.
    // Any ingredient created with no args constructor (i.e id is null) will fail to be distinct.
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
