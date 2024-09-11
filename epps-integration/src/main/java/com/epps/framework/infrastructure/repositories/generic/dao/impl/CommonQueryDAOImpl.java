package com.epps.framework.infrastructure.repositories.generic.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.jdbc.Work;
import org.hibernate.query.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PGobject;
import org.springframework.stereotype.Repository;

import com.epps.framework.application.api.beans.EppsAliasToBeanTransformer;
import com.epps.framework.application.constant.ApplicationConstants;
import com.epps.framework.application.threads.CustomThreadLocal;
import com.epps.framework.application.txmgmt.EppsTransactional;
import com.epps.framework.application.util.logger.ApplicationLogger;
import com.epps.framework.common.domain.model.entities.EppsModuleMaster;
import com.epps.framework.common.domain.model.entities.EppsModuleMasterPK;
import com.epps.framework.domain.exception.ApplicationException;
import com.epps.framework.domain.exception.DatabaseException;
import com.epps.framework.domain.exception.ErrorCode;
import com.epps.framework.domain.exception.ProcedureException;
import com.epps.framework.domain.exception.ResponseInfoType;
import com.epps.framework.infrastructure.repositories.generic.dao.ICommonQueryDAO;
import com.epps.framework.interfaces.responses.dtos.CommonQueryParametersDTO;
import com.epps.framework.interfaces.responses.dtos.PaginationDTO;
import com.epps.framework.interfaces.responses.dtos.SearchDTO;


/**
 * @author Abhinesh
 *
 */
@Repository("commonQueryDAOImpl")
public class CommonQueryDAOImpl extends GenericDAO<EppsModuleMaster, EppsModuleMasterPK> implements ICommonQueryDAO {
	private static final ApplicationLogger logger = new ApplicationLogger(CommonQueryDAOImpl.class);

	public CommonQueryDAOImpl() {
		super(EppsModuleMaster.class);
	}

	@Override
	public <T, S,V> List<S> getAllDataListFromTable(CommonQueryParametersDTO<T, S> commonQueryParamerersDTO,PaginationDTO pagination,
			SearchDTO searchDTO, Boolean countQueryYn, StringBuilder entityDetailAlias, Class<V> entityDetailClass, StringBuilder selectedColumnQuery, StringBuilder entityAlias) 
					throws ApplicationException,DatabaseException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		StringBuilder hqlQueryString = new StringBuilder(ApplicationConstants.SELECT);
		
		if(!countQueryYn && null == selectedColumnQuery) {
			getColumnListForHqlQuery(hqlQueryString,commonQueryParamerersDTO.getEntityClass().newInstance(), null, null);
		}else if(null != entityDetailAlias && null == selectedColumnQuery) {
			getColumnListForHqlQuery(hqlQueryString,entityDetailClass.newInstance(),entityDetailAlias, null);
		}else if(null != selectedColumnQuery){
			hqlQueryString.append(selectedColumnQuery);
		}

		if(null !=commonQueryParamerersDTO.getAdditionalColumnString()) { hqlQueryString.append(commonQueryParamerersDTO.getAdditionalColumnString()) ; }

		hqlQueryString.append(ApplicationConstants.FROM);

		if(null != entityAlias) {
			hqlQueryString.append(commonQueryParamerersDTO.getEntityClass().getSimpleName()).append(" as ").append(entityAlias);
		}else {
			hqlQueryString.append(commonQueryParamerersDTO.getEntityClass().getSimpleName() + ApplicationConstants.AS_ENTITY_ALIAS);
		}

		if(null != commonQueryParamerersDTO.getFetchEntityWithJoinString() ) { hqlQueryString.append(commonQueryParamerersDTO.getFetchEntityWithJoinString());}

		hqlQueryString.append(ApplicationConstants.WHERE);

		if(null != commonQueryParamerersDTO.getSessionId()) {hqlQueryString.append(" entityAlias.sessionId =:sessionId "+ApplicationConstants.AND);}

		if(null != commonQueryParamerersDTO.getIpAddress()) {hqlQueryString.append(" entityAlias.ipAddress = :ipAddress " );}

		if(null != commonQueryParamerersDTO.getAdditionalWhereClasue()) { hqlQueryString.append(commonQueryParamerersDTO.getAdditionalWhereClasue()) ;}

		if(null == pagination && null != commonQueryParamerersDTO.getOrderByFieldWithAlias()) {
			if(null != commonQueryParamerersDTO.getSortOrder()) {
			hqlQueryString.append( ApplicationConstants.ORDER_BY + " " +commonQueryParamerersDTO.getOrderByFieldWithAlias()  +" "+  commonQueryParamerersDTO.getSortOrder() );
			}else {
			hqlQueryString.append( ApplicationConstants.ORDER_BY + " " +commonQueryParamerersDTO.getOrderByFieldWithAlias());	
			}
			}
		Query query = null;
		String hqlString = hqlQueryString.toString();

		if(null != searchDTO || null!= pagination){
			if(commonQueryParamerersDTO.getIsOpenSession()) {
				query = addAdvanceSearchOptionsInHqlCollectionWithOpenSession(searchDTO,hqlString,pagination, false, null);
			}else {
				query = addAdvanceSearchOptionsInHqlCollection(searchDTO,hqlString,pagination, false, null);
			}
		}else{
			if(null !=commonQueryParamerersDTO.getIsOpenSession() && commonQueryParamerersDTO.getIsOpenSession()) {
				query = getHQLQuery(hqlString,true);
			}else {
				query = getHQLQuery(hqlString,false);
			}
		}	

		if(null != commonQueryParamerersDTO.getSessionId()) { query.setParameter(ApplicationConstants.SESSIONID, commonQueryParamerersDTO.getSessionId()); }
		if(null != commonQueryParamerersDTO.getIpAddress()) { query.setParameter(ApplicationConstants.IPADDRESS, commonQueryParamerersDTO.getIpAddress());	}
		if(null != commonQueryParamerersDTO.getParameters() && commonQueryParamerersDTO.getParameters().size()>0) {
			for (Map.Entry<String, Object> entry : commonQueryParamerersDTO.getParameters().entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		if(null != pagination){
			if(null != pagination.getRecordsPerPage() && null != pagination.getStartIndex()){
				query.setFirstResult(pagination.getStartIndex());
				query.setMaxResults(pagination.getRecordsPerPage());
			}
		}
		
		if(!countQueryYn) {
			query.setResultTransformer(new AliasToBeanResultTransformer(commonQueryParamerersDTO.getDtoClass()));
		}
		logger.info("Query : " + query);
		List<S> list = executeHQLSelectQuery(query);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T,S>  Map<String, List<S>> getAllDataMapAsKeyStringFromTable(CommonQueryParametersDTO<T, S> commonQueryParamerersDTO,PaginationDTO pagination,
			SearchDTO searchDTO) throws  ApplicationException,DatabaseException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		StringBuilder hqlQueryString = new StringBuilder(ApplicationConstants.SELECT);
		getColumnListForHqlQuery(hqlQueryString,commonQueryParamerersDTO.getEntityClass().newInstance(), null, null);

		if(null != commonQueryParamerersDTO.getAdditionalColumnString()) { hqlQueryString.append(commonQueryParamerersDTO.getAdditionalColumnString()) ; }

		hqlQueryString.append(ApplicationConstants.FROM);

		hqlQueryString.append(commonQueryParamerersDTO.getEntityClass().getSimpleName() + ApplicationConstants.AS_ENTITY_ALIAS);

		if(null != commonQueryParamerersDTO.getFetchEntityWithJoinString() ) { hqlQueryString.append(commonQueryParamerersDTO.getFetchEntityWithJoinString());}

		hqlQueryString.append(ApplicationConstants.WHERE);

		if(null != commonQueryParamerersDTO.getSessionId()) {hqlQueryString.append(" entityAlias.sessionId =:sessionId "+ApplicationConstants.AND);}

		if(null != commonQueryParamerersDTO.getIpAddress()) {hqlQueryString.append(" entityAlias.ipAddress = :ipAddress " );}

		if(null != commonQueryParamerersDTO.getAdditionalWhereClasue()) { hqlQueryString.append(commonQueryParamerersDTO.getAdditionalWhereClasue()) ;}

		Query query = null;
		String hqlString = hqlQueryString.toString();

		if(null != searchDTO || null!= pagination){
			if(commonQueryParamerersDTO.getIsOpenSession()) {
				query = addAdvanceSearchOptionsInHqlCollectionWithOpenSession(searchDTO,hqlString,pagination, false, null);
			}else {
				query = addAdvanceSearchOptionsInHqlCollection(searchDTO,hqlString,pagination, false, null);
			}
		}else{
			if(commonQueryParamerersDTO.getIsOpenSession()) {
				query = getHQLQuery(hqlString,true);
			}else {
				query = getHQLQuery(hqlString,false);
			}
		}	

		if(null != commonQueryParamerersDTO.getSessionId()) { query.setParameter(ApplicationConstants.SESSIONID, commonQueryParamerersDTO.getSessionId()); }	
		if(null != commonQueryParamerersDTO.getIpAddress()) { query.setParameter(ApplicationConstants.IPADDRESS, commonQueryParamerersDTO.getIpAddress());	}
		if(null != commonQueryParamerersDTO.getParameters() && commonQueryParamerersDTO.getParameters().size()>0) {
			for (Map.Entry<String, Object> entry : commonQueryParamerersDTO.getParameters().entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		if(null != pagination){
			if(null != pagination.getRecordsPerPage() && null != pagination.getStartIndex()){
				query.setFirstResult(pagination.getStartIndex());
				query.setMaxResults(pagination.getRecordsPerPage());
			}
		}else if( null != commonQueryParamerersDTO.getOrderByFieldWithAlias() && null != commonQueryParamerersDTO.getSortOrder()) {
			hqlQueryString.append( new StringBuilder(ApplicationConstants.ORDER_BY));
			hqlQueryString.append( commonQueryParamerersDTO.getOrderByFieldWithAlias());
			hqlQueryString.append(commonQueryParamerersDTO.getSortOrder());
		}

		query.setResultTransformer(new EppsAliasToBeanTransformer(commonQueryParamerersDTO.getDtoClass(),commonQueryParamerersDTO.getUniqueFieldName()));
		logger.info("Query : " + query);
		Map<String, List<S>> map = (Map<String, List<S>>) executeHQLSelectQuery(query).get(0);
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T,S>  Map<Integer, List<S>> getAllDataMapAsKeyIntegerFromTable(CommonQueryParametersDTO<T, S> commonQueryParamerersDTO,PaginationDTO pagination,
			SearchDTO searchDTO, StringBuilder entityAlias) 
			throws  ApplicationException,DatabaseException, IllegalArgumentException, IllegalAccessException, InstantiationException{
		Map<Integer, List<S>> map = new HashMap<Integer, List<S>>();
		String entityAliasString = null;
		StringBuilder hqlQueryString = new StringBuilder(ApplicationConstants.SELECT);
		if(null != entityAlias) {
			entityAliasString = entityAlias.toString();
		}
		getColumnListForHqlQuery(hqlQueryString,commonQueryParamerersDTO.getEntityClass().newInstance(), null, entityAliasString);

		if(null != commonQueryParamerersDTO.getAdditionalColumnString()) { hqlQueryString.append(commonQueryParamerersDTO.getAdditionalColumnString()) ; }

		hqlQueryString.append(ApplicationConstants.FROM);

		if(null != entityAlias) {
			hqlQueryString.append(commonQueryParamerersDTO.getEntityClass().getSimpleName()).append(" as ").append(entityAlias);
		}else {
			hqlQueryString.append(commonQueryParamerersDTO.getEntityClass().getSimpleName() + ApplicationConstants.AS_ENTITY_ALIAS);
		}

		if(null != commonQueryParamerersDTO.getFetchEntityWithJoinString() ) { hqlQueryString.append(commonQueryParamerersDTO.getFetchEntityWithJoinString());}

		hqlQueryString.append(ApplicationConstants.WHERE);

		if(null != commonQueryParamerersDTO.getSessionId()) {hqlQueryString.append(" entityAlias.sessionId =:sessionId "+ ApplicationConstants.AND);}
		if(null != commonQueryParamerersDTO.getIpAddress()) {hqlQueryString.append(" entityAlias.ipAddress = :ipAddress " );}

		if(null != commonQueryParamerersDTO.getAdditionalWhereClasue()) { hqlQueryString.append(commonQueryParamerersDTO.getAdditionalWhereClasue()) ;}

		if( null != commonQueryParamerersDTO.getOrderByFieldWithAlias() && null != commonQueryParamerersDTO.getSortOrder()) {
			hqlQueryString.append( new StringBuilder(ApplicationConstants.ORDER_BY));
			hqlQueryString.append( commonQueryParamerersDTO.getOrderByFieldWithAlias());
			hqlQueryString.append(commonQueryParamerersDTO.getSortOrder());
		}
		Query query = null;
		String hqlString = hqlQueryString.toString();

		if(null != searchDTO || null!= pagination){
			if(commonQueryParamerersDTO.getIsOpenSession()) {
				query = addAdvanceSearchOptionsInHqlCollectionWithOpenSession(searchDTO,hqlString,pagination, false, null);
			}else {
				query = addAdvanceSearchOptionsInHqlCollection(searchDTO,hqlString,pagination, false, null);
			}
		}else{

			if(commonQueryParamerersDTO.getIsOpenSession()) {
				query = getHQLQuery(hqlString,true);
			}else {
				query = getHQLQuery(hqlString,false);
			}
		}	

		if(null != commonQueryParamerersDTO.getSessionId()) { query.setParameter(ApplicationConstants.SESSIONID, commonQueryParamerersDTO.getSessionId()); }	
		if(null != commonQueryParamerersDTO.getIpAddress()) { query.setParameter(ApplicationConstants.IPADDRESS, commonQueryParamerersDTO.getIpAddress());	}
		if(null != commonQueryParamerersDTO.getParameters() && commonQueryParamerersDTO.getParameters().size()>0) {
			for (Map.Entry<String, Object> entry : commonQueryParamerersDTO.getParameters().entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		if(null != pagination){
			if(null != pagination.getRecordsPerPage() && null != pagination.getStartIndex()){
				query.setFirstResult(pagination.getStartIndex());
				query.setMaxResults(pagination.getRecordsPerPage());
			}
		}
		else if( null != commonQueryParamerersDTO.getOrderByFieldWithAlias() && null != commonQueryParamerersDTO.getSortOrder()) {
			hqlQueryString.append( new StringBuilder(ApplicationConstants.ORDER_BY));
			hqlQueryString.append( commonQueryParamerersDTO.getOrderByFieldWithAlias());
			hqlQueryString.append(commonQueryParamerersDTO.getSortOrder());
		}

		logger.info("Query : " + query);
		query.setResultTransformer(new EppsAliasToBeanTransformer(commonQueryParamerersDTO.getDtoClass(),commonQueryParamerersDTO.getUniqueFieldName()));
		map = (Map<Integer, List<S>>) executeHQLSelectQuery(query).get(0);
		/*List<S> list = executeHQLSelectQuery(query);
		if(null != list && list.size() > 0) {
			map = (Map<Integer, List<S>>)list;
		}*/
		return map;
	}
	
	@Override
	public <T> Boolean deleteFromTable(Class<T> entityClass ,Integer sessionId,String ipAddress, StringBuilder additionalWhereClasue,Map<String,Object> parameters) throws ApplicationException, DatabaseException {
		StringBuilder hqlQueryString = new StringBuilder( ApplicationConstants.DELETE ); 
		hqlQueryString.append(entityClass.getSimpleName() + ApplicationConstants.AS_ENTITY_ALIAS);

		hqlQueryString.append(ApplicationConstants.WHERE);

		if(null != sessionId) {hqlQueryString.append(" entityAlias.sessionId =:sessionId "+ApplicationConstants.AND);}
		if(null != ipAddress) {hqlQueryString.append(" entityAlias.ipAddress = :ipAddress " );}

		if(null != additionalWhereClasue) { hqlQueryString.append(additionalWhereClasue) ;}
		
		if(null != additionalWhereClasue) { hqlQueryString.append(additionalWhereClasue) ;}

		String hqlString = hqlQueryString.toString();
		Query query = getHQLQuery(hqlString);
		if(null != sessionId) { query.setParameter(ApplicationConstants.SESSIONID, sessionId); }	
		if(null != ipAddress) { query.setParameter(ApplicationConstants.IPADDRESS, ipAddress);	}
		if(null != parameters && parameters.size()>0) {
			for (Map.Entry<String, Object> entry :parameters.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		logger.info("Query : " + query);
		executeHQLDMLQuery(query);
		return true;
	}

	@Override
	public <T, S> List<S> getSumTotalListFromTable(CommonQueryParametersDTO<T, S> commonQueryParamerersDTO,PaginationDTO pagination,
			SearchDTO searchDTO) throws ApplicationException,DatabaseException {
		StringBuilder hqlStringTotal = new StringBuilder(ApplicationConstants.SELECT);
		List<String> fixedLenghtList = Arrays.asList(commonQueryParamerersDTO.getColumnString());

		for(String column : fixedLenghtList){
			hqlStringTotal.append("SUM("+column+") as " +column );
		}

		if(null != commonQueryParamerersDTO.getAdditionalColumnString()) { hqlStringTotal.append(commonQueryParamerersDTO.getAdditionalColumnString()) ; }

		hqlStringTotal.append(ApplicationConstants.FROM);

		hqlStringTotal.append(commonQueryParamerersDTO.getEntityClass().getSimpleName() + ApplicationConstants.AS_ENTITY_ALIAS);

		if(null != commonQueryParamerersDTO.getFetchEntityWithJoinString() ) { hqlStringTotal.append(commonQueryParamerersDTO.getFetchEntityWithJoinString());}

		hqlStringTotal.append(ApplicationConstants.WHERE);

		if(null != commonQueryParamerersDTO.getSessionId()) {hqlStringTotal.append(" entityAlias.sessionId =:sessionId "+ApplicationConstants.AND);}
		if(null != commonQueryParamerersDTO.getIpAddress()) {hqlStringTotal.append(" entityAlias.ipAddress = :ipAddress " );}

		if(null != commonQueryParamerersDTO.getAdditionalWhereClasue()) { hqlStringTotal.append(commonQueryParamerersDTO.getAdditionalWhereClasue()) ;}

		Query query = null;
		String hqlString = hqlStringTotal.toString();
		if(commonQueryParamerersDTO.getIsOpenSession()) {
			query = getHQLQuery(hqlString,true);
		}else {
			query = getHQLQuery(hqlString,false);
		}
		if(null != commonQueryParamerersDTO.getSessionId()) { query.setParameter(ApplicationConstants.SESSIONID, commonQueryParamerersDTO.getSessionId()); }	
		if(null != commonQueryParamerersDTO.getIpAddress()) { query.setParameter(ApplicationConstants.IPADDRESS, commonQueryParamerersDTO.getIpAddress()); }
		if(null != commonQueryParamerersDTO.getParameters() && commonQueryParamerersDTO.getParameters().size()>0) {
			for (Map.Entry<String, Object> entry : commonQueryParamerersDTO.getParameters().entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		query.setResultTransformer(new AliasToBeanResultTransformer(commonQueryParamerersDTO.getDtoClass()));
		logger.info("Query : " + query);
		List<S> list = executeHQLSelectQuery(query);
		return list;
	}

	@Override
	public <T, S> List<T> getAllDataFromNamedQuery(Class<S> dtoClass,String namedQueryName,Map<String,Object> parameters,Boolean isToOpenSession)throws ApplicationException,DatabaseException{
		Query query = getQueryFromNamedQuery(namedQueryName,isToOpenSession);    
		if(null != parameters && parameters.size()>0) {
			for (Map.Entry<String, Object> entry : parameters.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		logger.info("Query : " + query);
		List<T> list=executeHQLSelectQuery(query);
		return list;

	}
	
	@EppsTransactional
	@Override
	public PGobject commonProcedureCallWithOpenSession(JSONObject json, final String procedureCall) throws ProcedureException {
		final List<PGobject> result = new ArrayList<PGobject>();
				try {
					CustomThreadLocal.get().doWork(new Work() {
						@Override
						public void execute(Connection connection) throws SQLException {
							CallableStatement call = null;
							try {
								call = connection.prepareCall(procedureCall);
								call.setObject(1, json, Types.OTHER);
								call.registerOutParameter(2, Types.OTHER);
								call.execute();
								
								PGobject pGobject = (PGobject) call.getObject(2);
								result.add(pGobject);
							}catch (Exception e) {
								throw new ProcedureException(e.getMessage(), ErrorCode.PROCEDURE_ERROR, ResponseInfoType.ERROR);
							}finally {
								if(call != null){
									call.close();
								}
							}
							
						}
					});
				} 
		
			catch (HibernateException e) {
				throw new ProcedureException(e.getMessage(),ErrorCode.PROCEDURE_ERROR, ResponseInfoType.ERROR);
			}catch  (Exception e) {
				throw new ProcedureException(e.getMessage(),ErrorCode.PROCEDURE_ERROR, ResponseInfoType.ERROR);
			}
			
			return result.get(0);			
	}
	@EppsTransactional
	@Override
	public PGobject commonProcedureCallWithOpenSession(JSONArray json, final String procedureCall) throws ProcedureException {
		final List<PGobject> result = new ArrayList<PGobject>();
				try {
					CustomThreadLocal.get().doWork(new Work() {
						@Override
						public void execute(Connection connection) throws SQLException {
							CallableStatement call = null;
							try {
								call = connection.prepareCall(procedureCall);
								call.setObject(1, json.toString(), Types.OTHER);
								call.registerOutParameter(2, Types.OTHER);
								call.execute();
								
								PGobject pGobject = (PGobject) call.getObject(2);
								result.add(pGobject);
							}catch (Exception e) {
								throw new ProcedureException(e.getMessage(), ErrorCode.PROCEDURE_ERROR, ResponseInfoType.ERROR);
							}finally {
								if(call != null){
									call.close();
								}
							}
						}
					});
				} 
		
			catch (HibernateException e) {
				throw new ProcedureException(e.getMessage(),ErrorCode.PROCEDURE_ERROR, ResponseInfoType.ERROR);
			}catch  (Exception e) {
				throw new ProcedureException(e.getMessage(),ErrorCode.PROCEDURE_ERROR, ResponseInfoType.ERROR);
			}
			
			return result.get(0);			
	}
	
	@Override
	public PGobject commonProcedureCallWithHibernateSession(JSONArray json, String procedureCall)throws ProcedureException {
		final List<PGobject> result = new ArrayList<PGobject>();
		try {
			getCrntSession().doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement call = null;
					try {
						call = connection.prepareCall(procedureCall);
						call.setObject(1, json, Types.OTHER);
						call.registerOutParameter(2, Types.OTHER);
						call.execute();
						PGobject pGobject = (PGobject) call.getObject(2);
						result.add(pGobject);
					}catch (Exception e) {
						throw new ProcedureException(e.getMessage(), ErrorCode.PROCEDURE_ERROR, ResponseInfoType.ERROR);
					}finally {
						if(call != null){
							call.close();
						}
					}
				}
			});
		} 

	catch (HibernateException e) {
		throw new ProcedureException(e.getMessage(),ErrorCode.PROCEDURE_ERROR, ResponseInfoType.ERROR);
	}catch  (Exception e) {
		throw new ProcedureException(e.getMessage(),ErrorCode.PROCEDURE_ERROR, ResponseInfoType.ERROR);
	}
	
	return result.get(0);	
	}

	@Override
	public PGobject commonProcedureCallWithHibernateSession(JSONObject json, String procedureCall)throws ProcedureException {
		final List<PGobject> result = new ArrayList<PGobject>();
		try {
			getCrntSession().doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement call = null;
					try {
						call = connection.prepareCall(procedureCall);
						call.setObject(1, json, Types.OTHER);
						call.registerOutParameter(2, Types.OTHER);
						call.execute();
						
						PGobject pGobject = (PGobject) call.getObject(2);
						result.add(pGobject);
						
					}catch (Exception e) {
						throw new ProcedureException(e.getMessage(), ErrorCode.PROCEDURE_ERROR, ResponseInfoType.ERROR);
					}finally {
						if(call != null){
							call.close();
						}
					}
				}
			});
		} 

	catch (HibernateException e) {
		throw new ProcedureException(e.getMessage(),ErrorCode.PROCEDURE_ERROR, ResponseInfoType.ERROR);
	}catch  (Exception e) {
		throw new ProcedureException(e.getMessage(),ErrorCode.PROCEDURE_ERROR, ResponseInfoType.ERROR);
	}
	
	return result.get(0);	
	}

	@EppsTransactional
	@Override
	public List<PGobject> commonProcedureCallForListWithOpenSession(JSONObject json, String procedureCall)
			throws ProcedureException, ApplicationException, Exception {
		final List<PGobject> result = new ArrayList<PGobject>();
		try {
			CustomThreadLocal.get().doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement call = null;
					try {
						call = connection.prepareCall(procedureCall);
						call.setObject(1, json, Types.OTHER);
						call.registerOutParameter(2, Types.OTHER);
						call.registerOutParameter(3, Types.OTHER);
						call.execute();
						
						PGobject pGobject = (PGobject) call.getObject(2);
						result.add(pGobject);
						pGobject = (PGobject) call.getObject(3);
						result.add(pGobject);
					}catch (Exception e) {
						throw new ProcedureException(e.getMessage(), ErrorCode.PROCEDURE_ERROR, ResponseInfoType.ERROR);
					}finally {
						if(call != null){
							call.close();
						}
					}
				}
			});
		} 

	catch (HibernateException e) {
		throw new ProcedureException(e.getMessage(),ErrorCode.PROCEDURE_ERROR, ResponseInfoType.ERROR);
	}catch  (Exception e) {
		throw new ApplicationException(e.getMessage(),ErrorCode.PROCEDURE_ERROR, ResponseInfoType.ERROR);
	}
	
	return result;	
	}
	
	@Override
	public List<PGobject> commonProcedureCallWithHibernateSessionForThreeOP(JSONObject json, String procedureCall)throws ProcedureException {
		final List<PGobject> result = new ArrayList<PGobject>();
		try {
			getCrntSession().doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement call = null;
					try {
						call = connection.prepareCall(procedureCall);
						call.setObject(1, json, Types.OTHER);
						call.registerOutParameter(2, Types.OTHER);
						call.registerOutParameter(3, Types.OTHER);
						call.registerOutParameter(4, Types.OTHER);
						call.execute();
						
						/*PGobject pGobject = (PGobject) call.getObject(2);*/
						result.add((PGobject) call.getObject(2));
						result.add((PGobject) call.getObject(3));
						result.add((PGobject) call.getObject(4));
					}catch (Exception e) {
						throw new ProcedureException(e.getMessage(), ErrorCode.PROCEDURE_ERROR, ResponseInfoType.ERROR);
					}finally {
						if(call != null){
							call.close();
						}
					}
				}
			});
		} 

	catch (HibernateException e) {
		throw new ProcedureException(e.getMessage(),ErrorCode.PROCEDURE_ERROR, ResponseInfoType.ERROR);
	}catch  (Exception e) {
		throw new ProcedureException(e.getMessage(),ErrorCode.PROCEDURE_ERROR, ResponseInfoType.ERROR);
	}
	
	return result;	
	}

}