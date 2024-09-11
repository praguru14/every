package com.epps.framework.notification.mail.application.constant;

public class EmailServerPropertiesConstants {

	private String host;
	
	private Integer port;
	
	private String username;
	
	private String protocol;
	
	private String password;
	
	//Properties
	private String isAuth;
	
	private String isSSL;
	
	private String isDebug;
	
	private String smsUrl;
	
	private String smsReqMethod;
	
	private String smsNumbersKey;
	
	private String smsMessageKey;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(String isAuth) {
		this.isAuth = isAuth;
	}

	public String getIsDebug() {
		return isDebug;
	}

	public void setIsDebug(String isDebug) {
		this.isDebug = isDebug;
	}

	/**
	 * @return the isSSL
	 */
	public String getIsSSL() {
		return isSSL;
	}

	/**
	 * @param isSSL the isSSL to set
	 */
	public void setIsSSL(String isSSL) {
		this.isSSL = isSSL;
	}

	/**
	 * @return the smsUrl
	 */
	public String getSmsUrl() {
		return smsUrl;
	}

	/**
	 * @param smsUrl the smsUrl to set
	 */
	public void setSmsUrl(String smsUrl) {
		this.smsUrl = smsUrl;
	}

	/**
	 * @return the smsReqMethod
	 */
	public String getSmsReqMethod() {
		return smsReqMethod;
	}

	/**
	 * @param smsReqMethod the smsReqMethod to set
	 */
	public void setSmsReqMethod(String smsReqMethod) {
		this.smsReqMethod = smsReqMethod;
	}

	/**
	 * @return the smsNumbersKey
	 */
	public String getSmsNumbersKey() {
		return smsNumbersKey;
	}

	/**
	 * @param smsNumbersKey the smsNumbersKey to set
	 */
	public void setSmsNumbersKey(String smsNumbersKey) {
		this.smsNumbersKey = smsNumbersKey;
	}

	/**
	 * @return the smsMessageKey
	 */
	public String getSmsMessageKey() {
		return smsMessageKey;
	}

	/**
	 * @param smsMessageKey the smsMessageKey to set
	 */
	public void setSmsMessageKey(String smsMessageKey) {
		this.smsMessageKey = smsMessageKey;
	}
}
