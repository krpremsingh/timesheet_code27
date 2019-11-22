package com.awcsoftware.app.timesheet;

import java.time.LocalDate;

public class TimecardInfo {

	private int tcId;
	private int empId;

	private LocalDate addedOn;
	private LocalDate lastModifiedOn;
	
	public int getTcId() {
		return tcId;
	}
	public void setTcId(int tcId) {
		this.tcId = tcId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
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

	
	
	
}
