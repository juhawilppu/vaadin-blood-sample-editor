package com.juhawilppu.bloodsampleditor.ui.component;

import com.juhawilppu.bloodsampleditor.backend.entity.PlateSettings;
import com.juhawilppu.bloodsampleditor.backend.entity.Sample;
import com.juhawilppu.bloodsampleditor.ui.util.StringHelper;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Well is the UI presentation of a well. It might contain a sample or it might
 * be empty.
 */
public class Well extends VerticalLayout {

	private String row;
	private int column;
	private PlateSettings plateSettings;

	/** Sample inside this well. Sample is null if well is empty. **/
	private Sample sample;

	VerticalLayout round;
	private Label sampleLabel;
	private Label volumeLabel;

	public Well(String row, int column, PlateSettings plateSettings) {

		this.row = row;
		this.column = column;
		this.plateSettings = plateSettings;

		setMargin(false);
		round = new VerticalLayout();
		round.setMargin(false);
		round.setHeight(90, Unit.PERCENTAGE);
		round.setWidth(90, Unit.PERCENTAGE);
		addComponent(round);

		sampleLabel = new Label("");
		sampleLabel.addStyleName("info-text");
		sampleLabel.setContentMode(ContentMode.HTML);
		round.addComponent(sampleLabel);
		round.setComponentAlignment(sampleLabel, Alignment.MIDDLE_CENTER);

		volumeLabel = new Label("");
		volumeLabel.setContentMode(ContentMode.HTML);
		volumeLabel.addStyleName("info-text");
		round.addComponent(volumeLabel);
		round.setComponentAlignment(volumeLabel, Alignment.MIDDLE_CENTER);

		setWellEmpty();
	}

	public void setSample(Sample sample) {
		if (sample == null)
			throw new IllegalArgumentException("Sample cannot be null");

		this.sample = sample;
		round.setStyleName("well non-empty");

		round.addStyleName(WellHelper.getVolumeClass(sample.getVolume(),
				plateSettings.getMaxVolume()));

		sampleLabel.setValue(
				StringHelper.breakLineBeforeFirstNumber(sample.getSampleId()));
		volumeLabel.setValue(
				StringHelper.formatNumber(sample.getVolume()) + " ml");
	}

	public Sample getSample() {
		return sample;
	}

	public String getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public void setWellEmpty() {
		this.sample = null;
		round.setStyleName("well empty");
		sampleLabel.setValue("");
		volumeLabel.setValue("");
	}

	public void setSelected() {
		round.addStyleName("selected");
	}

	public void unselect() {
		round.removeStyleName("selected");
	}
}
