/*package com.epps.framework.interfaces.feignclients.fallback;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.epps.framework.application.constant.ApplicationConstants;
import com.epps.framework.domain.exception.ResponseInfoType;
import com.epps.framework.interfaces.cache.dtos.CacheOperationsDTO;
import com.epps.framework.interfaces.feignclients.EppsServicesFeignClient;
import com.epps.framework.interfaces.responses.ResponseInfo;
import com.epps.framework.interfaces.responses.dtos.ResponseWrapper;

@Component
public class EppsServicesFeigClientFallback implements EppsServicesFeignClient{

	
	@Override
	public ResponseEntity<?> addDataInCache(CacheOperationsDTO cache) {
		List<Object> emptyList = new ArrayList<Object>(1);
		List<ResponseInfo> responseStatusList = new ArrayList<ResponseInfo>(1);
		ResponseInfo responseStatus = new ResponseInfo(1031, ResponseInfoType.INFO.name(), "Service is down, returning empty response", ApplicationConstants.STATUS_SUCCESS, null);
		responseStatusList.add(responseStatus);
		return ResponseWrapper.getResponseEntityWithoutPagination(null, null,ApplicationConstants.STATUS_SUCCESS, responseStatusList, emptyList, null, ApplicationConstants.OPERATION_MODE_READ,HttpStatus.OK);
	}

	@Override
	public Object getDataFromCache(String cacheKey, String cacheStorageKey) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
*/