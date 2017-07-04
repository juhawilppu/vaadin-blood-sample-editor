package com.juhawilppu.bloodsampleeditor.backend.entity;

import java.util.List;

public class Plate {

	private String plateId;
	private List<Sample> samples;
	private PlateSettings plateSettings;

	public Plate(String plateId, List<Sample> samples,
			PlateSettings plateSettings) {
		this.plateId = plateId;
		this.samples = samples;
		this.plateSettings = plateSettings;
	}

	public Plate() {
		// JPA constructor
	}

	public String getPlateId() {
		return plateId;
	}

	public void setPlateId(String plateId) {
		this.plateId = plateId;
	}

	public List<Sample> getSamples() {
		return samples;
	}

	public void setSamples(List<Sample> samples) {
		this.samples = samples;
	}

	public PlateSettings getPlateSettings() {
		return plateSettings;
	}

	public void setPlateSettings(PlateSettings plateSettings) {
		this.plateSettings = plateSettings;
	}

}
