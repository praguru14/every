package com.epps.framework.notification.mail.interfaces.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class EmployeeFinancialYearVO implements Serializable{

	private Integer financialYearCode;
	
	private String financialYearName;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Schema(type  = "string", format="date")
	private Date financialYearStartDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Schema(type  = "string", format="date")
	private Date financialYearEndDate;

	public Integer getFinancialYearCode() {
		return financialYearCode;
	}

	public void setFinancialYearCode(Integer financialYearCode) {
		this.financialYearCode = financialYearCode;
	}

	public String getFinancialYearName() {
		return financialYearName;
	}

	public void setFinancialYearName(String financialYearName) {
		this.financialYearName = financialYearName;
	}

	public Date getFinancialYearStartDate() {
		return financialYearStartDate;
	}

	public void setFinancialYearStartDate(Date financialYearStartDate) {
		this.financialYearStartDate = financialYearStartDate;
	}

	public Date getFinancialYearEndDate() {
		return financialYearEndDate;
	}

	public void setFinancialYearEndDate(Date financialYearEndDate) {
		this.financialYearEndDate = financialYearEndDate;
	}
}

