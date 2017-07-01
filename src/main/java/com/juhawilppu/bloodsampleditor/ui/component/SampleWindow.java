package com.juhawilppu.bloodsampleditor.ui.component;

import java.util.ArrayList;
import java.util.List;

import com.juhawilppu.bloodsampleditor.backend.entity.PlateSettings;
import com.juhawilppu.bloodsampleditor.backend.entity.Sample;
import com.juhawilppu.bloodsampleditor.ui.util.LocationValidator;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToIntegerConverter;
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

	List<SaveListener> saveListeners;
	Binder<Sample> binder;

	TextField sampleId;
	TextField row;
	TextField column;
	TextField volume;

	interface SaveListener {
		public void save(Sample sample, Well well);
	}

	public SampleWindow(Well well, PlateSettings plateSettings,
			BloodSampleEditor wells96) {
		this.well = well;
		this.sample = well.getSample();
		if (sample == null) {
			sample = new Sample();
		}
		this.wells96 = wells96;

		binder = new Binder<>();

		saveListeners = new ArrayList<SaveListener>();

		setCaption("Edit sample");
		setWidth(300, Unit.PIXELS);

		FormLayout subContent = new FormLayout();
		subContent.setMargin(true);
		setContent(subContent);

		sampleId = new TextField("Sample Id");
		binder.forField(sampleId)
				.withValidator(new StringLengthValidator(
						"Sample Id must be between 1 and 20 characters long.",
						1, 20))
				.bind(Sample::getSampleId, Sample::setSampleId);
		subContent.addComponent(sampleId);

		row = new TextField("Row");
		binder.forField(row)
				.withConverter(String::toUpperCase, String::toUpperCase)
				.withValidator(
						new LocationValidator<String>(plateSettings.getRows()))
				.bind(Sample::getRow, Sample::setRow);
		subContent.addComponent(row);

		column = new TextField("Column");
		binder.forField(column)
				.withConverter(new StringToIntegerConverter("Must be a number"))
				.withValidator(new LocationValidator<Integer>(
						plateSettings.getColumns()))
				.bind(Sample::getColumn, Sample::setColumn);
		subContent.addComponent(column);

		volume = new TextField("Volume");
		binder.bind(volume, Sample::getVolumeString, Sample::setVolumeString);
		subContent.addComponent(volume);

		Button saveButton = new Button("Save");
		saveButton.setIcon(VaadinIcons.DISC);
		saveButton.addClickListener(e -> save());
		subContent.addComponent(saveButton);

		binder.readBean(sample);

		center();
	}

	private void save() {

		try {

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
			Notification.show("Error in saving");
		}
	}

	public void addSaveListener(SaveListener listener) {
		saveListeners.add(listener);
	}
}
