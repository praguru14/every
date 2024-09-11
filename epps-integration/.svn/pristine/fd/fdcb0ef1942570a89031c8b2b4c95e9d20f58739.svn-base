package com.epps.framework.infrastructure.repositories.generic.dao;

import java.util.List;
import java.util.Map;

import com.epps.framework.common.domain.model.entities.EppsModuleMaster;
import com.epps.framework.common.domain.model.entities.EppsModuleMasterPK;
import com.epps.framework.domain.exception.ApplicationException;
import com.epps.framework.domain.exception.DatabaseException;

public interface ICommonStagingDAO extends IGenericDAO<EppsModuleMaster, EppsModuleMasterPK> {

	/**
	 * This method is used to get data POST save in Staging
	 * @param <T>
	 * @param <S>
	 * @param stagingClass
	 * @param stagingDTOClass
	 * @param uploadedTerminalId
	 * @param sessionId
	 * @param hqlColumnString
	 * @return
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	<T,S> List<S> validateDataFromTablePostSaveInStaging(Class<T> stagingClass, Class<S> stagingDTOClass,String uploadedTerminalId,
			Integer sessionId, String hqlColumnString) throws ApplicationException ,DatabaseException;

	/**
	 * This method is used to get sub child data in Map upon unique Header serial No.
	 * @param <T>
	 * @param <S>
	 * @param stagingClass
	 * @param stagingDTOClass
	 * @param uploadedTerminalId
	 * @param sessionId
	 * @param hqlColumnString
	 * @param UniqueHeaderFieldName
	 * @return
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	<T,S>  Map<String, List<S>> validateDataFromTablePostSaveInStaging(Class<T> stagingClass, Class<S> stagingDTOClass,
			String uploadedTerminalId, Integer sessionId, String hqlColumnString, String UniqueHeaderFieldName)throws ApplicationException ,DatabaseException;
	
	/**
	 * This method is used to get sub child data in Map upon unique Header serial No with joined entity
	 * @param <T>
	 * @param <S>
	 * @param stagingClass
	 * @param stagingDTOClass
	 * @param uploadedTerminalId
	 * @param sessionId
	 * @param hqlColumnString
	 * @param UniqueHeaderFieldName
	 * @param fetchEntityWithJoinString
	 * @return
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	<T,S>  Map<String, List<S>> validateDataFromTablePostSaveInStagingWithJoinEntity(Class<T> stagingClass, Class<S> stagingDTOClass,
			String uploadedTerminalId, Integer sessionId, String hqlColumnString, String UniqueHeaderFieldName, String fetchEntityWithJoinString) 
					throws ApplicationException ,DatabaseException;
	
	/**
	 * This method is used to get sub child data in Map upon unique Header serial No with joined entity with primary key as Integer.
	 * @param <T>
	 * @param <S>
	 * @param stagingClass
	 * @param stagingDTOClass
	 * @param uploadedTerminalId
	 * @param sessionId
	 * @param hqlColumnString
	 * @param UniqueHeaderFieldName
	 * @param fetchEntityWithJoinString
	 * @return
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	<T,S>  Map<Integer, List<S>> validateDataFromTablePostSaveInStagingWithJoinEntityWithIntegerKey(Class<T> stagingClass, Class<S> stagingDTOClass,
			String uploadedTerminalId, Integer sessionId, String hqlColumnString, String UniqueHeaderFieldName, String fetchEntityWithJoinString) 
					throws ApplicationException ,DatabaseException;

}
