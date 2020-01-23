package com.awcsoftware.app.timesheet;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class TimecardApproverDetails implements Serializable {
	
	private int tcadId;
	private int tcId;
	private int projectId;
	private int approverId;
	private int levelId;
	private String status;
	private String comments;
	private LocalDateTime addedOn;
	private LocalDateTime lastModifiedOn;
	private String approverEmailId;
	private String approverName;
	private String employeeName;
	
	private List<TimecardInfo> timecardInfo;

	public int getTcadId() {
		return tcadId;
	}

	public void setTcadId(int tcadId) {
		this.tcadId = tcadId;
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

	public int getApproverId() {
		return approverId;
	}

	public void setApproverId(int approverId) {
		this.approverId = approverId;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

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

	public List<TimecardInfo> getTimecardInfo() {
		return timecardInfo;
	}

	public void setTimecardInfo(List<TimecardInfo> timecardInfo) {
		this.timecardInfo = timecardInfo;
	}

	public String getApproverEmailId() {
		return approverEmailId;
	}

	public void setApproverEmailId(String approverEmailId) {
		this.approverEmailId = approverEmailId;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	@Override
	public String toString() {
		return "TimecardApproverDetails [tcadId=" + tcadId + ", tcId=" + tcId + ", projectId=" + projectId
				+ ", approverId=" + approverId + ", levelId=" + levelId + ", status=" + status + ", comments="
				+ comments + ", addedOn=" + addedOn + ", lastModifiedOn=" + lastModifiedOn + ", timecardInfo="
				+ timecardInfo + "]";
	}

	

}
