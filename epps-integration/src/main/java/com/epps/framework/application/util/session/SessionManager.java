package com.epps.framework.application.util.session;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.epps.framework.application.util.logger.ApplicationLogger;
import com.epps.framework.domain.exception.ApplicationException;
import com.epps.framework.domain.exception.ErrorCode;
import com.epps.framework.domain.exception.ResponseInfoType;
import com.epps.framework.notification.mail.interfaces.dto.UserProfileVO;

@Component
public class SessionManager {

	private static final ApplicationLogger logger = new ApplicationLogger(SessionManager.class);

	private static final ConcurrentHashMap<String, UserProfileVO> sessions = new ConcurrentHashMap<String, UserProfileVO>();
	
	public static Integer getTotalHttpSessionsCount() {
		logger.info("In getTotalHttpSessionsCount is "+ sessions.size());
		return sessions.size();
	}
	public static ConcurrentHashMap<String, UserProfileVO> getSessions() {
		logger.info("In getSessions is "+ sessions);
		return sessions;
	}
	public static UserProfileVO find(String token) {
		logger.info("In sessions is "+ sessions.get(token));
		return sessions.get(token);
	}
	
	public void sessionCreated(String token,  UserProfileVO userProfileVO) {
		sessions.put(token, userProfileVO);
		logger.info("Current sessionCreated  : "+ token + " After Session Create Count :"+ sessions.size());
	}
	public String getauthorizationHeader(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		return authorizationHeader;
	}

	public String getXPlatfrom(HttpServletRequest request) {
		String XPlatformHeader = request.getHeader("X-Platform");
		return XPlatformHeader;
	}

	public UserProfileVO getUserProfileVOfromtoken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		String XPlatformHeader = request.getHeader("X-Platform");
		UserProfileVO userProfileVO = new UserProfileVO();
		String token = null;
		if (authorizationHeader == null && XPlatformHeader == null) {
			throw new ApplicationException("BAD REQUEST", ErrorCode.VALIDATION_FAILED, ResponseInfoType.ERROR);
		} else {
			token = authorizationHeader.substring(7);
			userProfileVO = find(token);
		}
		return userProfileVO;
	}

	public void removeUserProfileVoFromCache(String token) {
		sessions.remove(token);
	}

}
