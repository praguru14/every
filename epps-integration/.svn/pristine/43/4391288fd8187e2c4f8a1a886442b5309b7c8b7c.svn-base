
package com.epps.framework.infrastructure.repositories.generic.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.query.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import com.epps.framework.application.api.beans.EppsAliasToBeanTransformer;
import com.epps.framework.common.domain.model.entities.EppsModuleMaster;
import com.epps.framework.common.domain.model.entities.EppsModuleMasterPK;
import com.epps.framework.domain.exception.ApplicationException;
import com.epps.framework.domain.exception.DatabaseException;
import com.epps.framework.infrastructure.repositories.generic.dao.ICommonStagingDAO;

@Repository("commonStagingDAOImpl")
public class CommonStagingDAOImpl extends GenericDAO<EppsModuleMaster, EppsModuleMasterPK> implements ICommonStagingDAO {
	
	public CommonStagingDAOImpl() {
		super(EppsModuleMaster.class);
	}

	/**
	 * 
	 */
	@Override
	public <T,S> List<S> validateDataFromTablePostSaveInStaging(Class<T> stagingClass,Class<S> stagingDTOClass, 
			String uploadedTerminalId, Integer sessionId,String hqlColumnString) throws ApplicationException ,DatabaseException {
		String entityClassInString = stagingClass.getSimpleName();
		
		String hqlQueryString = " Select "
				+ " entityAlias.companyCode as companyCode, "
				//+ " entityAlias.divisionCode as divisionCode, "
				+ " entityAlias.dataUploadRemarks as dataUploadRemarks, "
				+ hqlColumnString
				+ " entityAlias.uploadedFlag as uploadedFlag, " 
				+ " entityAlias.uploadedDate as uploadedDate, " 
				+ " entityAlias.uploadedBy as uploadedBy "
				+ " FROM "
				+ entityClassInString   
				+ " as entityAlias "
				+ " WHERE entityAlias.sessionId= :sessionId "
				+ " AND entityAlias.uploadedTerminalId = :ipAddress "
				//+ " AND entityAlias.uploadedFlag= 0 "
				+ " AND entityAlias.dataUploadRemarks is not null ";
		
		Query query = null;
		query = getHQLQuery(hqlQueryString);	
		query.setParameter("ipAddress", uploadedTerminalId);
		query.setParameter("sessionId", sessionId);
		query.setResultTransformer(new AliasToBeanResultTransformer(stagingDTOClass))	;	
		List<S> dataList = executeHQLSelectQuery(query);
		return dataList;
		
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T,S> Map<String,List<S>> validateDataFromTablePostSaveInStaging(Class<T> stagingClass,Class<S> stagingDTOClass, String uploadedTerminalId, Integer sessionId, 
			String hqlColumnString, String UniqueHeaderFieldName) throws ApplicationException ,DatabaseException {
		String entityClassInString = stagingClass.getSimpleName();
		
		String hqlQueryString = " Select "
				+ " entityAlias.companyCode as companyCode, "
//				+ " entityAlias.divisionCode as divisionCode, "
				+ " entityAlias.dataUploadRemarks as dataUploadRemarks, "
				+ hqlColumnString
				+ " entityAlias.uploadedFlag as uploadedFlag " 
				+ " FROM "
				+ entityClassInString   
				+ " as entityAlias "
				+ " WHERE entityAlias.sessionId= :sessionId "
				+ " AND entityAlias.uploadedTerminalId = :ipAddress "
				+ " AND entityAlias.uploadedFlag= '0' "
				+ " AND entityAlias.dataUploadRemarks is not null ";
		
		Query query = null;
		query = getHQLQuery(hqlQueryString);	
		query.setParameter("ipAddress", uploadedTerminalId);
		query.setParameter("sessionId", sessionId);
		query.setResultTransformer(new EppsAliasToBeanTransformer(stagingDTOClass,UniqueHeaderFieldName));
		Map<String, List<S>> map = (Map<String, List<S>>) executeHQLSelectQuery(query).get(0);
		return map;
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T, S> Map<String, List<S>> validateDataFromTablePostSaveInStagingWithJoinEntity(Class<T> stagingClass,
			Class<S> stagingDTOClass, String uploadedTerminalId, Integer sessionId, String hqlColumnString,
			String UniqueHeaderFieldName, String fetchEntityWithJoinString) throws ApplicationException ,DatabaseException {
		
		String hqlQueryString = " Select "
				+ " entityAlias.companyCode as companyCode, "
	//			+ " entityAlias.divisionCode as divisionCode, "
				+ " entityAlias.dataUploadRemarks as dataUploadRemarks, "
				+ hqlColumnString
				+ " entityAlias.uploadedFlag as uploadedFlag " 
				+ " FROM "
				+ fetchEntityWithJoinString   
				+ " WHERE entityAlias.sessionId= :sessionId "
				+ " AND entityAlias.uploadedTerminalId = :ipAddress "
				+ " AND entityAlias.uploadedFlag= '0' "
				+ " AND entityAlias.dataUploadRemarks is not null ";
		
		Query query = null;
		query = getHQLQuery(hqlQueryString);	
		query.setParameter("ipAddress", uploadedTerminalId);
		query.setParameter("sessionId", sessionId);
		query.setResultTransformer(new EppsAliasToBeanTransformer(stagingDTOClass,UniqueHeaderFieldName));
		Map<String, List<S>> map = (Map<String, List<S>>) executeHQLSelectQuery(query).get(0);
		return map;
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T, S> Map<Integer, List<S>> validateDataFromTablePostSaveInStagingWithJoinEntityWithIntegerKey(Class<T> stagingClass,
			Class<S> stagingDTOClass, String uploadedTerminalId, Integer sessionId, String hqlColumnString,
			String UniqueHeaderFieldName, String fetchEntityWithJoinString) throws ApplicationException ,DatabaseException {
		
		String hqlQueryString = " Select "
				+ " entityAlias.companyCode as companyCode, "
		//		+ " entityAlias.divisionCode as divisionCode, "
				+ " entityAlias.dataUploadRemarks as dataUploadRemarks, "
				+ hqlColumnString
				+ " entityAlias.uploadedFlag as uploadedFlag " 
				+ " FROM "
				+ fetchEntityWithJoinString   
				+ " WHERE entityAlias.sessionId= :sessionId "
				+ " AND entityAlias.uploadedTerminalId = :ipAddress "
				+ " AND entityAlias.uploadedFlag= '0' "
				+ " AND entityAlias.dataUploadRemarks is not null ";
		
		Query query = null;
		query = getHQLQuery(hqlQueryString);	
		query.setParameter("ipAddress", uploadedTerminalId);
		query.setParameter("sessionId", sessionId);
		query.setResultTransformer(new EppsAliasToBeanTransformer(stagingDTOClass,UniqueHeaderFieldName));
		Map<Integer, List<S>> map = (Map<Integer, List<S>>) executeHQLSelectQuery(query).get(0);
		return map;
	}
	
}
