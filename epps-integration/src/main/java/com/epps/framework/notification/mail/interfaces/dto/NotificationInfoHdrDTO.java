package com.epps.framework.notification.mail.interfaces.dto;

import java.util.Date;
import java.util.List;

import com.epps.framework.application.util.date.DateHelper;

public class NotificationInfoHdrDTO {

	private Integer notiHdrSrNo;
	
	private Integer companyCode;
	private Integer divisionCode;
	private Integer roleCode;
    private String employeeCode;
    private String ipAddress;
	private Integer locationCode;
	
	private Date postedOn;
	private String notiDate;
	private String notiHeader;
	private String notiString;
	private String locationDisplayName;
	private String notiTime;
	private Integer updatorRoleCode;
	private String activeYn;
	
	private String notiSend;
	private String schedularJobName;
	
	private List<NotificationInfoDtlDTO> notiInfoDTOs;
	
	public Integer getNotiHdrSrNo() {
		return notiHdrSrNo;
	}

	public void setNotiHdrSrNo(Integer notiHdrSrNo) {
		this.notiHdrSrNo = notiHdrSrNo;
	}

	public Integer getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(Integer companyCode) {
		this.companyCode = companyCode;
	}

	public Integer getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(Integer divisionCode) {
		this.divisionCode = divisionCode;
	}

	public Integer getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(Integer locationCode) {
		this.locationCode = locationCode;
	}

	public Date getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
		
		if(this.postedOn != null){
			this.notiDate = DateHelper.getFormattedDateString(this.postedOn, DateHelper.DATE_TIME_FORMAT_FOR_NOTIFICATIONS);
		}
	}
	
	public String getNotiDate() {
		return notiDate;
	}

	public void setNotiDate(String notiDate) {
		this.notiDate = notiDate;
	}

	public String getNotiHeader() {
		return notiHeader;
	}

	public void setNotiHeader(String notiHeader) {
		this.notiHeader = notiHeader;
	}
	
	public String getNotiString() {
		return notiString;
	}

	public void setNotiString(String notiString) {
		this.notiString = notiString;
	}

	public String getNotiTime() {
		return notiTime;
	}

	public void setNotiTime(String notiTime) {
		this.notiTime = notiTime;
	}

	public Integer getUpdatorRoleCode() {
		return updatorRoleCode;
	}

	public void setUpdatorRoleCode(Integer updatorRoleCode) {
		this.updatorRoleCode = updatorRoleCode;
	}

	public String getLocationDisplayName() {
		return locationDisplayName;
	}

	public void setLocationDisplayName(String locationDisplayName) {
		this.locationDisplayName = locationDisplayName;
	}
	
	public String getActiveYn() {
		return activeYn;
	}

	public void setActiveYn(String activeYn) {
		this.activeYn = activeYn;
	}

	public Integer getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(Integer roleCode) {
		this.roleCode = roleCode;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getNotiSend() {
		return notiSend;
	}

	public void setNotiSend(String notiSend) {
		this.notiSend = notiSend;
	}

	public String getSchedularJobName() {
		return schedularJobName;
	}

	public void setSchedularJobName(String schedularJobName) {
		this.schedularJobName = schedularJobName;
	}

	public List<NotificationInfoDtlDTO> getNotiInfoDTOs() {
		return notiInfoDTOs;
	}

	public void setNotiInfoDTOs(List<NotificationInfoDtlDTO> notiInfoDTOs) {
		this.notiInfoDTOs = notiInfoDTOs;
	}
	
}
