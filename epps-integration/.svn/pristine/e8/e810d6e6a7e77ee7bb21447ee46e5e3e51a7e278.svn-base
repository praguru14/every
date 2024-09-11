package com.epps.framework.notification.mail;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.persistence.Id;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.epps.framework.application.util.logger.ApplicationLogger;
import com.epps.framework.domain.exception.ApplicationException;
import com.epps.framework.domain.exception.ErrorCode;
import com.epps.framework.domain.exception.ProcedureException;
import com.epps.framework.domain.exception.ResponseInfoType;
import com.epps.framework.notification.mail.application.constant.NotificationConstants;
import com.epps.framework.notification.mail.application.event.EntityEvent;
import com.epps.framework.notification.mail.application.event.Event;
import com.epps.framework.notification.mail.application.event.entities.EppsNotificationEvents;
import com.epps.framework.notification.mail.application.event.entities.EppsNotificationPresetEvents;
import com.epps.framework.notification.mail.application.event.listener.EventProcessor;
import com.epps.framework.notification.mail.application.service.INotificationConfigMasterService;
import com.epps.framework.notification.mail.application.spel.EppsVelocityEvaluationContext;
import com.epps.framework.notification.mail.application.spel.SpelUtility;
import com.epps.framework.notification.mail.interfaces.dto.ProgramMasterDTO;
import com.epps.framework.notification.mail.interfaces.dto.data.MimeType;
import com.epps.framework.notification.mail.interfaces.dto.mail.EmailDTO;
import com.epps.framework.notification.mail.interfaces.dto.mail.MailAttachments;
import com.epps.framework.notification.mail.interfaces.dto.mail.MailMimeBodyContent;
import com.epps.framework.notification.sms.interfaces.dto.SmsDTO;

@Component
public class NotificationService implements EventProcessor {

    @Produce
    protected ProducerTemplate template;

    @Autowired
    private DecimalFormat decimalFormater;

    //@Autowired private EmailServiceImpl emailServiceImpl;
    
    @Autowired
    private INotificationConfigMasterService notificationConfigMasterServiceImpl;

    //@Autowired private ICommonFileUploadService commonFileUploadServiceImpl;

    public static final ExpressionParser parser = new SpelExpressionParser();

    private static final ApplicationLogger logger = new ApplicationLogger(NotificationService.class);

    @Override
    public Object processEvent(Event event) {
    	EntityEvent ev = (EntityEvent) event;
    	String mailContent = null;
    	String smsContent = null;
    	Boolean isLinux = SystemUtils.IS_OS_LINUX;
    	String homeDir = null;
		if(isLinux != null && isLinux){
			homeDir = System.getProperty("catalina.home");
		}
		logger.error("Platform isLinux: "+isLinux + "  Catalina Home Directory while Processing Event: "+homeDir);
		Object headerObject = ev.getObject();
    	Integer companyCode = null;
    	Integer divisionCode = null;
    	Integer locationCode = null;
    	String eCode = null;
    	Integer primaryKey = null;
    	try{
    		if("T".equals(ev.getMtqrFlag()) || ("M".equals(ev.getMtqrFlag()) && StringUtils.isBlank(ev.getCompositePkGetterNm()))) {
    			Field companyCodeField = headerObject.getClass().getDeclaredField(ev.getCompCdField());
    			companyCodeField.setAccessible(true);
    			companyCode = (Integer) companyCodeField.get(headerObject);
    			
    			Field divisionCodeField = headerObject.getClass().getDeclaredField(ev.getDivCdField());
    			divisionCodeField.setAccessible(true);
    			divisionCode = (Integer) divisionCodeField.get(headerObject);
    		}
    		
    		if ("T".equals(ev.getMtqrFlag()) && StringUtils.isNotBlank(ev.getEcodeField())) {
    			
    			Field locationCodeField = headerObject.getClass().getDeclaredField(ev.getLocationCodeField());
    			locationCodeField.setAccessible(true);
    			locationCode = (Integer) locationCodeField.get(headerObject);
    			
    			Field ecodeField = headerObject.getClass().getDeclaredField(ev.getEcodeField());
    			ecodeField.setAccessible(true);
    			eCode = (String) ecodeField.get(headerObject);
    			Method[] methods = headerObject.getClass().getDeclaredMethods();
    			for (Method method : methods) {
    				if (method.isAnnotationPresent(Id.class)) {
    					primaryKey = (Integer) method.invoke(headerObject);
    				}
    			}
    			if(ev.getIsSingleScreenMultiPid()) {
    				ProgramMasterDTO programMasterDTO =	notificationConfigMasterServiceImpl.getPidAndTranIndicator(companyCode,eCode, ev.getMtqrFlag());
    				if(programMasterDTO.getProgramId() != null) {
    					ev.setpId(programMasterDTO.getProgramId());
    					if(ev.getSubScreenNm() != null && !ev.getSubScreenNm().equals(NotificationConstants.EmptyString) && PIDsWithoutSubScreens.isPIDWithoutSubScreen(programMasterDTO.getProgramId())) {
    						ev.setSubScreenNm(null);
    					}
    				}else {
    					logger.error("Program ID is NULL In Case of Single Screen Multi PID For Company Code:"+companyCode+" & Epps Code:"+eCode+" & MTQR Flag:"+ev.getMtqrFlag());
    				}
    				if(programMasterDTO.getTranIndicator() != null) {
    					ev.setTranIndicator(programMasterDTO.getTranIndicator());
    				}else {
    					logger.error("TranIndicator is NULL In Case of Single Screen Multi PID For Company Code:"+companyCode+" & Epps Code:"+eCode+" & MTQR Flag:"+ev.getMtqrFlag());
    				}
    			}
    		}else if("M".equals(ev.getMtqrFlag()) && StringUtils.isNotBlank(ev.getCompositePkGetterNm())) {
    			Method getCompositePkMethod = headerObject.getClass().getDeclaredMethod(ev.getCompositePkGetterNm());
    			Object compositePk =  getCompositePkMethod.invoke(headerObject);
    			
    			Field companyCodeField = compositePk.getClass().getDeclaredField(ev.getCompCdField());
    			companyCodeField.setAccessible(true);
    			companyCode = (Integer) companyCodeField.get(compositePk);
    			
    			Field divisionCodeField = compositePk.getClass().getDeclaredField(ev.getDivCdField());
    			divisionCodeField.setAccessible(true);
    			divisionCode = (Integer) divisionCodeField.get(compositePk);
    		}
    	}catch(Exception e){
    		
    		logger.error(e.getMessage(), e);
    	}
    	if (!ev.getIsScheduled()) {
    		try {
    			notificationConfigMasterServiceImpl.initializeObject(ev.getObject());
    			StandardEvaluationContext valueContext = initializeSpelContext(ev.getObject());
    			List<EppsNotificationEvents> events = notificationConfigMasterServiceImpl.getListOfEventsForScreen(ev.getpId(), null, NotificationConstants.FLAG_YES, null,ev.getSubScreenNm(), companyCode, divisionCode);
    			for (EppsNotificationEvents eppsNotificationEvent : events) {
    				try{
    					String stringConditions = eppsNotificationEvent.getCondition();
    					String[] conditions = stringConditions.split("&&");
    					boolean isAllConditionsTrue = true;
    					Integer trueCount = 0;
    					for (String condition : conditions) {
    						try{
    							boolean result = (boolean) parser.parseExpression(condition).getValue(valueContext);
    							if (!result && eppsNotificationEvent.getConditionType() != null && eppsNotificationEvent.getConditionType().equals(NotificationConstants.CONDITION_TYPE_ALL)) {
    								isAllConditionsTrue = false;
    								break;
    							}else if(eppsNotificationEvent.getConditionType() != null && eppsNotificationEvent.getConditionType().equals(NotificationConstants.CONDITION_TYPE_ANY) && result){
    								trueCount++;
    							}
    						}catch (Exception e) {
    							isAllConditionsTrue = false;
    							logger.error(e.getMessage(), e);
    						}
    					}
    					if ((eppsNotificationEvent.getConditionType() != null && eppsNotificationEvent.getConditionType().equals(NotificationConstants.CONDITION_TYPE_ALL) && isAllConditionsTrue) || trueCount > 0) {
    						String mailVMPath = null;
    						String smsVMPath = null;
							if(homeDir != null){
								mailVMPath = homeDir+File.separator+eppsNotificationEvent.getMailVmPath();
								smsVMPath = homeDir+File.separator+eppsNotificationEvent.getSmsVmPath();
							}else{
								mailVMPath = eppsNotificationEvent.getMailVmPath();
								smsVMPath = eppsNotificationEvent.getSmsVmPath();
							}
    						EmailDTO emailDto = prepareDataForMailAndSms(eppsNotificationEvent, ev,mailVMPath,valueContext);
    						mailContent = fillVelocityTemplate(mailVMPath, valueContext,ev.getObject());
    						emailDto.setMailContent(mailContent);
    						Map<String, Object> headers = new HashMap<String, Object>();
    						headers.put("emailDTO", emailDto);
    						
    						
    						
    						if (StringUtils.isNotBlank(ev.getReportUri()) && NotificationConstants.FLAG_YES.equals(eppsNotificationEvent.getIsToAttachReport())) {
    							headers.put("notificationEvent", eppsNotificationEvent);
    							headers.put("reportUri", ev.getReportUri().concat("(${body},${header.notificationEvent})"));
    						} else {
    							headers.put("reportUri", "");
    						}
    						
    						
    						//if (StringUtils.isNotBlank(ev.getDetailReportUri()) && NotificationConstants.FLAG_YES.equals(eppsNotificationEvent.getIsToAttachReport())) {
    							//headers.put("notificationEvent", eppsNotificationEvent);
    							//headers.put("detailReportUri", ev.getDetailReportUri().concat("(${body},${header.notificationEvent})"));
    						//} else {
    							//headers.put("detailReportUri", "");
    						//}
    						
    						
    						logger.error("Sending Event Based Mail With Object and Header.");
    						if("S".equals(ev.getMtqrFlag())) {
    							template.sendBodyAndHeaders("direct:sendMailWithMultipleReport", ev.getObject(), headers);
    						}else {
    							template.sendBodyAndHeaders("direct:sendMail", ev.getObject(), headers);
    						}
    						
    						//SMS
    						sendSMSWithVelocityTemplateAndHeaders(ev, valueContext, eppsNotificationEvent, smsVMPath,headers);
    					}
    				}catch (Exception e) {
    					
    					logger.error(e.getMessage(), e);
    				}
    			}
    		} catch (IllegalArgumentException | IllegalAccessException e) {
    			ApplicationException ex = new ApplicationException(e.getMessage(), ErrorCode.GENERIC_ERROR, e,ResponseInfoType.ERROR);
    			ex.setLogged(true);
    			throw ex;
    		} catch (Exception e) {
    			logger.error(e.getMessage(), e);
    		}
    	} else {
    		// For Scheduled Based Need To Update
    		//	    mailContent = fillVelocityTemplate(null, null, ev.getObject());
    		try{
    			StandardEvaluationContext valueContext = initializeSpelContext(ev.getObject());
    			String mailVMPath = null;
    			Method method = ev.getObject().getClass().getDeclaredMethod("getEventHdrSrNo");
    			Integer eventSrNo = (Integer)method.invoke(ev.getObject());
    			List<EppsNotificationEvents> events = notificationConfigMasterServiceImpl
    					.getListOfEventsForScreen(null, eventSrNo, NotificationConstants.FLAG_YES, null,null, null, null);
    			EppsNotificationEvents eppsNotificationEvent = new EppsNotificationEvents();
    			if(!events.isEmpty()){
    				eppsNotificationEvent = events.get(0);
    			}
    			if(homeDir != null){
					mailVMPath = homeDir+File.separator+eppsNotificationEvent.getMailVmPath();
				}else{
					mailVMPath = eppsNotificationEvent.getMailVmPath();
				}
//    			mailContent = "Scheduled Mail For "+eppsNotificationEvent.getEventName();
    			mailContent = fillVelocityTemplate(mailVMPath, valueContext,
    					ev.getObject());
    			EmailDTO emailDto = prepareDataForMailAndSmsForScheduled(eppsNotificationEvent, ev);
    			emailDto.setMailContent(mailContent);
    			Map<String, Object> headers = new HashMap<String, Object>();
    			headers.put("emailDTO", emailDto);
    			logger.error("Sending Scheduled Notification Mail.");
    			template.sendBodyAndHeaders("direct:sendMailSchedule", ev.getObject(), headers);
    		} catch (IllegalArgumentException | IllegalAccessException e) {
    			ApplicationException ex = new ApplicationException(e.getMessage(), ErrorCode.GENERIC_ERROR, e, ResponseInfoType.ERROR);
    			ex.setLogged(true);
    			logger.error(e.getMessage(), e);
    			throw ex;
    		} catch (Exception e) {
    			
    			logger.error(e.getMessage(), e);
    		}
    	}
    	if(ev.getPredefinedEvents().length > 0){
    		try {
    			for(String predefinedEventNm: ev.getPredefinedEvents()) {
    				List<EppsNotificationPresetEvents> predefinedEvents = notificationConfigMasterServiceImpl.getListOfPredefinedEvents(companyCode,divisionCode,locationCode,predefinedEventNm, eCode, primaryKey, ev.getTranIndicator());
    				EppsNotificationPresetEvents eppsPredefinedNotiEvent = new EppsNotificationPresetEvents();
    				if(predefinedEvents != null && !predefinedEvents.isEmpty()){
    					eppsPredefinedNotiEvent = predefinedEvents.get(0);
    				}
    				if(eppsPredefinedNotiEvent.getExcelFile() != null){
    					mailContent = eppsPredefinedNotiEvent.getMailContent();
    					EmailDTO emailDto = prepareDataForMailAndSmsForPredefinedNotification(eppsPredefinedNotiEvent);
    					emailDto.setMailContent(mailContent);
    					Map<String, Object> headers = new HashMap<String, Object>();
    					headers.put("emailDTO", emailDto);
    					logger.error("Sending Predefined Notification Mail For :"+predefinedEventNm);
    					template.sendBodyAndHeaders("direct:sendMailSchedule", ev.getObject(), headers);
    				}else{
    					logger.error("Procedure Result: No data to Send Mail For :"+predefinedEventNm);
    				}
    			}
    		} catch (SQLException | IOException e) {
				
				logger.error(e.getMessage(), e);
			} catch (Exception e) {
    			
    			logger.error(e.getMessage(), e);
    		}
    	}
    	return ev.getObject();
    }

	/**
	 * @param ev
	 * @param valueContext
	 * @param eppsNotificationEvent
	 * @param smsVMPath
	 * @param headers
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws CamelExecutionException
	 */
	private void sendSMSWithVelocityTemplateAndHeaders(EntityEvent ev, StandardEvaluationContext valueContext,
			EppsNotificationEvents eppsNotificationEvent, String smsVMPath, Map<String, Object> headers)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
			CamelExecutionException {
		String smsContent;
		if(CompanyFilesData.emailServerConstants.getSmsUrl() != null && CompanyFilesData.emailServerConstants.getSmsNumbersKey() != null && CompanyFilesData.emailServerConstants.getSmsMessageKey() != null){
			smsContent = fillVelocityTemplate(smsVMPath, valueContext,
					ev.getObject());
			//Regex to remove html tags
			smsContent = smsContent.replaceAll("\\<.*?\\>", "");
			//Regex to remove &nbsp;
			smsContent = smsContent.replaceAll("&nbsp;"," ");
			//Regex to remove all consecutive white space and also new line whether at beginning, middle or end. 
			smsContent = smsContent.replaceAll("(?m)(^\\s+|[\\t\\f ](?=[\\t\\f ])|[\\t\\f ]$|\\s+\\z)", "");
			smsContent = smsContent.trim();
			if(!smsContent.equals(NotificationConstants.EmptyString)){
				SmsDTO smsDTO = new SmsDTO();
				smsDTO = prepareDataForSMS(eppsNotificationEvent, ev,smsDTO);
				if(!smsDTO.getMobileNo().isEmpty()){
					headers.put("content", smsContent);
					headers.put("smsDTO", smsDTO);
					logger.error("Sending SMS With Object and Header.");
					logger.error("Sending SMS To: "+smsDTO.getMobileNo()+"With Content: "+smsContent);
					template.sendBodyAndHeaders("direct:sendSMS", ev.getObject(), headers);
				}else{
					logger.error("No SMS Recipients.");
				}
			}else{
				logger.error("SMS Content is Blank.");
			}
		}else{
			logger.error("SMS Configuration Not Done.");
		}
	}
    
    private SmsDTO prepareDataForSMS(EppsNotificationEvents eppsNotificationEvent, EntityEvent eventAnnotation, SmsDTO smsDTO) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    	StringBuilder toMobNos = new StringBuilder();
    	Object returnObject = eventAnnotation.getObject();
    	Integer locationCode = null;
    	try{
    		Field locationCodeField = returnObject.getClass().getDeclaredField(eventAnnotation.getLocationCodeField());
    		locationCodeField.setAccessible(true);
    		locationCode = (Integer) locationCodeField.get(returnObject);
    	}catch(Exception e){
    		
    		logger.error(e.getMessage(), e);
    	}
    	List<String> mobileNos = new ArrayList<String>();
    	Integer companyCode = eppsNotificationEvent.getCompanyCode();
    	Integer divisionCode = eppsNotificationEvent.getDivisionCode();

    	if (eppsNotificationEvent.getSmsToRoles() != null && !eppsNotificationEvent.getSmsToRoles().isEmpty()) {
    		addEmployeeMobNosFromRoles(eppsNotificationEvent.getSmsToRoles(), companyCode, divisionCode,
    				mobileNos, locationCode);
    	}

    	if (eppsNotificationEvent.getSmsToEmployees() != null && !eppsNotificationEvent.getSmsToEmployees().isEmpty()) {
    		addEmployeeMobNosFromEmpId(eppsNotificationEvent.getSmsToEmployees(), companyCode, divisionCode,
    				mobileNos);
    	}

    	if (eppsNotificationEvent.getSmsToNextUpdator() != null && eppsNotificationEvent.getSmsToNextUpdator() && !eventAnnotation.getNextUpdatorField().isEmpty()) {
    		Field nextUpdatorField = returnObject.getClass()
    				.getDeclaredField(eventAnnotation.getNextUpdatorField());
    		nextUpdatorField.setAccessible(true);
    		Integer nextUpdator = (Integer) nextUpdatorField.get(returnObject);
    		if(nextUpdator != null){
    			addEmployeeMobNosFromRoles(nextUpdator.toString(), companyCode, divisionCode, mobileNos, locationCode);
    		}
    	}

    	for (String mobileNo : mobileNos) {
    		toMobNos.append(mobileNo + ",");
    	}

    	if (eppsNotificationEvent.getSmsToNos() != null && !eppsNotificationEvent.getSmsToNos().trim().isEmpty()){
    		toMobNos.append(eppsNotificationEvent.getSmsToNos() + ",");
    	}

    	if (eppsNotificationEvent.getSmsToParty() != null && eppsNotificationEvent.getSmsToParty()
    			&& (!eventAnnotation.getPartyType().isEmpty() || eventAnnotation.getIsPartyTypeInHdr())) {
    		Field partyCodeField = returnObject.getClass()
    				.getDeclaredField(eventAnnotation.getPartyCodeField());
    		partyCodeField.setAccessible(true);
    		Field partyAddCodeField = returnObject.getClass()
    				.getDeclaredField(eventAnnotation.getPartyAddCodeField());
    		partyAddCodeField.setAccessible(true);
    		Integer partyCode = (Integer)partyCodeField.get(returnObject);
    		Integer partAddCode = (Integer)partyAddCodeField.get(returnObject);
    		String partyMobNo = "";
    		try{
    			if(eventAnnotation.getIsPartyTypeInHdr()){
    				Field partyTypeField = returnObject.getClass().getDeclaredField("partyType");
    				partyTypeField.setAccessible(true);
    				String partyType = (String)partyTypeField.get(returnObject);
    				eventAnnotation.setPartyType(partyType);
    			}
    		}catch(Exception e){
    			
    			logger.error(e.getMessage(), e);
    		}
    		
    		/**
    		if (eventAnnotation.getPartyType() != null && eventAnnotation.getPartyType().equals("S")) {
    			partyMobNo = notificationConfigMasterServiceImpl.getSupplierMobileNo(partyCode, partAddCode,companyCode, divisionCode);
    		} else if(eventAnnotation.getPartyType() != null && eventAnnotation.getPartyType().equals("C")){
    			partyMobNo = notificationConfigMasterServiceImpl.getCustomerMobileNo(partyCode, partAddCode,companyCode, divisionCode);
    		}
    		*/

    		if(partyMobNo != null && !partyMobNo.equals(NotificationConstants.EmptyString)){
    			toMobNos.append(partyMobNo + ",");
    		}
    	}
    	String toMobileNumbers = "";
    	if (toMobNos.length() > 0) {
    		toMobileNumbers = toMobNos.substring(0, toMobNos.length() - 1);
    		Set<String> uniqueMobNos = new HashSet<String>();
    		String[] numbers = toMobileNumbers.split(",");
    		for(int i = 0; i < numbers.length; i++){
    			if(numbers[i] != null && !numbers[i].equals("null")){
    				uniqueMobNos.add(numbers[i]);
    			}
    		}
    		toMobNos.setLength(0);
    		for (String mobNo : uniqueMobNos) {
    			toMobNos.append(mobNo + ",");
			}
    		toMobileNumbers = toMobNos.substring(0, toMobNos.length() - 1);
    	}
    	smsDTO.setMobileNo(toMobileNumbers);
		return smsDTO;
	}

	private void addEmployeeMobNosFromEmpId(String smsToEmployees,
			Integer companyCode, Integer divisionCode,
			List<String> toEmployeeMobNos) {
		String[] empIds = smsToEmployees.split(",");
    	if (empIds != null && empIds.length > 0) {
    		List<String> empMobNos = notificationConfigMasterServiceImpl.getMobNoOfEmployeeByEmpIds(empIds,
    				companyCode, divisionCode);
    		if (empMobNos != null) {
    			toEmployeeMobNos.addAll(empMobNos);
    		}
    	}
	}

	private void addEmployeeMobNosFromRoles(String smsToRoles,
			Integer companyCode, Integer divisionCode,
			List<String> toRoleMobNos, Integer locationCode) {
		String[] roles = smsToRoles.split(",");
    	if (roles != null && roles.length > 0) {
    		ArrayList<Integer> rolesIntegerList = new ArrayList<Integer>();
    		Integer intRole = null;
    		for (String role : roles) {
    			try {
    				intRole = Integer.parseInt(role);
    				rolesIntegerList.add(intRole);
    			} catch (NumberFormatException e) {
    				continue;
    			}
    		}
    		List<String> rolesMobNos = notificationConfigMasterServiceImpl
    				.getMobNoOfEmployeeByRole(rolesIntegerList, companyCode, divisionCode, locationCode);
    		if (rolesMobNos != null) {
    			toRoleMobNos.addAll(rolesMobNos);
    		}
    	}
	}

	private EmailDTO prepareDataForMailAndSmsForPredefinedNotification(EppsNotificationPresetEvents eppsPredefinedNotiEvent) throws IOException {
    	List<String> toEmployeeMailIds = new ArrayList<String>();
    	
    	StringBuilder toMailId = new StringBuilder();
    	
    	Integer companyCode = eppsPredefinedNotiEvent.getCompanyCode();
    	Integer divisionCode = eppsPredefinedNotiEvent.getDivisionCode();
    	
    	if (eppsPredefinedNotiEvent.getToRoles() != null && !eppsPredefinedNotiEvent.getToRoles().isEmpty()) {
    	    addEmplyeeMailIdsFromRoles(eppsPredefinedNotiEvent.getToRoles(), companyCode, divisionCode,
    		    toEmployeeMailIds, null);
    	}

    	if (eppsPredefinedNotiEvent.getToEmployees() != null && !eppsPredefinedNotiEvent.getToEmployees().isEmpty()) {
    	    addEmplyeeMailIdsFromEmpId(eppsPredefinedNotiEvent.getToEmployees(), companyCode, divisionCode,
    		    toEmployeeMailIds);
    	}
    	
    	for (String toEmployeeMailId : toEmployeeMailIds) {
    	    toMailId.append(toEmployeeMailId + ",");
    	}

    	if (null != eppsPredefinedNotiEvent.getToMailId() && !eppsPredefinedNotiEvent.getToMailId().trim().isEmpty())
    	    toMailId.append(eppsPredefinedNotiEvent.getToMailId() + ",");

    	String toMailIds = "";
    	if (toMailId.length() > 0) {
    	    toMailIds = toMailId.substring(0, toMailId.length() - 1);
    	}
		File file = eppsPredefinedNotiEvent.getExcelFile();
		List<MailAttachments> mailAttachemnts = new ArrayList<>();
		MailAttachments attachments = new MailAttachments();
		mailAttachemnts.add(attachments);
		//attachments.addAttachment(eppsPredefinedNotiEvent.getEventName(), FileUtils.readFileToByteArray(file),ResponseUtility.EXCEL_CONTENT_TYPE);
    	
    	EmailDTO emailDto = new EmailDTO(null, toMailIds, null, null, eppsPredefinedNotiEvent.getMailSubject(), null, null, null, null,
				mailAttachemnts, null, null);
    	
    	return emailDto;
	}

	private EmailDTO prepareDataForMailAndSmsForScheduled(EppsNotificationEvents eppsNotificationEvent,EntityEvent ev) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException{
    	
    	List<String> toEmployeeMailIds = new ArrayList<String>();
    	List<String> ccEmployeeMailIds = new ArrayList<String>();
    	List<String> bccEmployeeMailIds = new ArrayList<String>();
    	
    	StringBuilder toMailId = new StringBuilder();
    	StringBuilder ccMailId = new StringBuilder();
    	StringBuilder bccMailId = new StringBuilder();
    	
    	Integer companyCode = eppsNotificationEvent.getCompanyCode();
    	Integer divisionCode = eppsNotificationEvent.getDivisionCode();
    	
    	if (eppsNotificationEvent.getToRoles() != null && !eppsNotificationEvent.getToRoles().isEmpty()) {
    	    addEmplyeeMailIdsFromRoles(eppsNotificationEvent.getToRoles(), companyCode, divisionCode,
    		    toEmployeeMailIds, null);
    	}
    	if (eppsNotificationEvent.getCcRoles() != null && !eppsNotificationEvent.getCcRoles().isEmpty()) {
    	    addEmplyeeMailIdsFromRoles(eppsNotificationEvent.getCcRoles(), companyCode, divisionCode,
    		    ccEmployeeMailIds, null);
    	}
    	if (eppsNotificationEvent.getBccRoles() != null && !eppsNotificationEvent.getBccRoles().isEmpty()) {
    	    addEmplyeeMailIdsFromRoles(eppsNotificationEvent.getBccRoles(), companyCode, divisionCode,
    		    bccEmployeeMailIds, null);
    	}

    	if (eppsNotificationEvent.getToEmployees() != null && !eppsNotificationEvent.getToEmployees().isEmpty()) {
    	    addEmplyeeMailIdsFromEmpId(eppsNotificationEvent.getToEmployees(), companyCode, divisionCode,
    		    toEmployeeMailIds);
    	}
    	if (eppsNotificationEvent.getCcEmployees() != null && !eppsNotificationEvent.getCcEmployees().isEmpty()) {
    	    addEmplyeeMailIdsFromEmpId(eppsNotificationEvent.getCcEmployees(), companyCode, divisionCode,
    		    ccEmployeeMailIds);
    	}
    	if (eppsNotificationEvent.getBccEmployees() != null && !eppsNotificationEvent.getBccEmployees().isEmpty()) {
    	    addEmplyeeMailIdsFromEmpId(eppsNotificationEvent.getBccEmployees(), companyCode, divisionCode,
    		    bccEmployeeMailIds);
    	}
    	
    	for (String toEmployeeMailId : toEmployeeMailIds) {
    	    toMailId.append(toEmployeeMailId + ",");
    	}

    	for (String ccEmployeeMailId : ccEmployeeMailIds) {
    	    ccMailId.append(ccEmployeeMailId + ",");
    	}

    	for (String bccEmployeeMailId : bccEmployeeMailIds) {
    	    bccMailId.append(bccEmployeeMailId + ",");
    	}
    	if (null != eppsNotificationEvent.getToMailId() && !eppsNotificationEvent.getToMailId().trim().isEmpty())
    	    toMailId.append(eppsNotificationEvent.getToMailId() + ",");
    	if (null != eppsNotificationEvent.getCcMailId() && !eppsNotificationEvent.getCcMailId().trim().isEmpty())
    	    ccMailId.append(eppsNotificationEvent.getCcMailId() + ",");
    	if (null != eppsNotificationEvent.getBccMailId() && !eppsNotificationEvent.getBccMailId().trim().isEmpty())
    	    bccMailId.append(eppsNotificationEvent.getBccMailId() + ",");

    	String toMailIds = "";
    	String ccMailIds = "";
    	String bccMailIds = "";
    	if (toMailId.length() > 0) {
    	    toMailIds = toMailId.substring(0, toMailId.length() - 1);
    	}
    	if (ccMailId.length() > 0) {
    	    ccMailIds = ccMailId.substring(0, ccMailId.length() - 1);
    	}
    	if (bccMailId.length() > 0) {
    	    bccMailIds = bccMailId.substring(0, bccMailId.length() - 1);
    	}
        Method method = ev.getObject().getClass().getDeclaredMethod("getExcelFile");
		File file = (File) method.invoke(ev.getObject());
		List<MailAttachments> mailAttachemnts = new ArrayList<>();
		MailAttachments attachments = new MailAttachments();
		mailAttachemnts.add(attachments);
		//attachments.addAttachment(eppsNotificationEvent.getEventName(), FileUtils.readFileToByteArray(file),ResponseUtility.EXCEL_CONTENT_TYPE);
		
    	
    	EmailDTO emailDto = new EmailDTO(null, toMailIds, ccMailIds, bccMailIds, eppsNotificationEvent.getMailSubject(), null, null, null, null,
				mailAttachemnts, null, null);
    	
    	return emailDto;
    }

    private EmailDTO prepareDataForMailAndSms(EppsNotificationEvents eppsNotificationEvent, EntityEvent eventAnnotation,String vmFilePath, StandardEvaluationContext valueContext)
		    throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
		    ApplicationException, ProcedureException, InvocationTargetException, ParseException {
    	StringBuilder toMailId = new StringBuilder();
    	StringBuilder ccMailId = new StringBuilder();
    	StringBuilder bccMailId = new StringBuilder();
    	Object returnObjByAnotateMeth = eventAnnotation.getObject();
    	Integer locationCode = null;
    	
    	if(!eventAnnotation.getMtqrFlag().equalsIgnoreCase("S")) {
	    	try{
	    		Field locationCodeField = returnObjByAnotateMeth.getClass().getDeclaredField(eventAnnotation.getLocationCodeField());
	    		locationCodeField.setAccessible(true);
	    		locationCode = (Integer) locationCodeField.get(returnObjByAnotateMeth);
	    	}catch(Exception e){
	    		
	    		logger.error(e.getMessage(), e);
	    	}
    	}
    	List<String> toEmployeeMailIds = new ArrayList<String>();
    	List<String> ccEmployeeMailIds = new ArrayList<String>();
    	List<String> bccEmployeeMailIds = new ArrayList<String>();
    	
    	Integer companyCode = eppsNotificationEvent.getCompanyCode();
    	Integer divisionCode = eppsNotificationEvent.getDivisionCode();

    	if (eppsNotificationEvent.getToRoles() != null && !eppsNotificationEvent.getToRoles().isEmpty()) {
    		addEmplyeeMailIdsFromRoles(eppsNotificationEvent.getToRoles(), companyCode, divisionCode,toEmployeeMailIds, locationCode);
    	}
    	if (eppsNotificationEvent.getCcRoles() != null && !eppsNotificationEvent.getCcRoles().isEmpty()) {
    		addEmplyeeMailIdsFromRoles(eppsNotificationEvent.getCcRoles(), companyCode, divisionCode,ccEmployeeMailIds, locationCode);
    	}
    	if (eppsNotificationEvent.getBccRoles() != null && !eppsNotificationEvent.getBccRoles().isEmpty()) {
    		addEmplyeeMailIdsFromRoles(eppsNotificationEvent.getBccRoles(), companyCode, divisionCode,bccEmployeeMailIds, locationCode);
    	}

    	if (eppsNotificationEvent.getToEmployees() != null && !eppsNotificationEvent.getToEmployees().isEmpty()) {
    		addEmplyeeMailIdsFromEmpId(eppsNotificationEvent.getToEmployees(), companyCode, divisionCode,toEmployeeMailIds);
    	}
    	if (eppsNotificationEvent.getCcEmployees() != null && !eppsNotificationEvent.getCcEmployees().isEmpty()) {
    		addEmplyeeMailIdsFromEmpId(eppsNotificationEvent.getCcEmployees(), companyCode, divisionCode,ccEmployeeMailIds);
    	}
    	if (eppsNotificationEvent.getBccEmployees() != null && !eppsNotificationEvent.getBccEmployees().isEmpty()) {
    		addEmplyeeMailIdsFromEmpId(eppsNotificationEvent.getBccEmployees(), companyCode, divisionCode,bccEmployeeMailIds);
    	}

    	if (eppsNotificationEvent.getNextUpdatorIn() != null && !eppsNotificationEvent.getNextUpdatorIn().isEmpty() && !eventAnnotation.getNextUpdatorField().isEmpty()) {
    		String[] nextUpdatorInTypes = eppsNotificationEvent.getNextUpdatorIn().split(",");
    		Field nextUpdatorField = returnObjByAnotateMeth.getClass().getDeclaredField(eventAnnotation.getNextUpdatorField());
    		nextUpdatorField.setAccessible(true);
    		Integer nextUpdator = (Integer) nextUpdatorField.get(returnObjByAnotateMeth);
    		if(nextUpdator != null){
    			for (String nextUpdatorIn : nextUpdatorInTypes) {
    				switch (nextUpdatorIn) {
    				case "to":
    					addEmplyeeMailIdsFromRoles(nextUpdator.toString(), companyCode, divisionCode, toEmployeeMailIds, locationCode);
    					break;
    				case "cc":
    					addEmplyeeMailIdsFromRoles(nextUpdator.toString(), companyCode, divisionCode,ccEmployeeMailIds, locationCode);
    					break;
    				case "bcc":
    					addEmplyeeMailIdsFromRoles(nextUpdator.toString(), companyCode, divisionCode,bccEmployeeMailIds, locationCode);
    					break;
    				default:
    					break;
    				}
    			}
    		}
    	}

    	for (String toEmployeeMailId : toEmployeeMailIds) {
    		toMailId.append(toEmployeeMailId + ",");
    	}

    	for (String ccEmployeeMailId : ccEmployeeMailIds) {
    		ccMailId.append(ccEmployeeMailId + ",");
    	}

    	for (String bccEmployeeMailId : bccEmployeeMailIds) {
    		bccMailId.append(bccEmployeeMailId + ",");
    	}
    	if (null != eppsNotificationEvent.getToMailId() && !eppsNotificationEvent.getToMailId().trim().isEmpty())
    		toMailId.append(eppsNotificationEvent.getToMailId() + ",");
    	if (null != eppsNotificationEvent.getCcMailId() && !eppsNotificationEvent.getCcMailId().trim().isEmpty())
    		ccMailId.append(eppsNotificationEvent.getCcMailId() + ",");
    	if (null != eppsNotificationEvent.getBccMailId() && !eppsNotificationEvent.getBccMailId().trim().isEmpty())
    		bccMailId.append(eppsNotificationEvent.getBccMailId() + ",");

    	if (eppsNotificationEvent.getPartyIn() != null && !eppsNotificationEvent.getPartyIn().isEmpty() && (!eventAnnotation.getPartyType().isEmpty() || eventAnnotation.getIsPartyTypeInHdr())) {
    		String[] partyInTypes = eppsNotificationEvent.getPartyIn().split(",");
    		Field partCodeField = returnObjByAnotateMeth.getClass().getDeclaredField(eventAnnotation.getPartyCodeField());
    		partCodeField.setAccessible(true);
    		Field partAddCodeField = returnObjByAnotateMeth.getClass().getDeclaredField(eventAnnotation.getPartyAddCodeField());
    		partAddCodeField.setAccessible(true);
    		Integer partyCode = (Integer)partCodeField.get(returnObjByAnotateMeth);
    		Integer partAddCode = (Integer)partAddCodeField.get(returnObjByAnotateMeth);
    		String partyMailId = "";
    		try{
    			if(eventAnnotation.getIsPartyTypeInHdr()){
    				Field partTypeField = returnObjByAnotateMeth.getClass().getDeclaredField("partyType");
    				partTypeField.setAccessible(true);
    				String partyType = (String)partTypeField.get(returnObjByAnotateMeth);
    				eventAnnotation.setPartyType(partyType);
    			}
    		}catch(Exception e){
    			
    			logger.error(e.getMessage(), e);
    		}
    		
    		/**
    		if (eventAnnotation.getPartyType() != null && eventAnnotation.getPartyType().equals("S")) {
    			partyMailId = notificationConfigMasterServiceImpl.getSupplierMailId(partyCode, partAddCode,companyCode, divisionCode);
    		} else if(eventAnnotation.getPartyType() != null && eventAnnotation.getPartyType().equals("C")){
    			partyMailId = notificationConfigMasterServiceImpl.getCustomerMailId(partyCode, partAddCode,companyCode, divisionCode);
    		}
    		*/

    		if(partyMailId != null && !partyMailId.equals(NotificationConstants.EmptyString)){
    			for (String partyInType : partyInTypes) {
    				switch (partyInType) {
    				case "to":
    					toMailId.append(partyMailId + ",");
    					break;
    				case "cc":
    					ccMailId.append(partyMailId + ",");
    					break;
    				case "bcc":
    					bccMailId.append(partyMailId + ",");
    					break;
    				default:
    					break;
    				}
    			}
    		}
    	}
    	try{
    		Field nonConfigMailRecipientsField = returnObjByAnotateMeth.getClass().getSuperclass().getDeclaredField("nonConfigMailRecipients");
    		nonConfigMailRecipientsField.setAccessible(true);
    		String nonConfigMailRecipients = (String)nonConfigMailRecipientsField.get(returnObjByAnotateMeth);
    		if (nonConfigMailRecipients != null && !nonConfigMailRecipients.trim().isEmpty()) {
    			toMailId.append(nonConfigMailRecipients + ",");
    		}
    	}catch(Exception e){
			
			logger.error(e.getMessage(), e);
		}
		
    	String toMailIds = "";
    	String ccMailIds = "";
    	String bccMailIds = "";
    	if (toMailId.length() > 0) {
    		toMailIds = toMailId.substring(0, toMailId.length() - 1);
    	}
    	if (ccMailId.length() > 0) {
    		ccMailIds = ccMailId.substring(0, ccMailId.length() - 1);
    	}
    	if (bccMailId.length() > 0) {
    		bccMailIds = bccMailId.substring(0, bccMailId.length() - 1);
    	}
    	ArrayList<MailMimeBodyContent> mimeBodyContents = new ArrayList<MailMimeBodyContent>();
    	addMimeBodyContentsInMailBody(vmFilePath, divisionCode, mimeBodyContents);
    	List<File> uploadedFiles = new ArrayList<File>();
    	/**
    	List<GlobalImageUploadDTO> globalImageUploadDTOs = null;
    	globalImageUploadDTOs = commonFileUploadServiceImpl.getImageUplodedListForMasterScreens(eppsNotificationEvent.getFileUploadSrNo());
    	for (GlobalImageUploadDTO globalImageUploadDTO : globalImageUploadDTOs) {
    		String filePath = commonFileUploadServiceImpl.getFilePath(companyCode, globalImageUploadDTO.getFileName());
    		File uploadedFile = new File(filePath);
    		if(uploadedFile.exists()) {
    			uploadedFiles.add(uploadedFile);
    		}
    	}
    	*/

    	if (StringUtils.isNotBlank(eppsNotificationEvent.getIsToFetchTransDoc()) && eppsNotificationEvent.getIsToFetchTransDoc().equals(NotificationConstants.FLAG_YES)) {
    		//globalImageUploadDTOs = Collections.emptyList();
    		if ("T".equals(eventAnnotation.getMtqrFlag()) && StringUtils.isNotBlank(eventAnnotation.getEcodeField())) {
    			Integer primaryKey = null;
    			Field ecodeField = returnObjByAnotateMeth.getClass().getDeclaredField(eventAnnotation.getEcodeField());
    			ecodeField.setAccessible(true);
    			String ecode = (String) ecodeField.get(returnObjByAnotateMeth);
    			Method[] methods = returnObjByAnotateMeth.getClass().getDeclaredMethods();
    			for (Method method : methods) {
    				if (method.isAnnotationPresent(Id.class)) {
    					primaryKey = (Integer) method.invoke(returnObjByAnotateMeth);
    				}
    			}
    			//globalImageUploadDTOs = commonFileUploadServiceImpl.getImageUplodedList(null, null, null, ecode,primaryKey, null, null);
    		} else if ("M".equals(eventAnnotation.getMtqrFlag())) {
    			Field fileUploadSrNoField = returnObjByAnotateMeth.getClass().getDeclaredField("fileUploadSrNo");
    			fileUploadSrNoField.setAccessible(true);
    			Integer fileUploadSrNo = (Integer)fileUploadSrNoField.get(returnObjByAnotateMeth);
    			//globalImageUploadDTOs = commonFileUploadServiceImpl.getImageUplodedListForMasterScreens(fileUploadSrNo);
    		}
    		
    		/**
    		for (GlobalImageUploadDTO globalImageUploadDTO : globalImageUploadDTOs) {
    			String filePath = commonFileUploadServiceImpl.getFilePath(companyCode,globalImageUploadDTO.getFileName());
    			File uploadedFile = new File(filePath);
    			if(uploadedFile.exists()) {
        			uploadedFiles.add(uploadedFile);
        		}
    		}
    		*/
    	}

    	List<MailAttachments> attachments = new ArrayList<MailAttachments>();
    	MailAttachments mailAttachment = new MailAttachments();
    	attachments.add(mailAttachment);
    	String[] subjectLiterals = eppsNotificationEvent.getMailSubject().split("\\+");
    	for (int i = 0; i < subjectLiterals.length; i++) {
    		if (subjectLiterals[i].trim().startsWith("#this.")) {
    			int startIndex = subjectLiterals[i].trim().indexOf("#");
    			int endIndex;
    			if(subjectLiterals[i].trim().indexOf(" ") != -1){
    				endIndex = subjectLiterals[i].trim().indexOf(" ");
    			}else{
    				endIndex = subjectLiterals[i].trim().length();
    			}
    			String subjectExpression = subjectLiterals[i].trim().substring(startIndex, endIndex);
    			String parsedValue = parser.parseExpression(subjectExpression).getValue(valueContext,String.class);
    			if(parsedValue == null){
    				parsedValue = NotificationConstants.EmptyString;
    			}
    			String subjectLiteral = subjectLiterals[i].replace(subjectExpression, parsedValue);
    			subjectLiterals[i] = subjectLiteral;
    		}
    	}
    	StringBuilder mailSubject = new StringBuilder();
    	for (int i = 0; i < subjectLiterals.length; i++) {
    		mailSubject.append(subjectLiterals[i]);
    	}
    	EmailDTO emailDto = new EmailDTO(null, toMailIds, ccMailIds, bccMailIds, mailSubject.toString(), null, null, null, null,attachments, mimeBodyContents, uploadedFiles);
    	return emailDto;
    }

	/**
	 * @param vmFilePath
	 * @param divisionCode
	 * @param mimeBodyContents
	 */
	private void addMimeBodyContentsInMailBody(String vmFilePath, Integer divisionCode,
			ArrayList<MailMimeBodyContent> mimeBodyContents) {
		File f = new File(vmFilePath);
		if (f.exists()) {
			try {
				MailMimeBodyContent mimeBodyContent = null;
				String mailTemplateContent = new String(Files.readAllBytes(Paths.get(vmFilePath)));
				if (mailTemplateContent.contains(NotificationConstants.DIVISION_LOGO)) {
					mimeBodyContent = new MailMimeBodyContent(CompanyFilesData.divisionLogos.get(divisionCode),MimeType.IMAGE_PNG.getCode(), NotificationConstants.DIVISION_LOGO);
					mimeBodyContents.add(mimeBodyContent);
				}
				if (mailTemplateContent.contains(NotificationConstants.COMPANY_LOGO)) {
					mimeBodyContent = new MailMimeBodyContent(CompanyFilesData.companyLogo,MimeType.IMAGE_PNG.getCode(), NotificationConstants.COMPANY_LOGO);
					mimeBodyContents.add(mimeBodyContent);
				}
			} catch (IOException e) {
				logger.error(vmFilePath+" file not found",e);
			}
		} else {
			logger.error(vmFilePath+" file not found");
		}
	}

    private void addEmplyeeMailIdsFromEmpId(String employeesIdsString, Integer companyCode, Integer divisionCode,List<String> employeeMailIds) {
    	String[] empIds = employeesIdsString.split(",");
    	if (empIds != null && empIds.length > 0) {
    		List<String> empMailIds = notificationConfigMasterServiceImpl.getMailIdOfEmployeeByEmpIds(empIds,companyCode, divisionCode);
    		if (null != empMailIds) {
    			employeeMailIds.addAll(empMailIds);
    		}
    	}
    }

    private void addEmplyeeMailIdsFromRoles(String rolesInString, Integer companyCode, Integer divisionCode,List<String> employeeMailIds, Integer locationCode) {
    	String[] roles = rolesInString.split(",");
    	if (roles != null && roles.length > 0) {
    		ArrayList<Integer> rolesIntegerList = new ArrayList<Integer>();
    		Integer intRole = null;
    		for (String role : roles) {
    			try {
    				intRole = Integer.parseInt(role);
    				rolesIntegerList.add(intRole);
    			} catch (NumberFormatException e) {
    				continue;
    			}
    		}
    		List<String> rolesEmpIds = notificationConfigMasterServiceImpl.getMailIdOfEmployeeByRole(rolesIntegerList, companyCode, divisionCode, locationCode);
    		if (null != rolesEmpIds) {
    			employeeMailIds.addAll(rolesEmpIds);
    		}
    	}
    }

    @Override
    public String fillVelocityTemplate(String mailVmPath, StandardEvaluationContext valueContext, Object dataObj) {

    	logger.error("VM Path while Filling Velocity Template: "+mailVmPath);
    	VelocityEngine engine = new VelocityEngine();
    	Properties p = new Properties();
    	p.setProperty("resource.loader", "file");
    	p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
    	File vmFile = new File(mailVmPath);
    	String absolutePath = vmFile.getAbsolutePath();
    	p.setProperty("file.resource.loader.path", absolutePath.substring(0, absolutePath.indexOf(vmFile.getPath())));
    	p.setProperty("file.resource.loader.cache", "false");
    	p.setProperty("file.resource.loader.modificationCheckInterval", "0");
    	engine.init(p);
    	Template template = engine.getTemplate(vmFile.getPath());
    	VelocityContext context = new VelocityContext();
    	EppsVelocityEvaluationContext eppsVelocityEvaluationContext = new EppsVelocityEvaluationContext(parser, dataObj,valueContext);
    	context.put("dataObj", dataObj);
    	context.put("evaluator", eppsVelocityEvaluationContext);
    	context.put("decimalFormater", decimalFormater);
    	StringWriter sw = new StringWriter();
    	template.merge(context, sw);
    	return sw.toString();

    }

    @Override
    public StandardEvaluationContext initializeSpelContext(Object object) {
    	try {
    		StandardEvaluationContext valueContext = new StandardEvaluationContext(object);
    		valueContext.registerFunction("SUM",SpelUtility.class.getDeclaredMethod("sum", new Class[] { Object.class }));
    		valueContext.registerFunction("EVAL",SpelUtility.class.getDeclaredMethod("isElementsPresent", new Class[] { Object.class }));
    		valueContext.registerFunction("parseDate",SpelUtility.class.getDeclaredMethod("getDateObj", new Class[] { String.class }));
    		valueContext.registerFunction("VAL",SpelUtility.class.getDeclaredMethod("getValue", new Class[] { Object.class }));
    		valueContext.registerFunction("COUNT",SpelUtility.class.getDeclaredMethod("count", new Class[] { Object.class }));
    		return valueContext;
    	} catch (NoSuchMethodException | SecurityException e) {
    		logger.error(e.getMessage(), e);
    	}
    	return null;
    }
}
