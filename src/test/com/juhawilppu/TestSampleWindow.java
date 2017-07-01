package com.juhawilppu;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.juhawilppu.bloodsampleditor.backend.entity.Sample;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.TextField;

public class TestSampleWindow {

	@Test
	public void TestRowUpperCaseConverter() {
		Sample sample = new Sample();

		TextField row = new TextField("Row");
		Binder<Sample> binder = new Binder<Sample>();
		binder.forField(row)
				.withConverter(String::toUpperCase, String::toUpperCase)
				.bind(Sample::getRow, Sample::setRow);

		row.setValue("a");

		try {
			binder.writeBean(sample);
		} catch (ValidationException e) {
		}

		assertEquals("A", sample.getRow());

	}
}
