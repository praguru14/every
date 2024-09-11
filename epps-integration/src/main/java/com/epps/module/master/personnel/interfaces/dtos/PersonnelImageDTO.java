/**
 * 
 */
package com.epps.module.master.personnel.interfaces.dtos;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author Shubham Goliwar
 *
 */

@JsonInclude(Include.NON_NULL)
public class PersonnelImageDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7431307853362507868L;
    
	@Schema(description="Serial Number",required=false)
	private Integer serialNumber;
    
	@Schema(description="Personnel Serial Number",required=false)
	private Integer personnelSerialNumber;
    
	@Schema(description="Personal Image",required=false)
	private byte[] personalImage;
	
	@Schema(description="Remarks",required=false)
	private String remarks;
    
	@Schema(description="Source App",required=false)
	private String sourceApp;
    
	@Schema(description="Modified By",required=false)
	private String modifiedBy;
    
	@Schema(description="Modified Date",required=false)
	private Date modifiedDate;
    
	@Schema(description="Created By",required=false)
	private String createdBy;
	
	@Schema(description="Created Date",required=false)
	private Date createdDate;
	
	@Schema(description="ipAddress",required=false)
	private String ipAddress;
    
	@Schema(description="Personnel Number",required=false)
	private String personnelNumber;
	
	private String imageName;
	
	private String contentType;
	
	private Long imageSize;
	
	private String imageUrl;

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Integer getPersonnelSerialNumber() {
		return personnelSerialNumber;
	}

	public void setPersonnelSerialNumber(Integer personnelSerialNumber) {
		this.personnelSerialNumber = personnelSerialNumber;
	}

	public byte[] getPersonalImage() {
		return personalImage;
	}

	public void setPersonalImage(byte[] personalImage) {
		this.personalImage = personalImage;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSourceApp() {
		return sourceApp;
	}

	public void setSourceApp(String sourceApp) {
		this.sourceApp = sourceApp;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getPersonnelNumber() {
		return personnelNumber;
	}

	public void setPersonnelNumber(String personnelNumber) {
		this.personnelNumber = personnelNumber;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getImageSize() {
		return imageSize;
	}

	public void setImageSize(Long imageSize) {
		this.imageSize = imageSize;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	
}
