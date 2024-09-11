package com.epps.framework.notification.mail.interfaces.dto.mail;

/**
 * Class contains attributes for sending mime body part data through mail.
 * @author Aniket Jadhav
 * 
 *
 */
public class MailMimeBodyContent {
	
	private final byte[] byteDataSource;
	private final String mimeType;
	private final String headerValue;
	
	public MailMimeBodyContent(byte[] byteDataSource, String mimeType, String headerValue) {
		super();
		this.byteDataSource = byteDataSource;
		this.mimeType = mimeType;
		this.headerValue = headerValue;
	}

	public byte[] getByteDataSource() {
		return byteDataSource;
	}

	public String getMimeType() {
		return mimeType;
	}

	public String getHeaderValue() {
		return headerValue;
	}
	
	
	
	
	
	

}
