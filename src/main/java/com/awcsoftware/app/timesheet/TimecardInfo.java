package com.awcsoftware.app.timesheet;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class TimecardInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Field mapped for table TimeCardSummaryInfo
	private int tcId;
	private int empId;
	private LocalDate weekStart;
	private LocalDate weekEnd;
	private String totalHours;
	private String status;
	private LocalDateTime addedOn;
	private List<TimecardDayInfo> timecardDayInfo;
	private String WeekPeriod;
	private String statusGrid;
	private String managerName;
	
	private List<TimecardDetailSearch> timecardDetailSearch;
	
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


	public String getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(String totalHours) {
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

	public List<TimecardDayInfo> getTimecardDayInfo() {
		return timecardDayInfo;
	}

	public void setTimecardDayInfo(List<TimecardDayInfo> timecardDayInfo) {
		this.timecardDayInfo = timecardDayInfo;
	}

	
	public String getWeekPeriod() {
		return WeekPeriod;
	}

	public void setWeekPeriod(String weekPeriod) {
		WeekPeriod = weekPeriod;
	}

	public String getStatusGrid() {
		return statusGrid;
	}

	public void setStatusGrid(String statusGrid) {
		this.statusGrid = statusGrid;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	@Override
	public String toString() {
		return "TimecardInfo [tcId=" + tcId + ", empId=" + empId + ", weekStart=" + weekStart + ", weekEnd=" + weekEnd
				+ ", totalHours=" + totalHours + ", status=" + status + ", addedOn=" + addedOn + ", timecardDayInfo="
				+ timecardDayInfo + "]";
	}
}
