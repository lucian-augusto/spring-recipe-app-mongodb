package com.lucianaugusto.recipeapp.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.lucianaugusto.recipeapp.domain.Category;
import com.lucianaugusto.recipeapp.domain.UnitOfMeasure;
import com.lucianaugusto.recipeapp.repositories.CategoryRepository;
import com.lucianaugusto.recipeapp.repositories.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile({ "dev", "prod" })
public class BootstrapMySQL implements ApplicationListener<ContextRefreshedEvent> {

	private final CategoryRepository categoryRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

	public BootstrapMySQL(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (categoryRepository.count() == 0L) {
			log.debug("Loading Categories");
			loadCategories();
		}

		if (unitOfMeasureRepository.count() == 0L) {
			log.debug("Loading UOMs");
			loadUoms();
		}
	}

	private void loadCategories() {
		Category category1 = new Category();
		category1.setDescription("Canadian");
		categoryRepository.save(category1);

		Category category2 = new Category();
		category2.setDescription("American");
		categoryRepository.save(category2);

		Category category3 = new Category();
		category3.setDescription("Italian");
		categoryRepository.save(category3);

		Category category4 = new Category();
		category4.setDescription("Mexican");
		categoryRepository.save(category4);

		Category category5 = new Category();
		category5.setDescription("Brazilian");
		categoryRepository.save(category5);
	}

	private void loadUoms() {
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setDescription("Teaspoon");
		unitOfMeasureRepository.save(uom1);

		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setDescription("Tablespoon");
		unitOfMeasureRepository.save(uom2);

		UnitOfMeasure uom3 = new UnitOfMeasure();
		uom3.setDescription("Cup");
		unitOfMeasureRepository.save(uom3);

		UnitOfMeasure uom4 = new UnitOfMeasure();
		uom4.setDescription("Pinch");
		unitOfMeasureRepository.save(uom4);

		UnitOfMeasure uom5 = new UnitOfMeasure();
		uom5.setDescription("Ounce");
		unitOfMeasureRepository.save(uom5);

		UnitOfMeasure uom6 = new UnitOfMeasure();
		uom6.setDescription("Each");
		unitOfMeasureRepository.save(uom6);

		UnitOfMeasure uom7 = new UnitOfMeasure();
		uom7.setDescription("Pint");
		unitOfMeasureRepository.save(uom7);

		UnitOfMeasure uom8 = new UnitOfMeasure();
		uom8.setDescription("Dash");
		unitOfMeasureRepository.save(uom8);
	}
}
