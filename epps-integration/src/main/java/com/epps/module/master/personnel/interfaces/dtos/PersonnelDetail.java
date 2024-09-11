/**
 * 
 */
package com.epps.module.master.personnel.interfaces.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Abhinesh
 *
 */
@JsonInclude(Include.NON_NULL)
public class PersonnelDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2075134109314841890L;
	
	private Integer serialNumber;
	private String firstName;
	private String middleName;
	private String lastName;
	private String placeOfBirth;
	private Integer isActive;
	private String fullName;
	private String modifiedBy;
	private Date modifiedDate;
	private String sourceApp;
	private String ipAddress;
	private String personnelNumber;
	private String employeeGender;
	private Date dateOfBirth;
	private String userTypeCode;
	private String userType;
	private String gender;
	private String bloodGroup;
	private String nationality;
	private String maritalStatus;
	private String unitCode;
	private String commandCode;
	private String govEmailId;
	private String nudEmailId;
	private String personalEmailId;
	private Date dateOfJoining;
	private String rankCode;
	private String cadreCode;
	private String unitName;
	private String commandName;
	private String rankName;
	private String cadreName;
	private String stationName;
	private String userTypeName;	
	private String bloodGroupCode;
	private String aadharNumber;
	private String panNumber;
	private String voterId;
	private String religionCode;
	private String nationalityCode;
	private Date  dateOfMarriage;
	private String officialMobileNumber;
	private String personalMobileNumber;
	private Date dateOfCommission;
	private Date dateOfSeniority;
	private String serviceCode;
	private String commissionType;
	private String personnelEmailId;
	private Integer nationalityId;
	private String nationalityName;
	private String entryType;
	private String entryTypeName;
	private String genderName;
	private String bloodGroupName;
	private String maritalStatusCode;
	private String maritalStatusName;
	private String religionName;
	private String branchCode;
	private String branchName;
	private String designationCode;
	private String designation;
	private List<PersonnelDetail> data;
	private List<PersonnelDesignationDTO> personsDesignations;
	private PersonnelImageDTO personalImage;
	
	
//	public Integer getSerialNumber() {
//		return serialNumber;
//	}
//
//	public void setSerialNumber(Integer serialNumber) {
//		this.serialNumber = serialNumber;
//	}

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

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getSourceApp() {
		return sourceApp;
	}

	public void setSourceApp(String sourceApp) {
		this.sourceApp = sourceApp;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getPersonnelNumber() {
		return personnelNumber;
	}

	public void setPersonnelNumber(String personnelNumber) {
		this.personnelNumber = personnelNumber;
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

	public String getUserTypeCode() {
		return userTypeCode;
	}

	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getCommandCode() {
		return commandCode;
	}

	public void setCommandCode(String commandCode) {
		this.commandCode = commandCode;
	}

	public String getGovEmailId() {
		return govEmailId;
	}

	public void setGovEmailId(String govEmailId) {
		this.govEmailId = govEmailId;
	}

	public String getNudEmailId() {
		return nudEmailId;
	}

	public void setNudEmailId(String nudEmailId) {
		this.nudEmailId = nudEmailId;
	}

	public String getPersonalEmailId() {
		return personalEmailId;
	}

	public void setPersonalEmailId(String personalEmailId) {
		this.personalEmailId = personalEmailId;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getRankCode() {
		return rankCode;
	}

	public void setRankCode(String rankCode) {
		this.rankCode = rankCode;
	}

	public String getCadreCode() {
		return cadreCode;
	}

	public void setCadreCode(String cadreCode) {
		this.cadreCode = cadreCode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	public String getCadreName() {
		return cadreName;
	}

	public void setCadreName(String cadreName) {
		this.cadreName = cadreName;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
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

	public Date getDateOfMarriage() {
		return dateOfMarriage;
	}

	public void setDateOfMarriage(Date dateOfMarriage) {
		this.dateOfMarriage = dateOfMarriage;
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

	public Date getDateOfCommission() {
		return dateOfCommission;
	}

	public void setDateOfCommission(Date dateOfCommission) {
		this.dateOfCommission = dateOfCommission;
	}

	public Date getDateOfSeniority() {
		return dateOfSeniority;
	}

	public void setDateOfSeniority(Date dateOfSeniority) {
		this.dateOfSeniority = dateOfSeniority;
	}

	public List<PersonnelDetail> getData() {
		return data;
	}

	public void setData(List<PersonnelDetail> data) {
		this.data = data;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}

	public PersonnelImageDTO getPersonalImage() {
		return personalImage;
	}

	public void setPersonalImage(PersonnelImageDTO personalImage) {
		this.personalImage = personalImage;
	}
	

	public List<PersonnelDesignationDTO> getPersonsDesignations() {
		return personsDesignations;
	}

	public void setPersonsDesignations(List<PersonnelDesignationDTO> personsDesignations) {
		this.personsDesignations = personsDesignations;
	}

	public String getPersonnelEmailId() {
		return personnelEmailId;
	}

	public void setPersonnelEmailId(String personnelEmailId) {
		this.personnelEmailId = personnelEmailId;
	}

	public Integer getNationalityId() {
		return nationalityId;
	}

	public void setNationalityId(Integer nationalityId) {
		this.nationalityId = nationalityId;
	}

	public String getNationalityName() {
		return nationalityName;
	}

	public void setNationalityName(String nationalityName) {
		this.nationalityName = nationalityName;
	}

	public String getEntryType() {
		return entryType;
	}

	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

	public String getEntryTypeName() {
		return entryTypeName;
	}

	public void setEntryTypeName(String entryTypeName) {
		this.entryTypeName = entryTypeName;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public String getBloodGroupName() {
		return bloodGroupName;
	}

	public void setBloodGroupName(String bloodGroupName) {
		this.bloodGroupName = bloodGroupName;
	}

	public String getMaritalStatusCode() {
		return maritalStatusCode;
	}

	public void setMaritalStatusCode(String maritalStatusCode) {
		this.maritalStatusCode = maritalStatusCode;
	}

	public String getMaritalStatusName() {
		return maritalStatusName;
	}

	public void setMaritalStatusName(String maritalStatusName) {
		this.maritalStatusName = maritalStatusName;
	}

	public String getReligionName() {
		return religionName;
	}

	public void setReligionName(String religionName) {
		this.religionName = religionName;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getDesignationCode() {
		return designationCode;
	}

	public void setDesignationCode(String designationCode) {
		this.designationCode = designationCode;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	
}
