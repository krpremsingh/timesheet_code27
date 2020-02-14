package com.awcsoftware.app.timesheet;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TimecardDayInfo implements Serializable {
	private int tcdId;
	private int tcId;
	private int projectId;
	private LocalDate workingDate;
	private String workingDay;
	private String totalWeekWorkHours;
	private String status;
	private LocalDateTime addedOn;
	private LocalDateTime lastModifiedOn;
	
	private String projectGroup;

	private List<TimecardDayDetails> timecardDayDetails;
	private String timeRemainToApprove;


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

	public LocalDate getWorkingDate() {
		return workingDate;
	}

	public void setWorkingDate(LocalDate workingDate) {
		this.workingDate = workingDate;
	}

	public String getWorkingDay() {
		return workingDay;
	}

	public void setWorkingDay(String workingDay) {
		this.workingDay = workingDay;
	}


	public String getTotalWeekWorkHours() {
		return totalWeekWorkHours;
	}

	public void setTotalWeekWorkHours(String totalWeekWorkHours) {
		this.totalWeekWorkHours = totalWeekWorkHours;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public List<TimecardDayDetails> getTimecardDayDetails() {
		return timecardDayDetails;
	}

	public void setTimecardDayDetails(List<TimecardDayDetails> timecardDayDetails) {
		this.timecardDayDetails = timecardDayDetails;
	}
	
	
	public String getProjectGroup() {
		return projectGroup;
	}

	public void setProjectGroup(String projectGroup) {
		this.projectGroup = projectGroup;
	}


	public String getTimeRemainToApprove() {
		return timeRemainToApprove;
	}

	public void setTimeRemainToApprove(String timeRemainToApprove) {
		this.timeRemainToApprove = timeRemainToApprove;
	}
	

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	@Override
	public String toString() {
		return "TimecardDayInfo [tcdId=" + tcdId + ", tcId=" + tcId + ", projectId=" + projectId + ", workingDate="
				+ workingDate + ", workingDay=" + workingDay + ", totalWeekWorkHours=" + totalWeekWorkHours
				+ ", status=" + status + ", addedOn=" + addedOn + ", lastModifiedOn=" + lastModifiedOn
				+ ", projectGroup=" + projectGroup + ", timecardDayDetails=" + timecardDayDetails
				+ ", timeRemainToApprove=" + timeRemainToApprove + "]";
	}



}
