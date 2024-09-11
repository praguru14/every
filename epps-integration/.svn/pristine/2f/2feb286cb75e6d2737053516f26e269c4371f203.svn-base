package com.epps.framework.infrastructure.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="epps_config_param", schema="epps_admin")
public class EppsConfigParams extends EppsBaseEntity {

/**
	 * 
	 */
	private static final long serialVersionUID = -8206699154376314985L;

//	private static final long serialVersionUID = 1L;

	private Integer paramHdrSrNo;
	
	private Integer compCd;
	
	private String webNotification;
	
	private String foreignCurrActive;
	
	private String costcenterActive;
	
	private String freightActive;
	
	private String blockLogin;
	
	private String blockReason;
	
	private String remarks;
	
	private String autoEmpCd;
	
	private String fileUploadPath;
	
	private String pdcYn;
	
	private String lcYn;
	
	private Long maxFileSize;

	private String barcodeSource;
	
	private String grnLabelPrintFormat;
	
	private String tdsYn;
	
	private String autoRoundTax;
	
	private Integer itemAutoManual;
	
	private Integer itemNameAutoManual;
	
	private Integer fcRateMstControl;
	
	private String loginUserField;
	
	private Integer noOfTranIndParam;
	
	private Date runDate;

	private String autoRgNoYn;

	private String prodSingleUom;

	private String mobNotification;

	private String fgrLabelPrintFormat;
	
	private String auditTrailYn;
	
	private Integer  updatorRoleCode;
	
	private String empGeneration;
	
	private Integer itemCodeLength;
	
	private String defaultItemDrawingPath;
	
	private Integer noOfLiscControl;
	
	private Integer mobNoOfLiscControl;
	
	private Integer mobSdNoOfLiscControl;
	
	private Integer crmNoOfLiscControl;
	
	private Integer defaultItemAccessLevel;
	
	private String parentContextName;
	
	private String crmContextName;
	
	private String parentContextUrl;
	
	private String crmContextUrl;

	private String wsmsContextName;
	private String wsmsContextUrl;
	private Integer wsmsActiveYn;
	
	private String backboneContextName;
	private String backboneContextUrl;
	private Integer backboneActiveYn;

	private String dashboardContextName;
	private String dashboardContextUrl;
	private Integer dashboardActiveYn;

	@Id
	@Column(name="param_hdr_sr_no")
	public Integer getParamHdrSrNo() {
		return paramHdrSrNo;
	}

	/**
	 * @param paramHdrSrNo the paramHdrSrNo to set
	 */
	public void setParamHdrSrNo(Integer paramHdrSrNo) {
		this.paramHdrSrNo = paramHdrSrNo;
	}

	/**
	 * @return the compCd
	 */
	@Column(name="comp_cd", nullable=false)
	public Integer getCompCd() {
		return compCd;
	}

	/**
	 * @param compCd the compCd to set
	 */
	public void setCompCd(Integer compCd) {
		this.compCd = compCd;
	}

	/**
	 * @return the webNotification
	 */
	@Column(name="web_notification", nullable=false, length=1)
	public String getWebNotification() {
		return webNotification;
	}

	/**
	 * @param webNotification the webNotification to set
	 */
	public void setWebNotification(String webNotification) {
		this.webNotification = webNotification;
	}

	/**
	 * @return the foreignCurrActive
	 */
	@Column(name="foreign_curr_active", nullable=false, length=1)
	public String getForeignCurrActive() {
		return foreignCurrActive;
	}

	/**
	 * @param foreignCurrActive the foreignCurrActive to set
	 */
	public void setForeignCurrActive(String foreignCurrActive) {
		this.foreignCurrActive = foreignCurrActive;
	}

	/**
	 * @return the costcenterActive
	 */
	@Column(name="costcenter_active", nullable=false, length=1)
	public String getCostcenterActive() {
		return costcenterActive;
	}

	/**
	 * @param costcenterActive the costcenterActive to set
	 */
	public void setCostcenterActive(String costcenterActive) {
		this.costcenterActive = costcenterActive;
	}

	/**
	 * @return the freightActive
	 */
	@Column(name="freight_active", nullable=false, length=1)
	public String getFreightActive() {
		return freightActive;
	}

	/**
	 * @param freightActive the freightActive to set
	 */
	public void setFreightActive(String freightActive) {
		this.freightActive = freightActive;
	}
 
	
	/**
	 * @return the blockLogin
	 */
	@Column(name="block_login", nullable=false, length=1)
	public String getBlockLogin() {
		return blockLogin;
	}

	/**
	 * @param blockLogin the blockLogin to set
	 */
	public void setBlockLogin(String blockLogin) {
		this.blockLogin = blockLogin;
	}

	/**
	 * @return the blockReason
	 */
	@Column(name="block_reason", nullable=true, length=100)
	public String getBlockReason() {
		return blockReason;
	}

	/**
	 * @param blockReason the blockReason to set
	 */
	public void setBlockReason(String blockReason) {
		this.blockReason = blockReason;
	}

	/**
	 * @return the remarks
	 */
	@Column(name="remarks", nullable=true, length=100)
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the autoEmpCd
	 */
	@Column(name="auto_emp_cd")
	public String getAutoEmpCd() {
		return autoEmpCd;
	}

	/**
	 * @param autoEmpCd the autoEmpCd to set
	 */
	public void setAutoEmpCd(String autoEmpCd) {
		this.autoEmpCd = autoEmpCd;
	}

	/**
	 * @return the fileUploadPath
	 */
	@Column(name="file_upload_path")
	public String getFileUploadPath() {
		return fileUploadPath;
	}

	/**
	 * @param fileUploadPath the fileUploadPath to set
	 */
	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}
	

	@Column(name="pdc_yn",   length=1)
	public String getPdcYn() {
		return pdcYn;
	}

	public void setPdcYn(String pdcYn) {
		this.pdcYn = pdcYn;
	}

	@Column(name="lc_yn",   length=1)
	public String getLcYn() {
		return lcYn;
	}

	public void setLcYn(String lcYn) {
		this.lcYn = lcYn;
	}

	/**
	 * @return the maxFileSize
	 */
	@Column(name="image_upload_size")
	public Long getMaxFileSize() {
		return maxFileSize;
	}

	/**
	 * @param maxFileSize the maxFileSize to set
	 */
	public void setMaxFileSize(Long maxFileSize) {
		this.maxFileSize = maxFileSize;
	}
	
	@Column(name="barcode_source", length=50)
	public String getBarcodeSource() {
		return barcodeSource;
	}

	public void setBarcodeSource(String barcodeSource) {
		this.barcodeSource = barcodeSource;
	}
	
	@Column(name="grn_label_print_format", length=10)
	public String getGrnLabelPrintFormat() {
		return grnLabelPrintFormat;
	}

	public void setGrnLabelPrintFormat(String grnLabelPrintFormat) {
		this.grnLabelPrintFormat = grnLabelPrintFormat;
	}

	@Column(name="tds_yn",length=1)
	public String getTdsYn() {
		return tdsYn;
	}

	public void setTdsYn(String tdsYn) {
		this.tdsYn = tdsYn;
	}
	
	@Column(name="auto_round_tax",length=1)
	public String getAutoRoundTax(){
		return autoRoundTax;
	}

	public void setAutoRoundTax(String autoRoundTax) {
		this.autoRoundTax = autoRoundTax;
	}

	/**
	 * @return the itemAutoManual
	 */
	@Column(name="item_auto_manual")
	public Integer getItemAutoManual() {
		return itemAutoManual;
	}

	/**
	 * @param itemAutoManual the itemAutoManual to set
	 */
	public void setItemAutoManual(Integer itemAutoManual) {
		this.itemAutoManual = itemAutoManual;
	}

	@Column(name="fc_rate_mst_control",nullable=false)
	public Integer getFcRateMstControl() {
		return fcRateMstControl;
	}

	public void setFcRateMstControl(Integer fcRateMstControl) {
		this.fcRateMstControl = fcRateMstControl;
	}

	@Column(name="login_user_field",length=10)
	public String getLoginUserField() {
		return loginUserField;
	}

	public void setLoginUserField(String loginUserField) {
		this.loginUserField = loginUserField;
	}

	@Column(name="	no_of_tind_param")
	public Integer getNoOfTranIndParam() {
		return noOfTranIndParam;
	}

	public void setNoOfTranIndParam(Integer noOfTranIndParam) {
		this.noOfTranIndParam = noOfTranIndParam;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "run_date")
	public Date getRunDate() {
		return runDate;
	}
	
	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}

	@Column(name = "auto_rg_no_yn" , length = 1)
	public String getAutoRgNoYn() {
		return autoRgNoYn;
	}

	public void setAutoRgNoYn(String autoRgNoYn) {
		this.autoRgNoYn = autoRgNoYn;
	}

	@Column(name = "prod_single_uom" , length = 1)
	public String getProdSingleUom() {
		return prodSingleUom;
	}

	public void setProdSingleUom(String prodSingleUom) {
		this.prodSingleUom = prodSingleUom;
	}

	@Column(name = "mob_notification" , length = 1)
	public String getMobNotification() {
		return mobNotification;
	}

	public void setMobNotification(String mobNotification) {
		this.mobNotification = mobNotification;
	}
	
	@Column(name = "fgr_label_print_format" , length = 10)
	public String getFgrLabelPrintFormat() {
		return fgrLabelPrintFormat;
	}

	public void setFgrLabelPrintFormat(String fgrLabelPrintFormat) {
		this.fgrLabelPrintFormat = fgrLabelPrintFormat;
	}

	@Column(name = "audit_trail_yn" , length = 1)
	public String getAuditTrailYn() {
		return auditTrailYn;
	}

	public void setAuditTrailYn(String auditTrailYn) {
		this.auditTrailYn = auditTrailYn;
	}
	
	@Column(name="updator_role_cd",insertable=false,updatable=false)
	public Integer getUpdatorRoleCode() {
		return updatorRoleCode;
	}

	public void setUpdatorRoleCode(Integer updatorRoleCode) {
		this.updatorRoleCode = updatorRoleCode;
	}

	/**
	 * @return the empGeneration
	 */
	@Column(name="emp_generation",nullable=false,length=1)
	public String getEmpGeneration() {
		return empGeneration;
	}

	/**
	 * @param empGeneration the empGeneration to set
	 */
	public void setEmpGeneration(String empGeneration) {
		this.empGeneration = empGeneration;
	}

	/**
	 * @return the itemCodeLength
	 */
	@Column(name="itemcode_limit")
	public Integer getItemCodeLength() {
		return itemCodeLength;
	}

	/**
	 * @param itemCodeLength the itemCodeLength to set
	 */
	public void setItemCodeLength(Integer itemCodeLength) {
		this.itemCodeLength = itemCodeLength;
	}

	/**
	 * @return the defaultItemDrawingPath
	 */
	@Column(name="default_item_drawing_path")
	public String getDefaultItemDrawingPath() {
		return defaultItemDrawingPath;
	}

	/**
	 * @param defaultItemDrawingPath the defaultItemDrawingPath to set
	 */
	public void setDefaultItemDrawingPath(String defaultItemDrawingPath) {
		this.defaultItemDrawingPath = defaultItemDrawingPath;
	}

	@Column(name="no_of_lisc_control")
	public Integer getNoOfLiscControl() {
		return noOfLiscControl;
	}

	public void setNoOfLiscControl(Integer noOfLiscControl) {
		this.noOfLiscControl = noOfLiscControl;
	}

	@Column(name="mob_no_of_lisc_control")
	public Integer getMobNoOfLiscControl() {
		return mobNoOfLiscControl;
	}

	public void setMobNoOfLiscControl(Integer mobNoOfLiscControl) {
		this.mobNoOfLiscControl = mobNoOfLiscControl;
	}

	@Column(name="mob_sd_no_of_lisc_control")
	public Integer getMobSdNoOfLiscControl() {
		return mobSdNoOfLiscControl;
	}

	public void setMobSdNoOfLiscControl(Integer mobSdNoOfLiscControl) {
		this.mobSdNoOfLiscControl = mobSdNoOfLiscControl;
	}

	/**
	 * @return the crmNoOfLiscControl
	 */
	@Column(name="crm_no_of_lisc_control")
	public Integer getCrmNoOfLiscControl() {
		return crmNoOfLiscControl;
	}

	/**
	 * @param crmNoOfLiscControl the crmNoOfLiscControl to set
	 */
	public void setCrmNoOfLiscControl(Integer crmNoOfLiscControl) {
		this.crmNoOfLiscControl = crmNoOfLiscControl;
	}

	/**
	 * @return the defaultItemAccessLevel
	 */
	@Column(name="item_access_level")
	public Integer getDefaultItemAccessLevel() {
		return defaultItemAccessLevel;
	}

	/**
	 * @param defaultItemAccessLevel the defaultItemAccessLevel to set
	 */
	public void setDefaultItemAccessLevel(Integer defaultItemAccessLevel) {
		this.defaultItemAccessLevel = defaultItemAccessLevel;
	}

	@Column(name="parent_context_name")
	public String getParentContextName() {
		return parentContextName;
	}

	public void setParentContextName(String parentContextName) {
		this.parentContextName = parentContextName;
	}
	
	@Column(name="crm_context_name")
	public String getCrmContextName() {
		return crmContextName;
	}

	public void setCrmContextName(String crmContextName) {
		this.crmContextName = crmContextName;
	}

	@Column(name="parent_context_url")
	public String getParentContextUrl() {
		return parentContextUrl;
	}

	public void setParentContextUrl(String parentContextUrl) {
		this.parentContextUrl = parentContextUrl;
	}

	@Column(name="crm_context_url")
	public String getCrmContextUrl() {
		return crmContextUrl;
	}

	public void setCrmContextUrl(String crmContextUrl) {
		this.crmContextUrl = crmContextUrl;
	}

	@Column(name="wsms_context_name",length=15)
	public String getWsmsContextName() {
		return wsmsContextName;
	}

	public void setWsmsContextName(String wsmsContextName) {
		this.wsmsContextName = wsmsContextName;
	}
	
	@Column(name="wsms_context_url",length=50)
	public String getWsmsContextUrl() {
		return wsmsContextUrl;
	}

	public void setWsmsContextUrl(String wsmsContextUrl) {
		this.wsmsContextUrl = wsmsContextUrl;
	}

	@Column(name="wsms_activeyn")
	public Integer getWsmsActiveYn() {
		return wsmsActiveYn;
	}

	public void setWsmsActiveYn(Integer wsmsActiveYn) {
		this.wsmsActiveYn = wsmsActiveYn;
	}

	@Column(name="backbone_context_name",length=15)
	public String getBackboneContextName() {
		return backboneContextName;
	}

	public void setBackboneContextName(String backboneContextName) {
		this.backboneContextName = backboneContextName;
	}

	
	@Column(name="backbone_context_url",length=50)
	public String getBackboneContextUrl() {
		return backboneContextUrl;
	}

	public void setBackboneContextUrl(String backboneContextUrl) {
		this.backboneContextUrl = backboneContextUrl;
	}

	
	@Column(name="backbone_activeyn")
	public Integer getBackboneActiveYn() {
		return backboneActiveYn;
	}

	public void setBackboneActiveYn(Integer backboneActiveYn) {
		this.backboneActiveYn = backboneActiveYn;
	}

	@Column(name="dashboard_context_name",length=15)
	public String getDashboardContextName() {
		return dashboardContextName;
	}

	public void setDashboardContextName(String dashboardContextName) {
		this.dashboardContextName = dashboardContextName;
	}

	@Column(name="dashboard_context_url",length=50)
	public String getDashboardContextUrl() {
		return dashboardContextUrl;
	}

	public void setDashboardContextUrl(String dashboardContextUrl) {
		this.dashboardContextUrl = dashboardContextUrl;
	}

	@Column(name="dashboard_activeyn")
	public Integer getDashboardActiveYn() {
		return dashboardActiveYn;
	}

	public void setDashboardActiveYn(Integer dashboardActiveYn) {
		this.dashboardActiveYn = dashboardActiveYn;
	}
	
	@Column(name="item_name_auto_mnual")
	public Integer getItemNameAutoManual() {
		return itemNameAutoManual;
	}

	public void setItemNameAutoManual(Integer itemNameAutoManual) {
		this.itemNameAutoManual = itemNameAutoManual;
	}
}