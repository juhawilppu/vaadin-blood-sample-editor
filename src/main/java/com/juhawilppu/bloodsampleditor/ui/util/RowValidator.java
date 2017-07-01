package com.juhawilppu.bloodsampleditor.ui.util;

import java.util.Arrays;
import java.util.List;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;

public class RowValidator implements Validator<String> {

	List<String> rows = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H");

	@Override
	public ValidationResult apply(String value, ValueContext context) {
		if (rows.contains(value))
			return ValidationResult.ok();
		else
			return ValidationResult.error("Not a valid column");
	}

}
