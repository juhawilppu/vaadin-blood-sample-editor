package com.juhawilppu.bloodsampleditor.backend.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.juhawilppu.bloodsampleditor.backend.entity.Plate;
import com.juhawilppu.bloodsampleditor.backend.entity.PlateSettings;
import com.juhawilppu.bloodsampleditor.backend.entity.Sample;

/**
 * This is not mapped to database because it's just an example.
 */
public class PlateService {

	public Plate getPlate(String plateId) {
		List<Sample> samples = createSamples(plateId);		
		PlateSettings plateSettings = PlateSettings.create96();
		Plate plate = new Plate(plateId, samples, plateSettings);
		return plate;
	}

	public List<Sample> createSamples(String plateId) {
		List<Sample> samples = new ArrayList<Sample>();
		samples.add(
				new Sample("ID00001", plateId, "A", 1, new BigDecimal(0.15)));
		samples.add(
				new Sample("ID00002", plateId, "B", 10, new BigDecimal(0.85)));
		samples.add(
				new Sample("ID00003", plateId, "H", 4, new BigDecimal(0.01)));
		samples.add(
				new Sample("ID00004", plateId, "D", 6, new BigDecimal(0.50)));
		samples.add(
				new Sample("ID00005", plateId, "E", 4, new BigDecimal(0.50)));
		samples.add(
				new Sample("ID00006", plateId, "A", 3, new BigDecimal(1.50)));
		samples.add(
				new Sample("ID00007", plateId, "A", 2, new BigDecimal(2.50)));
		samples.add(
				new Sample("ID00008", plateId, "B", 7, new BigDecimal(3.50)));
		samples.add(
				new Sample("ID00009", plateId, "C", 1, new BigDecimal(0.80)));
		samples.add(
				new Sample("ID00010", plateId, "F", 12, new BigDecimal(0.96)));
		samples.add(
				new Sample("ID00011", plateId, "G", 9, new BigDecimal(0.50)));
		return samples;
	}

}
