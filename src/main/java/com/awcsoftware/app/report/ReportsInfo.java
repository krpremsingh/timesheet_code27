package com.awcsoftware.app.report;

public class ReportsInfo {
private String timcardStatus;
private int empId;
private String weekStart;
private String weekEnd;
private String totalHours;
private String projectId;
private int statusCount;
private int tcId;
public int getTcId() {
	return tcId;
}
public void setTcId(int tcId) {
	this.tcId = tcId;
}
public String getTimcardStatus() {
	return timcardStatus;
}
public void setTimcardStatus(String timcardStatus) {
	this.timcardStatus = timcardStatus;
}
public int getEmpId() {
	return empId;
}
public void setEmpId(int empId) {
	this.empId = empId;
}

public String getWeekStart() {
	return weekStart;
}
public void setWeekStart(String weekStart) {
	this.weekStart = weekStart;
}
public String getWeekEnd() {
	return weekEnd;
}
public void setWeekEnd(String weekEnd) {
	this.weekEnd = weekEnd;
}
public String getTotalHours() {
	return totalHours;
}
public void setTotalHours(String totalHours) {
	this.totalHours = totalHours;
}
public String getProjectId() {
	return projectId;
}
public void setProjectId(String projectId) {
	this.projectId = projectId;
}
public int getStatusCount() {
	return statusCount;
}
public void setStatusCount(int statusCount) {
	this.statusCount = statusCount;
}


}
