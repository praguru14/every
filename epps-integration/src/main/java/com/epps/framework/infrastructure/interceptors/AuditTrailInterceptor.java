package com.epps.framework.infrastructure.interceptors;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.type.Type;
import org.hibernate.EmptyInterceptor;
import org.springframework.stereotype.Component;

import com.epps.framework.application.constant.ApplicationConstants;
import com.epps.framework.infrastructure.model.entities.EppsBaseEntity;
import com.epps.framework.infrastructure.model.entities.CreatorBaseEntity;

@Component
public class AuditTrailInterceptor extends EmptyInterceptor {

	/**
	 * Generated serial version id
	 */
	private static final long serialVersionUID = 1006440937097128151L;

	@Override
	public boolean onFlushDirty(Object entity, Serializable id,Object[] currentState, Object[] previousState,
								String[] propertyNames, Type[] types) {

		if (entity instanceof EppsBaseEntity) {
			EppsBaseEntity baseEntity = (EppsBaseEntity) entity;
			if(baseEntity.getUpdatedBy()!=null && !baseEntity.getUpdatedBy().equals(ApplicationConstants.IsEmptyString)){
				for (int i = 0; i < propertyNames.length; i++) {
					if ("updatedDate".equals(propertyNames[i])) {
						currentState[i] = new Date();
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {

		if (entity instanceof EppsBaseEntity || entity instanceof CreatorBaseEntity) {
			for (int i = 0; i < propertyNames.length; i++) {
				if ("createdDate".equals(propertyNames[i])) {
					state[i] = new Date();
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public String onPrepareStatement(String sql) {
		String query= super.onPrepareStatement(sql);
		return query.replaceAll("epps_temp\\.", "");
	}
}
