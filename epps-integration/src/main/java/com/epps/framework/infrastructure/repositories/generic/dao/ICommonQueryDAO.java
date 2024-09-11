/**
 * 
 */
package com.epps.framework.infrastructure.repositories.generic.dao;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PGobject;

import com.epps.framework.common.domain.model.entities.EppsModuleMaster;
import com.epps.framework.common.domain.model.entities.EppsModuleMasterPK;
import com.epps.framework.domain.exception.ApplicationException;
import com.epps.framework.domain.exception.DatabaseException;
import com.epps.framework.domain.exception.ProcedureException;
import com.epps.framework.interfaces.responses.dtos.CommonQueryParametersDTO;
import com.epps.framework.interfaces.responses.dtos.PaginationDTO;
import com.epps.framework.interfaces.responses.dtos.SearchDTO;

/**
 * @author Abhinesh
 *
 */
public interface ICommonQueryDAO extends IGenericDAO<EppsModuleMaster, EppsModuleMasterPK>{
	
	public <T,S,V> List<S> getAllDataListFromTable(CommonQueryParametersDTO<T, S> commonQueryParamerersDTO,PaginationDTO pagination,
	SearchDTO searchDTO, Boolean countQueryYn, StringBuilder entityDetailAlias, Class<V> entityDetailClass, StringBuilder selectedColumnQuery, StringBuilder entityAlias) 
			throws ApplicationException,DatabaseException, IllegalArgumentException, IllegalAccessException, InstantiationException;
	
	public <T,S>  Map<String, List<S>> getAllDataMapAsKeyStringFromTable(CommonQueryParametersDTO<T, S> commonQueryParamerersDTO,PaginationDTO pagination,
			SearchDTO searchDTO)
			throws ApplicationException,DatabaseException, IllegalArgumentException, IllegalAccessException, InstantiationException;
	
	
	public <T,S>  Map<Integer, List<S>> getAllDataMapAsKeyIntegerFromTable(CommonQueryParametersDTO<T, S> commonQueryParamerersDTO,PaginationDTO pagination,
			SearchDTO searchDTO, StringBuilder entityAlias) 
			throws ApplicationException,DatabaseException, IllegalArgumentException, IllegalAccessException, InstantiationException;

	public <T> Boolean deleteFromTable(Class<T> entityClass, Integer sessionId, String ipAddress, StringBuilder additionalWhereClasue,Map<String,Object> parameters) throws ApplicationException;
	
	public <T,S>  List<S> getSumTotalListFromTable(CommonQueryParametersDTO<T, S> commonQueryParamerersDTO,PaginationDTO pagination,
			SearchDTO searchDTO)throws ApplicationException,DatabaseException;

	public <T,S> List<T> getAllDataFromNamedQuery(Class<S> dtoClass,String namedQueryName,Map<String,Object> parameters, Boolean isToOpenSession)throws ApplicationException,DatabaseException;

	public PGobject commonProcedureCallWithOpenSession(JSONObject json, String procedureCall) throws ProcedureException;
	
	public PGobject commonProcedureCallWithOpenSession(JSONArray json, String procedureCall) throws ProcedureException;
	
	public PGobject commonProcedureCallWithHibernateSession(JSONObject json, String procedureCall) throws ProcedureException;
	
	public PGobject commonProcedureCallWithHibernateSession(JSONArray json, String procedureCall) throws ProcedureException;

	public List<PGobject> commonProcedureCallForListWithOpenSession(JSONObject json, String procedureCall)
			throws ProcedureException, ApplicationException, Exception;
	
	List<PGobject> commonProcedureCallWithHibernateSessionForThreeOP(JSONObject json, String procedureCall)throws ProcedureException;

}