package com.juhawilppu.bloodsampleeditor.ui.component;

import com.juhawilppu.bloodsampleeditor.backend.entity.Sample;

public interface SampleFetcher {
	public Sample getSample(String row, int column);
}