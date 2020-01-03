package com.awcsoftware.app.common;

public enum BaseMessageConstants {

	ValidateProjects(" "), 
	ProjectNotAssigned(" "),
	ValidateProjectLocations(" ");

	private final String label;

	public String getLabel() {
		return label;
	}

	private BaseMessageConstants(String label) {
		this.label = label;
	}

}
