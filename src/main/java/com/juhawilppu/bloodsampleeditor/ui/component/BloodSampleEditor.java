package com.juhawilppu.bloodsampleeditor.ui.component;

import com.juhawilppu.bloodsampleeditor.backend.entity.Plate;
import com.juhawilppu.bloodsampleeditor.backend.entity.PlateSettings;
import com.juhawilppu.bloodsampleeditor.backend.entity.Sample;
import com.juhawilppu.bloodsampleeditor.ui.util.GridAdapter;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * BloodSampleEditor contains the grid, or plate, of blood samples.
 *
 */
public class BloodSampleEditor extends VerticalLayout
		implements SampleWindowListener, SampleFetcher {

	private Plate plate;

	private GridLayout grid;
	private PlateSettings plateSettings;
	private GridAdapter adapter;

	private SampleWindow sampleWindow;

	public BloodSampleEditor(Plate plate) {
		this.plate = plate;
		this.plateSettings = plate.getPlateSettings();
		this.adapter = new GridAdapter(plateSettings);

		drawEmptyPlate();
		populate();
	}

	private void drawEmptyPlate() {

		// GridLayout is the most light weight component for showing grid data.
		// Use +1 to add space for title rows.
		grid = new GridLayout(plateSettings.getNumberOfColumns() + 1,
				plateSettings.getNumberOfRows() + 1);
		grid.setMargin(false);
		grid.addStyleName("editor-grid");

		for (int column = 0; column <= plateSettings
				.getNumberOfColumns(); column++) {
			for (int row = 0; row <= plateSettings.getNumberOfRows(); row++) {

				if (isTopLeft(row, column)) {
					// Top left corner is empty.
					continue;
				} else if (isTopTitleRow(row, column)) {
					// Top title row contains the column numbers
					grid.addComponent(createLabel(column + ""), column, row);
				} else if (isLeftTitleColumn(row, column)) {
					// Left title column contains the row letters
					grid.addComponent(
							createLabel(adapter.convertRowForPlate(row)),
							column, row);
				} else {
					// Blood sample well
					Well well = new Well(adapter.convertRowForPlate(row),
							column, plateSettings);
					setSize(well);
					grid.addComponent(well, column, row);
					well.addLayoutClickListener(event -> {
						openDialog(well);
					});
				}
			}
		}

		addComponent(grid);
		setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
	}

	private boolean isTopLeft(int row, int column) {
		return row == 0 && column == 0;
	}

	private boolean isLeftTitleColumn(int row, int column) {
		return row > 0 && column == 0;
	}

	private boolean isTopTitleRow(int row, int column) {
		return row == 0 && column > 0;
	}

	private void populate() {
		for (Sample sample : plate.getSamples()) {
			Well well = getWell(sample.getRow(), sample.getColumn());
			well.setSample(sample);
		}
	}

	private Label createLabel(String number) {
		Label label = new Label(number);
		label.addStyleName("coordinate-titles");
		setSize(label);
		return label;
	}

	private void setSize(Component component) {
		component.setWidth(80, Unit.PIXELS);
		component.setHeight(80, Unit.PIXELS);
	}

	private void openDialog(Well well) {
		if (sampleWindow != null) {
			Notification.show("You can only open one edit window at a time",
					Type.ERROR_MESSAGE);
			return;
		}

		well.setSelected();
		sampleWindow = new SampleWindow(well, plateSettings, this);
		sampleWindow.addSampleWindowListener(this);
		UI.getCurrent().addWindow(sampleWindow);
	}

	public Well getWell(String row, int column) {
		return (Well) grid.getComponent(adapter.convertColumnForGrid(column),
				adapter.convertRowForGrid(row));
	}

	@Override
	public Sample getSample(String row, int column) {
		return getWell(row, column).getSample();
	}

	@Override
	public void moveSample(Sample sample, Well oldWell) {
		oldWell.setWellEmpty();

		Well newWell = getWell(sample.getRow(), sample.getColumn());
		newWell.setSample(sample);
	}

	@Override
	public void addSample(Sample sample, Well well) {
		Well newWell = getWell(sample.getRow(), sample.getColumn());
		newWell.setSample(sample);

		plate.getSamples().add(sample);
	}

	@Override
	public void close(Well well) {
		well.unselect();
		sampleWindow = null;
	}
}
