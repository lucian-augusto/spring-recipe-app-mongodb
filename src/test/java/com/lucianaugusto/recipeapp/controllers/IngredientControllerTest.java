package com.lucianaugusto.recipeapp.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.lucianaugusto.recipeapp.commands.IngredientCommand;
import com.lucianaugusto.recipeapp.commands.RecipeCommand;
import com.lucianaugusto.recipeapp.commands.UnitOfMeasureCommand;
import com.lucianaugusto.recipeapp.services.IngredientService;
import com.lucianaugusto.recipeapp.services.RecipeService;
import com.lucianaugusto.recipeapp.services.UnitOfMeasureService;

import reactor.core.publisher.Flux;

public class IngredientControllerTest {

	@Mock
	RecipeService recipeService;

	@Mock
	IngredientService ingredientService;

	@Mock
	UnitOfMeasureService unitOfMeasureService;

	IngredientController controller;

	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testListIngredients() throws Exception {
		String id = "1";
		RecipeCommand command = new RecipeCommand();

		when(recipeService.findCommandById(ArgumentMatchers.anyString())).thenReturn(command);

		mockMvc.perform(get("/recipe/" + id + "/ingredients")).andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/list")).andExpect(model().attributeExists("recipe"));

		verify(recipeService).findCommandById(id);
	}

	@Test
	public void testNewIngredientForm() throws Exception {
		String id = "1";
		RecipeCommand command = new RecipeCommand();
		command.setId(id);

		when(recipeService.findCommandById(ArgumentMatchers.anyString())).thenReturn(command);
		when(unitOfMeasureService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));

		mockMvc.perform(get("/recipe/" + id + "/ingredient/new")).andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/ingredientform"))
				.andExpect(model().attributeExists("ingredient")).andExpect(model().attributeExists("uomList"));

		verify(recipeService).findCommandById(ArgumentMatchers.anyString());
	}

	@Test
	public void testUpdateIngredientForm() throws Exception {
		String recipeId = "1";
		String ingredientId = "2";
		IngredientCommand ingredientCommand = new IngredientCommand();

		when(ingredientService.findByRecipeIdAndIngredientId(ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString())).thenReturn(ingredientCommand);
		when(unitOfMeasureService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));

		mockMvc.perform(get("/recipe/" + recipeId + "/ingredient/" + ingredientId + "/update"))
				.andExpect(status().isOk()).andExpect(view().name("recipe/ingredient/ingredientform"))
				.andExpect(model().attributeExists("ingredient")).andExpect(model().attributeExists("uomList"));
	}

	@Test
	public void testSaveOrUpdate() throws Exception {
		String recipeId = "1";
		String ingredientId = "2";
		IngredientCommand command = new IngredientCommand();
		command.setId(ingredientId);
		command.setRecipeId(recipeId);

		when(ingredientService.saveIngredientCommand(ArgumentMatchers.any())).thenReturn(command);

		mockMvc.perform(post("/recipe/" + recipeId + "/ingredient").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "").param("description", "TEST DESCRIPTION")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/" + recipeId + "/ingredient/" + ingredientId + "/show"));
	}

	@Test
	public void testDeleteIngredient() throws Exception {
		mockMvc.perform(get("/recipe/2/ingredient/3/delete")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/2/ingredients"));

		verify(ingredientService).deleteById(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
	}

}
