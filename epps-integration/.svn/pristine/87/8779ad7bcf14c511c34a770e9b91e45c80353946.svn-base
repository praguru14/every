package com.epps.framework.notification.mail.application.event.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.epps.framework.infrastructure.model.entities.BasePrimaryKey;

@Embeddable
public class EppsEmployeeMasterPK extends BasePrimaryKey {

	private static final long serialVersionUID = 1L;

	private Integer companyCode;
	
	private Integer divisionCode;
		
	private String  employeeCode;
	
	/**
	 * Instantiates a new epps employee master pk.
	 */
	public EppsEmployeeMasterPK() {
		 
	}
	
	/**
	 * Instantiates a new epps employee master pk.
	 *
	 * @param companyCode the company code
	 * @param divisionCode the division code
	 * @param employeeCode the employee code
	 */
	public EppsEmployeeMasterPK(Integer companyCode, Integer divisionCode,
			String employeeCode) {
		super();
		this.companyCode = companyCode;
		this.divisionCode = divisionCode;
		this.employeeCode = employeeCode;
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
	
	@Column(name="emp_cd",nullable=false)
	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	
}
