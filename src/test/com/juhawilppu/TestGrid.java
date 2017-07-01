package com.juhawilppu;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.juhawilppu.bloodsampleditor.backend.entity.Plate;
import com.juhawilppu.bloodsampleditor.backend.entity.PlateSettings;
import com.juhawilppu.bloodsampleditor.backend.entity.Sample;
import com.juhawilppu.bloodsampleditor.ui.component.BloodSampleEditor;

public class TestGrid {
	
	@Test
	public void testGridPopulatingAndFinding() {

		Plate plate = new Plate();		
		List<Sample> samples = new ArrayList<Sample>();

		Sample sampleA1 = new Sample("sampleId 1", "plateId", "A", 1, BigDecimal.ZERO);
		Sample sampleF8 = new Sample("sampleId 2", "plateId", "F", 8, BigDecimal.ZERO);
		
		samples.add(sampleA1);
		samples.add(sampleF8);
		
		plate.setSamples(samples);
		plate.setPlateSettings(PlateSettings.create96());
		
		BloodSampleEditor editor = new BloodSampleEditor(plate);

		// These coordinates should be populated and contain a sample
		assertEquals(sampleA1, editor.getWell("A", 1).getSample());
		assertEquals(sampleF8, editor.getWell("F", 8).getSample());
		
		// No sample in these coordinates
		assertEquals(null, editor.getWell("A", 2).getSample());
	}
}
