package com.epps.framework.notification.mail.application.event.listener;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.epps.framework.domain.exception.ApplicationException;
import com.epps.framework.domain.exception.ProcedureException;
import com.epps.framework.notification.mail.application.event.Event;



public interface EventProcessor {
	
	public Object processEvent(Event event) throws NoSuchFieldException, SecurityException, ApplicationException, ProcedureException, InvocationTargetException, ParseException, ClassNotFoundException;

	public String fillVelocityTemplate(String mailVmPath, StandardEvaluationContext valueContext, Object dataObj);
	
	public StandardEvaluationContext initializeSpelContext(Object object);
}
