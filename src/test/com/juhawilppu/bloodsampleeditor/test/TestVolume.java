package com.juhawilppu.bloodsampleeditor.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;

import com.juhawilppu.bloodsampleeditor.ui.component.WellHelper;

public class TestVolume {

	@Test
	public void testVolumeCss() {

		final BigDecimal MAX_VOLUME = new BigDecimal(0.360);

		assertEquals("blood-volume-40",
				WellHelper.getVolumeClass(new BigDecimal(0.00), MAX_VOLUME));

		assertEquals("blood-volume-70", WellHelper.getVolumeClass(
				MAX_VOLUME.divide(new BigDecimal(2), RoundingMode.HALF_UP),
				MAX_VOLUME));

		assertEquals("blood-volume-100",
				WellHelper.getVolumeClass(MAX_VOLUME, MAX_VOLUME));

		assertEquals("blood-volume-100", WellHelper.getVolumeClass(
				MAX_VOLUME.multiply(new BigDecimal(2)), MAX_VOLUME));

	}
}
