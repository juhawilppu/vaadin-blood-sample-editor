package com.juhawilppu.bloodsampleditor.ui.util;

import java.util.List;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;

public class LocationValidator<T> implements Validator<T> {

	List<T> allowedValues;

	public LocationValidator(List<T> allowedValues) {
		this.allowedValues = allowedValues;
	}

	@Override
	public ValidationResult apply(T value, ValueContext context) {
		if (allowedValues.contains(value))
			return ValidationResult.ok();
		else {
			String errorMessage = "Value should be between {min} and {max}";
			errorMessage = errorMessage.replace("{min}", allowedValues.get(0).toString());
			errorMessage = errorMessage.replace("{max}", allowedValues.get(allowedValues.size() - 1).toString());
			return ValidationResult.error(errorMessage);
		}
	}

}
