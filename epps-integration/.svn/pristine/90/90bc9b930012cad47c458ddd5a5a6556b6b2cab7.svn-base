/**
 * @author Dadaso.Patil
 * @version 1.0
 * @created 9 July 2014
 *
 * IGenericDAO
 *
 * Copyright @ 2014 by epps
 * All Rights Reserved.
 *
 */

package com.epps.framework.infrastructure.repositories.generic.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import com.epps.framework.domain.exception.ApplicationException;
import com.epps.framework.infrastructure.model.entities.BasePrimaryKey;
import com.epps.framework.interfaces.responses.dtos.ColumnDictionaryMasterDTO;
import com.epps.framework.interfaces.responses.dtos.GenericIdValueDTO;
import com.epps.framework.interfaces.responses.dtos.PaginationDTO;
import com.epps.framework.interfaces.responses.dtos.SearchDTO;

/**
 * A base interface for all DAOs. This defines the common methods present in all DAOs. 
 * All other DAOs must extend from this DAO.
 * 
 * @author Dadaso.Patil
 * 
 * @param <E>  Represents the generic Entity class for which the DAO needs to be returned.
 * @param <PK> Represents the type of primary Key class inside the database.
 * 
 */
@SuppressWarnings({ "rawtypes" })
public interface IGenericDAO<E, PK extends Serializable> {

	/**
	 * This method is used to save entity into the database
	 * 
	 * @param newInstance Represents the instance to be saved.
	 * 
	 * @return PK Represents the primary key of the entity
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	public PK create(E newInstance) throws ApplicationException;

	/**
	 * This method is used to update the entity into database.
	 * 
	 * @param entity Represents the instance to be updated.
	 * 
	 * @see org.hibernate.Session#saveOrUpdate(java.lang.Object)
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	void update(E entity) throws ApplicationException;
	
	/**
	 * This method is used to save object into the database
	 * @param object
	 * @return id
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	public BasePrimaryKey saveEntity(Object object) throws ApplicationException;
	
	/**
	 * This method is used to update object into the database
	 * @param newInstance
	 * 
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	public void updateEntity(Object newInstance) throws ApplicationException;
	
	
	
	/**
	 * This method is used to merge object into the database
	 * @param newInstance
	 * 
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	public void mergeEntity(Object newInstance) throws ApplicationException;
	
	/**
	 * This method is used to merge object into the database with open session or long session.
	 * @param newInstance
	 * 
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	void mergeEntityWithOpenSession(Object newInstance);
	
	/**
	 * This method is used to save Or update all objects into the database
	 * @param objects represents collection of objects
	 * 
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	public void saveOrUpdateAll(Collection<?> objects) throws ApplicationException;
	
	/**
	 * This method is used to save Or update object into the database
	 * @param object represents collection of object
	 * 
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	public void saveOrUpdate(Object object) throws ApplicationException ;

	
	/**
	 * This method is used to get the particular row from database and its columns will mapped to
	 * the object describes in the class signature E.
	 * 
	 * @param id 	  Represents the primary key value for which objects needs to be fetched.
	 * 
	 * @return entity Represents the mapped obejct from database row.
	 * 
	 * @see org.hibernate.Session#get(java.lang.Class, java.io.Serializable)
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	E get(PK id) throws ApplicationException;
	
	/**
	 * This method is used to load the proxy for the object describes in the class signature E. This method 
	 * never hit the database until the property (except id property) of that object() is accessed by getter.
	 * 
	 * @param id	  Represents the id of the object for which the proxy needs to be load.
	 * 
	 * @return entity Represents the proxy object.
	 * 
	 * @see org.hibernate.Session#load(java.lang.Class, java.io.Serializable)
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	E load(PK id) throws ApplicationException;
	
	/**
	 * This method is used to return the criteria object for the class describes in the class signature E..
	 * 	
	 * @return Represents the generic criteria object.
	 * 
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	Criteria getCriteria() throws ApplicationException;
	
	/**
	 * This method is used to return the criteria object for the class describes in the class signature E..
	 * 	
	 * @return Represents the generic criteria object.
	 * 
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	Criteria getCriteriaWithAlias(String alias) throws ApplicationException;
	
	/**
	 * This method is used to get the Generic Criteria object.
	 * 
	 * @param entityclass Represents the class for which Criteria object needs be generated.
	 * 
	 * @return	Criteria  Represents the generic criteria object.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	Criteria getCriteria(final Class entityclass) throws ApplicationException;
	
	
	/**
	 * This method is used to get the Generic Criteria object.
	 * 
	 * @param entityclass Represents the class for which Criteria object needs be generated.
	 * 
	 * @return			  Represents the generic criteria object.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	Criteria getCriteriaWithAlias(final Class entityclass,String alias) throws ApplicationException;
	
	Criteria getCriteriaWithAlias(final Class entityclass,String alias,Boolean isToUseOpenSession) throws ApplicationException;
	
	/**
	 * This method is used to get the Generic Criteria object with pagination values.
	 * @param entityclass
	 * @param firstResult
	 * @param maxResults
	 * 
	 * @return Criteria Represents the generic criteria object.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	Criteria getCriteriaWithPagination(final Class entityclass,final int firstResult, final int maxResults) throws ApplicationException;
	
	Criteria getCriteriaWithPaginationAndAlias(final Class entityclass,final String alias, final int firstResult, final int maxResults) throws ApplicationException;
	
	Criteria getCriteriaWithPaginationAndAlias(final Class entityclass,final String alias, final int firstResult, final int maxResults, final Boolean isToUseOpenSession) throws ApplicationException;
	
	/**
	 * This method is used to execute the query of the Criteria object.
	 * 
	 * @param criteria  Represents the criteria object which is going to execute the query.
	 * 
	 * @return List     Represents the results return from the query.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	<V> List<V> executeCriteira(final Criteria criteria) throws ApplicationException;
	
	/**
	 * This method is used to load the proxy for the generic object.This method 
	 * never hit the database until the property (except id property) of that object() is accessed by getter.
	 * 
	 * @param classs Represents the class for which the proxy needs to be generated.
	 * @param id	 Represents the id for which proxy needs to be generated.
	 * 
	 * @return V 	 Represents the proxy object.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	<V extends Object>  V  loadObject(final Class entityClass, final PK id) throws ApplicationException;
	
	/**
	 * This method is used to load the proxy for the generic object.This method 
	 * never hit the database until the property (except id property) of that object() is accessed by getter.
	 * 
	 * @param classs Represents the class for which the proxy needs to be generated.
	 * @param id	 Represents the id for which proxy needs to be generated.
	 * 
	 * @return V 	 Represents the proxy object.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	<V extends Object>  V  loadMasterObject(final Class classs,  Integer id) throws ApplicationException;
	
	/**
	 * This method is used to load the generic object from the database.
	 * 
	 * @param classs  Represents the class for which the object needs to be fetched from database.
	 * @param id	  Represents the id for which the object needs to be fetched from database.
	 * 
	 * @return V	  Represents the generic object.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	<V extends Object>  V  getObject(final Class entityClass, final PK id) throws ApplicationException;
	
	<V extends Object>  V  getMasterObject(final Class classs,  Integer id) throws ApplicationException;
	
	<V extends Object>  V  getMasterObject(final Class classs,  Integer id, Boolean isToUseOpenSession) throws ApplicationException;
	
	<V extends Object>	V  getObjectByPK(final Class classs,BasePrimaryKey basePrimaryKey) throws ApplicationException;
	
	/**
	 * This method is used to get the generic object from database with all mentioned associations.
	 * 	
	 * @param entityClass		 Represents the Class for which the object needs to fetch from database.
	 * @param entityId			 Represents the id for which the obejct needs to be fetch from database.
	 * @param entityAssociations Represents the associations of the object needs to be load with the object.
	 * 
	 * @return V 				 Represents the Object fetched from database.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	<V extends Object> V getEntityWithAssociations(Class entityClass,final PK entityId, final String... entityAssociations) throws ApplicationException;
	
	/**
	 * This method is used to get Query object for Hibernate Query.
	 * 
	 * @param hql Represents the Hibernate Query for which the Query object needs to fetched.
	 * 
	 * @return	Represents the Query object.
	 * 
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	Query getQueryObject(final String hql) throws ApplicationException;
	
	/**
	 * This method is used to get Query object for named Query in hibernate.
	 * @param namedQuery
	 * 
	 * @return Represents the Query object.
	 * @throws ApplicationException throws in case of query or DB Error
	 */
	Query getQueryFromNamedQuery(String namedQuery, Boolean isToUseOpenSession) throws ApplicationException;
	
	/**
	 * This method is used to get the results from the Hibernate DDL Query object.
	 * 
	 * @param query	Represents the hibernate query object for which the results needs to be fetched.
	 * 
	 * @return List Represents the result returns from the database.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	<V> List<V> executeHQLSelectQuery(final Query query) throws ApplicationException;
	
	/**
	 * This method is used to execute Hibernate DML Query object.
	 * 
	 * @param query	Represents the hibernate query object for which the results needs to be fetched.
	 * 
	 * @return represents number of rows inserted/updated or deleted.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	int executeHQLDMLQuery(final Query query) throws ApplicationException;
	
	void delete(final E entity) throws ApplicationException;

	<V> List<V> getEntitiesWithAssociations(Class entityClass, List<PK> entityIds, final String... entityAssociations) throws ApplicationException;

	Query getSQLQueryObject(String sql) throws ApplicationException;

	NativeQuery getSQLQueryObj(String sql) throws ApplicationException;

	void delete(Collection<?> objects) throws ApplicationException;

	<V extends Object>	V  loadMasterByPK(final Class classs,BasePrimaryKey basePrimaryKey) throws ApplicationException;

	Integer saveObject(Object newInstance) throws ApplicationException;

	Query getHQLQuery(String hql) throws ApplicationException;
	
	Query getHQLQuery(String hql, Boolean isToUseOpenSession) throws ApplicationException;

	<V extends Object> V loadMasterObjectByBasePrimaryKey(Class classs, BasePrimaryKey id)throws ApplicationException;

	void deleteEntity(Object newInstance) throws ApplicationException;

	void replaceObjectBlankValueWithNull(Object instance) throws ApplicationException;

	void addAdvanceSearchOptionsInCriteria(SearchDTO searchDTO, Criteria criteria, Boolean isCompositKey, Map<String, String> compositKeyFieldAliasMap) throws ApplicationException;

	Query addAdvanceSearchOptionsInHql(SearchDTO searchDTO, String hqlString,PaginationDTO pagination,Integer count, Boolean isCompositKey, Map<String, String> compositKeyFieldAliasMap) throws ApplicationException;

	Query addAdvanceSearchOptionsInHqlCollection(SearchDTO searchDTO,String hqlString, PaginationDTO pagination, Boolean isCompositKey, Map<String, String> compositKeyFieldAliasMap)	throws ApplicationException;
	
	Query addAdvanceSearchOptionsInHqlCollectionWithOpenSession(SearchDTO searchDTO,String hqlString,PaginationDTO pagination, Boolean isCompositKey, Map<String, String> compositKeyFieldAliasMap) throws ApplicationException;
	
	Query addAdvanceSearchOptionsInHqlCollectionWithGroupBy(SearchDTO searchDTO,String hqlString,String groupByString, PaginationDTO pagination, Boolean isCompositKey, Map<String, String> compositKeyFieldAliasMap)throws ApplicationException;
	
	Query addAdvanceSearchOptionsInHqlCollectionWithGroupBy(SearchDTO searchDTO,String hqlString,String groupByString, PaginationDTO pagination,Boolean isToUseOpenSession, Boolean isCompositKey, Map<String, String> compositKeyFieldAliasMap)throws ApplicationException;

	/**
	 * @author Sudam Jadhav
	 * @param object
	 * @return Boolean
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	Boolean isEmptyObject(Object object) throws IllegalArgumentException, IllegalAccessException;

	/**
	 * @author Sukhada Desale
	 * @description	This Common Method is used to fetch projection list using criteria
	 * @param criteria
	 * @param object
	 * @param proList
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	Criteria getProjectionsForCriteriaQuery(Criteria criteria, Object object,ProjectionList proList)
			throws IllegalArgumentException, IllegalAccessException;

	/**
	 * @author Sukhada Desale 
	 * @description	This Common Method Is used to fetch list of columns using criteria
	 * @param hqlString
	 * @param object
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	String getColumnListForHqlQuery(String hqlString,Object object)throws IllegalArgumentException, IllegalAccessException;

	/**
	 * @author Aniket Jadhav
	 * @description	This Common Method is used delete entity with some restriction and not only on primary key.
	 * @param entityClass for which data to be delted
	 * @param restrictionList on which data will be delete
	 * @throws ApplicationException
	 */
	void deleteEntityWithRestrictions(Class entityClass,List<GenericIdValueDTO<String, Object>> restrictionList) throws ApplicationException;
	
	/**
	 *  @author Gaurav Kamble
	 * @description	Common method to flush hibernate transaction. Used to force flush the hibernate transaction before automatic transaction management by - @Transactional  
	 */
	public void flush();

	Integer saveObjectForCustomSession(Object newInstance) throws ApplicationException;

	void updateEntityForCustomSession(Object newInstance) throws ApplicationException;

	public <T> String getColumnListForReportHqlQuery(String hqlQueryString, Class<T> className, List<ColumnDictionaryMasterDTO> list)
			throws IllegalArgumentException, IllegalAccessException;

	public Session getCrntSession() throws ApplicationException;
	
	public Session getNewOpentSession() throws ApplicationException;

	Query getSQLQueryObject(String sql, Boolean isToUseOpenSession)
			throws ApplicationException;
	/**
	 * @author Abhinandan Patil. 
	 * @description	fetch list of columns using criteria with Level 2 cache Enable.
	 * @param criteria
	 * @return
	 * @throws ApplicationException
	 */
	public <V> List<V> executeCriteiraL2CacheEanable(Criteria criteria)throws ApplicationException;

	Criteria getCachedCriteriaWithAlias(Class entityclass, String alias)throws ApplicationException;

	/**
	 * Make a transient instance persistent. This operation cascades to associated
	 * instances if the association is mapped with {@code cascade="persist"}
	 * <p/>
	 * The semantics of this method are defined by JSR-220.
	 *
	 * @param object a transient instance to be made persistent
	 */
	void persistObject(Object object);

	/**
	 * Make a transient instance persistent. This operation cascades to associated
	 * instances if the association is mapped with {@code cascade="persist"}
	 * <p/>
	 * The semantics of this method are defined by JSR-220.
	 *
	 * @param object a transient instance to be made persistent
	 */
	void persistAllObject(Collection<?> objects);

	public StringBuilder getColumnListForHqlQuery(StringBuilder hqlString, Object object, StringBuilder entityDetailAlias,
			String entityAliasString) throws IllegalArgumentException, IllegalAccessException;

	
//	public Session getOpenSession();
}
