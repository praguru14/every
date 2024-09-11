package com.epps.framework.interfaces.responses.dtos;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Schema(name="Pagination")
public class PaginationDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 284573639132080013L;
	private Integer startIndex;
	private Integer recordsPerPage = 50;
	private String orderByField;
	private String orderBy;
	private Long totalRecordCount;
	private Long totalNoPage;
	
	/**
	 * Additional data to pass from same ajax request.
	 */
	private String additionalDataString;
		
	private Integer currentpage = 1;


	/**
	 * @return the startIndex
	 */
	public Integer getStartIndex() {
		return startIndex;
	}

	/**
	 * @param startIndex the startIndex to set
	 */
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	/**
	 * @return the recordsPerPage
	 */
	public Integer getRecordsPerPage() {
		return recordsPerPage;
	}

	/**
	 * @param recordsPerPage the recordsPerPage to set
	 */
	public void setRecordsPerPage(Integer recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	/**
	 * @return the orderByField
	 */
	public String getOrderByField() {
		return orderByField;
	}

	/**
	 * @param orderByField the orderByField to set
	 */
	public void setOrderByField(String orderByField) {
		this.orderByField = orderByField;
	}

	

	/**
	 * @return the totalRecordCount
	 */
	public Long getTotalRecordCount() {
		return totalRecordCount;
	}

	/**
	 * @param totalRecordCount the totalRecordCount to set
	 */
	public void setTotalRecordCount(Long totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	/**
	 * @return the totalNoPage
	 */
	public Long getTotalNoPage() {
		return totalNoPage;
	}

	/**
	 * @param totalNoPage the totalNoPage to set
	 */
	public void setTotalNoPage(Long totalNoPage) {
		this.totalNoPage = totalNoPage;
	}

	/**
	 * @return the additionalDataString
	 */
	public String getAdditionalDataString() {
		return additionalDataString;
	}

	/**
	 * @param additionalDataString the additionalDataString to set
	 */
	public void setAdditionalDataString(String additionalDataString) {
		this.additionalDataString = additionalDataString;
	}

	/**
	 * @return the currentpage
	 */
	public Integer getCurrentpage() {
		return currentpage;
	}

	/**
	 * @param currentpage the currentpage to set
	 */
	public void setCurrentpage(Integer currentpage) {
		this.currentpage = currentpage;
	}

	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}



	



}
