package com.awcsoftware.app;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	static String emailRegex;
	static Pattern pattern;
	static Matcher matcher;
	static {
		emailRegex = "[a-zA-Z0-9]+@{1}[a-z]+\\.{1}[a-z]+";
		pattern = Pattern.compile(emailRegex);
	}
	public static Predicate<String> validateString = (str) -> str == null || str.isEmpty();
	public static Predicate<Integer> validateInt = (i) -> i == null || i < 1;
	public static Predicate<Float> validateDailyHours = (i) -> (!(i < 24.0f && i > 0.0f));
	public static Predicate<Float> validateWeeklyHours = (i) -> (!(i < 168.0f && i > 0.0f));

	public static boolean isEmptyOrNull(Object object) {
		if (object == null) {
			return true;
		}
		return false;
	}

	public static boolean isEmptyOrNull(String string) {
		if (string == null || string.trim().length() == 0) {
			return true;
		}
		return false;
	}

	static Predicate<String> validateEmail = (email) -> {
		return pattern.matcher(email).matches();
	};
}
