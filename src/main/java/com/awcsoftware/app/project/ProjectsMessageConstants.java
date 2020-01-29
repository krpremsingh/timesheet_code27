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
	EmployeeWorkingDateNotBeforeProjectStartDate("Employee project assigned date can't be greater than project startDate"),
	EmployeeWorkLocation("Work location can't be blank"),
	ProjectAndWorkLocationNotMatched("Work location not matched with project location"),
	ProjectAddedSuccessFully("Project added successfully"),
	ProjectNotAdded("Project not added"),
	ProjectNotFound("Project not found"),
	ProjectLocationNotFound("Project location not found"),
	ApproverStartDateNotGreaterThanProjectStartDate("Project approver start date must be after project start date"),
	ProjectEndDateRange("End date can't be less than start date"),
	ProjectApproverCantbeBlank("Project approver field can't be blank"),
	ProjectApproverStartDate("Project approver start date can't be blank"),
	ProjectApproverLevel("Project approver level can't be blank"),
	ProjectApproverStatus("Project approver status can't be blank"),
	ProjectUpdated("Project updated successfully");

	private final String label;

	public String getLabel() {
		return label;
	}

	private ProjectsMessageConstants(String label) {
		this.label = label;
	}
}
