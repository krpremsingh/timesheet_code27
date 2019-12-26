package com.awcsoftware.app.common;

public enum BaseMessageConstants {

	validateProjects("project not found"), validateProjectLocations("location not found");

	private final String label;

	public String getLabel() {
		return label;
	}

	private BaseMessageConstants(String label) {
		this.label = label;
	}

}
