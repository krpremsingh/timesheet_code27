package com.awcsoftware.app.timesheet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class TimeCardSummaryInfo {
	// Field mapped for table TimeCardSummaryInfo
	private int tcId;
	private int empId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate weekStart;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate weekEnd;
	private int yearWeek;
	private float totalHours;
	private String status;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime addedOn;

//	variable to store json Array
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

	public List<TimeCardDetails> getTimeCardDetails() {
		return timeCardDetails;
	}

	public void setTimeCardDetails(List<TimeCardDetails> timeCardDetails) {
		this.timeCardDetails = timeCardDetails;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addedOn == null) ? 0 : addedOn.hashCode());
		result = prime * result + empId;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + tcId;
		result = prime * result + ((timeCardDetails == null) ? 0 : timeCardDetails.hashCode());
		result = prime * result + Float.floatToIntBits(totalHours);
		result = prime * result + ((weekEnd == null) ? 0 : weekEnd.hashCode());
		result = prime * result + ((weekStart == null) ? 0 : weekStart.hashCode());
		result = prime * result + yearWeek;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeCardSummaryInfo other = (TimeCardSummaryInfo) obj;
		if (addedOn == null) {
			if (other.addedOn != null)
				return false;
		} else if (!addedOn.equals(other.addedOn))
			return false;
		if (empId != other.empId)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tcId != other.tcId)
			return false;
		if (timeCardDetails == null) {
			if (other.timeCardDetails != null)
				return false;
		} else if (!timeCardDetails.equals(other.timeCardDetails))
			return false;
		if (Float.floatToIntBits(totalHours) != Float.floatToIntBits(other.totalHours))
			return false;
		if (weekEnd == null) {
			if (other.weekEnd != null)
				return false;
		} else if (!weekEnd.equals(other.weekEnd))
			return false;
		if (weekStart == null) {
			if (other.weekStart != null)
				return false;
		} else if (!weekStart.equals(other.weekStart))
			return false;
		if (yearWeek != other.yearWeek)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TimeCardSummaryInfo [tcId=" + tcId + ", empId=" + empId + ", weekStart=" + weekStart + ", weekEnd="
				+ weekEnd + ", yearWeek=" + yearWeek + ", totalHours=" + totalHours + ", status=" + status
				+ ", addedOn=" + addedOn + ", timeCardDetails=" + timeCardDetails + "]";
	}
	
}
