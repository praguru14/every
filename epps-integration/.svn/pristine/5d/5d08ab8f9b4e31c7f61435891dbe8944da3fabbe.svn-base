package com.epps.framework.notification.mail.application.processor;


import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.epps.framework.notification.mail.application.service.impl.EmailServiceImpl;
import com.epps.framework.notification.mail.interfaces.dto.data.MimeType;
import com.epps.framework.notification.mail.interfaces.dto.mail.EmailDTO;
import com.epps.framework.notification.mail.interfaces.dto.mail.MailAttachments;

@Component("mailProcessor")
public class MailProcessor implements Processor {

	@Autowired
	private EmailServiceImpl emailServiceImpl;
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	@Override
	public void process(Exchange exchange) {
		Message in = exchange.getIn();
		byte[] file = in.getBody(byte[].class);
		String username =mailSender.getUsername();
		EmailDTO emailDTO= (EmailDTO) in.getHeader("emailDTO");
		emailDTO.setFrom(username);
		if(null != file){
			MailAttachments mailAttachments = new MailAttachments();
			mailAttachments.addAttachment("Report.pdf", file,MimeType.PDF.getCode());
			emailDTO.getMailAttachemnts().add(mailAttachments);
		}
		emailServiceImpl.sendMail(emailDTO);
	}
}
