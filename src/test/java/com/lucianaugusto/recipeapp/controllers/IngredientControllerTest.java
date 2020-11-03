package com.lucianaugusto.recipeapp.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;

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
import com.lucianaugusto.recipeapp.services.IngredientService;
import com.lucianaugusto.recipeapp.services.RecipeService;
import com.lucianaugusto.recipeapp.services.UnitOfMeasureService;

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
		Long id = 1L;
		RecipeCommand command = new RecipeCommand();
		
		when(recipeService.findCommandById(ArgumentMatchers.anyLong())).thenReturn(command);
		
		mockMvc.perform(get("/recipe/" + id + "/ingredients"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/list"))
			.andExpect(model().attributeExists("recipe"));
		
		verify(recipeService).findCommandById(id);
	}
	
	@Test
	public void testNewIngredientForm() throws Exception {
		Long id = 1L;
		RecipeCommand command = new RecipeCommand();
		command.setId(id);
		
		when(recipeService.findCommandById(ArgumentMatchers.anyLong())).thenReturn(command);
		when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());
		
		mockMvc.perform(get("/recipe/" + id + "/ingredient/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/ingredientform"))
			.andExpect(model().attributeExists("ingredient"))
			.andExpect(model().attributeExists("uomList"));
		
		verify(recipeService).findCommandById(ArgumentMatchers.anyLong());
	}
	
	@Test
	public void testUpdateIngredientForm() throws Exception {
		Long recipeId = 1L;
		Long ingredientId = 2L;
		IngredientCommand ingredientCommand = new IngredientCommand();
		
		when(ingredientService.findByRecipeIdAndIngredientId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(ingredientCommand);
		when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());
		
		mockMvc.perform(get("/recipe/"+ recipeId + "/ingredient/" + ingredientId + "/update"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/ingredientform"))
			.andExpect(model().attributeExists("ingredient"))
			.andExpect(model().attributeExists("uomList"));
	}
	
	@Test
	public void testSaveOrUpdate() throws Exception {
		Long recipeId = 1L;
		Long ingredientId = 2L;
		IngredientCommand command = new IngredientCommand();
		command.setId(ingredientId);
		command.setRecipeId(recipeId);
		
		when(ingredientService.saveIngredientCommand(ArgumentMatchers.any())).thenReturn(command);
		
		mockMvc.perform(post("/recipe/"+ recipeId + "/ingredient")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("description", "TEST DESCRIPTION"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/" + recipeId + "/ingredient/" + ingredientId + "/show"));
	}
	
	@Test
	public void testDeleteIngredient() throws Exception {
		mockMvc.perform(get("/recipe/2/ingredient/3/delete"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/recipe/2/ingredients"));
		
		verify(ingredientService).deleteById(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
	}

}
