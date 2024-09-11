package com.epps.framework.notification.mail.application.spel;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.epps.framework.application.util.date.DateHelper;

public class SpelUtility {
	
	public static  <T extends Number, U extends Collection<T>> BigDecimal sum(Object obj){
		if(obj instanceof List){
			ArrayList<Number> numbers = new ArrayList<Number>();
			converMultiDimentionalToSingle((ArrayList)obj,numbers);
			BigDecimal sum = BigDecimal.ZERO;
		    for ( Number number : numbers ) {
		        sum = sum.add(new BigDecimal(number.toString()));
		    }
		    return sum;
		}
		return new BigDecimal(obj.toString());
		
	}
	
	public static  <T extends Number, U extends Collection<T>> BigDecimal max(Object obj){
		if(obj instanceof List){
			ArrayList<Number> numbers = new ArrayList<Number>();
			converMultiDimentionalToSingle((ArrayList)obj,numbers);
			BigDecimal max = BigDecimal.ZERO;
		    for ( Number number : numbers ) {
		        max = max.max(new BigDecimal(number.toString()));
		    }
		    return max;
		}
		return new BigDecimal(obj.toString());
		
	}
	
	public static  <T extends Number, U extends Collection<T>> BigDecimal min(Object obj){
		if(obj instanceof List){
			ArrayList<Number> numbers = new ArrayList<Number>();
			converMultiDimentionalToSingle((ArrayList)obj,numbers);
			BigDecimal sum = new BigDecimal(numbers.get(0).toString());
		    for ( Number number : numbers ) {
		        sum = sum.min(new BigDecimal(number.toString()));
		    }
		    return sum;
		}
		return new BigDecimal(obj.toString());
		
	}
	
	public static int count(Object obj){
		if(obj instanceof List){
			ArrayList<Object> numbers = new ArrayList<Object>();
			converMultiDimentionalToSingle((ArrayList)obj,numbers);
			return numbers.size();
		}
		return 1;
		
	}
	
	private static boolean isElementsPresent(Object obj) {
		if(obj instanceof Boolean)
			return ((Boolean) obj).booleanValue();
		int noOfElements = count(obj);
		if(noOfElements > 0)
			return true;
		return false;
		
}

	private static void converMultiDimentionalToSingle(ArrayList<Object> objects, ArrayList oneDimentional) {
			for (Object object : objects) {
				if(object instanceof List){
					converMultiDimentionalToSingle((ArrayList)object,oneDimentional);
				}else{
					oneDimentional.add(object);
				}
			}
	}
	
	private static Date getDateObj(String dateString) throws ParseException {
		return DateHelper.getDateFromString(dateString, DateHelper.EPPS_DEFAULT_DATE_FORMAT);
}
	
	private static Object getValue(Object obj) {
		return obj;
	}

}
