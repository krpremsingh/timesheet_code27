package com.awcsoftware.app;

import java.time.LocalDate;

public class ErrorDetails {
	private LocalDate timestamp;
	private String details;
	private String message;
	public ErrorDetails() {
		
	}
	public ErrorDetails(LocalDate timestamp, String details, String message) {
		super();
		this.timestamp = timestamp;
		this.details = details;
		this.message = message;
	}
	public LocalDate getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDate timestamp) {
		this.timestamp = timestamp;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
