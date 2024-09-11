package com.epps.framework.application.api.beans;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

public class EppsAliasToBeanTransformerForPivoteData implements ResultTransformer, Serializable {

	

		private static final long serialVersionUID = 1L;
		
		private final Class<?> resultClass;
		private boolean isInitialized;
		private String[] aliases;
		private Setter[] setters;
		private String pivoteField;
		private List<String> aggregatesFields;
		private List<String> fixedColumns;
		private Map<String,Object> map;
		private Object columnTotal;
		private Map<String,Object> totalMap;
		private Map<String,Integer> aliasesMap;
		private Map<String, String> keyValuMap;
		public EppsAliasToBeanTransformerForPivoteData(Class<?> resultClass,List<String> aggregatesFields,String pivoteField,List<String> fixedColumns) {
			if ( resultClass == null ) {
				throw new IllegalArgumentException( "resultClass cannot be null" );
			}
			isInitialized = false;
			this.resultClass = resultClass;
			this.aggregatesFields = aggregatesFields;
			this.pivoteField = pivoteField;
			this.fixedColumns = fixedColumns;
			totalMap = new LinkedHashMap<>();
			map = new LinkedHashMap<>();
			aliasesMap = new HashMap<>();
			try {
				columnTotal = resultClass.newInstance();
			} catch (InstantiationException e) {
				
			} catch (IllegalAccessException e) {
				
			}
			keyValuMap = new LinkedHashMap<>();
			
		}
		
		@Override
		public Object transformTuple(Object[] tuple, String[] aliases) {
			Object result;
			Object resultInner;
			Object total = null;
			Class<?> class1;
			Boolean flag = false;
			try {
				
				if ( ! isInitialized ) {
					initialize( aliases );
				}
				else {
					check( aliases );
				}
				Map<String, Object> pivoteMap;
				String key = getKeyForMap(tuple, aliases);
				if(map.containsKey(key)){
					
					
					result = map.get(key);
					Field field = result.getClass().getDeclaredField("pivotMap");
					
					
					Type type = field.getGenericType();
					
					ParameterizedType  parameter = (ParameterizedType) type;
					
					type = parameter.getActualTypeArguments()[1];
					
					class1 = type.getClass();
					
					
					
					field.setAccessible(true);
					pivoteMap = (Map<String, Object>) field.get(result);
					
					total = pivoteMap.get("total");
//					pivoteMap.put("total", total);
					
				}else{
					result = resultClass.newInstance();
					map.put(key, result);
					pivoteMap = new LinkedHashMap<>();
					Field field = result.getClass().getDeclaredField("pivotMap");
					field.setAccessible(true);
					
					Type type = field.getGenericType();
					
					ParameterizedType  parameter = (ParameterizedType) type;
					
					type = parameter.getActualTypeArguments()[1];
					
					class1 = type.getClass();
					field.set(result, pivoteMap);
//					total = class1.newInstance();
					total = resultClass.newInstance();
//					pivoteMap.put("total", total);
				}
				Object verticalTotal = null;
				resultInner = resultClass.newInstance();
				for (int i = 0; i < aliases.length; i++ ) {
					if (setters[i] != null) {
						
						if(aliases[i].equals(pivoteField)){
							if(pivoteMap.containsKey(tuple[i].toString().replace(" ", "_").replace(".", ""))){
								flag = true;
								
								resultInner = pivoteMap.get(tuple[i].toString().replace(" ", "_").replace(".", ""));
							}else{
								pivoteMap.put(tuple[i].toString().replace(" ", "_").toString().replace(".", ""), resultInner);
								keyValuMap.put(tuple[i].toString().replace(" ", "_").toString().replace(".", ""), tuple[i].toString());
							}
							
							if(totalMap.containsKey(tuple[i].toString().replace(" ", "_").replace(".", "")+"total")){
								verticalTotal = totalMap.get(tuple[i].toString().replace(" ", "_").replace(".", "")+"total");
							}else{
								verticalTotal = resultClass.newInstance();
								totalMap.put(tuple[i].toString().replace(" ", "_").replace(".", "")+"total",verticalTotal );
							}
						}
						if(aggregatesFields.contains(aliases[i])){
//							aliasesMap.get(pivoteField);
//							Double v = (Double)tuple[i];
							doVerticalTotal(verticalTotal, tuple[i], i);
							doHorizontalTotal(total, tuple[i], i);
							
							Field fieldAgrigate = resultInner.getClass().getDeclaredField(aliases[i]);
							fieldAgrigate.setAccessible(true);
							Double valueAggrigate = (Double)fieldAgrigate.get(resultInner) == null ? 0.0 : (Double)fieldAgrigate.get(resultInner);
							
							if(flag || valueAggrigate > 0){ 
								flag = false;
								tuple[i] = (Double)(tuple[i] == null ? 0.0 : tuple[i]) + valueAggrigate;
								setters[i].set(resultInner, tuple[i], null);
							}else{
								setters[i].set(resultInner, tuple[i], null);
							}
						}else{
							
							setters[i].set(result, tuple[i], null);
						}
					}
				}
				
				if(pivoteMap.containsKey("total")){
					pivoteMap.remove("total");
				}
				pivoteMap.put("total", total);
			}
			catch ( InstantiationException e ) {
				throw new HibernateException( "Could not instantiate resultclass: " + resultClass.getName() );
			}
			catch ( IllegalAccessException e ) {
				throw new HibernateException( "Could not instantiate resultclass: " + resultClass.getName() );
			} catch (NoSuchFieldException e) {
				throw new HibernateException( "Could not instantiate resultclass: " + resultClass.getName() );
			} catch (SecurityException e) {
				throw new HibernateException( "Could not instantiate resultclass: " + resultClass.getName() );
			}
			return result;
		}
		
		
		private String getKeyForMap(Object[] tuples,String [] alise){
			String mapKey = "";
			for (String fixedColumn : fixedColumns) {
				mapKey = mapKey + (tuples[aliasesMap.get(fixedColumn)] ==null ? "_" : tuples[aliasesMap.get(fixedColumn)].toString().replace(" ", "_") +"_");
			}
			return mapKey;
		}
		
		private void doHorizontalTotal(Object total,Object tuppleValu,Integer index) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, InstantiationException{
//			Object total = pivoteMap.get("total");
			Field field = total.getClass().getDeclaredField(aliases[index]);
			field.setAccessible(true);
			if(field.getType().equals(Double.class)){
				Double totalValue = (Double)field.get(total);
				if(totalValue == null){
					totalValue = (Double) (tuppleValu == null ? 0.0d : tuppleValu);
				}else{
					totalValue = totalValue + (tuppleValu == null ? 0.0d : (Double) tuppleValu);
				}
				field.set(total, totalValue);
			}
		}
		
		private void doVerticalTotal(Object verticalTotal,Object tupleValue,Integer index) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
			
			Field field = verticalTotal.getClass().getDeclaredField(aliases[index]);
			field.setAccessible(true);
			if(field.getType().equals(Double.class)){
				Double totalValue = (Double)field.get(verticalTotal);
				if(totalValue == null){
					totalValue = (Double) tupleValue;
				}else{
					totalValue = totalValue + (Double) (tupleValue == null ? 0.0d : tupleValue);
				}
				field.set(verticalTotal, totalValue);
			}
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
					aliasesMap.put(alias, i);
					this.aliases[ i ] = alias;
					setters[ i ] = propertyAccessStrategy.buildPropertyAccess( resultClass, alias ).getSetter();
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
			
			Field field;
			try {
				field = columnTotal.getClass().getDeclaredField("pivotMap");
				field.setAccessible(true);
				field.set(columnTotal,totalMap);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				
			}
			List<Object> list = new ArrayList<Object>();
			list.addAll(map.values());
			list.add(columnTotal);
			
			
			try {
				field = list.get(0).getClass().getDeclaredField("keyValuMap");
				field.setAccessible(true);
				field.set(list.get(0),keyValuMap);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				
			}
			
			return list;
		}


	
	
}
