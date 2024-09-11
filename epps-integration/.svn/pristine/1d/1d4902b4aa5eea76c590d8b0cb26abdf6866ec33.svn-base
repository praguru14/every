package com.epps.framework.infrastructure.model.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.epps.framework.notification.mail.application.event.annotation.EventAttribute;

@MappedSuperclass
public class CreatorBaseEntity implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * Represents the user who has created the record of particular entity.
	 */
	@EventAttribute(displayName = "Created By",purpose="B")
	private String createdBy;
	
	/**
	 * Represents the time on which the particular record is created.
	 */

	private Date createdDate;
	
	private String ipAddress;
	

	private Integer creatorRole;
	
	
	/**
	 * @return the createdBy
	 */
	@Column(name = "created_by", nullable = false)
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
	@Column(name = "created_dt", nullable = false)
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
	@Column(name = "terminal_id", nullable = false,length=100)
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
	@Column(name = "creator_role_cd", nullable = false)
	public Integer getCreatorRole() {
		return creatorRole;
	}

	/**
	 * @param updatorRole the updatorRole to set
	 */
	public void setCreatorRole(Integer updatorRole) {
		this.creatorRole = updatorRole;
	}

}

