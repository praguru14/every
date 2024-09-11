package com.epps.framework.notification.mail.application.event.listener;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.epps.framework.domain.exception.ApplicationException;
import com.epps.framework.domain.exception.ProcedureException;
import com.epps.framework.notification.mail.application.event.EntityEvent;


@Component
public class EntityEventListener {

	@Autowired
	private EventProcessor notificationService;
	
	@Async
	public void handleSendEvent(EntityEvent entityEvent) throws NoSuchFieldException, SecurityException, ApplicationException, ProcedureException, InvocationTargetException, ParseException, ClassNotFoundException {
		notificationService.processEvent(entityEvent);
		
	}

	public EventProcessor getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(EventProcessor notificationService) {
		this.notificationService = notificationService;
	}
}
