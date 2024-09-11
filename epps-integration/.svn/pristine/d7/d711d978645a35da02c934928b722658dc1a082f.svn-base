package com.epps.framework.notification.mail.application.event.entities;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.epps.framework.infrastructure.model.entities.EppsBaseEntity;

@Entity
@Table(name = "epps_employee_mst", schema = "epps_admin")
@DynamicInsert @DynamicUpdate
public class EppsEmployeeMaster extends EppsBaseEntity {

	private static final long serialVersionUID = 1L;

	private EppsEmployeeMasterPK eppsEmployeeMasterPk;

	private String employeeTitle;

	private String employeeFirstName;

	private String employeeMiddleName;

	private String employeeLastName;

	private String employeeCategory;

	private Date dateOfJoining;

	private Date dateOfLeaving;

	private Integer departmentCode;

	private String employeeMailId;

	private String employeePassword;

	private Integer activeYn;

	private Integer fileUploadSrNo;

	private String employeeType;

	private String refEmpId;
	
	private Integer clientUploadFlag;
	
	private String empphotourl;
	
	@Id
	@AttributeOverrides({ 
			@AttributeOverride(name = "companyCode", column = @Column(name = "comp_cd")),
			@AttributeOverride(name = "divisionCode", column = @Column(name = "div_cd")),
			@AttributeOverride(name = "employeeCode", column = @Column(name = "emp_cd")) })
	public EppsEmployeeMasterPK getEppsEmployeeMasterPk() {
		return eppsEmployeeMasterPk;
	}

	public void setEppsEmployeeMasterPk(EppsEmployeeMasterPK eppsEmployeeMasterPk) {
		this.eppsEmployeeMasterPk = eppsEmployeeMasterPk;
	}

	/**
	 * @return the employeeTitle
	 */
	@Column(name="emp_title", length=10)
	public String getEmployeeTitle() {
		return employeeTitle;
	}

	/**
	 * @param employeeTitle the employeeTitle to set
	 */
	public void setEmployeeTitle(String employeeTitle) {
		this.employeeTitle = employeeTitle;
	}

	/**
	 * @return the employeeFirstName
	 */
	@Column(name="emp_first_name", length=50)
	public String getEmployeeFirstName() {
		return employeeFirstName;
	}

	/**
	 * @param employeeFirstName the employeeFirstName to set
	 */
	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}

	/**
	 * @return the employeeMiddleName
	 */
	@Column(name="emp_middle_name", length=50)
	public String getEmployeeMiddleName() {
		return employeeMiddleName;
	}

	/**
	 * @param employeeMiddleName the employeeMiddleName to set
	 */
	public void setEmployeeMiddleName(String employeeMiddleName) {
		this.employeeMiddleName = employeeMiddleName;
	}

	/**
	 * @return the employeeLastName
	 */
	@Column(name="emp_last_name", length=50)
	public String getEmployeeLastName() {
		return employeeLastName;
	}

	/**
	 * @param employeeLastName the employeeLastName to set
	 */
	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}

	/**
	 * @return the employeeCategory
	 */
	@Column(name="emp_category", length=15)
	public String getEmployeeCategory() {
		return employeeCategory;
	}

	/**
	 * @param employeeCategory the employeeCategory to set
	 */
	public void setEmployeeCategory(String employeeCategory) {
		this.employeeCategory = employeeCategory;
	}

	/**
	 * @return the dateOfJoining
	 */
	@Column(name="date_of_join")
	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	/**
	 * @param dateOfJoining the dateOfJoining to set
	 */
	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	/**
	 * @return the dateOfLeaving
	 */
	@Column(name="date_of_leaving")
	public Date getDateOfLeaving() {
		return dateOfLeaving;
	}

	/**
	 * @param dateOfLeaving the dateOfLeaving to set
	 */
	public void setDateOfLeaving(Date dateOfLeaving) {
		this.dateOfLeaving = dateOfLeaving;
	}

	/**
	 * @return the departmentCode
	 */
	@Column(name="dept_cd")
	public Integer getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * @param departmentCode the departmentCode to set
	 */
	public void setDepartmentCode(Integer departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * @return the employeeMailId
	 */
	@Column(name="emp_mail_id", length=50)
	public String getEmployeeMailId() {
		return employeeMailId;
	}

	/**
	 * @param employeeMailId the employeeMailId to set
	 */
	public void setEmployeeMailId(String employeeMailId) {
		this.employeeMailId = employeeMailId;
	}

	/**
	 * @return the employeePassword
	 */
	@Column(name="emp_password", nullable =false)
	public String getEmployeePassword() {
		return employeePassword;
	}

	/**
	 * @param employeePassword the employeePassword to set
	 */
	public void setEmployeePassword(String employeePassword) {
		this.employeePassword = employeePassword;
	}

	/**
	 * @return the activeYn
	 */
	@Column(name="active_yn")
	public Integer getActiveYn() {
		return activeYn;
	}

	/**
	 * @param activeYn the activeYn to set
	 */
	public void setActiveYn(Integer activeYn) {
		this.activeYn = activeYn;
	}

	/**
	 * @return the fileUploadSrNo
	 */
	@Column(name="file_upload_sr_no")
	public Integer getFileUploadSrNo() {
		return fileUploadSrNo;
	}

	/**
	 * @param fileUploadSrNo the fileUploadSrNo to set
	 */
	public void setFileUploadSrNo(Integer fileUploadSrNo) {
		this.fileUploadSrNo = fileUploadSrNo;
	}

	/**
	 * @return the employeeType
	 */
	@Column(name="emp_type")
	public String getEmployeeType() {
		return employeeType;
	}

	/**
	 * @param employeeType the employeeType to set
	 */
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	/**
	 * @return the refEmpId
	 */
	 @Column(name="ref_emp_id")
	public String getRefEmpId() {
		return refEmpId;
	}

	/**
	 * @param refEmpId the refEmpId to set
	 */
	public void setRefEmpId(String refEmpId) {
		this.refEmpId = refEmpId;
	}

	/**
	 * @return the clientUploadFlag
	 */
	@Column(name="client_upload_flag")
	public Integer getClientUploadFlag() {
		return clientUploadFlag;
	}

	/**
	 * @param clientUploadFlag the clientUploadFlag to set
	 */
	public void setClientUploadFlag(Integer clientUploadFlag) {
		this.clientUploadFlag = clientUploadFlag;
	}

	@Column(name="emp_photo_url")
	public String getEmpphotourl() {
		return empphotourl;
	}

	public void setEmpphotourl(String empphotourl) {
		this.empphotourl = empphotourl;
	}
	
	
	
}
