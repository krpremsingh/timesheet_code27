package com.awcsoftware.app.timesheet;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TimeCardTimeValidate implements Serializable 
{
	private int tcdId;
	private int tcId; 
	private String projectId;
	private String activityId; 
	private String workingDate; 
	private String startTime ;
	private String endTime ; 
	private String workingHours;  
	private String status;

	public int getTcdId() {
		return tcdId;
	}
	public void setTcdId(int tcdId) {
		this.tcdId = tcdId;
	}
	
	public int getTcId() {
		return tcId;
	}
	public void setTcId(int tcId) {
		this.tcId = tcId;
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
	public String getWorkingDate() {
		return workingDate;
	}
	public void setWorkingDate(String workingDate) {
		this.workingDate = workingDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getWorkingHours() {
		return workingHours;
	}
	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	@Override
	public String toString() {
		return "TimeCardTimeValidate [tcdId=" + tcdId + ", tcId=" + tcId + ", projectId=" + projectId + ", activityId="
				+ activityId + ", workingDate=" + workingDate + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", workingHours=" + workingHours + ", status=" + status + "]";
	}
	
	
	
	


}
