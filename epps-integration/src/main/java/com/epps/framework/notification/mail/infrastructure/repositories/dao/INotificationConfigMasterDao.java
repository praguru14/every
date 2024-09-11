package com.epps.framework.notification.mail.infrastructure.repositories.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.epps.framework.common.domain.model.entities.EppsModuleMaster;
import com.epps.framework.common.domain.model.entities.EppsModuleMasterPK;
import com.epps.framework.infrastructure.repositories.generic.dao.IGenericDAO;
import com.epps.framework.interfaces.responses.dtos.GenericIdValueDTO;
import com.epps.framework.interfaces.responses.dtos.PaginationDTO;
import com.epps.framework.interfaces.responses.dtos.SearchDTO;
import com.epps.framework.notification.mail.application.event.entities.EppsNotificationEvents;
import com.epps.framework.notification.mail.application.event.entities.EppsNotificationPresetEvents;
import com.epps.framework.notification.mail.interfaces.dto.NotificationConfigAttributeDto;
import com.epps.framework.notification.mail.interfaces.dto.NotificationConfigurationMasterDTO;
import com.epps.framework.notification.mail.interfaces.dto.ProgramMasterDTO;

public interface INotificationConfigMasterDao extends IGenericDAO<EppsModuleMaster, EppsModuleMasterPK>{

	List<EppsNotificationEvents> getListOfEventsForScreen(String pId, Integer eventSrNo, String activeYn, String scheduleYn, String subScreenName, Integer companyCode, Integer divisionCode);
	
	EppsNotificationEvents getListOfEventsForScreen(Integer notiEventSrNo);
	
	Long listNotiEventsCount(Integer comapanyCode, Integer divisionCode,SearchDTO searchDTO);
	
	List<NotificationConfigurationMasterDTO> listNotiEvents(Integer comapanyCode, Integer divisionCode,	PaginationDTO paginationDTO, SearchDTO searchDTO);
	
	@Deprecated
	List<ProgramMasterDTO> getNotiConfiguredScreens(Integer companyCode);
	
	List<ProgramMasterDTO> getNotiConfiguredScheduledEvents(Integer companyCode,Integer divisionCode, String activeYn);
	
	@Deprecated
	List<String> getMailIdOfEmployeeByRole(List<Integer> asList, Integer companyCode, Integer divisionCode, Integer locationCode);
	
	@Deprecated
	List<String> getMailIdOfEmployeeByEmpIds(String[] employeesIds,Integer companyCode, Integer divisionCode);
	
	List<String> getMobNoOfEmployeeByEmpIds(String[] empIds,Integer companyCode, Integer divisionCode);

	List<String> getMobNoOfEmployeeByRole(ArrayList<Integer> rolesIntegerList,Integer companyCode, Integer divisionCode, Integer locationCode);

	ProgramMasterDTO getPidAndTranIndicator(Integer companyCode, String eCode, String mtqrFlag);
	
	List<EppsNotificationPresetEvents> getListOfPredefinedEvents(Integer companyCode, Integer divisionCode, Integer activeYn,String predefinedEventNm);

	GenericIdValueDTO<ResultSet, ResultSet> callProcToGetPredefinedNotification(String[][] filterConditions, String procName);

	List<NotificationConfigAttributeDto> getColumnNamesForScheduledBasedEvent(Integer notiEventSrNo, String headerOrDetail);

	List<NotificationConfigAttributeDto> getNotificationDictionary(Integer eventSrNo, String notiType);
	
	/*

	List<ProgramMasterDTO> getNotiConfiguredSubScreens(Integer companyCode,String programId);





	


	String getSupplierMailId(Integer partyCode, Integer partAddCode,Integer companyCode, Integer divisionCode);

	String getCustomerMailId(Integer partyCode, Integer partAddCode,Integer companyCode, Integer divisionCode);




	GenericIdValueDTO<ResultSet, ResultSet> callProcToGetItemNotification(String[][] filterConditions, String procName);


	String getSupplierMobileNo(Integer partyCode, Integer partAddCode,Integer companyCode, Integer divisionCode);
	
	String getCustomerMobileNo(Integer partyCode, Integer partAddCode,Integer companyCode, Integer divisionCode);



	
	*/
}
