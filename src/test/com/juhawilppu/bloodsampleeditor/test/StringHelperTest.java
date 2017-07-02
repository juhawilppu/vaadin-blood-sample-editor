package com.juhawilppu.bloodsampleeditor.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.juhawilppu.bloodsampleeditor.ui.util.StringHelper;

public class StringHelperTest {

	@Test
	public void testLineBreak() {
		assertEquals("ID<br>100",
				StringHelper.breakLineBeforeFirstNumber("ID100"));
		assertEquals("100", StringHelper.breakLineBeforeFirstNumber("100"));
		assertEquals("ID", StringHelper.breakLineBeforeFirstNumber("ID"));

		assertEquals("IDENTIFIER<br>99",
				StringHelper.breakLineBeforeFirstNumber("IDENTIFIER99"));
	}

}
