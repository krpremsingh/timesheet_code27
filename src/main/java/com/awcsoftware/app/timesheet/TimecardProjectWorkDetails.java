package com.awcsoftware.app.timesheet;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class TimecardProjectWorkDetails implements Serializable {
	
	private int tcId;
	private int empId;
	private String projectId;
	private String projectName;
	private String totalProjectWorkHour;
	private String approverName;
	private String approverComment;	
	private String status;
	private List<TimecardDayDetails> projectTimecard;
//	private List<TimecardDetailSearch> projectTimecard;
	
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
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getTotalProjectWorkHour() {
		return totalProjectWorkHour;
	}
	public void setTotalProjectWorkHour(String totalProjectWorkHour) {
		this.totalProjectWorkHour = totalProjectWorkHour;
	}
	public List<TimecardDayDetails> getProjectTimecard() {
		return projectTimecard;
	}
	public void setProjectTimecard(List<TimecardDayDetails> projectTimecard) {
		this.projectTimecard = projectTimecard;
	}
	public String getApproverComment() {
		return approverComment;
	}
	public void setApproverComment(String approverComment) {
		this.approverComment = approverComment;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	

//	public List<TimecardDayDetails> getProjectTimecardDetails() {
//		return projectTimecardDetails;
//	}
//	public void setProjectTimecardDetails(List<TimecardDayDetails> projectTimecardDetails) {
//		this.projectTimecardDetails = projectTimecardDetails;
//	}	

	
}
