package com.awcsoftware.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.awcsoftware.spring.security.auth.UserAuthenticationDetail;

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

	public static boolean validateDateRange(String dateToCheck, String startDate, String endDate) {
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
		return ((target.compareTo(start) >= 0) && (target.compareTo(end) <= 0));
	}

	public static String TimeAdd(String strStartTime, String strEndTime)  {
		String date3 = "";
		try
		{
			String time1 = strStartTime;
			String time2 = strEndTime;
	
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	
			Date date1 = timeFormat.parse(time1);
			Date date2 = timeFormat.parse(time2);
	
			long sum = date1.getTime() + date2.getTime();
	
			date3 = timeFormat.format(new Date(sum)).toString();
		}
		catch(ParseException ex)
		{
			return new AppException(ex).toString();
		}
		return date3;
	}

	public static String TimeDiff(String strStartTime, String strEndTime) {
		String date4 ="";
		try
		{
			String time1 = strStartTime;
			String time2 = strEndTime;
	
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	
			Date date1 = timeFormat.parse(time1);
			Date date2 = timeFormat.parse(time2);
	
			long diff = date2.getTime() - date1.getTime();
	
			date4 = timeFormat.format(new Date(diff));
		}
		catch(ParseException ex)
		{
			return new AppException(ex).toString();
		}
		return date4;
	}

	/*
	 * public static String findTimeDifference(String startTime,String endTime) { //
	 * change string (eg. 2:21 --> 221, 00:23 --> 23) int time1 =
	 * Integer.parseInt(startTime.replaceAll(":","")); int time2 =
	 * Integer.parseInt(endTime.replaceAll(":",""));
	 * 
	 * // difference between hours int hourDiff = time2 / 100 - time1 / 100 - 1;
	 * 
	 * // difference between minutes int minDiff = time2 % 100 + (60 - time1 % 100);
	 * 
	 * if (minDiff >= 60) { hourDiff++; minDiff = minDiff - 60; }
	 * 
	 * // convert answer again in string with ':' String res =
	 * String.valueOf(hourDiff) + '.' + String.valueOf(minDiff);
	 * System.out.print(res); return res; }
	 * 
	 * public static String addTimeDifference(String strTime1, String strTime2)
	 * throws Exception { String time1=strTime1; //"0:01:30"; String time2=strTime2;
	 * //"0:01:35";
	 * 
	 * SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	 * timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	 * 
	 * Date date1 = timeFormat.parse(time1); Date date2 = timeFormat.parse(time2);
	 * 
	 * long sum = date1.getTime() + date2.getTime();
	 * 
	 * String date3 = timeFormat.format(new Date(sum));
	 * System.out.println("The sum is "+date3);
	 * 
	 * System.out.print(date3); return date3; }
	 * 
	 * public static String addTimeDifferenceComp(String startTime,String endTime) {
	 * if (isTimeValid(startTime) && isTimeValid(endTime)) {
	 * 
	 * // Separating first String using delimiter ":" String[] firstTimeParts =
	 * startTime.split("."); // Converting String to Integer int hours1 =
	 * Integer.parseInt(firstTimeParts[0]); int minutes1 =
	 * Integer.parseInt(firstTimeParts[1]); int seconds1 =
	 * Integer.parseInt(firstTimeParts[2]);
	 * 
	 * // Separating second String using delimiter ":" String[] secondTimeParts =
	 * endTime.split("."); // Converting String to Integer int hours2 =
	 * Integer.parseInt(secondTimeParts[0]); int minutes2 =
	 * Integer.parseInt(secondTimeParts[1]); int seconds2 =
	 * Integer.parseInt(secondTimeParts[2]);
	 * 
	 * int hours = hours1 + hours2; int minutes = minutes1 + minutes2; int seconds =
	 * seconds1 + seconds2; int days = 0;
	 * 
	 * 
	 * 
	 * 60 seconds=1 minute. So if value of seconds>59 adding 1 minute to minutes. 60
	 * minutes=1 hour So if value of minutes>59 adding 1 hour to hours. 24 hours=1
	 * day So if value of hours>23 adding 1 day to days.
	 * 
	 * 
	 * if (seconds > 59) { seconds = seconds - 60; minutes = minutes + 1; if
	 * (minutes > 59) { minutes = minutes - 60; hours = hours + 1; if (hours > 23) {
	 * hours = hours - 24; days = days + 1; } } else {
	 * 
	 * if (hours > 23) { hours = hours - 24; days = days + 1; }
	 * 
	 * } } else { if (minutes > 59) { minutes = minutes - 60; hours = hours + 1; if
	 * (hours > 23) { hours = hours - 24; days = days + 1; } } else {
	 * 
	 * if (hours > 23) { hours = hours - 24; days = days + 1; }
	 * 
	 * } }
	 * 
	 * // Converting each integer value of String and combining all Strings. String
	 * finalTime = String.valueOf(days) + ":" + String.valueOf(hours) + ":" +
	 * String.valueOf(minutes) + ":" + String.valueOf(seconds);
	 * 
	 * System.out.println("New time is :\n" + finalTime); System.out.println("OR");
	 * System.out.println(days + " Days " + hours + " Hours " + minutes +
	 * " Minutes " + seconds + " Seconds ");
	 * 
	 * finalTime= hours+"."+minutes; return finalTime;
	 * 
	 * } else { return "F" ; }
	 * 
	 * } private static boolean isTimeValid(String time) {
	 * 
	 * boolean result = false;
	 * 
	 * Regular expression that matches String with format HH:mm:ss HH -> 0-23 mm ->
	 * 0-59 ss -> 0-59
	 * 
	 * String pattern =
	 * "(0?[0-9]|1[0-9]|2[0-3]):(0?[0-9]|[1-5][0-9]):(0?[0-9]|[1-5][0-9])"; if
	 * (time.matches(pattern)) { result = true; } return result; }
	 */
	public static UserAuthenticationDetail getLoggedinUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserAuthenticationDetail auth = (UserAuthenticationDetail) authentication;
		return auth;

	}
}
