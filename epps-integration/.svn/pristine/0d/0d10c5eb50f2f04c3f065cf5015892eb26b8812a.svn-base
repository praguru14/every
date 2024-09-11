package com.epps.framework.notification.mail.application.queryService.impl;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.epps.framework.notification.mail.application.queryService.IEmailNotificationQueryService;
import com.epps.framework.notification.mail.infrastructure.repositories.dao.IEmailNotificationDao;

@Service("emailNotificationQueryServiceImpl")
public class EmailNotificationQueryServiceImpl implements IEmailNotificationQueryService{

	@Autowired
	private IEmailNotificationDao emailNotificationDaoImpl;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Object> getEmailNotificationData(JSONObject inputRequiredJson) {
		return	emailNotificationDaoImpl.getEmailNotificationData(inputRequiredJson);
	}

}
