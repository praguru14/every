package com.epps.framework.application.threads;

import org.hibernate.Session;

public class CustomThreadLocal {
	public static final ThreadLocal<Session> userThreadLocal = new ThreadLocal<Session>();

	public static void set(Session session) {
		userThreadLocal.set(session);
	}

	public static void unset() {
		userThreadLocal.remove();
	}

	public static Session get() {
		return userThreadLocal.get();
	}
}
