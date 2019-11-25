package com.awcsoftware.app.timesheet;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

public class TimeCardDetails 
{
	private static final long serialVersionUID = -2190013203632489950L;

	@JsonBackReference
	private TimeCardSummaryInfo tc;
	private int tcdId;
	private int tcId;
	private int projectId;
	private int activityId;
	private String taskDetails;
	private String workingDay;
	private LocalDate workingDate;
	private float workingHours;
	private String status;
	private String comments;


	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime addedOn;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastModifiedOn;

	private TimeCardApprovalDetails timeCardApprovalDetails;

	public TimeCardDetails() {
	}

	/**
	 * @param tcdId
	 * @param tcId
	 * @param projectId
	 * @param activityId
	 * @param taskDetails
	 * @param workingDay
	 * @param workingDate
	 * @param workingHours
	 * @param status
	 * @param comments
	 * @param addedOn
	 * @param lastModifiedOn
	 * @param timeCardApprovalDetails
	 */

	/**
	 * @return the tcdId
	 */
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

	public String getTaskDetails() {
		return taskDetails;
	}

	public void setTaskDetails(String taskDetails) {
		this.taskDetails = taskDetails;
	}

	public String getWorkingDay() {
		return workingDay;
	}

	public void setWorkingDay(String workingDay) {
		this.workingDay = workingDay;
	}

	public LocalDate getWorkingDate() {
		return workingDate;
	}

	public void setWorkingDate(LocalDate workingDate) {
		this.workingDate = workingDate;
	}

	public float getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(float workingHours) {
		this.workingHours = workingHours;
	}

	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the timeCardApprovalDetails
	 */
	public TimeCardApprovalDetails getTimeCardApprovalDetails() {
		return timeCardApprovalDetails;
	}

	/**
	 * @param timeCardApprovalDetails the timeCardApprovalDetails to set
	 */
	public void setTimeCardApprovalDetails(TimeCardApprovalDetails timeCardApprovalDetails) {
		this.timeCardApprovalDetails = timeCardApprovalDetails;
	}

	@Override
	public String toString() 
	{
		String strReturn= "('"+tcId+"','"+projectId+"','"+activityId+"','"+workingDay+"','"+
				workingDate+"','"+workingHours+"','"+comments+"','"+status+"',";
		if(addedOn==null)		
			strReturn=strReturn+"now(),now(),'"+taskDetails+"')";		
		else
			strReturn=strReturn+"'"+addedOn+"',now(),'"+taskDetails+"')";
		
		return strReturn;
	}


}
