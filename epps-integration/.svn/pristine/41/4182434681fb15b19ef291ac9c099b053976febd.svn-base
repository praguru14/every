package com.epps.framework.application.txmgmt;

import java.sql.SQLException;
import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epps.framework.application.threads.CustomThreadLocal;
import com.epps.framework.application.util.logger.ApplicationLogger;
import com.epps.framework.domain.exception.ApplicationException;
import com.epps.framework.domain.exception.ResponseInfoType;
/**
 * @description	Aspect for creating session and transaction management(begin, commit and rollback).
 * @author 		Rahul Patil
 * @since  		2017/20/07
 * @history
 * updated by			updated date		description
 * Vivek Nerle			2017/17/08			Simultaneous httpRequest hampers aspects functionality by begin
 * 											transaction which is already began. Issue handled by adding code 
 * 											in synchronized block on session object.
 * Vivek Nerle			2017/18/09			Hibernate Session closing issues resolved while getting exception.
 * Pravin M				2020/13/01			Fixed Issue with obtain session method used for autocommit false, due to this new connection created every time.
 * Vivek Nerle			2020/01/06			While transaction exception,session exception occurred session is closed but from threadlocal session object is not cleared 
 * 											so that when that thread is reused in that object get accessible then "Session is closed" exception occurred. 
 * 											So cleared session object from threadlocal after closing session. 
 */
@Aspect
@Component
public class TransactionMgmtAspect{
	
	private static final ApplicationLogger logger = new ApplicationLogger(TransactionMgmtAspect.class);

	@Autowired
	protected SessionFactory sessionFactory;

	@Around("@annotation(com.epps.framework.application.txmgmt.EppsTransactional)")
	private Object beginTransaction(ProceedingJoinPoint pjp) throws Throwable{
		
		Session session = getOpenSession();
		synchronized (session) {
			Transaction tx = session.beginTransaction();
			Object returnObject = pjp.proceed();

			if(!tx.getStatus().equals(TransactionStatus.COMMITTED)){
				tx.commit();
			}
//			session.disconnect(); ---Dependent temp_tables get issues of "table not present" so code commented on 22/09/2017 
			return returnObject;
		}
	}

	@AfterThrowing("@annotation(com.epps.framework.application.txmgmt.EppsTransactional)")
	private void rollbackTransaction(JoinPoint joinPoint) throws SQLException {

		Session session = CustomThreadLocal.get();
		if(session != null){
			synchronized (session) {
				try{
					Transaction tx = CustomThreadLocal.get().getTransaction();
					tx.rollback();
//					session.disconnect(); ---Dependent temp_tables get issues of "table not present" so code commented on 22/09/2017 
					}catch(TransactionException e){
						session.close();
						CustomThreadLocal.set(null);
						logger.error("epps:exception:CustomThreadLocal session closed and settled to null");
						throw new ApplicationException(e.getMessage(),null, ResponseInfoType.ERROR);
					}catch(SessionException e){
						if(session.isOpen()){
						session.close();
					}
					CustomThreadLocal.set(null);
					logger.error("epps:exception:CustomThreadLocal session already closed so settled to null");
					throw new ApplicationException(e.getMessage(),null, ResponseInfoType.ERROR);
				}
			}
		}
	}

	public org.hibernate.Session getOpenSession(){
		org.hibernate.Session session = CustomThreadLocal.get();
		try{
			if (session == null) {
				session = sessionFactory.openSession();
				try {
					SessionImplementor implementor = (SessionImplementor) session;
					implementor.connection().setAutoCommit(false);
					CustomThreadLocal.set(session);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			UUID uuid=UUID.randomUUID();
			logger.error("exception:uuid="+uuid+":",e);
		}
		return session;
	}
}