package com.epps.framework.common.domain.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EppsModuleMasterPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4021225809715053151L;

	private Integer companyCode;
	
	private String moduleId;
	
	
	
	public EppsModuleMasterPK() {
		super();
		 
	}

	public EppsModuleMasterPK(Integer companyCode, String moduleId) {
		super();
		this.companyCode = companyCode;
		this.moduleId = moduleId;
	}

	@Column(name="comp_cd",nullable=false)
	public Integer getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(Integer companyCode) {
		this.companyCode = companyCode;
	}

	@Column(name="module_id",nullable=false)
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
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
				+ ((moduleId == null) ? 0 : moduleId.hashCode());
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
		if (!(obj instanceof EppsModuleMasterPK))
			return false;
		EppsModuleMasterPK other = (EppsModuleMasterPK) obj;
		if (companyCode == null) {
			if (other.companyCode != null)
				return false;
		} else if (!companyCode.equals(other.companyCode))
			return false;
		if (moduleId == null) {
			if (other.moduleId != null)
				return false;
		} else if (!moduleId.equals(other.moduleId))
			return false;
		return true;
	}
	
	
}
