package com.juhawilppu.bloodsampleditor.ui.component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.juhawilppu.bloodsampleditor.backend.entity.PlateSettings;
import com.juhawilppu.bloodsampleditor.backend.entity.Sample;
import com.juhawilppu.bloodsampleditor.ui.util.LocationValidator;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.BigDecimalRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class SampleWindow extends Window {

	Well well;
	Sample sample;

	BloodSampleEditor wells96;
	PlateSettings plateSettings;

	List<SaveListener> saveListeners;
	Binder<Sample> binder;

	TextField sampleId;
	TextField row;
	TextField column;
	TextField volume;

	boolean isNew;

	interface SaveListener {
		public void save(Sample sample, Well well);
	}

	public SampleWindow(Well well, PlateSettings plateSettings,
			BloodSampleEditor wells96) {

		this.well = well;
		this.sample = well.getSample();
		this.plateSettings = plateSettings;
		this.wells96 = wells96;

		isNew = sample == null;

		saveListeners = new ArrayList<SaveListener>();

		if (isNew) {
			// TODO This will cause volume to be zero at the beginning. It
			// should be null instead, but it throws an exception. The converter
			// or the validator must be changed.
			sample = new Sample("", "", well.getRow(), well.getColumn(),
					BigDecimal.ZERO);
			setCaption("Add sample");
		} else {
			setCaption("Edit sample");
		}

		setWidth(300, Unit.PIXELS);

		FormLayout subContent = new FormLayout();
		subContent.setMargin(true);
		setContent(subContent);

		sampleId = new TextField("Sample Id");
		subContent.addComponent(sampleId);

		row = new TextField("Row");
		subContent.addComponent(row);

		column = new TextField("Column");
		subContent.addComponent(column);

		volume = new TextField("Volume (ml)");
		subContent.addComponent(volume);

		Button saveButton = new Button("Save");
		saveButton.setIcon(VaadinIcons.CHECK);
		saveButton.addClickListener(e -> save());
		subContent.addComponent(saveButton);

		Button cancelButton = new Button("Cancel");
		cancelButton.setIcon(VaadinIcons.CLOSE);
		cancelButton.addClickListener(e -> close());
		subContent.addComponent(cancelButton);

		center();
		addBindings();

		if (isNew) {
			row.setValue(well.getRow());
			column.setValue(well.getColumn() + "");
		} else {
			populate();
		}
	}

	private void addBindings() {
		binder = new Binder<>();

		binder.forField(sampleId)
				.withValidator(new StringLengthValidator(
						"Sample Id must be between 1 and 20 characters long.",
						1, 20))
				.bind(Sample::getSampleId, Sample::setSampleId);

		binder.forField(row)
				.withConverter(String::toUpperCase, String::toUpperCase)
				.withValidator(
						new LocationValidator<String>(plateSettings.getRows()))
				.bind(Sample::getRow, Sample::setRow);

		binder.forField(column)
				.withConverter(new StringToIntegerConverter("Must be a number"))
				.withValidator(new LocationValidator<Integer>(
						plateSettings.getColumns()))
				.bind(Sample::getColumn, Sample::setColumn);

		BigDecimalRangeValidator volumeValidator = new BigDecimalRangeValidator(
				"Must be a positive number", BigDecimal.ZERO, null);
		volumeValidator.setMinValueIncluded(false);
		binder.forField(volume)
				.withConverter(new StringToBigDecimalConverter(BigDecimal.ZERO,
						"Must be a positive number"))
				.withValidator(volumeValidator)
				.bind(Sample::getVolume, Sample::setVolume);
	}

	private void populate() {
		binder.readBean(sample);
	}

	private void save() {

		try {

			binder.validate();

			// Use testSample because then we can read values using the
			// converters
			Sample testSample = new Sample();
			binder.writeBean(testSample);

			Sample existingSample = wells96.getSample(testSample.getRow(),
					testSample.getColumn());

			boolean isAcceptableLocation = existingSample == null
					|| existingSample == sample;
			if (!isAcceptableLocation) {
				Notification.show(
						"New location is not acceptable because the location is already taken by another sample.",
						Type.ERROR_MESSAGE);
				return;
			}

			binder.writeBean(sample);

			for (SaveListener listener : saveListeners) {
				listener.save(sample, well);
			}

			close();

		} catch (ValidationException e) {
			// The fields will already show the errors, no need to add a
			// notification here
		}
	}

	@Override
	public void close() {
		super.close();
		wells96.closeWindow(well);
	}

	public void addSaveListener(SaveListener listener) {
		saveListeners.add(listener);
	}
}
