/**
 * 
 */
package com.epps.module.master.personnel.interfaces.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author Shubham G.
 *
 */

@JsonInclude(Include.NON_NULL)
@Schema(name = "Rank Master")
public class RankMasterDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9191518687179245446L;

	private Integer rankId;

	private String rankCode;

	private String rankName;

	private Integer precedence;

	private String userTypeCode;

	private Integer rankMinYears;

	private Integer retirementAge;

	private Integer isActive;

	private String sourceApp;
	
	private Integer isFlagOfficer;

	private String createdBy;

	private String modifiedBy;

	private Date createdDate;

	private Date modifiedDate;

	private String ipAddress;
	
	private List<RankMasterDTO> data;


	public Integer getPrecedence() {
		return precedence;
	}

	public void setPrecedence(Integer precedence) {
		this.precedence = precedence;
	}

	public String getUserTypeCode() {
		return userTypeCode;
	}

	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}

	public Integer getRankMinYears() {
		return rankMinYears;
	}

	public void setRankMinYears(Integer rankMinYears) {
		this.rankMinYears = rankMinYears;
	}

	public Integer getRetirementAge() {
		return retirementAge;
	}

	public void setRetirementAge(Integer retirementAge) {
		this.retirementAge = retirementAge;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getSourceApp() {
		return sourceApp;
	}

	public void setSourceApp(String sourceApp) {
		this.sourceApp = sourceApp;
	}

	public Integer getIsFlagOfficer() {
		return isFlagOfficer;
	}

	public void setIsFlagOfficer(Integer isFlagOfficer) {
		this.isFlagOfficer = isFlagOfficer;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	

	public String getRankCode() {
		return rankCode;
	}

	public void setRankCode(String rankCode) {
		this.rankCode = rankCode;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	public Integer getRankId() {
		return rankId;
	}

	public void setRankId(Integer rankId) {
		this.rankId = rankId;
	}

	public List<RankMasterDTO> getData() {
		return data;
	}

	public void setData(List<RankMasterDTO> data) {
		this.data = data;
	}
	
	

}
