package com.epps.framework.infrastructure.configurations;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
	
	@Value("${gatewayswaggerport}")
	String gatewayswaggerport;
	
	@Value("${gatewayhost}")
	String gatewayhost;
	
	@Value("${server.port}")
	String appswaggerport;
	
	@Value("${server.servlet.context-path}")
	String contextPath;
	
	@Bean
	public OpenAPI customConfiguration() throws UnknownHostException {
		// final String securitySchemeName = "bearerAuth";
		// final String securitySchemeName1 = "cookie";
		return new OpenAPI()
				//.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
		       // .components(
		         //   new Components()
		          //      .addSecuritySchemes(securitySchemeName,
		              //      new SecurityScheme()
		            //            .name(securitySchemeName)
		               //         .type(SecurityScheme.Type.HTTP)
		                 //       .scheme("bearer")
		                 //       .bearerFormat("JWT")
		                //    )
		              //  .addSecuritySchemes(securitySchemeName1, 
		               // 		new SecurityScheme()
		                //		.name(securitySchemeName1)
		                //		.type(SecurityScheme.Type.APIKEY)
		                //		.scheme("JSESSIONID")
		                //		.in(In.COOKIE)
		                //		)
		             // )
				.info(new Info()
						.version("1.0")
						.title("EPPS-INTEGRATION REST API Documentation")
						.description("Core Application API Docs of Epps-INTEGRATION")
						.contact(new Contact()
								.name("EPPS")
								.email("info@epps-erp.com")
								.url("http://www.epps-erp.com"))
			    .license(new License()
			    		.name("EppsLicense.1.0")
			    		))
		       .addServersItem(new Server().url("http://"+gatewayhost+":"+gatewayswaggerport+contextPath))
		       .addServersItem(new Server().url("http://localhost:"+appswaggerport+contextPath));
	}


}
