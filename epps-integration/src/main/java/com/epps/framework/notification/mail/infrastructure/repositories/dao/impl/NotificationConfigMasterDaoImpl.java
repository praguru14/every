package com.epps.framework.notification.mail.infrastructure.repositories.dao.impl;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.hibernate.query.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import com.epps.framework.application.constant.ApplicationConstants;
import com.epps.framework.common.domain.model.entities.EppsModuleMaster;
import com.epps.framework.common.domain.model.entities.EppsModuleMasterPK;
import com.epps.framework.domain.exception.ProcedureException;
import com.epps.framework.domain.exception.ResponseInfoType;
import com.epps.framework.infrastructure.repositories.generic.dao.impl.GenericDAO;
import com.epps.framework.interfaces.responses.dtos.GenericIdValueDTO;
import com.epps.framework.interfaces.responses.dtos.PaginationDTO;
import com.epps.framework.interfaces.responses.dtos.SearchDTO;
import com.epps.framework.notification.mail.application.event.entities.EppsEmployeeLocationLink;
import com.epps.framework.notification.mail.application.event.entities.EppsEmployeeMaster;
import com.epps.framework.notification.mail.application.event.entities.EppsNotificationDictionaryHdr;
import com.epps.framework.notification.mail.application.event.entities.EppsNotificationEvents;
import com.epps.framework.notification.mail.application.event.entities.EppsNotificationPresetEvents;
import com.epps.framework.notification.mail.application.event.entities.EppsProgramMaster;
import com.epps.framework.notification.mail.infrastructure.repositories.dao.INotificationConfigMasterDao;
import com.epps.framework.notification.mail.interfaces.dto.NotificationConfigAttributeDto;
import com.epps.framework.notification.mail.interfaces.dto.NotificationConfigurationMasterDTO;
import com.epps.framework.notification.mail.interfaces.dto.ProgramMasterDTO;

@Repository("notificationConfigMasterDaoImpl")
public class NotificationConfigMasterDaoImpl extends GenericDAO<EppsModuleMaster, EppsModuleMasterPK> implements INotificationConfigMasterDao{

	public NotificationConfigMasterDaoImpl() {
		super(EppsModuleMaster.class);
	}

	
	 
	@Override
	public List<EppsNotificationEvents> getListOfEventsForScreen(String pId, Integer eventSrNo, String activeYn, String scheduleYn, String subScreenName, Integer companyCode,
			Integer divisionCode) {
		Criteria notiInfoCriteria =  getCriteriaWithAlias(EppsNotificationEvents.class, "eppsNotificationEventsAlias");
		if(!ApplicationConstants.EmptyString.equals(pId) && pId != null){
			notiInfoCriteria.add(Restrictions.eq("eppsNotificationEventsAlias.pid", pId));
		}
		if(eventSrNo != null){
			notiInfoCriteria.add(Restrictions.eq("eppsNotificationEventsAlias.notiEventSrNo", eventSrNo));
		}
		
		if(!ApplicationConstants.EmptyString.equals(scheduleYn) && scheduleYn != null){
			notiInfoCriteria.add(Restrictions.eq("eppsNotificationEventsAlias.scheduledYn", scheduleYn));
		}
		
		if(!ApplicationConstants.EmptyString.equals(subScreenName) && subScreenName != null){
			notiInfoCriteria.add(Restrictions.eq("eppsNotificationEventsAlias.subScreenName", subScreenName));
		}
		
		if(companyCode != null){
			notiInfoCriteria.add(Restrictions.eq("eppsNotificationEventsAlias.companyCode", companyCode));
		}
		
		if(divisionCode != null){
			notiInfoCriteria.add(Restrictions.eq("eppsNotificationEventsAlias.divisionCode", divisionCode));
		}
		
		notiInfoCriteria.add(Restrictions.eq("eppsNotificationEventsAlias.activeYn", activeYn));
		
		return executeCriteira(notiInfoCriteria);
	}

	
	@Override
	public EppsNotificationEvents getListOfEventsForScreen(	Integer notiEventSrNo) {
		Criteria notiInfoCriteria =  getCriteriaWithAlias(EppsNotificationEvents.class, "eppsNotificationEventsAlias");
		notiInfoCriteria.add(Restrictions.eq("eppsNotificationEventsAlias.notiEventSrNo", notiEventSrNo));
		List<EppsNotificationEvents> notificationConfigurations =executeCriteira(notiInfoCriteria);
		
		if(notificationConfigurations.size() > 0)
		return notificationConfigurations.get(0);
		
		return null;
	}


	@Override
	public Long listNotiEventsCount(Integer comapanyCode, Integer divisionCode,	SearchDTO searchDTO) {
		Criteria notiInfoCriteria =  getCriteriaWithAlias(EppsNotificationEvents.class, "eppsNotificationEventsAlias");

		notiInfoCriteria.setProjection(Projections.projectionList().add(Projections.count("eppsNotificationEventsAlias.notiEventSrNo")));
			notiInfoCriteria.add(Restrictions.eq("eppsNotificationEventsAlias.companyCode", comapanyCode));
			notiInfoCriteria.add(Restrictions.eq("eppsNotificationEventsAlias.divisionCode", divisionCode));

			Criterion isEventBased = Restrictions.eq("eppsNotificationEventsAlias.scheduledYn", ApplicationConstants.FLAG_NO);
			Criterion isScheduledBased = Restrictions.eq("eppsNotificationEventsAlias.scheduledYn", ApplicationConstants.FLAG_YES);
			Criterion isCronExprPresent = Restrictions.isNotNull("eppsNotificationEventsAlias.scheduledCronExp");
			LogicalExpression orExpression = Restrictions.or(isEventBased, Restrictions.and(isScheduledBased, isCronExprPresent));
			notiInfoCriteria.add(orExpression);
		
		if(null!=searchDTO)
		addAdvanceSearchOptionsInCriteria(searchDTO, notiInfoCriteria,false,null);
		List<Long> eventsCount = executeCriteira(notiInfoCriteria);

		if(null != eventsCount && eventsCount.size()>0){
			return eventsCount.get(0);
		}

		return null;
	}

	
	@Override
	public List<NotificationConfigurationMasterDTO> listNotiEvents(	Integer comapanyCode, Integer divisionCode,
			PaginationDTO paginationDTO, SearchDTO searchDTO) {
		Criteria notiInfoCriteria =  getCriteriaWithPaginationAndAlias(EppsNotificationEvents.class, "eppsNotificationEventsAlias", paginationDTO.getStartIndex(),
				paginationDTO.getRecordsPerPage());
		notiInfoCriteria.createAlias("eppsNotificationEventsAlias.eppsProgramMaster", "eppsProgramMasterAlias", CriteriaSpecification.LEFT_JOIN);
			notiInfoCriteria.add(Restrictions.eq("eppsNotificationEventsAlias.companyCode", comapanyCode));
			notiInfoCriteria.add(Restrictions.eq("eppsNotificationEventsAlias.divisionCode", divisionCode));

		notiInfoCriteria.setProjection(Projections.projectionList()
				.add(Projections.property("eppsNotificationEventsAlias.notiEventSrNo").as("notiEventSrNo"))
				.add(Projections.property("eppsNotificationEventsAlias.eventName").as("eventName"))
				.add(Projections.property("eppsProgramMasterAlias.programDisplayName").as("programName"))
				.add(Projections.property("eppsNotificationEventsAlias.scheduledYn").as("scheduledYn"))
				.add(Projections.property("eppsNotificationEventsAlias.activeYn").as("activeYn"))
				.add(Projections.property("eppsNotificationEventsAlias.fileUploadSrNo").as("fileUploadSrNo"))
				).setResultTransformer(new AliasToBeanResultTransformer(NotificationConfigurationMasterDTO.class));
		
		Criterion isEventBased = Restrictions.eq("eppsNotificationEventsAlias.scheduledYn", ApplicationConstants.FLAG_NO);
		Criterion isScheduledBased = Restrictions.eq("eppsNotificationEventsAlias.scheduledYn", ApplicationConstants.FLAG_YES);
		Criterion isCronExprPresent = Restrictions.isNotNull("eppsNotificationEventsAlias.scheduledCronExp");
		LogicalExpression orExpression = Restrictions.or(isEventBased, Restrictions.and(isScheduledBased, isCronExprPresent));
		notiInfoCriteria.add(orExpression);
		
		/**
		if (paginationDTO.getOrderByAscending()) {
			notiInfoCriteria.addOrder(Order.asc(paginationDTO.getOrderByField())); 
		} else {
			notiInfoCriteria.addOrder(Order.desc(paginationDTO.getOrderByField()));
		}
		*/
		
		if(null!=searchDTO)
			addAdvanceSearchOptionsInCriteria(searchDTO, notiInfoCriteria,false,null);
		List<NotificationConfigurationMasterDTO> events = executeCriteira(notiInfoCriteria);

		return events;
	}

	
	@Deprecated
	@Override
	public List<ProgramMasterDTO> getNotiConfiguredScreens( Integer companyCode) {
		Criteria notiInfoCriteria =  getCriteriaWithAlias(EppsProgramMaster.class, "eppsProgramMasterAlias");
			notiInfoCriteria.add(Restrictions.eq("eppsProgramMasterAlias.eppsProgramMasterPK.companyCode", companyCode));
			notiInfoCriteria.add(Restrictions.eq("eppsProgramMasterAlias.activeYn", ApplicationConstants.FLAG_YES));
			notiInfoCriteria.add(Restrictions.isNotNull("eppsProgramMasterAlias.javaNotificationClsName"));

		notiInfoCriteria.setProjection(Projections.projectionList()
				.add(Projections.property("eppsProgramMasterAlias.programId").as("programId"))
				.add(Projections.property("eppsProgramMasterAlias.programDisplayName").as("programDisplayName"))
				.add(Projections.property("eppsProgramMasterAlias.tranIndicator").as("tranIndicator"))
				.add(Projections.property("eppsProgramMasterAlias.programMtqrFlag").as("programMtqrFlag"))
				.add(Projections.property("eppsProgramMasterAlias.javaNotificationClsName").as("javaNotificationClsName"))
				.add(Projections.property("eppsProgramMasterAlias.eppsProgramMasterPK.programeCode").as("programeCode"))
				.add(Projections.property("eppsProgramMasterAlias.multiScreenYn").as("multiScreenYn"))
				).setResultTransformer(new AliasToBeanResultTransformer(ProgramMasterDTO.class));

		List<ProgramMasterDTO> notiConfiguredScreens = executeCriteira(notiInfoCriteria);

		return notiConfiguredScreens;
	}
	
	
	@Deprecated
	@Override
	public List<ProgramMasterDTO> getNotiConfiguredScheduledEvents(Integer companyCode,
			Integer divisionCode, String activeYn) {
		Criteria notiInfoCriteria =  getCriteriaWithAlias(EppsNotificationEvents.class, "eppsNotificationEventsAlias");
		notiInfoCriteria.createAlias("eppsNotificationEventsAlias.eppsProgramMaster", "eppsProgramMasterAlias", CriteriaSpecification.LEFT_JOIN);
			notiInfoCriteria.add(Restrictions.eq("eppsNotificationEventsAlias.companyCode", companyCode));
			notiInfoCriteria.add(Restrictions.eq("eppsNotificationEventsAlias.divisionCode", divisionCode));
			notiInfoCriteria.add(Restrictions.eq("eppsNotificationEventsAlias.scheduledYn", ApplicationConstants.FLAG_YES));
			if(null != activeYn){
				notiInfoCriteria.add(Restrictions.eq("eppsNotificationEventsAlias.activeYn", activeYn));
			}

		notiInfoCriteria.setProjection(Projections.projectionList()
				.add(Projections.property("eppsNotificationEventsAlias.notiEventSrNo").as("notiEventSrNo"))
				.add(Projections.property("eppsNotificationEventsAlias.eventName").as("eventName"))
				.add(Projections.property("eppsNotificationEventsAlias.activeYn").as("activeYn"))
				).setResultTransformer(new AliasToBeanResultTransformer(ProgramMasterDTO.class));


		List<ProgramMasterDTO> notiConfiguredScreens = executeCriteira(notiInfoCriteria);

		return notiConfiguredScreens;
	}

	
	@Override
	public List<NotificationConfigAttributeDto> getColumnNamesForScheduledBasedEvent(
			Integer notiEventSrNo, String headerOrDetail) {
		String hqlQueryString = 
				"SELECT eppsNotificationDictionaryHdrAlias.columnName as field,"
					 +" eppsNotificationDictionaryHdrAlias.columnDispName as displayName,"
					 +" eppsNotificationDictionaryHdrAlias.columnType as fieldType,"
				   	 +" (case when eppsNotificationDictionaryHdrAlias.headerOrDetail='H' then 'B' end) as purpose"
			+" FROM EppsNotificationDictionaryHdr as eppsNotificationDictionaryHdrAlias "
			+" INNER JOIN eppsNotificationDictionaryHdrAlias.eppsNotificationEvents as eppsNotificationEventsAlias "
			+" WHERE eppsNotificationEventsAlias.notiEventSrNo = :notiEventSrNo"
			+" AND eppsNotificationDictionaryHdrAlias.headerOrDetail = :headerOrDetail"
			+" ORDER BY eppsNotificationDictionaryHdrAlias.columnDispName";
		Query q = getHQLQuery(hqlQueryString);
		q.setParameter("notiEventSrNo", notiEventSrNo);
		q.setParameter("headerOrDetail", headerOrDetail);
		q.setResultTransformer(new AliasToBeanResultTransformer(NotificationConfigAttributeDto.class));

		List<NotificationConfigAttributeDto> eventColumns =executeHQLSelectQuery(q);

		return eventColumns;
	}
	
	
	@Deprecated
	@Override
	public List<String> getMailIdOfEmployeeByRole(List<Integer>  asList, Integer companyCode, Integer divisionCode, Integer locationCode) {
		Criteria criteria = getCriteriaWithAlias(EppsEmployeeLocationLink.class, "eppsEmployeeLocationLinkAlias");
		criteria.createAlias("eppsEmployeeLocationLinkAlias.eppsEmployeeMaster", "eppsEmployeeMasterAlias",CriteriaSpecification.INNER_JOIN); 
		criteria.setProjection(Projections.distinct(Projections.property("eppsEmployeeMasterAlias.employeeMailId")));
		criteria.add(Restrictions.eq("eppsEmployeeLocationLinkAlias.employeeLocationLinkPK.companyCode", companyCode));
		criteria.add(Restrictions.eq("eppsEmployeeLocationLinkAlias.employeeLocationLinkPK.divisionCode",divisionCode));
		criteria.add(Restrictions.eq("eppsEmployeeLocationLinkAlias.activeYn",ApplicationConstants.FLAG_YES));
		criteria.add(Restrictions.eq("eppsEmployeeMasterAlias.activeYn",ApplicationConstants.FLAG_YES));
		criteria.add(Restrictions.in("eppsEmployeeLocationLinkAlias.employeeLocationLinkPK.roleCode",asList));
		if(locationCode != null){
			criteria.add(Restrictions.eq("eppsEmployeeLocationLinkAlias.employeeLocationLinkPK.locationCode",locationCode));
		}
		
		List<String> employeeMailList =  executeCriteira(criteria);
		return employeeMailList;
	}

	
	@Deprecated
	@Override
	public List<String> getMailIdOfEmployeeByEmpIds(String[] employeesIds,Integer companyCode, Integer divisionCode) {
		Criteria employeeMailIDCriteria =  getCriteriaWithAlias(EppsEmployeeMaster.class, "eppsEmployeeMasterAlias");
			employeeMailIDCriteria.add(Restrictions.eq("eppsEmployeeMasterAlias.eppsEmployeeMasterPk.companyCode", companyCode));
			employeeMailIDCriteria.add(Restrictions.eq("eppsEmployeeMasterAlias.eppsEmployeeMasterPk.divisionCode", divisionCode));
			employeeMailIDCriteria.add(Restrictions.eq("eppsEmployeeMasterAlias.activeYn", ApplicationConstants.FLAG_YES));
			employeeMailIDCriteria.add(Restrictions.in("eppsEmployeeMasterAlias.eppsEmployeeMasterPk.employeeCode", employeesIds));

		employeeMailIDCriteria.setProjection(Projections.property("eppsEmployeeMasterAlias.employeeMailId"));

		List<String> employeeMailIDs = executeCriteira(employeeMailIDCriteria);

		return employeeMailIDs;
	}

	@Override
	public List<NotificationConfigAttributeDto> getNotificationDictionary(Integer eventSrNo,String notiType){
		
		Criteria criteria = getCriteriaWithAlias(EppsNotificationDictionaryHdr.class, "eppsNotificationDictionaryHdrAlias");
		if(notiType != null && notiType.equals("S")){
			criteria.createCriteria("eppsNotificationDictionaryHdrAlias.eppsNotificationEvents","eppsNotificationEventsAlias",CriteriaSpecification.INNER_JOIN);
			criteria.add(Restrictions.eq("eppsNotificationEventsAlias.notiEventSrNo", eventSrNo));
		}else if(notiType != null && notiType.equals("PE")){
			criteria.createCriteria("eppsNotificationDictionaryHdrAlias.eppsNotificationPresetEvents","eppsNotificationPresetEventsAlias",CriteriaSpecification.INNER_JOIN);
			criteria.add(Restrictions.eq("eppsNotificationPresetEventsAlias.notiEventSrNo", eventSrNo));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("eppsNotificationDictionaryHdrAlias.columnName").as("field"))
				.add(Projections.property("eppsNotificationDictionaryHdrAlias.columnDispName").as("displayName"))
				.add(Projections.property("eppsNotificationDictionaryHdrAlias.headerOrDetail").as("headerOrDetail"))
				).setResultTransformer(new AliasToBeanResultTransformer(NotificationConfigAttributeDto.class));
		criteria.addOrder(Order.asc("eppsNotificationDictionaryHdrAlias.headerOrDetail"));
		criteria.addOrder(Order.asc("eppsNotificationDictionaryHdrAlias.displayOrder"));
		List<NotificationConfigAttributeDto> configAttributeDtos = executeCriteira(criteria);
		return configAttributeDtos;
	}
	
	/*
	@Override
	public String getSupplierMailId(Integer partyCode, Integer partAddCode,
			Integer companyCode, Integer divisionCode) {
		
		Criteria criteria = getCriteriaWithAlias(EppsSupplierAddressMaster.class,  "eppsSupplierAddressMasterAlias");
		criteria.createAlias("eppsSupplierAddressMasterAlias.eppsSupplierMaster", "eppsSupplierMasterAlias", CriteriaSpecification.INNER_JOIN);
		criteria.setProjection(Projections.property("eppsSupplierAddressMasterAlias.contactEmailId1"));
		criteria.add(Restrictions.eq("eppsSupplierAddressMasterAlias.eppsSupplierAddressMasterPK.companyCode", companyCode));
		criteria.add(Restrictions.eq("eppsSupplierAddressMasterAlias.eppsSupplierAddressMasterPK.divisionCode", divisionCode));
		criteria.add(Restrictions.eq("eppsSupplierAddressMasterAlias.eppsSupplierAddressMasterPK.supplierCode", partyCode));
			criteria.add(Restrictions.eq("eppsSupplierAddressMasterAlias.eppsSupplierAddressMasterPK.supplierAddressCode", partAddCode));
		List<String> supplierMailIds = executeCriteira(criteria);
		if(supplierMailIds.size() > 0){
			return supplierMailIds.get(0);
		}else{
			return "";
		}
	}

	
	@Override
	public String getCustomerMailId(Integer partyCode, Integer partAddCode,
			Integer companyCode, Integer divisionCode) {
		Criteria criteria = getCriteriaWithAlias(EppsCustomerAddMaster.class,  "eppsCustomerAddMasterAlias");
		criteria.createAlias("eppsCustomerAddMasterAlias.eppsCustomerMaster", "eppsCustomerMasterAlias", CriteriaSpecification.INNER_JOIN);
		criteria.setProjection(Projections.property("eppsCustomerAddMasterAlias.contactEmail1"));
		criteria.add(Restrictions.eq("eppsCustomerAddMasterAlias.eppsCustomerAddMasterPK.companyCode", companyCode));
		criteria.add(Restrictions.eq("eppsCustomerAddMasterAlias.eppsCustomerAddMasterPK.divisionCode", divisionCode));
		criteria.add(Restrictions.eq("eppsCustomerAddMasterAlias.eppsCustomerAddMasterPK.customerCode", partyCode));
			criteria.add(Restrictions.eq("eppsCustomerAddMasterAlias.eppsCustomerAddMasterPK.customerAddCode", partAddCode));
		List<String> customerMailIds = executeCriteira(criteria);
		if(customerMailIds.size() > 0){
			return customerMailIds.get(0);
		}else{
			return "";
		}

	}
	
	@Override
	public GenericIdValueDTO<ResultSet, ResultSet> callProcToGetItemNotification(final String[][] filterConditions, final String procName){
		
		final GenericIdValueDTO<ResultSet, ResultSet> genericIdValueDTO = new GenericIdValueDTO<>();
		getCrntSession().doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				CallableStatement call = null;
				final Array filterConditionArray = connection.createArrayOf("varchar", filterConditions);
				try {
					call = connection.prepareCall("{ call epps."+procName+"(?,?)}");
					call.setArray(1, filterConditionArray);
					
					call.registerOutParameter(2, Types.VARCHAR);
					call.execute();
					String [] tableNames = call.getString(2).split(",");
					
					Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				  	ResultSet notiHdrResults =  statement.executeQuery("SELECT * FROM "+tableNames[0]);
				  	
				  	genericIdValueDTO.setId(notiHdrResults);
				  	
				  	Statement notDtlStatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				  	notDtlStatement.setFetchSize(100);
				  	ResultSet notiDtlResults =  notDtlStatement.executeQuery("SELECT * FROM "+tableNames[1]);
				  	
					genericIdValueDTO.setValue(notiDtlResults);
				}catch(Exception e){
					throw new ProcedureException(e.getMessage(),null);
				}
			}
		});
		return genericIdValueDTO;
	}
	
	

	@Override
	public List<ProgramMasterDTO> getNotiConfiguredSubScreens(Integer companyCode, String programId) {
		Criteria notiInfoCriteria =  getCriteriaWithAlias(EppsSubProgramMaster.class, "eppsSubProgramMasterAlias");
		notiInfoCriteria.add(Restrictions.eq("eppsSubProgramMasterAlias.companyCode", companyCode));
		notiInfoCriteria.add(Restrictions.eq("eppsSubProgramMasterAlias.programId", programId));
		notiInfoCriteria.add(Restrictions.eq("eppsSubProgramMasterAlias.activeYn", 1));
		notiInfoCriteria.add(Restrictions.isNotNull("eppsSubProgramMasterAlias.javaNotificationClsName"));

	notiInfoCriteria.setProjection(Projections.projectionList()
			.add(Projections.property("eppsSubProgramMasterAlias.subScreenName").as("programDisplayName"))
			.add(Projections.property("eppsSubProgramMasterAlias.subScreenName").as("subScreenName"))
			.add(Projections.property("eppsSubProgramMasterAlias.javaNotificationClsName").as("javaNotificationClsName"))
			).setResultTransformer(new AliasToBeanResultTransformer(ProgramMasterDTO.class));

	List<ProgramMasterDTO> notiConfiguredSubScreens = executeCriteira(notiInfoCriteria);

	return notiConfiguredSubScreens;
	}
	*/

	@Override
	public List<EppsNotificationPresetEvents> getListOfPredefinedEvents(Integer companyCode, Integer divisionCode, Integer activeYn,
			String predefinedEventNm) {

		Criteria notiCriteria =  getCriteriaWithAlias(EppsNotificationPresetEvents.class, "eppsNotificationPresetEventsAlias");
		
		if(!predefinedEventNm.equals(ApplicationConstants.EmptyString)){
			notiCriteria.add(Restrictions.eq("eppsNotificationPresetEventsAlias.eventName", predefinedEventNm));
		}
		
		if(companyCode != null){
			notiCriteria.add(Restrictions.eq("eppsNotificationPresetEventsAlias.companyCode", companyCode));
		}
		
		if(divisionCode != null){
			notiCriteria.add(Restrictions.eq("eppsNotificationPresetEventsAlias.divisionCode", divisionCode));
		}
		
		notiCriteria.add(Restrictions.eq("eppsNotificationPresetEventsAlias.activeYn", activeYn));
		
		return executeCriteira(notiCriteria);
	
	}

	@Override
	public GenericIdValueDTO<ResultSet, ResultSet> callProcToGetPredefinedNotification(final String[][] filterConditions, final String procName){
		
		final GenericIdValueDTO<ResultSet, ResultSet> genericIdValueDTO = new GenericIdValueDTO<>();
		getCrntSession().doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				CallableStatement call = null;
				final Array filterConditionArray = connection.createArrayOf("varchar", filterConditions);
				try {
					call = connection.prepareCall("{ call epps."+procName+"(?,?,?)}");
					call.setArray(1, filterConditionArray);
					
					call.registerOutParameter(2, Types.VARCHAR);
					call.registerOutParameter(3, Types.VARCHAR);
					call.execute();
					String isToSendMail = call.getString(2);
					if(isToSendMail != null && isToSendMail.equals(ApplicationConstants.FLAG_YES)){
						String [] tableNames = call.getString(3).split(",");
						
						Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						ResultSet notiHdrResults =  statement.executeQuery("SELECT * FROM "+tableNames[0]);
						
						genericIdValueDTO.setId(notiHdrResults);
						
						Statement notDtlStatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						notDtlStatement.setFetchSize(100);
						ResultSet notiDtlResults =  notDtlStatement.executeQuery("SELECT * FROM "+tableNames[1]);
						
						genericIdValueDTO.setValue(notiDtlResults);
					}else{
						genericIdValueDTO.setId(null);
						genericIdValueDTO.setValue(null);
					}
				}catch(Exception e){
					throw new ProcedureException(e.getMessage(),null, ResponseInfoType.ERROR);
				}
			}
		});
		return genericIdValueDTO;
	}

	/**
	@Override
	public String getSupplierMobileNo(Integer partyCode, Integer partAddCode,Integer companyCode, Integer divisionCode) {
		Criteria criteria = getCriteriaWithAlias(EppsSupplierAddressMaster.class,  "eppsSupplierAddressMasterAlias");
		criteria.createAlias("eppsSupplierAddressMasterAlias.eppsSupplierMaster", "eppsSupplierMasterAlias", CriteriaSpecification.INNER_JOIN);
		criteria.setProjection(Projections.property("eppsSupplierAddressMasterAlias.contactCellNumber1"));
		criteria.add(Restrictions.eq("eppsSupplierAddressMasterAlias.eppsSupplierAddressMasterPK.companyCode", companyCode));
		criteria.add(Restrictions.eq("eppsSupplierAddressMasterAlias.eppsSupplierAddressMasterPK.divisionCode", divisionCode));
		criteria.add(Restrictions.eq("eppsSupplierAddressMasterAlias.eppsSupplierAddressMasterPK.supplierCode", partyCode));
			criteria.add(Restrictions.eq("eppsSupplierAddressMasterAlias.eppsSupplierAddressMasterPK.supplierAddressCode", partAddCode));
		List<String> supplierMobileNos = executeCriteira(criteria);
		if(supplierMobileNos.size() > 0){
			return supplierMobileNos.get(0);
		}else{
			return "";
		}
	}

	
	@Override
	public String getCustomerMobileNo(Integer partyCode, Integer partAddCode,
			Integer companyCode, Integer divisionCode) {
		Criteria criteria = getCriteriaWithAlias(EppsCustomerAddMaster.class,  "eppsCustomerAddMasterAlias");
		criteria.createAlias("eppsCustomerAddMasterAlias.eppsCustomerMaster", "eppsCustomerMasterAlias", CriteriaSpecification.INNER_JOIN);
		criteria.setProjection(Projections.property("eppsCustomerAddMasterAlias.contactCell1"));
		criteria.add(Restrictions.eq("eppsCustomerAddMasterAlias.eppsCustomerAddMasterPK.companyCode", companyCode));
		criteria.add(Restrictions.eq("eppsCustomerAddMasterAlias.eppsCustomerAddMasterPK.divisionCode", divisionCode));
		criteria.add(Restrictions.eq("eppsCustomerAddMasterAlias.eppsCustomerAddMasterPK.customerCode", partyCode));
			criteria.add(Restrictions.eq("eppsCustomerAddMasterAlias.eppsCustomerAddMasterPK.customerAddCode", partAddCode));
		List<String> customerMobileNos = executeCriteira(criteria);
		if(customerMobileNos.size() > 0){
			return customerMobileNos.get(0);
		}else{
			return "";
		}
	}

*/
	@Override
	public List<String> getMobNoOfEmployeeByEmpIds(String[] empIds,
			Integer companyCode, Integer divisionCode) {
		Criteria employeeMailIDCriteria =  getCriteriaWithAlias(EppsEmployeeMaster.class, "eppsEmployeeMasterAlias");
		employeeMailIDCriteria.add(Restrictions.eq("eppsEmployeeMasterAlias.eppsEmployeeMasterPk.companyCode", companyCode));
		employeeMailIDCriteria.add(Restrictions.eq("eppsEmployeeMasterAlias.eppsEmployeeMasterPk.divisionCode", divisionCode));
		employeeMailIDCriteria.add(Restrictions.eq("eppsEmployeeMasterAlias.activeYn", ApplicationConstants.FLAG_YES));
		employeeMailIDCriteria.add(Restrictions.in("eppsEmployeeMasterAlias.eppsEmployeeMasterPk.employeeCode", empIds));

	employeeMailIDCriteria.setProjection(Projections.property("eppsEmployeeMasterAlias.employeeMobileNumber"));

	List<String> employeeMobNos = executeCriteira(employeeMailIDCriteria);

	return employeeMobNos;
	}

	@Override
	public List<String> getMobNoOfEmployeeByRole(
			ArrayList<Integer> rolesIntegerList, Integer companyCode,
			Integer divisionCode, Integer locationCode) {
		Criteria criteria = getCriteriaWithAlias(EppsEmployeeLocationLink.class, "eppsEmployeeLocationLinkAlias");
		criteria.createAlias("eppsEmployeeLocationLinkAlias.eppsEmployeeMaster", "eppsEmployeeMasterAlias",CriteriaSpecification.INNER_JOIN); 
		criteria.setProjection(Projections.distinct(Projections.property("eppsEmployeeMasterAlias.employeeMobileNumber")));
		criteria.add(Restrictions.eq("eppsEmployeeLocationLinkAlias.employeeLocationLinkPK.companyCode", companyCode));
		criteria.add(Restrictions.eq("eppsEmployeeLocationLinkAlias.employeeLocationLinkPK.divisionCode",divisionCode));
		criteria.add(Restrictions.eq("eppsEmployeeLocationLinkAlias.activeYn",ApplicationConstants.FLAG_YES));
		criteria.add(Restrictions.eq("eppsEmployeeMasterAlias.activeYn",ApplicationConstants.FLAG_YES));
		criteria.add(Restrictions.in("eppsEmployeeLocationLinkAlias.employeeLocationLinkPK.roleCode",rolesIntegerList));
		if(locationCode != null){
			criteria.add(Restrictions.eq("eppsEmployeeLocationLinkAlias.employeeLocationLinkPK.locationCode",locationCode));
		}
		
		List<String> employeeMobNos =  executeCriteira(criteria);
		return employeeMobNos;
	}

	@Override
	public ProgramMasterDTO getPidAndTranIndicator(Integer companyCode, String eCode, String mtqrFlag) {
		String hqlString =	"SELECT eppsUniEcodeMasterAlias.tranIndicator as tranIndicator,"
				+" (SELECT eppsProgramMasterAlias.programId FROM EppsProgramMaster as eppsProgramMasterAlias "
				+" WHERE eppsProgramMasterAlias.eppsProgramMasterPK.companyCode = :companyCode "
				+ "AND eppsProgramMasterAlias.tranIndicator = eppsUniEcodeMasterAlias.tranIndicator "
				+ "AND eppsProgramMasterAlias.programMtqrFlag = :mtqrFlag "
				+ "AND eppsProgramMasterAlias.activeYn = 'Y') as programId"
				+ " FROM EppsUniEcodeMaster eppsUniEcodeMasterAlias  "
				+ " WHERE eppsUniEcodeMasterAlias.uniEcodeMasterPK.companyCode = :companyCode "
				+ " AND eppsUniEcodeMasterAlias.uniEcodeMasterPK.eppsCode = :eCode ";

		Query query = getHQLQuery(hqlString);
		query.setParameter("companyCode", companyCode);
		query.setParameter("eCode", eCode);
		query.setParameter("mtqrFlag", mtqrFlag);
		query.setResultTransformer(new AliasToBeanResultTransformer(ProgramMasterDTO.class));
		List<ProgramMasterDTO> programMasterDTOs = executeHQLSelectQuery(query);
		if(programMasterDTOs != null && programMasterDTOs.size() > 0) {
			return programMasterDTOs.get(0);
		}else {
			return new ProgramMasterDTO();	
		}
	}


}
