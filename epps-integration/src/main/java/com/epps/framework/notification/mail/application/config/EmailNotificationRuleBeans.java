/**
 * 
 */
package com.epps.framework.notification.mail.application.config;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.support.processor.idempotent.MemoryIdempotentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Abhinesh
 *
 */

/*<aop:aspectj-autoproxy />*/

@Configuration
@EnableAspectJAutoProxy 

public class EmailNotificationRuleBeans {
	 	
		/*<bean id="mailDedupQueue" class="org.apache.camel.processor.idempotent.MemoryIdempotentRepository">
				<property name="cacheSize" value="20" />
		</bean>*/
	 	@Bean
	 	public MemoryIdempotentRepository MailDedupQueue() {
	 		Map<String, Object> properties = new HashMap<String , Object>(1);
	 		properties.put("cacheSize",20);
	 		return new MemoryIdempotentRepository(properties);
	 	}
		
		/*<bean id="smsRcvdDedupQueue" class="org.apache.camel.processor.idempotent.MemoryIdempotentRepository">
				<property name="cacheSize" value="20" />
		</bean>*/
	 	@Bean
	 	public MemoryIdempotentRepository SmsRcvdDedupQueue() {
	 		Map<String, Object> properties = new HashMap<String , Object>(1);
	 		properties.put("cacheSize",20);
	 		return new MemoryIdempotentRepository(properties);
	 	}
	 	
	 	//TODO
		/*<bean id="rssProcessor" class="com.epps.domain.processor.RssProcessor">
				<property name="dedupQueue" ref="smsRcvdDedupQueue" />
		</bean>*/
	 	/*@Bean
	 	public RssProcessor RssProcessor() {
	 		Map<String, Object> properties = new HashMap<String , Object>(1);
	 		properties.put("dedupQueue","smsRcvdDedupQueue");
	 		return new RssProcessor();
	 	}*/
	 	
	 	
	 	
		/*<bean id="decimalFormater" class="java.text.DecimalFormat">
				<constructor-arg type="String" value="###,###,###,###,##0.00"></constructor-arg>
		</bean>*/
	 	@Bean
	 	public DecimalFormat DecimalFormater() {
	 		String pattern = "###,###,###,###,##0.00";
	 		return new DecimalFormat(pattern);
	 	}
}
