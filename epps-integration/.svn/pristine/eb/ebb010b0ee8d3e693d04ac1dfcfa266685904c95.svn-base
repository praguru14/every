package com.epps.module.master.personnel.interfaces.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(Include.NON_NULL)
@Schema(name="Command Master")
public class CommandMasterDTO implements Serializable{
	
	private static final long serialVersionUID = -4577027345019066689L;

	@Schema(description = "Serial Number", required = false)
    private Integer commandId;
	
	@Schema(description = "Code", required = false)
	private String commandCode;
	
	@Schema(description = " Name", required = false)
	private String commandName;
	
	@Schema(description = "isActive", required = false)
	private Integer isActive;
	
	@Schema(description = "Created By", required = false)
	private String createdBy;
	
	@Schema(description = "Created Date", required = false)
	private Date createdDate;
	
	@Schema(description = "Modified By", required = false)
	private String modifiedBy;
	
	@Schema(description = "Modified Date", required = false)
	private Date modifiedDate;
	
	@Schema(description = "ipAddress", required = false)
	private String ipAddress;
	
	@Schema(description = "Source App", required = false)
	private String sourceApp;
	
	private List<CommandMasterDTO> data;
	

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
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

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getSourceApp() {
		return sourceApp;
	}

	public void setSourceApp(String sourceApp) {
		this.sourceApp = sourceApp;
	}

	public Integer getCommandId() {
		return commandId;
	}

	public void setCommandId(Integer commandId) {
		this.commandId = commandId;
	}

	public String getCommandCode() {
		return commandCode;
	}

	public void setCommandCode(String commandCode) {
		this.commandCode = commandCode;
	}

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public List<CommandMasterDTO> getData() {
		return data;
	}

	public void setData(List<CommandMasterDTO> data) {
		this.data = data;
	}
}
