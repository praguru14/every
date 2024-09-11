package com.epps.framework.application.util.locale;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component("localeMessageUtility")
public class LocaleMessageUtility {
    
	@Autowired
	private MessageSource messageService;

	/**
	 * For given key it will load message from property files
	 * 
	 * @param key
	 * @return {@link String}
	 */
	public String getMessage(final String key) {
		final String msg = messageService.getMessage(key, null, new Locale(LocaleContextHolder.getLocale().toString().split("_")[0]));
		return msg;
	}

	/**
	 * For given key it will load message from property files, also replacing
	 * the properties
	 * 
	 * @param key
	 * @param params
	 * @return {@link String}
	 */
	public String getMessage(final String key, final Object[] params) {
		final String msg = this.messageService.getMessage(key, params,new Locale(LocaleContextHolder.getLocale().toString().split("_")[0]));
		return msg;
	}

	/**
	 * For given error code it will load message from property files
	 * 
	 * @param key
	 * @return {@link String}
	 */
	public String getErrorMessage(final int key) {
		final String errorMsg = this.messageService.getMessage(key + "", null,new Locale(LocaleContextHolder.getLocale().toString().split("_")[0]));
		return errorMsg;
	}

}
