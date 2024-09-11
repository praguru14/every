package com.epps.framework.interfaces.controller;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.epps.framework.application.util.logger.ApplicationLogger;

/**
 * @author 
 * @since 30/May/2019
 * @param <T>
 */
@RestController
@RequestMapping(value="/restTemplateCommonController/")
public class RestTemplateController{

	private static final ApplicationLogger LOGGER = new ApplicationLogger(RestTemplateController.class);

	/**@param url
	 * @param token TODO
	 * @param <T>
	 * @param <K>
	 * @Description :
	 *                 Common  method used for get the data using restTemplate.
	 * @return
	 * @throws KeyStoreException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	
	public <K, V> ResponseEntity<Object> callRestTemplate(HttpServletRequest request,String url,String isRequestType,String token) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		LOGGER.info("In callRestTemplate method : url is : "+url);

		ResponseEntity<Object> restResponse = null;
		
		TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
	    SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
	    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

	    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
	            .register("https", sslsf).register("http", new PlainConnectionSocketFactory()).build();

	    BasicHttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager(
	            socketFactoryRegistry);
	    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
	            .setConnectionManager(connectionManager).build();

	    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

	    RestTemplate restTemplate = new RestTemplate(requestFactory);
			
			HttpHeaders requestHeaders = new HttpHeaders();
			LOGGER.info("In callRestTemplate method : Create the RestTemplate & HttpHeaders Object : ");
			
			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
			
			if (null!=token) {
				requestHeaders.add("Authorization", "Bearer " + token);
			}
			
			/*MultiValueMap<String, InstallationUpdateDtlDTO> body = new LinkedMultiValueMap<String, InstallationUpdateDtlDTO>();     
			HttpEntity<?> requestEntity = new HttpEntity<Object>(body, requestHeaders);*/
			
			HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", requestHeaders);
			requestEntity = new HttpEntity<String>("parameters", requestHeaders);
			
			
			LOGGER.info("In RestTemplateController : callRestTemplate Method: Given the call from WEB to WSMS is : url  "+url);
			try {
				if("GET".equalsIgnoreCase(isRequestType)) {
					restResponse  = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Object>() {});
				}else {
					restResponse  = restTemplate.exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Object>() {});
				}
			}catch(HttpClientErrorException clientError) {
				
				LOGGER.info("In RestTemplateController : callRestTemplate Method: Given the call from WEB to WSMS is : url  "+clientError.getMessage());
			}
		//}
//		LOGGER.info("In RestTemplateController : callRestTemplate Method: Given the call from WEB to WSMS is : url  "+restResponse);
		return restResponse;
	}
	
	public <K, V> ResponseEntity<?> callRestTemplateWithBody(String url,String isRequestType,String token, List<?> body) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		LOGGER.info("In callRestTemplate method : url is : "+url);

		ResponseEntity<?> restResponse = null;

		TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
	    SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
	    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

	    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
	            .register("https", sslsf).register("http", new PlainConnectionSocketFactory()).build();

	    BasicHttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager(
	            socketFactoryRegistry);
	    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
	            .setConnectionManager(connectionManager).build();

	    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

	    RestTemplate restTemplate = new RestTemplate(requestFactory);
					
			HttpHeaders requestHeaders = new HttpHeaders();
			LOGGER.info("In callRestTemplate method : Create the RestTemplate & HttpHeaders Object : ");
			
			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
			
			if (null!=token) {
				requestHeaders.add("Authorization", "Bearer " + token);
			}
		
			HttpEntity<?> requestEntity = new HttpEntity<Object>(body.get(0), requestHeaders);

			try {
				if("GET".equalsIgnoreCase(isRequestType)) {
					restResponse  = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<?>>() {});
				}else {
					restResponse  = restTemplate.exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<?>>() {});		
				}
			}catch(HttpClientErrorException clientError) {	
				LOGGER.info("In RestTemplateController : callRestTemplate Method: Given the call from WSMS to WEB is : url  "+clientError.getMessage());
			}
		LOGGER.info("In RestTemplateController : callRestTemplate Method: Given the call from WSMS to WEB is : url  "+restResponse);
		return restResponse;
	}
}
