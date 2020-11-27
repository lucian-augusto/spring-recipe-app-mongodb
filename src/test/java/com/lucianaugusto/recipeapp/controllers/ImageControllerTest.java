package com.lucianaugusto.recipeapp.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.lucianaugusto.recipeapp.commands.RecipeCommand;
import com.lucianaugusto.recipeapp.services.ImageService;
import com.lucianaugusto.recipeapp.services.RecipeService;

public class ImageControllerTest {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		controller = new ImageController(imageService, recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new ControllerExceptionHandler())
				.build();
	}

	@Test
	public void testShowUploadForm() throws Exception {
		// given
		RecipeCommand command = new RecipeCommand();
		command.setId("1");

		when(recipeService.findCommandById(ArgumentMatchers.anyString())).thenReturn(command);

		// when
		mockMvc.perform(get("/recipe/1/image")).andExpect(status().isOk()).andExpect(model().attributeExists("recipe"));

		verify(recipeService).findCommandById(ArgumentMatchers.anyString());
	}

	@Test
	public void testHandleImagePost() throws Exception {
		MockMultipartFile expectedFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
				"Lucian Augusto".getBytes());

		mockMvc.perform(multipart("/recipe/1/image").file(expectedFile)).andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/recipe/1/show"));

		verify(imageService).saveImageFile(ArgumentMatchers.anyString(), ArgumentMatchers.any());
	}

	@Test
	public void testRenderImageFromDB() throws Exception {
		// given
		RecipeCommand command = new RecipeCommand();
		command.setId("1");

		String s = "fake image text";
		Byte[] byteBoxed = new Byte[s.getBytes().length];

		int i = 0;
		for (byte b : s.getBytes()) {
			byteBoxed[i++] = b;
		}

		command.setImage(byteBoxed);

		when(recipeService.findCommandById(ArgumentMatchers.anyString())).thenReturn(command);

		// when
		MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage")).andExpect(status().isOk())
				.andReturn().getResponse();

		byte[] responseBytes = response.getContentAsByteArray();

		assertEquals(s.getBytes().length, responseBytes.length);
	}

	@Ignore
	@Test
	public void testGetImageNotFound() throws Exception {
		mockMvc.perform(get("/recipe/test/image")).andExpect(status().isBadRequest())
				.andExpect(view().name("errorBadRequest"));
	}

	@Mock
	private ImageService imageService;

	@Mock
	private RecipeService recipeService;

	private ImageController controller;

	private MockMvc mockMvc;
}
