/**
 * 
 */
package com.epps.module.staging.hrcdf.interfaces.dtos;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Shubham Goliwar
 *
 */

public class StagingPersonnelDetailsDto implements Serializable {

	private static final long serialVersionUID = -7868636565838086310L;

	private Integer serialNumber;

	private String personnelNumber;

	private String firstName;

	private String middleName;

	private String lastName;	

	private String employeeGender;

	private Date dateOfBirth;

	private String placeOfBirth;

	private String userTypeCode;

	private String fullName;

	private String sourceApp;

	private String unitCode;

	private String unitName;

	private String rankCode;

	private String rankName;
	
	private  String nudEmailId;
	
	private String commandCode;

	private Integer stage;
	
	private String stage_remarks;
	
	private String bloodGroupCode;
	
	private String aadharNumber;
	
	private String panNumber;
	
	private String voterId;
	
	private String religionCode;
	
	private String nationalityCode;
	
	private String officialMobileNumber;
	
	private String personalMobileNumber;
	
	private String branchCode;
	
 	private String entryType;
	
	private String medicalCode;
	
	private String maritalStatus;
	
	private String govEmailId;
	
	private String personalEmailId;
	
	private String cadreCode;


	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getPersonnelNumber() {
		return personnelNumber;
	}

	public void setPersonnelNumber(String personnelNumber) {
		this.personnelNumber = personnelNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmployeeGender() {
		return employeeGender;
	}

	public void setEmployeeGender(String employeeGender) {
		this.employeeGender = employeeGender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getUserTypeCode() {
		return userTypeCode;
	}

	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSourceApp() {
		return sourceApp;
	}

	public void setSourceApp(String sourceApp) {
		this.sourceApp = sourceApp;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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

	public String getNudEmailId() {
		return nudEmailId;
	}

	public void setNudEmailId(String nudEmailId) {
		this.nudEmailId = nudEmailId;
	}

	public String getCommandCode() {
		return commandCode;
	}

	public void setCommandCode(String commandCode) {
		this.commandCode = commandCode;
	}

	public Integer getStage() {
		return stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}

	public String getStage_remarks() {
		return stage_remarks;
	}

	public void setStage_remarks(String stage_remarks) {
		this.stage_remarks = stage_remarks;
	}

	public String getBloodGroupCode() {
		return bloodGroupCode;
	}

	public void setBloodGroupCode(String bloodGroupCode) {
		this.bloodGroupCode = bloodGroupCode;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getVoterId() {
		return voterId;
	}

	public void setVoterId(String voterId) {
		this.voterId = voterId;
	}

	public String getReligionCode() {
		return religionCode;
	}

	public void setReligionCode(String religionCode) {
		this.religionCode = religionCode;
	}

	public String getNationalityCode() {
		return nationalityCode;
	}

	public void setNationalityCode(String nationalityCode) {
		this.nationalityCode = nationalityCode;
	}

	public String getOfficialMobileNumber() {
		return officialMobileNumber;
	}

	public void setOfficialMobileNumber(String officialMobileNumber) {
		this.officialMobileNumber = officialMobileNumber;
	}

	public String getPersonalMobileNumber() {
		return personalMobileNumber;
	}

	public void setPersonalMobileNumber(String personalMobileNumber) {
		this.personalMobileNumber = personalMobileNumber;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getEntryType() {
		return entryType;
	}

	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

	public String getMedicalCode() {
		return medicalCode;
	}

	public void setMedicalCode(String medicalCode) {
		this.medicalCode = medicalCode;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getGovEmailId() {
		return govEmailId;
	}

	public void setGovEmailId(String govEmailId) {
		this.govEmailId = govEmailId;
	}

	public String getPersonalEmailId() {
		return personalEmailId;
	}

	public void setPersonalEmailId(String personalEmailId) {
		this.personalEmailId = personalEmailId;
	}

	public String getCadreCode() {
		return cadreCode;
	}

	public void setCadreCode(String cadreCode) {
		this.cadreCode = cadreCode;
	}
	
	
	
	
}
