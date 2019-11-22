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
		
		/*
		 * 
		private int tcId;
		private int empId;
		private LocalDate weekStart;
		private LocalDate weekEnd;
		private int yearWeek;
		private float totalHours;
		private String status;
		private LocalDateTime addedOn;
		private LocalDateTime lastModifiedOn;
		private List<TimeCardDetails> timeCardDetails;
		 * */
		TimeCardSummaryInfo tci=new TimeCardSummaryInfo();
		tci.setEmpId(1277);
		tci.setWeekStart(LocalDate.now());
		tci.setWeekEnd(LocalDate.now().plusDays(7));
		tci.setTotalHours(20);
		TimeCardDetails tcd1=new TimeCardDetails();
		tcd1.setWorkingDate(LocalDate.now());
		TimeCardDetails tcd2=new TimeCardDetails();
		tcd2.setWorkingDate(LocalDate.now().plusDays(1));
		TimeCardDetails tcd3=new TimeCardDetails();
		tcd3.setWorkingDate(LocalDate.now().plusDays(2));
		TimeCardDetails tcd4=new TimeCardDetails();
		tcd4.setWorkingDate(LocalDate.now().plusDays(3));
		TimeCardDetails tcd5=new TimeCardDetails();
		tcd5.setWorkingDate(LocalDate.now().plusDays(4));
		
		List<TimeCardDetails> list=new ArrayList<TimeCardDetails>();
		list.add(tcd1);list.add(tcd2);list.add(tcd3);list.add(tcd4);//list.add(tcd5);
		tci.setTimeCardDetails(list);
		TimeCardValidator tcv=new TimeCardValidator();
		
		System.out.println(tcv.validateSubmitTimeCard(tci));
	}
}
