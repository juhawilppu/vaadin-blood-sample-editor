package com.juhawilppu.bloodsampleditor.ui.util;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;

public class ColumnValidator implements Validator<Integer> {

	@Override
	public ValidationResult apply(Integer value, ValueContext context) {
			if (value >= 1 && value <= 12)
			return ValidationResult.ok();
		else
			return ValidationResult.error("Row has to be between 1 and 12");
	}

}
