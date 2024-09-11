/**
 * @author Dadaso Patil
 * @version 1.0
 * @created 9 July 2014
 *
 * GenericDAO
 *
 * Copyright @ 2014 by epps
 * All Rights Reserved.
 *
 */
package com.epps.framework.infrastructure.repositories.generic.dao.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.epps.framework.application.constant.ApplicationConstants;
import com.epps.framework.application.threads.CustomThreadLocal;
import com.epps.framework.application.txmgmt.EppsTransactional;
import com.epps.framework.application.util.date.DateHelper;
import com.epps.framework.application.util.logger.ApplicationLogger;
import com.epps.framework.domain.exception.ApplicationException;
import com.epps.framework.domain.exception.DatabaseException;
import com.epps.framework.domain.exception.ErrorCode;
import com.epps.framework.domain.exception.ResponseInfoType;
import com.epps.framework.infrastructure.model.entities.BasePrimaryKey;
import com.epps.framework.infrastructure.repositories.generic.dao.IGenericDAO;
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

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GenericDAO<E, PK extends java.io.Serializable> implements IGenericDAO<E, PK> {

	/**
	 * Represents the hibernate session factory object.
	 */
	protected SessionFactory sessionFactory;

	/**
	 * Represents the entity class for which the DAO has been written.
	 */
	private final Class<? extends E> _entityClass;

	/**
	 * Represents the logger of the application.
	 */
	private static final ApplicationLogger logger = new ApplicationLogger(GenericDAO.class);

	/**
	 * Represents the constructor of the DAO object.
	 * 
	 * @param entityClass represents the calss for which DAO needs to be created.
	 */
	public GenericDAO(final Class<? extends E> entityClass) {
		super();
		_entityClass = entityClass;
	}

	/**
	 * Method returns the current Hibernate session for the thread.
	 * 
	 * @return Session Represents the Hibernate session.
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public Session getCrntSession() throws DatabaseException {
		try {
			return sessionFactory.getCurrentSession();
		} catch (final HibernateException e) {
			logger.error("getCrntSession: failed to complete operation");
			throw new DatabaseException("Failed to get thread session ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}

	/**
	 * Setter for the session factory.
	 * 
	 * @param sessionFactory Represents the hibernate session factory object.
	 */
	@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * This method is used to save entity into the database
	 * 
	 * @param newInstance Represents the instance to be saved.
	 * 
	 * @return PK Represents the primary key of the entity
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public PK create(final E newInstance) throws DatabaseException
	{
		PK id = null;
		final Session session = getCrntSession();
		try {
			logger.debug("save: Adding Object  - {}", newInstance);
			id = (PK) session.save(newInstance);
			logger.debug("save: Insertion completed successfully for  - {}", newInstance);
		} catch (final HibernateException e) {
			logger.error("save: Failed to insert " + newInstance + " successfully", e);
			throw new DatabaseException("Failed to insert  " + newInstance + " successfully", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
		return id;
	}

	/**
	 * This method is used to update the entity into database.
	 * 
	 * @param entity Represents the instance to be updated.
	 * 
	 * @see org.hibernate.Session#saveOrUpdate(java.lang.Object)
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public void update(final E entity) throws DatabaseException {
		try {
			getCrntSession().update(entity);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while refresh entity ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}

	}

	/**
	 * This method is used to save Entity into the database
	 * @param object
	 * @return id
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	public BasePrimaryKey saveEntity(Object newInstance) throws DatabaseException{
		BasePrimaryKey id = null;
		final Session session = getCrntSession();
		try {
			logger.debug("save: Adding Object  - {}", newInstance);
			id = (BasePrimaryKey) session.save(newInstance);
			logger.debug("save: Insertion completed successfully for  - {}", newInstance);
		} catch (final HibernateException e) {
			logger.error("save: Failed to insert " + newInstance + " successfully", e);
			throw new DatabaseException("Failed to insert  " + newInstance + " successfully", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
		return id;
	}


	/**
	 * This method is used to save Entity into the database
	 * @param object
	 * @return id
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public Integer saveObject(Object newInstance) throws DatabaseException{
		Integer id = null;
		final Session session = getCrntSession();
		try {
			logger.debug("save: Adding Object  - {}", newInstance);
			id =  (Integer)session.save(newInstance);
			logger.debug("save: Insertion completed successfully for  - {}", newInstance);
		} catch (final HibernateException e) {
			logger.error("save: Failed to insert " + newInstance + " successfully", e);
			throw new DatabaseException("Failed to insert  " + newInstance + " successfully", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
		return id;
	}

	/**
	 * This method is used to save Entity into the database
	 * @param object
	 * @return id
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	@EppsTransactional
	public Integer saveObjectForCustomSession(Object newInstance) throws DatabaseException{
		Integer id = null;
		final Session session = CustomThreadLocal.get();
		try {
			logger.debug("save: Adding Object  - {}", newInstance);
			id =  (Integer)session.save(newInstance);
			logger.debug("save: Insertion completed successfully for  - {}", newInstance);
		} catch (final HibernateException e) {
			logger.error("save: Failed to insert " + newInstance + " successfully", e);
			throw new DatabaseException("Failed to insert  " + newInstance + " successfully", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
		return id;
	}

	/**
	 * This method is used to update object into the database
	 * @param newInstance Represents newInstance
	 * 
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	@EppsTransactional
	public void updateEntityForCustomSession(Object newInstance) throws DatabaseException{
		final Session session = CustomThreadLocal.get();
		try {
			session.update(newInstance);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while refresh entity ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}

	/**
	 * This method is used to update object into the database
	 * @param newInstance Represents newInstance
	 * 
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	public void updateEntity(Object newInstance) throws DatabaseException{
		try {
			getCrntSession().update(newInstance);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while refresh entity ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}


	/**
	 * This method is used to merge object into the database
	 * @param newInstance Represents newInstance
	 * 
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	public void mergeEntity(Object newInstance) throws DatabaseException{
		try {
			getCrntSession().merge(newInstance);

		} catch (final StaleObjectStateException e) {
			throw new DatabaseException("trying to save by no of users ", ErrorCode.STALE_OBJECT_STATE_EXCEPTION, e, ResponseInfoType.ERROR);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while refresh entity ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}
	
	/**
	 * This method is used to merge object into the database with open session or long session.
	 * @param newInstance Represents newInstance
	 * 
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@EppsTransactional
	public void mergeEntityWithOpenSession(Object newInstance) throws DatabaseException{
		try {
				CustomThreadLocal.get().merge(newInstance);
				
		} catch (final StaleObjectStateException e) {
			throw new DatabaseException("trying to save by no of users ", ErrorCode.STALE_OBJECT_STATE_EXCEPTION, e, ResponseInfoType.ERROR);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while refresh entity ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}

	/**
	 * This method is used to save Or update all objects into the database
	 * @param objects represents collection of objects
	 * 
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public void saveOrUpdateAll(Collection<?> objects) throws DatabaseException  {
		try {
			for (Object object : objects) {
				getCrntSession().saveOrUpdate(object);
			}
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while save or update All ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	} 
	
	/**
	 * This method is used to save Or update  object into the database
	 * @param objects represents collection of object
	 * 
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public void saveOrUpdate(Object object) throws DatabaseException  {
		try {
				getCrntSession().saveOrUpdate(object);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while save or update All ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	} 

	/**
	 * This method is used to get the particular row from database and its columns will mapped to
	 * the object describes in the class signature E.
	 * 
	 * @param id 	  Represents the primary key value for which objects needs to be fetched.
	 * 
	 * @return entity Represents the mapped obejct from database row.
	 * 
	 * @see org.hibernate.Session#get(java.lang.Class, java.io.Serializable)
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public E get(final PK id) throws DatabaseException {
		try {
			return (E) getCrntSession().get(_entityClass, id);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while get entity ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}

	/**
	 * This method is used to load the proxy for the object describes in the class signature E. This method 
	 * never hit the database until the property (except id property) of that object() is accessed by getter.
	 * 
	 * @param id	  Represents the id of the object for which the proxy needs to be load.
	 * 
	 * @return entity Represents the proxy object.
	 * 
	 * @see org.hibernate.Session#load(java.lang.Class, java.io.Serializable)
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public E load(final PK id) throws DatabaseException {
		try {
			return (E) getCrntSession().load(_entityClass, id);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while load entity ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}

	}

	/**
	 * This method is used to return the criteria object for the class describes in the class signature E..
	 * 	
	 * @return Represents the generic criteria object.
	 * 
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public Criteria getCriteria() throws DatabaseException {
		try {
			return getCrntSession().createCriteria(_entityClass);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while creating criteria ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}


	/**
	 * This method is used to return the criteria object for the class describes in the class signature E..
	 * 	
	 * @return Represents the generic criteria object.
	 * 
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public Criteria getCriteriaWithAlias(final String alias) throws DatabaseException {
		try {
			return getCrntSession().createCriteria(_entityClass,alias);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while creating criteria ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}


	/**
	 * This method is used to get the Criteria object with pagination values.
	 * @param entityclass
	 * @param firstResult Represents the starting point for the result set .
	 * @param maxResults Represents the max number for records to be fetched
	 * 
	 * @return Criteria Represents the generic criteria object.
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public Criteria getCriteriaWithPagination(Class entityclass,int firstResult, int maxResults) throws DatabaseException {
		try {

			Criteria criteria =getCrntSession().createCriteria(entityclass);;
			criteria.setFirstResult(firstResult);
			criteria.setMaxResults(maxResults);
			return criteria;
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while creating criteria with pagination", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}

	/**
	 * This method is used to get the Criteria object with pagination values.
	 * @param entityclass
	 * @param firstResult Represents the starting point for the result set .
	 * @param maxResults Represents the max number for records to be fetched
	 * 
	 * @return Criteria Represents the generic criteria object.
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public Criteria getCriteriaWithPaginationAndAlias(Class entityclass,final String alias,int firstResult, int maxResults) throws DatabaseException {
		try {

			Criteria criteria =getCrntSession().createCriteria(entityclass,alias);
			criteria.setFirstResult(firstResult);
			criteria.setMaxResults(maxResults);
			return criteria;
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while creating criteria with pagination", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}
	
	@Override
	public Criteria getCriteriaWithPaginationAndAlias(Class entityclass,final String alias,int firstResult, int maxResults,	Boolean isToUseOpenSession) throws DatabaseException {
		try {
			Criteria criteria = null;
			if(isToUseOpenSession){
				criteria = CustomThreadLocal.get().createCriteria(entityclass,alias);
			}else{
				criteria = getCrntSession().createCriteria(entityclass,alias);
			}
			criteria.setFirstResult(firstResult);
			criteria.setMaxResults(maxResults);
			return criteria;
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while creating criteria with pagination", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}
	
	/**
	 * This method is used to get the Generic Criteria object.
	 * 
	 * @param entityclass Represents the class for which Criteria object needs be generated.
	 * 
	 * @return	Criteria  Represents the generic criteria object.
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public Criteria getCriteria(final Class entityclass) throws DatabaseException {
		try {
			return getCrntSession().createCriteria(entityclass);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while creating criteria ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}

	/**
	 * This method is used to get the Generic Criteria object.
	 * 
	 * @param entityclass Represents the class for which Criteria object needs be generated.
	 * 
	 * @return			  Represents the generic criteria object.
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public Criteria getCriteriaWithAlias(final Class entityclass,final String alias) throws DatabaseException {
		try {
			return getCrntSession().createCriteria(entityclass,alias);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while creating criteria ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}

	@Override
	public Criteria getCriteriaWithAlias(final Class entityclass,final String alias,Boolean isToUseOpenSession) throws DatabaseException {
		try {
			if (isToUseOpenSession != null && isToUseOpenSession) {
				return CustomThreadLocal.get().createCriteria(entityclass,alias);
			}else {
				return getCrntSession().createCriteria(entityclass,alias);
			}
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while creating criteria ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}

	/**
	 * This method is used to execute the query of the Criteria object.
	 * 
	 * @param criteria  Represents the criteria object which is going to execute the query.
	 * 
	 * @return List     Represents the results return from the query.
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public <V> List<V> executeCriteira(final Criteria criteria) throws DatabaseException {
		try {
			return criteria.list();
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while executing criteria " + criteria, ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}

	/**
	 * This method is used to load the proxy for the generic object.This method 
	 * never hit the database until the property (except id property) of that object() is accessed by getter.
	 * 
	 * @param classs Represents the class for which the proxy needs to be generated.
	 * @param id	 Represents the id for which proxy needs to be generated.
	 * 
	 * @return V 	 Represents the proxy object.
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public <V extends Object>  V  loadObject(final Class classs, final PK id) throws DatabaseException {
		try {
			return (V) getCrntSession().load(classs, id);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while load entity ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}

	}

	@Override
	public  <V extends Object>	V  loadMasterByPK(final Class classs,BasePrimaryKey basePrimaryKey) throws DatabaseException {
		try {
			return (V) getCrntSession().load(classs, basePrimaryKey);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while load entity ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}

	/**
	 * This method is used to load the proxy for the generic object.This method 
	 * never hit the database until the property (except id property) of that object() is accessed by getter.
	 * 
	 * @param classs Represents the class for which the proxy needs to be generated.
	 * @param id	 Represents the id for which proxy needs to be generated.
	 * 
	 * @return V 	 Represents the proxy object.
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public <V extends Object>  V  loadMasterObject(final Class classs,  Integer id) throws DatabaseException {
		try {
			return (V) getCrntSession().load(classs, id);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while load entity ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}

	}


	/**
	 * This method is used to load the generic object from the database.
	 * 
	 * @param classs  Represents the class for which the object needs to be fetched from database.
	 * @param id	  Represents the id for which the object needs to be fetched from database.
	 * 
	 * @return V	  Represents the generic object.
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public  <V extends Object>  V getObject(final Class classs, final PK id) throws DatabaseException {
		try {
			return (V) getCrntSession().get(classs, id);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while get entity ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}

	@Override
	public <V extends Object>  V  getMasterObject(final Class classs,  Integer id) throws DatabaseException {
		try {
			return (V) getCrntSession().get(classs, id);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while load entity ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}

	}
	
	@Override
	public <V extends Object>  V  getMasterObject(final Class classs,  Integer id, Boolean isToUseOpenSession) throws DatabaseException {
		try {
			if (isToUseOpenSession != null && isToUseOpenSession) {
				return (V) CustomThreadLocal.get().get(classs, id);
			}else {
				return (V) getCrntSession().get(classs, id);
			}
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while load entity ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}

	@Override
	public  <V extends Object>	V  getObjectByPK(final Class classs,final BasePrimaryKey basePrimaryKey) throws DatabaseException {
		try {
			return (V) getCrntSession().get(classs, basePrimaryKey);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while load entity ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}

	/**
	 * This method is used to get the generic object from database with all mentioned associations.
	 * 	
	 * @param entityClass		 Represents the Class for which the object needs to fetch from database.
	 * @param entityId			 Represents the id for which the obejct needs to be fetch from database.
	 * @param entityAssociations Represents the associations of the object needs to be load with the object.
	 * 
	 * @return V 				 Represents the Object fetched from database.
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public  <V extends Object> V getEntityWithAssociations(Class entityClass,PK entityId,String... entityAssociations) throws DatabaseException {

		V dataObject = null;
		final Criteria criteria = getCriteria(entityClass);
		try{
			for (final String associationName : entityAssociations) {
				criteria.createAlias(associationName, associationName, CriteriaSpecification.LEFT_JOIN);
			}
			criteria.add(Restrictions.eq("id",entityId));
			final List<V> entityList = executeCriteira(criteria);
			if (entityList.size() > 0) {
				dataObject = entityList.get(0);
			}
		}
		catch(final HibernateException e){
			logger.error("getEntityWithAssociations : failed to get entity class for entityId {}",entityId);
			throw new DatabaseException("Exception while executing getEntityWithAssociations " + criteria, ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
		return dataObject;
	}


	/**
	 * This method is used to get the generic object from database with all mentioned associations.
	 * 	
	 * @param entityClass		 Represents the Class for which the object needs to fetch from database.
	 * @param entityId			 Represents the id for which the obejct needs to be fetch from database.
	 * @param entityAssociations Represents the associations of the object needs to be load with the object.
	 * 
	 * @return V 				 Represents the Object fetched from database.
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public  <V> List<V> getEntitiesWithAssociations(Class entityClass,List<PK> entityIds,String... entityAssociations) throws DatabaseException {

		List<V> entityList = null;
		final Criteria criteria = getCriteria(entityClass);
		try{
			for (final String associationName : entityAssociations) {
				criteria.createAlias(associationName, associationName, CriteriaSpecification.LEFT_JOIN);
			}
			criteria.add(Restrictions.in("id",entityIds));
			entityList = executeCriteira(criteria);
		}
		catch(final HibernateException e){
			logger.error("getEntityWithAssociations : failed to get entity class for entityId {}",entityIds);
			throw new DatabaseException("Exception while executing getEntityWithAssociations " + criteria, ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
		return entityList;
	}


	/**
	 * This method is used to get Query object for Hibernate Query.
	 * 
	 * @param hql Represents the Hibernate Query for which the Query object needs to fetched.
	 * 
	 * @return	Represents the Query object.
	 * 
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public Query getQueryObject(final String hql) throws DatabaseException {
		try {
			Query query = getCrntSession().createQuery(hql);
			return query;
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while creating query object " + hql, ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}

	}


	/**
	 * This method is used to get Query object for SQL Query.
	 * 
	 * @param sql Represents the Hibernate Query for which the Query object needs to fetched.
	 * 
	 * @return	Represents the Query object.
	 * 
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public Query getSQLQueryObject(final String sql) throws DatabaseException {
		try {
			Query query = getCrntSession().createSQLQuery(sql);
			return query;
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while creating sql query object " + sql, ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}

	}

	/**
	 * This method is used to get Query object for named Query in hibernate.
	 * @param namedQuery
	 * 
	 * @return Represents the Query object.
	 * @throws DatabaseException throws in case of query or DB Error
	 */
	@Override
	public Query getQueryFromNamedQuery(String namedQuery,Boolean isToUseOpenSession) throws DatabaseException {

		try {
			if (isToUseOpenSession != null && isToUseOpenSession) {
				return CustomThreadLocal.get().getNamedQuery(namedQuery);
			}else {
				return getCrntSession().getNamedQuery(namedQuery);
			}
			
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while executing named HQL query " + namedQuery, ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}

	/**
	 * This method is used to get the results from the Hibernate DDL Query object.
	 * 
	 * @param query	Represents the hibernate query object for which the results needs to be fetched.
	 * 
	 * @return List Represents the result returns from the database.
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	public  <V> List<V> executeHQLSelectQuery(final Query query) throws DatabaseException {
		try {
			return query.list();
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while executing hq query  " + query, ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}

	}

	/**
	 * This method is used to execute Hibernate DML Query object.
	 * 
	 * @param query	Represents the hibernate query object for which the results needs to be fetched.
	 * 
	 * @return represents number of rows inserted/updated or deleted.
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	public  int executeHQLDMLQuery(final Query query) throws DatabaseException {
		try {
			return query.executeUpdate();
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while executing hq query  " + query, ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}

	}

	@Override
	public void delete(E entity) throws DatabaseException {

		try {
			logger.debug("delete:  Object  - " + entity);
			getCrntSession().delete(entity);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while executing delete query ",ErrorCode.BASE_DB_ERROR, ResponseInfoType.ERROR);
		}
		logger.debug("delete: Operation completed successfully for  - " + entity);
	}

	@Override
	public void deleteEntity(Object newInstance) throws DatabaseException {

		try {
			logger.debug("delete:  Object  - " + newInstance);
			getCrntSession().delete(newInstance);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while executing delete query ",ErrorCode.BASE_DB_ERROR, ResponseInfoType.ERROR);
		}
		logger.debug("delete: Operation completed successfully for  - " + newInstance);
	}

	/**
	 * This method is used to get Query object for SQL Query.
	 * 
	 * @param sql Represents the Hibernate Query for which the Query object needs to fetched.
	 * 
	 * @return	Represents the Query object.
	 * 
	 * @throws DatabaseException throws in case of query or DB Error. 
	 */
	@Override
	public NativeQuery getSQLQueryObj(final String sql) throws DatabaseException {
		try {
			NativeQuery sqlQuery = getCrntSession().createSQLQuery(sql);
			return sqlQuery;
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while creating sql query object " + sql, ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}

	}

	@Override
	public void delete(Collection<?> objects) throws DatabaseException  {
		try {
			for (Object object : objects) {
				getCrntSession().delete(object);
			}
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while delete All ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}

	@Override
	public Query getHQLQuery(String hql) throws DatabaseException {
		try {
			Query hqlQuery = getCrntSession().createQuery(hql);
			return hqlQuery;
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while creating hql query object " + hql, ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}

	@Override
	public Query getHQLQuery(String hql,Boolean isToUseOpenSession) throws DatabaseException {
		try {
			Query hqlQuery = null;
			if (isToUseOpenSession != null && isToUseOpenSession) {
				hqlQuery = CustomThreadLocal.get().createQuery(hql);
			}else {
				hqlQuery = getCrntSession().createQuery(hql);
			}
			return hqlQuery;
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while creating hql query object " + hql, ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}

	@Override
	public <V extends Object>  V  loadMasterObjectByBasePrimaryKey(final Class classs,  BasePrimaryKey id) throws DatabaseException {
		try {
			return (V) getCrntSession().load(classs, id);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while load entity ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}

	}

	/**
	 * Added To trim DTO to remove empty string from object. 
	 */
	@Override
	public void replaceObjectBlankValueWithNull(Object instance) throws ApplicationException {

		for (Field field : instance.getClass().getDeclaredFields()) {
			Object fieldType = field.getType();
			field.setAccessible(true);
			if(fieldType.equals(String.class)){
				try {
					Object value = field.get(instance);
					if(null!=value && value.equals("")){
						field.set(instance, null);
					}
					if(null!=value && !value.equals(""))
						field.set(instance, value.toString().trim());
				} catch (Exception e) {
					throw new ApplicationException("Exception While Setting Blank Values to null",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
				}  
			}
		}
		logger.debug("Setting Blank Values to Null Completed Successfully for  - " + instance);
	}

	@Override
	public void addAdvanceSearchOptionsInCriteria(SearchDTO searchDTO,Criteria criteria, Boolean isCompositKey, Map<String, String> compositKeyFieldAliasMap) throws ApplicationException {

		try {

			String groupOp=null;
			
			if(null != searchDTO.getFilters() && !searchDTO.getFilters().equals("")){

				JSONObject jsonFilter =  new JSONObject(searchDTO.getFilters() );                                       

				JSONArray rules = (JSONArray)jsonFilter.getJSONArray("rules");

				int rulesCount = rules.length();//JSONArray.getDimensions(rules)[0];

				List<Criterion> restrictionList =new ArrayList<Criterion>();

				for (int i = 0; i < rulesCount; i++) {

					JSONObject rule = rules.getJSONObject(i);

					String fld = rule.getString("field");

					String opr =rule.getString("op");

					if(isCompositKey) {
						if(compositKeyFieldAliasMap.containsKey(fld)) {
							fld  = compositKeyFieldAliasMap.get(fld); 
						}
					}
					String dataVal = null;
					if(null != rule.getString("data") && rule.getString("data").contains("_")) {
						dataVal =rule.getString("data").replaceAll("_", "\\\\_");
					}else if(null != rule.getString("data") && rule.getString("data").contains("\\")){
						dataVal =rule.getString("data").replace("\\", "\\\\");
					}else {
						dataVal =rule.getString("data");
					}
					
					String searchType=rule.getString("searchType").toLowerCase();

					groupOp= jsonFilter.getString("groupOp");

					if(null !=dataVal && !dataVal.equalsIgnoreCase("") && !dataVal.equals("__/__/____") &&  null !=fld && !fld.equalsIgnoreCase("")){

						if(opr.equals("lt")){
							if(searchType.equalsIgnoreCase("integer")){
								restrictionList.add(Restrictions.lt(fld.toString(),Integer.parseInt(dataVal)));
							}else if(searchType.equalsIgnoreCase("double")){
								restrictionList.add(Restrictions.lt(fld.toString(),Double.parseDouble(dataVal)));
							}else if(searchType.equalsIgnoreCase("date")){
								try {
									restrictionList.add(Restrictions.lt(fld.toString(),DateHelper.getDateFromString(dataVal, DateHelper.ISO_8601_DATE_FORMAT)));
								} catch (ParseException e) {
									throw new ApplicationException("Exception While Setting Date",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
								}
							}else if(searchType.equalsIgnoreCase("timeStampIgnoredDate")){
								try {
									restrictionList.add(Restrictions.lt(fld.toString(),DateHelper.getDateFromString(dataVal, DateHelper.ISO_8601_DATE_FORMAT)));
								} catch (ParseException e) {
									throw new ApplicationException("Exception While Setting Date Without Timestamp",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
								}
							}else if(searchType.equalsIgnoreCase("time")){
								try {
									SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
									SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
									Date date = parseFormat.parse(dataVal);
									restrictionList.add(Restrictions.lt(fld.toString(),new Time(displayFormat.parse(displayFormat.format(date)).getTime())));
								} catch (ParseException e) {
									throw new ApplicationException("Exception While Setting Time",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
								}
							}
						}else if(opr.equals("le")){
							if(searchType.equalsIgnoreCase("integer")){
								restrictionList.add(Restrictions.le(fld.toString(),Integer.parseInt(dataVal)));
							}else if(searchType.equalsIgnoreCase("double")){
								restrictionList.add(Restrictions.le(fld.toString(),Double.parseDouble(dataVal)));
							}else if(searchType.equalsIgnoreCase("date")){
								try {
									restrictionList.add(Restrictions.le(fld.toString(),DateHelper.getDateFromString(dataVal, DateHelper.ISO_8601_DATE_FORMAT)));
								} catch (ParseException e) {
									throw new ApplicationException("Exception While Setting Date",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
								}
							}else if(searchType.equalsIgnoreCase("timeStampIgnoredDate")){
								try {
									final SimpleDateFormat format = new SimpleDateFormat(DateHelper.ISO_8601_DATE_FORMAT);
									final Date date = format.parse(dataVal);
									final Calendar calendarFrom = Calendar.getInstance();
									calendarFrom.setTime(date);
									calendarFrom.add(Calendar.DAY_OF_YEAR, 1);
									restrictionList.add(Restrictions.lt(fld.toString(),calendarFrom.getTime()));
								} catch (ParseException e) {
									throw new ApplicationException("Exception While Setting Date Without Timestamp",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
								}
							}else if(searchType.equalsIgnoreCase("time")){
								try {
									SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
									SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
									Date date = parseFormat.parse(dataVal);
									restrictionList.add(Restrictions.le(fld.toString(),new Time(displayFormat.parse(displayFormat.format(date)).getTime())));
								} catch (ParseException e) {
									throw new ApplicationException("Exception While Setting Time",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
								}
							}
						}else if(opr.equals("gt")){
							if(searchType.equalsIgnoreCase("integer")){
								restrictionList.add(Restrictions.gt(fld.toString(),Integer.parseInt(dataVal)));
							}else if(searchType.equalsIgnoreCase("double")){
								restrictionList.add(Restrictions.gt(fld.toString(),Double.parseDouble(dataVal)));
							}else if(searchType.equalsIgnoreCase("date")){
								try {
									restrictionList.add(Restrictions.gt(fld.toString(),DateHelper.getDateFromString(dataVal, DateHelper.ISO_8601_DATE_FORMAT)));
								} catch (ParseException e) {
									throw new ApplicationException("Exception While Setting Date",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
								}
							}else if(searchType.equalsIgnoreCase("timeStampIgnoredDate")){
								try {
									final SimpleDateFormat format = new SimpleDateFormat(DateHelper.ISO_8601_DATE_FORMAT);
									final Date date = format.parse(dataVal);
									final Calendar calendarFrom = Calendar.getInstance();
									calendarFrom.setTime(date);
									calendarFrom.add(Calendar.DAY_OF_YEAR, 1);
									restrictionList.add(Restrictions.ge(fld.toString(),calendarFrom.getTime()));
								} catch (ParseException e) {
									throw new ApplicationException("Exception While Setting Date Without Timestamp",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
								}
							}else if(searchType.equalsIgnoreCase("time")){
								try {
									SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
									SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
									Date date = parseFormat.parse(dataVal);
									restrictionList.add(Restrictions.gt(fld.toString(),new Time(displayFormat.parse(displayFormat.format(date)).getTime())));
								} catch (ParseException e) {
									throw new ApplicationException("Exception While Setting Time",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
								}
							}
						}else if(opr.equals("ge")){
							if(searchType.equalsIgnoreCase("integer")){
								restrictionList.add(Restrictions.ge(fld.toString(),Integer.parseInt(dataVal)));
							}else if(searchType.equalsIgnoreCase("double")){
								restrictionList.add(Restrictions.ge(fld.toString(),Double.parseDouble(dataVal)));
							}else if(searchType.equalsIgnoreCase("date")){
								try {
									restrictionList.add(Restrictions.ge(fld.toString(),DateHelper.getDateFromString(dataVal, DateHelper.ISO_8601_DATE_FORMAT)));
								} catch (ParseException e) {
									throw new ApplicationException("Exception While Setting Date",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
								}
							}else if(searchType.equalsIgnoreCase("timeStampIgnoredDate")){
								try {
									restrictionList.add(Restrictions.ge(fld.toString(),DateHelper.getDateFromString(dataVal, DateHelper.ISO_8601_DATE_FORMAT)));
								} catch (ParseException e) {
									throw new ApplicationException("Exception While Setting Date Without Timestamp",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
								}
							}else if(searchType.equalsIgnoreCase("time")){
								try {
									SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
									SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
									Date date = parseFormat.parse(dataVal);
									restrictionList.add(Restrictions.ge(fld.toString(),new Time(displayFormat.parse(displayFormat.format(date)).getTime())));
								} catch (ParseException e) {
									throw new ApplicationException("Exception While Setting Time",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
								}
							}
						}else if(opr.equals("eq")){

							if(searchType.equalsIgnoreCase("integer")){
								restrictionList.add(Restrictions.eq(fld.toString(),Integer.parseInt(dataVal)));
							}else if(searchType.equalsIgnoreCase("double")){
								restrictionList.add(Restrictions.eq(fld.toString(),Double.parseDouble(dataVal)));
							}else if(searchType.equalsIgnoreCase("date")){
								try {
									restrictionList.add(Restrictions.eq(fld.toString(),DateHelper.getDateFromString(dataVal, DateHelper.ISO_8601_DATE_FORMAT)));
								} catch (ParseException e) {
									throw new ApplicationException("Exception While Setting Date",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
								}
							}else if(searchType.equalsIgnoreCase("timeStampIgnoredDate")){
								try {
									restrictionList.add(Restrictions.ge(fld.toString(),DateHelper.getDateFromString(dataVal, DateHelper.ISO_8601_DATE_FORMAT)));

									final SimpleDateFormat format = new SimpleDateFormat(DateHelper.ISO_8601_DATE_FORMAT);
									final Date date = format.parse(dataVal);
									final Calendar calendarTo = Calendar.getInstance();
									calendarTo.setTime(date);
									calendarTo.add(Calendar.DAY_OF_YEAR, 1);
									restrictionList.add(Restrictions.lt(fld.toString(),calendarTo.getTime()));

								} catch (ParseException e) {
									throw new ApplicationException("Exception While Setting Date Without Timestamp",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
								}
							}else if(searchType.equalsIgnoreCase("time")){
								try {
									SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
									SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
									Date date = parseFormat.parse(dataVal);
									restrictionList.add(Restrictions.eq(fld.toString(),new Time(displayFormat.parse(displayFormat.format(date)).getTime())));
								} catch (ParseException e) {
									throw new ApplicationException("Exception While Setting Time",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
								}
							}else{
								restrictionList.add(Restrictions.eq(fld.toString(),dataVal).ignoreCase());
							}
						}else if(opr.equals("ne")){
							if(searchType.equalsIgnoreCase("integer")){
								restrictionList.add(Restrictions.ne(fld.toString(),Integer.parseInt(dataVal)));
							}else if(searchType.equalsIgnoreCase("double")){
								restrictionList.add(Restrictions.ne(fld.toString(),Double.parseDouble(dataVal)));
							}else if(searchType.equalsIgnoreCase("date")){
								try {
									restrictionList.add(Restrictions.ne(fld.toString(),DateHelper.getDateFromString(dataVal, DateHelper.ISO_8601_DATE_FORMAT)));
								} catch (ParseException e) {
									throw new ApplicationException("Exception While Setting Date",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
								}
							}else if(searchType.equalsIgnoreCase("time")){
								try {
									SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
									SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
									Date date = parseFormat.parse(dataVal);
									restrictionList.add(Restrictions.ne(fld.toString(),new Time(displayFormat.parse(displayFormat.format(date)).getTime())));
								} catch (ParseException e) {
									throw new ApplicationException("Exception While Setting Time",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
								}
							}else{
								restrictionList.add(Restrictions.ne(fld.toString(),dataVal).ignoreCase());
							}
						}else if(opr.equals("bw")){
							if(searchType.equalsIgnoreCase("string")){
								restrictionList.add(Restrictions.ilike(fld.toString(), dataVal, MatchMode.START));
							}
						}else if(opr.equals("bn")){
							if(searchType.equalsIgnoreCase("string")){
								restrictionList.add(Restrictions.not(Restrictions.ilike(fld, dataVal, MatchMode.START)));
							}
						}else if(opr.equals("ew")){
							if(searchType.equalsIgnoreCase("string")){
								restrictionList.add(Restrictions.ilike(fld.toString(), dataVal, MatchMode.END));
							}
						}else if(opr.equals("en")){
							if(searchType.equalsIgnoreCase("string")){
								restrictionList.add(Restrictions.not(Restrictions.ilike(fld.toString(), dataVal, MatchMode.END)));
							}
						}else if(opr.equals("cn")){
							if(searchType.equalsIgnoreCase("string")){
								restrictionList.add(Restrictions.ilike(fld.toString(), dataVal, MatchMode.ANYWHERE));
							}
						}else if(opr.equals("nc")){
							if(searchType.equalsIgnoreCase("string")){
								restrictionList.add(Restrictions.not(Restrictions.ilike(fld.toString(), dataVal, MatchMode.ANYWHERE)));
							}

						}else if(opr.equals("in")){
							if(searchType.equalsIgnoreCase("string")){
								restrictionList.add(Restrictions.in(fld.toString(),dataVal.split(",")));
							}

						}else if(opr.equals("ni")){
							if(searchType.equalsIgnoreCase("string")){
								restrictionList.add(Restrictions.not(Restrictions.in(fld.toString(), dataVal.split(","))));
							}
						}

					}

				}

				Disjunction disjunction = Restrictions.disjunction();

				for (Criterion criterion : restrictionList){
					if(groupOp.equalsIgnoreCase("and")){
						criteria.add(criterion);
					}else{
						disjunction.add(criterion);
					}

				}

				if(null !=groupOp && groupOp.equalsIgnoreCase("or")){
					criteria.add(disjunction);
				}

			}
		}catch (NumberFormatException | JSONException e) {
			throw new ApplicationException(null, ErrorCode.PARSE_EXCEPTION, ResponseInfoType.ERROR);
		}
		logger.debug("adding advance search options Successfully in criteria");
	}

	
	// Changes Done For Replacing Positional Parameters With Named Parameters
	@Override
	public Query addAdvanceSearchOptionsInHql(SearchDTO searchDTO,String hqlString,PaginationDTO pagination,Integer count, Boolean isCompositKey, Map<String, String> compositKeyFieldAliasMap) throws ApplicationException {

		Query  query =null;
		Map<String , Object > parameters = new HashMap<String, Object>();
		try {

			String groupOperation=null;

			if(null != searchDTO.getFilters() && !searchDTO.getFilters().equals("")){

				JSONObject jsonFilter =  new JSONObject(searchDTO.getFilters() );                                       

				JSONArray rules = (JSONArray)jsonFilter.getJSONArray("rules");

				groupOperation= jsonFilter.getString("groupOp");

				int rulesCount = rules.length();//JSONArray.getDimensions(rules)[0];

				String groupOp="";

				String hqlSubString="";

				for (int i = 0; i < rulesCount; i++) {

					JSONObject rule = rules.getJSONObject(i);

					String fld = rule.getString("field");

					String opr =rule.getString("op");

					String dataVal =rule.getString("data");

					String searchType=rule.getString("searchType").toLowerCase();

					if(isCompositKey) {
						if(compositKeyFieldAliasMap.containsKey(fld)) {
							fld  = compositKeyFieldAliasMap.get(fld); 
						}
					}
					if(null !=dataVal && !dataVal.equalsIgnoreCase("") && !dataVal.equals("__/__/____") &&  null !=fld && !fld.equalsIgnoreCase("")){

						if(opr.equals("lt")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " <  :" + fld.toString();
							parameters.put(fld.toString(), dataVal);
							groupOp=groupOperation;
						}else if(opr.equals("le")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " <=  :" + fld.toString();
							parameters.put(fld.toString(), dataVal);
							groupOp=groupOperation;
						}else if(opr.equals("gt")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " >  :" + fld.toString();
							parameters.put(fld.toString(), dataVal);
							groupOp=groupOperation;
						}else if(opr.equals("ge")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " >=  :" + fld.toString();
							parameters.put(fld.toString(), dataVal);
							groupOp=groupOperation;
						}else if(opr.equals("eq")){
							if(searchType.equalsIgnoreCase("integer")){
								hqlSubString += " "+groupOp+" "+ fld.toString() + " =  :" + fld.toString();
								parameters.put(fld.toString(), dataVal);
								groupOp=groupOperation;
							}else if(searchType.equalsIgnoreCase("double") || searchType.equalsIgnoreCase("date")){
								hqlSubString += " "+groupOp+" "+ fld.toString() + " =  :" + fld.toString();
								//parameters.put(fld.toString(), dataVal);
								
								try {
									parameters.put(fld.toString(), DateHelper.getDateFromString("24-05-2021", DateHelper.ISO_8601_DATE_FORMAT));
								} catch (ParseException e) {
									throw new ApplicationException("Exception While Setting Date",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
								}
								
								groupOp=groupOperation;
							}else if(searchType.equalsIgnoreCase("TimeStampIgnoredDate")){
								parameters.put(fld.toString(), dataVal);
								hqlSubString += " "+groupOp+" "+ fld.toString() + " <  :" + fld.toString();
								final SimpleDateFormat format = new SimpleDateFormat(DateHelper.ISO_8601_DATE_FORMAT);
								final Date date = format.parse(dataVal);
								final Calendar calendarTo = Calendar.getInstance();
								calendarTo.setTime(date);
								calendarTo.add(Calendar.DAY_OF_YEAR, 1);
								parameters.put(fld.toString()+"lessThan", calendarTo.getTime());
								/**
								try {
									hqlSubString += " "+groupOp+" "+ fld.toString() + " >=  :" + fld.toString();
									parameters.put(fld.toString()+"greaterThan", DateHelper.getDateFromString(dataVal, DateHelper.ISO_8601_DATE_FORMAT));
									
								}catch (ParseException e) {
									throw new ApplicationException("Exception While Setting Timestamp Ignored Date",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
								}
								*/
										
								groupOp=groupOperation;
							}else if(searchType.equalsIgnoreCase("string")){
								hqlSubString += " "+groupOp+ " lower("+ fld.toString() + ") = "+  "lower(:"+fld.toString()+")";
								parameters.put(fld.toString(), dataVal);
								groupOp=groupOperation;
							}
						}else if(opr.equals("ne")){

							if(searchType.equalsIgnoreCase("integer")){
								hqlSubString += " "+groupOp+" "+ fld.toString() + " !=  :" + fld.toString();
								parameters.put(fld.toString(), dataVal);
								groupOp=groupOperation;
							}if(searchType.equalsIgnoreCase("double") || searchType.equalsIgnoreCase("date")){
								hqlSubString += " "+groupOp+" "+ fld.toString() + " !=  :" +fld.toString()  ;
								parameters.put(fld.toString(), dataVal);
								groupOp=groupOperation;
							}else if(searchType.equalsIgnoreCase("string")){
								hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") != "+  "lower(:"+fld.toString()+")";
								parameters.put(fld.toString(), dataVal);
								groupOp=groupOperation;
							}
						}else if(opr.equals("bw")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") = "+  "like lower(:"+fld.toString()+")";
							parameters.put(fld.toString(), dataVal);
							groupOp=groupOperation;
						}else if(opr.equals("bn")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") = "+  "not like lower(:"+fld.toString()+")";
							parameters.put(fld.toString(), dataVal);
							groupOp=groupOperation;
						}else if(opr.equals("ew")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") = "+  "like lower(:"+fld.toString()+")";
							parameters.put(fld.toString(), dataVal);
							groupOp=groupOperation;
						}else if(opr.equals("en")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") = "+  "not like lower(:"+fld.toString()+")";
							parameters.put(fld.toString(), dataVal);
							groupOp=groupOperation;
						}else if(opr.equals("cn")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") = "+  "like lower(:"+fld.toString()+")";
							parameters.put(fld.toString(), dataVal);
							groupOp=groupOperation;
						}else if(opr.equals("nc")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") not like  lower(?)";
							parameters.put(fld.toString(), dataVal);
							groupOp=groupOperation;

						}else if(opr.equals("in")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " in (:"+ fld.toString()+ ")";
							parameters.put(fld.toString(), dataVal);
							groupOp=groupOperation;

						}else if(opr.equals("ni")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " not  in (:"+ fld.toString()+ ")";
							parameters.put(fld.toString(), dataVal);
							groupOp=groupOperation;
						}

					}

				}

				if((hqlSubString.trim()).length()>0){
					hqlString += "AND ( "+hqlSubString+")";
				}

				if(null !=pagination && null != pagination.getOrderByField()  && null != pagination.getOrderBy()){
					hqlString += " ORDER BY" + ApplicationConstants.EmptyString +pagination.getOrderByField() + ApplicationConstants.EmptyString +  pagination.getOrderBy();
				}
				
				query=getHQLQuery(hqlString);

				for (int i = 0; i < rulesCount; i++) {

					JSONObject rule = rules.getJSONObject(i);

					String fld = rule.getString("field");

					String opr =rule.getString("op");

					String dataVal =rule.getString("data");

					String searchType=rule.getString("searchType").toLowerCase();

					if(isCompositKey) {
						if(compositKeyFieldAliasMap.containsKey(fld)) {
							fld  = compositKeyFieldAliasMap.get(fld); 
						}
					}
					if(null !=dataVal && !dataVal.equalsIgnoreCase("") &&  null !=fld && !fld.equalsIgnoreCase("")){
				
						for (Map.Entry<String, Object> entry : parameters.entrySet()) {
							query.setParameter(entry.getKey(), entry.getValue());
						}
				/**

						if(searchType.equalsIgnoreCase("integer")){
							query.setParameter(++count, Integer.parseInt(dataVal));
						}else if(searchType.equalsIgnoreCase("double")){
							query.setParameter(++count, Double.parseDouble((dataVal)));
						}else if(searchType.equalsIgnoreCase("date")){
							try {
								query.setParameter(++count,DateHelper.getDateFromString(dataVal, DateHelper.ISO_8601_DATE_FORMAT));
							}catch (ParseException e) {
								throw new ApplicationException("Exception While Setting Date",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
							}
						}else if(searchType.equalsIgnoreCase("TimeStampIgnoredDate")){
							try {
								query.setParameter(++count,DateHelper.getDateFromString(dataVal, DateHelper.ISO_8601_DATE_FORMAT));
								
								final SimpleDateFormat format = new SimpleDateFormat(DateHelper.ISO_8601_DATE_FORMAT);
								final Date date = format.parse(dataVal);
								final Calendar calendarTo = Calendar.getInstance();
								calendarTo.setTime(date);
								calendarTo.add(Calendar.DAY_OF_YEAR, 1);
								
								query.setParameter(++count,calendarTo.getTime());
							}catch (ParseException e) {
								throw new ApplicationException("Exception While Setting Timestamp Ignored Date",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
							}
						}else if(searchType.equalsIgnoreCase("string")){
							if(opr.equals("eq") || opr.equals("ne")){
								query.setParameter(++count, dataVal);
							}else if(opr.equals("bw") || opr.equals("bn")){
								query.setParameter(++count, dataVal+"%");
							}else if(opr.equals("ew") || opr.equals("en")){
								query.setParameter(++count, "%"+dataVal);
							}else if(opr.equals("cn") || opr.equals("nc")){
								query.setParameter(++count, "%"+dataVal+"%");
							}
						} 
						
				*/		
					}

				
				}

			}else{
				if(null !=pagination && null != pagination.getOrderByField()  && null != pagination.getOrderBy()){
					hqlString += " ORDER BY" + ApplicationConstants.EmptyString +pagination.getOrderByField() + ApplicationConstants.EmptyString +  pagination.getOrderBy();
				}
				query=getHQLQuery(hqlString);
			}

		}catch (NumberFormatException e) {
			throw new ApplicationException("Number Format Exception",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
		} catch (JSONException e) {
			throw new ApplicationException("Json Format Exception",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
		} catch (ParseException e) {
			throw new ApplicationException("Exception While Setting Timestamp Ignored Date",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
		}

		logger.debug("adding advance search options Successfully in hql");

		if(pagination!=null){
			query.setFirstResult(pagination.getStartIndex());
			query.setMaxResults(pagination.getRecordsPerPage());
		}

		return query;
	}

	@Override
	public Query addAdvanceSearchOptionsInHqlCollection(SearchDTO searchDTO,String hqlString,PaginationDTO pagination, Boolean isCompositKey, Map<String, String> compositKeyFieldAliasMap) throws ApplicationException {

		Query query = getAdvanceSearchOptionsInHqlQuery(searchDTO, hqlString, pagination, false, isCompositKey, compositKeyFieldAliasMap);
		return query;
	}

	@Override
	public Query addAdvanceSearchOptionsInHqlCollectionWithOpenSession(SearchDTO searchDTO,String hqlString,PaginationDTO pagination, Boolean isCompositKey, Map<String, String> compositKeyFieldAliasMap) throws ApplicationException {

		Query query = getAdvanceSearchOptionsInHqlQuery(searchDTO, hqlString, pagination, true, isCompositKey, compositKeyFieldAliasMap);
		return query;
	}

	private Query getAdvanceSearchOptionsInHqlQuery(SearchDTO searchDTO, String hqlString, PaginationDTO pagination, Boolean isToUseOpenSession, Boolean isCompositKey, Map<String, String> compositKeyFieldAliasMap) {

		Query  query =null;
		try {

			String groupOperation=null;

			if(null != searchDTO.getFilters() && !searchDTO.getFilters().equals("")){

				JSONObject jsonFilter =  new JSONObject(searchDTO.getFilters() );                                       

				JSONArray rules = (JSONArray)jsonFilter.getJSONArray("rules");

				groupOperation= jsonFilter.getString("groupOp");

				int rulesCount = rules.length();//JSONArray.getDimensions(rules)[0];

				String groupOp="";
				String hqlSubString="";
				String namedParam = null;

				ArrayList<String> duplicateFldChk= new ArrayList() ;

				for (int i = 0; i < rulesCount; i++) {

					JSONObject rule = rules.getJSONObject(i);

					String fld = rule.getString("field");

					String opr =rule.getString("op");

					String dataVal =rule.getString("data");

					String searchType=rule.getString("searchType").toLowerCase();
					if(isCompositKey) {
						if(compositKeyFieldAliasMap.containsKey(fld)) {
							fld  = compositKeyFieldAliasMap.get(fld); 
						}
					}
					if(null !=dataVal && !dataVal.equalsIgnoreCase("") &&  !dataVal.equals("__/__/____")  &&  null !=fld && !fld.equalsIgnoreCase("")){
						if(fld.contains(".")){ 
							namedParam = fld.substring(fld.lastIndexOf(".") + 1);
							if(duplicateFldChk != null && duplicateFldChk.size() > 0){
								for (int x = 0;  x< duplicateFldChk.size(); x++){
									if(namedParam != null && namedParam.equalsIgnoreCase(duplicateFldChk.get(x))){
										namedParam = fld.substring(fld.lastIndexOf(".") + 1)+"_"+i;
									}
								}
							}
						}else{
							namedParam = fld;
						}

						duplicateFldChk.add(namedParam);
						if(opr.equals("lt")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " <  :"+namedParam.toString();
							groupOp=groupOperation;
						}else if(opr.equals("le")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " <=  :"+namedParam.toString();
							groupOp=groupOperation;
						}else if(opr.equals("gt")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " >  :"+namedParam.toString();
							groupOp=groupOperation;
						}else if(opr.equals("ge")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " >=  :"+namedParam.toString();
							groupOp=groupOperation;
						}else if(opr.equals("eq")){
							if(searchType.equalsIgnoreCase("integer") || searchType.equalsIgnoreCase("double")){
								hqlSubString += " "+groupOp+" "+ fld.toString() + " =  :"+namedParam.toString();
								groupOp=groupOperation;
							}else if(searchType.equalsIgnoreCase("date")){
								hqlSubString += " "+groupOp+" cast("+ fld.toString() + " as date) =  :"+namedParam.toString();
								groupOp=groupOperation;
							}else if(searchType.equalsIgnoreCase("TimeStampIgnoredDate")){
								
								hqlSubString += " "+groupOp+" cast("+ fld.toString() + " as date) >= :"+namedParam.toString()+"_1";
								hqlSubString += " "+groupOp+" cast("+ fld.toString() + " as date) < :"+namedParam.toString()+"_2";
								groupOp=groupOperation;
							}else if(searchType.equalsIgnoreCase("string")){
								hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") =  lower(:"+namedParam.toString()+") ";
								groupOp=groupOperation;
							}
						}else if(opr.equals("ne")){

							if(searchType.equalsIgnoreCase("integer")){
								hqlSubString += " "+groupOp+" "+ fld.toString() + " !=  :"+namedParam.toString();
								groupOp=groupOperation;
							}if(searchType.equalsIgnoreCase("double") || searchType.equalsIgnoreCase("date")){
								hqlSubString += " "+groupOp+" "+ fld.toString() + " !=  :"+namedParam.toString();
								groupOp=groupOperation;
							}else if(searchType.equalsIgnoreCase("string")){
								hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") !=  lower(:"+namedParam.toString()+") ";
								groupOp=groupOperation;
							}
						}else if(opr.equals("bw")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("bn")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") not like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("ew")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ")  like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("en")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") not like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("cn")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("nc")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") not like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;

						}else if(opr.equals("in")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " in  (:"+namedParam.toString()+") ";
							groupOp=groupOperation;

						}else if(opr.equals("ni")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " not  in  (:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}
					}
				}

				if((hqlSubString.trim()).length()>0){
					if(hqlString.toLowerCase().indexOf("where") > 0 && hqlString.toLowerCase().indexOf("group by") > 0){
						String[] arrOfStr = hqlString.split("GROUP BY", 2);
						if(arrOfStr.length == 1) {
							arrOfStr = hqlString.split("group by", 2);
						}
						if(arrOfStr.length > 1) {
							String hqlQry = arrOfStr[0];
							hqlQry += " AND ("+hqlSubString+")";
							hqlQry += " GROUP BY "+arrOfStr[1];
							hqlString = hqlQry;
						}
					}else if(hqlString.toLowerCase().indexOf("where") > 0){
						hqlString += " AND ("+hqlSubString+")";
					}else {
						hqlString += " WHERE  ("+hqlSubString+")";
					}
				}

				if(null !=pagination && null != pagination.getOrderByField()  && null != pagination.getOrderBy()){
					hqlString += " ORDER BY" + ApplicationConstants.EmptyString +pagination.getOrderByField() + ApplicationConstants.EmptyString +  pagination.getOrderBy();
				}
				query=getHQLQuery(hqlString,isToUseOpenSession);
				String namedVal = null;
				ArrayList<String> duplicateChk= new ArrayList() ;
				for (int i = 0; i < rulesCount; i++) {

					JSONObject rule = rules.getJSONObject(i);

					String fld = rule.getString("field");

					String opr =rule.getString("op");

					String dataVal =rule.getString("data");

					String searchType=rule.getString("searchType").toLowerCase();

					if(isCompositKey) {
						if(compositKeyFieldAliasMap.containsKey(fld)) {
							fld  = compositKeyFieldAliasMap.get(fld); 
						}
					}
					if(null !=dataVal && !dataVal.equalsIgnoreCase("") && !dataVal.equals("__/__/____") &&  null !=fld && !fld.equalsIgnoreCase("")){
						if(fld.contains(".")){ 

							namedVal = fld.substring(fld.lastIndexOf(".") + 1);
							if(duplicateChk != null && duplicateChk.size() > 0){
								for (int x = 0;  x< duplicateChk.size(); x++){
									if(namedVal != null && namedVal.equalsIgnoreCase(duplicateChk.get(x))){
										namedVal = fld.substring(fld.lastIndexOf(".") + 1)+"_"+i;
									}
								}
							}
						}else{
							namedVal =fld;
						}

						duplicateChk.add(namedVal);
						if(searchType.equalsIgnoreCase("integer")){
							query.setParameter(namedVal, Integer.parseInt(dataVal));
						}else if(searchType.equalsIgnoreCase("double")){
							query.setParameter(namedVal, Double.parseDouble((dataVal)));
						}else if(searchType.equalsIgnoreCase("date")){
							try {
								query.setParameter(namedVal,DateHelper.getDateFromString(dataVal, DateHelper.ISO_8601_DATE_FORMAT));
							}catch (ParseException e) {
								throw new ApplicationException("Exception While Setting Date",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
							}
						}else if(searchType.equalsIgnoreCase("TimeStampIgnoredDate")){
							try {
								query.setParameter(namedVal+"_1",DateHelper.getDateFromString(dataVal, DateHelper.ISO_8601_DATE_FORMAT));
								
								final SimpleDateFormat format = new SimpleDateFormat(DateHelper.ISO_8601_DATE_FORMAT);
								final Date date = format.parse(dataVal);
								final Calendar calendarTo = Calendar.getInstance();
								calendarTo.setTime(date);
								calendarTo.add(Calendar.DAY_OF_YEAR, 1);
								query.setParameter(namedVal+"_2",calendarTo.getTime());
							}catch (ParseException e) {
								throw new ApplicationException("Exception While Setting Timestamp Ignored Date",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
							}
						}else if(searchType.equalsIgnoreCase("string")){
							if(opr.equals("eq") || opr.equals("ne")){
								query.setParameter(namedVal, dataVal);
							}else if(opr.equals("bw") || opr.equals("bn")){
								query.setParameter(namedVal, dataVal+"%");
							}else if(opr.equals("ew") || opr.equals("en")){
								query.setParameter(namedVal, "%"+dataVal);
							}else if(opr.equals("cn") || opr.equals("nc")){
								query.setParameter(namedVal, "%"+dataVal+"%");
							}
						} 
					}
				}
			}else{
				if(null !=pagination && null != pagination.getOrderByField()  && null != pagination.getOrderBy()){
					hqlString += " ORDER BY" + ApplicationConstants.EmptyString +pagination.getOrderByField() + ApplicationConstants.EmptyString +  pagination.getOrderBy();
				}
				query=getHQLQuery(hqlString,isToUseOpenSession);
			}

			/*if(null !=pagination){
				query.setParameter(++count, pagination.getOrderByField());
			}*/

		}catch (NumberFormatException e) {
			throw new ApplicationException("Number Format Exception",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
		} catch (JSONException e) {
			throw new ApplicationException("Json Format Exception",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
		}

		logger.debug("adding advance search options Successfully in hql");

		if(pagination!=null){
			query.setFirstResult(pagination.getStartIndex());
			query.setMaxResults(pagination.getRecordsPerPage());
		}
		return query;
	}

	/* 
	 *  Add search filters with GROUP BY - Used in FAS Screens.
	 * 
	 * (non-Javadoc)
	 * @see com.epps.domain.generic.dao.IGenericDAO#addAdvanceSearchOptionsInHqlCollectionWithGroupBy(com.epps.domain.common.dto.GridSearchDTO, java.lang.String, java.lang.String, com.epps.domain.common.dto.PaginationDTO)
	 *
	 */
	@Override
	public Query addAdvanceSearchOptionsInHqlCollectionWithGroupBy(SearchDTO searchDTO, String hqlString,String groupByString,PaginationDTO pagination, Boolean isCompositKey, Map<String, String> compositKeyFieldAliasMap) throws ApplicationException {
		Query  query =null;
		try {
			String groupOperation=null;
			if(null != searchDTO.getFilters() && !searchDTO.getFilters().equals("")){
				JSONObject jsonFilter =  new JSONObject(searchDTO.getFilters() );                                       
				JSONArray rules = (JSONArray)jsonFilter.getJSONArray("rules");
				groupOperation= jsonFilter.getString("groupOp");
				int rulesCount = rules.length();//JSONArray.getDimensions(rules)[0];
				String groupOp="";
				String hqlSubString="";
				String namedParam = null;
				ArrayList<String> duplicateFldChk= new ArrayList();
				for (int i = 0; i < rulesCount; i++) {
					JSONObject rule = rules.getJSONObject(i);
					String fld = rule.getString("field");
					String opr =rule.getString("op");
					String dataVal =rule.getString("data");
					String searchType=rule.getString("searchType").toLowerCase();
					if(isCompositKey) {
						if(compositKeyFieldAliasMap.containsKey(fld)) {
							fld  = compositKeyFieldAliasMap.get(fld); 
						}
					}
					if(null !=dataVal && !dataVal.equalsIgnoreCase("") &&  !dataVal.equals("__/__/____")  &&  null !=fld && !fld.equalsIgnoreCase("")){
						if(fld.contains(".")){ 
							namedParam = fld.substring(fld.lastIndexOf(".") + 1);
							if(duplicateFldChk != null && duplicateFldChk.size() > 0){
								for (int x = 0;  x< duplicateFldChk.size(); x++){
									if(namedParam != null && namedParam.equalsIgnoreCase(duplicateFldChk.get(x))){
										namedParam = fld.substring(fld.lastIndexOf(".") + 1)+"_"+i;
									}
								}
							}
						}else{
							namedParam = fld;
						}
						duplicateFldChk.add(namedParam);
						if(opr.equals("lt")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " <  :"+namedParam.toString();
							groupOp=groupOperation;
						}else if(opr.equals("le")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " <=  :"+namedParam.toString();
							groupOp=groupOperation;
						}else if(opr.equals("gt")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " >  :"+namedParam.toString();
							groupOp=groupOperation;
						}else if(opr.equals("ge")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " >=  :"+namedParam.toString();
							groupOp=groupOperation;
						}else if(opr.equals("eq")){
							if(searchType.equalsIgnoreCase("integer") || searchType.equalsIgnoreCase("double")){
								hqlSubString += " "+groupOp+" "+ fld.toString() + " =  :"+namedParam.toString();
								groupOp=groupOperation;
							}else if(searchType.equalsIgnoreCase("date")){
								hqlSubString += " "+groupOp+" cast("+ fld.toString() + " as date) =  :"+namedParam.toString();
								groupOp=groupOperation;
							}else if(searchType.equalsIgnoreCase("TimeStampIgnoredDate")){
								hqlSubString += " "+groupOp+" cast("+ fld.toString() + " as date) >= :"+namedParam.toString()+"_1";
								hqlSubString += " "+groupOp+" cast("+ fld.toString() + " as date) <  :"+namedParam.toString()+"_2";
								groupOp=groupOperation;
							}else if(searchType.equalsIgnoreCase("string")){
								hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") =  lower(:"+namedParam.toString()+") ";
								groupOp=groupOperation;
							}
						}else if(opr.equals("ne")){

							if(searchType.equalsIgnoreCase("integer")){
								hqlSubString += " "+groupOp+" "+ fld.toString() + " !=  :"+namedParam.toString();
								groupOp=groupOperation;
							}if(searchType.equalsIgnoreCase("double") || searchType.equalsIgnoreCase("date")){
								hqlSubString += " "+groupOp+" "+ fld.toString() + " !=  :"+namedParam.toString();
								groupOp=groupOperation;
							}else if(searchType.equalsIgnoreCase("string")){
								hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") !=  lower(:"+namedParam.toString()+") ";
								groupOp=groupOperation;
							}
						}else if(opr.equals("bw")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("bn")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") not like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("ew")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ")  like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("en")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") not like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("cn")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("nc")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") not like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("in")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " in  (:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("ni")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " not  in  (:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}
					}
				}
				if((hqlSubString.trim()).length()>0){
					hqlString += "AND ( "+hqlSubString+") ";
				}
				hqlString+=groupByString;
				if(null !=pagination && null != pagination.getOrderByField()  && null != pagination.getOrderBy()){
					hqlString += " ORDER BY" + ApplicationConstants.EmptyString +pagination.getOrderByField() + ApplicationConstants.EmptyString +  pagination.getOrderBy();
				}
				query=getHQLQuery(hqlString);
				String namedVal = null;
				ArrayList<String> duplicateChk= new ArrayList() ;
				for (int i = 0; i < rulesCount; i++) {
					JSONObject rule = rules.getJSONObject(i);
					String fld = rule.getString("field");
					String opr =rule.getString("op");
					String dataVal =rule.getString("data");
					String searchType=rule.getString("searchType").toLowerCase();
					if(isCompositKey) {
						if(compositKeyFieldAliasMap.containsKey(fld)) {
							fld  = compositKeyFieldAliasMap.get(fld); 
						}
					}
					if(null !=dataVal && !dataVal.equalsIgnoreCase("") && !dataVal.equals("__/__/____") &&  null !=fld && !fld.equalsIgnoreCase("")){
						if(fld.contains(".")){ 
							namedVal = fld.substring(fld.lastIndexOf(".") + 1);
							if(duplicateChk != null && duplicateChk.size() > 0){
								for (int x = 0;  x< duplicateChk.size(); x++){
									if(namedVal != null && namedVal.equalsIgnoreCase(duplicateChk.get(x))){
										namedVal = fld.substring(fld.lastIndexOf(".") + 1)+"_"+i;
									}
								}
							}
						}else{
							namedVal =fld;
						}
						duplicateChk.add(namedVal);
						if(searchType.equalsIgnoreCase("integer")){
							query.setParameter(namedVal, Integer.parseInt(dataVal));
						}else if(searchType.equalsIgnoreCase("double")){
							query.setParameter(namedVal, Double.parseDouble((dataVal)));
						}else if(searchType.equalsIgnoreCase("date")){
							try {
								query.setParameter(namedVal,DateHelper.getDateFromString(dataVal, DateHelper.ISO_8601_DATE_FORMAT));
							}catch (ParseException e) {
								throw new ApplicationException("Exception While Setting Date",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
							}
						}else if(searchType.equalsIgnoreCase("TimeStampIgnoredDate")){
							try {
								query.setParameter(namedVal+"_1",DateHelper.getDateFromString(dataVal, DateHelper.ISO_8601_DATE_FORMAT));
								
								final SimpleDateFormat format = new SimpleDateFormat(DateHelper.ISO_8601_DATE_FORMAT);
								final Date date = format.parse(dataVal);
								final Calendar calendarTo = Calendar.getInstance();
								calendarTo.setTime(date);
								calendarTo.add(Calendar.DAY_OF_YEAR, 1);
								query.setParameter(namedVal+"_2",calendarTo.getTime());
							}catch (ParseException e) {
								throw new ApplicationException("Exception While Setting Timestamp Ignored Date",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
							}
						}else if(searchType.equalsIgnoreCase("string")){
							if(opr.equals("eq") || opr.equals("ne")){
								query.setParameter(namedVal, dataVal);
							}else if(opr.equals("bw") || opr.equals("bn")){
								query.setParameter(namedVal, dataVal+"%");
							}else if(opr.equals("ew") || opr.equals("en")){
								query.setParameter(namedVal, "%"+dataVal);
							}else if(opr.equals("cn") || opr.equals("nc")){
								query.setParameter(namedVal, "%"+dataVal+"%");
							}
						} 
					}
				}
			}else{
				hqlString+=groupByString;
				if(null !=pagination && null != pagination.getOrderByField()  && null != pagination.getOrderBy()){
					hqlString += " ORDER BY" + ApplicationConstants.EmptyString +pagination.getOrderByField() + ApplicationConstants.EmptyString +  pagination.getOrderBy();
				}
				query=getHQLQuery(hqlString);
			}
		}catch (NumberFormatException e) {
			throw new ApplicationException("Number Format Exception",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
		} catch (JSONException e) {
			throw new ApplicationException("Json Format Exception",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
		}

		if(pagination!=null){
			query.setFirstResult(pagination.getStartIndex());
			query.setMaxResults(pagination.getRecordsPerPage());
		}
		return query;
	}
	
	/**
	 * @see com.epps.domain.generic.dao.IGenericDAO#addAdvanceSearchOptionsInHqlCollectionWithGroupBy(com.epps.framework.interfaces.responses.dtos.SearchDTO.common.dto.GridSearchDTO, java.lang.String, java.lang.String, com.epps.framework.interfaces.responses.dtos.domain.common.dto.PaginationDTO, java.lang.Boolean, Boolean, Map)
	 */
	@Override
	public Query addAdvanceSearchOptionsInHqlCollectionWithGroupBy(SearchDTO searchDTO, String hqlString,
			String groupByString,PaginationDTO pagination,Boolean isToUseOpenSession, Boolean isCompositKey, Map<String, String> compositKeyFieldAliasMap) throws ApplicationException {
		Query  query =null;
		try {
			String groupOperation=null;
			if(null != searchDTO.getFilters() && !searchDTO.getFilters().equals("")){
				JSONObject jsonFilter =  new JSONObject(searchDTO.getFilters() );                                       
				JSONArray rules = (JSONArray)jsonFilter.getJSONArray("rules");
				groupOperation= jsonFilter.getString("groupOp");
				int rulesCount = rules.length();//JSONArray.getDimensions(rules)[0];
				String groupOp="";
				String hqlSubString="";
				String namedParam = null;
				ArrayList<String> duplicateFldChk= new ArrayList();
				for (int i = 0; i < rulesCount; i++) {
					JSONObject rule = rules.getJSONObject(i);
					String fld = rule.getString("field");
					String opr =rule.getString("op");
					String dataVal =rule.getString("data");
					String searchType=rule.getString("searchType").toLowerCase();
					if(isCompositKey) {
						if(compositKeyFieldAliasMap.containsKey(fld)) {
							fld  = compositKeyFieldAliasMap.get(fld); 
						}
					}
					if(null !=dataVal && !dataVal.equalsIgnoreCase("") &&  !dataVal.equals("__/__/____")  &&  null !=fld && !fld.equalsIgnoreCase("")){
						if(fld.contains(".")){ 
							namedParam = fld.substring(fld.lastIndexOf(".") + 1);
							if(duplicateFldChk != null && duplicateFldChk.size() > 0){
								for (int x = 0;  x< duplicateFldChk.size(); x++){
									if(namedParam != null && namedParam.equalsIgnoreCase(duplicateFldChk.get(x))){
										namedParam = fld.substring(fld.lastIndexOf(".") + 1)+"_"+i;
									}
								}
							}
						}else{
							namedParam = fld;
						}
						duplicateFldChk.add(namedParam);
						if(opr.equals("lt")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " <  :"+namedParam.toString();
							groupOp=groupOperation;
						}else if(opr.equals("le")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " <=  :"+namedParam.toString();
							groupOp=groupOperation;
						}else if(opr.equals("gt")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " >  :"+namedParam.toString();
							groupOp=groupOperation;
						}else if(opr.equals("ge")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " >=  :"+namedParam.toString();
							groupOp=groupOperation;
						}else if(opr.equals("eq")){
							if(searchType.equalsIgnoreCase("integer") || searchType.equalsIgnoreCase("double")){
								hqlSubString += " "+groupOp+" "+ fld.toString() + " =  :"+namedParam.toString();
								groupOp=groupOperation;
							}else if(searchType.equalsIgnoreCase("date")){
								hqlSubString += " "+groupOp+" cast("+ fld.toString() + " as date) =  :"+namedParam.toString();
								groupOp=groupOperation;
							}else if(searchType.equalsIgnoreCase("TimeStampIgnoredDate")){
								hqlSubString += " "+groupOp+" cast("+ fld.toString() + " as date) >= :"+namedParam.toString()+"_1";
								hqlSubString += " "+groupOp+" cast("+ fld.toString() + " as date) < :"+namedParam.toString()+"_2";
								groupOp=groupOperation;
							}else if(searchType.equalsIgnoreCase("string")){
								hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") =  lower(:"+namedParam.toString()+") ";
								groupOp=groupOperation;
							}
						}else if(opr.equals("ne")){

							if(searchType.equalsIgnoreCase("integer")){
								hqlSubString += " "+groupOp+" "+ fld.toString() + " !=  :"+namedParam.toString();
								groupOp=groupOperation;
							}if(searchType.equalsIgnoreCase("double") || searchType.equalsIgnoreCase("date")){
								hqlSubString += " "+groupOp+" "+ fld.toString() + " !=  :"+namedParam.toString();
								groupOp=groupOperation;
							}else if(searchType.equalsIgnoreCase("string")){
								hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") !=  lower(:"+namedParam.toString()+") ";
								groupOp=groupOperation;
							}
						}else if(opr.equals("bw")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("bn")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") not like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("ew")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ")  like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("en")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") not like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("cn")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("nc")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") not like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("in")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " in  (:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("ni")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " not  in  (:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}
					}
				}
				if((hqlSubString.trim()).length()>0){
					hqlString += "AND ( "+hqlSubString+") ";
				}
				hqlString+=groupByString;
				if(null !=pagination && null != pagination.getOrderByField()  && null != pagination.getOrderBy()){
					hqlString += " ORDER BY" + ApplicationConstants.EmptyString +pagination.getOrderByField() + ApplicationConstants.EmptyString +  pagination.getOrderBy();
				}
				query=getHQLQuery(hqlString,isToUseOpenSession);
				String namedVal = null;
				ArrayList<String> duplicateChk= new ArrayList() ;
				for (int i = 0; i < rulesCount; i++) {
					JSONObject rule = rules.getJSONObject(i);
					String fld = rule.getString("field");
					String opr =rule.getString("op");
					String dataVal =rule.getString("data");
					String searchType=rule.getString("searchType").toLowerCase();
					if(isCompositKey) {
						if(compositKeyFieldAliasMap.containsKey(fld)) {
							fld  = compositKeyFieldAliasMap.get(fld); 
						}
					}
					if(null !=dataVal && !dataVal.equalsIgnoreCase("") && !dataVal.equals("__/__/____") &&  null !=fld && !fld.equalsIgnoreCase("")){
						if(fld.contains(".")){ 
							namedVal = fld.substring(fld.lastIndexOf(".") + 1);
							if(duplicateChk != null && duplicateChk.size() > 0){
								for (int x = 0;  x< duplicateChk.size(); x++){
									if(namedVal != null && namedVal.equalsIgnoreCase(duplicateChk.get(x))){
										namedVal = fld.substring(fld.lastIndexOf(".") + 1)+"_"+i;
									}
								}
							}
						}else{
							namedVal =fld;
						}
						duplicateChk.add(namedVal);
						if(searchType.equalsIgnoreCase("integer")){
							query.setParameter(namedVal, Integer.parseInt(dataVal));
						}else if(searchType.equalsIgnoreCase("double")){
							query.setParameter(namedVal, Double.parseDouble((dataVal)));
						}else if(searchType.equalsIgnoreCase("date")){
							try {
								query.setParameter(namedVal,DateHelper.getDateFromString(dataVal, DateHelper.ISO_8601_DATE_FORMAT));
							}catch (ParseException e) {
								throw new ApplicationException("Exception While Setting Date",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
							}
						}else if(searchType.equalsIgnoreCase("TimeStampIgnoredDate")){
							try {
								query.setParameter(namedVal+"_1",DateHelper.getDateFromString(dataVal, DateHelper.ISO_8601_DATE_FORMAT));
								
								final SimpleDateFormat format = new SimpleDateFormat(DateHelper.ISO_8601_DATE_FORMAT);
								final Date date = format.parse(dataVal);
								final Calendar calendarTo = Calendar.getInstance();
								calendarTo.setTime(date);
								calendarTo.add(Calendar.DAY_OF_YEAR, 1);
								query.setParameter(namedVal+"_2",calendarTo.getTime());
							}catch (ParseException e) {
								throw new ApplicationException("Exception While Setting Timestamp Ignored Date",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
							}
						}else if(searchType.equalsIgnoreCase("string")){
							if(opr.equals("eq") || opr.equals("ne")){
								query.setParameter(namedVal, dataVal);
							}else if(opr.equals("bw") || opr.equals("bn")){
								query.setParameter(namedVal, dataVal+"%");
							}else if(opr.equals("ew") || opr.equals("en")){
								query.setParameter(namedVal, "%"+dataVal);
							}else if(opr.equals("cn") || opr.equals("nc")){
								query.setParameter(namedVal, "%"+dataVal+"%");
							}
						} 
					}
				}
			}else{
				hqlString+=groupByString;
				if(null !=pagination && null != pagination.getOrderByField()  && null != pagination.getOrderBy()){
					hqlString += " ORDER BY" + ApplicationConstants.EmptyString +pagination.getOrderByField() + ApplicationConstants.EmptyString +  pagination.getOrderBy();
				}
				query=getHQLQuery(hqlString,isToUseOpenSession);
			}
		}catch (NumberFormatException e) {
			throw new ApplicationException("Number Format Exception",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
		} catch (JSONException e) {
			throw new ApplicationException("Json Format Exception",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
		}

		if(pagination!=null){
			query.setFirstResult(pagination.getStartIndex());
			query.setMaxResults(pagination.getRecordsPerPage());
		}
		return query;
	}

	/**
	 * (non-Javadoc)
	 * @see com.epps.domain.generic.dao.IGenericDAO#isEmptyObject(java.lang.Object)
	 * 
	 * @author Sudam Jadhav
	 * @description	This Common Method Is used to check Is All Fields Are Empty Or Null In Given Object.
	 * @param object
	 * @return Boolean
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */

	@Override
	public Boolean isEmptyObject(Object object) throws IllegalArgumentException, IllegalAccessException{

		for (Field field : object.getClass().getDeclaredFields()) {
			Object fieldType = field.getType();
			field.setAccessible(true);
			Object value = field.get(object);
			if(fieldType.equals(String.class)){
				if(value != null && !value.equals("")){
					return false;
				}
			}else if(value != null){
				return false;
			}
		}
		return true;
	}

	/**
	 * @see com.epps.domain.generic.dao.IGenericDAO#getProjectionsForCriteriaQuery(org.hibernate.Criteria, java.lang.Object, org.hibernate.criterion.ProjectionList)
	 */
	@Override
	public Criteria getProjectionsForCriteriaQuery(Criteria criteria,Object object,ProjectionList proList)throws IllegalArgumentException, IllegalAccessException{

		for (Field field : object.getClass().getDeclaredFields()) {
			if(!field.getType().getName().contains("com.epps.entity") && !Collection.class.isAssignableFrom(field.getType())){
				proList.add(Projections.property(field.getName()).as(field.getName()));
			}
		}
		return criteria.setProjection(proList);
	}

	/**
	 * @see com.epps.domain.generic.dao.IGenericDAO#getColumnListForHqlQuery(java.lang.String, java.lang.Object)
	 */
	@Override
	public String getColumnListForHqlQuery(String hqlString,Object object)throws IllegalArgumentException, IllegalAccessException{

		for (Field field : object.getClass().getDeclaredFields()) {
			if(!field.getType().getName().contains("com.epps.entity") && !Collection.class.isAssignableFrom(field.getType())){
				hqlString += (field.getName())+" as "+ (field.getName())+" , ";
			}
		}

		//to fetch columns only and remove last ,
		int index=hqlString.lastIndexOf(",");
		if(index != -1) {
			hqlString = hqlString.substring(0,index);
		}
		return hqlString;
	}

	/**
	 * @author Aniket Jadhav
	 * @description	This Common Method is used delete entity with some restriction and not only on primary key.
	 * @param entityClass for which data to be delted
	 * @param restrictionList on which data will be delete
	 * @throws DatabaseException
	 */	
	@Override
	public void deleteEntityWithRestrictions(Class entityClass, List<GenericIdValueDTO<String, Object>> restrictionList)
			throws DatabaseException {
		Query  query =null;
		String deleteQuery = "DELETE FROM " + entityClass.getName() + " WHERE ";
		//Criteria deleteCriteria = getCriteria(entityClass);
		int restrictionsSize= restrictionList.size();
		for (int i = 0; i < restrictionsSize; i++) {
			deleteQuery = deleteQuery + restrictionList.get(i).getId() +  "= :" +restrictionList.get(i).getId();
			if((restrictionsSize-1) != i){
				deleteQuery=deleteQuery+ " AND ";
			}

		}
		query = getHQLQuery(deleteQuery);

		for (GenericIdValueDTO<String, Object> genericIdValueDTO : restrictionList) {
			query.setParameter(genericIdValueDTO.getId(), genericIdValueDTO.getValue());
		}
		executeHQLDMLQuery(query);
	}


	/**
	 *  @author Gaurav Kamble
	 * @description	Common method to flush hibernate transaction. Used to force flush the hibernate transaction before automatic transaction management by - @Transactional  
	 */
	@Override
	public void flush() {
		getCrntSession().flush();
	}

	@Override
	public <T> String getColumnListForReportHqlQuery(String hqlQueryString,Class<T> className,List<ColumnDictionaryMasterDTO> list)throws IllegalArgumentException, IllegalAccessException{

		/**Table table = EppsGlobalMmPurcReport.class.getAnnotation(Table.class);
		String tableName = table.name();
		String schema = table.schema();*/
		//To get the table name and schema and attribute of that table
		AbstractEntityPersister aep=((AbstractEntityPersister)sessionFactory.getClassMetadata(className));
		String[] properties=aep.getPropertyNames();
		for(int nameIndex=0;nameIndex!=properties.length;nameIndex++){
			String[] columns=aep.getPropertyColumnNames(nameIndex);
			for (ColumnDictionaryMasterDTO columnDictionaryMasterDTO : list) {
				if(columnDictionaryMasterDTO.getTableColumnId().equals(columns[0])){
//					coalesce(eppsIndentDetailAlias.netQuantity, 0
					if(columnDictionaryMasterDTO.getColumnDataType().contains("Double") || columnDictionaryMasterDTO.getColumnDataType().contains("Integer") || columnDictionaryMasterDTO.getColumnDataType().contains("Numeric")){
						hqlQueryString += "coalesce("+properties[nameIndex]+",0)"+" as "+ properties[nameIndex] +" , ";
					}else if(columnDictionaryMasterDTO.getColumnDataType().contains("String") || columnDictionaryMasterDTO.getColumnDataType().contains("string")){
						hqlQueryString += "coalesce("+properties[nameIndex]+",'')"+" as "+ properties[nameIndex] +" , ";
					} else{
						hqlQueryString += properties[nameIndex]+" as "+ properties[nameIndex] +" , ";
					}
				}
			}
		}
		int index=hqlQueryString.lastIndexOf(",");
		if(index != -1) {
			hqlQueryString = hqlQueryString.substring(0,index);
		}
		return hqlQueryString;
	}

	@Override
	public Session getNewOpentSession() throws DatabaseException {
		try {
			return sessionFactory.openSession();
		} catch (final HibernateException e) {
			logger.error("getNewOpentSession: failed to complete operation");
			throw new DatabaseException("Failed to get thread session ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}
	
	@Override
	public Query getSQLQueryObject(final String sql,Boolean isToUseOpenSession) throws DatabaseException {
		try {
			Query hqlQuery = null;
			if (isToUseOpenSession != null && isToUseOpenSession) {
				hqlQuery = CustomThreadLocal.get().createSQLQuery(sql);
			}else {
				hqlQuery = getCrntSession().createSQLQuery(sql);
			}
			return hqlQuery;
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while creating sql query object " + sql, ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}

	@Override
	public <V> List<V> executeCriteiraL2CacheEanable(final Criteria criteria) throws DatabaseException {
		try {
			return criteria.setCacheable(true).list();
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while executing criteria " + criteria, ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}
	
	@Override
	public Criteria getCachedCriteriaWithAlias(final Class entityclass,final String alias) throws DatabaseException {
		try {
			return getCrntSession().createCriteria(entityclass,alias).setCacheable(true);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while creating criteria ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
	}

	@Override
	public void persistObject(Object object) {
	try {
			getCrntSession().persist(object);
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while save or update All ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
		
	}

	@Override
	public void persistAllObject(Collection<?> objects) {
		try {
			for (Object object : objects) {
				getCrntSession().persist(object);
			}
		} catch (final HibernateException e) {
			throw new DatabaseException("Exception while save or update All ", ErrorCode.BASE_DB_ERROR, e, ResponseInfoType.ERROR);
		}
		
	}

	@Override
	public StringBuilder getColumnListForHqlQuery(StringBuilder hqlString, Object object, StringBuilder entityDetailAlias, String entityAliasString)
			throws IllegalArgumentException, IllegalAccessException {
		for (Field field : object.getClass().getDeclaredFields()) {
			if ((field.toString().contains("com.epps.module.entity") || field.toString().contains("com.epps.domain.crm.entity") || field.toString().contains("com.epps.framework.entity.base"))
					&& !Collection.class.isAssignableFrom(field.getType())
					&& !field.getType().toString().contains("com.epps")
					&& !field.getName().equalsIgnoreCase(ApplicationConstants.FIELD_NAME_SERIAL_VERSION_UID)
					&& !checkFieldForTransientAnnotation(field)) {
				if (null != entityDetailAlias) {
					hqlString.append(entityDetailAlias);
				} else {
					if (null != entityAliasString) {
						hqlString.append(entityAliasString + ".");
					} else {
						hqlString.append("entityAlias.");
					}
				}
				hqlString.append(field.getName());
				hqlString.append(" as ");
				hqlString.append(field.getName());
				hqlString.append(" , ");
			}
		}

		if (null != object.getClass().getSuperclass() && (object.getClass().getSuperclass().getName().contains("com.epps.module.entity.base.BaseEntity")
						|| object.getClass().getSuperclass().getName().contains("com.epps.module.entity.base.CreatorBaseEntity") 
						|| object.getClass().getSuperclass().getName().contains("com.epps.framework.entity.base.CreatorBaseEntity")
						|| object.getClass().getSuperclass().getName().contains("com.epps.framework.entity.base.BaseEntity"))) {
			for (Field field : object.getClass().getSuperclass().getDeclaredFields()) {
				if ((field.toString().contains("com.epps.module.entity")
//						|| field.toString().contains("com.epps.module.entity")	// duplicate code
						|| field.toString().contains("com.epps.module.entity.base")
						|| field.toString().contains("com.epps.framework.entity.base"))
						&& !Collection.class.isAssignableFrom(field.getType())
						&& !field.getType().toString().contains("com.epps")
						&& !field.getName().equalsIgnoreCase(ApplicationConstants.FIELD_NAME_SERIAL_VERSION_UID)
						&& !checkFieldForTransientAnnotation(field)) {
					
					
					
					if (null != entityDetailAlias) {
						hqlString.append(entityDetailAlias);
					} else {
						if (null != entityAliasString) {
							hqlString.append(entityAliasString + ".");
						} else {
							hqlString.append("entityAlias.");
						}
					}
					hqlString.append(field.getName());
					hqlString.append(" as ");
					hqlString.append(field.getName());
					hqlString.append(" , ");
				}
			}
		}
//to fetch columns only and remove last ,
		int index = hqlString.lastIndexOf(",");
		if (index != -1) {
			hqlString = hqlString.deleteCharAt(index);
		}
		return hqlString;
	}
	
	public Query getAdvanceSearchOptionsInSqlQuery(SearchDTO searchDTO, String hqlString, PaginationDTO pagination, Boolean isToUseOpenSession, Boolean isCompositKey, Map<String, String> compositKeyFieldAliasMap) {

		Query  query =null;
		try {

			String groupOperation=null;

			if(null != searchDTO.getFilters() && !searchDTO.getFilters().equals("")){

				JSONObject jsonFilter =  new JSONObject(searchDTO.getFilters() );                                       

				JSONArray rules = (JSONArray)jsonFilter.getJSONArray("rules");

				groupOperation= jsonFilter.getString("groupOp");

				int rulesCount = rules.length();//JSONArray.getDimensions(rules)[0];

				String groupOp="";
				String hqlSubString="";
				String namedParam = null;

				ArrayList<String> duplicateFldChk= new ArrayList() ;

				for (int i = 0; i < rulesCount; i++) {

					JSONObject rule = rules.getJSONObject(i);

					String fld = rule.getString("field");

					String opr =rule.getString("op");

					String dataVal =rule.getString("data");

					String searchType=rule.getString("searchType").toLowerCase();
					if(isCompositKey) {
						if(compositKeyFieldAliasMap.containsKey(fld)) {
							fld  = compositKeyFieldAliasMap.get(fld); 
						}
					}
					if(null !=dataVal && !dataVal.equalsIgnoreCase("") &&  !dataVal.equals("__/__/____")  &&  null !=fld && !fld.equalsIgnoreCase("")){
						if(fld.contains(".")){ 
							namedParam = fld.substring(fld.lastIndexOf(".") + 1);
							if(duplicateFldChk != null && duplicateFldChk.size() > 0){
								for (int x = 0;  x< duplicateFldChk.size(); x++){
									if(namedParam != null && namedParam.equalsIgnoreCase(duplicateFldChk.get(x))){
										namedParam = fld.substring(fld.lastIndexOf(".") + 1)+"_"+i;
									}
								}
							}
						}else{
							namedParam = fld;
						}

						duplicateFldChk.add(namedParam);
						if(opr.equals("lt")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " <  :"+namedParam.toString();
							groupOp=groupOperation;
						}else if(opr.equals("le")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " <=  :"+namedParam.toString();
							groupOp=groupOperation;
						}else if(opr.equals("gt")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " >  :"+namedParam.toString();
							groupOp=groupOperation;
						}else if(opr.equals("ge")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " >=  :"+namedParam.toString();
							groupOp=groupOperation;
						}else if(opr.equals("eq")){
							if(searchType.equalsIgnoreCase("integer") || searchType.equalsIgnoreCase("double")){
								hqlSubString += " "+groupOp+" "+ fld.toString() + " =  :"+namedParam.toString();
								groupOp=groupOperation;
							}else if(searchType.equalsIgnoreCase("date")){
								hqlSubString += " "+groupOp+" cast("+ fld.toString() + " as date) =  :"+namedParam.toString();
								groupOp=groupOperation;
							}else if(searchType.equalsIgnoreCase("TimeStampIgnoredDate")){
								
								hqlSubString += " "+groupOp+" cast("+ fld.toString() + " as date) >= :"+namedParam.toString()+"_1";
								hqlSubString += " "+groupOp+" cast("+ fld.toString() + " as date) < :"+namedParam.toString()+"_2";
								groupOp=groupOperation;
							}else if(searchType.equalsIgnoreCase("string")){
								hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") =  lower(:"+namedParam.toString()+") ";
								groupOp=groupOperation;
							}
						}else if(opr.equals("ne")){

							if(searchType.equalsIgnoreCase("integer")){
								hqlSubString += " "+groupOp+" "+ fld.toString() + " !=  :"+namedParam.toString();
								groupOp=groupOperation;
							}if(searchType.equalsIgnoreCase("double") || searchType.equalsIgnoreCase("date")){
								hqlSubString += " "+groupOp+" "+ fld.toString() + " !=  :"+namedParam.toString();
								groupOp=groupOperation;
							}else if(searchType.equalsIgnoreCase("string")){
								hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") !=  lower(:"+namedParam.toString()+") ";
								groupOp=groupOperation;
							}
						}else if(opr.equals("bw")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("bn")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") not like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("ew")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ")  like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("en")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") not like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("cn")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}else if(opr.equals("nc")){
							hqlSubString += " "+groupOp+" lower("+ fld.toString() + ") not like  lower(:"+namedParam.toString()+") ";
							groupOp=groupOperation;

						}else if(opr.equals("in")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " in  (:"+namedParam.toString()+") ";
							groupOp=groupOperation;

						}else if(opr.equals("ni")){
							hqlSubString += " "+groupOp+" "+ fld.toString() + " not  in  (:"+namedParam.toString()+") ";
							groupOp=groupOperation;
						}
					}
				}

				if((hqlSubString.trim()).length()>0){
					if(hqlString.toLowerCase().indexOf("where") > 0 && hqlString.toLowerCase().indexOf("group by") > 0){
						String[] arrOfStr = hqlString.split("GROUP BY", 2);
						if(arrOfStr.length == 1) {
							arrOfStr = hqlString.split("group by", 2);
						}
						if(arrOfStr.length > 1) {
							String hqlQry = arrOfStr[0];
							hqlQry += " AND ("+hqlSubString+")";
							hqlQry += " GROUP BY "+arrOfStr[1];
							hqlString = hqlQry;
						}
					}else if(hqlString.toLowerCase().indexOf("where") > 0){
						hqlString += " AND ("+hqlSubString+")";
					}else {
						hqlString += " WHERE  ("+hqlSubString+")";
					}
				}

				if(null !=pagination && null != pagination.getOrderByField()  && null != pagination.getOrderBy()){
					hqlString += " ORDER BY" + ApplicationConstants.EmptyString +pagination.getOrderByField() + ApplicationConstants.EmptyString +  pagination.getOrderBy();
				}
				query=getSQLQueryObject(hqlString,isToUseOpenSession);
				String namedVal = null;
				ArrayList<String> duplicateChk= new ArrayList() ;
				for (int i = 0; i < rulesCount; i++) {

					JSONObject rule = rules.getJSONObject(i);

					String fld = rule.getString("field");

					String opr =rule.getString("op");

					String dataVal =rule.getString("data");

					String searchType=rule.getString("searchType").toLowerCase();
					if(isCompositKey) {
						if(compositKeyFieldAliasMap.containsKey(fld)) {
							fld  = compositKeyFieldAliasMap.get(fld); 
						}
					}
					if(null !=dataVal && !dataVal.equalsIgnoreCase("") && !dataVal.equals("__/__/____") &&  null !=fld && !fld.equalsIgnoreCase("")){
						if(fld.contains(".")){ 

							namedVal = fld.substring(fld.lastIndexOf(".") + 1);
							if(duplicateChk != null && duplicateChk.size() > 0){
								for (int x = 0;  x< duplicateChk.size(); x++){
									if(namedVal != null && namedVal.equalsIgnoreCase(duplicateChk.get(x))){
										namedVal = fld.substring(fld.lastIndexOf(".") + 1)+"_"+i;
									}
								}
							}
						}else{
							namedVal =fld;
						}

						duplicateChk.add(namedVal);
						if(searchType.equalsIgnoreCase("integer")){
							query.setParameter(namedVal, Integer.parseInt(dataVal));
						}else if(searchType.equalsIgnoreCase("double")){
							query.setParameter(namedVal, Double.parseDouble((dataVal)));
						}else if(searchType.equalsIgnoreCase("date")){
							try {
								query.setParameter(namedVal,DateHelper.getDateFromString(dataVal, DateHelper.ISO_8601_DATE_FORMAT));
							}catch (ParseException e) {
								throw new ApplicationException("Exception While Setting Date",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
							}
						}else if(searchType.equalsIgnoreCase("TimeStampIgnoredDate")){
							try {
								query.setParameter(namedVal+"_1",DateHelper.getDateFromString(dataVal, DateHelper.ISO_8601_DATE_FORMAT));
								
								final SimpleDateFormat format = new SimpleDateFormat(DateHelper.ISO_8601_DATE_FORMAT);
								final Date date = format.parse(dataVal);
								final Calendar calendarTo = Calendar.getInstance();
								calendarTo.setTime(date);
								calendarTo.add(Calendar.DAY_OF_YEAR, 1);
								query.setParameter(namedVal+"_2",calendarTo.getTime());
							}catch (ParseException e) {
								throw new ApplicationException("Exception While Setting Timestamp Ignored Date",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
							}
						}else if(searchType.equalsIgnoreCase("string")){
							if(opr.equals("eq") || opr.equals("ne")){
								query.setParameter(namedVal, dataVal);
							}else if(opr.equals("bw") || opr.equals("bn")){
								query.setParameter(namedVal, dataVal+"%");
							}else if(opr.equals("ew") || opr.equals("en")){
								query.setParameter(namedVal, "%"+dataVal);
							}else if(opr.equals("cn") || opr.equals("nc")){
								query.setParameter(namedVal, "%"+dataVal+"%");
							}
						} 
					}
				}
			}else{
				if(null !=pagination && null != pagination.getOrderByField()  && null != pagination.getOrderBy()){
					hqlString += " ORDER BY" + ApplicationConstants.EmptyString +pagination.getOrderByField() + ApplicationConstants.EmptyString +  pagination.getOrderBy();
				}
				query=getSQLQueryObject(hqlString,isToUseOpenSession);
			}

			/*if(null !=pagination){
				query.setParameter(++count, pagination.getOrderByField());
			}*/

		}catch (NumberFormatException e) {
			throw new ApplicationException("Number Format Exception",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
		} catch (JSONException e) {
			throw new ApplicationException("Json Format Exception",ErrorCode.GENERIC_ERROR, ResponseInfoType.ERROR);
		}

		logger.debug("adding advance search options Successfully in hql");

		if(pagination!=null){
			query.setFirstResult(pagination.getStartIndex());
			query.setMaxResults(pagination.getRecordsPerPage());
		}
		return query;
	}

	private Boolean checkFieldForTransientAnnotation(Field field) {
		Annotation[] annotations = field.getDeclaredAnnotations();
		for(Annotation annotation : annotations){
		    if(annotation instanceof Transient){
		      return true;
		    }
		}
		return false;
	}
	
	
	
}
