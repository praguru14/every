package com.epps.framework.notification.mail.application.event.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Component
@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)

public @interface EventAttribute {
	
	String field() default "";
	
	String displayName() default "";
	
	String fieldType() default "";
	
	String purpose() default "B";
	
	String predefinedValues() default "";
	
	boolean isTableField() default false;
	
	boolean isCountField() default false;
	
	boolean isSumField() default false;
	
	boolean isToConcatPrefix() default false;
	
	String prefix() default "";
	
	boolean isOnlyForMaster() default false;
	
	boolean isSmsField() default false;
	
}
