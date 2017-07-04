package com.juhawilppu.bloodsampleeditor.ui.util;

import java.util.List;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;

/**
 * Validates that the field values must be contained in the predefined list.
 * LocationValidator is used in validation rows and columns.
 */
public class LocationValidator<T> implements Validator<T> {

	private List<T> allowedValues;

	public LocationValidator(List<T> allowedValues) {
		this.allowedValues = allowedValues;
	}

	@Override
	public ValidationResult apply(T value, ValueContext context) {
		if (allowedValues.contains(value))
			return ValidationResult.ok();
		else {
			String errorMessage = "Value should be between {min} and {max}";
			errorMessage = errorMessage.replace("{min}",
					allowedValues.get(0).toString());
			errorMessage = errorMessage.replace("{max}",
					allowedValues.get(allowedValues.size() - 1).toString());
			return ValidationResult.error(errorMessage);
		}
	}

}
