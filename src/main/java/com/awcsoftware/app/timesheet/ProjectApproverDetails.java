package com.awcsoftware.app.timesheet;

import java.time.LocalDate;

public class ProjectApproverDetails {
	
	private int padId;
	private int pId;
	private int tcApprover;
	private int level;
	private LocalDate startDate;
	private LocalDate endDate;
	private String status;
	public int getPadId() {
		return padId;
	}
	public void setPadId(int padId) {
		this.padId = padId;
	}
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	public int getTcApprover() {
		return tcApprover;
	}
	public void setTcApprover(int tcApprover) {
		this.tcApprover = tcApprover;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "TimecardApproverDetails [padId=" + padId + ", pId=" + pId + ", tcApprover=" + tcApprover + ", level="
				+ level + ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status + "]";
	}
	
	

}
