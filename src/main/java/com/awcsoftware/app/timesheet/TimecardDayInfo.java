package com.awcsoftware.app.timesheet;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TimecardDayInfo implements Serializable {
	private int tcdId;
	private int tcId;
	
	private LocalDate workingDate;
	private String workingDay;
	private float totalWeekWorkHours;
	private String status;
	private LocalDateTime addedOn;
	private LocalDateTime lastModifiedOn;

	private List<TimecardDayDetails> timecardDayDetails;

	public int getTcdId() {
		return tcdId;
	}

	public void setTcdId(int tcdId) {
		this.tcdId = tcdId;
	}

	public int getTcId() {
		return tcId;
	}

	public void setTcId(int tcId) {
		this.tcId = tcId;
	}

	public LocalDate getWorkingDate() {
		return workingDate;
	}

	public void setWorkingDate(LocalDate workingDate) {
		this.workingDate = workingDate;
	}

	public String getWorkingDay() {
		return workingDay;
	}

	public void setWorkingDay(String workingDay) {
		this.workingDay = workingDay;
	}

	public float getTotalWeekWorkHours() {
		return totalWeekWorkHours;
	}

	public void setTotalWeekWorkHours(float totalWeekWorkHours) {
		this.totalWeekWorkHours = totalWeekWorkHours;
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

	public List<TimecardDayDetails> getTimecardDayDetails() {
		return timecardDayDetails;
	}

	public void setTimecardDayDetails(List<TimecardDayDetails> timecardDayDetails) {
		this.timecardDayDetails = timecardDayDetails;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addedOn == null) ? 0 : addedOn.hashCode());
		result = prime * result + ((lastModifiedOn == null) ? 0 : lastModifiedOn.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + tcId;
		result = prime * result + tcdId;
		result = prime * result + Float.floatToIntBits(totalWeekWorkHours);
		result = prime * result + ((workingDate == null) ? 0 : workingDate.hashCode());
		result = prime * result + ((workingDay == null) ? 0 : workingDay.hashCode());
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
		TimecardDayInfo other = (TimecardDayInfo) obj;
		if (addedOn == null) {
			if (other.addedOn != null)
				return false;
		} else if (!addedOn.equals(other.addedOn))
			return false;
		if (lastModifiedOn == null) {
			if (other.lastModifiedOn != null)
				return false;
		} else if (!lastModifiedOn.equals(other.lastModifiedOn))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tcId != other.tcId)
			return false;
		if (tcdId != other.tcdId)
			return false;
		if (Float.floatToIntBits(totalWeekWorkHours) != Float.floatToIntBits(other.totalWeekWorkHours))
			return false;
		if (workingDate == null) {
			if (other.workingDate != null)
				return false;
		} else if (!workingDate.equals(other.workingDate))
			return false;
		if (workingDay == null) {
			if (other.workingDay != null)
				return false;
		} else if (!workingDay.equals(other.workingDay))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TimecardDayInfo [tcdId=" + tcdId + ", tcId=" + tcId + ", workingDate=" + workingDate + ", workingDay="
				+ workingDay + ", totalWeekWorkHours=" + totalWeekWorkHours + ", status=" + status + ", addedOn="
				+ addedOn + ", lastModifiedOn=" + lastModifiedOn + "]";
	}

}
