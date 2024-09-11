package com.epps.framework.notification.mail.application.event.entities;

import java.io.File;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.epps.framework.infrastructure.model.entities.EppsBaseEntity;

@Entity
@Table(name="epps_notification_preset_events",schema="epps_admin")
@DynamicInsert @DynamicUpdate
public class EppsNotificationPresetEvents extends EppsBaseEntity  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5957775966363299314L;

	private Integer notiEventSrNo;
	
	private Integer companyCode;

	private Integer divisionCode;
	
	private String eventName;
	
	private String toRoles;

	private String toEmployees;

	private String toMailId;

	private Integer activeYn;

	private String mailSubject;
	
	private String mailContent;
	
	private String eventProcName;
	
	private String procParams;
	
	private Set<EppsNotificationDictionaryHdr> eppsPredefinedNotificationDictionaryHdrs;
	
	private File excelFile;
	
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
	 * @return the activeYn
	 */
	@Column(name = "active_yn")
	public Integer getActiveYn() {
		return activeYn;
	}

	/**
	 * @param activeYn the activeYn to set
	 */
	public void setActiveYn(Integer activeYn) {
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
	 * @return the mailContent
	 */
	@Column(name = "mail_content")
	public String getMailContent() {
		return mailContent;
	}

	/**
	 * @param mailContent the mailContent to set
	 */
	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	/**
	 * @param mailSubject the mailSubject to set
	 */
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	@Column(name = "procedure_name")
	public String getEventProcName() {
		return eventProcName;
	}

	public void setEventProcName(String eventProcName) {
		this.eventProcName = eventProcName;
	}
	
	/**
	 * @return the procParams
	 */
	@Column(name = "procedure_params")
	public String getProcParams() {
		return procParams;
	}

	/**
	 * @param procParams the procParams to set
	 */
	public void setProcParams(String procParams) {
		this.procParams = procParams;
	}

	/**
	 * @return the eppsPredefinedNotificationDictionaryHdrs
	 */
	@OneToMany(mappedBy = "eppsNotificationPresetEvents",fetch=FetchType.LAZY,orphanRemoval=true)
	@Cascade(CascadeType.ALL)
	public Set<EppsNotificationDictionaryHdr> getEppsPredefinedNotificationDictionaryHdrs() {
		return eppsPredefinedNotificationDictionaryHdrs;
	}

	/**
	 * @param eppsPredefinedNotificationDictionaryHdrs the eppsPredefinedNotificationDictionaryHdrs to set
	 */
	public void setEppsPredefinedNotificationDictionaryHdrs(
			Set<EppsNotificationDictionaryHdr> eppsPredefinedNotificationDictionaryHdrs) {
		this.eppsPredefinedNotificationDictionaryHdrs = eppsPredefinedNotificationDictionaryHdrs;
	}

	/**
	 * @return the excelFile
	 */
	@Transient
	public File getExcelFile() {
		return excelFile;
	}

	/**
	 * @param excelFile the excelFile to set
	 */
	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}
}
