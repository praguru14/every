package com.epps.framework.notification.mail;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.epps.framework.notification.mail.application.constant.NotificationConstants;
import com.epps.framework.notification.mail.application.event.annotation.EventAttribute;
import com.epps.framework.notification.mail.interfaces.dto.EcodeMasterDTO;
import com.epps.framework.notification.mail.interfaces.dto.NotificationConfigAttributeDto;

@Component("eventAttributeScaner")
public class EventAttributeScaner {
	
	//@Autowired private ICommonService commonServiceImpl;
	
	
	public List<NotificationConfigAttributeDto> getClassScannedWithSuperClasses(
			String classNameToGetScanned, List<NotificationConfigAttributeDto> notificationTemplateDTOs,String preAssociationPath, EcodeMasterDTO ecodeUserInfo, NotificationConfigAttributeDto objForTable, String eventAttributePurpose, String mtqrFlag){
		if (classNameToGetScanned != null) {
			Class<?> headerClass;
			try {
				headerClass = Class.forName(classNameToGetScanned);
				List<Class<?>> classList = new ArrayList<Class<?>>();
				getSuperClassesToGetScanned(headerClass, classList);
				for (Class<?> classToGetScaned : classList) {
					getClassScanned(classToGetScaned, notificationTemplateDTOs, preAssociationPath, ecodeUserInfo, objForTable, eventAttributePurpose, null, mtqrFlag);
				}
			} catch (ClassNotFoundException e) {
			}
		}
		return notificationTemplateDTOs;		
	}
	
	public List<NotificationConfigAttributeDto> getClassScanned(
			Class<?> classToGetScaned, List<NotificationConfigAttributeDto> notificationTemplateDTOs,String preAssociationPath, EcodeMasterDTO ecodeUserInfo, NotificationConfigAttributeDto objForTable, String eventAttributePurpose,String prefix, String mtqrFlag) {
		try {
			

					Field[] fields = classToGetScaned.getDeclaredFields();
					for (Field field : fields) {
						String prefixVal = null;
						// Iterating only that fields which has EventAttribute or
						// EventAttributes annotation.
						if (field.isAnnotationPresent((Class<? extends Annotation>) EventAttribute.class)) {
							EventAttribute eventAttr = field.getAnnotation(EventAttribute.class);
							/*
							 * Below line concat previous association path and
							 * field name so that we can reach to that field for
							 * eg. In PO Header to access item code
							 * preAssociationPath will come 'eppsPoDetails?.!['
							 * and we will go on appending eppsPoDetail fields
							 * to this path
							 */
							if(mtqrFlag != null && ((mtqrFlag.equals("T") && !eventAttr.isOnlyForMaster()) || mtqrFlag.equals("M") || mtqrFlag.equals("S"))){
								String fieldAssociationPath = preAssociationPath + field.getName();
								NotificationConfigAttributeDto notificationTemplateDTO = new NotificationConfigAttributeDto();
								/*
								 * endAssociationPath method appends ']' so as
								 * to close all opening '[' brackets.
								 */
								String finalAssociationpath = endAssociationPath(field, fieldAssociationPath);
								notificationTemplateDTO.setField(finalAssociationpath);
								if (eventAttr.fieldType().isEmpty()) {
									notificationTemplateDTO.setFieldType(field.getType().getSimpleName());
								} else {
									notificationTemplateDTO.setFieldType(eventAttr.fieldType());
								}

								if((eventAttr.purpose().equals(eventAttributePurpose) || eventAttr.purpose().equals(NotificationConstants.EVENT_ATTR_PURPOSE_BOTH)) && 
										null != eventAttr.displayName() && !eventAttr.displayName().isEmpty()){
									if(eventAttr.isToConcatPrefix() && prefix != null){
										notificationTemplateDTO.setDisplayName(prefix+NotificationConstants.SPACE+eventAttr.displayName());
									}else{
										if(eventAttr.isCountField() && eventAttributePurpose != null && eventAttributePurpose.equals(NotificationConstants.EVENT_ATTR_PURPOSE_CONDITION)){
											notificationTemplateDTO.setDisplayName(eventAttr.displayName().substring(0, eventAttr.displayName().indexOf(NotificationConstants.SPACE)));
										}else{
											notificationTemplateDTO.setDisplayName(eventAttr.displayName());
										}
									}
									notificationTemplateDTO.setPurpose(eventAttr.purpose());
									notificationTemplateDTO.setTableField(eventAttr.isTableField());
									notificationTemplateDTO.setCountField(eventAttr.isCountField());
									notificationTemplateDTO.setSumField(eventAttr.isSumField());
									notificationTemplateDTO.setSmsField(eventAttr.isSmsField());
									if (!eventAttr.predefinedValues().isEmpty()) {
										//TODO
										//notificationTemplateDTO.setPredefinedValues(commonServiceImpl.getPredefineValueForEntity(eventAttr.predefinedValues(), ecodeUserInfo));
									}
									notificationTemplateDTOs.add(notificationTemplateDTO);
								}
								if (objForTable != null && !(field.getGenericType() instanceof ParameterizedType) && getInstancesOfChar(notificationTemplateDTO.getField(), '!') == 1) {
									objForTable.getColumnsForTable().add(notificationTemplateDTO);
								}
								// Prefix is provided to differentiate between Customer and Consignee / Supplier and Ship To Party, as the entity used for both is same so we can't
								// mention different Display Names in entity.
								if(eventAttr.isToConcatPrefix() && !eventAttr.prefix().equals(NotificationConstants.EmptyString)){
									prefixVal = eventAttr.prefix();
								}
								if (isUserDefined(field)) {
									getClassScanned(field.getType(), notificationTemplateDTOs,fieldAssociationPath + "?.", ecodeUserInfo, objForTable, eventAttributePurpose, prefixVal, mtqrFlag);
								} else {
									Type type = field.getGenericType();
									if (type instanceof ParameterizedType) {
										ParameterizedType pType = (ParameterizedType) type;
										Type[] arr = pType.getActualTypeArguments();
										Class<?> subListClass = null;
										for (Type tp : arr) {
											subListClass = (Class<?>) tp;
										}
										if (eventAttr.fieldType().equals(NotificationConstants.EVENT_ATTR_FIELD_TYPE_TABLE) && fieldAssociationPath.indexOf(".") < 0) {
											notificationTemplateDTO.setColumnsForTable(new ArrayList<NotificationConfigAttributeDto>());
											objForTable = notificationTemplateDTO;
										}
										getClassScanned(subListClass, notificationTemplateDTOs,fieldAssociationPath + "?.![", ecodeUserInfo, objForTable,eventAttributePurpose,null, mtqrFlag);
									}
								}
							}
					}
			}
		} catch (Exception e) {
		}
		//notificationConfigAttributeDto.getFieldType().equalsIgnoreCase("Table") && ( null == notificationConfigAttributeDto.getColumnsForTable() ||notificationConfigAttributeDto.getColumnsForTable().size() == 0)
		notificationTemplateDTOs.removeIf(d -> d.getFieldType().equals("Table") && (null == d.getColumnsForTable() || d.getColumnsForTable().size() == 0));
		return notificationTemplateDTOs;
	}


	private String endAssociationPath(Field field,String path){
		if(!isUserDefined(field)){
			int instancesOfSqrBrckt = getInstancesOfChar(path,'[');
			StringBuilder finalAssociationPath = new StringBuilder(path);
			for (int i = 0; i < instancesOfSqrBrckt; i++) {
				finalAssociationPath.append(']');
			}
			return finalAssociationPath.toString();
		}
		return path;
	}

	/**
	 * @param field
	 * @return
	 */
	private boolean isUserDefined(Field field) {
		return !(field.getType().getName().startsWith("java.") || field.getType().isPrimitive());
	}
	
	private int getInstancesOfChar(String s,char c) {
		int counter = 0;
		for( int i=0; i<s.length(); i++ ) {
		    if( s.charAt(i) == c ) {
		        counter++;
		    } 
		}
		return counter;
	}
	
	private void getSuperClassesToGetScanned(Class<?> classToGetscan, List<Class<?>> superClassList){
		
		if(!classToGetscan.equals(Object.class)){
			superClassList.add(classToGetscan);
			getSuperClassesToGetScanned(classToGetscan.getSuperclass(), superClassList);
		}
	}

}
