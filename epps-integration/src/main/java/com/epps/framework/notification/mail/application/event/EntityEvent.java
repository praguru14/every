package com.epps.framework.notification.mail.application.event;

import com.epps.framework.notification.mail.application.event.annotation.SendEvent;

public class EntityEvent implements Event{
	
	
	private String pId;
	
	private String tranIndicator;
	
	private String ecodeField;
	
	private String mtqrFlag;

	private Object object;
	
	private Boolean isScheduled;
	
	private String partyCodeField;
	
	private String partyAddCodeField;
	
	private String partyType;
	
	private String nextUpdatorField;
	
	private String reportUri;
	
	private String subScreenNm;
	
	private String locationCodeField;
	
	private Boolean isPartyTypeInHdr;
	
	private String[] predefinedEvents;
	
	private String compCdField;
	
	private String divCdField;
	
	private Boolean isSingleScreenMultiPid;
	
	private String compositePkGetterNm;
	
	//private String detailReportUri;
	
	

	public EntityEvent() {
		super();
	}
	
	public EntityEvent(SendEvent event,Object dataObject) {
		super();
		this.pId = event.pId();
		this.tranIndicator = event.tranIndicator();
		this.mtqrFlag = event.mtqrFlag();
		this.object = dataObject;
		this.isScheduled = event.isScheduled();
		this.partyCodeField = event.partyCodeField();
		this.partyAddCodeField = event.partyAddCodeField();
		this.partyType= event.partyType();
		this.nextUpdatorField = event.nextUpdatorField();
		this.isPartyTypeInHdr = event.isPartyTypeInHdr();
		this.reportUri = event.reportUri();
		this.ecodeField =event.ecodeField();
		this.subScreenNm = event.subScreenNm();
		this.locationCodeField = event.locationCodeField();
		this.predefinedEvents = event.predefinedEvents();
		this.compCdField = event.compCdField();
		this.divCdField = event.divCdField();
		this.isSingleScreenMultiPid = event.isSingleScreenMultiPid();
		this.compositePkGetterNm = event.compositePkGetterNm();
	}
	

	public EntityEvent(String pId, String tranIndicator, String mtqrFlag,
			Object object, Boolean isScheduled, String partyCodeField,
			String partyAddCodeField,String partyType, String nextUpdatorField,String reportUri,String eCodeField) {
		super();
		this.pId = pId;
		this.tranIndicator = tranIndicator;
		this.ecodeField=eCodeField;
		this.mtqrFlag = mtqrFlag;
		this.object = object;
		this.isScheduled = isScheduled;
		this.partyCodeField = partyCodeField;
		this.partyAddCodeField = partyAddCodeField;
		this.partyType= partyType;
		this.nextUpdatorField = nextUpdatorField;
		this.reportUri = reportUri;
	}

	/**
	 * @return the pId
	 */
	public String getpId() {
		return pId;
	}


	/**
	 * @param pId the pId to set
	 */
	public void setpId(String pId) {
		this.pId = pId;
	}


	/**
	 * @return the tranIndicator
	 */
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
	 * @return the mtqrFlag
	 */
	public String getMtqrFlag() {
		return mtqrFlag;
	}


	/**
	 * @param mtqrFlag the mtqrFlag to set
	 */
	public void setMtqrFlag(String mtqrFlag) {
		this.mtqrFlag = mtqrFlag;
	}
	
	/**
	 * @return the ecodeField
	 */
	public String getEcodeField() {
		return ecodeField;
	}

	/**
	 * @param ecodeField the ecodeField to set
	 */
	public void setEcodeField(String ecodeField) {
		this.ecodeField = ecodeField;
	}

	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}


	/**
	 * @param object the object to set
	 */
	public void setObject(Object object) {
		this.object = object;
	}


	/**
	 * @return the isScheduled
	 */
	public Boolean getIsScheduled() {
		return isScheduled;
	}


	/**
	 * @param isScheduled the isScheduled to set
	 */
	public void setIsScheduled(Boolean isScheduled) {
		this.isScheduled = isScheduled;
	}


	/**
	 * @return the partyCodeField
	 */
	public String getPartyCodeField() {
		return partyCodeField;
	}


	/**
	 * @param partyCodeField the partyCodeField to set
	 */
	public void setPartyCodeField(String partyCodeField) {
		this.partyCodeField = partyCodeField;
	}


	/**
	 * @return the partyAddCodeField
	 */
	public String getPartyAddCodeField() {
		return partyAddCodeField;
	}


	/**
	 * @param partyAddCodeField the partyAddCodeField to set
	 */
	public void setPartyAddCodeField(String partyAddCodeField) {
		this.partyAddCodeField = partyAddCodeField;
	}
	
	/**
	 * @return the partyType
	 */
	public String getPartyType() {
		return partyType;
	}


	/**
	 * @param partyType the partyType to set
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}


	/**
	 * @return the nextUpdatorField
	 */
	public String getNextUpdatorField() {
		return nextUpdatorField;
	}


	/**
	 * @param nextUpdatorField the nextUpdatorField to set
	 */
	public void setNextUpdatorField(String nextUpdatorField) {
		this.nextUpdatorField = nextUpdatorField;
	}


	/**
	 * @return the reportUri
	 */
	public String getReportUri() {
		return reportUri;
	}


	/**
	 * @param reportUri the reportUri to set
	 */
	public void setReportUri(String reportUri) {
		this.reportUri = reportUri;
	}

	/**
	 * @return the subScreenNm
	 */
	public String getSubScreenNm() {
		return subScreenNm;
	}

	/**
	 * @param subScreenNm the subScreenNm to set
	 */
	public void setSubScreenNm(String subScreenNm) {
		this.subScreenNm = subScreenNm;
	}

	/**
	 * @return the isPartyTypeInHdr
	 */
	public Boolean getIsPartyTypeInHdr() {
		return isPartyTypeInHdr;
	}

	/**
	 * @param isPartyTypeInHdr the isPartyTypeInHdr to set
	 */
	public void setIsPartyTypeInHdr(Boolean isPartyTypeInHdr) {
		this.isPartyTypeInHdr = isPartyTypeInHdr;
	}

	/**
	 * @return the locationCodeField
	 */
	public String getLocationCodeField() {
		return locationCodeField;
	}

	/**
	 * @param locationCodeField the locationCodeField to set
	 */
	public void setLocationCodeField(String locationCodeField) {
		this.locationCodeField = locationCodeField;
	}


	public String[] getPredefinedEvents() {
		return predefinedEvents;
	}

	public void setPredefinedEvents(String[] predefinedEvents) {
		this.predefinedEvents = predefinedEvents;
	}

	/**
	 * @return the compCdField
	 */
	public String getCompCdField() {
		return compCdField;
	}

	/**
	 * @param compCdField the compCdField to set
	 */
	public void setCompCdField(String compCdField) {
		this.compCdField = compCdField;
	}

	/**
	 * @return the divCdField
	 */
	public String getDivCdField() {
		return divCdField;
	}

	/**
	 * @param divCdField the divCdField to set
	 */
	public void setDivCdField(String divCdField) {
		this.divCdField = divCdField;
	}

	/**
	 * @return the isSingleScreenMultiPid
	 */
	public Boolean getIsSingleScreenMultiPid() {
		return isSingleScreenMultiPid;
	}

	/**
	 * @param isSingleScreenMultiPid the isSingleScreenMultiPid to set
	 */
	public void setIsSingleScreenMultiPid(Boolean isSingleScreenMultiPid) {
		this.isSingleScreenMultiPid = isSingleScreenMultiPid;
	}

	/**
	 * @return the compositePkGetterNm
	 */
	public String getCompositePkGetterNm() {
		return compositePkGetterNm;
	}

	/**
	 * @param compositePkGetterNm the compositePkGetterNm to set
	 */
	public void setCompositePkGetterNm(String compositePkGetterNm) {
		this.compositePkGetterNm = compositePkGetterNm;
	}	
	
}
