/**
 * 
 */
package com.epps.framework.domain.validation;

/**
 * @author Abhinesh
 *
 */
public enum ComparisonMode {
	EQUAL, 
	EQUAL_IGNORE_CASE, 
	NOT_EQUAL, 
	NOT_EQUAL_IGNORE_CASE, 
	
	
	GRETAER_THAN,
	GRETAER_THAN_EQUAL_TO,
	LESS_THAN,
	LESS_THAN_EQUAL_TO,
	
	DATE1_LESS_THAN_DATE2,
	DATE1_GREATER_THAN_DATE2,
	DATE1_LESS_THAN_EQUAL_TO_DATE2,
	DATE1_GREATER_THAN_EQUAL_TO_DATE2
	
	
}
