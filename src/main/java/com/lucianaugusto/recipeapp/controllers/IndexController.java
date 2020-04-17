package com.lucianaugusto.recipeapp.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lucianaugusto.recipeapp.domain.Category;
import com.lucianaugusto.recipeapp.domain.UnitOfMeasure;
import com.lucianaugusto.recipeapp.repositories.CategoryRepository;
import com.lucianaugusto.recipeapp.repositories.UnitOfMeasureRepository;
import com.lucianaugusto.recipeapp.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexController {
	
	private final RecipeService recipeService;
	
	
//	Injecting a service using constructor-based dependency injection
	public IndexController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@RequestMapping({"", "/", "/index"})
	public String getIndexPage(Model model) {

		model.addAttribute("recipes", recipeService.getRecipes());
		log.debug("Controller Log");
		
		return "index";
	}
}
