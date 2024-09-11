/**
 * 
 */
package com.epps.framework.interfaces.responses;

import java.io.Serializable;
import java.util.List;

import com.epps.framework.interfaces.responses.dtos.PaginationDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 
 * @author Abhinesh
 *
 * @param <T>
 * @param <V>
 */

@JsonInclude(Include.NON_NULL)
public class ResponseEnvelope<T> implements Serializable {

	private static final long serialVersionUID = -2918022830208124530L;

	@Schema(description = "UniqueId of request - shall be client ID - from which request received, if not generated internally. Can be null.", required = false)
	private String uniqueId;
	
	@Schema(description = "timestamp shall be client request generated timestamp sent through request object - from which request received, if not generated internally.", required = false)
	private String timestamp;
	
	@Schema(description = "Status of the Request/Response SUCCESS or FAILED", required = false)
	private String status;
	
	@Schema(description = "To get List of an object , Resquest / Response Data is mapped", required = false)
	private List<T> data;
	
	@Schema(description = "Operation Mode based on method purpose ->> OPERATION_MODE_INSERT (A), OPERATION_MODE_UPDATE (U), OPERATION_MODE_DELETE (D), OPERATION_MODE_READ. (R)", required = false)
	private String operationMode;
	
	@Schema(description = "Conatins the Paging Information, Used for GET REQUEST ", required = false)
	private PaginationDTO pagination;
	
	@Hidden
	@JsonIgnore
	@Schema(description = " Used for HATEOAS Concept for smartly navigate further with given response url", required = false)
	private List<String> links;
	
	@Schema(description = "Contains the meta information for Response Generated From System", required = false)
	private List<ResponseInfo> responseInfo;
	
	
	
	/**
	 * @description - Envelope used to send data of pagination
	 * @param uniqueId - uniqueId of request - shall be client ID - from which request received, if not generated internally. Can be null.
	 * @param timestamp shall be client request generated timestamp sent through request object - from which request received, if not generated internally.
	 * @param data  - To get List of an object
	 * @param pagination
	 * @param links  - this is to be used as REST HATEOAS concept.
	 * @param operationMode Operation Mode based on method purpose ->> OPERATION_MODE_INSERT, OPERATION_MODE_UPDATE, OPERATION_MODE_DELETE, OPERATION_MODE_READ.
	 * @param status - Internal EPPS Status Code to show to client Can be null.Default -> STATUS_SUCCESS ,  STATUS_FAILED;
	 * @param details this is to show list of success msg or error msg based on response .. Can be null.
	 */
	
	public ResponseEnvelope(String uniqueId, String timestamp,String status,List<ResponseInfo> responseStatus, List<T> data,PaginationDTO pagination,List<String> links, String operationMode) {
		super();
		this.uniqueId = uniqueId;
		this.timestamp = timestamp;
		this.status = status;
		this.responseInfo = responseStatus;
		this.data = data;
		this.pagination = pagination;
		this.links = links;
		this.operationMode = operationMode;
	}
	 
	/**
	 * @description - Envelope used to send without data with pagination
	 * @param uniqueId - uniqueId of request - shall be client ID - from which request received, if not generated internally. Can be null.
	 * @param timestamp shall be client request generated timestamp sent through request object - from which request received, if not generated internally.
	 * @param status - Internal EPPS Status Code to show to client like ->> Can be null.Default -> STATUS_SUCCESS ,  STATUS_FAILED;.
	 * @param responseInfo this is to show list of success msg or error msg based on response .. Can be null.
	 * @param pagination - To add pagination data
	 * @param links  - this is to be used as REST HATEOAS concept.
	 * @param operationMode Operation Mode based on method purpose  like ->> OPERATION_MODE_INSERT, OPERATION_MODE_UPDATE, OPERATION_MODE_DELETE, OPERATION_MODE_READ.
	 */
	
	  public ResponseEnvelope(String uniqueId, String timestamp,String status,List<ResponseInfo> responseStatus,PaginationDTO pagination,List<String> links, String operationMode) { 
		  super(); 
		  this.uniqueId = uniqueId; 
		  this.timestamp = timestamp;
		  this.status = status;
		  this.responseInfo = responseStatus; 
		  this.pagination =pagination;
		  this.links = links; 
		  this.operationMode = operationMode; 
	  }
	 
	
	
	
	/**
	 * @description - Envelope used to send Data without pagination
	 * @param uniqueId - uniqueId of request - shall be client ID - from which request received, if not generated internally. Can be null.
	 * @param timestamp shall be client request generated timestamp sent through request object - from which request received, if not generated internally.
	 * @param status - Internal EPPS Status Code to show to client like ->> Can be null.Default -> STATUS_SUCCESS ,  STATUS_FAILED;.
	 * @param responseInfo this is to show list of success msg or error msg based on response .. Can be null.
	 * @param data  - To get List of an object
	 * @param links  - this is to be used as REST HATEOAS concept.
	 * @param operationMode Operation Mode based on method purpose  like ->> OPERATION_MODE_INSERT, OPERATION_MODE_UPDATE, OPERATION_MODE_DELETE, OPERATION_MODE_READ.
	 */
	public ResponseEnvelope(String uniqueId, String timestamp,String status, List<ResponseInfo> responseStatus, List<T> data,List<String> links, String operationMode) {
		super();
		this.uniqueId = uniqueId;
		this.timestamp = timestamp;
		this.status = status;
		this.responseInfo = responseStatus;
		this.data = data;
		this.links = links;
		this.operationMode = operationMode;
	}
	
	
	/**
	 * Default Constructor
	 */
	public ResponseEnvelope() {
		super();
	}

	/**
	 * @return the uniqueId
	 */
	public String getUniqueId() {
		return uniqueId;
	}

	/**
	 * @param uniqueId the uniqueId to set
	 */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the responseInfo
	 */
	public List<ResponseInfo> getResponseInfo() {
		return responseInfo;
	}

	/**
	 * @param responseInfo the responseInfo to set
	 */
	public void setResponseInfo(List<ResponseInfo> responseInfo) {
		this.responseInfo = responseInfo;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the data
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<T> data) {
		this.data = data;
	}

	/**
	 * @return the links
	 */
	public List<String> getLinks() {
		return links;
	}

	/**
	 * @param links the links to set
	 */
	public void setLinks(List<String> links) {
		this.links = links;
	}

	/**
	 * @return the operationMode
	 */
	public String getOperationMode() {
		return operationMode;
	}

	/**
	 * @param operationMode the operationMode to set
	 */
	public void setOperationMode(String operationMode) {
		this.operationMode = operationMode;
	}

	/**
	 * @return the pagination
	 */
	public PaginationDTO getPagination() {
		return pagination;
	}

	/**
	 * @param pagination the pagination to set
	 */
	public void setPagination(PaginationDTO pagination) {
		this.pagination = pagination;
	}
	
}

