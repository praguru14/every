package com.epps.framework.application.txmgmt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;
/**
 * @description	Custom annotation for transaction management(begin, commit and rollback).
 * @author 		Rahul Patil
 * @since  		2017/20/07
 * @history
 * updated by			updated date		description
 * 
 */
@Component
@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface EppsTransactional {

}
