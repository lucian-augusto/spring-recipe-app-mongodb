package com.lucianaugusto.recipeapp.repositories.reactive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lucianaugusto.recipeapp.domain.UnitOfMeasure;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {

	static final String UOM = "Each";

	@Autowired
	UnitOfMeasureReactiveRepository repository;

	@Before
	public void setUp() {
		repository.deleteAll().block();
	}

	@Test
	public void save() {
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setDescription(UOM);

		repository.save(uom).block();

		assertEquals(Long.valueOf(1L), repository.count().block());
	}

	@Test
	public void findByDescription() {
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setDescription(UOM);

		repository.save(uom).block();

		UnitOfMeasure foundUom = repository.findByDescription(UOM).block();

		assertNotNull(foundUom.getId());
	}
}
