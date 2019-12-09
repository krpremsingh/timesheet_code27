package com.awcsoftware.app.timesheet;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class TimecardDayDetails implements Serializable {
	
	private int tcddId;
	private int tcdId;
	private int tcId;
	private int projectId;
	private int activityId;
	private int location;
	private LocalDate workingDate;
	private String startTime;	
	private String endTime;
	private String workingHours;
	private String status;
	private String taskDetails;
	private LocalDateTime addedOn;
	private LocalDateTime lastModifiedOn;
	private String recordType;
	
	public int getTcddId() {
		return tcddId;
	}
	public void setTcddId(int tcddId) {
		this.tcddId = tcddId;
	}
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
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
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

	public String getTaskDetails() {
		return taskDetails;
	}
	public void setTaskDetails(String taskDetails) {
		this.taskDetails = taskDetails;
	}
	public LocalDateTime getAddedOn() {
		return addedOn;
	}
	public void setAddedOn(LocalDateTime addedOn) {
		this.addedOn = addedOn;
	}
	public LocalDateTime getLastModifiedOn() {
		return lastModifiedOn;
	}
	public void setLastModifiedOn(LocalDateTime lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	
	public LocalDate getWorkingDate() {
		return workingDate;
	}
	public void setWorkingDate(LocalDate workingDate) {
		this.workingDate = workingDate;
	}

	
	
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	@Override
	public String toString() {
		return "TimecardDayDetails [tcddId=" + tcddId + ", tcdId=" + tcdId + ", tcId=" + tcId + ", projectId="
				+ projectId + ", activityId=" + activityId + ", location=" + location + ", workingDate=" + workingDate
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", workingHours=" + workingHours + ", status="
				+ status + ", taskDetails=" + taskDetails + ", addedOn=" + addedOn + ", lastModifiedOn="
				+ lastModifiedOn + "]";
	}	
}
