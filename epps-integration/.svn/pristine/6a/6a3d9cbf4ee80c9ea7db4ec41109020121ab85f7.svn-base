/*package com.epps.framework.interfaces.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.epps.framework.interfaces.cache.dtos.CacheOperationsDTO;
import com.epps.framework.interfaces.feignclients.fallback.EppsServicesFeigClientFallback;

//@FeignClient(serviceId="epps-services",fallback=EppsServicesFeigClientFallback.class,url="http://192.168.1.76:10091")
@FeignClient(serviceId="epps-services",fallback=EppsServicesFeigClientFallback.class)
public interface EppsServicesFeignClient {
	
	@PostMapping("/epps-services/cache/v1/cache")
	ResponseEntity<?> addDataInCache(@RequestBody CacheOperationsDTO<?> cache);
	
	@GetMapping("/epps-services/cache/v1/cache")
	Object getDataFromCache(@RequestParam("cacheKey") String cacheKey,@RequestParam("cacheStorageKey") String cacheStorageKey);

}

*/