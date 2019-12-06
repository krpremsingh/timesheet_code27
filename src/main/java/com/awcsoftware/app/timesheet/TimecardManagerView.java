package com.awcsoftware.app.timesheet;

import java.io.Serializable;

public class TimecardManagerView implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int empId;
	private String dayName;
	private String workingDate;
	private int projectId;
	private String totalWeekHours;
	private String weekEnd;
	private String weekStart;
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getDayName() {
		return dayName;
	}
	public void setDayName(String dayName) {
		this.dayName = dayName;
	}
	public String getWorkingDate() {
		return workingDate;
	}
	public void setWorkingDate(String workingDate) {
		this.workingDate = workingDate;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getTotalWeekHours() {
		return totalWeekHours;
	}
	public void setTotalWeekHours(String totalWeekHours) {
		this.totalWeekHours = totalWeekHours;
	}
	public String getWeekEnd() {
		return weekEnd;
	}
	public void setWeekEnd(String weekEnd) {
		this.weekEnd = weekEnd;
	}
	public String getWeekStart() {
		return weekStart;
	}
	public void setWeekStart(String weekStart) {
		this.weekStart = weekStart;
	}
	

}
