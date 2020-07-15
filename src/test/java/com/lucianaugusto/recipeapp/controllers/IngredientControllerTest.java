package com.lucianaugusto.recipeapp.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.lucianaugusto.recipeapp.commands.RecipeCommand;
import com.lucianaugusto.recipeapp.services.IngredientService;
import com.lucianaugusto.recipeapp.services.RecipeService;

public class IngredientControllerTest {

	@Mock
	RecipeService recipeService;
	
	@Mock
	IngredientService ingredientService;
	
	IngredientController controller;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		controller = new IngredientController(recipeService, ingredientService);
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

}
