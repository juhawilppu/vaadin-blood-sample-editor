package com.juhawilppu.bloodsampleeditor.ui.component;

import com.juhawilppu.bloodsampleeditor.backend.entity.Sample;

public interface SampleWindowListener {
	public void addSample(Sample sample, Well well);

	public void moveSample(Sample sample, Well well);

	public void close(Well well);
}