package com.juhawilppu.bloodsampleeditor.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.juhawilppu.bloodsampleeditor.backend.entity.PlateSettings;
import com.juhawilppu.bloodsampleeditor.ui.util.GridAdapter;

public class TestGridCoordinateAdapter {

	@Test
	public void testRows() {
		PlateSettings plateSettings = PlateSettings.create96();
		GridAdapter adapter = new GridAdapter(plateSettings);
		
		assertEquals("", adapter.convertRowForPlate(0));
		assertEquals("A", adapter.convertRowForPlate(1));
		assertEquals("B", adapter.convertRowForPlate(2));
		
		assertEquals(1, adapter.convertRowForGrid("A"));
		assertEquals(2, adapter.convertRowForGrid("B"));
	}
	
	@Test
	public void testColumns() {
		PlateSettings plateSettings = PlateSettings.create96();
		GridAdapter adapter = new GridAdapter(plateSettings);
		
		assertEquals(1, adapter.convertColumnForPlate(1));
		assertEquals(2, adapter.convertColumnForPlate(2));
		
		assertEquals(1, adapter.convertColumnForGrid(1));
		assertEquals(2, adapter.convertColumnForGrid(2));
	}
}
