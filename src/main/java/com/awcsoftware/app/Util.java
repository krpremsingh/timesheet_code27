package com.awcsoftware.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

	public static Predicate<String> validateEmail = (email) -> {
		return pattern.matcher(email).matches();
	};

	public static boolean chkDateRange(String dateToCheck, String startDate, String endDate) {
		boolean res = false;
		SimpleDateFormat fmt1 = new SimpleDateFormat("yyyy-MM-dd"); 
		SimpleDateFormat fmt2 = new SimpleDateFormat("yyyy-MM-dd"); 
		try {
			Date requestDate = fmt2.parse(dateToCheck);
			Date fromDate = fmt1.parse(startDate);
			Date toDate = fmt1.parse(endDate);
			res = requestDate.compareTo(fromDate) >= 0 && requestDate.compareTo(toDate) <= 0;
		} catch (ParseException pex) {
			pex.printStackTrace();
		}
		return res;
	}

    public static boolean isHourInInterval(String target, String start, String end) {
        return ((target.compareTo(start) >= 0)
                && (target.compareTo(end) <= 0));
    }	

}
