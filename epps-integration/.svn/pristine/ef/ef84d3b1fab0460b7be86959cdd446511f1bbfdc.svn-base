package com.epps.framework.infrastructure.configurations;

import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.epps.framework.infrastructure.interceptors.AuditTrailInterceptor;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.epps.*")
@RefreshScope
public class HibernateConf {

	@Value("${connection-timeout}")
	Integer connectiontimeout;

	@Value("${minimum-idle}")
	Integer minimumidle;

	@Value("${maximum-pool-size}")
	Integer maximumpoolsize;

	@Value("${idle-timeout}")
	Integer idletimeout;

	@Value("${jdbc-driverClassName}")
	String driverClassName;

	@Value("${jdbc-url}")
	String url;

	@Value("${jdbc-username}")
	String username;

	@Value("${jdbc-password}")
	String password;

	@Autowired
	private AuditTrailInterceptor auditTrailInterceptor;

	@Bean
	public LocalSessionFactoryBean sessionFactory() throws NamingException {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("com.epps.*");
		sessionFactory.setHibernateProperties(hibernateProperties());
		sessionFactory.setEntityInterceptor(auditTrailInterceptor);
		return sessionFactory;
	}

	@Bean
	@RefreshScope
	public DataSource dataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setJdbcUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setConnectionTimeout(connectiontimeout);
		dataSource.setMinimumIdle(minimumidle);
		dataSource.setMaximumPoolSize(maximumpoolsize);
		dataSource.setIdleTimeout(idletimeout);
		dataSource.setPoolName("epps-ayu");
		return dataSource;
	}

	@Bean
	@RefreshScope
	public PlatformTransactionManager hibernateTransactionManager() throws NamingException {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

	private final Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL82Dialect");
		hibernateProperties.setProperty("hibernate.jdbc.lob.non_contextual_creation", "true");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		return hibernateProperties;
	}
}
