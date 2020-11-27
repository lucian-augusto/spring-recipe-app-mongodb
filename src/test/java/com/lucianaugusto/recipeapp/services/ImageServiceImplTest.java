package com.lucianaugusto.recipeapp.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.lucianaugusto.recipeapp.domain.Recipe;
import com.lucianaugusto.recipeapp.repositories.RecipeRepository;

public class ImageServiceImplTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new ImageServiceImpl(repository);
	}

	@Test
	public void testSaveImageFile() throws Exception {
		// given
		String id = "7";
		MultipartFile file = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
				"Lucian Augusto".getBytes());

		Recipe recipe = new Recipe();
		recipe.setId(id);
		Optional<Recipe> recipeOptional = Optional.of(recipe);

		when(repository.findById(ArgumentMatchers.anyString())).thenReturn(recipeOptional);

		ArgumentCaptor<Recipe> captor = ArgumentCaptor.forClass(Recipe.class);

		// when
		service.saveImageFile(id, file);

		// then
		verify(repository).save(captor.capture());
		assertEquals(file.getBytes().length, captor.getValue().getImage().length);
	}

	@Mock
	private RecipeRepository repository;

	private ImageService service;
}
