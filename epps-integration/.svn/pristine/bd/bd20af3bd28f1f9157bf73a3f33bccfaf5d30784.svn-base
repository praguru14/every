package com.epps.framework.notification.mail.application.event.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.epps.framework.infrastructure.model.entities.EppsBaseEntity;

@Entity
@Table(name="epps_info_notification_hdr",schema="epps_admin")
@DynamicInsert @DynamicUpdate
public class EppsNotificationInfoHeader extends EppsBaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Integer notiHdrSrNo;
	
	private Integer companyCode;
	
	private Integer divisionCode;
	
	private Integer locationCode;
	
	private Date postedOn;
	
	private String notiHeader;
	
	private String notiString;
	
	private String activeYn;
	
	private String notiSend;
	
	
	private Set<EppsNotificationInfoDtl> eppsNotiInfoDtl;
	
	//private EppsLocationMaster eppsLocationMaster;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="noti_hdr_sr_no",nullable=false)
	public Integer getNotiHdrSrNo() {
		return notiHdrSrNo;
	}

	public void setNotiHdrSrNo(Integer notiHdrSrNo) {
		this.notiHdrSrNo = notiHdrSrNo;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="posted_on_dt")
	public Date getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Column(name="noti_header",nullable=false)
	public String getNotiHeader() {
		return notiHeader;
	}

	public void setNotiHeader(String notiHeader) {
		this.notiHeader = notiHeader;
	}

	@Column(name="noti_string",nullable=false)
	public String getNotiString() {
		return notiString;
	}

	public void setNotiString(String notiString) {
		this.notiString = notiString;
	}
	
	@Column(name="active_yn")
	public String getActiveYn() {
		return activeYn;
	}

	public void setActiveYn(String activeYn) {
		this.activeYn = activeYn;
	}


	@OneToMany(mappedBy = "eppsNotificationInfoHeader",fetch=FetchType.LAZY,orphanRemoval=true)
	@Cascade(CascadeType.ALL)
	public Set<EppsNotificationInfoDtl> getEppsNotiInfoDtl() {
		return eppsNotiInfoDtl;
	}

	public void setEppsNotiInfoDtl(Set<EppsNotificationInfoDtl> eppsNotiInfoDtl) {
		this.eppsNotiInfoDtl = eppsNotiInfoDtl;
	}
	
	@Column(name="noti_sent")
	public String getNotiSend() {
		return notiSend;
	}

	public void setNotiSend(String notiSend) {
		this.notiSend = notiSend;
	}
	
	/**
	@ManyToOne(fetch=FetchType.LAZY)
	@Cascade (value=CascadeType.SAVE_UPDATE)
	@JoinColumns({
		@JoinColumn(name="comp_cd",referencedColumnName="comp_cd",insertable=false,updatable=false),
		@JoinColumn(name="div_cd",referencedColumnName="div_cd",insertable=false,updatable=false),
		@JoinColumn(name="loc_cd",referencedColumnName="loc_cd",insertable=false,updatable=false)
		})
	public EppsLocationMaster getEppsLocationMaster() {
		return eppsLocationMaster;
	}

	public void setEppsLocationMaster(EppsLocationMaster eppsLocationMaster) {
		this.eppsLocationMaster = eppsLocationMaster;
	}
	*/
}
