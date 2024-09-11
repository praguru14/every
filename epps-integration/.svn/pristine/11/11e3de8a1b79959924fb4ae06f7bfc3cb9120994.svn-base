package com.epps.framework.notification.mail.application.processor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.epps.framework.application.util.logger.ApplicationLogger;
import com.epps.framework.notification.mail.CompanyFilesData;
import com.epps.framework.notification.sms.interfaces.dto.SmsDTO;

@Component("smsProcessor")
public class SmsProcessor implements Processor  {
	
	 private static final ApplicationLogger logger = new ApplicationLogger(SmsProcessor.class);
	
		@Override
		public void process(Exchange exchange) throws Exception {
			
			Message in = exchange.getIn();
			String content = in.getHeader("content", String.class);
//			EmailSmsTemplateDTO mailSmsRecipientData = in.getHeader("mailSmsRecipientData",EmailSmsTemplateDTO.class);
			String reciversNumbers = "";
			SmsDTO smsDTO = in.getHeader("smsDTO",SmsDTO.class);
//			String reciversNumbers =  mailSmsRecipientData.getNotiReciverNumbers();
			if(smsDTO != null){
				reciversNumbers = smsDTO.getMobileNo();
			}
			
			String message = "&"+CompanyFilesData.emailServerConstants.getSmsMessageKey()+"=" + URLEncoder.encode(content, "UTF-8");
			
//			For Testing Only
//			String numbers = "&"+CompanyFilesData.emailServerConstants.getSmsNumbersKey()+"=" + URLEncoder.encode("8793194297", "UTF-8");
			
			String numbers = "&"+CompanyFilesData.emailServerConstants.getSmsNumbersKey()+"=" + URLEncoder.encode(reciversNumbers, "UTF-8");
			
			HttpURLConnection conn = (HttpURLConnection) new URL(CompanyFilesData.emailServerConstants.getSmsUrl()).openConnection();
			String data = numbers + message;
			conn.setDoOutput(true);
			conn.setRequestMethod(CompanyFilesData.emailServerConstants.getSmsReqMethod());
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
			logger.error(stringBuffer.toString());
		}
}
