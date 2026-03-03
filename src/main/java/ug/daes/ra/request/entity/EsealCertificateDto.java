package ug.daes.ra.request.entity;

import java.io.Serializable;

public class EsealCertificateDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String spocFullName;
	private String orgName;
	private String startDate;
	private String endDate;
	public String getSpocFullName() {
		return spocFullName;
	}
	public void setSpocFullName(String spocFullName) {
		this.spocFullName = spocFullName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	@Override
	public String toString() {
		return "EsealCertificateDto [spocFullName=" + spocFullName + ", orgName=" + orgName + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}
}

