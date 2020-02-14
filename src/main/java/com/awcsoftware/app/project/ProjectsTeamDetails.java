package com.awcsoftware.app.project;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class ProjectsTeamDetails implements Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pdId;
	 private int projectId;
	 private List<Integer> projectsId;
	 private int empId;
	 private int role;
	 private int workingLocation;
	 private String name;
	 private String startDate;
	 private String endDate;
	 private String status;
	 private LocalDateTime addedOn;
	 private LocalDateTime lastModifiedOn;
	public int getPdId() {
		return pdId;
	}
	public void setPdId(int pdId) {
		this.pdId = pdId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public int getWorkingLocation() {
		return workingLocation;
	}
	public void setWorkingLocation(int workingLocation) {
		this.workingLocation = workingLocation;
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
	
	public List<Integer> getProjectsId() {
		return projectsId;
	}
	public void setProjectsId(List<Integer> projectsId) {
		this.projectsId = projectsId;
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
	 

}
