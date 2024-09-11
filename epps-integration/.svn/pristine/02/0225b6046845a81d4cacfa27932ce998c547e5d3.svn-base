package com.epps.framework.notification.mail.application.event.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.epps.framework.infrastructure.model.entities.BasePrimaryKey;

@Embeddable
public class EppsProgramMasterPK extends BasePrimaryKey {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9063488114299021652L;

	private Integer companyCode;
	
	private Integer programeCode;
	
	public EppsProgramMasterPK() {
		super();
	}

	public EppsProgramMasterPK(Integer companyCode, Integer programeCode) {
		super();
		this.companyCode = companyCode;
		this.programeCode = programeCode;
	}

	/**
	 * @return the companyCode
	 */
	@Column(name="comp_cd",nullable=false)
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
	 * @return the programeCode
	 */
	@Column(name="prog_cd",nullable=false)
	public Integer getProgrameCode() {
		return programeCode;
	}

	/**
	 * @param programeCode the programeCode to set
	 */
	public void setProgrameCode(Integer programeCode) {
		this.programeCode = programeCode;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result;
		result = programeCode.hashCode();
		result = 29 * result + companyCode.hashCode();
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof EppsProgramMasterPK))
			return false;
		EppsProgramMasterPK other = (EppsProgramMasterPK) obj;
		if (companyCode == null) {
			if (other.companyCode != null)
				return false;
		} else if (!companyCode.equals(other.companyCode))
			return false;
		if (programeCode == null) {
			if (other.programeCode != null)
				return false;
		} else if (!programeCode.equals(other.programeCode))
			return false;
		return true;
	}
}
