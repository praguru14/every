package com.epps.framework.notification.mail.interfaces.dto;

import java.io.Serializable;

public class EmployeeDefaultRoleDetailsVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7315714408839047268L;

	private Integer defaultRoleCode;

	private String defaultRoleName;
	
	private String roleType;
	
	private String salLocationFlag;

	public Integer getDefaultRoleCode() {
		return defaultRoleCode;
	}

	public void setDefaultRoleCode(Integer defaultRoleCode) {
		this.defaultRoleCode = defaultRoleCode;
	}

	public String getDefaultRoleName() {
		return defaultRoleName;
	}

	public void setDefaultRoleName(String defaultRoleName) {
		this.defaultRoleName = defaultRoleName;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getSalLocFlag() {
		return salLocationFlag;
	}

	public void setSalLocFlag(String salLocationFlag) {
		this.salLocationFlag = salLocationFlag;
	}
	
	
	
}

