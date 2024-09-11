package com.epps.framework.notification.mail.application.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang.SystemUtils;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.epps.framework.application.constant.ApplicationConstants;
import com.epps.framework.application.util.logger.ApplicationLogger;
import com.epps.framework.domain.exception.ApplicationException;
import com.epps.framework.domain.exception.ResponseInfoType;
import com.epps.framework.interfaces.responses.dtos.GenericIdValueDTO;
import com.epps.framework.interfaces.responses.dtos.PaginationDTO;
import com.epps.framework.interfaces.responses.dtos.SearchDTO;
import com.epps.framework.notification.mail.application.constant.NotificationConstants;
import com.epps.framework.notification.mail.application.event.annotation.EventAttribute;
import com.epps.framework.notification.mail.application.event.entities.EppsNotificationEvents;
import com.epps.framework.notification.mail.application.event.entities.EppsNotificationPresetEvents;
import com.epps.framework.notification.mail.application.event.listener.EventProcessor;
import com.epps.framework.notification.mail.application.service.INotificationConfigMasterService;
import com.epps.framework.notification.mail.infrastructure.repositories.dao.INotificationConfigMasterDao;
import com.epps.framework.notification.mail.interfaces.dto.NotificationConfigAttributeDto;
import com.epps.framework.notification.mail.interfaces.dto.NotificationConfigurationMasterDTO;
import com.epps.framework.notification.mail.interfaces.dto.ProgramMasterDTO;

@Service("notificationConfigMasterServiceImpl")
public class NotificationConfigMasterServiceImpl implements INotificationConfigMasterService{
	
	private static final ApplicationLogger logger = new ApplicationLogger(NotificationConfigMasterServiceImpl.class);
	
	@Autowired
	private INotificationConfigMasterDao notificationConfigMasterDaoImpl;
	
	@Produce
	protected ProducerTemplate template;
	
	@Autowired
	private EventProcessor notificationService;
	

	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED)
	public List<EppsNotificationEvents> getListOfEventsForScreen(String pId, Integer eventSrNo, String activeYn, String scheduleYn, String subScreenName, Integer companyCode, Integer divisionCode) {
		return notificationConfigMasterDaoImpl.getListOfEventsForScreen(pId, eventSrNo, activeYn, scheduleYn, subScreenName, companyCode, divisionCode);
	}

	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED)
	public NotificationConfigurationMasterDTO getNotificationEventInfo(	Integer notiEventSrNo) throws IOException, IllegalAccessException, InvocationTargetException {
		EppsNotificationEvents eppsNotificationEvent= notificationConfigMasterDaoImpl.getListOfEventsForScreen(notiEventSrNo);
		NotificationConfigurationMasterDTO notificationConfigurationMasterDTO = new NotificationConfigurationMasterDTO();
		BeanUtils.copyProperties(notificationConfigurationMasterDTO, eppsNotificationEvent);
		if(eppsNotificationEvent.getPartyIn() != null && eppsNotificationEvent.getPartyIn().length() >0){
			String[] partyIns = eppsNotificationEvent.getPartyIn().split(",");
			for (String partyIn : partyIns) {
					if(partyIn.equals("to")){
						notificationConfigurationMasterDTO.setToParty(true);
					}
					if(partyIn.equals("cc")){
						notificationConfigurationMasterDTO.setCcParty(true);
					}
					if(partyIn.equals("bcc")){
						notificationConfigurationMasterDTO.setBccParty(true);
					}
				}
		}
		if(eppsNotificationEvent.getNextUpdatorIn() != null && eppsNotificationEvent.getNextUpdatorIn().length() >0){
			String[] nextUpdatorIns = eppsNotificationEvent.getNextUpdatorIn().split(",");
			for (String nextUpdatorIn : nextUpdatorIns) {
					if(nextUpdatorIn.equals("to")){
						notificationConfigurationMasterDTO.setToNextUpdator(true);
					}
					if(nextUpdatorIn.equals("cc")){
						notificationConfigurationMasterDTO.setCcNextUpdator(true);
					}
					if(nextUpdatorIn.equals("bcc")){
						notificationConfigurationMasterDTO.setBccNextUpdator(true);
					}
				}
				
		}
		String vmPath = ApplicationConstants.EmptyString;
		Boolean isLinux = SystemUtils.IS_OS_LINUX;
		String homeDir = System.getProperty("catalina.home");
		logger.info("Platform isLinux: "+isLinux);
		logger.info("Catalina Home Directory while Updating Template: "+homeDir);
		if(notificationConfigurationMasterDTO.getMailVmPath() != null){
			if(isLinux != null && isLinux){
				if(homeDir != null){
					vmPath = homeDir+File.separator+notificationConfigurationMasterDTO.getMailVmPath();
				}else{
					vmPath = notificationConfigurationMasterDTO.getMailVmPath();
				}
			}else{
				vmPath = notificationConfigurationMasterDTO.getMailVmPath();
			}
			logger.info("VM Path while Updating Template: "+vmPath);
			try {
				notificationConfigurationMasterDTO.setMailBody( new String ( Files.readAllBytes( Paths.get(vmPath))));
			}catch(NoSuchFileException ex) {
				throw new ApplicationException("Email template not found",null,ResponseInfoType.ERROR);
			}
		}
		if(notificationConfigurationMasterDTO.getSmsVmPath() != null){
			if(isLinux != null && isLinux){
				if(homeDir != null){
					vmPath = homeDir+File.separator+notificationConfigurationMasterDTO.getSmsVmPath();
				}else{
					vmPath = notificationConfigurationMasterDTO.getSmsVmPath();
				}
			}else{
				vmPath = notificationConfigurationMasterDTO.getSmsVmPath();
			}
			logger.info("VM Path while Updating Template: "+vmPath);
			try {
				notificationConfigurationMasterDTO.setSmsBody( new String ( Files.readAllBytes( Paths.get(vmPath))));
			}catch(NoSuchFileException ex) {
				throw new ApplicationException("SMS template not found",null,ResponseInfoType.ERROR);
			}
		}
		return notificationConfigurationMasterDTO;
	}

	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED)
	public Long listNotiEventsCount(Integer comapanyCode, Integer divisionCode,	SearchDTO searchDTO) {
		return notificationConfigMasterDaoImpl.listNotiEventsCount(comapanyCode, divisionCode,searchDTO);
	}

	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED)
	public List<NotificationConfigurationMasterDTO> listNotiEvents(Integer comapanyCode, Integer divisionCode,PaginationDTO paginationDTO, SearchDTO searchDTO) {
		return notificationConfigMasterDaoImpl.listNotiEvents(comapanyCode, divisionCode,paginationDTO,searchDTO);
	}

	
	@Deprecated
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED)
	public List<ProgramMasterDTO> getNotiConfiguredPrograms(Integer companyCode, Integer divisionCode, String notiConfigType, String operationMode) {
		if(notiConfigType.equals("E")){
			return notificationConfigMasterDaoImpl.getNotiConfiguredScreens(companyCode);
		}else{
			String activeYn = null;
			if(operationMode.equals(ApplicationConstants.OPERATION_MODE_INSERT)){
				activeYn=ApplicationConstants.FLAG_NO;
			}
			return notificationConfigMasterDaoImpl.getNotiConfiguredScheduledEvents(companyCode,divisionCode, activeYn);
		}
	}


	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void initializeObject(Object object) throws IllegalArgumentException, IllegalAccessException {
		try{
			notificationConfigMasterDaoImpl.getCrntSession().refresh(object);
		}catch(Exception e){
			
		}
		initializeEntity(object);
	}

	private void initializeEntity(Object object) throws IllegalArgumentException, IllegalAccessException {
		if(object != null){
			if(object.getClass().getName().contains("com.epps") && object instanceof HibernateProxy){
				Hibernate.initialize(object);
				object =  ((HibernateProxy) object).getHibernateLazyInitializer().getImplementation();
				initializeEntity(object);
			}else{
				Field[] fields=object.getClass().getDeclaredFields();
				for (Field field : fields) {
					if (field.isAnnotationPresent((Class<? extends Annotation>) EventAttribute.class)) {
						field.setAccessible(true);
						Object fieldValue= field.get(object);
						if(field.getType().getName().contains("com.epps")){
							if (fieldValue instanceof HibernateProxy) {
								Hibernate.initialize(fieldValue);
								fieldValue =  ((HibernateProxy) fieldValue).getHibernateLazyInitializer().getImplementation();
							}
							initializeEntity(fieldValue);
						}else if(Collection.class.isAssignableFrom(field.getType())){
							Set<?> entitySet = (Set<?>) fieldValue;
							
							if(null != entitySet) {
								Iterator<?> i=entitySet.iterator();
								while (i.hasNext()) {
									initializeEntity(i.next());
								}
							}
						}
					}
				}
			}
		}
	}

	
	@Deprecated
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED)
	public List<String> getMailIdOfEmployeeByRole(List<Integer> asList,Integer companyCode,Integer divisionCode, Integer locationCode) {
		return notificationConfigMasterDaoImpl.getMailIdOfEmployeeByRole(asList, companyCode, divisionCode, locationCode);
	}

	/**
	 * {@inheritDoc}
	 */
	@Deprecated
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED)
	public List<String> getMailIdOfEmployeeByEmpIds(String[] employeesIds,Integer companyCode, Integer divisionCode) {
		return notificationConfigMasterDaoImpl.getMailIdOfEmployeeByEmpIds(employeesIds, companyCode, divisionCode);
	}



	private void getNotiColumnMapFromList(List<NotificationConfigAttributeDto> configAttributeDtos,Map<String, NotificationConfigAttributeDto> notiHdrColumnMap,Map<String, NotificationConfigAttributeDto> notiDtlColumnMap) throws SQLException{
		Integer hdrCount = 0;
		Integer dtlCount = 0;
		
		
		for (NotificationConfigAttributeDto configAttributeDto : configAttributeDtos) {
			if(configAttributeDto.getHeaderOrDetail() !=null && configAttributeDto.getHeaderOrDetail().equals(NotificationConstants.NOTI_CONFIG_HEADER_FLAG)){
				configAttributeDto.setColumnId(hdrCount);
				notiHdrColumnMap.put((configAttributeDto.getField() == null ? null : configAttributeDto.getField().toLowerCase()).toString(), configAttributeDto);
				hdrCount++;
			}else{
				configAttributeDto.setColumnId(dtlCount);
				notiDtlColumnMap.put((configAttributeDto.getField() == null ? null : configAttributeDto.getField().toLowerCase()).toString(), configAttributeDto);
				dtlCount++;
			}
		}
	}
	
	
	public String getStringFromFilterArray(String[][] filterCondition){
		StringBuilder builder = new StringBuilder();
		for (String[] strings : filterCondition) {
			for (String string : strings) {
				builder.append(string);
				builder.append(",");
			}
			builder = new StringBuilder(builder.substring(0,builder.length()-1));
			builder.append("@");
		}
		builder = new StringBuilder(builder.substring(0,builder.length()-1));
		return builder.toString();
	}
	
	@Override
	public String [][] getArrayFromString(String filterCondition, Boolean isFromScheduler, Object[] procParamsArray){
		if(filterCondition != null) {
			String [] s =  filterCondition.split("@");
			String [][] strArray = new String [s.length][3] ;
			if(isFromScheduler){
				for (Integer index = 0;index < s.length; index++) {
					String [] st =  s[index].split(",");
					for (Integer arIndex = 0;arIndex < st.length; arIndex++) {
						strArray[index][arIndex] = st[arIndex];
					}
				}
			}else{
				for (Integer index = 0;index < s.length; index++) {
					String [] st =  s[index].split(",");
					for (Integer arIndex = 0;arIndex < st.length; arIndex++) {
						if(arIndex.equals(2)){
							Object obj = procParamsArray[index];
							strArray[index][arIndex] = obj.toString();
						}else{
							strArray[index][arIndex] = st[arIndex];
						}
					}
				}
			}
			return strArray;
		}else {
			return null;
		}
	}

	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED)
	public List<String> getMobNoOfEmployeeByEmpIds(String[] empIds,Integer companyCode, Integer divisionCode) {
		return notificationConfigMasterDaoImpl.getMobNoOfEmployeeByEmpIds(empIds, companyCode, divisionCode);
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED)
	public List<String> getMobNoOfEmployeeByRole( ArrayList<Integer> rolesIntegerList, Integer companyCode,	Integer divisionCode, Integer locationCode) {
		return notificationConfigMasterDaoImpl.getMobNoOfEmployeeByRole(rolesIntegerList, companyCode, divisionCode, locationCode);
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED)
	public ProgramMasterDTO getPidAndTranIndicator(Integer companyCode, String eCode, String mtqrFlag) {
		return notificationConfigMasterDaoImpl.getPidAndTranIndicator(companyCode,eCode, mtqrFlag);
	}

	private void addEmplyeeMailIdsFromRoles(String rolesInString, Integer companyCode, Integer divisionCode,
			List<String> employeeMailIds, Integer locationCode) {
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
			List<String> rolesEmpIds = notificationConfigMasterDaoImpl.getMailIdOfEmployeeByRole(rolesIntegerList, companyCode, divisionCode, locationCode);
			if (null != rolesEmpIds) {
				employeeMailIds.addAll(rolesEmpIds);
			}
		}
	}
	
	private void addEmplyeeMailIdsFromEmpId(String employeesIdsString, Integer companyCode, Integer divisionCode,
    		List<String> employeeMailIds) {
    	String[] empIds = employeesIdsString.split(",");
    	if (empIds != null && empIds.length > 0) {
    		List<String> empMailIds = notificationConfigMasterDaoImpl.getMailIdOfEmployeeByEmpIds(empIds,companyCode, divisionCode);
    		if (null != empMailIds) {
    			employeeMailIds.addAll(empMailIds);
    		}
    	}
    }

	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public List<EppsNotificationPresetEvents> getListOfPredefinedEvents(Integer companyCode, Integer divisionCode, Integer locationCode,
			String predefinedEventNm, String eCode, Integer hdrSrNo, String tranIndicator) throws FileNotFoundException, SQLException, IOException {
		List<EppsNotificationPresetEvents> predefinedEvents = notificationConfigMasterDaoImpl.getListOfPredefinedEvents(companyCode,divisionCode,1,predefinedEventNm);
		if(predefinedEvents != null && !predefinedEvents.isEmpty()){
			Object [] procParamsArray = {predefinedEvents.get(0).getEventProcName(),companyCode,divisionCode,locationCode,tranIndicator,eCode,hdrSrNo};
			String [][] strArray = getArrayFromString(predefinedEvents.get(0).getProcParams(),false, procParamsArray);
			GenericIdValueDTO<ResultSet, ResultSet> notiItemGenericIdValueDTO = notificationConfigMasterDaoImpl.callProcToGetPredefinedNotification(strArray, "proc_noti_preset_events");
			if(notiItemGenericIdValueDTO.getId() != null && notiItemGenericIdValueDTO.getValue() != null){
				/**
				ScheduleBaseExcelDTO baseExcelDTOScheduleBaseExcelDTO = getDictionaryDetailsAndCreateExcel(	predefinedEvents.get(0).getNotiEventSrNo(), notiItemGenericIdValueDTO, "PE", predefinedEvents.get(0).getEventName());
				predefinedEvents.get(0).setExcelFile(baseExcelDTOScheduleBaseExcelDTO.getExcelFile());
				*/
			}
		}
		return predefinedEvents;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public List<NotificationConfigAttributeDto> getColumnNamesForScheduledBasedEvent(Integer notiEventSrNo, String eventAttributePurpose) {
		List<NotificationConfigAttributeDto> scheduledBasedEventHdrColumns=notificationConfigMasterDaoImpl.getColumnNamesForScheduledBasedEvent(notiEventSrNo, "H");
		if(eventAttributePurpose.equals("D")){
			List<NotificationConfigAttributeDto> scheduledBasedEventDtlColumns=notificationConfigMasterDaoImpl.getColumnNamesForScheduledBasedEvent(notiEventSrNo, "D");
			NotificationConfigAttributeDto detailsColumnsTable = new NotificationConfigAttributeDto();
			detailsColumnsTable.setDisplayName("Details");
			detailsColumnsTable.setFieldType("Table");
			detailsColumnsTable.setField("itemNotiDtlJson");
			detailsColumnsTable.setPurpose("D");
			detailsColumnsTable.setColumnsForTable(scheduledBasedEventDtlColumns);
			scheduledBasedEventHdrColumns.add(detailsColumnsTable);
		}
		
		return scheduledBasedEventHdrColumns;
		
	}

	/**
		
	@Override
	@Transactional(propagation = Propagation.REQUIRED,readOnly = false)
	public void sendNotificationsToPartiesForReceivables(String[][] filterCondition, Integer eventHdrSrNo, String procName, String eventName) throws SQLException, JSONException, IllegalAccessException, InvocationTargetException, JsonGenerationException, JsonMappingException, IOException{
//		String [][]  array = {{"itemcode", "=", "170306000004"}, {"comp_cd", "=", "1"}, {"div_cd", "=", "1"}};
		logger.info("Executing Scheduled Event For: "+eventName);
		try {
			GenericIdValueDTO<ResultSet, ResultSet> notiItemGenericIdValueDTO = notificationConfigMasterDaoImpl.callProcToGetItemNotification(filterCondition, procName);
			List<NotificationConfigAttributeDto> configAttributeDtos = notificationConfigMasterDaoImpl.getNotificationDictionary(eventHdrSrNo, "S");
			Map<String, NotificationConfigAttributeDto> notiHdrColumnMap = new HashMap<>();
			Map<String, NotificationConfigAttributeDto> notiDtlColumnMap = new HashMap<>();
			getNotiColumnMapFromList(configAttributeDtos,notiHdrColumnMap,notiDtlColumnMap);

			ResultSet itemNotiDtlResultSet = notiItemGenericIdValueDTO.getValue();
			itemNotiDtlResultSet.beforeFirst();
			Map<String, String> uniquePartyMailIdsMap = new HashMap<>();
			while (itemNotiDtlResultSet.next()) {
				if(itemNotiDtlResultSet.getObject(1) != null && !itemNotiDtlResultSet.getObject(1).equals(ApplicationConstants.EmptyString)){
					uniquePartyMailIdsMap.put((String) itemNotiDtlResultSet.getObject(1),(String) itemNotiDtlResultSet.getObject(1));
				}
			}
			logger.info("Mail Recepient Parties Count: "+uniquePartyMailIdsMap.size()+" IDs: "+uniquePartyMailIdsMap.toString());
			List<EppsNotificationEvents> events = notificationConfigMasterDaoImpl.getListOfEventsForScreen(null, eventHdrSrNo, ApplicationConstants.FLAG_YES, null,null, null, null);
			EppsNotificationEvents eppsNotificationEvent = new EppsNotificationEvents();
			if(!events.isEmpty()){
				eppsNotificationEvent = events.get(0);
			}
			String mailContent = null;
			EmailDTO emailDto = null;
			Boolean isLinux = SystemUtils.IS_OS_LINUX;
			String homeDir = null;
			if(isLinux != null && isLinux){
				homeDir = System.getProperty("catalina.home");
			}
			logger.info("Platform isLinux: "+isLinux);
			logger.info("Catalina Home Directory while Processing Event: "+homeDir);
			String mailVMPath = null;
			if(homeDir != null){
				mailVMPath = homeDir+File.separator+eppsNotificationEvent.getMailVmPath();
			}else{
				mailVMPath = eppsNotificationEvent.getMailVmPath();
			}
			/**
			
						ScheduleBaseExcelDTO baseExcelDTOScheduleBaseExcelDTO = new ScheduleBaseExcelDTO();
			StandardEvaluationContext valueContext = notificationService.initializeSpelContext(baseExcelDTOScheduleBaseExcelDTO);
			for(Map.Entry<String, String> entry:uniquePartyMailIdsMap.entrySet()) {
				SXSSFWorkbook workbook = new SXSSFWorkbook(ReportConstants.EXCEL_ROW_SIZE_100);
				SXSSFSheet sheet = workbook.createSheet((eventName != null ? eventName : "").concat(DateHelper.getFormattedDateString(new Date(),DateHelper.ISO_8601_DATE_FORMAT)+".xlsx"));
				ResultSet itemNotiHdrResultSet = notiItemGenericIdValueDTO.getId();
				ResultSetMetaData metaData = itemNotiHdrResultSet.getMetaData();
				Integer coulumnCount = metaData.getColumnCount();
				itemNotiHdrResultSet.beforeFirst();
				Integer headerRowCount = 0;
				while(itemNotiHdrResultSet.next()){
					for (int index = 1; index < coulumnCount; index++) {
						if(notiHdrColumnMap.get(metaData.getColumnName(index)) != null){
							ScheduleRowDTO rowDTO = new ScheduleRowDTO();
							rowDTO.setCellDTOs(new ArrayList<ScheduleCellDTO>());
							ScheduleCellDTO cellDisplayDTO = new ScheduleCellDTO();
							cellDisplayDTO.setValue(notiHdrColumnMap.get(metaData.getColumnName(index)).getDisplayName());
							cellDisplayDTO.setColumnId(ApplicationConstants.ZERO_INTEGER_TYPE);
							ScheduleCellDTO cellValueDTO = new ScheduleCellDTO();
							cellValueDTO.setValue(itemNotiHdrResultSet.getObject(index));
							cellValueDTO.setColumnId(ApplicationConstants.ONE_INTEGER_TYPE);
							rowDTO.setRowId(index);
							rowDTO.getCellDTOs().add(cellDisplayDTO);
							rowDTO.getCellDTOs().add(cellValueDTO);
							addRowInWorkBook(rowDTO, workbook, sheet);
							headerRowCount = index;
						}
					}
				}
				//		ResultSet itemNotiDtlResultSet = notiItemGenericIdValueDTO.getValue();
				metaData = itemNotiDtlResultSet.getMetaData();
				coulumnCount = metaData.getColumnCount();
				itemNotiDtlResultSet.beforeFirst();
				Integer rowCount = headerRowCount + 2;

				addDetailsHeadersInWorkBook(notiDtlColumnMap, workbook, sheet, rowCount);
				rowCount = rowCount + 1;
				while (itemNotiDtlResultSet.next()) {
					if(entry.getValue().equals(itemNotiDtlResultSet.getObject(1))) {
						for (Integer index = 1; index <= coulumnCount; index++) {
							if(notiDtlColumnMap.get(metaData.getColumnName(index)) != null){
								ScheduleCellDTO cellDTO = new ScheduleCellDTO();
								cellDTO.setValue(itemNotiDtlResultSet.getObject(index));
								cellDTO.setColumnId(notiDtlColumnMap.get(metaData.getColumnName(index)).getColumnId());
								addCellInWorkBook(cellDTO, workbook, sheet, rowCount);
							}
						}
						rowCount++;
					}
				}
				for(int i=0 ; i<= coulumnCount;i++){
					sheet.trackColumnForAutoSizing(i); sheet.autoSizeColumn(i);
				}
				ExcelHelper.resetCellStyle();

				File file = File.createTempFile("Receivable",".xlsx",null);
				if (!file.exists()) {
					file.createNewFile();
				}
				file.deleteOnExit();

				FileOutputStream fileOutputStream = new FileOutputStream(file);
				workbook.write(fileOutputStream);
				baseExcelDTOScheduleBaseExcelDTO.setExcelFile(file);
				baseExcelDTOScheduleBaseExcelDTO.setEventHdrSrNo(eventHdrSrNo);
				mailContent = notificationService.fillVelocityTemplate(mailVMPath, valueContext,
						baseExcelDTOScheduleBaseExcelDTO);
				emailDto = prepareDataForMailForAccountReceivablesPartyWise(eppsNotificationEvent, baseExcelDTOScheduleBaseExcelDTO);
				emailDto.setMailContent(mailContent);
				if(emailDto.getTo() != null && !emailDto.getTo().equals(ApplicationConstants.EmptyString)) {
					emailDto.setTo(emailDto.getTo()+","+entry.getValue());
				}else {
					emailDto.setTo(entry.getValue());
				}
				Map<String, Object> headers = new HashMap<String, Object>();
				headers.put("emailDTO", emailDto);
				logger.info("Sending Scheduled Notification Mail for Account Receivales Party Wise");
				template.sendBodyAndHeaders("direct:sendMailSchedule", baseExcelDTOScheduleBaseExcelDTO, headers);
				fileOutputStream.close();
				workbook.dispose();
				workbook.close();
				try {
					file.delete();
				} catch(Exception e) {
					
					logger.error(e.getMessage(), e);
				}

			}
			
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException e) {
			
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
		}
	}
	

	
	

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED)
	@Deprecated
	public String getCustomerMailId(Integer partyCode, Integer partAddCode,Integer companyCode, Integer divisionCode) {
		return notificationConfigMasterDaoImpl.getCustomerMailId(partyCode, partAddCode, companyCode, divisionCode);
	}
	
	@Deprecated
	@Override
	@SendEvent(isScheduled=true)
	@Transactional(propagation = Propagation.REQUIRED,readOnly = false)
	public ScheduleBaseExcelDTO getItemNotification(String[][] filterCondition, Integer eventHdrSrNo, String procName, String eventName) throws SQLException, JSONException, IllegalAccessException, InvocationTargetException, JsonGenerationException, JsonMappingException, IOException{
//		String [][]  array = {{"itemcode", "=", "170306000004"}, {"comp_cd", "=", "1"}, {"div_cd", "=", "1"}};
		logger.info("Executing Scheduled Event For: "+eventName);
		GenericIdValueDTO<ResultSet, ResultSet> notiItemGenericIdValueDTO = notificationConfigMasterDaoImpl.callProcToGetItemNotification(filterCondition, procName);
		ScheduleBaseExcelDTO baseExcelDTOScheduleBaseExcelDTO = getDictionaryDetailsAndCreateExcel(eventHdrSrNo, notiItemGenericIdValueDTO, "S", eventName);
		return baseExcelDTOScheduleBaseExcelDTO;
	}

	
	private ScheduleBaseExcelDTO getDictionaryDetailsAndCreateExcel(Integer eventHdrSrNo,GenericIdValueDTO<ResultSet, ResultSet> notiItemGenericIdValueDTO, String notiType, String eventName)
			throws SQLException, IOException, FileNotFoundException {
		List<NotificationConfigAttributeDto> configAttributeDtos = notificationConfigMasterDaoImpl.getNotificationDictionary(eventHdrSrNo, notiType);
		Map<String, NotificationConfigAttributeDto> notiHdrColumnMap = new HashMap<>();
		Map<String, NotificationConfigAttributeDto> notiDtlColumnMap = new HashMap<>();
		getNotiColumnMapFromList(configAttributeDtos,notiHdrColumnMap,notiDtlColumnMap);
		SXSSFWorkbook workbook = new SXSSFWorkbook(-1);
		SXSSFSheet sheet = workbook.createSheet((eventName != null ? eventName : "").concat(DateHelper.getFormattedDateString(new Date(),DateHelper.ISO_8601_DATE_FORMAT)+".xlsx"));
		ResultSet itemNotiHdrResultSet = notiItemGenericIdValueDTO.getId();
		ResultSetMetaData metaData = itemNotiHdrResultSet.getMetaData();
		Integer coulumnCount = metaData.getColumnCount();
		Integer headerRowCount = 0;
		while(itemNotiHdrResultSet.next()){
			for (int index = 1; index < coulumnCount; index++) {
				if(notiHdrColumnMap.get(metaData.getColumnName(index)) != null){
					ScheduleRowDTO rowDTO = new ScheduleRowDTO();
					rowDTO.setCellDTOs(new ArrayList<ScheduleCellDTO>());
					ScheduleCellDTO cellDisplayDTO = new ScheduleCellDTO();
					cellDisplayDTO.setValue(notiHdrColumnMap.get(metaData.getColumnName(index)).getDisplayName());
					cellDisplayDTO.setColumnId(ApplicationConstants.ZERO_INTEGER_TYPE);
					ScheduleCellDTO cellValueDTO = new ScheduleCellDTO();
					cellValueDTO.setValue(itemNotiHdrResultSet.getObject(index));
					cellValueDTO.setColumnId(ApplicationConstants.ONE_INTEGER_TYPE);
					rowDTO.setRowId(index);
					rowDTO.getCellDTOs().add(cellDisplayDTO);
					rowDTO.getCellDTOs().add(cellValueDTO);
					addRowInWorkBook(rowDTO, workbook, sheet);
					headerRowCount = index;
				}
			}
		}
		ResultSet itemNotiDtlResultSet = notiItemGenericIdValueDTO.getValue();
		metaData = itemNotiDtlResultSet.getMetaData();
		coulumnCount = metaData.getColumnCount();
		itemNotiDtlResultSet.beforeFirst();
		ScheduleBaseExcelDTO baseExcelDTOScheduleBaseExcelDTO = new ScheduleBaseExcelDTO();
		Integer rowCount = headerRowCount + 2;
		
		addDetailsHeadersInWorkBook(notiDtlColumnMap, workbook, sheet, rowCount);
		rowCount = rowCount + 1;
		while (itemNotiDtlResultSet.next()) {
			for (Integer index = 1; index <= coulumnCount; index++) {
				if(notiDtlColumnMap.get(metaData.getColumnName(index)) != null){
					ScheduleCellDTO cellDTO = new ScheduleCellDTO();
					cellDTO.setValue(itemNotiDtlResultSet.getObject(index));
					cellDTO.setColumnId(notiDtlColumnMap.get(metaData.getColumnName(index)).getColumnId());
					addCellInWorkBook(cellDTO, workbook, sheet, rowCount);
				}
			}
			rowCount++;
		}
		for(int i=0 ; i<= coulumnCount;i++){
			sheet.trackColumnForAutoSizing(i); sheet.autoSizeColumn(i);
		}
		ExcelHelper.resetCellStyle();
		File file = new File("Test.xlsx");
		if(!file.exists()){
			file.createNewFile();
		}
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		workbook.write(fileOutputStream);
		baseExcelDTOScheduleBaseExcelDTO.setExcelFile(file);
		baseExcelDTOScheduleBaseExcelDTO.setEventHdrSrNo(eventHdrSrNo);
		return baseExcelDTOScheduleBaseExcelDTO;
		return null;
		}
		
			@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED)
	public String getSupplierMobileNo(Integer partyCode, Integer partAddCode,Integer companyCode, Integer divisionCode) {
		return notificationConfigMasterDaoImpl.getSupplierMobileNo(partyCode, partAddCode, companyCode, divisionCode);
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED)
	public String getCustomerMobileNo(Integer partyCode, Integer partAddCode,Integer companyCode, Integer divisionCode) {
		return notificationConfigMasterDaoImpl.getCustomerMobileNo(partyCode, partAddCode, companyCode, divisionCode);
	}
	
		
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED)
	@Deprecated
	public String getSupplierMailId(Integer partyCode, Integer partAddCode,	Integer companyCode, Integer divisionCode) {
		return notificationConfigMasterDaoImpl.getSupplierMailId(partyCode, partAddCode, companyCode, divisionCode);
	}

	
		public SXSSFWorkbook itemNotificationExcelDownload(List<ScheduleRowDTO> headerRowDtos, List<ScheduleRowDTO> detailsRowDtos, Map<String, NotificationConfigAttributeDto> notiDtlColumnMap, Integer headerRowId) {
		
		SXSSFWorkbook workbook = new SXSSFWorkbook(-1);
			
		SXSSFSheet sheet = workbook.createSheet("ItemNotification".concat(DateHelper.getFormattedDateString(new Date(),DateHelper.ISO_8601_DATE_FORMAT)+".xlsx"));
		HashMap<Integer,Object> headerList = new HashMap<Integer,Object>();
		Integer columnCount = 0;
		for (ScheduleRowDTO row : headerRowDtos) {
			for(ScheduleCellDTO cellDTO : row.getCellDTOs()){
				headerList.clear();
				headerList.put(cellDTO.getColumnId(),cellDTO.getValue());
				ExcelHelper.createHeaderForExcelStreamWithFormat(sheet, row.getRowId(), headerList,workbook,null,null,null, null, null);
				 
			}
		}
		
		for (Entry<String, NotificationConfigAttributeDto> entry: notiDtlColumnMap.entrySet()) {
			headerList.clear();
			headerList.put(entry.getValue().getColumnId(),entry.getValue().getDisplayName());
			ExcelHelper.createHeaderForExcelStreamWithFormat(sheet, headerRowId , headerList,workbook,null,null, null, null, null);
		}
		
		for (ScheduleRowDTO row : detailsRowDtos) {
			for(ScheduleCellDTO cellDTO : row.getCellDTOs()){
				headerList.clear();
				headerList.put(cellDTO.getColumnId(),cellDTO.getValue());
				if(columnCount < cellDTO.getColumnId()){
					columnCount = cellDTO.getColumnId();
				}
				ExcelHelper.createHeaderForExcelStreamWithFormat(sheet, row.getRowId(), headerList,workbook,null,null,null, null, null);
			}
		}
		for(int i=0 ; i<= columnCount;i++){
			sheet.trackColumnForAutoSizing(i); sheet.autoSizeColumn(i);
		}
		ExcelHelper.resetCellStyle();
		
		return workbook;
	}
	
	private void addRowInWorkBook(ScheduleRowDTO scheduleRowDTO,SXSSFWorkbook workbook,Sheet sheet){
		HashMap<Integer,Object> headerList = new HashMap<Integer,Object>();
		Integer columnCount = 0;
		for(ScheduleCellDTO cellDTO : scheduleRowDTO.getCellDTOs()){
			headerList.clear();
			headerList.put(cellDTO.getColumnId(),cellDTO.getValue());
			if(columnCount < cellDTO.getColumnId()){
				columnCount = cellDTO.getColumnId();
			}
			ExcelHelper.createHeaderForExcelStreamWithFormat(sheet, scheduleRowDTO.getRowId(), headerList,workbook,null,null,null, null, null);
		}
	}
	
	private void addCellInWorkBook(ScheduleCellDTO cellDTO,SXSSFWorkbook workbook,Sheet sheet,Integer rowId){
		HashMap<Integer,Object> headerList = new HashMap<Integer,Object>();
		Integer columnCount = 0;
		headerList.clear();
		headerList.put(cellDTO.getColumnId(),cellDTO.getValue());
		if(columnCount < cellDTO.getColumnId()){
			columnCount = cellDTO.getColumnId();
		}
		ExcelHelper.createHeaderForExcelStreamWithFormat(sheet, rowId , headerList,workbook,null,null,null,null, null);
	}
	
	private void addHeaderInWorkBook(List<ScheduleRowDTO> headerRowDtos,SXSSFWorkbook workbook,Sheet sheet){
		HashMap<Integer,Object> headerList = new HashMap<Integer,Object>();
		for (ScheduleRowDTO row : headerRowDtos) {
			for(ScheduleCellDTO cellDTO : row.getCellDTOs()){
				headerList.clear();
				headerList.put(cellDTO.getColumnId(),cellDTO.getValue());
				ExcelHelper.createHeaderForExcelStreamWithFormat(sheet, row.getRowId(), headerList,workbook,null,null, null,null, null);
			}
		}
	}
	
	private void addDetailsHeadersInWorkBook(Map<String, NotificationConfigAttributeDto> notiDtlColumnMap,SXSSFWorkbook workbook,Sheet sheet,Integer rowId){
		HashMap<Integer,Object> headerList = new HashMap<Integer,Object>();
		for (Entry<String, NotificationConfigAttributeDto> entry: notiDtlColumnMap.entrySet()) {
			headerList.clear();
			headerList.put(entry.getValue().getColumnId(),entry.getValue().getDisplayName());
			ExcelHelper.createHeaderForExcelStreamWithFormat(sheet, rowId , headerList,workbook,null,null, null,null, null);
		}
	}
	
	
	private EmailDTO prepareDataForMailForAccountReceivablesPartyWise(EppsNotificationEvents eppsNotificationEvent,ScheduleBaseExcelDTO excelDTO) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException{
	    	
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
			File file = excelDTO.getExcelFile();
			List<MailAttachments> mailAttachemnts = new ArrayList<>();
			MailAttachments attachments = new MailAttachments();
			mailAttachemnts.add(attachments);
			attachments.addAttachment(eppsNotificationEvent.getEventName(), FileUtils.readFileToByteArray(file),ResponseUtility.EXCEL_CONTENT_TYPE);
	    	EmailDTO emailDto = new EmailDTO(null, toMailIds, ccMailIds, bccMailIds, eppsNotificationEvent.getMailSubject(), null, null, null, null,
					mailAttachemnts, null, null);
	    	return emailDto;
    }
    
    	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public Integer saveNotificationConfig(NotificationConfigurationMasterDTO notificationConfigurationMasterDTO, UserProfileVO userProfileVO) throws IllegalAccessException, InvocationTargetException {
		EppsNotificationEvents eppsNotificationEvent = null;
		EmployeeDefaultRoleDetailsVO userRoleDetails=userProfileVO.getDefaultRoleDetailsVO();
		String directoryPath = commonFileUploadDAOImpl.getConfigParamFilePath(userProfileVO.getCompanyCode()).getFilePath()+File.separator+CommonConstants.DOCUMENTS_UPLOAD_FOLDER_NAME+File.separator+userProfileVO.getDivisionCode();
		String dirPath =  directoryPath;
		Boolean isLinux = SystemUtils.IS_OS_LINUX;
		logger.info("Platform isLinux: "+isLinux);
		if(isLinux != null && isLinux){
			String homeDir = System.getProperty("catalina.home");
			logger.info("Catalina Home Directory while Saving Template: "+homeDir);
			if(homeDir != null){
				directoryPath = homeDir+File.separator+directoryPath;
			}
		}
		File dir = new File(directoryPath);
	        if (!dir.exists())
	            dir.mkdirs();
		String eventfileName= File.separator+notificationConfigurationMasterDTO.getEventName();
		String emailVmPath=directoryPath+eventfileName+"_EMAIL.vm";
		String smsVmPath=directoryPath+eventfileName+"_SMS.vm";
		logger.info("Email VM Path while Saving Template: "+emailVmPath);
		logger.info("SMS VM Path while Saving Template: "+smsVmPath);
		String emailVmPathToSave = dirPath+eventfileName+"_EMAIL.vm";
		String smsVmPathToSave = dirPath+eventfileName+"_SMS.vm";
		
		if(null != notificationConfigurationMasterDTO.getNotiEventSrNo()){
			eppsNotificationEvent = notificationConfigMasterDaoImpl.getMasterObject(EppsNotificationEvents.class, notificationConfigurationMasterDTO.getNotiEventSrNo());
			eppsNotificationEvent.setUpdatedBy(userProfileVO.getEmployeeCode());
			eppsNotificationEvent.setUpdaterRole(userRoleDetails.getDefaultRoleCode());
		}else{
			eppsNotificationEvent = new EppsNotificationEvents();
			eppsNotificationEvent.setCreatedBy(userProfileVO.getEmployeeCode());
			eppsNotificationEvent.setCreatorRole(userRoleDetails.getDefaultRoleCode());
		}
		BeanUtils.copyProperties(eppsNotificationEvent, notificationConfigurationMasterDTO);
		
		if(ApplicationConstants.FLAG_YES.equals(notificationConfigurationMasterDTO.getScheduledYn())){
			eppsNotificationEvent.setCondition(getStringFromFilterArray(notificationConfigurationMasterDTO.getFilterCondition()));
		}
		
		eppsNotificationEvent.setCompanyCode(userProfileVO.getCompanyCode());
		eppsNotificationEvent.setDivisionCode(userProfileVO.getDivisionCode());
		eppsNotificationEvent.setFileUploadSrNo(notificationConfigurationMasterDTO.getFileUploadSrNo());
		StringBuilder partyIn=new StringBuilder(); 
		StringBuilder nextUpdatorIn=new StringBuilder(); 
		if(null != notificationConfigurationMasterDTO.getToParty() && notificationConfigurationMasterDTO.getToParty()){
			partyIn.append("to");
		}
		if(null != notificationConfigurationMasterDTO.getCcParty() && notificationConfigurationMasterDTO.getCcParty()){
			if(partyIn.length()>0)
			partyIn.append(",cc");
			else
				partyIn.append("cc");
		}
		
		if(null != notificationConfigurationMasterDTO.getBccParty() && notificationConfigurationMasterDTO.getBccParty()){
			if(partyIn.length()>0)
			partyIn.append(",bcc");
			else
				partyIn.append("bcc");
		}
		
		eppsNotificationEvent.setPartyIn(partyIn.toString());
		
		if(null != notificationConfigurationMasterDTO.getToNextUpdator() && notificationConfigurationMasterDTO.getToNextUpdator()){
			nextUpdatorIn.append("to");
		}
		if(null != notificationConfigurationMasterDTO.getCcNextUpdator() && notificationConfigurationMasterDTO.getCcNextUpdator()){
			if(nextUpdatorIn.length()>0)
				nextUpdatorIn.append(",cc");
			else
				nextUpdatorIn.append("cc");
		}
		
		if(null != notificationConfigurationMasterDTO.getBccNextUpdator() && notificationConfigurationMasterDTO.getBccNextUpdator()){
			if(nextUpdatorIn.length()>0)
				nextUpdatorIn.append(",bcc");
			else
				nextUpdatorIn.append("bcc");
		}
		eppsNotificationEvent.setNextUpdatorIn(nextUpdatorIn.toString());
		try {
			FileUtils.writeStringToFile(new File(emailVmPath), notificationConfigurationMasterDTO.getMailBody());
			FileUtils.writeStringToFile(new File(smsVmPath), notificationConfigurationMasterDTO.getSmsBody());
			eppsNotificationEvent.setMailVmPath(emailVmPathToSave);
			eppsNotificationEvent.setSmsVmPath(smsVmPathToSave);
		} catch (IOException e) {
			throw new ApplicationException(ErrorCode.GENERIC_ERROR);
		}
		notificationConfigMasterDaoImpl.replaceObjectBlankValueWithNull(eppsNotificationEvent);
		if(eppsNotificationEvent.getActiveYn() == null || eppsNotificationEvent.getActiveYn().isEmpty()) {
			eppsNotificationEvent.setActiveYn(ApplicationConstants.FLAG_NO);
			notificationConfigurationMasterDTO.setActiveYn(ApplicationConstants.FLAG_NO);
		}
		notificationConfigMasterDaoImpl.saveOrUpdate(eppsNotificationEvent);
		notificationConfigurationMasterDTO.setNotiEventSrNo(eppsNotificationEvent.getNotiEventSrNo());
		
		if(ApplicationConstants.FLAG_YES.equals(notificationConfigurationMasterDTO.getScheduledYn()) && ApplicationConstants.FLAG_YES.equals(notificationConfigurationMasterDTO.getActiveYn())){
			try {
				jobSchedularImpl.scheduleEvent(eppsNotificationEvent);
			} catch (Exception e) {
				
			}
		}else if(ApplicationConstants.FLAG_YES.equals(notificationConfigurationMasterDTO.getScheduledYn()) && ApplicationConstants.FLAG_NO.equals(notificationConfigurationMasterDTO.getActiveYn())){
			try {
				jobSchedularImpl.commonDeleteJob(eppsNotificationEvent.getEventName());
			} catch (SchedulerException e) {
				
			} 
		}
		return eppsNotificationEvent.getNotiEventSrNo();
		
	}
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED)
	public List<ProgramMasterDTO> getNotiConfiguredSubPrograms(Integer companyCode, Integer divisionCode, String notiConfigType,String operationMode, String programId) {
		if(notiConfigType != null && notiConfigType.equals(NotificationConstants.NOTI_CONFIG_TYPE_EVENT)){
			return notificationConfigMasterDaoImpl.getNotiConfiguredSubScreens(companyCode,programId);
		}else{
			return null;
		}
	}
	
	 */
}
