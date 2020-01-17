package com.awcsoftware.app.project;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class ProjectsInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int projectId;
	private String projectName;
	private float budget;
	private String startDate;
	private String endDate;
	private String status;
	private LocalDate addedOn;
	private LocalDate lastModifiedOn;
	private List<ProjectLocations> projectLocations;

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public float getBudget() {
		return budget;
	}

	public void setBudget(float budget) {
		this.budget = budget;
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

	public LocalDate getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(LocalDate addedOn) {
		this.addedOn = addedOn;
	}

	public LocalDate getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(LocalDate lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public List<ProjectLocations> getProjectLocations() {
		return projectLocations;
	}

	public void setProjectLocations(List<ProjectLocations> projectLocations) {
		this.projectLocations = projectLocations;
	}

}
