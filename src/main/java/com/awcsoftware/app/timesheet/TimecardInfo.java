package com.awcsoftware.app.timesheet;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;

public class TimecardInfo implements Serializable {
	static Logger logger = Logger.getLogger(TimecardInfo.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Field mapped for table TimeCardSummaryInfo
/*
 * Field used for inserting/Editing data
 * 
 */
	private int tcId;
	private int empId;
	private LocalDate weekStart;
	private LocalDate weekEnd;
	private String totalHours;
	private String status;
	private LocalDateTime addedOn;
	private List<TimecardDayInfo> timecardDayInfo;
	
	private String WeekPeriod;
	private String statusGrid;
	private String managerName;
    private String email;
    private String midsubmitflag;
    private String relievingDate;
    private String resourceType;
    /*
	 * Field used for showing error when logged-in user 
	 * and data insert user are different
	 * 
	 */

	private String searchStatus;

	/*
	 * Data show per tcId
	 * 
	 */
	
	private String empName;
	private String projectId;
	private String projectName;
	private String projectGroup;
	private List<Integer> projectValue;
	private String approverId;
	private String searchType;	
	private List<String> statusValue;
	
	private List<TimecardProjectWorkDetails> employeeProjectTimecard;
	private List<TimecardApproverDetails> timecardApproverDetails;
	
	
	public String getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}

	public int getTcId() {
		return tcId;
	}

	public void setTcId(int tcId) {
		this.tcId = tcId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public LocalDate getWeekStart() {
		return weekStart;
	}

	public void setWeekStart(LocalDate weekStart) {
		this.weekStart = weekStart;
	}

	public LocalDate getWeekEnd() {
		return weekEnd;
	}

	public void setWeekEnd(LocalDate weekEnd) {
		this.weekEnd = weekEnd;
	}


	public String getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
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

	public List<TimecardDayInfo> getTimecardDayInfo() {
		return timecardDayInfo;
	}

	public void setTimecardDayInfo(List<TimecardDayInfo> timecardDayInfo) {
		this.timecardDayInfo = timecardDayInfo;
	}

	
	public String getWeekPeriod() {
		return WeekPeriod;
	}

	public void setWeekPeriod(String weekPeriod) {
		WeekPeriod = weekPeriod;
	}

	public String getStatusGrid() {
		return statusGrid;
	}

	public void setStatusGrid(String statusGrid) {
		this.statusGrid = statusGrid;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
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

	public String getProjectGroup() {
		
		return projectGroup;
	}

	public void setProjectGroup(String projectGroup) {
		this.projectGroup = projectGroup;
	}

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	
	
	public List<TimecardProjectWorkDetails> getEmployeeProjectTimecard() {
		return employeeProjectTimecard;
	}

	public void setEmployeeProjectTimecard(List<TimecardProjectWorkDetails> employeeProjectTimecard) {
		this.employeeProjectTimecard = employeeProjectTimecard;
	}

	public List<Integer> getProjectValue() {
		return projectValue;
	}

	public void setProjectValue(List<Integer> projectValue) {
		this.projectValue = projectValue;
	}
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}


	
	public List<String> getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(List<String> statusValue) {
		this.statusValue = statusValue;
	}


	public List<TimecardApproverDetails> getTimecardApproverDetails() {
		return timecardApproverDetails;
	}

	public void setTimecardApproverDetails(List<TimecardApproverDetails> timecardApproverDetails) {
		this.timecardApproverDetails = timecardApproverDetails;
	}
	

	public String getMidsubmitflag() {
		return midsubmitflag;
	}

	public void setMidsubmitflag(String midsubmitflag) {
		this.midsubmitflag = midsubmitflag;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	
	public String getRelievingDate() {
		return relievingDate;
	}

	public void setRelievingDate(String relievingDate) {
		this.relievingDate = relievingDate;
	}

	@Override
	public String toString() {
		return "TimecardInfo [tcId=" + tcId + ", empId=" + empId + ", weekStart=" + weekStart + ", weekEnd=" + weekEnd
				+ ", totalHours=" + totalHours + ", status=" + status + ", addedOn=" + addedOn + ", timecardDayInfo="
				+ timecardDayInfo + ", WeekPeriod=" + WeekPeriod + ", statusGrid=" + statusGrid + ", managerName="
				+ managerName + ", email=" + email + ", searchStatus=" + searchStatus + ", empName=" + empName
				+ ", projectId=" + projectId + ", projectName=" + projectName + ", projectGroup=" + projectGroup
				+ ", projectValue=" + projectValue + ", approverId=" + approverId + ", employeeProjectTimecard="
				+ employeeProjectTimecard + "]";
	}


}
