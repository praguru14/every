/*package com.epps.framework.interfaces.feignclients.fallback;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.epps.framework.application.constant.ApplicationConstants;
import com.epps.framework.domain.exception.ResponseInfoType;
import com.epps.framework.interfaces.feignclients.EppsFasFeignClient;
import com.epps.framework.interfaces.responses.ResponseInfo;
import com.epps.framework.interfaces.responses.dtos.ResponseWrapper;

@Component
public class EppsFasFeignClientFallback implements EppsFasFeignClient {

	@Override
	public ResponseEntity<?> getFinancialYearData(String authorizationHeader,String XPlatformheader,Integer companyCode, Integer defaultYn) {
		List<Object> emptyList = new ArrayList<Object>(1);
		List<ResponseInfo> responseStatusList = new ArrayList<ResponseInfo>(1);
		ResponseInfo responseStatus = new ResponseInfo(1031, ResponseInfoType.INFO.name(), "Service is down, returning empty response", ApplicationConstants.STATUS_SUCCESS, null);
		responseStatusList.add(responseStatus);
		return ResponseWrapper.getResponseEntityWithoutPagination(null, null,ApplicationConstants.STATUS_SUCCESS, responseStatusList, emptyList, null, ApplicationConstants.OPERATION_MODE_READ,HttpStatus.OK);
	}

	
}

*/