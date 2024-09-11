package com.epps.framework.notification.mail.application.event.entities;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;

import com.epps.framework.infrastructure.model.entities.EppsBaseEntity;

@Entity
@Table(name="epps_role_mst",schema="epps_admin")
@DynamicInsert @DynamicUpdate
public class EppsRoleMaster extends EppsBaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6481319614544894755L;

	private EppsRoleMasterPK eppsRoleMasterPK; 
	
	private String roleDisplayName;
	
	private String roleLongName;
	
	private Integer roleParentRole;
	
	private String activeYn;
	
	private Double perItemLimit;
	
	private Double perTtransactionLimit;
	
	private String roleType;
	
	private Integer roleDisplaySequnceNo;
	
	private String sysAdminFlag;
	
	private String roleId;
	
	private Integer updatorRole;
	
	private String roleExpAppFlag;
	
	private Set<EppsEmployeeLocationLink> employeeLocationLinks;
	
	//private Set<EppsRoleProgramLink> eppsRoleProgramLinks; 
	
//	private Set<EppsRoleTreeDtl> eppsRoleTreeDtl;
	
	private String roleDisplayNameWithoutSpace;
	
	//private Set<EppsSdIprodHdr> eppsSdIprodHdr;
	
	private Integer hrAdminFlag;
	
	private String refRoleId;
	
	private Integer defaultYn;
	
	/**
	 * @return the eppsRoleMasterPK
	 */
	@Id
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode",column = @Column(name="comp_cd") ),
		@AttributeOverride(name = "divisionCode",column = @Column(name="div_cd") ),
		@AttributeOverride(name = "roleCode",column = @Column(name="role_cd") )
		})
	public EppsRoleMasterPK getEppsRoleMasterPK() {
		return eppsRoleMasterPK;
	}

	/**
	 * @param eppsRoleMasterPK the eppsRoleMasterPK to set
	 */
	public void setEppsRoleMasterPK(EppsRoleMasterPK eppsRoleMasterPK) {
		this.eppsRoleMasterPK = eppsRoleMasterPK;
	}

	/**
	 * @return the roleDisplayName
	 */
	@Column(name="role_disp_name",nullable=true)
	public String getRoleDisplayName() {
		return roleDisplayName;
	}

	/**
	 * @param roleDisplayName the roleDisplayName to set
	 */
	public void setRoleDisplayName(String roleDisplayName) {
		this.roleDisplayName = roleDisplayName;
	}

	/**
	 * @return the roleLongName
	 */
	@Column(name="role_long_name",nullable=true)
	public String getRoleLongName() {
		return roleLongName;
	}

	/**
	 * @param roleLongName the roleLongName to set
	 */
	public void setRoleLongName(String roleLongName) {
		this.roleLongName = roleLongName;
	}

	/**
	 * @return the roleParentRole
	 */
	@Column(name="role_parent_role",nullable=true)
	public Integer getRoleParentRole() {
		return roleParentRole;
	}

	/**
	 * @param roleParentRole the roleParentRole to set
	 */
	public void setRoleParentRole(Integer roleParentRole) {
		this.roleParentRole = roleParentRole;
	}

	/**
	 * @return the activeYn
	 */
	@Column(name="active_yn",nullable=false)
	public String getActiveYn() {
		return activeYn;
	}

	/**
	 * @param activeYn the activeYn to set
	 */
	public void setActiveYn(String activeYn) {
		this.activeYn = activeYn;
	}

	/**
	 * @return the perItemLimit
	 */
	@Column(name="per_item_limit",nullable=true)
	public Double getPerItemLimit() {
		return perItemLimit;
	}

	/**
	 * @param perItemLimit the perItemLimit to set
	 */
	public void setPerItemLimit(Double perItemLimit) {
		this.perItemLimit = perItemLimit;
	}

	/**
	 * @return the perTtransactionLimit
	 */
	@Column(name="per_transaction_limit",nullable=true)
	public Double getPerTtransactionLimit() {
		return perTtransactionLimit;
	}

	/**
	 * @param perTtransactionLimit the perTtransactionLimit to set
	 */
	public void setPerTtransactionLimit(Double perTtransactionLimit) {
		this.perTtransactionLimit = perTtransactionLimit;
	}

	/**
	 * @return the roleType
	 */
	@Column(name="role_type",nullable=true)
	public String getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	/**
	 * @return the roleDisplaySequnceNo
	 */
	@Column(name="role_disp_seq_no",nullable=true)
	public Integer getRoleDisplaySequnceNo() {
		return roleDisplaySequnceNo;
	}

	/**
	 * @param roleDisplaySequnceNo the roleDisplaySequnceNo to set
	 */
	public void setRoleDisplaySequnceNo(Integer roleDisplaySequnceNo) {
		this.roleDisplaySequnceNo = roleDisplaySequnceNo;
	}

	/**
	 * @return the roleAdminFlag
	 */
	@Column(name="sys_admin_flag",nullable=true)
	public String getSysAdminFlag() {
		return sysAdminFlag;
	}

	/**
	 * @param roleAdminFlag the roleAdminFlag to set
	 */
	public void setSysAdminFlag(String sysAdminFlag) {
		this.sysAdminFlag = sysAdminFlag;
	}

	/**
	 * @return the roleId
	 */
	@Column(name="role_id",nullable=true)
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	/**
	 * @return the employeeLocationLinks
	 */
	@OneToMany(mappedBy = "eppsRoleMaster",fetch=FetchType.LAZY,orphanRemoval=true)
	@Cascade(CascadeType.SAVE_UPDATE)
	public Set<EppsEmployeeLocationLink> getEmployeeLocationLinks() {
		return employeeLocationLinks;
	}

	/**
	 * @param employeeLocationLinks the employeeLocationLinks to set
	 */
	public void setEmployeeLocationLinks(
			Set<EppsEmployeeLocationLink> employeeLocationLinks) {
		this.employeeLocationLinks = employeeLocationLinks;
	}
	
	/**
	 * @return the roleDisplayNameWithoutSpace
	 */
	@Formula("replace(role_disp_name,' ','')")
	public String getRoleDisplayNameWithoutSpace() {
		return roleDisplayNameWithoutSpace;
	}

	/**
	 * @param roleDisplayNameWithoutSpace the roleDisplayNameWithoutSpace to set
	 */
	public void setRoleDisplayNameWithoutSpace(String roleDisplayNameWithoutSpace) {
		this.roleDisplayNameWithoutSpace = roleDisplayNameWithoutSpace;
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
	 * @return the roleExpAppFlag
	 */
	@Column(name="role_exp_app_flag", nullable=false)
	public String getRoleExpAppFlag() {
		return roleExpAppFlag;
	}

	/**
	 * @param roleExpAppFlag the roleExpAppFlag to set
	 */
	public void setRoleExpAppFlag(String roleExpAppFlag) {
		this.roleExpAppFlag = roleExpAppFlag;
	}

	/**
	 * @return the hrAdminFlag
	 */
	@Column(name="hr_admin_flag")
	public Integer getHrAdminFlag() {
		return hrAdminFlag;
	}

	/**
	 * @param hrAdminFlag the hrAdminFlag to set
	 */
	public void setHrAdminFlag(Integer hrAdminFlag) {
		this.hrAdminFlag = hrAdminFlag;
	}

	/**
	 * @return the refRoleId
	 */
	@Column(name="ref_role_id",length=10)
	public String getRefRoleId() {
		return refRoleId;
	}

	/**
	 * @param refRoleId the refRoleId to set
	 */
	public void setRefRoleId(String refRoleId) {
		this.refRoleId = refRoleId;
	}

	@Column(name="default_yn")
	public Integer getDefaultYn() {
		return defaultYn;
	}

	public void setDefaultYn(Integer defaultYn) {
		this.defaultYn = defaultYn;
	}

	/*@OneToMany(mappedBy="eppsRoleMaster",orphanRemoval=true)
	@Cascade(CascadeType.SAVE_UPDATE)
	public Set<EppsSdIprodHdr> getEppsSdIprodHdr() {
		return eppsSdIprodHdr;
	}

	public void setEppsSdIprodHdr(Set<EppsSdIprodHdr> eppsSdIprodHdr) {
		this.eppsSdIprodHdr = eppsSdIprodHdr;
	}*/
	
	
	
	
}