package com.epps.framework.notification.mail.application.event.entities;

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
@Table(name="epps_prog_mst",schema="epps_admin")
@DynamicInsert @DynamicUpdate
public class EppsProgramMaster extends EppsBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5435695055436680959L;

	private EppsProgramMasterPK eppsProgramMasterPK;
	
	private String programId;
	
	private String programDisplayName;
	
	private String programLongName;
	
	private String programType;
	
	private Integer parentId;
	
	private String activeYn;
	
	private String programDevelopedBy;
	
	private String programMenuDisplayYn;
	
	private Integer programDisplaySeqNo;
	
	private String menuPassParameter;
	
	private String programDocId;
	
	private String programReportDisplayName;
	
	private String programApplicationFlag;
		
	private String programMtqrFlag;
	
//	mobActiveYn column removed 23-07-2016
//	private String mobActiveYn;

	private String mobDisplayName;
	
	private String mobSubMenuFlag;
	
	private String mobMenuPassParameter;
	
	private String defaultAccessFlag;
	
	private String eppsAdminFlag;
	
	private String screenRoleFlag;
	
	private String tranIndicator;
	
	private String reportType;
	
	private String moduleId;
	
	private String reportDescription;
	
	private String sessionReqYn;//session_req_yn
	
	private Integer updatorRoleCode;
	
	//private String flag4SubTranRef;
	
	private String mwaFlag;
	
	private Integer repMaxDays;
	
	private String approvalFlag;
	
	private String progTreeDelFlag;
	
	private String mobEcsFlag;
	
	private String allowDownloadExcel;
	
	private String notiConfigYn;
	
	private String javaNotificationClsName;
	
	private String repDispName;
	
	private Integer multiScreenYn;
	
	private String dbCtType;
	
	private String dtParamType;
	
	private String viewEntityName;
	
	/*
private EppsModuleMaster eppsModuleMaster;
	
	private EppsProgMtqrAllowed eppsProgMtqrAllowed;
	
	private EppsTranIndicator eppsTranIndicator;
	
	private Set<EppsRoleProgramLink> eppsRoleProgramLinks;
	
	private Set<EppsEcodeProgramLink> eppsEcodeProgramLinks;
	
	private Set<EppsMobSupplierProgramLink> eppsMobSupplierProgramLinks;
	*/
	
	
	@Id
	@AttributeOverrides({
        @AttributeOverride(name = "companyCode", column = @Column(name = "comp_cd")),
        @AttributeOverride(name = "programeCode", column = @Column(name = "prog_cd")) })
	public EppsProgramMasterPK getEppsProgramMasterPK() {
		return eppsProgramMasterPK;
	}

	
	public void setEppsProgramMasterPK(EppsProgramMasterPK eppsProgramMasterPK) {
		this.eppsProgramMasterPK = eppsProgramMasterPK;
	}
	
	@Column(name="prog_id",nullable=true,length=40,unique=true)
	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	
	@Column(name="prog_disp_name",nullable=false,length=100)
	public String getProgramDisplayName() {
		return programDisplayName;
	}

	
	public void setProgramDisplayName(String programDisplayName) {
		this.programDisplayName = programDisplayName;
	}

	/**
	 * @return the programLongName
	 */
	@Column(name="prog_long_name",nullable=true,length=2000)
	public String getProgramLongName() {
		return programLongName;
	}

	/**
	 * @param programLongName the programLongName to set
	 */
	public void setProgramLongName(String programLongName) {
		this.programLongName = programLongName;
	}

	/**
	 * @return the programType
	 */
	@Column(name="prog_type",nullable=false,length=1)
	public String getProgramType() {
		return programType;
	}

	/**
	 * @param programType the programType to set
	 */
	public void setProgramType(String programType) {
		this.programType = programType;
	}

	/**
	 * @return the parentId
	 */
	@Column(name="parent_id",nullable=true)
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the activeYn
	 */
	@Column(name="active_yn",nullable=true,length=1)
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
	 * @return the programDevelopedBy
	 */
	@Column(name="prog_developed_by",nullable=true,length=10)
	public String getProgramDevelopedBy() {
		return programDevelopedBy;
	}

	/**
	 * @param programDevelopedBy the programDevelopedBy to set
	 */
	public void setProgramDevelopedBy(String programDevelopedBy) {
		this.programDevelopedBy = programDevelopedBy;
	}

	/**
	 * @return the programMenuDisplayYn
	 */
	@Column(name="prog_menu_display_yn",nullable=true,length=1)
	public String getProgramMenuDisplayYn() {
		return programMenuDisplayYn;
	}

	/**
	 * @param programMenuDisplayYn the programMenuDisplayYn to set
	 */
	public void setProgramMenuDisplayYn(String programMenuDisplayYn) {
		this.programMenuDisplayYn = programMenuDisplayYn;
	}

	/**
	 * @return the programDisplaySeqNo
	 */
	@Column(name="prog_disp_seq_no",nullable=true)
	public Integer getProgramDisplaySeqNo() {
		return programDisplaySeqNo;
	}

	/**
	 * @param programDisplaySeqNo the programDisplaySeqNo to set
	 */
	public void setProgramDisplaySeqNo(Integer programDisplaySeqNo) {
		this.programDisplaySeqNo = programDisplaySeqNo;
	}

	/**
	 * @return the menuPassParameter
	 */
	@Column(name="menu_pass_parameter",nullable=true,length=50)
	public String getMenuPassParameter() {
		return menuPassParameter;
	}

	/**
	 * @param menuPassParameter the menuPassParameter to set
	 */
	public void setMenuPassParameter(String menuPassParameter) {
		this.menuPassParameter = menuPassParameter;
	}

	/**
	 * @return the programDocId
	 */
	@Column(name="prog_doc_id",nullable=true,length=25)
	public String getProgramDocId() {
		return programDocId;
	}

	/**
	 * @param programDocId the programDocId to set
	 */
	public void setProgramDocId(String programDocId) {
		this.programDocId = programDocId;
	}

	/**
	 * @return the programReportDisplayName
	 */
	@Column(name="prog_report_name",nullable=true,length=50)
	public String getProgramReportDisplayName() {
		return programReportDisplayName;
	}

	/**
	 * @param programReportDisplayName the programReportDisplayName to set
	 */
	public void setProgramReportDisplayName(String programReportDisplayName) {
		this.programReportDisplayName = programReportDisplayName;
	}

	/**
	 * @return the programApplicationFlag
	 */
	@Column(name="prog_app_flag",nullable=true,length=1)
	public String getProgramApplicationFlag() {
		return programApplicationFlag;
	}

	/**
	 * @param programApplicationFlag the programApplicationFlag to set
	 */
	public void setProgramApplicationFlag(String programApplicationFlag) {
		this.programApplicationFlag = programApplicationFlag;
	}

	/**
	 * @return the programMtqrFlag
	 */
	@Column(name="prog_mtqr_flag",nullable=true,length=1)
	public String getProgramMtqrFlag() {
		return programMtqrFlag;
	}

	/**
	 * @param programMtqrFlag the programMtqrFlag to set
	 */
	public void setProgramMtqrFlag(String programMtqrFlag) {
		this.programMtqrFlag = programMtqrFlag;
	}

//	/**
//	 * @return the mobActiveYn
//	 */
//	@Column(name="mob_active_yn",nullable=true,length=1)
//	public String getMobActiveYn() {
//		return mobActiveYn;
//	}
//
//	/**
//	 * @param mobActiveYn the mobActiveYn to set
//	 */
//	public void setMobActiveYn(String mobActiveYn) {
//		this.mobActiveYn = mobActiveYn;
//	}

	/**
	 * @return the mobDisplayName
	 */
	@Column(name="mob_disp_name",nullable=true,length=50)
	public String getMobDisplayName() {
		return mobDisplayName;
	}

	/**
	 * @param mobDisplayName the mobDisplayName to set
	 */
	public void setMobDisplayName(String mobDisplayName) {
		this.mobDisplayName = mobDisplayName;
	}

	/**
	 * @return the mobSubMenuFlag
	 */
	@Column(name="mob_sub_menu_flag",nullable=true,length=1)
	public String getMobSubMenuFlag() {
		return mobSubMenuFlag;
	}

	/**
	 * @param mobSubMenuFlag the mobSubMenuFlag to set
	 */
	public void setMobSubMenuFlag(String mobSubMenuFlag) {
		this.mobSubMenuFlag = mobSubMenuFlag;
	}

	/**
	 * @return the mobMenuPassParameter
	 */
	@Column(name="mob_menu_pass_para",nullable=true,length=30)
	public String getMobMenuPassParameter() {
		return mobMenuPassParameter;
	}

	/**
	 * @param mobMenuPassParameter the mobMenuPassParameter to set
	 */
	public void setMobMenuPassParameter(String mobMenuPassParameter) {
		this.mobMenuPassParameter = mobMenuPassParameter;
	}

	/**
	 * @return the defaultAccessFlag
	 */
	@Column(name="default_access_flag",nullable=true,length=1)
	public String getDefaultAccessFlag() {
		return defaultAccessFlag;
	}

	/**
	 * @param defaultAccessFlag the defaultAccessFlag to set
	 */
	public void setDefaultAccessFlag(String defaultAccessFlag) {
		this.defaultAccessFlag = defaultAccessFlag;
	}

	/**
	 * @return the eppsAdminFlag
	 */
	@Column(name="epps_admin_flag",nullable=true,length=1)
	public String getEppsAdminFlag() {
		return eppsAdminFlag;
	}

	/**
	 * @param eppsAdminFlag the eppsAdminFlag to set
	 */
	public void setEppsAdminFlag(String eppsAdminFlag) {
		this.eppsAdminFlag = eppsAdminFlag;
	}

	/**
	 * @return the screenRoleFlag
	 */
	@Column(name="screen_role_flag",nullable=true,length=25)
	public String getScreenRoleFlag() {
		return screenRoleFlag;
	}

	/**
	 * @param screenRoleFlag the screenRoleFlag to set
	 */
	public void setScreenRoleFlag(String screenRoleFlag) {
		this.screenRoleFlag = screenRoleFlag;
	}

	/**
	 * @return the tranIndicator
	 */
	@Column(name="tran_indicator",nullable=true,length=20)
	public String getTranIndicator() {
		return tranIndicator;
	}

	/**
	 * @param tranIndicator the tranIndicator to set
	 */
	public void setTranIndicator(String tranIndicator) {
		this.tranIndicator = tranIndicator;
	}

	/**
	 * @return the reportType
	 */
	@Column(name="rep_type",nullable=true,length=1)
	public String getReportType() {
		return reportType;
	}

	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	
	/**
	 * @return the moduleId
	 */
	@Column(name="module_id",length=6)
	public String getModuleId() {
		return moduleId;
	}

	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * @return the reportDescription
	 */
	@Column(name="report_description",length=250)
	public String getReportDescription() {
		return reportDescription;
	}

	/**
	 * @param reportDescription the reportDescription to set
	 */
	public void setReportDescription(String reportDescription) {
		this.reportDescription = reportDescription;
	}
	
	
	/**
	 * @return the sessionReqYn
	 */
	@Column(name="session_req_yn",length=1)
	public String getSessionReqYn() {
		return sessionReqYn;
	}

	/**
	 * @param sessionReqYn the sessionReqYn to set
	 */
	public void setSessionReqYn(String sessionReqYn) {
		this.sessionReqYn = sessionReqYn;
	}

	/*@Column(name="flag4sub_tran_ref" ,length=15)
	public String getFlag4SubTranRef() {
		return flag4SubTranRef;
	}

	public void setFlag4SubTranRef(String flag4SubTranRef) {
		this.flag4SubTranRef = flag4SubTranRef;
	}
*/
	/*
		
	*//**
	 * @return the tiCode
	 *//*
	@Column(name="ti_code",nullable=false,length=20)
	public String getTiCode() {
		return tiCode;
	}

	*//**
	 * @param tiCode the tiCode to set
	 *//*
	public void setTiCode(String tiCode) {
		this.tiCode = tiCode;
	}
*/

	
	@Column(name="mwa_flag",length=1)
	public String getMwaFlag() {
		return mwaFlag;
	}

	public void setMwaFlag(String mwaFlag) {
		this.mwaFlag = mwaFlag;
	}

	@Column(name="rep_max_days")
	public Integer getRepMaxDays() {
		return repMaxDays;
	}

	public void setRepMaxDays(Integer repMaxDays) {
		this.repMaxDays = repMaxDays;
	}

	@Column(name="approval_flag",length=20)
	public String getApprovalFlag() {
		return approvalFlag;
	}

	public void setApprovalFlag(String approvalFlag) {
		this.approvalFlag = approvalFlag;
	}

	@Column(name="prog_tree_del_flag",length=20)
	public String getProgTreeDelFlag() {
		return progTreeDelFlag;
	}

	public void setProgTreeDelFlag(String progTreeDelFlag) {
		this.progTreeDelFlag = progTreeDelFlag;
	}

	@Column(name="mob_ecs_flag",length=20)
	public String getMobEcsFlag() {
		return mobEcsFlag;
	}

	public void setMobEcsFlag(String mobEcsFlag) {
		this.mobEcsFlag = mobEcsFlag;
	}

	@Column(name="allow_excel_down_yn",length=1)
	public String getAllowDownloadExcel() {
		return allowDownloadExcel;
	}

	public void setAllowDownloadExcel(String allowDownloadExcel) {
		this.allowDownloadExcel = allowDownloadExcel;
	}
	

	/**
	 * @return the notiConfigYn
	 */
	@Column(name="noti_config_yn")
	public String getNotiConfigYn() {
		return notiConfigYn;
	}

	/**
	 * @param notiConfigYn the notiConfigYn to set
	 */
	public void setNotiConfigYn(String notiConfigYn) {
		this.notiConfigYn = notiConfigYn;
	}

	/**
	 * @return the javaNotificationClsName
	 */
	@Column(name="java_notification_cls_name")
	public String getJavaNotificationClsName() {
		return javaNotificationClsName;
	}

	/**
	 * @param javaNotificationClsName the javaNotificationClsName to set
	 */
	public void setJavaNotificationClsName(String javaNotificationClsName) {
		this.javaNotificationClsName = javaNotificationClsName;
	}

	@Column(name="rep_disp_name",length=100)
	public String getRepDispName() {
		return repDispName;
	}

	public void setRepDispName(String repDispName) {
		this.repDispName = repDispName;
	}

	/**
	 * @return the multiScreenYn
	 */
	@Column(name="multi_screen_yn")
	public Integer getMultiScreenYn() {
		return multiScreenYn;
	}

	/**
	 * @param multiScreenYn the multiScreenYn to set
	 */
	public void setMultiScreenYn(Integer multiScreenYn) {
		this.multiScreenYn = multiScreenYn;
	}
	@Column(name="db_ct_type",length=1)
	public String getDbCtType() {
		return dbCtType;
	}

	public void setDbCtType(String dbCtType) {
		this.dbCtType = dbCtType;
	}
	@Column(name="dt_param_type",length=1)
	public String getDtParamType() {
		return dtParamType;
	}

	public void setDtParamType(String dtParamType) {
		this.dtParamType = dtParamType;
	}

	@Column(name="view_entity_name",length=500)
	public String getViewEntityName() {
		return viewEntityName;
	}

	public void setViewEntityName(String viewEntityName) {
		this.viewEntityName = viewEntityName;
	}

	
/*
	@ManyToOne(fetch=FetchType.LAZY)
	@Cascade (value=CascadeType.SAVE_UPDATE)
	@JoinColumns({
		@JoinColumn(name="comp_cd",referencedColumnName="comp_cd",insertable=false,updatable=false),
		@JoinColumn(name="prog_mtqr_flag",referencedColumnName="prog_mtqr_flag",insertable=false,updatable=false)})
	public EppsProgMtqrAllowed getEppsProgMtqrAllowed() {
		return eppsProgMtqrAllowed;
	}


	public void setEppsProgMtqrAllowed(EppsProgMtqrAllowed eppsProgMtqrAllowed) {
		this.eppsProgMtqrAllowed = eppsProgMtqrAllowed;
	}

	
	@ManyToOne(fetch=FetchType.LAZY)
	@Cascade (value=CascadeType.SAVE_UPDATE)
	@JoinColumns({
		@JoinColumn(name="comp_cd",referencedColumnName="comp_cd",insertable=false,updatable=false),
		@JoinColumn(name="tran_indicator",referencedColumnName="ti_code",insertable=false,updatable=false)})
	public EppsTranIndicator getEppsTranIndicator() {
		return eppsTranIndicator;
	}

	
	public void setEppsTranIndicator(EppsTranIndicator eppsTranIndicator) {
		this.eppsTranIndicator = eppsTranIndicator;
	}
	
		@ManyToOne(fetch=FetchType.LAZY)
	@Cascade (value=CascadeType.SAVE_UPDATE)
	@JoinColumns({
		@JoinColumn(name="comp_cd",referencedColumnName="comp_cd",insertable=false,updatable=false),
		@JoinColumn(name="module_id",referencedColumnName="module_id",insertable=false,updatable=false)})
	public EppsModuleMaster getEppsModuleMaster() {
		return eppsModuleMaster;
	}

	
	public void setEppsModuleMaster(EppsModuleMaster eppsModuleMaster) {
		this.eppsModuleMaster = eppsModuleMaster;
	}

	
	@OneToMany(mappedBy = "eppsProgramMaster",fetch=FetchType.LAZY,orphanRemoval=true)
	@Cascade(CascadeType.SAVE_UPDATE)
	public Set<EppsRoleProgramLink> getEppsRoleProgramLinks() {
		return eppsRoleProgramLinks;
	}

	
	public void setEppsRoleProgramLinks(
			Set<EppsRoleProgramLink> eppsRoleProgramLinks) {
		this.eppsRoleProgramLinks = eppsRoleProgramLinks;
	}

	@OneToMany(mappedBy = "eppsProgramMaster",fetch=FetchType.LAZY,orphanRemoval=true)
	@Cascade(CascadeType.SAVE_UPDATE)
	public Set<EppsEcodeProgramLink> getEppsEcodeProgramLinks() {
		return eppsEcodeProgramLinks;
	}

	public void setEppsEcodeProgramLinks(
			Set<EppsEcodeProgramLink> eppsEcodeProgramLinks) {
		this.eppsEcodeProgramLinks = eppsEcodeProgramLinks;
	}

	@OneToMany(mappedBy = "eppsProgramMaster",fetch=FetchType.LAZY,orphanRemoval=true)
	@Cascade(CascadeType.SAVE_UPDATE)
	public Set<EppsMobSupplierProgramLink> getEppsMobSupplierProgramLinks() {
		return eppsMobSupplierProgramLinks;
	}

	public void setEppsMobSupplierProgramLinks(Set<EppsMobSupplierProgramLink> eppsMobSupplierProgramLinks) {
		this.eppsMobSupplierProgramLinks = eppsMobSupplierProgramLinks;
	}

	
	*/
}
