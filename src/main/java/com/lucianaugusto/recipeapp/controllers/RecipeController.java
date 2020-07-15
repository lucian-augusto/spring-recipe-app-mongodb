package com.lucianaugusto.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lucianaugusto.recipeapp.commands.RecipeCommand;
import com.lucianaugusto.recipeapp.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RecipeController {

	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@GetMapping
	@RequestMapping("/recipe/{id}/show")
	public String showById(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		
		return "recipe/show";
	}
	
	@GetMapping
	@RequestMapping("recipe/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		
		return "recipe/recipeform";
	}
	
	@GetMapping
	@RequestMapping("recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		
		return "recipe/recipeform";
	}
	
//	@RequestMapping(name = "recipe", method = RequestMethod.POST) // Old way
	@PostMapping // New way of mapping HTTP requests
	@RequestMapping("recipe")
	public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
		
		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}
	
	@GetMapping
	@RequestMapping("recipe/{id}/delete")
	public String deleteById(@PathVariable Long id) {
		log.debug("Deleting id: " + id);
		
		recipeService.deleteById(id);
		
		return "redirect:/";
	}
}
