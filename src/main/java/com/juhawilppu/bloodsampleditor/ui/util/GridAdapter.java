package com.juhawilppu.bloodsampleditor.ui.util;

import com.juhawilppu.bloodsampleditor.backend.entity.PlateSettings;

/**
 * This adapters does the coordinates system mapping between Grid UI component and the plate coordinates.
 * Adapter is needed because the UI component only uses integers but plate rows use letter. Also the first
 * row and column in UI is used for title.
 */
public class GridAdapter {

	PlateSettings plateSettings;
	
	public GridAdapter(PlateSettings plateSettings) {
		this.plateSettings = plateSettings;
	}
	
	public String convertRowForPlate(int row) {

		// Row 0 is special case. It is reserved for title.
		if (row == 0)
			return "";
		else
			return plateSettings.getRows().get(row-1);
	}

	public int convertRowForGrid(String string) {
		return plateSettings.getRows().indexOf(string) + 1;
	}
	
	public int convertColumnForPlate(int column) {
		return column;
	}

	public int convertColumnForGrid(int column) {
		return column;
	}
	
}
