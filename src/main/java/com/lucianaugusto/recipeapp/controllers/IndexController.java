package com.lucianaugusto.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@RequestMapping({ "", "/", "/index" })
	public String getIndexPage(Model model) {

		model.addAttribute("recipes", recipeService.getRecipes().collectList().block());
		log.debug("Controller Log");

		return "index";
	}
}
