package com.lucianaugusto.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.lucianaugusto.recipeapp.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
