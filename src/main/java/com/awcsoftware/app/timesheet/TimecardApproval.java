package com.awcsoftware.app.timesheet;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TimecardApproval {
	private int tcadId;

	private int tcdId;
	private int approverId;
	private int levelId;
	private String comments;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime addedOn;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastModifiedOn;

	/**
	 * @return the tcadId
	 */
	public int getTcadId() {
		return tcadId;
	}

	/**
	 * @param tcadId the tcadId to set
	 */
	public void setTcadId(int tcadId) {
		this.tcadId = tcadId;
	}

	/**
	 * @return the tcdId
	 */
	public int getTcdId() {
		return tcdId;
	}

	/**
	 * @param tcdId the tcdId to set
	 */
	public void setTcdId(int tcdId) {
		this.tcdId = tcdId;
	}

	/**
	 * @return the approverId
	 */
	public int getApproverId() {
		return approverId;
	}

	/**
	 * @param approverId the approverId to set
	 */
	public void setApproverId(int approverId) {
		this.approverId = approverId;
	}

	/**
	 * @return the levelId
	 */
	public int getLevelId() {
		return levelId;
	}

	/**
	 * @param levelId the levelId to set
	 */
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
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

	@Override
	public String toString() {
		return "TimeCardApprovalDetails [tcadId=" + tcadId + ", tcdId=" + tcdId + ", approverId=" + approverId
				+ ", levelId=" + levelId + ", comments=" + comments + ", addedOn=" + addedOn + ", lastModifiedOn="
				+ lastModifiedOn + "]";
	}

}
