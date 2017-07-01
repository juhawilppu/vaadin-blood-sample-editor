package com.juhawilppu.bloodsampleditor.backend.entity;

import java.math.BigDecimal;

public class Sample {

	String sampleId;
	String plateId;
	String row;
	int column;

	/** In milliliters. */
	BigDecimal volume;

	public Sample(String sampleId, String plateId, String row, int column,
			BigDecimal volume) {
		this.sampleId = sampleId;
		this.plateId = plateId;
		this.row = row;
		this.column = column;
		this.volume = volume;
	}

	public Sample() {

	}

	// Boilerplate below

	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}

	public String getPlateId() {
		return plateId;
	}

	public void setPlateId(String plateId) {
		this.plateId = plateId;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	@Override
	public String toString() {
		return getPlateId() + " " + getSampleId() + " " + getRow()
				+ getColumn();
	}
}
