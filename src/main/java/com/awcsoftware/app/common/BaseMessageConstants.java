package com.awcsoftware.app.common;

public enum BaseMessageConstants {

	ValidateProjects("Project not found"), 
	ProjectNotAssigned("Project not assigned"),
	ValidateProjectLocations("Location not found");

	private final String label;

	public String getLabel() {
		return label;
	}

	private BaseMessageConstants(String label) {
		this.label = label;
	}

}
