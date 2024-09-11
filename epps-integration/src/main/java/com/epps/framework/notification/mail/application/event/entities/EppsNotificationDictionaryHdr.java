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

@Entity
@Table(name="notification_dictionary_hdr",schema="epps_admin")
public class EppsNotificationDictionaryHdr {

	private Integer dictionartyHdrSrNo;
	
	private String columnName;
	
	private String columnDispName;
	
	private String columnType;
	
	private String headerOrDetail;
	
	private Integer displayOrder;
	
	private String notiType;
	
	private EppsNotificationEvents eppsNotificationEvents;
	
	private EppsNotificationPresetEvents eppsNotificationPresetEvents;
	

	/**
	 * @return the dictionartyHdrSrNo
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="dict_hdr_sr_no",nullable=false)
	public Integer getDictionartyHdrSrNo() {
		return dictionartyHdrSrNo;
	}

	/**
	 * @param dictionartyHdrSrNo the dictionartyHdrSrNo to set
	 */
	public void setDictionartyHdrSrNo(Integer dictionartyHdrSrNo) {
		this.dictionartyHdrSrNo = dictionartyHdrSrNo;
	}

	/**
	 * @return the columnName
	 */
	@Column(name="column_name")
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * @return the columnDispName
	 */
	@Column(name="column_disp_name")
	public String getColumnDispName() {
		return columnDispName;
	}

	/**
	 * @param columnDispName the columnDispName to set
	 */
	public void setColumnDispName(String columnDispName) {
		this.columnDispName = columnDispName;
	}

	/**
	 * @return the columnType
	 */
	@Column(name="column_type")
	public String getColumnType() {
		return columnType;
	}

	/**
	 * @param columnType the columnType to set
	 */
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
	

	/**
	 * @return the headerOrDetail
	 */
	@Column(name="hdr_or_dtl")
	public String getHeaderOrDetail() {
		return headerOrDetail;
	}

	/**
	 * @param headerOrDetail the headerOrDetail to set
	 */
	public void setHeaderOrDetail(String headerOrDetail) {
		this.headerOrDetail = headerOrDetail;
	}

	/**
	 * @return the eppsNotificationEvents
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@Cascade (value=CascadeType.SAVE_UPDATE)
	@JoinColumn(name="event_sr_no",nullable = false)
	public EppsNotificationEvents getEppsNotificationEvents() {
		return eppsNotificationEvents;
	}

	/**
	 * @param eppsNotificationEvents the eppsNotificationEvents to set
	 */
	public void setEppsNotificationEvents(EppsNotificationEvents eppsNotificationEvents) {
		this.eppsNotificationEvents = eppsNotificationEvents;
	}

	@Column(name="disp_order")
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * @return the notiType
	 */
	@Column(name="noti_type")
	public String getNotiType() {
		return notiType;
	}

	/**
	 * @param notiType the notiType to set
	 */
	public void setNotiType(String notiType) {
		this.notiType = notiType;
	}

	/**
	 * @return the eppsNotificationPresetEvents
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@Cascade (value=CascadeType.SAVE_UPDATE)
	@JoinColumn(name="pre_noti_event_sr_no",nullable = false)
	public EppsNotificationPresetEvents getEppsNotificationPresetEvents() {
		return eppsNotificationPresetEvents;
	}

	/**
	 * @param eppsNotificationPresetEvents the eppsNotificationPresetEvents to set
	 */
	public void setEppsNotificationPresetEvents(
			EppsNotificationPresetEvents eppsNotificationPresetEvents) {
		this.eppsNotificationPresetEvents = eppsNotificationPresetEvents;
	}
	
	
}
