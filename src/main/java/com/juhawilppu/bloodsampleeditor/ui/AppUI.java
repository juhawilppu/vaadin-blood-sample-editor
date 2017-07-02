package com.juhawilppu.bloodsampleeditor.ui;

import com.juhawilppu.bloodsampleeditor.backend.entity.Plate;
import com.juhawilppu.bloodsampleeditor.backend.service.PlateService;
import com.juhawilppu.bloodsampleeditor.ui.component.BloodSampleEditor;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Theme("blood_sample_editor")
@Title("Juha Wilppu - Blood Sample Editor")
public class AppUI extends UI {

	PlateService plateService;

	@Override
	protected void init(VaadinRequest request) {
		plateService = new PlateService();
		Plate plate = plateService.getPlate("plate12345");

		BloodSampleEditor bloodSampleEditor = new BloodSampleEditor(plate);
		setContent(bloodSampleEditor);
	}
}
