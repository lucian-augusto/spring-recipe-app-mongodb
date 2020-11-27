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

import com.lucianaugusto.recipeapp.commands.RecipeCommand;
import com.lucianaugusto.recipeapp.exceptions.NotFoundException;
import com.lucianaugusto.recipeapp.services.RecipeService;

public class RecipeControllerTest {

	@Mock
	RecipeService recipeService;

	RecipeController recipeController;

	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		recipeController = new RecipeController(recipeService);

		mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
				.setControllerAdvice(new ControllerExceptionHandler()).build();
	}

	@Test
	public void getRecipeTest() throws Exception {
		String id = "1";
		RecipeCommand command = new RecipeCommand();
		command.setId(id);

		when(recipeService.findCommandById(ArgumentMatchers.anyString())).thenReturn(command);

		mockMvc.perform(get("/recipe/" + id + "/show")).andExpect(status().isOk()).andExpect(view().name("recipe/show"))
				.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void getRecipeNotFoundTest() throws Exception {
		String id = "1";
		RecipeCommand command = new RecipeCommand();
		command.setId(id);

		when(recipeService.findCommandById(ArgumentMatchers.anyString())).thenThrow(NotFoundException.class);

		mockMvc.perform(get("/recipe/" + id + "/show")).andExpect(status().isNotFound())
				.andExpect(view().name("error404"));
	}

	@Test
	public void getNewRecipeFormTest() throws Exception {

		mockMvc.perform(get("/recipe/new")).andExpect(status().isOk()).andExpect(view().name("recipe/recipeform"))
				.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void getUpdateViewTest() throws Exception {
		String id = "1";
		RecipeCommand command = new RecipeCommand();
		command.setId(id);

		when(recipeService.findCommandById(ArgumentMatchers.any())).thenReturn(command);

		mockMvc.perform(get("/recipe/" + id + "/update")).andExpect(status().isOk())
				.andExpect(view().name("recipe/recipeform")).andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void postNewRecipeFormTest() throws Exception {
		String id = "1";
		RecipeCommand command = new RecipeCommand();
		command.setId(id);

		when(recipeService.saveRecipeCommand(ArgumentMatchers.any())).thenReturn(command);

		mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "")
				.param("description", "Some String").param("directions", "directions"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/recipe/" + id + "/show"));
	}

	@Test
	public void postNewRecipeFormValidationFailTest() throws Exception {
		String id = "7";
		RecipeCommand command = new RecipeCommand();
		command.setId(id);

		when(recipeService.saveRecipeCommand(ArgumentMatchers.any(RecipeCommand.class))).thenReturn(command);

		mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", ""))
				.andExpect(status().isOk()).andExpect(view().name("recipe/recipeform"))
				.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void deleteActionTest() throws Exception {
		String id = "7";
		mockMvc.perform(get("/recipe/" + id + "/delete")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));

		verify(recipeService).deleteById(ArgumentMatchers.anyString());
	}

}
