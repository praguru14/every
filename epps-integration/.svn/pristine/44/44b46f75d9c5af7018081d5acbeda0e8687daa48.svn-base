package com.epps.framework.notification.mail.application.event.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;


@Component
@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface SendEvent {

	String pId() default "";
	
	String tranIndicator() default "";
	
	String mtqrFlag() default "";
	
	String ecodeField() default "";
	
	boolean isScheduled() default false;
	
	String partyType() default "";
	
	String partyCodeField() default "";
	
	String partyAddCodeField() default "";
	
	String nextUpdatorField() default "";
	
	String reportUri() default "";
	
	String subScreenNm() default "";
	
	String locationCodeField() default "";
	
	boolean isPartyTypeInHdr() default false;
	
	String[] predefinedEvents() default {};
	
	String compCdField() default "";
	
	String divCdField() default "";
	
	boolean isSingleScreenMultiPid() default false;
	
	String compositePkGetterNm() default "";
	
	//String detailReportUri()  default "";
	
}
