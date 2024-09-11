package com.epps.framework.notification.mail.application.event.entities;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.epps.framework.infrastructure.model.entities.EppsBaseEntity;

@Entity
@Table(name="epps_emp_loc_lnk", schema ="epps_admin")
@DynamicInsert @DynamicUpdate
public class EppsEmployeeLocationLink extends EppsBaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3059169375666158135L;

	private EppsEmployeeLocationLinkPK employeeLocationLinkPK;
	
	private String activeYn;
	
	private String salLocationFlag;
	
	private Integer updatorRole;
	
	private EppsRoleMaster eppsRoleMaster;
	
	private String reporting2EmpCode;
	
	//private EppsRoleEmployeeLink roleEmployeeLink;

	private EppsEmployeeMaster eppsEmployeeMaster;
	
	private Integer autoCreatedEntry;
	
	/**
	 * @return the employeeLocationLinkPK
	 */
	@Id
	@AttributeOverrides({
        @AttributeOverride(name = "companyCode", column = @Column(name = "comp_cd")),
        @AttributeOverride(name = "divisionCode", column = @Column(name = "div_cd")),
        @AttributeOverride(name = "locationCode", column = @Column(name = "loc_cd")),
        @AttributeOverride(name = "roleCode", column = @Column(name = "role_cd")),
        @AttributeOverride(name = "employeeCode", column = @Column(name = "emp_cd"))})
	public EppsEmployeeLocationLinkPK getEmployeeLocationLinkPK() {
		return employeeLocationLinkPK;
	}

	/**
	 * @param employeeLocationLinkPK the employeeLocationLinkPK to set
	 */
	public void setEmployeeLocationLinkPK(
			EppsEmployeeLocationLinkPK employeeLocationLinkPK) {
		this.employeeLocationLinkPK = employeeLocationLinkPK;
	}
	@Column(name="active_yn")
	public String getActiveYn() {
		return activeYn;
	}

	public void setActiveYn(String activeYn) {
		this.activeYn = activeYn;
	}

	@Column(name="sal_loc_flag")
	public String getSalLocationFlag() {
		return salLocationFlag;
	}

	public void setSalLocationFlag(String salLocationFlag) {
		this.salLocationFlag = salLocationFlag;
	}
	
	/**
	 * @return the updatorRole
	 */
	@Column(name="updator_role_cd")
	public Integer getUpdatorRole() {
		return updatorRole;
	}

	/**
	 * @param updatorRole the updatorRole to set
	 */
	public void setUpdatorRole(Integer updatorRole) {
		this.updatorRole = updatorRole;
	}

	/**
	 * @return the eppsRoleMaster
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@Cascade (value=CascadeType.SAVE_UPDATE)
	@JoinColumns({
		@JoinColumn(name="comp_cd",referencedColumnName="comp_cd",insertable=false,updatable=false),
		@JoinColumn(name="div_cd",referencedColumnName="div_cd",insertable=false,updatable=false),
		@JoinColumn(name="role_cd",referencedColumnName="role_cd",insertable=false,updatable=false)})
	public EppsRoleMaster getEppsRoleMaster() {
		return eppsRoleMaster;
	}

	/**
	 * @param eppsRoleMaster the eppsRoleMaster to set
	 */
	public void setEppsRoleMaster(EppsRoleMaster eppsRoleMaster) {
		this.eppsRoleMaster = eppsRoleMaster;
	}

	/**
	 * @return the roleEmployeeLink
	 *//*
	@ManyToOne(fetch=FetchType.LAZY)
	@Cascade (value=CascadeType.SAVE_UPDATE)
	@JoinColumns({
		@JoinColumn(name="comp_cd",referencedColumnName="comp_cd",insertable=false,updatable=false),
		@JoinColumn(name="div_cd",referencedColumnName="div_cd",insertable=false,updatable=false),
		@JoinColumn(name="role_cd",referencedColumnName="role_cd",insertable=false,updatable=false),
		@JoinColumn(name="emp_cd",referencedColumnName="emp_cd",insertable=false,updatable=false)})
	public EppsRoleEmployeeLink getRoleEmployeeLink() {
		return roleEmployeeLink;
	}

	*//**
	 * @param roleEmployeeLink the roleEmployeeLink to set
	 *//*
	public void setRoleEmployeeLink(EppsRoleEmployeeLink roleEmployeeLink) {
		this.roleEmployeeLink = roleEmployeeLink;
	}
*/

	/**
	 * @return the eppsEmployeeMaster
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@Cascade (value=CascadeType.SAVE_UPDATE)
	@JoinColumns({
		@JoinColumn(name="comp_cd",referencedColumnName="comp_cd",insertable=false,updatable=false),
		@JoinColumn(name="div_cd",referencedColumnName="div_cd",insertable=false,updatable=false),
		@JoinColumn(name="emp_cd",referencedColumnName="emp_cd",insertable=false,updatable=false)
		})
	public EppsEmployeeMaster getEppsEmployeeMaster() {
		return eppsEmployeeMaster;
	}

	/**
	 * @param eppsEmployeeMaster the eppsEmployeeMaster to set
	 */
	public void setEppsEmployeeMaster(EppsEmployeeMaster eppsEmployeeMaster) {
		this.eppsEmployeeMaster = eppsEmployeeMaster;
	}
	@Column(name="rep2_emp_cd",length=10)
	public String getReporting2EmpCode() {
		return reporting2EmpCode;
	}

	public void setReporting2EmpCode(String reporting2EmpCode) {
		this.reporting2EmpCode = reporting2EmpCode;
	}
	
	@Column(name="auto_created_entry")
	public Integer getAutoCreatedEntry() {
		return autoCreatedEntry;
	}

	public void setAutoCreatedEntry(Integer autoCreatedEntry) {
		this.autoCreatedEntry = autoCreatedEntry;
	}
}