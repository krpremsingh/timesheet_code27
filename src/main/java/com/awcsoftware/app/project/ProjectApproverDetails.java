package com.awcsoftware.app.project;

import java.io.Serializable;

public class ProjectApproverDetails  implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int projectId;
	 private int tcApprover;
	 private int level;
	 private String startDate;
	 private String endDate;
	 private String status;
	 private String addedOn;
	 private String lastModifiedOn;


	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getTcApprover() {
		return tcApprover;
	}
	public void setTcApprover(int tcApprover) {
		this.tcApprover = tcApprover;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAddedOn() {
		return addedOn;
	}
	public void setAddedOn(String addedOn) {
		this.addedOn = addedOn;
	}
	public String getLastModifiedOn() {
		return lastModifiedOn;
	}
	public void setLastModifiedOn(String lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	 
}
