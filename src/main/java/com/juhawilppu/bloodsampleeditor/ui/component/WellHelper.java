package com.juhawilppu.bloodsampleeditor.ui.component;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class WellHelper {

	/**
	 * Returns css class corresponding to the volume. Returns color based on the
	 * percentage of maxValue (from PlateSettings), so color is between 40 % and
	 * 100 %.
	 */
	public static String getVolumeClass(BigDecimal volume,
			BigDecimal maxVolume) {

		double percentageVolume = volume
				.divide(maxVolume, 4, RoundingMode.HALF_UP).doubleValue();
		if (percentageVolume > 1)
			percentageVolume = 1;

		// Use 40 % as minimum so that even low volumes would still look of the
		// same color as bigger volumes.
		int volumeInt = (int) (40.0 + 60.0 * percentageVolume);
		return "blood-volume-" + volumeInt;

	}

}