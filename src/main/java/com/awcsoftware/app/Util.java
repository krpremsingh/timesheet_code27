package com.awcsoftware.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
		SimpleDateFormat fmt1 = new SimpleDateFormat(AppConstant.TIME_FORMAT_CONST.DATE_FORMAT.getValue());
		SimpleDateFormat fmt2 = new SimpleDateFormat(AppConstant.TIME_FORMAT_CONST.DATE_FORMAT.getValue());
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

	public static String TimeAdd(String startTimeParam, String endTimeParam)  {
		String returnTime = "";
		try
		{
			String startTime = startTimeParam;
			String endTime = endTimeParam;
	
			SimpleDateFormat timeFormat = new SimpleDateFormat(AppConstant.TIME_FORMAT_CONST.TIME_24_HOUR_FORMAT.getValue());
			timeFormat.setTimeZone(TimeZone.getTimeZone(AppConstant.TIME_FORMAT_CONST.TIME_ZONE.getValue()));
	
			Date startFormatDate = timeFormat.parse(startTime);
			Date endFormatDate = timeFormat.parse(endTime);
	
			long sum = startFormatDate.getTime() + endFormatDate.getTime();
	
			returnTime = timeFormat.format(new Date(sum)).toString();
		}
		catch(ParseException ex)
		{
			return new AppException(ex).toString();
		}
		return returnTime;
	}

	public static String TimeDiff(String startTimeParam, String endTimeParam) {
		String returnTime ="";
		try
		{
			String startTime = startTimeParam;
			String endTime = endTimeParam;
	
			SimpleDateFormat timeFormat = new SimpleDateFormat(AppConstant.TIME_FORMAT_CONST.TIME_24_HOUR_FORMAT.getValue());
			timeFormat.setTimeZone(TimeZone.getTimeZone(AppConstant.TIME_FORMAT_CONST.TIME_ZONE.getValue()));
	
			Date startFormatDate = timeFormat.parse(startTime);
			Date endFormatDate = timeFormat.parse(endTime);
	
			long diff = endFormatDate.getTime() - startFormatDate.getTime();
	
			returnTime = timeFormat.format(new Date(diff));
		}
		catch(ParseException ex)
		{
			return new AppException(ex).toString();
		}
		return returnTime;
	}

	public static UserAuthenticationDetail getLoggedinUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserAuthenticationDetail auth = (UserAuthenticationDetail) authentication;
		return auth;

	}

	public static boolean isValidTime(String inputTime) {

		boolean result = false;
		/*
		 * Regular expression that matches String with format HH:mm:ss 
		 * HH -> 0-23 
		 * mm -> 0-59 
		 * ss -> 0-59
		 */
		String pattern = "(0?[0-9]|1[0-9]|2[0-3]):(0?[0-9]|[1-5][0-9])";
		if (inputTime.matches(pattern)) {
			result = true;
		}
		return result;
	}

	public static boolean isValidDate(String inputDate)
	{
		boolean result= false;
		Pattern DATE_PATTERN = Pattern.compile(
			      "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
			      + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
			      + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
			      + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");
		
		return DATE_PATTERN.matcher(inputDate).matches();
		
	}

	public static String getDateDay(String inputDate) throws ParseException
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstant.TIME_FORMAT_CONST.DATE_FORMAT.getValue());
		Date parsedDate = dateFormat.parse(inputDate);
		Calendar calcInstance = Calendar.getInstance();
		calcInstance.setTime(parsedDate);		
        int dayNumber = calcInstance.get(Calendar.DAY_OF_WEEK);
        String[] strDayArray= {"Sunday","Monday","Tuesday","Wednesday","ThursDay","Friday","Saturday"};       
        return strDayArray[dayNumber-1];
		
	}


 	public static Date getWeekStartDate() {
	    Calendar calendar = Calendar.getInstance();
	    while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
	        calendar.add(Calendar.DATE, -1);
	    }
	    return calendar.getTime();
	}

 	public static String addNumberOfdays(Date inputDate)
 	{
 		SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstant.TIME_FORMAT_CONST.DATE_FORMAT.getValue());
 		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(inputDate);
	    calendar.add(Calendar.DATE, AppConstant.WORKING_HOURS.Number_of_future_Days_allowed.getValue());	    
	    String futureDate = dateFormat.format(calendar.getTime());		    
	    return futureDate;
 		
 	}
 	
	public static Date getWeekEndDate() {
	    Calendar calendar = Calendar.getInstance();
	    while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
	        calendar.add(Calendar.DATE, 1);
	    }
	    calendar.add(Calendar.DATE, -1);
	    return calendar.getTime(); 
	}

}
