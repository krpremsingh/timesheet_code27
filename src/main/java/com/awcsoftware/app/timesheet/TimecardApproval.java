package com.awcsoftware.app.timesheet;

import java.time.LocalDateTime;
import java.util.List;

public class TimecardApproval {
	private int tcadId;
	private int tcdId;
	private int approverId;
	private String comments;
	private LocalDateTime addedOn;
	private LocalDateTime lastModifiedOn;
	private String status;
	private List<TimecardInfo> timecardInfo;
	
	public int getTcadId() {
		return tcadId;
	}
	public void setTcadId(int tcadId) {
		this.tcadId = tcadId;
	}
	public int getTcdId() {
		return tcdId;
	}
	public void setTcdId(int tcdId) {
		this.tcdId = tcdId;
	}
	public int getApproverId() {
		return approverId;
	}
	public void setApproverId(int approverId) {
		this.approverId = approverId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<TimecardInfo> getTimecardInfo() {
		return timecardInfo;
	}
	public void setTimecardInfo(List<TimecardInfo> timecardInfo) {
		this.timecardInfo = timecardInfo;
	}
	



}
