package com.awcsoftware.app.timesheet;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TimecardView  implements Serializable 
{

	private int tcId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate weekstartDT;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate weekEndDT;
	private String status;

	private int tcdId;
	private int projectId;
	
	private String weekPeriod;
	private String totalHour;
	private String managerName;
	private String statusGrid;
	private String managerComment;
	private String weekWorkingHours;
	
	
	private List<TimecardDayDetails> timecardDaySummary;

	public int getTcId() {
		return tcId;
	}

	public void setTcId(int tcId) {
		this.tcId = tcId;
	}


	public LocalDate getWeekstartDT() {
		return weekstartDT;
	}

	public void setWeekstartDT(LocalDate weekstartDT) {
		this.weekstartDT = weekstartDT;
	}

	public LocalDate getWeekEndDT() {
		return weekEndDT;
	}

	public void setWeekEndDT(LocalDate weekEndDT) {
		this.weekEndDT = weekEndDT;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWeekPeriod() {
		return weekPeriod;
	}

	public void setWeekPeriod(String weekPeriod) {
		this.weekPeriod = weekPeriod;
	}

	public String getTotalHour() {
		return totalHour;
	}

	public void setTotalHour(String totalHour) {
		this.totalHour = totalHour;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getStatusGrid() {
		return statusGrid;
	}

	public void setStatusGrid(String statusGrid) {
		this.statusGrid = statusGrid;
	}

	public String getManagerComment() {
		return managerComment;
	}

	public void setManagerComment(String managerComment) {
		this.managerComment = managerComment;
	}
	  

	public List<TimecardDayDetails> getTimecardDaySummary() {
		return timecardDaySummary;
	}

	public void setTimecardDaySummary(List<TimecardDayDetails> timecardDaySummary) {
		this.timecardDaySummary = timecardDaySummary;
	}

	
	public int getTcdId() {
		return tcdId;
	}

	public void setTcdId(int tcdId) {
		this.tcdId = tcdId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getWeekWorkingHours() {
		return weekWorkingHours;
	}

	public void setWeekWorkingHours(String weekWorkingHours) {
		this.weekWorkingHours = weekWorkingHours;
	}

	@Override public String toString() { return "TimeCardView [weekstartDT=" +
	  weekstartDT + ", weekEndDT=" + weekEndDT + ", status=" + status +
	  ", weekPeriod=" + weekPeriod + ", totalHour=" + totalHour + ", managerName="
	  + managerName + ", statusGrid=" + statusGrid + ", managerComment=" +
	  managerComment + "]"; }
	 

}
