/*package com.awcsoftware.app.report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.awcsoftware.app.Util;
import com.awcsoftware.app.employee.EmployeeValidator;

public class ReportsValidator {
	static Logger logger = Logger.getLogger(EmployeeValidator.class);
	static Set<String> errorMsg;
	static {
		errorMsg = new LinkedHashSet<String>();
	}
	
	public LocalDate dateFormatter(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;
		
	}
	
public Set<String> validateReportsDatesInput(ReportsInfo info){
	errorMsg.clear(); 
	if(Util.isEmptyOrNull(info.getWeekStart())) {
		errorMsg.add(ReportsMessageConstants.WeekStartDateCannotBeBlank.getLabel().toString());
		return errorMsg;		
	}
	if(Util.isEmptyOrNull(info.getWeekEnd())) {
		errorMsg.add(ReportsMessageConstants.WeekEndDateCannotBeBlank.getLabel().toString());
		return errorMsg;		
	}
	if(!Util.isValidDate(info.getWeekStart())) {
		errorMsg.add(ReportsMessageConstants.WeekStartDateFormatIsNotValid.getLabel().toString());
		return errorMsg;
	}
	if(!Util.isValidDate(info.getWeekEnd())) {
		errorMsg.add(ReportsMessageConstants.WeekEndDateFormatIsNotValid.getLabel().toString());
		return errorMsg;
	}
	
	if(dateFormatter(info.getWeekEnd()).isBefore(dateFormatter(info.getWeekStart()))) {
		errorMsg.add(ReportsMessageConstants.WeekStartDateNotBeforeEndDate.getLabel().toString());
		return errorMsg;		
	}

	return errorMsg;
	
}

public Set<String> validateReportsStatusInput(ReportsInfo info){
	errorMsg.clear();
	if(Util.isEmptyOrNull(info.getStatus())) {
		errorMsg.add(ReportsMessageConstants.TimecardStatusCannotbeBlank.getLabel().toString());
		return errorMsg;		
	}
	if(Util.isEmptyOrNull(info)) {
		errorMsg.add(ReportsMessageConstants.RecordNotFoundForStatus.getLabel().toString());
		return errorMsg;	
	}
	return errorMsg;
	
}

}
*/