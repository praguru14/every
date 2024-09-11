package com.epps.framework.interfaces.responses.dtos;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class PaginationSearchVO implements Serializable {
	
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
	private Integer currentPage = 1;
	
	private String[] searchField;
    private String[] searchString;
    private String[] searchOper;
    private String filters;
    private String groupOp;
    private String[] searchType;
    private String[] dtoField;
    
	public Integer getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
	public Integer getRecordsPerPage() {
		return recordsPerPage;
	}
	public void setRecordsPerPage(Integer recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
	public String getOrderByField() {
		return orderByField;
	}
	public void setOrderByField(String orderByField) {
		this.orderByField = orderByField;
	}
	public Long getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(Long totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	public Long getTotalNoPage() {
		return totalNoPage;
	}
	public void setTotalNoPage(Long totalNoPage) {
		this.totalNoPage = totalNoPage;
	}
	public String[] getSearchField() {
		return searchField;
	}
	public void setSearchField(String[] searchField) {
		this.searchField = searchField;
	}
	public String[] getSearchString() {
		return searchString;
	}
	public void setSearchString(String[] searchString) {
		this.searchString = searchString;
	}
	public String[] getSearchOper() {
		return searchOper;
	}
	public void setSearchOper(String[] searchOper) {
		this.searchOper = searchOper;
	}
	public String getFilters() {
		return filters;
	}
	public void setFilters(String filters) {
		this.filters = filters;
	}
	public String getGroupOp() {
		return groupOp;
	}
	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}
	public String[] getSearchType() {
		return searchType;
	}
	public void setSearchType(String[] searchType) {
		this.searchType = searchType;
	}
	public String[] getDtoField() {
		return dtoField;
	}
	public void setDtoField(String[] dtoField) {
		this.dtoField = dtoField;
	}
	/**
	 * @return the currentPage
	 */
	public Integer getCurrentPage() {
		return currentPage;
	}
	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
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
