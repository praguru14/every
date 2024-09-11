package com.epps.framework.interfaces.responses.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="GenericTree")
@JsonInclude(Include.NON_NULL)
public class GenericTreeDTO<E , V> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7724345772778561280L;

	@Schema(
			description="Tree level",
			required=true
			)
	private Integer level;
	
	@Schema(
			description="Id",
			required=true
			)
	private E id;
	
	@Schema(
			description="Parent Id",
			required=true
			)
	private V parentId;
	
	@Schema(
			description="Display Name",
			required=true
			)
	private String displayName;
	
	@Schema(
			description="Display sequence no",
			required=true
			)
	private Integer displaySeqNo;
	
	@Schema(
			description="Lowest level flag",
			required=true
			)
	private String lowestLevelFlag;

	
	/**
	 * @return the level
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * @return the id
	 */
	public E getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(E id) {
		this.id = id;
	}

	/**
	 * @return the parentId
	 */
	public V getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(V parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the displaySeqNo
	 */
	public Integer getDisplaySeqNo() {
		return displaySeqNo;
	}

	/**
	 * @param displaySeqNo the displaySeqNo to set
	 */
	public void setDisplaySeqNo(Integer displaySeqNo) {
		this.displaySeqNo = displaySeqNo;
	}

	public String getLowestLevelFlag() {
		return lowestLevelFlag;
	}

	public void setLowestLevelFlag(String lowestLevelFlag) {
		this.lowestLevelFlag = lowestLevelFlag;
	}

	
}
