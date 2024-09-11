package com.epps.framework.notification.mail.application.queryService;

import java.util.List;

import org.json.JSONObject;

public interface IEmailNotificationQueryService {

	List<Object> getEmailNotificationData(JSONObject inputRequiredJson);

}
