package com.juhawilppu.bloodsampleditor.backend.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public class PlateSettings {

	List<String> rows;
	List<Integer> columns;
	BigDecimal maxVolume;

	public PlateSettings(List<String> rows, List<Integer> columns,
			BigDecimal maxVolume) {
		this.rows = rows;
		this.columns = columns;
		this.maxVolume = maxVolume.setScale(2, RoundingMode.HALF_UP);
	}

	public List<String> getRows() {
		return rows;
	}

	public List<Integer> getColumns() {
		return columns;
	}

	public int getNumberOfColumns() {
		return getColumns().size();
	}

	public int getNumberOfRows() {
		return getRows().size();
	}

	public static PlateSettings create96() {
		List<String> rows = Arrays.asList("A", "B", "C", "D", "E", "F", "G",
				"H");
		List<Integer> columns = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
				12);
		BigDecimal maxVolume = new BigDecimal(0.36);
		return new PlateSettings(rows, columns, maxVolume);
	}

	public BigDecimal getMaxVolume() {
		return maxVolume;
	}

	public String getMaxVolumeString() {
		return StringHelper.formatNumber(getMaxVolume());
	}
}
