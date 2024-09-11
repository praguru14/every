package com.epps.framework.notification.mail.application.event.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.epps.framework.infrastructure.model.entities.BasePrimaryKey;

@Embeddable
public class EppsRoleMasterPK extends BasePrimaryKey {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8481689117690372917L;

	private Integer companyCode;
	
	private Integer divisionCode;
	
	private Integer roleCode;
	
	/**
	 * 
	 */
	public EppsRoleMasterPK() {
		super();
		 
	}

	/**
	 * @param companyCode
	 * @param divisionCode
	 * @param roleCode
	 */
	public EppsRoleMasterPK(Integer companyCode, Integer divisionCode,
			Integer roleCode) {
		super();
		this.companyCode = companyCode;
		this.divisionCode = divisionCode;
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

	/**
	 * @return the roleCode
	 */
	@Column(name="role_cd",nullable=false)
	public Integer getRoleCode() {
		return roleCode;
	}

	/**
	 * @param roleCode the roleCode to set
	 */
	public void setRoleCode(Integer roleCode) {
		this.roleCode = roleCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((companyCode == null) ? 0 : companyCode.hashCode());
		result = prime * result
				+ ((divisionCode == null) ? 0 : divisionCode.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof EppsRoleMasterPK)) {
			return false;
		}
		EppsRoleMasterPK other = (EppsRoleMasterPK) obj;
		if (companyCode == null) {
			if (other.companyCode != null) {
				return false;
			}
		} else if (!companyCode.equals(other.companyCode)) {
			return false;
		}
		if (divisionCode == null) {
			if (other.divisionCode != null) {
				return false;
			}
		} else if (!divisionCode.equals(other.divisionCode)) {
			return false;
		}
		return true;
	}
}
