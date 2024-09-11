/**
 * 
 */
package com.epps.framework.infrastructure.configurations;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author abhinesh
 *
 */
@Configuration
public class MessageSouceConfig {
	
	@Bean("messageSource")
	@Primary
	public MessageSource getMessageSource() {
		ReloadableResourceBundleMessageSource messageSource= new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(
				"classpath:/localeMessages/error",
				"classpath:/localeMessages/message",
				"classpath:/locale/screenlabel"
				);
		messageSource.setCacheSeconds(100);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setUseCodeAsDefaultMessage(true);

		return messageSource;
	}
	

}
