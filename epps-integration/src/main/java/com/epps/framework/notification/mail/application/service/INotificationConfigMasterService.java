package com.epps.framework.notification.mail.application.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epps.framework.interfaces.responses.dtos.GenericIdValueDTO;
import com.epps.framework.interfaces.responses.dtos.PaginationDTO;
import com.epps.framework.interfaces.responses.dtos.SearchDTO;
import com.epps.framework.notification.mail.application.event.entities.EppsNotificationEvents;
import com.epps.framework.notification.mail.application.event.entities.EppsNotificationPresetEvents;
import com.epps.framework.notification.mail.interfaces.dto.NotificationConfigAttributeDto;
import com.epps.framework.notification.mail.interfaces.dto.NotificationConfigurationMasterDTO;
import com.epps.framework.notification.mail.interfaces.dto.ProgramMasterDTO;

public interface INotificationConfigMasterService {
	
	void initializeObject(Object object) throws IllegalArgumentException, IllegalAccessException;
	
	String[][] getArrayFromString(String filterCondition, Boolean isFromScheduler, Object[] procParamsArray);

	List<EppsNotificationEvents> getListOfEventsForScreen(String pId, Integer eventSrNo, String activeYn, String scheduleYn, String subScreenName, Integer companyCode, Integer divisionCode);
	
	NotificationConfigurationMasterDTO getNotificationEventInfo(Integer notiEventSrNo) throws IOException, IllegalAccessException, InvocationTargetException;

	Long listNotiEventsCount(Integer comapanyCode, Integer divisionCode,SearchDTO searchDTO);
	
	List<NotificationConfigurationMasterDTO> listNotiEvents(Integer comapanyCode, Integer divisionCode,PaginationDTO paginationDTO, SearchDTO searchDTO);

	List<ProgramMasterDTO> getNotiConfiguredPrograms(Integer companyCode, Integer divisionCode, String notiConfigType, String operationMode);
	
	@Deprecated
	List<String> getMailIdOfEmployeeByRole(List<Integer> asList,Integer companyCode,Integer divisionCode, Integer locationCode);
	
	@Deprecated
	List<String> getMailIdOfEmployeeByEmpIds(String[] employeesIds,Integer companyCode, Integer divisionCode);

	List<String> getMobNoOfEmployeeByEmpIds(String[] empIds,Integer companyCode, Integer divisionCode);
	
	List<String> getMobNoOfEmployeeByRole(ArrayList<Integer> rolesIntegerList,Integer companyCode, Integer divisionCode, Integer locationCode);
	
	ProgramMasterDTO getPidAndTranIndicator(Integer companyCode, String eCode, String mtqrFlag);
	
	List<EppsNotificationPresetEvents> getListOfPredefinedEvents(Integer companyCode, Integer divisionCode, Integer locationCode, String predefinedEventNm, String eCode, Integer hdrSrNo, String tranIndicator) throws FileNotFoundException, SQLException, IOException;
	
	List<NotificationConfigAttributeDto> getColumnNamesForScheduledBasedEvent(Integer notiEventSrNo, String eventAttributePurpose);

	//List<GenericIdValueDTO<Object,Object>> getPredefineValueForEntity(String entity,EcodeMasterSrvcDTO ecodeUserInfo);
	/**


	List<ProgramMasterDTO> getNotiConfiguredSubPrograms(Integer companyCode,Integer divisionCode, String notiConfigType, String operationMode, String programId);



	Integer saveNotificationConfig(NotificationConfigurationMasterDTO notificationConfigurationMasterDTO, UserProfileVO userProfileVO) throws IllegalAccessException, InvocationTargetException;




	String getSupplierMailId(Integer partyCode, Integer partAddCode,Integer companyCode, Integer divisionCode);

	String getCustomerMailId(Integer partyCode, Integer partAddCode,Integer companyCode, Integer divisionCode);

	ScheduleBaseExcelDTO getItemNotification(String[][] filterCondition, Integer eventHdrSrNo, String procName, String eventName)
			throws SQLException, JSONException, IllegalAccessException, InvocationTargetException,
			JsonGenerationException, JsonMappingException, IOException;




	String getSupplierMobileNo(Integer partyCode, Integer partAddCode,Integer companyCode, Integer divisionCode);

	String getCustomerMobileNo(Integer partyCode, Integer partAddCode,Integer companyCode, Integer divisionCode);




	void sendNotificationsToPartiesForReceivables(String[][] filterCondition, Integer eventHdrSrNo,	String procName, String eventName) throws SQLException, JSONException, IllegalAccessException,
			InvocationTargetException, JsonGenerationException, JsonMappingException, IOException;
	
	*/
}

