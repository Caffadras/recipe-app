package com.caffadras.recipeapp.repositories;

import com.caffadras.recipeapp.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
