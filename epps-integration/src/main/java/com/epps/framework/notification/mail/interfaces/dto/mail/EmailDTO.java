package com.epps.framework.notification.mail.interfaces.dto.mail;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class EmailDTO {

	private String from;
	
	private String to;
	
	private String cc;
	
	private String bcc;
	
	private String subject;
	
	private String mailContent;
	
	private String recipientName;
	
	private String contactNo;
	
	private List<CommonsMultipartFile> attachemnts; 
	
	private List<MailAttachments> mailAttachemnts;
	
	private List<MailMimeBodyContent> mimeBodyContents;
	
	private List<File> mimeBodyAttachemnt;
	
	private String personalName;
	
	public EmailDTO() {
		super();
	}

	public EmailDTO(String from, String to, String cc, String bcc, String subject, String mailContent,
			String recipientName, String contactNo, List<CommonsMultipartFile> attachemnts,
			List<MailAttachments> mailAttachemnts, List<MailMimeBodyContent> mimeBodyContents,List<File> mimeBodyAttachemnt) {
		super();
		this.from = from;
		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
		this.subject = subject;
		this.mailContent = mailContent;
		this.recipientName = recipientName;
		this.contactNo = contactNo;
		this.attachemnts = attachemnts;
		this.mailAttachemnts = mailAttachemnts;
		this.mimeBodyContents = mimeBodyContents;
		this.setMimeBodyAttachemnt(mimeBodyAttachemnt);
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @return the cc
	 */
	public String getCc() {
		return cc;
	}

	/**
	 * @param cc the cc to set
	 */
	public void setCc(String cc) {
		this.cc = cc;
	}

	/**
	 * @return the bcc
	 */
	public String getBcc() {
		return bcc;
	}

	/**
	 * @param bcc the bcc to set
	 */
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the mailContent
	 */
	public String getMailContent() {
		return mailContent;
	}

	/**
	 * @param mailContent the mailContent to set
	 */
	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}
	
	/**
	 * @return the recipientName
	 */
	public String getRecipientName() {
		return recipientName;
	}

	/**
	 * @param recipientName the recipientName to set
	 */
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	/**
	 * @return the contactNo
	 */
	public String getContactNo() {
		return contactNo;
	}

	/**
	 * @param contactNo the contactNo to set
	 */
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	/**
	 * @return the attachemnts
	 */
	public List<CommonsMultipartFile> getAttachemnts() {
		return attachemnts;
	}

	/**
	 * @param attachemnts the attachemnts to set
	 */
	public void setAttachemnts(List<CommonsMultipartFile> attachemnts) {
		this.attachemnts = attachemnts;
	}

	/**
	 * @return the mailAttachemnts
	 */
	public List<MailAttachments> getMailAttachemnts() {
		return mailAttachemnts;
	}

	/**
	 * @param mailAttachemnts the mailAttachemnts to set
	 */
	public void setMailAttachemnts(List<MailAttachments> mailAttachemnts) {
		this.mailAttachemnts = mailAttachemnts;
	}

	/**
	 * @return the mimeBodyContents
	 */
	public List<MailMimeBodyContent> getMimeBodyContents() {
		return mimeBodyContents;
	}

	/**
	 * @param mimeBodyContents the mimeBodyContents to set
	 */
	public void setMimeBodyContents(List<MailMimeBodyContent> mimeBodyContents) {
		this.mimeBodyContents = mimeBodyContents;
	}

	/**
	 * @return the mimeBodyAttachemnt
	 */
	public List<File> getMimeBodyAttachemnt() {
		return mimeBodyAttachemnt;
	}

	/**
	 * @param mimeBodyAttachemnt the mimeBodyAttachemnt to set
	 */
	public void setMimeBodyAttachemnt(List<File> mimeBodyAttachemnt) {
		this.mimeBodyAttachemnt = mimeBodyAttachemnt;
	}

	public String getPersonalName() {
		return personalName;
	}

	public void setPersonalName(String personalName) {
		this.personalName = personalName;
	}
}
