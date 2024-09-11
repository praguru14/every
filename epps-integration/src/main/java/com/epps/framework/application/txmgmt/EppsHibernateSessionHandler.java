package com.epps.framework.application.txmgmt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;
/**
 * @description	Custom annotation for hibernate Session handling for long session management in 
 * 				httpSession at @Controller methods. Use this annotation about @Controller method 
 * 				calls in which long hibernate session is required.
 * @author 		Vivek Nerle
 * @since  		2017/20/09
 * @history
 * updated by			updated date		description
 * 
 */
@Component
@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)

public @interface EppsHibernateSessionHandler {

}
