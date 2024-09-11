/*package com.epps.framework.interfaces.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.epps.framework.interfaces.feignclients.fallback.EppsFasFeignClientFallback;


@FeignClient(serviceId="epps-fas",fallback=EppsFasFeignClientFallback.class)
public interface EppsFasFeignClient {
	
	@GetMapping("/epps-fas/financeMaster/v1/financialYear")
	ResponseEntity<?> getFinancialYearData(@RequestHeader("Authorization") String authorizationHeader,@RequestHeader("X-Platform") String XPlatformheader,@RequestParam("companyCode") Integer companyCode,@RequestParam("defaultYn") Integer defaultYn);
	
}
*/