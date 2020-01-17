package com.awcsoftware.app.timesheet;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;


public class TimecardDayDetails implements Serializable,Comparator<TimecardDayDetails> {
	
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
	private String rowIndex;	
	
	private String projectName;
	private String projectWorkHour;

	private String activityName;
	private String locationName;
	
	private String timecardRowDetails;	
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

		
	public String getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectWorkHour() {
		return projectWorkHour;
	}
	public void setProjectWorkHour(String projectWorkHour) {
		this.projectWorkHour = projectWorkHour;
	}

	
	public String getTimecardRowDetails() {
		return timecardRowDetails;
	}
	public void setTimecardRowDetails(String timecardRowDetails) {
		this.timecardRowDetails = timecardRowDetails;
	}
	
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	@Override
	public String toString() {
		return "TimecardDayDetails [tcddId=" + tcddId + ", tcdId=" + tcdId + ", tcId=" + tcId + ", projectId="
				+ projectId + ", activityId=" + activityId + ", location=" + location + ", workingDate=" + workingDate
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", workingHours=" + workingHours + ", status="
				+ status + ", taskDetails=" + taskDetails + ", addedOn=" + addedOn + ", lastModifiedOn="
				+ lastModifiedOn + "]";
	}

	@Override
	public int compare(TimecardDayDetails o1, TimecardDayDetails o2) {
		// TODO Auto-generated method stub
        try {
            return new SimpleDateFormat("HH:mm").parse(o1.getStartTime()).compareTo(new SimpleDateFormat("HH:mm").parse(o2.getStartTime()));
        } catch (ParseException e) {
            return 0;
        }
	}	
}
