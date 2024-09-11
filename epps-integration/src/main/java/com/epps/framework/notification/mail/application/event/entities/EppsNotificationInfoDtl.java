package com.epps.framework.notification.mail.application.event.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.epps.framework.infrastructure.model.entities.EppsBaseEntity;

@Entity
@Table(name="epps_info_notification_dtl",schema="epps_admin")
@DynamicInsert @DynamicUpdate
public class EppsNotificationInfoDtl extends EppsBaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Integer notiDtlSrNo;
	
	private Integer companyCode;
	
	private Integer divisionCode;
	
	private Integer locationCode;
	
	private Integer roleCode;
	
	private String activeYn;
	
	private EppsNotificationInfoHeader eppsNotificationInfoHeader;
	 
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="noti_dtl_sr_no",nullable=false)
	public Integer getNotiDtlSrNo() {
		return notiDtlSrNo;
	}

	public void setNotiDtlSrNo(Integer notiDtlSrNo) {
		this.notiDtlSrNo = notiDtlSrNo;
	}
	
	@Column(name="role_cd",nullable=false)
	public Integer getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(Integer roleCode) {
		this.roleCode = roleCode;
	}

	@Column(name="comp_cd",nullable=false)
	public Integer getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(Integer companyCode) {
		this.companyCode = companyCode;
	}

	@Column(name="div_cd",nullable=false)
	public Integer getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(Integer divisionCode) {
		this.divisionCode = divisionCode;
	}

	@Column(name="loc_cd",nullable=false)
	public Integer getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(Integer locationCode) {
		this.locationCode = locationCode;
	}

	
	@Column(name="active_yn")
	public String getActiveYn() {
		return activeYn;
	}

	public void setActiveYn(String activeYn) {
		this.activeYn = activeYn;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@Cascade (value=CascadeType.SAVE_UPDATE)
	@JoinColumn(name="noti_hdr_sr_no",referencedColumnName="noti_hdr_sr_no",insertable=false,updatable=false)
	public EppsNotificationInfoHeader getEppsNotificationInfoHeader() {
		return eppsNotificationInfoHeader;
	}

	public void setEppsNotificationInfoHeader(
			EppsNotificationInfoHeader eppsNotificationInfoHeader) {
		this.eppsNotificationInfoHeader = eppsNotificationInfoHeader;
	}

}
