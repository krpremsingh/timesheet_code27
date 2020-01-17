package com.awcsoftware.app.project;

import java.io.Serializable;

public class ProjectLocations implements Serializable {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
private int projectId;
private int workLocationId;
private String workLocation;

public int getProjectId() {
	return projectId;
}
public void setProjectId(int projectId) {
	this.projectId = projectId;
}
public int getWorkLocationId() {
	return workLocationId;
}
public void setWorkLocationId(int workLocationId) {
	this.workLocationId = workLocationId;
}
public String getWorkLocation() {
	return workLocation;
}
public void setWorkLocation(String workLocation) {
	this.workLocation = workLocation;
}


}
