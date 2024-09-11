package com.epps.framework.domain.validation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

public class FieldsValidator implements ConstraintValidator<FieldsValidate, Object> {

    private String[] propertyNames;
    private ComparisonMode comparisonMode;
    private boolean allowNull;
    private String dataType;

    @Override
    public void initialize(FieldsValidate constraintAnnotation) {
        this.propertyNames = constraintAnnotation.propertyNames();
        this.comparisonMode = constraintAnnotation.matchMode();
        this.allowNull = constraintAnnotation.allowNull();
        this.dataType = constraintAnnotation.dataType();
    }

    @Override
    public boolean isValid(Object target, ConstraintValidatorContext context) {
    	switch (dataType) {
    		case "INTEGER":
	        	return isValidInteger(target, context);
	        case "STRING":
	        	return isValidString(target, context);
	        case "DOUBLE":
	        	return isValidDouble(target, context);
	        case "DATE":
	        	return isValidDate(target, context);
			default:
				break;
        }
		
    	return false;
    }
    
    
	private boolean isValidString(Object target, ConstraintValidatorContext context){
    	
    	 boolean isValid = true;
         List<String> propertyValues = new ArrayList<String> (propertyNames.length);
         for(int i=0; i<propertyNames.length; i++) {
             String propertyValue = ConstraintValidatorHelper.getPropertyValue(String.class, propertyNames[i], target);
             if(propertyValue == null) {
                 if(!allowNull) {
                     isValid = false;
                     break;
                 }
             } else {
                 propertyValues.add(propertyValue);
             }
         }

         if(isValid) {
             isValid = ConstraintValidatorHelper.isValidString(propertyValues, comparisonMode);
         }

         if (!isValid) {
           generateMessageAtIsNotValid(context);
         }    

         return isValid;
    	
    }
    
    private boolean isValidInteger(Object target, ConstraintValidatorContext context){
    	
   	 boolean isValid = true;
        List<Integer> propertyValues = new ArrayList<Integer> (propertyNames.length);
        for(int i=0; i<propertyNames.length; i++) {
            Integer propertyValue = ConstraintValidatorHelper.getPropertyValue(Integer.class, propertyNames[i], target);
            if(propertyValue == null) {
                if(!allowNull) {
                    isValid = false;
                    break;
                }
            } else {
                propertyValues.add(propertyValue);
            }
        }

        if(isValid) {
            isValid = ConstraintValidatorHelper.isValidInteger(propertyValues, comparisonMode);
        }

        if (!isValid) {
          generateMessageAtIsNotValid(context);
        }    
        return isValid;
   }

    private boolean isValidDouble(Object target, ConstraintValidatorContext context){
    	
      	 boolean isValid = true;
           List<Double> propertyValues = new ArrayList<Double> (propertyNames.length);
           for(int i=0; i<propertyNames.length; i++) {
               Double propertyValue = ConstraintValidatorHelper.getPropertyValue(Double.class, propertyNames[i], target);
               if(propertyValue == null) {
                   if(!allowNull) {
                       isValid = false;
                       break;
                   }
               } else {
                   propertyValues.add(propertyValue);
               }
           }

           if(isValid) {
               isValid = ConstraintValidatorHelper.isValidDouble(propertyValues, comparisonMode);
           }

           if (!isValid) {
             generateMessageAtIsNotValid(context);
           }    
           return isValid;
    }
    
    private boolean isValidDate(Object target, ConstraintValidatorContext context) {
     	 boolean isValid = true;
         List<Date> propertyValues = new ArrayList<Date> (propertyNames.length);
         for(int i=0; i<propertyNames.length; i++) {
             Date propertyValue = ConstraintValidatorHelper.getPropertyValue(java.util.Date.class, propertyNames[i], target);
             if(propertyValue == null) {
                 if(!allowNull) {
                     isValid = false;
                     break;
                 }
             } else {
                 propertyValues.add(propertyValue);
             }
         }

         if(isValid) {
             isValid = ConstraintValidatorHelper.isValidDate(propertyValues, comparisonMode);
         }

         if (!isValid) {
           generateMessageAtIsNotValid(context);
         }    
         return isValid;
	}
    
	private void generateMessageAtIsNotValid(ConstraintValidatorContext context) {
		String message = context.getDefaultConstraintMessageTemplate();
          message = (message.isEmpty()) ?  ConstraintValidatorHelper.resolveMessage(propertyNames, comparisonMode) : message;

          context.disableDefaultConstraintViolation();
          ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(message);
          violationBuilder.addConstraintViolation();
	}
}
