package com.epps.framework.notification.mail.interfaces.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.epps.framework.interfaces.responses.dtos.GenericIdValueDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4381506549428235748L;

	private String employeeMiddleName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Schema(type  = "string", format="date")
	private Date dateOfJoining;

	private Integer departmentCode;

	private Integer activeYn;

	private Integer fileUploadSrNo;

	private String refEmpId;

	private Integer clientUploadFlag;

	private String empphotourl;

	private String userId;

	private String employeeTitle;

	private String employeeFirstName;

	private String employeeLastName;

	private String employeeFullName;

	private String employeeCode;

	private String employeeType;

	private Integer companyCode;

	private Integer divisionCode;

	private String divisionName;

	private Boolean isNotificationPresent;

	private String userLocale;

	private Integer decimalPlacesQty;

	private Integer decimalPlacesAmount;

	private String userIp;

	private Integer repAmtPrecesion;

	private Integer repQtyPrecesion;

	private Integer fasRepAmtPrecesion;

	private Integer fasRepQtyPrecesion;

	private String eppsType;

	private HashMap<String, String> hashStore;

	private EmployeeDefaultRoleDetailsVO defaultRoleDetailsVO;

	private FinYearMasterDTO finYearMasterDTO;

	private List<GenericIdValueDTO<Integer, String>> userRoles;

	private List<GenericIdValueDTO<Integer, String>> userFinYears;
	
	private PersonnelDetail personnelDetail;

	private String sesssionId;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Schema(type  = "string", format="date")
	private Date loginDateTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Schema(type  = "string", format="date")
	private Date loginDateTimeString;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Schema(type  = "string", format="date")
	private Date lastAccessDateTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Schema(type  = "string", format="date")
	private String lastAccessDateTimeString;

	private String employeeActiveYn;

	private Integer userLoginSrNo;

	private String employeeCategory;

	private String defaultDateFormat;

	private String companyDisplayName;

	private String divAsCompany;

	private String numFormatForAmt;

	private String employeeMailId;

	private String employeePassword;

	private List<FinYearMasterDTO> employeeFinancialYearList;

	//private List<DivisionMasterDTO> employeeDivisionList;


	public List<FinYearMasterDTO> getEmployeeFinancialYearList() {
		return employeeFinancialYearList;
	}

	public void setEmployeeFinancialYearList(List<FinYearMasterDTO> employeeFinancialYearList) {
		this.employeeFinancialYearList = employeeFinancialYearList;
	}

	public String getEmployeeMiddleName() {
		return employeeMiddleName;
	}

	public void setEmployeeMiddleName(String employeeMiddleName) {
		this.employeeMiddleName = employeeMiddleName;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public Integer getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(Integer departmentCode) {
		this.departmentCode = departmentCode;
	}

	public Integer getActiveYn() {
		return activeYn;
	}

	public void setActiveYn(Integer activeYn) {
		this.activeYn = activeYn;
	}

	public Integer getFileUploadSrNo() {
		return fileUploadSrNo;
	}

	public void setFileUploadSrNo(Integer fileUploadSrNo) {
		this.fileUploadSrNo = fileUploadSrNo;
	}

	public String getRefEmpId() {
		return refEmpId;
	}

	public void setRefEmpId(String refEmpId) {
		this.refEmpId = refEmpId;
	}

	public Integer getClientUploadFlag() {
		return clientUploadFlag;
	}

	public void setClientUploadFlag(Integer clientUploadFlag) {
		this.clientUploadFlag = clientUploadFlag;
	}

	public String getEmpphotourl() {
		return empphotourl;
	}

	public void setEmpphotourl(String empphotourl) {
		this.empphotourl = empphotourl;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmployeeTitle() {
		return employeeTitle;
	}

	public void setEmployeeTitle(String employeeTitle) {
		this.employeeTitle = employeeTitle;
	}

	public String getEmployeeFirstName() {
		return employeeFirstName;
	}

	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}

	public String getEmployeeLastName() {
		return employeeLastName;
	}

	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}

	public String getEmployeeFullName() {
		return employeeFullName;
	}

	public void setEmployeeFullName(String employeeFullName) {
		this.employeeFullName = employeeFullName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public Integer getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(Integer companyCode) {
		this.companyCode = companyCode;
	}

	public Integer getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(Integer divisionCode) {
		this.divisionCode = divisionCode;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public Boolean getIsNotificationPresent() {
		return isNotificationPresent;
	}

	public void setIsNotificationPresent(Boolean isNotificationPresent) {
		this.isNotificationPresent = isNotificationPresent;
	}

	public String getUserLocale() {
		return userLocale;
	}

	public void setUserLocale(String userLocale) {
		this.userLocale = userLocale;
	}

	public Integer getDecimalPlacesQty() {
		return decimalPlacesQty;
	}

	public void setDecimalPlacesQty(Integer decimalPlacesQty) {
		this.decimalPlacesQty = decimalPlacesQty;
	}

	public Integer getDecimalPlacesAmount() {
		return decimalPlacesAmount;
	}

	public void setDecimalPlacesAmount(Integer decimalPlacesAmount) {
		this.decimalPlacesAmount = decimalPlacesAmount;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public Integer getRepAmtPrecesion() {
		return repAmtPrecesion;
	}

	public void setRepAmtPrecesion(Integer repAmtPrecesion) {
		this.repAmtPrecesion = repAmtPrecesion;
	}

	public Integer getRepQtyPrecesion() {
		return repQtyPrecesion;
	}

	public void setRepQtyPrecesion(Integer repQtyPrecesion) {
		this.repQtyPrecesion = repQtyPrecesion;
	}

	public Integer getFasRepAmtPrecesion() {
		return fasRepAmtPrecesion;
	}

	public void setFasRepAmtPrecesion(Integer fasRepAmtPrecesion) {
		this.fasRepAmtPrecesion = fasRepAmtPrecesion;
	}

	public Integer getFasRepQtyPrecesion() {
		return fasRepQtyPrecesion;
	}

	public void setFasRepQtyPrecesion(Integer fasRepQtyPrecesion) {
		this.fasRepQtyPrecesion = fasRepQtyPrecesion;
	}

	public String getEppsType() {
		return eppsType;
	}

	public void setEppsType(String eppsType) {
		this.eppsType = eppsType;
	}

	public HashMap<String, String> getHashStore() {
		return hashStore;
	}

	public void setHashStore(HashMap<String, String> hashStore) {
		this.hashStore = hashStore;
	}

	public EmployeeDefaultRoleDetailsVO getDefaultRoleDetailsVO() {
		return defaultRoleDetailsVO;
	}

	public void setDefaultRoleDetailsVO(EmployeeDefaultRoleDetailsVO defaultRoleDetailsVO) {
		this.defaultRoleDetailsVO = defaultRoleDetailsVO;
	}

	public FinYearMasterDTO getFinYearMasterDTO() {
		return finYearMasterDTO;
	}

	public void setFinYearMasterDTO(FinYearMasterDTO finYearMasterDTO) {
		this.finYearMasterDTO = finYearMasterDTO;
	}

	public List<GenericIdValueDTO<Integer, String>> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<GenericIdValueDTO<Integer, String>> userRoles) {
		this.userRoles = userRoles;
	}

	public List<GenericIdValueDTO<Integer, String>> getUserFinYears() {
		return userFinYears;
	}

	public void setUserFinYears(List<GenericIdValueDTO<Integer, String>> userFinYears) {
		this.userFinYears = userFinYears;
	}

	public String getSesssionId() {
		return sesssionId;
	}

	public void setSesssionId(String sesssionId) {
		this.sesssionId = sesssionId;
	}

	public Date getLoginDateTime() {
		return loginDateTime;
	}

	public void setLoginDateTime(Date loginDateTime) {
		this.loginDateTime = loginDateTime;
	}

	public Date getLoginDateTimeString() {
		return loginDateTimeString;
	}

	public void setLoginDateTimeString(Date loginDateTimeString) {
		this.loginDateTimeString = loginDateTimeString;
	}

	public Date getLastAccessDateTime() {
		return lastAccessDateTime;
	}

	public void setLastAccessDateTime(Date lastAccessDateTime) {
		this.lastAccessDateTime = lastAccessDateTime;
	}

	public String getLastAccessDateTimeString() {
		return lastAccessDateTimeString;
	}

	public void setLastAccessDateTimeString(String lastAccessDateTimeString) {
		this.lastAccessDateTimeString = lastAccessDateTimeString;
	}

	public String getEmployeeActiveYn() {
		return employeeActiveYn;
	}

	public void setEmployeeActiveYn(String employeeActiveYn) {
		this.employeeActiveYn = employeeActiveYn;
	}

	public Integer getUserLoginSrNo() {
		return userLoginSrNo;
	}

	public void setUserLoginSrNo(Integer userLoginSrNo) {
		this.userLoginSrNo = userLoginSrNo;
	}

	public String getEmployeeCategory() {
		return employeeCategory;
	}

	public void setEmployeeCategory(String employeeCategory) {
		this.employeeCategory = employeeCategory;
	}

	public String getDefaultDateFormat() {
		return defaultDateFormat;
	}

	public void setDefaultDateFormat(String defaultDateFormat) {
		this.defaultDateFormat = defaultDateFormat;
	}

	public String getCompanyDisplayName() {
		return companyDisplayName;
	}

	public void setCompanyDisplayName(String companyDisplayName) {
		this.companyDisplayName = companyDisplayName;
	}

	public String getDivAsCompany() {
		return divAsCompany;
	}

	public void setDivAsCompany(String divAsCompany) {
		this.divAsCompany = divAsCompany;
	}

	public String getNumFormatForAmt() {
		return numFormatForAmt;
	}

	public void setNumFormatForAmt(String numFormatForAmt) {
		this.numFormatForAmt = numFormatForAmt;
	}

	public String getEmployeeMailId() {
		return employeeMailId;
	}

	public void setEmployeeMailId(String employeeMailId) {
		this.employeeMailId = employeeMailId;
	}

	public String getEmployeePassword() {
		return employeePassword;
	}

	public void setEmployeePassword(String employeePassword) {
		this.employeePassword = employeePassword;
	}

	public PersonnelDetail getPersonnelDetail() {
		return personnelDetail;
	}

	public void setPersonnelDetail(PersonnelDetail personnelDetail) {
		this.personnelDetail = personnelDetail;
	}
	
	

}
