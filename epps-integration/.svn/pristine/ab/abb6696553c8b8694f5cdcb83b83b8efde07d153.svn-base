package com.epps.framework.application.txmgmt;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.springframework.stereotype.Component;

import com.epps.framework.application.constant.ApplicationConstants;
import com.epps.framework.application.threads.CustomThreadLocal;
import com.epps.framework.application.util.logger.ApplicationLogger;
import com.epps.framework.domain.exception.ApplicationException;
import com.epps.framework.domain.exception.ProcedureException;
import com.epps.framework.domain.exception.ResponseInfoType;
/**
 * @description	Aspect for hibernate Session handling for long session management in httpSession.
 * 				Around pointcut aspect get called, before pointcut hibernate Session if bound to 
 * 				httpSession present in attribute(ApplicationConstants.HIBERNATE_SESSION) then it 
 * 				set to Threadlocal. Then method get executed. After pointcut if hibernate Session 
 * 				present in Threadlocal, then it pushed to httpSession in attribute(ApplicationConstants.HIBERNATE_SESSION) 
 * 				and ThreadLocal is cleared from thread for next request use.
 * @author 		Vivek Nerle
 * @since  		2017/20/09
 * @history
 * updated by			updated date		description
 * Vivek Nerle			2017/03/10			In @AfterThrowing method changes done to close transaction if current 
 * 											transaction is not successfully completed.
 * Vivek Nerle			2018/08/08			Changes made for implementing functionality for mobile requests.
 * Pravin B				2019/15/03			Changes made for remove MobileSession and Added MobileHttpSession
 * 
 */
@Aspect
@Component
public class HibernateSessionHandlingAspect{

	private static final ApplicationLogger LOGGER = new ApplicationLogger(HibernateSessionHandlingAspect.class);


	@Around("@annotation(com.epps.framework.application.txmgmt.EppsHibernateSessionHandler)")
	private Object beforeAndAfterActions(ProceedingJoinPoint pjp) throws Throwable{

		HttpServletRequest httpServletRequest = null;
		HttpSession httpSession = null;
		Session hibernateSession = null;
		//ConcurrentHashMap<String, MobileSession> sessionContainer = sessionAttributeManager.getMobSessions();
		Boolean requestParamFound = false;

		Object[] signatureArgs = pjp.getArgs();
		for (Object signatureArg: signatureArgs) {

			if(signatureArg instanceof HttpServletRequest){
				requestParamFound = true;
				httpServletRequest = (HttpServletRequest)signatureArg;
				httpSession = httpServletRequest.getSession(false);
				if (null != httpSession) {
					hibernateSession = (Session) httpSession.getAttribute(ApplicationConstants.HIBERNATE_SESSION);
					if (hibernateSession != null && hibernateSession.isOpen()) {
						CustomThreadLocal.set(hibernateSession);
					}
				}
			}
		}

		if(requestParamFound.equals(true)){
			Object returnObject = pjp.proceed();
				if (CustomThreadLocal.get() != null && CustomThreadLocal.get().isOpen()){
					if(null!=httpSession)
						httpSession.setAttribute(ApplicationConstants.HIBERNATE_SESSION, CustomThreadLocal.get());
					LOGGER.info("Hibernate Session Set in HTTP Session");
				}else{
					if (null != httpSession && hibernateSession != null && hibernateSession.isOpen()) {
						hibernateSession.close();
						httpSession.setAttribute(ApplicationConstants.HIBERNATE_SESSION, null);
						LOGGER.info("Hibernate Session Close and remove from HTTP Session");
					}else{
						if(null != httpSession)
							httpSession.setAttribute(ApplicationConstants.HIBERNATE_SESSION, null);
							LOGGER.info("Hibernate Session Close and remove from HTTP Session");
					}
				}
			
			CustomThreadLocal.set(null);
			return returnObject;
		}else{
			throw new ApplicationException("Session management having problem.",null,ResponseInfoType.ERROR);
		}
	}


	@AfterThrowing(pointcut="@annotation(com.epps.framework.application.txmgmt.EppsHibernateSessionHandler)",throwing="ex")
	private void afterThrowingActions(JoinPoint jp,Exception ex){

		HttpServletRequest httpServletRequest = null;
		HttpSession httpSession = null;

		Object[] signatureArgs = jp.getArgs();
		for (Object signatureArg: signatureArgs) {

			if(signatureArg instanceof HttpServletRequest){
				if(CustomThreadLocal.get() != null){
					if(ex instanceof ProcedureException || ex instanceof HibernateException || ex instanceof SQLException || 
							(ex.getCause()!=null && ex.getCause().getCause()!=null && ex.getCause().getCause().getClass().getName().equals(ApplicationConstants.ORG_POSTGRESQL_UTIL_PSQLEXCEPTION))){
						
						httpServletRequest = (HttpServletRequest) signatureArg;
						httpSession = httpServletRequest.getSession(false);
						Transaction tx = CustomThreadLocal.get().getTransaction();
						try{
							tx.rollback();
							CustomThreadLocal.get().close();
							httpSession.setAttribute(ApplicationConstants.HIBERNATE_SESSION, null);
							LOGGER.info("Hibernate Session Close and remove from HTTP Session");
							LOGGER.info("Hibernate Session Close and remove from Mobile HTTP Session");
						}catch(TransactionException e){
							CustomThreadLocal.get().close();
							httpSession.setAttribute(ApplicationConstants.HIBERNATE_SESSION, null);
							LOGGER.info("Hibernate Session Close and remove from HTTP Session");
						}
					}
				}
			}
		}
		CustomThreadLocal.set(null);
	}

}
