package com.kh.ti.point.model.vo;

import java.sql.Date;

public class SearchPoint {
	private String userName;
	private Date startDate;
	private Date endDate;
	private int refundStatus;
	private int rebateStatus;
	private int condition;
	
	public SearchPoint() {}

	public SearchPoint(String userName, Date startDate, Date endDate, int refundStatus, int rebateStatus,
			int condition) {
		super();
		this.userName = userName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.refundStatus = refundStatus;
		this.rebateStatus = rebateStatus;
		this.condition = condition;
	}

	public String getUserName() {
		return userName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public int getRefundStatus() {
		return refundStatus;
	}

	public int getRebateStatus() {
		return rebateStatus;
	}

	public int getCondition() {
		return condition;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setRefundStatus(int refundStatus) {
		this.refundStatus = refundStatus;
	}

	public void setRebateStatus(int rebateStatus) {
		this.rebateStatus = rebateStatus;
	}

	public void setCondition(int condition) {
		this.condition = condition;
	}

	@Override
	public String toString() {
		return "SearchPoint [userName=" + userName + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", refundStatus=" + refundStatus + ", rebateStatus=" + rebateStatus + ", condition=" + condition
				+ "]";
	}
	
}