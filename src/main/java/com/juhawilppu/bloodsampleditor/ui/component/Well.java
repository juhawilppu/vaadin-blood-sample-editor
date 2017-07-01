package com.juhawilppu.bloodsampleditor.ui.component;

import com.juhawilppu.bloodsampleditor.backend.entity.Sample;
import com.juhawilppu.bloodsampleditor.backend.entity.StringHelper;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class Well extends VerticalLayout {

	private String row;
	private int column;
	private Sample sample;

	VerticalLayout round;
	private Label sampleLabel;
	private Label volumeLabel;

	public Well(String row, int column) {

		this.row = row;
		this.column = column;

		setMargin(false);
		round = new VerticalLayout();
		round.setMargin(false);
		round.addStyleName("well");
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

		round.addStyleName("empty");
	}

	public void setSample(Sample sample) {
		if (sample == null)
			throw new IllegalArgumentException("Sample cannot be null");

		this.sample = sample;
		round.removeStyleName("empty");
		round.addStyleName("non-empty");

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

	public void removeSample() {
		this.sample = null;
		round.removeStyleName("non-empty");
		round.addStyleName("empty");
	}

	public void setSelected() {
		round.addStyleName("selected");
	}

	public void unselect() {
		round.removeStyleName("selected");
	}
}
