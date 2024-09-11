package com.epps.framework.notification.sms.interfaces.dto;

import java.util.HashMap;
import java.util.Map;

public class SmsDTO {
	
	private String mobileNo;
	
	private Map<String, Object> smsParameters;
	
	public SmsDTO(){
		smsParameters = new HashMap<>();
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the smsParameters
	 */
	public Map<String, Object> getSmsParameters() {
		return smsParameters;
	}

	/**
	 * @param smsParameters the smsParameters to set
	 */
	public void setSmsParameters(Map<String, Object> smsParameters) {
		this.smsParameters = smsParameters;
	}
}
