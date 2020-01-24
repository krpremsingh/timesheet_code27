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
	EmployeeIdCantBeBlank("Employee id can't be blank"),
	EmployeeRoleCantBeBlank("Employee role can't be blank"),
	EmployeeWorkingDateNotBeforeProjectStartDate("Project start date can't be less than employee startDate"),
	EmployeeWorkLocation("Work location can't be blank"),
	ProjectAndWorkLocationNotMatched("Work location not matched with project location"),
	ProjectAddedSuccessFully("Project added successfully"),
	ProjectNotAdded("Project not added"),
	ProjectNotFound("Project not found"),
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
