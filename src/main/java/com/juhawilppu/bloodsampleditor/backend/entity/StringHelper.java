package com.juhawilppu.bloodsampleditor.backend.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

	public static String breakLineBeforeFirstNumber(String string) {
		int index = StringHelper.indexOfFirstNumber(string);

		String beginning = string.substring(0, index);
		String end = string.substring(index, string.length());
		return beginning + "<br>" + end;
	}

	private static int indexOfFirstNumber(String string) {
		Matcher matcher = Pattern.compile("\\d+").matcher(string);
		matcher.find();
		return matcher.start(0);
	}
}
