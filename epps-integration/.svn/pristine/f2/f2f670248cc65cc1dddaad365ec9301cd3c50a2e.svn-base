package com.epps.framework.infrastructure.model.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.epps.framework.notification.mail.application.event.annotation.EventAttribute;

@MappedSuperclass
public class EppsBaseEntity implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * Represents the user who has created the record of particular entity.
	 */
	@EventAttribute(displayName = "Created By",purpose="B")
	private String createdBy;
	
	/**
	 * Represents the user who has updated the record of particular entity.
	 */
	
	private String updatedBy;
	
	/**
	 * Represents the time on which the particular record is created.
	 */

	private Date createdDate;
	
	/**
	 * Represents the time on which the particular record is modified.
	 */

	private Date updatedDate;
	

	private String ipAddress;
	

	private Integer creatorRole;
	

	private Integer updatorRole;

	/**
	 * @return the createdBy
	 */
	@Column(name = "created_by", nullable = true)
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	

	/**
	 * @return the createdDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_dt", nullable = true)
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the ipAddress
	 */
	@Column(name = "terminal_id", nullable = true,length=100)
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
	 * @return the updatorRole
	 */
	@Column(name = "creator_role_cd", nullable = true)
	public Integer getCreatorRole() {
		return creatorRole;
	}

	/**
	 * @param updatorRole the updatorRole to set
	 */
	public void setCreatorRole(Integer updatorRole) {
		this.creatorRole = updatorRole;
	}

	/**
	 * @return the updatorRole
	 */
	@Column(name = "updator_role_cd", nullable = true)	
	public Integer getUpdatorRole() {
		return updatorRole;
	}

	/**
	 * @param updatorRole the updatorRole to set
	 */
	public void setUpdatorRole(Integer updatorRole) {
		this.updatorRole = updatorRole;
	}

	@Column(name = "updated_by", nullable = true)
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_dt", nullable = true)
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	
}