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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

/**
 * SampleWindow is used in modifying existing samples or adding new samples to
 * the editor.
 */
public class SampleWindow extends Window {

	Well well;
	Sample sample;

	SampleFetcher editor;
	PlateSettings plateSettings;

	List<SampleWindowListener> listeners;
	Binder<Sample> binder;

	TextField sampleId;
	TextField row;
	TextField column;
	TextField volume;

	boolean isNew;

	interface SampleWindowListener {
		public void save(Sample sample, Well well);

		public void close(Well well);
	}

	public SampleWindow(Well well, PlateSettings plateSettings,
			SampleFetcher editor) {

		this.well = well;
		this.sample = well.getSample();
		this.plateSettings = plateSettings;
		this.editor = editor;

		isNew = sample == null;
		listeners = new ArrayList<SampleWindowListener>();

		setModal(true);
		addStyleName("disable-maximise");

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

		setWidth(350, Unit.PIXELS);

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

		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.addStyleName("buttons-layout");

		Button saveButton = new Button("Save");
		saveButton.setIcon(VaadinIcons.CHECK);
		saveButton.addClickListener(e -> save());
		buttonsLayout.addComponent(saveButton);

		Button cancelButton = new Button("Cancel");
		cancelButton.setIcon(VaadinIcons.CLOSE);
		cancelButton.addClickListener(e -> close());
		buttonsLayout.addComponent(cancelButton);

		subContent.addComponent(buttonsLayout);

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

		// TODO sampleId is missing validation for uniqueness.
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
				.withConverter(
						new StringToIntegerConverter("Must be a number."))
				.withValidator(new LocationValidator<Integer>(
						plateSettings.getColumns()))
				.bind(Sample::getColumn, Sample::setColumn);

		BigDecimalRangeValidator volumeValidator = new BigDecimalRangeValidator(
				"Must be larger than 0 and less than 100.", BigDecimal.ZERO,
				new BigDecimal(100));
		volumeValidator.setMinValueIncluded(false);
		volumeValidator.setMaxValueIncluded(false);
		binder.forField(volume)
				.withConverter(new StringToBigDecimalConverter(BigDecimal.ZERO,
						"Must be a positive number."))
				.withValidator(volumeValidator)
				.bind(Sample::getVolume, Sample::setVolume);
	}

	private void populate() {
		binder.readBean(sample);
	}

	private void save() {

		try {
			binder.validate();

			// TODO move this validation to the fields
			if (!isAcceptableLocation()) {
				Notification.show(
						"Given location is not acceptable because the location is already occupied by another sample.",
						Type.ERROR_MESSAGE);
				return;
			}

			binder.writeBean(sample);
			for (SampleWindowListener listener : listeners) {
				listener.save(sample, well);
			}
			close();

		} catch (ValidationException e) {
			// The fields will already show the errors, no need to add a
			// notification here
		}
	}

	private boolean isAcceptableLocation() {
		// Use testSample because then we can read column and row values using
		// the converters and we won't override the data in the sample
		Sample testSample = new Sample();
		try {
			binder.writeBean(testSample);
		} catch (Exception e) {
			// This problem will be handled by other validation than this
			return true;
		}

		Sample existingSample = editor.getSample(testSample.getRow(),
				testSample.getColumn());

		return existingSample == null || existingSample == sample;
	}

	@Override
	public void close() {
		super.close();

		for (SampleWindowListener listener : listeners) {
			listener.close(well);
		}
	}

	public void addSampleWindowListener(SampleWindowListener listener) {
		listeners.add(listener);
	}
}
