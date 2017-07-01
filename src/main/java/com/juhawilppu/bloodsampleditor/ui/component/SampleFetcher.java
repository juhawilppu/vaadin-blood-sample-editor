package com.juhawilppu.bloodsampleditor.ui.component;

import com.juhawilppu.bloodsampleditor.backend.entity.Sample;

public interface SampleFetcher {
	public Sample getSample(String row, int column);
}