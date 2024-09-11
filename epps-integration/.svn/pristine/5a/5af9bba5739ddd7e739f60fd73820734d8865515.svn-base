package com.epps.framework.notification.mail.interfaces.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epps.framework.notification.mail.EventAttributeScaner;
import com.epps.framework.notification.mail.application.constant.NotificationConstants;
import com.epps.framework.notification.mail.application.service.INotificationConfigMasterService;
import com.epps.framework.notification.mail.interfaces.dto.EcodeMasterDTO;
import com.epps.framework.notification.mail.interfaces.dto.NotificationConfigAttributeDto;
import com.epps.framework.notification.mail.interfaces.dto.NotificationConfigurationMasterDTO;
import com.epps.framework.notification.mail.interfaces.dto.ProgramMasterDTO;
import com.epps.framework.notification.mail.interfaces.dto.UserProfileVO;

@RestController
@RequestMapping("/notification/")
public class NotificationConfigController {
	
	//@Autowired @Autowired 	SessionManager sessionManager;
	
	@Autowired
	private EventAttributeScaner eventAttributeScaner;
	
	@Autowired
	private INotificationConfigMasterService notificationConfigMasterServiceImpl;
	
	//@Autowired private GridUtil gridUtil;
	
	
	

	
	@RequestMapping(method = RequestMethod.POST, value = "getEventAttributes")
	public ResponseEntity<?> getEventAttributes(@RequestBody List<ProgramMasterDTO> screenNameDataList,final ModelMap modelMap) throws JSONException {
			ProgramMasterDTO screenNameData = null;
			if( null != screenNameDataList && screenNameDataList.size() >0) {
				screenNameData = screenNameDataList.get(0);
			}
			List<NotificationConfigAttributeDto> notificationTemplateDTOs = new ArrayList<>();
			//List <WebSrvcResponseDTO> webSrvcResponseDTOList = new ArrayList<WebSrvcResponseDTO>();
			EcodeMasterDTO ecodeUserInfo= new EcodeMasterDTO();
			UserProfileVO userProfileVO = null;//userProfileVO;
			ecodeUserInfo.setCompanyCode(userProfileVO.getCompanyCode());
			ecodeUserInfo.setTranIndicator(screenNameData.getTranIndicator());
			ecodeUserInfo.setDivisionCode(userProfileVO.getDivisionCode());
			ecodeUserInfo.setSubScreenName(screenNameData.getSubScreenName());
			
			if(screenNameData.getNotiConfigType().equals(NotificationConstants.NOTI_CONFIG_TYPE_EVENT)){
				notificationTemplateDTOs = eventAttributeScaner.getClassScannedWithSuperClasses(screenNameData.getJavaNotificationClsName(), notificationTemplateDTOs,"", ecodeUserInfo, null, screenNameData.getEventAttributePurpose(), screenNameData.getProgramMtqrFlag());
			}
			
			/**
			WebSrvcResponseDTO webSrvcResponseDTO =null;
			if(null != notificationTemplateDTOs) {
				webSrvcResponseDTO	= new WebSrvcResponseDTO(UUID.randomUUID().toString(),new Date().getTime(),CommonConstants.STATUS_SUCCESS, null, notificationTemplateDTOs, null, CommonConstants.OPERATION_MODE_READ, null);
				webSrvcResponseDTOList.add(webSrvcResponseDTO);
			}else {
				String errorDetails =  CommonConstants.COMPANY_CODE + userProfileVO.getCompanyCode()  + CommonConstants.DIVISION_CODE + userProfileVO.getDivisionCode()+  CommonConstants.DATA_NOT_FOUND; 
				List<String> errorStrings = new ArrayList<String>();
				errorStrings.add(errorDetails);
				webSrvcResponseDTO	= new WebSrvcResponseDTO(UUID.randomUUID().toString(),new Date().getTime(),CommonConstants.STATUS_SUCCESS, errorStrings, null, null, CommonConstants.OPERATION_MODE_READ, null);
				webSrvcResponseDTOList.add(webSrvcResponseDTO);
			}
			return new ResponseEntity<>(webSrvcResponseDTOList, HttpStatus.OK);
			
			*/
			
			return null;
	}
	
	private NotificationConfigAttributeDto getNotiTemplateDTOForCompOrDivLogo(String compOrDiv) {
		NotificationConfigAttributeDto notiConfigAttrDto = new NotificationConfigAttributeDto();
		notiConfigAttrDto.setPurpose(NotificationConstants.EVENT_ATTR_PURPOSE_DESIGN);
		notiConfigAttrDto.setFieldType(NotificationConstants.IMAGE_TYPE);
		if(compOrDiv.equals(NotificationConstants.COMPANY)){
			notiConfigAttrDto.setField(NotificationConstants.COMPANY_LOGO);
			notiConfigAttrDto.setDisplayName(NotificationConstants.DISPLAY_NAME_COMPANY_LOGO);
		}else{
			notiConfigAttrDto.setField(NotificationConstants.DIVISION_LOGO);
			notiConfigAttrDto.setDisplayName(NotificationConstants.DISPLAY_NAME_DIVISION_LOGO);
		}
		return notiConfigAttrDto;
	}

	/**
	 * @author Aniket Jadhav
	 * @description Returns Jsp Page which contains form for mail Notification Config Screen.
	 * @param programeCode
	 * @param modelMap
	 * @return
	 */
/*	@RequestMapping(method = RequestMethod.GET, value = "loadAdminNotificationConfig.do")
	public String loadAdminNotificationConfig(@RequestParam Integer programCode, final ModelMap modelMap) {
		UserProfileVO userProfileVO = sessionManager.getUserProfileVOfromtoken(request);
		Integer companyCode = userProfileVO.getCompanyCode();
		Integer divisionCode = userProfileVO.getDivisionCode();
		Integer roleCode = userProfileVO.getDefaultRoleDetailsVO().getDefaultRoleCode();
		RoleProgramLinkDTO programLinkDTO = adminMasterServiceImpl.getProgramPermissions(companyCode, programCode, roleCode, divisionCode);
		modelMap.addAttribute("programLinkDTO",programLinkDTO);
		if(CompanyFilesData.emailServerConstants.getSmsUrl() != null && CompanyFilesData.emailServerConstants.getSmsNumbersKey() != null && 
				CompanyFilesData.emailServerConstants.getSmsMessageKey() != null){
			modelMap.addAttribute("isToEnableSMSTab", true);
		}else{
			modelMap.addAttribute("isToEnableSMSTab", false);
		}
		return PageConstants.LOAD_ADMIN_NOTIFICATION_CONFIG;
	}*/
	
	
	/**
	 * @author Aniket Jadhav
	 * @description Returns Data mail Notification Config Screen.
	 * @param programeCode
	 * @param modelMap
	 * @return
	 * @throws IOException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	//@RequestMapping(method = RequestMethod.GET, value = "getAdminNotificationConfigData.srvc")
	public @ResponseBody NotificationConfigurationMasterDTO getAdminNotificationConfigData(@RequestParam(required=false) Integer notiEventSrNo) throws IOException, IllegalAccessException, InvocationTargetException {
		return notificationConfigMasterServiceImpl.getNotificationEventInfo(notiEventSrNo);
	}
	
	/**
	@RequestMapping(method = RequestMethod.POST, value = "listNotiEvents.srvc")
	public @ResponseBody NotificationConfigurationMasterDTO listNotiEvents(@ModelAttribute PaginationVO paginationVO,@ModelAttribute SearchVO searchVO){
		final GridResponseVO<NotificationConfigurationMasterDTO> jqGridResponseVO = new GridResponseVO<NotificationConfigurationMasterDTO>();
			UserProfileVO userProfileVO = sessionManager.getUserProfileVOfromtoken(request);
			Integer comapanyCode = userProfileVO.getCompanyCode();
			Integer divisionCode = userProfileVO.getDivisionCode();
			
			final Long currentPage = paginationVO.getPage();
			final Long totalRowsToFetch = paginationVO.getRows();
			final Long startIndex = gridUtil.calculateStartIndexForReport(currentPage, totalRowsToFetch);
			PaginationDTO paginationDTO = gridUtil.getPaginationDTO(startIndex, totalRowsToFetch, paginationVO.getSidx(), paginationVO.getSord());
			GridSearchDTO searchDTO = new GridSearchDTO();
            searchDTO.setFilters(searchVO.getFilters());
			final Long totalCount = notificationConfigMasterServiceImpl.listNotiEventsCount(comapanyCode, divisionCode,searchDTO);
			jqGridResponseVO.setRecords(totalCount);
			jqGridResponseVO.setTotal(gridUtil.calculateTotalPageCount(totalCount, totalRowsToFetch));
			
			if (null != totalCount && totalCount.equals(0L)) {
				jqGridResponseVO.setPage(0L);
				jqGridResponseVO.setGridRecords(new ArrayList<NotificationConfigurationMasterDTO>(0));
			} else if(null != totalCount) {
				List<NotificationConfigurationMasterDTO> listOfEvents = notificationConfigMasterServiceImpl.listNotiEvents(comapanyCode, divisionCode, paginationDTO,searchDTO);
				jqGridResponseVO.setGridRecords(listOfEvents);
			}

		return jqGridResponseVO;
	}
	
	
	@RequestMapping(value = "saveOrUpdateNotificationConfigMaster.srvc",method = RequestMethod.POST)
	public String saveOrUpdateNotificationConfigMaster(@RequestBody NotificationConfigurationMasterDTO notificationConfigurationMasterDTO, HttpServletRequest request,final ModelMap modelMap) throws IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException, ParseException, SchedulerException, JsonGenerationException, JsonMappingException, SQLException, JSONException, IOException{
		UserProfileVO userProfileVO = sessionManager.getUserProfileVOfromtoken(request);
		notificationConfigurationMasterDTO.setIpAddress(IPUtil.getIpFromRequest(request));
		notificationConfigMasterServiceImpl.saveNotificationConfig(notificationConfigurationMasterDTO, userProfileVO);
		modelMap.addAttribute(CommonConstants.SUCCESS_MSG, this.localeMessageUtility.getMessage("success.message.data.saved"));
		modelMap.addAttribute(CommonConstants.ID, notificationConfigurationMasterDTO.getNotiEventSrNo());
		return PageConstants.AJAX_SUCCESS_PAGE;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "loadScheduleEventPopUp.srvc")
	public String loadScheduleEventPopUp(HttpServletRequest request,final ModelMap modelMap){
		return PageConstants.LOAD_CRON_HOME;
	}
	
	
	
	//@RequestMapping(method = RequestMethod.POST, value = "getEventEnableSubScreens.srvc")
	public  @ResponseBody String getEventEnableSubScreens(@RequestParam String notiConfigType,@RequestParam String operationMode,@RequestParam String programId, final ModelMap modelMap) throws JSONException {
		UserProfileVO userProfileVO = sessionManager.getUserProfileVOfromtoken(request);
		Integer companyCode = userProfileVO.getCompanyCode();
		Integer divisionCode = userProfileVO.getDivisionCode();
		List<ProgramMasterDTO> screens=notificationConfigMasterServiceImpl.getNotiConfiguredSubPrograms(companyCode, divisionCode, notiConfigType, operationMode,programId);
		JSONObject json=new JSONObject();
		json.put("total",screens.size());
		json.put("rows", screens);

		return json.toString();
	}
	

	@SuppressWarnings("unchecked")
	//@RequestMapping(method = RequestMethod.POST, value = "getColumnAttributesForScheduleBasedEvent")
	public ResponseEntity<?> getColumnAttributesForScheduleBasedEvent(@RequestBody List<ProgramMasterDTO> screenNameDataList,final ModelMap modelMap) throws JSONException {
		List<NotificationConfigAttributeDto> notificationTemplateDTOs = new ArrayList<>();
		List <WebSrvcResponseDTO> webSrvcResponseDTOList = new ArrayList<WebSrvcResponseDTO>();
		UserProfileVO userProfileVO = sessionManager.getUserProfileVOfromtoken(request);
		ProgramMasterDTO screenNameData = null;
		
		if( null != screenNameDataList && screenNameDataList.size() >0) {
			screenNameData = screenNameDataList.get(0);
		}
		notificationTemplateDTOs =notificationConfigMasterServiceImpl.getColumnNamesForScheduledBasedEvent(screenNameData.getNotiEventSrNo(), screenNameData.getEventAttributePurpose());
		WebSrvcResponseDTO webSrvcResponseDTO =null;
		if(null != notificationTemplateDTOs) {
			webSrvcResponseDTO	= new WebSrvcResponseDTO(UUID.randomUUID().toString(),new Date().getTime(), 
					CommonConstants.STATUS_SUCCESS, null, notificationTemplateDTOs, null, CommonConstants.OPERATION_MODE_READ, null);
			webSrvcResponseDTOList.add(webSrvcResponseDTO);
		}else {
			String errorDetails =  CommonConstants.COMPANY_CODE + userProfileVO.getCompanyCode()  + CommonConstants.DIVISION_CODE + userProfileVO.getDivisionCode()+  CommonConstants.DATA_NOT_FOUND; 
			List<String> errorStrings = new ArrayList<String>();
			errorStrings.add(errorDetails);
			webSrvcResponseDTO	= new WebSrvcResponseDTO(UUID.randomUUID().toString(),new Date().getTime(),CommonConstants.STATUS_SUCCESS, errorStrings, null, null, CommonConstants.OPERATION_MODE_READ, null);
			webSrvcResponseDTOList.add(webSrvcResponseDTO);
		}
		return new ResponseEntity<>(webSrvcResponseDTOList, HttpStatus.OK);
	}
	
	
	

	@RequestMapping(method = RequestMethod.POST, value = "getEventEnableScreens.srvc")
	public  @ResponseBody String getEventEnableScreens(@RequestParam String notiConfigType,@RequestParam String operationMode,final ModelMap modelMap) throws JSONException {
		UserProfileVO userProfileVO = sessionManager.getUserProfileVOfromtoken(request);
		Integer companyCode = userProfileVO.getCompanyCode();
		Integer divisionCode = userProfileVO.getDivisionCode();
		List<ProgramMasterDTO> screens=notificationConfigMasterServiceImpl.getNotiConfiguredPrograms(companyCode, divisionCode, notiConfigType, operationMode);
		JSONObject json=new JSONObject();
		json.put("total",screens.size());
		json.put("rows", screens);

		return json.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	*/
	
	
}
