package com.epps.framework.domain.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;



@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER ,TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy=FieldsValidator.class)
@Documented
public @interface FieldsValidate {
    String[] propertyNames();
    ComparisonMode matchMode() default ComparisonMode.EQUAL;
    boolean allowNull() default false;
    String message() default "";
    String dataType();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
   
   
    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List{
    	FieldsValidate[] value();
    }
    
   
}
