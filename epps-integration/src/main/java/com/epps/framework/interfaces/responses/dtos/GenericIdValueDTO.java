/**
 * 
 */
package com.epps.framework.interfaces.responses.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author abhinesh
 *
 */
@Schema(name="GenericIdValue")
@JsonInclude(Include.NON_NULL)
public class GenericIdValueDTO<E , V> implements Serializable {
		
	private static final long serialVersionUID = 1L;

	private E id;
	
	private V value;
	
	private String code;
	
	private Integer anotherId;
	
	private String anotherValue;
	
	private Long parentID;
	
	private Double doubleValue;

	private String gstInType;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Schema( type  = "string", format="date")
	private Date dateField;
	
	private Integer otherId;
	
	private List<Map<E,V>> genericMap;
	
	public GenericIdValueDTO() {
		super();
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
	 * @return the value
	 */
	public V getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(V value) {
		this.value = value;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the anotherId
	 */
	public Integer getAnotherId() {
		return anotherId;
	}

	/**
	 * @param anotherId the anotherId to set
	 */
	public void setAnotherId(Integer anotherId) {
		this.anotherId = anotherId;
	}

	/**
	 * @return the anotherValue
	 */
	public String getAnotherValue() {
		return anotherValue;
	}

	/**
	 * @param anotherValue the anotherValue to set
	 */
	public void setAnotherValue(String anotherValue) {
		this.anotherValue = anotherValue;
	}

	/**
	 * @return the parentID
	 */
	public Long getParentID() {
		return parentID;
	}

	/**
	 * @param parentID the parentID to set
	 */
	public void setParentID(Long parentID) {
		this.parentID = parentID;
	}

	/**
	 * @return the doubleValue
	 */
	public Double getDoubleValue() {
		return doubleValue;
	}

	/**
	 * @param doubleValue the doubleValue to set
	 */
	public void setDoubleValue(Double doubleValue) {
		this.doubleValue = doubleValue;
	}

	/**
	 * @return the gstInType
	 */
	public String getGstInType() {
		return gstInType;
	}

	/**
	 * @param gstInType the gstInType to set
	 */
	public void setGstInType(String gstInType) {
		this.gstInType = gstInType;
	}
	
	@Override
	public int hashCode() {
		return id ==null ? 0 : id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GenericIdValueDTO<?, ?>) {
            return id == null ? false : id.equals(((GenericIdValueDTO<E, V>)obj).getId());
        }
        return false;
	}

	public Date getDateField() {
		return dateField;
	}

	public void setDateField(Date dateField) {
		this.dateField = dateField;
	}

	public Integer getOtherId() {
		return otherId;
	}

	public void setOtherId(Integer otherId) {
		this.otherId = otherId;
	}

	public List<Map<E, V>> getGenericMap() {
		return genericMap;
	}

	public void setGenericMap(List<Map<E, V>> genericMap) {
		this.genericMap = genericMap;
	}

}
