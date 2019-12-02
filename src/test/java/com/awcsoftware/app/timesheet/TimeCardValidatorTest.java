/**
 * 
 */
package com.awcsoftware.app.timesheet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pratik
 *
 */
public class TimeCardValidatorTest {
	public static void main(String[] args) {
		TimecardInfo tci=new TimecardInfo();
		tci.setEmpId(1277);
		tci.setWeekStart(LocalDate.now());
		tci.setWeekEnd(LocalDate.now().plusDays(7));
		tci.setTotalHours("20");
		
		TimecardDetails tcd1=new TimecardDetails();
		tcd1.setWorkingDate(LocalDate.now());
		TimecardDetails tcd2=new TimecardDetails();
		tcd2.setWorkingDate(LocalDate.now().plusDays(1));
		TimecardDetails tcd3=new TimecardDetails();
		tcd3.setWorkingDate(LocalDate.now().plusDays(2));
		TimecardDetails tcd4=new TimecardDetails();
		tcd4.setWorkingDate(LocalDate.now().plusDays(3));
		TimecardDetails tcd5=new TimecardDetails();
		tcd5.setWorkingDate(LocalDate.now().plusDays(4));
		
		List<TimecardDetails> list=new ArrayList<TimecardDetails>();
		list.add(tcd1);list.add(tcd2);list.add(tcd3);list.add(tcd4);//list.add(tcd5);
		tci.setTimeCardDetails(list);
		TimecardValidator tcv=new TimecardValidator();
		
		System.out.println("Submit Validation :"+tcv.validateSubmitTimeCard(tci));
		System.out.println("Save Validation :"+tcv.validateSaveTimeCard(tci));
	}
}
