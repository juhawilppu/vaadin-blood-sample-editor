package com.juhawilppu.bloodsampleditor.ui.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

	public static String breakLineBeforeFirstNumber(String string) {
		if (string == null)
			throw new IllegalArgumentException();

		int index = StringHelper.indexOfFirstNumber(string);

		if (index == 0)
			return string;

		String beginning = string.substring(0, index);
		String end = string.substring(index, string.length());
		return beginning + "<br>" + end;
	}

	private static int indexOfFirstNumber(String string) {
		Matcher matcher = Pattern.compile("\\d+").matcher(string);
		matcher.find();

		try {
			return matcher.start(0);
		} catch (Exception e) {
			return 0;
		}
	}

	public static String formatNumber(BigDecimal number) {
		if (number == null)
			throw new IllegalArgumentException();

		// TODO Finnish number formatting. Needs localization.
		return number.setScale(2, RoundingMode.HALF_UP).toString().replace(".",
				",");
	}
}
