package com.epps.framework.notification.mail.interfaces.controller;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epps.framework.application.util.logger.ApplicationLogger;
import com.epps.framework.notification.mail.application.queryService.IEmailNotificationQueryService;
import com.epps.framework.notification.mail.application.service.impl.EmailServiceImpl;
import com.epps.framework.notification.mail.interfaces.dto.mail.AppointmentEmailDTO;
import com.epps.framework.notification.mail.interfaces.dto.mail.EmailDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@EnableScheduling
@RestController
@RequestMapping("/emailNotificationSchedular")
public class EmailSchedulerController {

	private static final ApplicationLogger LOGGER = new ApplicationLogger(EmailSchedulerController.class);
	
	@Autowired
	private IEmailNotificationQueryService emailNotificationQueryServiceImpl;

	@Autowired
	private EmailServiceImpl emailServiceImpl;
	

	@Async
	@Scheduled(cron = "${cronExpression}")
	@GetMapping(value="/medicalOverdueEmail")
	public void sendMedicalOverdueScheduledEmail() {
		LOGGER.info("In EmailSchedulerController : sendMedicalOverdueScheduledEmail");
		JSONObject inputRequiredJson = new JSONObject();
		inputRequiredJson.accumulate("vin_app_no",null);
		inputRequiredJson.accumulate("vin_examination_overdue",1);
		getEmailContentAndSendMail(inputRequiredJson);
	}
	
	@Async
	@Scheduled(cron = "${cronExpression}")
	@GetMapping(value="/appointmentRequestEmail")
	public void sendAppointmentRequestScheduledEmail() throws InterruptedException {
		LOGGER.info("In EmailSchedulerController : sendAppointmentRequestScheduledEmail");
		JSONObject inputRequiredJson = new JSONObject();
		inputRequiredJson.accumulate("vin_app_no",null);
		inputRequiredJson.accumulate("vin_examination",1);
		getEmailContentAndSendMail(inputRequiredJson);}

	@Async
	@Scheduled(cron = "${cronExpression}")
	@GetMapping(value="/appointmentScheduledEmail")
	public void sendExaminationScheduledEmail() throws InterruptedException {
		LOGGER.info("In EmailSchedulerController : sendExaminationScheduledEmail");
		JSONObject inputRequiredJson = new JSONObject();
		inputRequiredJson.accumulate("vin_app_no",null);
		inputRequiredJson.accumulate("vin_request",1);
		getEmailContentAndSendMail(inputRequiredJson);
	}
	
	@Async
	public void getEmailContentAndSendMail(JSONObject inputRequiredJson ) {
		List<Object> emailNotificationData = emailNotificationQueryServiceImpl.getEmailNotificationData(inputRequiredJson);
		if(null !=emailNotificationData) {
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<AppointmentEmailDTO> appointmentEmailLists = mapper.convertValue(emailNotificationData,new TypeReference<List<AppointmentEmailDTO>>() {});
			if(null!=appointmentEmailLists.get(0).getTo() && null!=appointmentEmailLists.get(0).getFrom()) {
				for(AppointmentEmailDTO appointmentEmailDTO : appointmentEmailLists) {
					try {
						EmailDTO emailData = new EmailDTO();
						BeanUtils.copyProperties(emailData,appointmentEmailDTO);
//						emailData.setTo("rahul.p@epps-erp.com");
						emailServiceImpl.sendMail(emailData);
					}catch (Exception e) {

					}
				}
			}
		}
	}
}
