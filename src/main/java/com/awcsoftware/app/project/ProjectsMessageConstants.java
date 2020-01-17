package com.awcsoftware.app.project;

public enum ProjectsMessageConstants {
	BlankProjectName("Project name can't be blank"), 
	BlankProjectBudget("Budget can't be blank"),
	BlankStartDate("Project start date can't be blank"),
	BlankProjectStatus("Project status can't be blank"),
	BlankEndDate("Project end date can't be blank"),
	BlankProjectLocation("Project location can't be blank"),
	ProjectAlreadyExist("Project already exist"),
	InvalidDateFormat("Date format is invalid"),
	ProjectAddedSuccessFully("Project added successfully"),
	ProjectNotAdded("Project not added"),
	ProjectLocationNotFound("Project location not found"),
	ProjectEndDateRange("End date can't be less than start date"),
	ProjectUpdated("Project updated successfully");

	private final String label;

	public String getLabel() {
		return label;
	}

	private ProjectsMessageConstants(String label) {
		this.label = label;
	}
}
