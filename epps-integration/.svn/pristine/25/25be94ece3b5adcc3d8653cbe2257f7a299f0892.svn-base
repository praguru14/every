package com.epps.framework.notification.mail.application.service.impl;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epps.framework.application.constant.ApplicationConstants;
import com.epps.framework.application.util.logger.ApplicationLogger;
import com.epps.framework.domain.exception.ApplicationException;
import com.epps.framework.domain.exception.ErrorCode;
import com.epps.framework.domain.exception.ResponseInfoType;
import com.epps.framework.notification.mail.interfaces.dto.data.EMailType;
import com.epps.framework.notification.mail.interfaces.dto.mail.EmailDTO;
import com.epps.framework.notification.mail.interfaces.dto.mail.Mail;
import com.epps.framework.notification.mail.interfaces.dto.mail.MailAttachments;
import com.epps.framework.notification.mail.interfaces.dto.mail.MailHeader;

@Component("emailServiceImpl")
public class EmailServiceImpl {
	
	private static ApplicationLogger logger = new ApplicationLogger(EmailServiceImpl.class);
	
	@Autowired
	private MailManager mailManager;
	
	public void sendEmailsToUser(final EmailDTO emailDTO,final List<MailAttachments> attachments) {

		final Executor pool = Executors.newFixedThreadPool(1);

		if (pool instanceof ThreadPoolExecutor) {
			((ThreadPoolExecutor) pool).setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		}

		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					
				} catch (final Exception e) {
					logger.info("send Mail To failed" + e);
				}
			}

		};
		pool.execute(runnable);

	}
	
	public void sendMail(final EmailDTO emailDTO) {

		try {
			String[] emailTo = null;
			String[] ccTo = null;
			String[] bccTo = null;
			String from = emailDTO.getFrom();
			String subject = emailDTO.getSubject();
			String mailContent = emailDTO.getMailContent();
			
			if(null != emailDTO.getTo() && !emailDTO.getTo().equals(ApplicationConstants.EmptyString)) {
				emailTo = emailDTO.getTo().split(",");
			}
			
			if(null != emailDTO.getCc() && !emailDTO.getCc().equals(ApplicationConstants.EmptyString)) {
				ccTo = emailDTO.getCc().split(",");
			}
			
			if(null != emailDTO.getBcc() && !emailDTO.getBcc().equals(ApplicationConstants.EmptyString)) {
				bccTo = emailDTO.getBcc().split(",");
			}

			final MailHeader mailHeader = new MailHeader();
			if (null!= mailContent) {
				mailHeader.setContent(mailContent);
			}
			else {
				mailHeader.setContent("");
			}
			mailHeader.setMimeType(EMailType.HTML);
			InternetAddress fromAddr = null;
			if(emailDTO.getPersonalName() != null && !emailDTO.getPersonalName().equals(ApplicationConstants.EmptyString)) {
				fromAddr = new InternetAddress(emailDTO.getFrom(), emailDTO.getPersonalName());
			}else {
				fromAddr = new InternetAddress(from.toString(), from.toString());
			}
			
			if(null != emailTo) {
				mailHeader.setTo(emailTo);
			}
			
			if(null != ccTo) {
				mailHeader.setCc(ccTo);
			}
			
			if(null != bccTo) {
				mailHeader.setBcc(bccTo);
			}
			
			mailHeader.setFrom(fromAddr.toString());
			mailHeader.setSubject(subject);

			final Mail mail = new Mail();
			mail.setMailHeader(mailHeader);
			
			mail.setAttachments(emailDTO.getMailAttachemnts());
			mail.setMimeBodyContents(emailDTO.getMimeBodyContents());
			mail.setMimeBodyAttachemnt(emailDTO.getMimeBodyAttachemnt());
			mailManager.sendMail(mail);
			
		}catch (Exception e) {
			
			logger.error("Inside send Mail method :"+e);
			throw new ApplicationException("Failure sending mail", ErrorCode.UNEXPECTED_ERROR, ResponseInfoType.ERROR);
		} 
	}
}
