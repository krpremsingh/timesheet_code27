package com.awcsoftware.app.timesheet;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TimeCardDBValidate implements Serializable 
{
	private int tcId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate weekStart;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate weekEnd;
	private int dateDiffVal;
	private String status;

	
	public int getTcId() {
		return tcId;
	}
	public void setTcId(int tcId) {
		this.tcId = tcId;
	}
	public LocalDate getWeekStart() {
		return weekStart;
	}
	public void setWeekStart(LocalDate weekStart) {
		this.weekStart = weekStart;
	}
	public LocalDate getWeekEnd() {
		return weekEnd;
	}
	public void setWeekEnd(LocalDate weekEnd) {
		this.weekEnd = weekEnd;
	}
	public int getDateDiffVal() {
		return dateDiffVal;
	}
	public void setDateDiffVal(int dateDiffVal) {
		this.dateDiffVal = dateDiffVal;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "TimeCardDBValidate [tcId=" + tcId + ", weekStart=" + weekStart + ", weekEnd=" + weekEnd
				+ ", dateDiffVal=" + dateDiffVal + ", status=" + status + "]";
	}
	
	
	


}
