package com.epps.framework.notification.mail.application.event.aspect;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.epps.framework.notification.mail.application.event.EntityEvent;
import com.epps.framework.notification.mail.application.event.annotation.SendEvent;
import com.epps.framework.notification.mail.application.event.listener.EntityEventListener;

/**
 * @description	: This aspect needs to execute around @Transactional (TransactionIntercepter) so applied @Order and PRECEDENCE has been set to HIGHEST.
 * 				  In future if there is a need to apply logic at "way in to the joinpoint", then it might be necessary to change the precedence.
 */
@Aspect
@Order(value=Ordered.HIGHEST_PRECEDENCE)
@Component
public class SendEventAspect {
	
	@Autowired
	private EntityEventListener entityEventListener;
	
	
	/**
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(com.epps.framework.notification.mail.application.event.annotation.SendEvent)")
	private Object processSendEvent(ProceedingJoinPoint pjp) throws Throwable {
        Object retVal = pjp.proceed();
        if(retVal != null) {
        	SendEvent event = extractAnnotation(pjp);
        	entityEventListener.handleSendEvent(fillEntityEvent(event,retVal));
        }
        return retVal;
	}


	/**
	 * @param event
	 * @param retVal
	 * @param pjp
	 * @return
	 */
	private EntityEvent fillEntityEvent(final SendEvent event, final Object retVal) {
		return new EntityEvent(event,retVal);
	}

	private Object evalExpression(final String expr, final Object obj) {
		Object value = obj;
		String expression = expr != null? expr.trim() : "";
		if(!StringUtils.isBlank(expression)) {
			final ExpressionParser parser = new SpelExpressionParser();
			StandardEvaluationContext valueContext = new StandardEvaluationContext(obj);
			Expression exp =  parser.parseExpression(expression);
			value = exp.getValue(valueContext); 
		}
		return value;
	}

	/**
	 * @param pjp
	 * @return
	 * @throws NoSuchMethodException
	 */
	private SendEvent extractAnnotation(final ProceedingJoinPoint pjp)
			throws NoSuchMethodException {
		final String methodName = pjp.getSignature().getName();
		final MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		Method method = methodSignature.getMethod();
		if (method.getDeclaringClass().isInterface()) {
			method = pjp.getTarget().getClass()
					.getDeclaredMethod(methodName, method.getParameterTypes());
		}
		return method.getAnnotation(SendEvent.class);
	}
	
}
