package com.juhawilppu.bloodsampleditor.backend.entity;

import java.util.Arrays;
import java.util.List;

public class PlateSettings {

	List<String> rows;	
	List<Integer> columns;
	
	public PlateSettings(List<String> rows, List<Integer> columns) {
		this.rows = rows;
		this.columns = columns;
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
		List<String> rows = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H");
		List<Integer> columns = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		return new PlateSettings(rows, columns);
	}
}
