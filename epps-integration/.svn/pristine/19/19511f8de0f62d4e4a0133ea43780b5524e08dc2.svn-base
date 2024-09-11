package com.epps.framework.application.api.beans;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.property.access.internal.PropertyAccessStrategyBasicImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyChainedImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyFieldImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyMapImpl;
import org.hibernate.property.access.spi.Setter;
import org.hibernate.transform.ResultTransformer;

import com.epps.framework.application.util.date.DateHelper;
import com.epps.framework.domain.exception.ApplicationException;
import com.epps.framework.domain.exception.ResponseInfoType;
import com.epps.framework.infrastructure.annotation.DateParseToStringField;

public  class EppsAliasToBeanTransformer implements ResultTransformer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Class<?> resultClass;
	private boolean isInitialized;
	private String[] aliases;
	private Setter[] setters;
	private String keyField;
	private Map<Object , List<Object>> returnMap;
	
	public EppsAliasToBeanTransformer(Class<?> resultClass,String keyField) {
		if ( resultClass == null ) {
			throw new IllegalArgumentException( "resultClass cannot be null" );
		}
		isInitialized = false;
		this.resultClass = resultClass;
		this.keyField = keyField;
		returnMap = new LinkedHashMap<>();
	}
	
	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object result;

		try {
			
			if ( ! isInitialized ) {
				initialize( aliases );
			}
			else {
				check( aliases );
			}
			result = resultClass.newInstance();

			for ( int i = 0; i < aliases.length; i++ ) {
				if ( setters[i] != null ) {
					
					if(aliases[i].equals(keyField)){
						List<Object>  s = returnMap.get(tuple[i]);
						if(s == null){
							s = new ArrayList<>();
							s.add(result);
							returnMap.put(tuple[i], s);
						}else{
							s.add(result);
						}
					}
					
					Annotation dateParseToStringField [] = result.getClass().getDeclaredField(aliases[i]).getAnnotations();
					if(dateParseToStringField.length > 0 && dateParseToStringField[0].annotationType().equals(DateParseToStringField.class)){
						String strFieldName =((DateParseToStringField)dateParseToStringField[0]).stringDateField();
						Field field = result.getClass().getDeclaredField(strFieldName);
						field.setAccessible(true);
						if(tuple[i] == null || tuple[i] instanceof Date){
							if(tuple[i] != null){
								field.set(result, DateHelper.getFormattedDateString((Date)tuple[i], DateHelper.EPPS_DEFAULT_DATE_FORMAT));
							}
						}else{
							throw new ApplicationException("Field annotated is not Date type", null, ResponseInfoType.ERROR);
						}
					}
					setters[i].set( result, tuple[i], null );
				}
			}
		}
		catch ( InstantiationException e ) {
			throw new HibernateException( "Could not instantiate resultclass: " + resultClass.getName() );
		}
		catch ( IllegalAccessException e ) {
			throw new HibernateException( "Could not instantiate resultclass: " + resultClass.getName() );
		}  catch (SecurityException e) {
			throw new ApplicationException(e.getMessage(), null, ResponseInfoType.ERROR);
		} catch (NoSuchFieldException e) {
			throw new ApplicationException("No Such Field Exception",null, ResponseInfoType.ERROR);
		}

		return result;
	}
	
	private void initialize(String[] aliases) {
		 PropertyAccessStrategyChainedImpl propertyAccessStrategy = new PropertyAccessStrategyChainedImpl(
                 PropertyAccessStrategyBasicImpl.INSTANCE,
                 PropertyAccessStrategyFieldImpl.INSTANCE,
                 PropertyAccessStrategyMapImpl.INSTANCE
);
		this.aliases = new String[ aliases.length ];
		setters = new Setter[ aliases.length ];
		for ( int i = 0; i < aliases.length; i++ ) {
			String alias = aliases[ i ];
			if ( alias != null ) {
				this.aliases[ i ] = alias;
				setters[ i ] =  propertyAccessStrategy.buildPropertyAccess( resultClass, alias ).getSetter();
			}
		}
		isInitialized = true;
	}

	private void check(String[] aliases) {
		if ( ! Arrays.equals( aliases, this.aliases ) ) {
			throw new IllegalStateException(
					"aliases are different from what is cached; aliases=" + Arrays.asList( aliases ) +
							" cached=" + Arrays.asList( this.aliases ) );
		}
	}

	@Override
	public List transformList(List collection) {
		if(keyField == null || keyField.isEmpty()){
			return collection;
		}else{
			List<Map<Object, List<Object>>> s =new ArrayList<>();
			s.add(returnMap);
			return s;
		}
	}

}
