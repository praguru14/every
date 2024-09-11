package com.epps.framework.notification.mail.application.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.epps.framework.application.util.logger.ApplicationLogger;
import com.epps.framework.domain.exception.ApplicationException;
import com.epps.framework.domain.exception.ErrorCode;
import com.epps.framework.domain.exception.ResponseInfoType;
import com.epps.framework.notification.mail.application.file.MailFileLoader;
import com.epps.framework.notification.mail.application.file.MailFileUtility;
import com.epps.framework.notification.mail.interfaces.dto.data.EMailType;
import com.epps.framework.notification.mail.interfaces.dto.data.InlineBinaryContent;
import com.epps.framework.notification.mail.interfaces.dto.data.MimeType;
import com.epps.framework.notification.mail.interfaces.dto.mail.Mail;
import com.epps.framework.notification.mail.interfaces.dto.mail.MailAttachments;
import com.epps.framework.notification.mail.interfaces.dto.mail.MailHeader;
import com.epps.framework.notification.mail.interfaces.dto.mail.MailMimeBodyContent;

@Component("mailManager")
public class MailManager {

private static final ApplicationLogger logger = new ApplicationLogger(MailManager.class);
	
	@Autowired
	private JavaMailSenderImpl mailSender;

	@Autowired
	private MailFileLoader mailFileLoader;


	/**
	 * Method sends the mail through the system. No attachments processed
	 * 
	 * @param mail
	 * @throws HerdNetException
	 */
	public void sendMail(final Mail mail) throws ApplicationException {
		MimeMessage message = null;
		try {
			if(mail.getAttachments() != null){
				message = this.setupMail(mail, true);
			}else{
				message = this.setupMail(mail, false);
			}
		} catch (final MessagingException e) {
			logger.error("sendMail: Failure creating mail", e);
			throw new ApplicationException("Failure creating mail instance", ErrorCode.UNEXPECTED_ERROR, ResponseInfoType.ERROR);
		}
		try {
			logger.debug("sendMail: Sending mail  ");
			this.mailSender.send(message);
		} catch (final MailException ex) {
			logger.error("sendMail: Failure sending mail", ex);
			throw new ApplicationException("Failure sending mail", ErrorCode.UNEXPECTED_ERROR, ResponseInfoType.ERROR);
		}
		logger.debug("sendMail:  mail sent successfully ");
	}

	/**
	 * Method is responsible for setting up the {@link MimeMessage} instance
	 * 
	 * @param mail
	 * @param helper
	 * @param processAttachments
	 * @return
	 * @throws MessagingException
	 */
	private MimeMessage setupMail(final Mail mail, final boolean processAttachments) throws MessagingException {
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		MimeMessageHelper helper = null;
		// as images are sent embedded we need second param true here too
		if (processAttachments || ((null != mail.getBinaryContent()) && mail.getBinaryContent().isEmbeddedContentPresent())) {
			helper = new MimeMessageHelper(mimeMessage, true);
		} else {
				helper = new MimeMessageHelper(mimeMessage, false);
		}
		final MailHeader mailHeader = mail.getMailHeader();
		helper.setFrom(mailHeader.getFrom());

		if ((null != mailHeader.getTo()) && (0 != mailHeader.getTo().length)) {
			helper.setTo(mailHeader.getTo());
		}
		
		if ((null != mailHeader.getCc()) && (0 != mailHeader.getCc().length)) {
			helper.setCc(mailHeader.getCc());
		}
		
		if ((null != mailHeader.getBcc()) && (0 != mailHeader.getBcc().length)) {
			helper.setBcc(mailHeader.getBcc());
		}
		helper.setSubject(mailHeader.getSubject());

		if(mail.getMimeBodyContents() != null && mail.getMimeBodyContents().size() >0){
	        MimeMultipart multipart = new MimeMultipart("related");
	        BodyPart messageBodyPart = new MimeBodyPart();
  	        messageBodyPart.setContent(mailHeader.getContent(), EMailType.HTML.getCode());
  	        multipart.addBodyPart(messageBodyPart);
	        for (MailMimeBodyContent mimeBodyContent : mail.getMimeBodyContents()) {
	  	        messageBodyPart = new MimeBodyPart();
	  	        DataSource fds = new ByteArrayDataSource(mimeBodyContent.getByteDataSource(),MimeType.IMAGE_PNG.getCode());
	  	        messageBodyPart.setDataHandler(new DataHandler(fds));
	  	        messageBodyPart.setHeader("Content-ID","<"+mimeBodyContent.getHeaderValue()+">");
	  	        multipart.addBodyPart(messageBodyPart);
			}
	        
	        if (mail.getMimeBodyAttachemnt() != null) {
        		//File mailAttachmentFile =mail.getMimeBodyAttachemnt();
  	    		for(File mailAttachmentFile :mail.getMimeBodyAttachemnt()){
  	    			messageBodyPart = new MimeBodyPart();
  	  	    		DataSource source = new FileDataSource(mailAttachmentFile);
  			  	    messageBodyPart.setDataHandler(new DataHandler(source));
  			  	    messageBodyPart.setFileName(mailAttachmentFile.getName());
  			  	    multipart.addBodyPart(messageBodyPart);
  	    		}
        		
	  	      }
	        if(mail.getAttachments() != null){
	        	List<MailAttachments> attachments = mail.getAttachments();
				for (MailAttachments mailAttachment : attachments) {
					final Set<String> attachmentKeys = mailAttachment.getAttachmentsNames();
					for (final String attachmentKey : attachmentKeys) {
						messageBodyPart = new MimeBodyPart();
		  	    		DataSource source = new ByteArrayDataSource(mailAttachment.getAttachment(attachmentKey),mailAttachment.getContentType(attachmentKey));
				  	    messageBodyPart.setDataHandler(new DataHandler(source));
				  	    messageBodyPart.setFileName(attachmentKey);
				  	    multipart.addBodyPart(messageBodyPart);
					}
					
					
				}
	        	
	        }
	        mimeMessage.setContent(multipart);
		}else{
			helper.setText(mailHeader.getContent(), mailHeader.getMimeType() == EMailType.HTML);
			if (processAttachments) {
				
				List<MailAttachments> attachments = mail.getAttachments();
				
				for (MailAttachments mailAttachment : attachments) {
					final MailAttachments mailAttachments = mailAttachment;
					
					if ((null != mailAttachments) && !mailAttachments.isEmpty()) {
						logger.debug("setupMail: setting attachments");
						final Set<String> attachmentKeys = mailAttachments.getAttachmentsNames();
						for (final String attachmentKey : attachmentKeys) {
							final String contentType = mailAttachments.getContentType(attachmentKey);
							if (null == contentType) {
								helper.addAttachment(attachmentKey,
										new ByteArrayResource(mailAttachments.getAttachment(attachmentKey)));
							} else {
								helper.addAttachment(attachmentKey,
										new ByteArrayResource(mailAttachments.getAttachment(attachmentKey)), contentType);
							}
						}
						logger.debug("setupMail: set {} attachment(s)", attachmentKeys.size());
					} else {
						logger.debug("setupMail: no attachments to set");
					}
				}
				
				if (mail.getMimeBodyAttachemnt() != null && mail.getMimeBodyAttachemnt().size() > 0) {
					for(File mailAttachmentFile :mail.getMimeBodyAttachemnt()){
						BodyPart messageBodyPart = new MimeBodyPart();
						DataSource source = new FileDataSource(mailAttachmentFile);
						messageBodyPart.setDataHandler(new DataHandler(source));
						messageBodyPart.setFileName(mailAttachmentFile.getName());
						helper.getMimeMultipart().addBodyPart(messageBodyPart);
					}
				}
			}
		}

		final InlineBinaryContent binaryContent = mail.getBinaryContent();
		if (null != binaryContent) {
			logger.debug("setupMail: adding inline/embedded content");
			this.addInline(helper, binaryContent);
		}
		
		return mimeMessage;
	}

	/**
	 * Method will add any available inline content
	 * 
	 * @param helper
	 * @param binaryContent
	 * @throws MessagingException
	 */
	private void addInline(final MimeMessageHelper helper, final InlineBinaryContent binaryContent)
			throws MessagingException {
		final Set<String> relativeFileNames = binaryContent.getAttachmentsFileNames();
		for (final String relativeFileName : relativeFileNames) {
			final byte[] contents = this.mailFileLoader
					.getResourceAsrray(binaryContent.getAttachmentPath(relativeFileName));
			helper.addInline(relativeFileName, new ByteArrayResource(contents),
					MailFileUtility.getContentTypeFromFileName(relativeFileName));
		}

		final Set<String> relativeContentArrayKeys = binaryContent.getBinaryContentKeys();
		for (final String key : relativeContentArrayKeys) {
			final byte[] contents = binaryContent.getbinaryContent(key);
			helper.addInline(key, new ByteArrayResource(contents), MailFileUtility.getContentTypeFromFileName(key));
		}
	}

	/**
	 * @return the mailSender
	 *//*
	public JavaMailSender getMailSender() {
		return mailSender;
	}

	*//**
	 * @param mailSender the mailSender to set
	 *//*
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}*/
	
	public  void sendNewPasswordToUser(InternetAddress emailTo,String newPassword){
        
        MimetypesFileTypeMap mimetypes = (MimetypesFileTypeMap)MimetypesFileTypeMap.getDefaultFileTypeMap();
        mimetypes.addMimeTypes("text/calendar ics ICS");
        MailcapCommandMap mailcap = (MailcapCommandMap) MailcapCommandMap.getDefaultCommandMap();
        mailcap.addMailcap("text/calendar;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        try {
               Map<String, String> paramMap = new HashMap<String, String>();        
               paramMap.put("UserPassword", newPassword);
               final String mailContent = null;
               
               final MailHeader mailHeader = new MailHeader();
               mailHeader.setContent(mailContent);
               mailHeader.setMimeType(EMailType.TEXT);
               final String to = emailTo.toString();
               
               mailHeader.setFrom("");
               mailHeader.setTo(to);
               mailHeader.setSubject("MOM Tracker : Password Recorvery");
               
               final Mail mail = new Mail();
               mail.setMailHeader(mailHeader);
               //logger.info("emailExpressAppUpdateDetails: sending update mail to {} for candidate with id {}", to, candidateId);
               sendMail(mail);
               //logger.info("emailExpressAppUpdateDetails: sent mail for candidate with id {} ", candidateId);
               
        }catch (Exception e) {

               logger.error("Inside sendNewPasswordToUser method :"+e);
        } 
 }

}
