/**
 * 
 */
package com.awcsoftware.app.timesheet;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class TimeCardSummaryInfo implements Serializable {

	/**
	 * Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(TimeCardSummaryInfo.class);

	private int tcId;
	private int empId;
	private LocalDate weekStart;
	private LocalDate weekEnd;
	private int yearWeek;
	private float totalHours;
	private String status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime addedOn;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastModifiedOn;
	@JsonManagedReference
	private List<TimeCardDetails> timeCardDetails;

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

	public LocalDate getWeekEnd() {
		return weekEnd;
	}

	public void setWeekEnd(LocalDate weekEnd) {
		this.weekEnd = weekEnd;
	}

	public LocalDate getWeekStart() {
		return weekStart;
	}

	public void setWeekStart(LocalDate weekStart) {
		this.weekStart = weekStart;
	}

	public int getYearWeek() {
		return yearWeek;
	}

	public void setYearWeek(int yearWeek) {
		this.yearWeek = yearWeek;
	}

	public float getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(float totalHours) {
		this.totalHours = totalHours;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(LocalDateTime addedOn) {
		this.addedOn = addedOn;
	}

	public LocalDateTime getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(LocalDateTime lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public List<TimeCardDetails> getTimeCardDetails() {
		return timeCardDetails;
	}

	public void setTimeCardDetails(List<TimeCardDetails> timeCardDetails) {
		this.timeCardDetails = timeCardDetails;
	}

	@Override
	public String toString() {
		return "TimeCardSummaryInfo [tcId=" + tcId + ", empId=" + empId + ", weekStart=" + weekStart + ", weekEnd="
				+ weekEnd + ", yearWeek=" + yearWeek + ", totalHours=" + totalHours + ", status=" + status
				+ ", addedOn=" + addedOn + ", lastModifiedOn=" + lastModifiedOn + ", timeCardDetails=" + timeCardDetails
				+ "]";
	}

}
