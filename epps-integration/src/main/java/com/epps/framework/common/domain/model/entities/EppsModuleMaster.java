package com.epps.framework.common.domain.model.entities;


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
@Table(name="epps_module_mst",schema="epps_admin")
@DynamicInsert @DynamicUpdate
public class EppsModuleMaster extends EppsBaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 230400696148090696L;

	private EppsModuleMasterPK eppsModuleMasterPK;
	
	private String moduleDisplayName;
	
	private Integer activeYn;
	
	private Integer moduleDisplaySeqNo;
	
	private Integer updatorRole;
	
	private Integer displayYn;
	
	//private Set<EppsProgramMaster> eppsProgramMasters;
	
	//private Set<EppsTranIndicator> eppsTranIndicators;
	


	/**
	 * @return the eppsModuleMasterPK
	 */
	@Id
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode",column = @Column(name="comp_cd") ),
		@AttributeOverride(name = "moduleId",column = @Column(name="module_id") )
		})
	public EppsModuleMasterPK getEppsModuleMasterPK() {
		return eppsModuleMasterPK;
	}

	/**
	 * @param eppsModuleMasterPK the eppsModuleMasterPK to set
	 */
	public void setEppsModuleMasterPK(EppsModuleMasterPK eppsModuleMasterPK) {
		this.eppsModuleMasterPK = eppsModuleMasterPK;
	}

	/**
	 * @return the moduleDisplayName
	 */
	@Column(name="module_disp_name",nullable=true)
	public String getModuleDisplayName() {
		return moduleDisplayName;
	}

	/**
	 * @param moduleDisplayName the moduleDisplayName to set
	 */
	public void setModuleDisplayName(String moduleDisplayName) {
		this.moduleDisplayName = moduleDisplayName;
	}

	/**
	 * @return the activeYn
	 */
	@Column(name="active_yn",nullable=false)
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
	 * @return the moduleDisplaySeqNo
	 */
	@Column(name="module_disp_seq_no",nullable=true)
	public Integer getModuleDisplaySeqNo() {
		return moduleDisplaySeqNo;
	}

	/**
	 * @param moduleDisplaySeqNo the moduleDisplaySeqNo to set
	 */
	public void setModuleDisplaySeqNo(Integer moduleDisplaySeqNo) {
		this.moduleDisplaySeqNo = moduleDisplaySeqNo;
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

	@Column(name="display_yn")
	public Integer getDisplayYn() {
		return displayYn;
	}

	public void setDisplayYn(Integer displayYn) {
		this.displayYn = displayYn;
	}	
	
	/**
	 * @return the eppsProgramMasters
	 */
	/*@OneToMany(mappedBy = "eppsModuleMaster",fetch=FetchType.LAZY,orphanRemoval=true)
	@Cascade(CascadeType.SAVE_UPDATE)
	
	 * public Set<EppsProgramMaster> getEppsProgramMasters() { return
	 * eppsProgramMasters; }
	 */

	/**
	 * @param eppsProgramMasters the eppsProgramMasters to set
	 */
	/*
	 * public void setEppsProgramMasters(Set<EppsProgramMaster> eppsProgramMasters)
	 * { this.eppsProgramMasters = eppsProgramMasters; }
	 */
	

	/**
	 * @return the eppsTranIndicators
	 */
	/*
	 * @OneToMany(mappedBy =
	 * "eppsModuleMaster",fetch=FetchType.LAZY,orphanRemoval=true)
	 * 
	 * @Cascade(CascadeType.SAVE_UPDATE) public Set<EppsTranIndicator>
	 * getEppsTranIndicators() { return eppsTranIndicators; }
	 */

	/**
	 * @param eppsTranIndicators the eppsTranIndicators to set
	 */
	/*
	 * public void setEppsTranIndicators(Set<EppsTranIndicator> eppsTranIndicators)
	 * { this.eppsTranIndicators = eppsTranIndicators; }
	 */

	
}
