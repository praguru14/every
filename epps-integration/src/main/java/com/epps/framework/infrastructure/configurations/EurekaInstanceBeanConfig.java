/**
 * 
 */
package com.epps.framework.infrastructure.configurations;

import org.springframework.context.annotation.Configuration;

/**
 * @author abhinesh
 *
 */
@Configuration
public class EurekaInstanceBeanConfig {
	/**
	@Bean
	@Profile("devAws")
	public EurekaInstanceConfigBean eurekadevAwsInstanceConfig(InetUtils inetUtils) {
	  EurekaInstanceConfigBean b = new EurekaInstanceConfigBean(inetUtils);
	  AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
	  b.setDataCenterInfo(info);
	  return b;
	}
	
	@Bean
	@Profile("prod")
	public EurekaInstanceConfigBean eurekaProdInstanceConfig(InetUtils inetUtils) {
	  EurekaInstanceConfigBean b = new EurekaInstanceConfigBean(inetUtils);
	  AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
	  b.setDataCenterInfo(info);
	  return b;
	}
	
	@Bean
	@Profile("qa")
	public EurekaInstanceConfigBean eurekaqaInstanceConfig(InetUtils inetUtils) {
	  EurekaInstanceConfigBean b = new EurekaInstanceConfigBean(inetUtils);
	  AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
	  b.setDataCenterInfo(info);
	  return b;
	}
	
	@Bean
	@Profile("staging")
	public EurekaInstanceConfigBean eurekaStagingInstanceConfig(InetUtils inetUtils) {
	  EurekaInstanceConfigBean b = new EurekaInstanceConfigBean(inetUtils);
	  AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
	  b.setDataCenterInfo(info);
	  return b;
	}
	
	*/
}
