package com.epps.framework.notification.mail.interfaces.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class FinYearMasterDTO  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4399430330917666335L;


	private Integer companyCode;

	private Integer financialYear;

	private String createdBy;

	private String updatedBy;

	private Date   createdDate;

	private Date updatedDate;

	private Integer creatorRole;

	private Integer updatorRole;

	private String ipAddress;

	private String financialDescription;

	private Integer defaultYn;

	private Integer activeYn;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Schema(type  = "string", format="date")
	private Date fromDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Schema(type  = "string", format="date")
	private Date toDate;

	private Integer allowQueryYn;

	private String docNoString;

	private List<FinYearMasterDTO> data;

	public Integer getDefaultYn() {
		return defaultYn;
	}

	public void setDefaultYn(Integer defaultYn) {
		this.defaultYn = defaultYn;
	}


	public Integer getAllowQueryYn() {
		return allowQueryYn;
	}

	public void setAllowQueryYn(Integer allowQueryYn) {
		this.allowQueryYn = allowQueryYn;
	}

	public List<FinYearMasterDTO> getData() {
		return data;
	}

	public void setData(List<FinYearMasterDTO> data) {
		this.data = data;
	}

	public Integer getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(Integer financialYear) {
		this.financialYear = financialYear;
	}

	public String getFinancialDescription() {
		return financialDescription;
	}

	public void setFinancialDescription(String financialDescription) {
		this.financialDescription = financialDescription;
	}

	public String getDocNoString() {
		return docNoString;
	}

	public void setDocNoString(String docNoString) {
		this.docNoString = docNoString;
	}

	public Integer getActiveYn() {
		return activeYn;
	}

	public void setActiveYn(Integer activeYn) {
		this.activeYn = activeYn;
	}

	public Integer getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(Integer companyCode) {
		this.companyCode = companyCode;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getCreatorRole() {
		return creatorRole;
	}

	public void setCreatorRole(Integer creatorRole) {
		this.creatorRole = creatorRole;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Integer getUpdatorRole() {
		return updatorRole;
	}

	public void setUpdatorRole(Integer updatorRole) {
		this.updatorRole = updatorRole;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}
