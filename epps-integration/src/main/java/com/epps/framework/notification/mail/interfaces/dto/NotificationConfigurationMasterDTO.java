package com.epps.framework.notification.mail.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationConfigurationMasterDTO {
	
	private Integer notiEventSrNo;
	
	private Integer companyCode;

	private Integer divisionCode;
	
	private String eventName;

	private String mtqrFlag;

	private String pid;
	
	private String programName;

	private String scheduledYn;

	private String scheduledCronExp;

	private String condition;
	
	private String conditionType;

	@JsonProperty("to_Roles")
	private String toRoles;

	@JsonProperty("to_Employees")
	private String toEmployees;

	@JsonProperty("to_MailId")
	private String toMailId;

	@JsonProperty("cc_Roles")
	private String ccRoles;

	@JsonProperty("cc_Employees")
	private String ccEmployees;
	
	@JsonProperty("cc_MailId")
	private String ccMailId;

	@JsonProperty("bcc_Roles")
	private String bccRoles;

	@JsonProperty("bcc_Employees")
	private String bccEmployees;

	@JsonProperty("bcc_MailId")
	private String bccMailId;

	@JsonProperty("sms_to_Roles")
	private String smsToRoles;

	@JsonProperty("sms_to_Employees")
	private String smsToEmployees;

	@JsonProperty("sms_to_Nos")
	private String smsToNos;

	private String mailVmPath;

	private String smsVmPath;

	private String activeYn;
	
	private String mailBody;
	
	private String smsBody;

	private Integer updaterRole;
	
	private String ipAddress;
	
	private Integer programeCode;
	
	@JsonProperty("to_Party")
	private Boolean toParty;

	@JsonProperty("cc_Party")
	private Boolean ccParty;
	
	@JsonProperty("bcc_Party")
	private Boolean bccParty;

	@JsonProperty("to_NextUpdator")
	private Boolean toNextUpdator;

	@JsonProperty("cc_NextUpdator")
	private Boolean ccNextUpdator;

	@JsonProperty("bcc_NextUpdator")
	private Boolean bccNextUpdator;
	
	private String attachUploadedDocsYn;
	
	private String mailSubject;
	
	private String isToFetchTransDoc;
	
	private String isToAttachReport;
	
	private Integer  fileUploadSrNo;
	
	private String  attachedDocSrNo;
	
	private String [][] filterCondition;
	
	private String subScreenName;
	
	@JsonProperty("sms_to_Party")
	private Boolean smsToParty;
	
	@JsonProperty("sms_to_NextUpdator")
	private Boolean smsToNextUpdator;
	
	@JsonProperty("cron_data_string")
	private String scheduledCronDataString;
	
	private Integer active;
	
	/**
	 * @return the conditionType
	 */
	public String getConditionType() {
		return conditionType;
	}

	/**
	 * @param conditionType the conditionType to set
	 */
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	/**
	 * @return the notiEventSrNo
	 */
	public Integer getNotiEventSrNo() {
		return notiEventSrNo;
	}

	/**
	 * @param notiEventSrNo the notiEventSrNo to set
	 */
	public void setNotiEventSrNo(Integer notiEventSrNo) {
		this.notiEventSrNo = notiEventSrNo;
	}

	/**
	 * @return the companyCode
	 */
	public Integer getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(Integer companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the divisionCode
	 */
	public Integer getDivisionCode() {
		return divisionCode;
	}

	/**
	 * @param divisionCode the divisionCode to set
	 */
	public void setDivisionCode(Integer divisionCode) {
		this.divisionCode = divisionCode;
	}
	
	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * @return the mtqrFlag
	 */
	public String getMtqrFlag() {
		return mtqrFlag;
	}

	/**
	 * @param mtqrFlag the mtqrFlag to set
	 */
	public void setMtqrFlag(String mtqrFlag) {
		this.mtqrFlag = mtqrFlag;
	}

	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * @return the programName
	 */
	public String getProgramName() {
		return programName;
	}

	/**
	 * @param programName the programName to set
	 */
	public void setProgramName(String programName) {
		this.programName = programName;
	}

	/**
	 * @return the scheduledYn
	 */
	public String getScheduledYn() {
		return scheduledYn;
	}

	/**
	 * @param scheduledYn the scheduledYn to set
	 */
	public void setScheduledYn(String scheduledYn) {
		this.scheduledYn = scheduledYn;
	}

	/**
	 * @return the scheduledCronExp
	 */
	public String getScheduledCronExp() {
		return scheduledCronExp;
	}

	/**
	 * @param scheduledCronExp the scheduledCronExp to set
	 */
	public void setScheduledCronExp(String scheduledCronExp) {
		this.scheduledCronExp = scheduledCronExp;
	}

	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * @return the toRoles
	 */
	@JsonProperty("to_Roles")
	public String getToRoles() {
		return toRoles;
	}

	/**
	 * @param toRoles the toRoles to set
	 */
	@JsonProperty("to_Roles")
	public void setToRoles(String toRoles) {
		this.toRoles = toRoles;
	}

	/**
	 * @return the toEmployees
	 */
	@JsonProperty("to_Employees")
	public String getToEmployees() {
		return toEmployees;
	}

	/**
	 * @param toEmployees the toEmployees to set
	 */
	@JsonProperty("to_Employees")
	public void setToEmployees(String toEmployees) {
		this.toEmployees = toEmployees;
	}

	/**
	 * @return the toMailId
	 */
	@JsonProperty("to_MailId")
	public String getToMailId() {
		return toMailId;
	}

	/**
	 * @param toMailId the toMailId to set
	 */
	@JsonProperty("to_MailId")
	public void setToMailId(String toMailId) {
		this.toMailId = toMailId;
	}

	/**
	 * @return the ccRoles
	 */
	@JsonProperty("cc_Roles")
	public String getCcRoles() {
		return ccRoles;
	}

	/**
	 * @param ccRoles the ccRoles to set
	 */
	@JsonProperty("cc_Roles")
	public void setCcRoles(String ccRoles) {
		this.ccRoles = ccRoles;
	}

	/**
	 * @return the ccEmployees
	 */
	@JsonProperty("cc_Employees")
	public String getCcEmployees() {
		return ccEmployees;
	}

	/**
	 * @param ccEmployees the ccEmployees to set
	 */
	@JsonProperty("cc_Employees")
	public void setCcEmployees(String ccEmployees) {
		this.ccEmployees = ccEmployees;
	}

	/**
	 * @return the ccMailId
	 */
	@JsonProperty("cc_MailId")
	public String getCcMailId() {
		return ccMailId;
	}

	/**
	 * @param ccMailId the ccMailId to set
	 */
	@JsonProperty("cc_MailId")
	public void setCcMailId(String ccMailId) {
		this.ccMailId = ccMailId;
	}

	/**
	 * @return the bccRole
	 */
	@JsonProperty("bcc_Roles")
	public String getBccRoles() {
		return bccRoles;
	}

	/**
	 * @param bccRole the bccRole to set
	 */
	@JsonProperty("bcc_Roles")
	public void setBccRoles(String bccRoles) {
		this.bccRoles = bccRoles;
	}

	/**
	 * @return the bccEmployees
	 */
	@JsonProperty("bcc_Employees")
	public String getBccEmployees() {
		return bccEmployees;
	}

	/**
	 * @param bccEmployees the bccEmployees to set
	 */
	@JsonProperty("bcc_Employees")
	public void setBccEmployees(String bccEmployees) {
		this.bccEmployees = bccEmployees;
	}

	/**
	 * @return the bccMailId
	 */
	@JsonProperty("bcc_MailId")
	public String getBccMailId() {
		return bccMailId;
	}

	/**
	 * @param bccMailId the bccMailId to set
	 */
	@JsonProperty("bcc_MailId")
	public void setBccMailId(String bccMailId) {
		this.bccMailId = bccMailId;
	}

	/**
	 * @return the smsToRoles
	 */
	@JsonProperty("sms_to_Roles")
	public String getSmsToRoles() {
		return smsToRoles;
	}

	/**
	 * @param smsToRoles the smsToRoles to set
	 */
	@JsonProperty("sms_to_Roles")
	public void setSmsToRoles(String smsToRoles) {
		this.smsToRoles = smsToRoles;
	}

	/**
	 * @return the smsToEmployees
	 */
	@JsonProperty("sms_to_Employees")
	public String getSmsToEmployees() {
		return smsToEmployees;
	}

	/**
	 * @param smsToEmployees the smsToEmployees to set
	 */
	@JsonProperty("sms_to_Employees")
	public void setSmsToEmployees(String smsToEmployees) {
		this.smsToEmployees = smsToEmployees;
	}

	/**
	 * @return the smsToNos
	 */
	@JsonProperty("sms_to_Nos")
	public String getSmsToNos() {
		return smsToNos;
	}

	/**
	 * @param smsToNos the smsToNos to set
	 */
	@JsonProperty("sms_to_Nos")
	public void setSmsToNos(String smsToNos) {
		this.smsToNos = smsToNos;
	}

	/**
	 * @return the mailVmPath
	 */
	public String getMailVmPath() {
		return mailVmPath;
	}

	/**
	 * @param mailVmPath the mailVmPath to set
	 */
	public void setMailVmPath(String mailVmPath) {
		this.mailVmPath = mailVmPath;
	}

	/**
	 * @return the smsVmPath
	 */
	public String getSmsVmPath() {
		return smsVmPath;
	}

	/**
	 * @param smsVmPath the smsVmPath to set
	 */
	public void setSmsVmPath(String smsVmPath) {
		this.smsVmPath = smsVmPath;
	}

	/**
	 * @return the activeYn
	 */
	public String getActiveYn() {
		return activeYn;
	}

	/**
	 * @param activeYn the activeYn to set
	 */
	public void setActiveYn(String activeYn) {
		this.activeYn = activeYn;
	}
	
	/**
	 * @return the mailBody
	 */
	public String getMailBody() {
		return mailBody;
	}

	/**
	 * @param mailBody the mailBody to set
	 */
	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}

	/**
	 * @return the smsBody
	 */
	public String getSmsBody() {
		return smsBody;
	}

	/**
	 * @param smsBody the smsBody to set
	 */
	public void setSmsBody(String smsBody) {
		this.smsBody = smsBody;
	}

	/**
	 * @return the updaterRole
	 */
	public Integer getUpdaterRole() {
		return updaterRole;
	}

	/**
	 * @param updaterRole the updaterRole to set
	 */
	public void setUpdaterRole(Integer updaterRole) {
		this.updaterRole = updaterRole;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the programeCode
	 */
	public Integer getProgrameCode() {
		return programeCode;
	}

	/**
	 * @param programeCode the programeCode to set
	 */
	public void setProgrameCode(Integer programeCode) {
		this.programeCode = programeCode;
	}

	/**
	 * @return the toParty
	 */
	@JsonProperty("to_Party")
	public Boolean getToParty() {
		return toParty;
	}

	/**
	 * @param toParty the toParty to set
	 */
	@JsonProperty("to_Party")
	public void setToParty(Boolean toParty) {
		this.toParty = toParty;
	}

	/**
	 * @return the ccParty
	 */
	@JsonProperty("cc_Party")
	public Boolean getCcParty() {
		return ccParty;
	}

	/**
	 * @param ccParty the ccParty to set
	 */
	@JsonProperty("cc_Party")
	public void setCcParty(Boolean ccParty) {
		this.ccParty = ccParty;
	}

	/**
	 * @return the bccParty
	 */
	@JsonProperty("bcc_Party")
	public Boolean getBccParty() {
		return bccParty;
	}

	/**
	 * @param bccParty the bccParty to set
	 */
	@JsonProperty("bcc_Party")
	public void setBccParty(Boolean bccParty) {
		this.bccParty = bccParty;
	}

	/**
	 * @return the toNextUpdator
	 */
	@JsonProperty("to_NextUpdator")
	public Boolean getToNextUpdator() {
		return toNextUpdator;
	}

	/**
	 * @param toNextUpdator the toNextUpdator to set
	 */
	@JsonProperty("to_NextUpdator")
	public void setToNextUpdator(Boolean toNextUpdator) {
		this.toNextUpdator = toNextUpdator;
	}

	/**
	 * @return the ccNextUpdator
	 */
	@JsonProperty("cc_NextUpdator")
	public Boolean getCcNextUpdator() {
		return ccNextUpdator;
	}

	/**
	 * @param ccNextUpdator the ccNextUpdator to set
	 */
	@JsonProperty("cc_NextUpdator")
	public void setCcNextUpdator(Boolean ccNextUpdator) {
		this.ccNextUpdator = ccNextUpdator;
	}

	/**
	 * @return the bccNextUpdator
	 */
	@JsonProperty("bcc_NextUpdator")
	public Boolean getBccNextUpdator() {
		return bccNextUpdator;
	}

	/**
	 * @param bccNextUpdator the bccNextUpdator to set
	 */
	@JsonProperty("bcc_NextUpdator")
	public void setBccNextUpdator(Boolean bccNextUpdator) {
		this.bccNextUpdator = bccNextUpdator;
	}

	/**
	 * @return the attachUploadedDocsYn
	 */
	public String getAttachUploadedDocsYn() {
		return attachUploadedDocsYn;
	}

	/**
	 * @param attachUploadedDocsYn the attachUploadedDocsYn to set
	 */
	public void setAttachUploadedDocsYn(String attachUploadedDocsYn) {
		this.attachUploadedDocsYn = attachUploadedDocsYn;
	}

	/**
	 * @return the mailSubject
	 */
	public String getMailSubject() {
		return mailSubject;
	}

	/**
	 * @param mailSubject the mailSubject to set
	 */
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	/**
	 * @return the isToFetchTransDoc
	 */
	public String getIsToFetchTransDoc() {
		return isToFetchTransDoc;
	}

	/**
	 * @param isToFetchTransDoc the isToFetchTransDoc to set
	 */
	public void setIsToFetchTransDoc(String isToFetchTransDoc) {
		this.isToFetchTransDoc = isToFetchTransDoc;
	}
	
	/**
	 * @return the isToAttachReport
	 */
	public String getIsToAttachReport() {
		return isToAttachReport;
	}

	/**
	 * @param isToAttachReport the isToAttachReport to set
	 */
	public void setIsToAttachReport(String isToAttachReport) {
		this.isToAttachReport = isToAttachReport;
	}

	/**
	 * @return the fileUploadSrNo
	 */
	public Integer getFileUploadSrNo() {
		return fileUploadSrNo;
	}

	/**
	 * @param fileUploadSrNo the fileUploadSrNo to set
	 */
	public void setFileUploadSrNo(Integer fileUploadSrNo) {
		this.fileUploadSrNo = fileUploadSrNo;
	}

	/**
	 * @return the attachedDocSrNo
	 */
	public String getAttachedDocSrNo() {
		return attachedDocSrNo;
	}

	/**
	 * @param attachedDocSrNo the attachedDocSrNo to set
	 */
	public void setAttachedDocSrNo(String attachedDocSrNo) {
		this.attachedDocSrNo = attachedDocSrNo;
	}

	public String[][] getFilterCondition() {
		return filterCondition;
	}

	public void setFilterCondition(String[][] filterCondition) {
		this.filterCondition = filterCondition;
	}

	/**
	 * @return the subScreenName
	 */
	public String getSubScreenName() {
		return subScreenName;
	}

	/**
	 * @param subScreenName the subScreenName to set
	 */
	public void setSubScreenName(String subScreenName) {
		this.subScreenName = subScreenName;
	}

	/**
	 * @return the smsToParty
	 */
	@JsonProperty("sms_to_Party")
	public Boolean getSmsToParty() {
		return smsToParty;
	}

	/**
	 * @param smsToParty the smsToParty to set
	 */
	@JsonProperty("sms_to_Party")
	public void setSmsToParty(Boolean smsToParty) {
		this.smsToParty = smsToParty;
	}

	/**
	 * @return the smsToNextUpdator
	 */
	@JsonProperty("sms_to_NextUpdator")
	public Boolean getSmsToNextUpdator() {
		return smsToNextUpdator;
	}

	/**
	 * @param smsToNextUpdator the smsToNextUpdator to set
	 */
	@JsonProperty("sms_to_NextUpdator")
	public void setSmsToNextUpdator(Boolean smsToNextUpdator) {
		this.smsToNextUpdator = smsToNextUpdator;
	}

	@JsonProperty("cron_data_string")
	public String getScheduledCronDataString() {
		return scheduledCronDataString;
	}

	@JsonProperty("cron_data_string")
	public void setScheduledCronDataString(String scheduledCronDataString) {
		this.scheduledCronDataString = scheduledCronDataString;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}
	
}
