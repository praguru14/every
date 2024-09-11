/**
 * 
 */
package com.epps.framework.domain.validation;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.epps.framework.application.util.date.DateHelper;


/**
 * @author Homeair
 *
 */
public abstract class ConstraintValidatorHelper {

public static <T> T getPropertyValue(Class<T> requiredType, String propertyName, Object instance) {
        if(requiredType == null) {
            throw new IllegalArgumentException("Invalid argument. requiredType must NOT be null!");
        }
        if(propertyName == null) {
            throw new IllegalArgumentException("Invalid argument. PropertyName must NOT be null!");
        }
        if(instance == null) {
            throw new IllegalArgumentException("Invalid argument. Object instance must NOT be null!");
        }
        T returnValue = null;
        try {
            PropertyDescriptor descriptor = new PropertyDescriptor(propertyName, instance.getClass());
            Method readMethod = descriptor.getReadMethod();
            if(readMethod == null) {
                throw new IllegalStateException("Property '" + propertyName + "' of " + instance.getClass().getName() + " is NOT readable!");
            }
            if(requiredType.isAssignableFrom(readMethod.getReturnType())) {
                try {
                    Object propertyValue = readMethod.invoke(instance);
                    returnValue = requiredType.cast(propertyValue);
                } catch (Exception e) {
                    e.printStackTrace(); // unable to invoke readMethod
                }
            }
        } catch (IntrospectionException e) {
            throw new IllegalArgumentException("Property '" + propertyName + "' is NOT defined in " + instance.getClass().getName() + "!", e);
        }
        return returnValue; 
    }

    public static boolean isValidString(Collection<String> propertyValues, ComparisonMode comparisonMode) {
        boolean ignoreCase = false;
        switch (comparisonMode) {
        case EQUAL_IGNORE_CASE:
        	
        case NOT_EQUAL_IGNORE_CASE:
            ignoreCase = true;
		default:
			break;
        }

        List<String> values = new ArrayList<String> (propertyValues.size());
        for(String propertyValue : propertyValues) {
            if(ignoreCase) {
                values.add(propertyValue.toLowerCase());
            } else {
                values.add(propertyValue);
            }
        }

        switch (comparisonMode) {
        case EQUAL:
        	
        	
        case EQUAL_IGNORE_CASE:
            Set<String> uniqueValues = new LinkedHashSet<String> (values);
            return uniqueValues.size() == 1 ? true : false;
            
        case NOT_EQUAL:
        	
        case NOT_EQUAL_IGNORE_CASE:
            Set<String> allValues = new LinkedHashSet<String> (values);
            return allValues.size() == values.size() ? true : false;
		default:
			break;
        }

        return true;
    }
    
    protected static boolean isValidInteger(List<Integer> propertyValues, ComparisonMode comparisonMode) {
         List<Integer> values = new ArrayList<Integer> (propertyValues.size());
         for(Integer propertyValue : propertyValues) {
               values.add(propertyValue);
             
         }

         switch (comparisonMode) {
	         case EQUAL:
	         
	         case GRETAER_THAN:
	             List<Integer> uniqueValues = new LinkedList<Integer> (values);
	             return uniqueValues.get(0) > uniqueValues.get(1) ? true :false;
	            	 
	         
	         case GRETAER_THAN_EQUAL_TO:
	        	 List<Integer> uniqueValuesEqualTo = new LinkedList<Integer> (values);
	        	 return uniqueValuesEqualTo.get(0) >= uniqueValuesEqualTo.get(1) ? true :false;
	             
	         
	         case LESS_THAN:
	        	 List<Integer> uniqueValuesLessEqual = new LinkedList<Integer> (values);
	        	 return uniqueValuesLessEqual.get(0) < uniqueValuesLessEqual.get(1) ? true :false;
	        
	         case LESS_THAN_EQUAL_TO:
	        	 List<Integer> uniqueValuesLessEqualTo = new LinkedList<Integer> (values);
	        	 return uniqueValuesLessEqualTo.get(0) <= uniqueValuesLessEqualTo.get(1) ? true :false;
		default:
			break;
	           
         }

         return true;
	}
    
    protected static boolean isValidDouble(List<Double> propertyValues, ComparisonMode comparisonMode) {
        List<Double> values = new ArrayList<Double> (propertyValues.size());
        for(Double propertyValue : propertyValues) {
              values.add(propertyValue);
            
        }

        switch (comparisonMode) {
	         case EQUAL:
	         
	         case GRETAER_THAN:
	             List<Double> uniqueValues = new LinkedList<Double> (values);
	             return uniqueValues.get(0) > uniqueValues.get(1) ? true :false;
	            	 
	         
	         case GRETAER_THAN_EQUAL_TO:
	        	 List<Double> uniqueValuesEqualTo = new LinkedList<Double> (values);
	        	 return uniqueValuesEqualTo.get(0) >= uniqueValuesEqualTo.get(1) ? true :false;
	             
	         
	         case LESS_THAN:
	        	 List<Double> uniqueValuesLessEqual = new LinkedList<Double> (values);
	        	 return uniqueValuesLessEqual.get(0) < uniqueValuesLessEqual.get(1) ? true :false;
	        
	         case LESS_THAN_EQUAL_TO:
	        	 List<Double> uniqueValuesLessEqualTo = new LinkedList<Double> (values);
	        	 return uniqueValuesLessEqualTo.get(0) <= uniqueValuesLessEqualTo.get(1) ? true :false;
		default:
			break;
	           
        }

        return true;
	}
    
    public static boolean isValidDate(List<Date> propertyValues, ComparisonMode comparisonMode) {
    	 List<Date> values = new ArrayList<Date> (propertyValues.size());
         for(Date propertyValue : propertyValues) {
               values.add(propertyValue);
             
         }
    	
         switch (comparisonMode) {
 	         case EQUAL:
 	         
 	         case DATE1_LESS_THAN_DATE2:
 	             Long dateDiffLessValues = DateHelper.getDateDifference(values.get(0), values.get(0));
 	            return dateDiffLessValues < 0L ? true :false;
 	            	 
 	         
 	         case DATE1_GREATER_THAN_DATE2:
 	        	 Long dateDiffGrtrValues = DateHelper.getDateDifference(values.get(0), values.get(0));
  	            return dateDiffGrtrValues > 0L ? true :false;
 	             
 	         
 	         case DATE1_LESS_THAN_EQUAL_TO_DATE2:
 	        	 Long dateDiffLessEqualValues = DateHelper.getDateDifference(values.get(0), values.get(0));
  	            return dateDiffLessEqualValues <= 0L ? true :false;
 	        
 	        case DATE1_GREATER_THAN_EQUAL_TO_DATE2:
 	        	 Long dateDiffGrtrEqualValues = DateHelper.getDateDifference(values.get(0), values.get(0));
  	            return dateDiffGrtrEqualValues >= 0L ? true :false;
		default:
			break;
         }

         return true;

	}
    
    
    public static String resolveMessage(String[] propertyNames, ComparisonMode comparisonMode) {
        StringBuffer buffer = concatPropertyNames(propertyNames);
        buffer.append(" must");
        switch(comparisonMode) {
        case EQUAL:
        case EQUAL_IGNORE_CASE:
            buffer.append(" be equal");
            break;
        case NOT_EQUAL:
        case NOT_EQUAL_IGNORE_CASE:
            buffer.append(" not be equal");
            break;
		default:
			break;
        }
        buffer.append('.');
        return buffer.toString();
    }

    private static StringBuffer concatPropertyNames(String[] propertyNames) {
        //TODO improve concating algorithm
        StringBuffer buffer = new StringBuffer();
        buffer.append('[');
        for(String propertyName : propertyNames) {
            char firstChar = Character.toUpperCase(propertyName.charAt(0));
            buffer.append(firstChar);
            buffer.append(propertyName.substring(1));
            buffer.append(", ");
        }
        buffer.delete(buffer.length()-2, buffer.length());
        buffer.append("]");
        return buffer;
    }
}
