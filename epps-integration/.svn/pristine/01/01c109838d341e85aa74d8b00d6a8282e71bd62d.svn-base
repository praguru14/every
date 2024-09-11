package com.epps.framework.notification.mail.application.event.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.epps.framework.infrastructure.model.entities.BasePrimaryKey;

@Embeddable
public class EppsEmployeeLocationLinkPK extends BasePrimaryKey{

	/**
	 * 
	 */
	private static final long serialVersionUID = 18993393238072070L;

	private Integer companyCode;
	
	private Integer divisionCode;
	
	private Integer locationCode;
	
	private Integer roleCode;
	
	private String employeeCode;

	/**
	 * 
	 */
	public EppsEmployeeLocationLinkPK() {
		super();
		 
	}

	/**
	 * @param companyCode
	 * @param divisionCode
	 * @param locationCode
	 * @param roleCode
	 * @param employeeCode
	 */
	public EppsEmployeeLocationLinkPK(Integer companyCode,
			Integer divisionCode, Integer locationCode, Integer roleCode,
			String employeeCode) {
		super();
		this.companyCode = companyCode;
		this.divisionCode = divisionCode;
		this.locationCode = locationCode;
		this.roleCode = roleCode;
		this.employeeCode = employeeCode;
	}

	/**
	 * @return the companyCode
	 */
	@Column(name="comp_cd",nullable =false)
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
	 */@Column(name="div_cd",nullable =false)
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
	 * @return the locationCode
	 */
	@Column(name=" loc_cd",nullable =false)
	public Integer getLocationCode() {
		return locationCode;
	}

	/**
	 * @param locationCode the locationCode to set
	 */
	public void setLocationCode(Integer locationCode) {
		this.locationCode = locationCode;
	}

	/**
	 * @return the roleCode
	 */
	@Column(name="role_cd",nullable =false)
	public Integer getRoleCode() {
		return roleCode;
	}

	/**
	 * @param roleCode the roleCode to set
	 */
	public void setRoleCode(Integer roleCode) {
		this.roleCode = roleCode;
	}

	/**
	 * @return the employeeCode
	 */
	@Column(name="emp_cd",nullable =false)
	public String getEmployeeCode() {
		return employeeCode;
	}

	/**
	 * @param employeeCode the employeeCode to set
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	
}
