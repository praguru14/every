package com.epps.framework.interfaces.responses;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ResponseInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6233913057047674309L;
	private Integer msgCode;
	private String msgType;
	private String userMessage;
	private String internalMessage;
	private String moreInfo;
	
	public ResponseInfo() {
		super();
	}
	
	public ResponseInfo(Integer msgCode, String msgType, String internalMessage, String userMessage,
			String moreInfo) {
		super();
		this.msgCode = msgCode;
		this.msgType = msgType;
		this.internalMessage = internalMessage;
		this.userMessage = userMessage;
		this.moreInfo = moreInfo;
	}

	/**
	 * @return the msgCode
	 */
	public Integer getMsgCode() {
		return msgCode;
	}

	/**
	 * @param msgCode the msgCode to set
	 */
	public void setMsgCode(Integer msgCode) {
		this.msgCode = msgCode;
	}

	/**
	 * @return the msgType
	 */
	public String getMsgType() {
		return msgType;
	}

	/**
	 * @param msgType the msgType to set
	 */
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	/**
	 * @return the internalMessage
	 */
	public String getInternalMessage() {
		return internalMessage;
	}

	/**
	 * @param internalMessage the internalMessage to set
	 */
	public void setInternalMessage(String internalMessage) {
		this.internalMessage = internalMessage;
	}

	/**
	 * @return the userMessage
	 */
	public String getUserMessage() {
		return userMessage;
	}

	/**
	 * @param userMessage the userMessage to set
	 */
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	/**
	 * @return the moreInfo
	 */
	public String getMoreInfo() {
		return moreInfo;
	}

	/**
	 * @param moreInfo the moreInfo to set
	 */
	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}
}
