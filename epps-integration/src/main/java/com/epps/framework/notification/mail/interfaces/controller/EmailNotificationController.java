package com.epps.framework.notification.mail.interfaces.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epps.framework.application.constant.ApplicationConstants;
import com.epps.framework.application.util.date.DateHelper;
import com.epps.framework.application.util.logger.ApplicationLogger;
import com.epps.framework.domain.exception.ResponseInfoType;
import com.epps.framework.interfaces.responses.ResponseInfo;
import com.epps.framework.interfaces.responses.dtos.ResponseWrapper;
import com.epps.framework.notification.mail.application.queryService.IEmailNotificationQueryService;
import com.epps.framework.notification.mail.application.service.impl.EmailServiceImpl;
import com.epps.framework.notification.mail.interfaces.dto.mail.AppointmentEmailDTO;
import com.epps.framework.notification.mail.interfaces.dto.mail.EmailDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@Validated
@RequestMapping("/emailNotification")
@Tag(name="Email Notification API" , description="Email Notification API")
public class EmailNotificationController {

	private static final ApplicationLogger LOGGER = new ApplicationLogger(EmailNotificationController.class);

	//	@Autowired
	//	private AppointmentTransformers appointmentTransformers;

	@Autowired
	private IEmailNotificationQueryService emailNotificationQueryServiceImpl;

	@Autowired
	private EmailServiceImpl emailServiceImpl;


	@GetMapping("/v1/emailNotifications")
	public ResponseEntity<?> getEmailNotificationData(
			@Parameter(name = "appointmentNumber", description = "provide Appointment Number", required = false) @RequestParam(value="appointmentNumber", required=false) String appointmentNumber,
			@Parameter(name = "examinationOverdueYn", description = "Examination OverdueYn", required = false) @RequestParam(value="examinationOverdueYn", required=false) Integer examinationOverdueYn
			) throws IllegalAccessException, InvocationTargetException{
		LOGGER.info("In AppointmentRestController : getEmailNotificationData");
		List<ResponseInfo> responseStatusList = new ArrayList<ResponseInfo>(1);
		getEmailContentAndSendMail(appointmentNumber, examinationOverdueYn);

		ResponseInfo responseStatus = new ResponseInfo(1031, ResponseInfoType.INFO.name(), null, ApplicationConstants.STATUS_SUCCESS, null);
		responseStatusList.add(responseStatus);
		return ResponseWrapper.getResponseEntityWithoutPagination(null, null, ApplicationConstants.STATUS_SUCCESS, responseStatusList, null, null, ApplicationConstants.OPERATION_MODE_READ, HttpStatus.OK);
	}

	public void getEmailContentAndSendMail(String appointmentNumber, Integer examinationOverdueYn) {
		JSONObject inputRequiredJson = new JSONObject();
		if (null!=appointmentNumber) {inputRequiredJson.accumulate("vin_app_no",appointmentNumber);}
		if (null!=examinationOverdueYn) { inputRequiredJson.accumulate("vin_examination_overdue",examinationOverdueYn);}

		List<Object> emailNotificationData = emailNotificationQueryServiceImpl.getEmailNotificationData(inputRequiredJson);
		if(null !=emailNotificationData) {
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<AppointmentEmailDTO> appointmentEmailLists = mapper.convertValue(emailNotificationData,new TypeReference<List<AppointmentEmailDTO>>() {});
			if(null!=appointmentEmailLists.get(0).getTo() && null!=appointmentEmailLists.get(0).getFrom()) {
			for(AppointmentEmailDTO appointmentEmailDTO : appointmentEmailLists) {
				// create html 
				createHtml(appointmentEmailDTO);
				//				appointmentEmailDTO.setTo("kundan.kumar@epps-erp.com");

				try {
					EmailDTO emailData = new EmailDTO();
					BeanUtils.copyProperties(emailData,appointmentEmailDTO);
					emailServiceImpl.sendMail(emailData);
				}catch (Exception e) {
					//					throw new ApplicationException("No Data For Email Template Found", null, null);
				}
			}
			}
		}
		//		}
	}

	private void createHtml(AppointmentEmailDTO appointmentEmailDTO) {
		if(null!= appointmentEmailDTO) {
			StringBuilder htmlsb = new StringBuilder("<!DOCTYPE html>");
			htmlsb = htmlsb.append("<html lang=\"en\">");
			htmlsb = htmlsb.append("<head>");
			htmlsb = htmlsb.append(" <meta charset=\"UTF-8\">");
			htmlsb = htmlsb.append(" <meta name=\"viewport\" content=/width=device-width, initial-scale=1.0/>");
			htmlsb = htmlsb.append(" <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">");
			htmlsb = htmlsb.append(" <title>Notification</title>");

			htmlsb = htmlsb.append("<style>\r\n" + 
					"		      * {\r\n" + 
					"		          margin: 0;\r\n" + 
					"		          padding: 0;\r\n" + 
					"		          box-sizing: border-box;\r\n" + 
					"		      }\r\n" + 
					"\r\n" + 
					"		      body {\r\n" + 
					"		          font-size: 16px;\r\n" + 
					"		          color: #161616;\r\n" + 
					"		          overflow-x: hidden;\r\n" + 
					"		          background-color: #FFFFFF;\r\n" + 
					"		          font-family: Calibri, arial, sans-serif;\r\n" + 
					"		      }\r\n" + 
					"\r\n" + 
					"		      .notify-container {\r\n" + 
					"		          width: 63%;\r\n" + 
					"		          margin: 0 auto;\r\n" + 
					"		          background-color: white;\r\n" + 
					"		          padding-bottom: 20px;\r\n" + 
					"		      }\r\n" + 
					"\r\n" + 
					"		      .notify {\r\n" + 
					"		        background: #fff;\r\n" + 
					"		        padding: 0;\r\n" + 
					"		        margin: 0 1rem;\r\n" + 
					"		      }\r\n" + 
					"		      \r\n" + 
					"		      .table {\r\n" + 
					"		        width: 100%;\r\n" + 
					"		      }\r\n" + 
					"\r\n" + 
					"		      .table-container {\r\n" + 
					"		          overflow-x: auto;\r\n" + 
					"		          margin: 10px 0;\r\n" + 
					"		      }\r\n" + 
					"\r\n" + 
					"		      .table {\r\n" + 
					"		          border-collapse: collapse;\r\n" + 
					"		          width: 100%;\r\n" + 
					"		      }\r\n" + 
					"\r\n" + 
					"		      .table thead tr {\r\n" + 
					"		          background: rgb(247 202 172);\r\n" + 
					"		          font-weight: bold;\r\n" + 
					"		          text-align: center;\r\n" + 
					"		      }\r\n" + 
					"\r\n" + 
					"		      .table thead tr th {\r\n" + 
					"		          border: 1px solid #fff;\r\n" + 
					"		      }\r\n" + 
					"\r\n" + 
					"		      .table tbody tr {\r\n" + 
					"		          background: #fff;\r\n" + 
					"		      }\r\n" + 
					"\r\n" + 
					"		      .table tbody tr td,\r\n" + 
					"		      .table thead tr th {\r\n" + 
					"		          font-size: 12px;\r\n" + 
					"		          padding: 0.8rem;\r\n" + 
					"		          text-align: center;\r\n" + 
					"		          min-width: 100px;\r\n" + 
					"		          border: 1px solid black\r\n" + 
					"		      }\r\n" + 
					"		      \r\n" + 
					"		      @media only screen and (max-width: 768px) {\r\n" + 
					"		        .img-flex{\r\n" + 
					"		          height: 100%;\r\n" + 
					"		        }\r\n" + 
					"		          .ticket-container {\r\n" + 
					"		              width: 100%;\r\n" + 
					"		          }\r\n" + 
					"		      }\r\n" + 
					"		    </style>");
			/*	
			htmlsb = htmlsb.append(" <style>");
			htmlsb = htmlsb.append(" * {");
			htmlsb = htmlsb.append(" margin: 0;");
			htmlsb = htmlsb.append(" padding: 0;");
			htmlsb = htmlsb.append(" box-sizing: border-box;");
			htmlsb = htmlsb.append(" }");

			htmlsb = htmlsb.append(" body {");
			htmlsb = htmlsb.append(" font-size: 16px;");
			htmlsb = htmlsb.append(" color: #161616;");
			htmlsb = htmlsb.append(" overflow-x: hidden;");
			htmlsb = htmlsb.append(" background-color: #FFFFFF;");
			htmlsb = htmlsb.append(" font-family: Calibri, arial, sans-serif;");
			htmlsb = htmlsb.append(" }");

			htmlsb = htmlsb.append(" .notify-container {");
			htmlsb = htmlsb.append(" width: 63%;");
			htmlsb = htmlsb.append(" margin: 0 auto;");
			htmlsb = htmlsb.append(" background-color: white;");
			htmlsb = htmlsb.append(" padding-bottom: 20px;");
			htmlsb = htmlsb.append(" }");

			htmlsb = htmlsb.append(" .notify {");
			htmlsb = htmlsb.append(" background: #fff;");
			htmlsb = htmlsb.append(" padding: 0;");
			htmlsb = htmlsb.append(" margin: 0 1rem;");
			htmlsb = htmlsb.append(" }");

			htmlsb = htmlsb.append(" .table {");
			htmlsb = htmlsb.append("  width: 100%;");
			htmlsb = htmlsb.append("  }");

			htmlsb = htmlsb.append(" .table-container {");
			htmlsb = htmlsb.append(" overflow-x: auto;");
			htmlsb = htmlsb.append("  margin: 10px 0;");
			htmlsb = htmlsb.append(" }");

			htmlsb = htmlsb.append(" .table {");
			htmlsb = htmlsb.append(" border-collapse: collapse;");
			htmlsb = htmlsb.append(" width: 100%;");
			htmlsb = htmlsb.append("  }");

			htmlsb = htmlsb.append(" .table thead tr {");
			htmlsb = htmlsb.append(" background: rgb(247 202 172");
			htmlsb = htmlsb.append(" font-weight: bold;");
			htmlsb = htmlsb.append(" text-align: center;");
			htmlsb = htmlsb.append("  }");

			htmlsb = htmlsb.append(" .table thead tr th {");
			htmlsb = htmlsb.append(" border: 1px solid #fff;");
			htmlsb = htmlsb.append(" }");

			htmlsb = htmlsb.append(" .table tbody tr {");
			htmlsb = htmlsb.append(" background: #fff;");
			htmlsb = htmlsb.append(" }");

			htmlsb = htmlsb.append(" .table tbody tr td,");
			htmlsb = htmlsb.append(" .table thead tr th {");
			htmlsb = htmlsb.append(" font-size: 12px;");
			htmlsb = htmlsb.append(" padding: 0.8rem;");
			htmlsb = htmlsb.append(" text-align: center;");
			htmlsb = htmlsb.append(" min-width: 100px;");
			htmlsb = htmlsb.append(" border: 1px solid black");
			htmlsb = htmlsb.append(" }");

			htmlsb = htmlsb.append(" @media only screen and (max-width: 768px) {");
			htmlsb = htmlsb.append(" .img-flex{");
			htmlsb = htmlsb.append(" height: 100%;");
			htmlsb = htmlsb.append(" }");
			htmlsb = htmlsb.append(" .ticket-container {");
			htmlsb = htmlsb.append(" width: 100%;");
			htmlsb = htmlsb.append(" }");
			htmlsb = htmlsb.append(" }");
			htmlsb = htmlsb.append("  </style>");
			 */	
			htmlsb = htmlsb.append(" </head>");

			htmlsb = htmlsb.append(" <body>");
			htmlsb = htmlsb.append(" <div class=\"notify-container\">");
			htmlsb = htmlsb.append(" <div>");
			htmlsb = htmlsb.append(" <div class=\"notify\">");
			htmlsb = htmlsb.append(" <br>");

			htmlsb = htmlsb.append(" <div>");
			htmlsb = htmlsb.append(" <span> Dear <b> ");
			htmlsb = htmlsb.append(appointmentEmailDTO.getPersonalName());
			htmlsb = htmlsb.append("</b> </span>");
			htmlsb = htmlsb.append(" </div>");
			htmlsb = htmlsb.append(" <br>");
			htmlsb = htmlsb.append(" <div>");
			htmlsb = htmlsb.append(" <span> <b> Unit of Personnel </b> </span> <br> <br>");
			htmlsb = htmlsb.append(" <span> Your appointment has been confirmed! </span> <br> <br>");
			htmlsb = htmlsb.append(" <span> Refer to the details below: </span> <br><br>");
			htmlsb = htmlsb.append(" <span> <b>Appointment Number: </b>");
			htmlsb = htmlsb.append(appointmentEmailDTO.getAppointmentNumber());
			htmlsb = htmlsb.append(" </span>");
			htmlsb = htmlsb.append(" </div>");
			htmlsb = htmlsb.append(" <br>");

			htmlsb = htmlsb.append(" <div class=\"table-container\" style=\"float: left; width: 100%;\">");

			if(null!=appointmentEmailDTO.getMiRoom()) {
				htmlsb = htmlsb.append(" <table class=\"table\">");
				htmlsb = htmlsb.append(" <thead>");
				htmlsb = htmlsb.append(" <tr>");
				htmlsb = htmlsb.append(" <th style=\"width: 25%;\"> MI Room </th>");
				htmlsb = htmlsb.append(" <th style=\"width: 25%;\"> Date of investigation	</th>");
				htmlsb = htmlsb.append(" <th style=\"width: 25%;\"> Date of examination </th>");
				htmlsb = htmlsb.append(" <th style=\"width: 25%;\"> Examination Details </th>");
				htmlsb = htmlsb.append(" </tr>");
				htmlsb = htmlsb.append(" </thead>");
				htmlsb = htmlsb.append(" <tbody>");
				htmlsb = htmlsb.append(" <tr>");
				htmlsb = htmlsb.append(" <td style=\"width: 25%;\"> ");
				htmlsb = htmlsb.append(appointmentEmailDTO.getMiRoom());
				htmlsb = htmlsb.append(" </td>");
				htmlsb = htmlsb.append(" <td style=\"width: 25%;\"> ");
				htmlsb = htmlsb.append(DateHelper.getFormattedDateString(appointmentEmailDTO.getDateOfInvestigation(), DateHelper.EPPS_DEFAULT_DATE_FORMAT));
				htmlsb = htmlsb.append(" </td>");
				htmlsb = htmlsb.append(" <td style=\"width: 25%;\"> ");
				htmlsb  = htmlsb.append(DateHelper.getFormattedDateString(appointmentEmailDTO.getDateOfExamination(), DateHelper.EPPS_DEFAULT_DATE_FORMAT));
				htmlsb = htmlsb.append(" </td>");
				htmlsb = htmlsb.append(" <td style=\"width: 25%;\"> ");
				htmlsb = htmlsb.append(appointmentEmailDTO.getExaminationDetails());
				htmlsb = htmlsb.append(" </td>");
				htmlsb = htmlsb.append(" </tr>");
				htmlsb = htmlsb.append(" </tbody>");
				htmlsb = htmlsb.append(" </table>");
			}
			if(null!=appointmentEmailDTO.getNavalHospital()) {
				htmlsb = htmlsb.append(" <table class=\"table\">");
				htmlsb = htmlsb.append(" <thead>");
				htmlsb = htmlsb.append(" <tr>");
				htmlsb = htmlsb.append(" <th style=\"width: 25%;\"> Naval Hospital </th>");
				htmlsb = htmlsb.append(" <th style=\"width: 25%;\"> Date of investigation</th>");
				htmlsb = htmlsb.append(" <th style=\"width: 25%;\"> Date of examination </th>");
				htmlsb = htmlsb.append(" <th style=\"width: 25%;\"> Examination Details </th>");
				htmlsb = htmlsb.append(" </tr>");
				htmlsb = htmlsb.append(" </thead>");
				htmlsb = htmlsb.append(" <tbody>");
				htmlsb = htmlsb.append(" <tr>");
				htmlsb = htmlsb.append(" <td style=\"width: 25%;\"> ");
				htmlsb = htmlsb.append(appointmentEmailDTO.getNavalHospital());
				htmlsb = htmlsb.append(" </td>");
				htmlsb = htmlsb.append(" <td style=\"width: 25%;\"> ");
				htmlsb = htmlsb.append(DateHelper.getFormattedDateString(appointmentEmailDTO.getNavalHospitalDateOfInvestigaton(), DateHelper.EPPS_DEFAULT_DATE_FORMAT));
				htmlsb = htmlsb.append(" </td>");
				htmlsb = htmlsb.append(" <td style=\"width: 25%;\"> ");
				htmlsb = htmlsb.append(DateHelper.getFormattedDateString(appointmentEmailDTO.getNavalHospitalDateOfExamination(), DateHelper.EPPS_DEFAULT_DATE_FORMAT));
				htmlsb = htmlsb.append(" </td>");
				htmlsb = htmlsb.append(" <td style=\"width: 25%;\"> ");
				htmlsb = htmlsb.append(appointmentEmailDTO.getNavalHospitalExaminationDetails());
				htmlsb = htmlsb.append(" </td>");
				htmlsb = htmlsb.append(" </tr>");
				htmlsb = htmlsb.append(" </tbody>");
				htmlsb = htmlsb.append(" </table>");
			}
			if(null!=appointmentEmailDTO.getExternalHospital()) {
				htmlsb = htmlsb.append(" <table class=\"table\">");
				htmlsb = htmlsb.append(" <thead>");
				htmlsb = htmlsb.append(" <tr>");
				htmlsb = htmlsb.append(" <th style=\"width: 25%;\"> External Hospital </th>");
				htmlsb = htmlsb.append(" <th style=\"width: 25%;\"> Date of investigation</th>");
				htmlsb = htmlsb.append(" <th style=\"width: 25%;\"> Date of examination </th>");
				htmlsb = htmlsb.append(" <th style=\"width: 25%;\"> Examination Details </th>");
				htmlsb = htmlsb.append(" </tr>");
				htmlsb = htmlsb.append(" </thead>");
				htmlsb = htmlsb.append(" <tbody>");
				htmlsb = htmlsb.append(" <tr>");
				htmlsb = htmlsb.append(" <td style=\"width: 25%;\"> ");
				htmlsb = htmlsb.append(appointmentEmailDTO.getExternalHospital());
				htmlsb = htmlsb.append(" </td>");
				htmlsb = htmlsb.append(" <td style=\"width: 25%;\"> ");
				htmlsb = htmlsb.append(DateHelper.getFormattedDateString(appointmentEmailDTO.getExternalHospitalDateOfInvestigaton(), DateHelper.EPPS_DEFAULT_DATE_FORMAT));
				htmlsb = htmlsb.append(" </td>");
				htmlsb = htmlsb.append(" <td style=\"width: 25%;\"> ");
				htmlsb = htmlsb.append(DateHelper.getFormattedDateString(appointmentEmailDTO.getExternalHospitalDateOfExamination(), DateHelper.EPPS_DEFAULT_DATE_FORMAT));
				htmlsb = htmlsb.append(" </td>");
				htmlsb = htmlsb.append(" <td style=\"width: 25%;\"> ");
				htmlsb = htmlsb.append(appointmentEmailDTO.getExternalHospitalExaminationDetails());
				htmlsb = htmlsb.append(" </td>");
				htmlsb = htmlsb.append(" </tr>");
				htmlsb = htmlsb.append(" </tbody>");
				htmlsb = htmlsb.append(" </table>");
			}
			htmlsb = htmlsb.append(" </div> ");
			htmlsb = htmlsb.append(" <br> <br> <br> ");
			htmlsb = htmlsb.append(" <div>");
			htmlsb = htmlsb.append(" <span> <u> You will undergo the following examinations: </u> </span> <br> <br> ");
			htmlsb = htmlsb.append(" <span> ");
			htmlsb = htmlsb.append(appointmentEmailDTO.getExaminations());
			htmlsb = htmlsb.append(" </span> <br> <br> <br> <br>");
			htmlsb = htmlsb.append(" <span> Regards, </span> <br> <br>");
			htmlsb = htmlsb.append(" <span> <b> ");
			htmlsb = htmlsb.append(appointmentEmailDTO.getAmaRank()+" "+appointmentEmailDTO.getAmaName());
			htmlsb = htmlsb.append("</b> </span> <br ><br>");
			htmlsb = htmlsb.append(" <span> <b> ");
			htmlsb = htmlsb.append(appointmentEmailDTO.getAmaUnit());		
			htmlsb = htmlsb.append(" </b> </span> <br> <br> <br>");
			htmlsb = htmlsb.append(" </div>");
			htmlsb = htmlsb.append(" <br>");
			htmlsb = htmlsb.append(" </div>");
			htmlsb = htmlsb.append(" </div>");
			htmlsb = htmlsb.append(" </div>");
			htmlsb = htmlsb.append(" </body>");
			htmlsb = htmlsb.append(" </html>"); 

			appointmentEmailDTO.setMailContent(htmlsb.toString());
		}
	}


}
