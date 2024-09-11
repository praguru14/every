package com.epps.framework.notification.mail.application.event.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.epps.framework.infrastructure.model.entities.EppsBaseEntity;

@Entity
@Table(name="epps_notification_events",schema="epps_admin")
@DynamicInsert @DynamicUpdate
public class EppsNotificationEvents extends EppsBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5957775966363299314L;

	private Integer notiEventSrNo;
	
	private Integer companyCode;

	private Integer divisionCode;
	
	private String eventName;
	
	private String mtqrFlag;

	private String pid;

	private String scheduledYn;

	private String scheduledCronExp;

	private String condition;

	private String toRoles;

	private String toEmployees;

	private String toMailId;

	private String ccRoles;

	private String ccEmployees;

	private String ccMailId;

	private String bccRoles;

	private String bccEmployees;

	private String bccMailId;

	private String smsToRoles;

	private String smsToEmployees;

	private String smsToNos;

	private String mailVmPath;

	private String smsVmPath;

	private String activeYn;

	private String mailSubject;
	
	private String isToFetchTransDoc;
	
	private String isToAttachReport;
	
	private Integer  fileUploadSrNo;
	
	private Integer programeCode;
	
	private String partyIn;
	
	private String nextUpdatorIn;
	
	private String eventProcName;
	
	private EppsProgramMaster eppsProgramMaster; 
	
	private Set<EppsNotificationDictionaryHdr> eppsNotificationDictionaryHdrs;
	
	private String conditionType;
	
	private String subScreenName;
	
	private Boolean smsToParty;
	
	private Boolean smsToNextUpdator;
	
	private String scheduledCronDataString;

	/**
	 * @return the notiEventSrNo
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="noti_event_sr_no")
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
	@Column(name = "comp_cd")
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
	@Column(name = "div_cd")
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
	@Column(name = "event_name")
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
	@Column(name = "mtqr_flag")
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
	@Column(name = "pid")
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
	 * @return the scheduledYn
	 */
	@Column(name = "scheduled_yn")
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
	@Column(name = "scheduled_cron_exp")
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
	@Column(name = "condition")
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
	@Column(name = "to_roles")
	public String getToRoles() {
		return toRoles;
	}

	/**
	 * @param toRoles the toRoles to set
	 */
	public void setToRoles(String toRoles) {
		this.toRoles = toRoles;
	}

	/**
	 * @return the toEmployees
	 */
	@Column(name = "to_employees")
	public String getToEmployees() {
		return toEmployees;
	}

	/**
	 * @param toEmployees the toEmployees to set
	 */
	public void setToEmployees(String toEmployees) {
		this.toEmployees = toEmployees;
	}

	/**
	 * @return the toMailId
	 */
	@Column(name = "to_mailid")
	public String getToMailId() {
		return toMailId;
	}

	/**
	 * @param toMailId the toMailId to set
	 */
	public void setToMailId(String toMailId) {
		this.toMailId = toMailId;
	}

	/**
	 * @return the ccRoles
	 */
	@Column(name = "cc_roles")
	public String getCcRoles() {
		return ccRoles;
	}

	/**
	 * @param ccRoles the ccRoles to set
	 */
	public void setCcRoles(String ccRoles) {
		this.ccRoles = ccRoles;
	}

	/**
	 * @return the ccEmployees
	 */
	@Column(name = "cc_employes")
	public String getCcEmployees() {
		return ccEmployees;
	}

	/**
	 * @param ccEmployees the ccEmployees to set
	 */
	public void setCcEmployees(String ccEmployees) {
		this.ccEmployees = ccEmployees;
	}

	/**
	 * @return the ccMailId
	 */
	@Column(name = "cc_mailid")
	public String getCcMailId() {
		return ccMailId;
	}

	/**
	 * @param ccMailId the ccMailId to set
	 */
	public void setCcMailId(String ccMailId) {
		this.ccMailId = ccMailId;
	}

	/**
	 * @return the bccRole
	 */
	@Column(name = "bcc_role")
	public String getBccRoles() {
		return bccRoles;
	}

	/**
	 * @param bccRole the bccRole to set
	 */
	public void setBccRoles(String bccRoles) {
		this.bccRoles = bccRoles;
	}

	
	/**
	 * @return the bccEmployees
	 */
	@Column(name = "bcc_employe")
	public String getBccEmployees() {
		return bccEmployees;
	}

	/**
	 * @param bccEmployees the bccEmployees to set
	 */
	public void setBccEmployees(String bccEmployees) {
		this.bccEmployees = bccEmployees;
	};

	/**
	 * @return the bccMailId
	 */
	@Column(name = "bcc_mailid")
	public String getBccMailId() {
		return bccMailId;
	}

	/**
	 * @param bccMailId the bccMailId to set
	 */
	public void setBccMailId(String bccMailId) {
		this.bccMailId = bccMailId;
	}

	/**
	 * @return the smsToRoles
	 */
	@Column(name = "sms_to_roles")
	public String getSmsToRoles() {
		return smsToRoles;
	}

	/**
	 * @param smsToRoles the smsToRoles to set
	 */
	public void setSmsToRoles(String smsToRoles) {
		this.smsToRoles = smsToRoles;
	}

	/**
	 * @return the smsToEmployees
	 */
	@Column(name = "sms_to_employees")
	public String getSmsToEmployees() {
		return smsToEmployees;
	}

	/**
	 * @param smsToEmployees the smsToEmployees to set
	 */
	public void setSmsToEmployees(String smsToEmployees) {
		this.smsToEmployees = smsToEmployees;
	}

	/**
	 * @return the smsToNos
	 */
	@Column(name = "sms_to_nos")
	public String getSmsToNos() {
		return smsToNos;
	}

	/**
	 * @param smsToNos the smsToNos to set
	 */
	public void setSmsToNos(String smsToNos) {
		this.smsToNos = smsToNos;
	}

	/**
	 * @return the mailVmPath
	 */
	@Column(name = "mail_vm_path")
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
	@Column(name = "sms_vm_path")
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
	@Column(name = "active_yn")
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
	 * @return the mailSubject
	 */
	@Column(name = "mail_sub")
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
	@Column(name = "is_to_fetch_trans_doc")
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
	@Column(name = "is_to_attach_report")
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
	 * @return the attachedDocSrNo
	 */
	@Column(name = "attached_doc_sr_no")
	public Integer getFileUploadSrNo() {
		return fileUploadSrNo;
	}

	/**
	 * @param attachedDocSrNo the attachedDocSrNo to set
	 */
	public void setFileUploadSrNo(Integer fileUploadSrNo) {
		this.fileUploadSrNo = fileUploadSrNo;
	}

	/**
	 * @return the programeCode
	 */
	@Column(name = "prog_cd")
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
	 * @return the partyIn
	 */
	@Column(name = "party_in")
	public String getPartyIn() {
		return partyIn;
	}

	/**
	 * @param partyIn the partyIn to set
	 */
	public void setPartyIn(String partyIn) {
		this.partyIn = partyIn;
	}

	/**
	 * @return the nextUpdatorIn
	 */
	@Column(name = "next_updator_in")
	public String getNextUpdatorIn() {
		return nextUpdatorIn;
	}

	/**
	 * @param nextUpdatorIn the nextUpdatorIn to set
	 */
	public void setNextUpdatorIn(String nextUpdatorIn) {
		this.nextUpdatorIn = nextUpdatorIn;
	}
	
	/**
	 * @return the eppsProgramMaster
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@Cascade (value=CascadeType.SAVE_UPDATE)
	@JoinColumns({
		@JoinColumn(name="comp_cd",insertable= false,updatable=false),
		@JoinColumn(name="prog_cd",insertable= false,updatable=false)
	})
	public EppsProgramMaster getEppsProgramMaster() {
		return eppsProgramMaster;
	}

	/**
	 * @param eppsProgramMaster the eppsProgramMaster to set
	 */
	public void setEppsProgramMaster(EppsProgramMaster eppsProgramMaster) {
		this.eppsProgramMaster = eppsProgramMaster;
	}

	/**
	 * @return the eppsNotificationDictionaryHdrs
	 */
	@OneToMany(mappedBy = "eppsNotificationEvents",fetch=FetchType.LAZY,orphanRemoval=true)
	@Cascade(CascadeType.ALL)
	public Set<EppsNotificationDictionaryHdr> getEppsNotificationDictionaryHdrs() {
		return eppsNotificationDictionaryHdrs;
	}

	/**
	 * @param eppsNotificationDictionaryHdrs the eppsNotificationDictionaryHdrs to set
	 */
	public void setEppsNotificationDictionaryHdrs(Set<EppsNotificationDictionaryHdr> eppsNotificationDictionaryHdrs) {
		this.eppsNotificationDictionaryHdrs = eppsNotificationDictionaryHdrs;
	}

	@Column(name = "event_proc_name")
	public String getEventProcName() {
		return eventProcName;
	}

	public void setEventProcName(String eventProcName) {
		this.eventProcName = eventProcName;
	}

	/**
	 * @return the conditionType
	 */
	@Column(name="condition_type")
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
	 * @return the subScreenName
	 */
	@Column(name="sub_screen_name",length=20)
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
	@Column(name="sms_to_party")
	public Boolean getSmsToParty() {
		return smsToParty;
	}

	/**
	 * @param smsToParty the smsToParty to set
	 */
	public void setSmsToParty(Boolean smsToParty) {
		this.smsToParty = smsToParty;
	}

	/**
	 * @return the smsToNextUpdator
	 */
	@Column(name="sms_to_nextupdator")
	public Boolean getSmsToNextUpdator() {
		return smsToNextUpdator;
	}

	/**
	 * @param smsToNextUpdator the smsToNextUpdator to set
	 */
	public void setSmsToNextUpdator(Boolean smsToNextUpdator) {
		this.smsToNextUpdator = smsToNextUpdator;
	}

	@Column(name="cron_data_string")
	public String getScheduledCronDataString() {
		return scheduledCronDataString;
	}

	public void setScheduledCronDataString(String scheduledCronDataString) {
		this.scheduledCronDataString = scheduledCronDataString;
	}
	
}
