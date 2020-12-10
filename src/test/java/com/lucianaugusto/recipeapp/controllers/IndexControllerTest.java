package com.lucianaugusto.recipeapp.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.lucianaugusto.recipeapp.domain.Recipe;
import com.lucianaugusto.recipeapp.services.RecipeService;

import reactor.core.publisher.Flux;

public class IndexControllerTest {

	@Mock
	RecipeService recipeService;

	@Mock
	Model model;

	IndexController controller;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new IndexController(recipeService);
	}

	@Test
	public void testMockMVC() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		when(recipeService.getRecipes()).thenReturn(Flux.empty());

		mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
	}

	@Test
	public void testGetIndexPage() {

		// Given
		Recipe recipe = new Recipe();
		recipe.setId("1");

		when(recipeService.getRecipes()).thenReturn(Flux.just(new Recipe(), recipe));

		ArgumentCaptor<List<Recipe>> argumentCaptor = ArgumentCaptor.forClass(List.class);

		// When
		String viewName = controller.getIndexPage(model);

		// Then
		assertEquals("index", viewName);
		verify(recipeService, times(1)).getRecipes();
		verify(model, times(1)).addAttribute(ArgumentMatchers.eq("recipes"), argumentCaptor.capture());
		List<Recipe> setInController = argumentCaptor.getValue();
		assertEquals(2, setInController.size());
	}

}
