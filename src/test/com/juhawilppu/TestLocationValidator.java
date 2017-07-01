package com.juhawilppu;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.juhawilppu.bloodsampleditor.backend.entity.PlateSettings;
import com.juhawilppu.bloodsampleditor.ui.util.LocationValidator;

public class TestLocationValidator {

	PlateSettings plateSettings = PlateSettings.create96();

	@Test
	public void testRowValidator() {
		LocationValidator<String> validator = new LocationValidator<>(plateSettings.getRows());

		// These are proper rows
		assertEquals(false, validator.apply("A", null).isError());
		assertEquals(false, validator.apply("B", null).isError());

		// These are not
		assertEquals(true, validator.apply(null, null).isError());
		assertEquals(true, validator.apply("2", null).isError());
		assertEquals(true, validator.apply("I", null).isError());
	}

	@Test
	public void testColumnValidator() {
		LocationValidator<Integer> validator = new LocationValidator<>(plateSettings.getColumns());

		// These are proper columns
		assertEquals(false, validator.apply(1, null).isError());
		assertEquals(false, validator.apply(12, null).isError());

		// These are not
		assertEquals(true, validator.apply(null, null).isError());
		assertEquals(true, validator.apply(0, null).isError());
		assertEquals(true, validator.apply(13, null).isError());
	}
}
