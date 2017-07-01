package com.juhawilppu.bloodsampleditor.ui.component;

import com.juhawilppu.bloodsampleditor.backend.entity.Plate;
import com.juhawilppu.bloodsampleditor.backend.entity.PlateSettings;
import com.juhawilppu.bloodsampleditor.backend.entity.Sample;
import com.juhawilppu.bloodsampleditor.ui.component.SampleWindow.SampleWindowListener;
import com.juhawilppu.bloodsampleditor.ui.util.GridAdapter;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class BloodSampleEditor extends VerticalLayout implements SampleWindowListener {

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

		for (int column = 0; column <= plateSettings
				.getNumberOfColumns(); column++) {
			for (int row = 0; row <= plateSettings.getNumberOfRows(); row++) {

				if (column == 0 && row == 0)
					continue;
				if (row == 0 && column > 0)
					grid.addComponent(createLabel(column + ""), column, row);
				else if (column == 0 && row > 0)
					grid.addComponent(
							createLabel(adapter.convertRowForPlate(row)),
							column, row);
				else {
					Well well = new Well(adapter.convertRowForPlate(row),
							column);
					setSize(well);
					grid.addComponent(well, column, row);
					well.addLayoutClickListener(event -> {
						openDialog(well);
					});
				}
			}
		}

		addComponent(grid);
	}

	private void populate() {
		for (Sample sample : plate.getSamples()) {
			Well well = getWell(sample.getRow(), sample.getColumn());
			well.setSample(sample);
		}
	}

	private Label createLabel(String number) {
		Label label = new Label(number);
		label.addStyleName("number");
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

	public Sample getSample(String row, int column) {
		return getWell(row, column).getSample();
	}

	@Override
	public void save(Sample sample, Well oldWell) {
		oldWell.removeSample();

		Well newWell = getWell(sample.getRow(), sample.getColumn());
		newWell.setSample(sample);
	}

	public Well getWell(String row, int column) {
		return (Well) grid.getComponent(adapter.convertColumnForGrid(column),
				adapter.convertRowForGrid(row));
	}

	@Override
	public void close(Well well) {
		well.unselect();
		sampleWindow = null;
	}
}
