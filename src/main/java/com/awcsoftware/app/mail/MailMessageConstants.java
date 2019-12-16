package com.awcsoftware.app.mail;

public enum MailMessageConstants {

	ChangePasswordRequest("Change Password Request"),
	
	InvalidEmail("Email id does not exist");
	
	private final String label;

	public String getLabel() {
		return label;
	}

	private MailMessageConstants(String label) {
		this.label = label;
	}
	
}
