/**
 * 
 */
package com.epps.module.staging.hrcdf.domain.model.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @author Shubham Goliwar
 *
 */

@Entity
@Table(name = "personal_details",schema = "hrcdf_staging")
@DynamicInsert @DynamicUpdate
public class EppsStagingPersonnelDetails implements Serializable {
	

	private static final long serialVersionUID = -5096388743994030257L;

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
	
	private String unitCode;

	private String rankCode;
	
	private String nudEmailId;
	
	private String commandCode;

	private String sourceApp;

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

	@Id
	@Column(name = "sr_no",nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Column(name = "pno")
	public String getPersonnelNumber() {
		return personnelNumber;
	}

	public void setPersonnelNumber(String personnelNumber) {
		this.personnelNumber = personnelNumber;
	}

	@Column(name = "first_name")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "mid_name")
	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@Column(name = "last_name")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "emp_gender")
	public String getEmployeeGender() {
		return employeeGender;
	}

	public void setEmployeeGender(String employeeGender) {
		this.employeeGender = employeeGender;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dob")
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Column(name = "placeofbirth")
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	@Column(name = "user_type_code")
	public String getUserTypeCode() {
		return userTypeCode;
	}

	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}

	@Column(name = "full_name")
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "source_app")
	public String getSourceApp() {
		return sourceApp;
	}

	public void setSourceApp(String sourceApp) {
		this.sourceApp = sourceApp;
	}

	@Column(name = "unit_code")
	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}


	@Column(name = "rank_code")
	public String getRankCode() {
		return rankCode;
	}

	public void setRankCode(String rankCode) {
		this.rankCode = rankCode;
	}

	@Column(name = "nud_email_id")
	public String getNudEmailId() {
		return nudEmailId;
	}

	public void setNudEmailId(String nudEmailId) {
		this.nudEmailId = nudEmailId;
	}

	@Column(name = "cmnd_code")
	public String getCommandCode() {
		return commandCode;
	}

	public void setCommandCode(String commandCode) {
		this.commandCode = commandCode;
	}

	@Column(name = "stage")
	public Integer getStage() {
		return stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}

	@Column(name = "stage_remarks")
	public String getStage_remarks() {
		return stage_remarks;
	}

	public void setStage_remarks(String stage_remarks) {
		this.stage_remarks = stage_remarks;
	}

	@Column(name = "blood_group_code")
	public String getBloodGroupCode() {
		return bloodGroupCode;
	}

	public void setBloodGroupCode(String bloodGroupCode) {
		this.bloodGroupCode = bloodGroupCode;
	}

	@Column(name = "aadhar_no")
	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	@Column(name = "pan_no")
	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	@Column(name = "voter_id")
	public String getVoterId() {
		return voterId;
	}

	public void setVoterId(String voterId) {
		this.voterId = voterId;
	}

	@Column(name = "religion_code")
	public String getReligionCode() {
		return religionCode;
	}

	public void setReligionCode(String religionCode) {
		this.religionCode = religionCode;
	}

	@Column(name = "nationality_code")
	public String getNationalityCode() {
		return nationalityCode;
	}

	public void setNationalityCode(String nationalityCode) {
		this.nationalityCode = nationalityCode;
	}

	@Column(name = "off_mob_no")
	public String getOfficialMobileNumber() {
		return officialMobileNumber;
	}

	public void setOfficialMobileNumber(String officialMobileNumber) {
		this.officialMobileNumber = officialMobileNumber;
	}

	@Column(name = "per_mob_no")
	public String getPersonalMobileNumber() {
		return personalMobileNumber;
	}

	public void setPersonalMobileNumber(String personalMobileNumber) {
		this.personalMobileNumber = personalMobileNumber;
	}

	@Column(name = "branch_code")
	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	@Column(name = "entry_type")
	public String getEntryType() {
		return entryType;
	}

	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

	@Column(name = "medi_code")
	public String getMedicalCode() {
		return medicalCode;
	}

	public void setMedicalCode(String medicalCode) {
		this.medicalCode = medicalCode;
	}

	@Column(name = "marital_status")
	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	@Column(name = "guv_email_id")
	public String getGovEmailId() {
		return govEmailId;
	}

	public void setGovEmailId(String govEmailId) {
		this.govEmailId = govEmailId;
	}

	@Column(name = "pers_email_id")
	public String getPersonalEmailId() {
		return personalEmailId;
	}

	public void setPersonalEmailId(String personalEmailId) {
		this.personalEmailId = personalEmailId;
	}

	@Column(name = "cadre_code")
	public String getCadreCode() {
		return cadreCode;
	}

	
	public void setCadreCode(String cadreCode) {
		this.cadreCode = cadreCode;
	}
	
	
}
