package com.awcsoftware.app.timesheet;

import java.io.Serializable;
import java.util.List;

public class TimecardDetailSearch implements Serializable{

	private String tcId;
	private String tcdId;
	private String projectId;
	private String activityId;
	private String location;
	private String totalWeekWorkHours;
	private String managerName;
	private String status;
	private String workingDate;
	private String workingDay;
	private String managerComment;
	
	public String getTcId() {
		return tcId;
	}
	public void setTcId(String tcId) {
		this.tcId = tcId;
	}
	public String getTcdId() {
		return tcdId;
	}
	public void setTcdId(String tcdId) {
		this.tcdId = tcdId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getWeekWorkingHours() {
		return totalWeekWorkHours;
	}
	public void setWeekWorkingHours(String weekWorkingHours) {
		this.totalWeekWorkHours = weekWorkingHours;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTotalWeekWorkHours() {
		return totalWeekWorkHours;
	}
	public void setTotalWeekWorkHours(String totalWeekWorkHours) {
		this.totalWeekWorkHours = totalWeekWorkHours;
	}
	public String getWorkingDate() {
		return workingDate;
	}
	public void setWorkingDate(String workingDate) {
		this.workingDate = workingDate;
	}
	public String getWorkingDay() {
		return workingDay;
	}
	public void setWorkingDay(String workingDay) {
		this.workingDay = workingDay;
	}
	public String getManagerComment() {
		return managerComment;
	}
	public void setManagerComment(String managerComment) {
		this.managerComment = managerComment;
	}	
}
