package com.epps.framework.notification.mail.infrastructure.repositories.dao;

import java.util.List;

import org.json.JSONObject;

import com.epps.framework.common.domain.model.entities.EppsModuleMaster;
import com.epps.framework.common.domain.model.entities.EppsModuleMasterPK;
import com.epps.framework.infrastructure.repositories.generic.dao.IGenericDAO;

public interface IEmailNotificationDao extends IGenericDAO<EppsModuleMaster, EppsModuleMasterPK>{

	List<Object> getEmailNotificationData(JSONObject inputRequiredJson);

}
