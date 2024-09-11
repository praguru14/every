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
@Schema(name="Search")
public class SearchDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3687509841163398932L;
	
	private String[] searchField;
    private String[] searchString;
    private String[] searchOper;
    private String filters;
    private String groupOp;
    private String[] searchType;
    private String[] dtoField;
    
	/**
	 * @return the searchField
	 */
	public String[] getSearchField() {
		return searchField;
	}
	/**
	 * @param searchField the searchField to set
	 */
	public void setSearchField(String[] searchField) {
		this.searchField = searchField;
	}
	/**
	 * @return the searchString
	 */
	public String[] getSearchString() {
		return searchString;
	}
	/**
	 * @param searchString the searchString to set
	 */
	public void setSearchString(String[] searchString) {
		this.searchString = searchString;
	}
	/**
	 * @return the searchOper
	 */
	public String[] getSearchOper() {
		return searchOper;
	}
	/**
	 * @param searchOper the searchOper to set
	 */
	public void setSearchOper(String[] searchOper) {
		this.searchOper = searchOper;
	}
	/**
	 * @return the filters
	 */
	public String getFilters() {
		return filters;
	}
	/**
	 * @param filters the filters to set
	 */
	public void setFilters(String filters) {
		this.filters = filters;
	}
	/**
	 * @return the groupOp
	 */
	public String getGroupOp() {
		return groupOp;
	}
	/**
	 * @param groupOp the groupOp to set
	 */
	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}
	/**
	 * @return the searchType
	 */
	public String[] getSearchType() {
		return searchType;
	}
	/**
	 * @param searchType the searchType to set
	 */
	public void setSearchType(String[] searchType) {
		this.searchType = searchType;
	}
	/**
	 * @return the dtoField
	 */
	public String[] getDtoField() {
		return dtoField;
	}
	/**
	 * @param dtoField the dtoField to set
	 */
	public void setDtoField(String[] dtoField) {
		this.dtoField = dtoField;
	}

    

	
}
