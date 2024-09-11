/**
 * 
 */
package com.epps.framework.notification.mail.application.config;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epps.framework.notification.mail.application.processor.MailProcessor;

/**
 * @author Abhinesh
 *
 */

@Component
public class DirectMailRouter extends RouteBuilder{
	
	@Autowired
	MailProcessor mailProcessor;

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		from("direct:sendMail").process(mailProcessor).choice().when().simple("${header.reportUri} != ''").enrich().simple("${header.reportUri}");
		//from("direct:sendMail").process(mailProcessor);
	}

}
