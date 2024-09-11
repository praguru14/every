package com.epps.framework.notification.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.epps.framework.application.util.logger.ApplicationLogger;
import com.epps.framework.notification.mail.application.constant.EmailServerPropertiesConstants;

public abstract class CompanyFilesData {

	public static final byte[] eppsCertificate = new byte[0];

	public static final byte[] companyLogo= new byte[0];

	public static final  Map<Integer,byte[]> divisionLogos = new HashMap<Integer,byte[]>();

	public static final EmailServerPropertiesConstants emailServerConstants = new EmailServerPropertiesConstants();

	private static final ApplicationLogger logger = new ApplicationLogger(CompanyFilesData.class);
	
	public static final String eppsType=null;
	
	public static Date currentDbDate;
	
	//public static Boolean DM_ACTIVE;  
	//public static Map<Integer,String> ewayErrorCodes = new HashMap<Integer,String>();
	//public static  final Boolean CRM_ACTIVE=false;
	//public static  final Boolean WSMS_ACTIVE=false;
	
	
	public static String getComapanyLogoInString(){
		if(companyLogo != null){
			return getImageBase64String(companyLogo);
		}else{
			return null;
		}
	}

	public static String getDivisionLogoInString(Integer divisionCode){
		return getImageBase64String(divisionLogos.get(divisionCode));
	}

	public static String getImageBase64String(byte[] image){
		try {
			if(image != null && image.length > 0){
				byte[] encodeBase64 = Base64.encodeBase64(image);
				return new String(encodeBase64, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("Error In Reading Image File: "+e.getMessage());

		}
		return null;
	}
	
	public static EmailServerPropertiesConstants getEmailServerProperties(){
		return emailServerConstants;
	}
}
