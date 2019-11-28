package com.awcsoftware.app.timesheet;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TimeCardView  implements Serializable 
{

	private int tcId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate weekstartDT;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate weekEndDT;
	private String status;

	private String weekPeriod;
	private String totalHour;
	private String managerName;
	private String statusGrid;
	private String managerComment;

	public int getTcId() {
		return tcId;
	}

	public void setTcId(int tcId) {
		this.tcId = tcId;
	}


	public LocalDate getWeekstartDT() {
		return weekstartDT;
	}

	public void setWeekstartDT(LocalDate weekstartDT) {
		this.weekstartDT = weekstartDT;
	}

	public LocalDate getWeekEndDT() {
		return weekEndDT;
	}

	public void setWeekEndDT(LocalDate weekEndDT) {
		this.weekEndDT = weekEndDT;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWeekPeriod() {
		return weekPeriod;
	}

	public void setWeekPeriod(String weekPeriod) {
		this.weekPeriod = weekPeriod;
	}

	public String getTotalHour() {
		return totalHour;
	}

	public void setTotalHour(String totalHour) {
		this.totalHour = totalHour;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getStatusGrid() {
		return statusGrid;
	}

	public void setStatusGrid(String statusGrid) {
		this.statusGrid = statusGrid;
	}

	public String getManagerComment() {
		return managerComment;
	}

	public void setManagerComment(String managerComment) {
		this.managerComment = managerComment;
	}

	
	  @Override public int hashCode() { final int prime = 31; int result = 1;
	  result = prime * result + ((managerComment == null) ? 0 :
	  managerComment.hashCode()); result = prime * result + ((managerName == null)
	  ? 0 : managerName.hashCode()); result = prime * result + ((status == null) ?
	  0 : status.hashCode()); result = prime * result + ((statusGrid == null) ? 0 :
	  statusGrid.hashCode()); result = prime * result + ((totalHour == null) ? 0 :
	  totalHour.hashCode()); result = prime * result + ((weekEndDT == null) ? 0 :
	  weekEndDT.hashCode()); result = prime * result + ((weekPeriod == null) ? 0 :
	  weekPeriod.hashCode()); result = prime * result + ((weekstartDT == null) ? 0
	  : weekstartDT.hashCode()); return result; }
	  
	  @Override public boolean equals(Object obj) { if (this == obj) return true;
	  if (obj == null) return false; if (getClass() != obj.getClass()) return
	  false; TimeCardView other = (TimeCardView) obj; if (managerComment == null) {
	  if (other.managerComment != null) return false; } else if
	  (!managerComment.equals(other.managerComment)) return false; if (managerName
	  == null) { if (other.managerName != null) return false; } else if
	  (!managerName.equals(other.managerName)) return false; if (status == null) {
	  if (other.status != null) return false; } else if
	  (!status.equals(other.status)) return false; if (statusGrid == null) { if
	  (other.statusGrid != null) return false; } else if
	  (!statusGrid.equals(other.statusGrid)) return false; if (totalHour == null) {
	  if (other.totalHour != null) return false; } else if
	  (!totalHour.equals(other.totalHour)) return false; if (weekEndDT == null) {
	  if (other.weekEndDT != null) return false; } else if
	  (!weekEndDT.equals(other.weekEndDT)) return false; if (weekPeriod == null) {
	  if (other.weekPeriod != null) return false; } else if
	  (!weekPeriod.equals(other.weekPeriod)) return false; if (weekstartDT == null)
	  { if (other.weekstartDT != null) return false; } else if
	  (!weekstartDT.equals(other.weekstartDT)) return false; return true; }
	  
	  @Override public String toString() { return "TimeCardView [weekstartDT=" +
	  weekstartDT + ", weekEndDT=" + weekEndDT + ", status=" + status +
	  ", weekPeriod=" + weekPeriod + ", totalHour=" + totalHour + ", managerName="
	  + managerName + ", statusGrid=" + statusGrid + ", managerComment=" +
	  managerComment + "]"; }
	 

}
