/**
 * 
 */
package com.epps.framework.interfaces.responses.dtos;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.epps.framework.application.constant.ApplicationConstants;
import com.epps.framework.application.util.date.DateHelper;
import com.epps.framework.domain.exception.ApplicationException;
import com.epps.framework.domain.exception.ResponseInfoType;
import com.epps.framework.interfaces.responses.ResponseEnvelope;
import com.epps.framework.interfaces.responses.ResponseInfo;

/**
 * @author Abhinesh
 *
 */
public class ResponseWrapper {
	
	/**
	 * 
	 * @param <T>
	 * @param uniqueId - uniqueId of request - shall be client ID - from which request received, if not generated internally. Can be null.
	 * @param timestamp shall be client request generated timestamp sent through request object - from which request received, if not generated internally.
	 * @param status
	 * @param details - this is to show list of success msg or error msg based on response .. Can be null.
	 * @param data  - To get List of an object
	 * @param links  - this is to be used as REST HATEOAS concept.
	 * @param operationMode Operation Mode based on method purpouse  like ApplicationConstants ->> OPERATION_MODE_INSERT, OPERATION_MODE_UPDATE, OPERATION_MODE_DELETE, OPERATION_MODE_READ.
	 * @param pagination - 
	 * @param httpStatusCode - HTTP status code based on Request Success Failure - as in HTTP Status Code. . Can be null. Default -> HttpStatus.OK
	 * @param dataCount - To getDataCount of list of objects
	 * 						Can be null. Default -> STATUS_SUCCESS ,  STATUS_FAILED;
	 * @param status - Internal EPPS Status Code to show to client like ApplicationConstants ->> OPERATION_MODE_INSERT, OPERATION_MODE_UPDATE, OPERATION_MODE_DELETE, OPERATION_MODE_READ.
	 * @return
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T>  ResponseEntity<?> getResponseEntityWithPagination(String uniqueId, String timestamp,String status,
			List<ResponseInfo> details,List<T> data,List<String> links,String operationMode, PaginationDTO pagination,HttpStatus httpStatusCode, Long dataCount )throws ApplicationException{
		
		//List <ResponseEnvelope> responseEnvelopeList = new ArrayList<ResponseEnvelope>(1);
		ResponseEnvelope responseEnvelope = null;
		
		if(null == uniqueId ) { UUID.randomUUID().toString();}
		if(null == timestamp) {timestamp = DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss);}
		if(null ==  operationMode) {operationMode = ApplicationConstants.OPERATION_MODE_READ;}
		if(null == httpStatusCode) {httpStatusCode =  HttpStatus.OK;}
		
		if(null != pagination && null != pagination.getStartIndex() && null != pagination.getRecordsPerPage()) {
			pagination.setTotalRecordCount(dataCount);
		}
		
		
		if(null == dataCount ||(null != dataCount && dataCount == 0)) {
			responseEnvelope = new ResponseEnvelope(UUID.randomUUID().toString(), timestamp, status, details, null, pagination, links, ApplicationConstants.OPERATION_MODE_INSERT);
			//responseEnvelopeList.add(responseEnvelope);
			return new ResponseEntity<>(responseEnvelope,httpStatusCode);
		}else {
			responseEnvelope = new ResponseEnvelope(UUID.randomUUID().toString(), timestamp, status, details, data, pagination, links, ApplicationConstants.OPERATION_MODE_INSERT);
			//responseEnvelopeList.add(responseEnvelope);
			return new ResponseEntity<>(responseEnvelope, httpStatusCode);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T>  ResponseEntity<?> getResponseEntityWithoutPagination(String uniqueId, String timestamp,String status,
			List<ResponseInfo> details,List<T> data,List<String> links,String operationMode, HttpStatus httpStatusCode)throws ApplicationException{
		
		//List <ResponseEnvelope> responseEnvelopeList = new ArrayList<ResponseEnvelope>(1);
		ResponseEnvelope responseEnvelope = null;
		
		if(null == uniqueId ) { UUID.randomUUID().toString();}
		if(null == timestamp) {timestamp = DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss);}
		if(null ==  operationMode) {operationMode = ApplicationConstants.OPERATION_MODE_READ;}
		if(null == httpStatusCode) {httpStatusCode =  HttpStatus.OK;}
		
		responseEnvelope = new ResponseEnvelope(uniqueId, timestamp, status,details, data, links, operationMode);
		//responseEnvelopeList.add(responseEnvelope);
		return new ResponseEntity<>(responseEnvelope, httpStatusCode);
	}
	
	public static <T> ResponseEntity<?> getEmptyResponseWithoutPaginationInResponseEnvelope(List<ResponseInfo> responseStatusList, String uniqueId, String timestamp, String status, List<String> links,
			String operationMode)throws ApplicationException {
		ResponseInfo responseStatus = new ResponseInfo(1035, ResponseInfoType.WARN.name(), null, ApplicationConstants.DATA_NOT_FOUND, null);
		responseStatusList.add(responseStatus);
		return ResponseWrapper.getResponseEntityWithoutPagination(uniqueId, timestamp, status, responseStatusList, responseStatusList, links, ApplicationConstants.OPERATION_MODE_READ, HttpStatus.NOT_FOUND);
	}
}
