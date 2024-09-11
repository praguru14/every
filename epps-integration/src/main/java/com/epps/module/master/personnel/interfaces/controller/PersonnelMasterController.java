package com.epps.module.master.personnel.interfaces.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epps.framework.application.constant.ApplicationConstants;
import com.epps.framework.application.util.logger.ApplicationLogger;
import com.epps.framework.domain.exception.ApplicationException;
import com.epps.framework.domain.exception.ResponseInfoType;
import com.epps.framework.interfaces.responses.ResponseInfo;
import com.epps.framework.interfaces.responses.dtos.ResponseWrapper;
import com.epps.module.master.personnel.application.services.query.IHrcdfPersonnelMaster;
import com.epps.module.master.personnel.interfaces.dtos.PersonnelDetail;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author Shubham Goliwar
 *
 */

@RestController
@Validated
@RequestMapping("/personnel")
@Tag(name = "Personnel Master API", description = "Resources to handle Personnel Master API")
public class PersonnelMasterController {

	private static final ApplicationLogger LOGGER = new ApplicationLogger(PersonnelMasterController.class);
	
	@Autowired
	private IHrcdfPersonnelMaster hrcdfPersonnelMasterImpl;

	
	@GetMapping("/v1/info")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful Operation",content=@Content(array=@ArraySchema(schema=@Schema(implementation=PersonnelDetail.class)))),
			@ApiResponse(responseCode = "400", description = "Bad Request",content=@Content(examples= @ExampleObject(value="400:Bad Request"))),
			@ApiResponse(responseCode = "404", description = "Records not found",content=@Content(examples= @ExampleObject(value="404:Records not found")))
	})
	@Operation(summary = "Resource to read Personnel Detail from HRCDF", description = "Resource to read Personnel Detail from HRCDF", tags="Personnel Master API")
	public ResponseEntity<?> getPersonnelInfo(HttpServletRequest request,
			@Parameter(name = "personnels", description = "Provide personnels", required = false) @RequestParam(value="personnels",required=false) List<String> personnels,
			@Parameter(name = "unitTypes", description = "Provide unitTypes", required = false) @RequestParam(value="unitTypes",required=false) List<String> unitTypes,
			@Parameter(name = "activeFlag", description = "Provide activeFlag", required = false) @RequestParam(value="activeFlag",required=false ,defaultValue = "1") Integer activeFlag,
			@Parameter(name = "unitCodes", description = "Provide unitCodes", required = false) @RequestParam(value="unitCodes",required=false) List<String> unitCodes,
			@Parameter(name = "commandCodes", description = "Provide commandCodes", required = false) @RequestParam(value="commandCodes",required=false) List<String> commandCodes,
			@Parameter(name = "userTypes", description = "Provide userTypes", required = false) @RequestParam(value="userTypes",required=false) List<String> userTypes,
			@Parameter(name = "designationRequiredFlag", description = "Provide designationRequiredFlag", required = false) @RequestParam(value="designationRequiredFlag",required=false) Integer designationRequiredFlag,
			@Parameter(name = "profileImageYn", description = "Provide profileImageYn", required = false) @RequestParam(value="profileImageYn",required=false) Integer profileImageYn
			) throws JSONException, Exception{
		LOGGER.info("Calling method  getPersonnelInfo in PersonnelMasterController");
		List<ResponseInfo> responseStatusList = new ArrayList<>(1);
		List<PersonnelDetail> list  = hrcdfPersonnelMasterImpl.getPersonnelInfoFromHrcdf(personnels, unitTypes, activeFlag, unitCodes, commandCodes, userTypes, designationRequiredFlag,profileImageYn);
		if(null != list && !list.isEmpty()){
			ResponseInfo responseStatus = new ResponseInfo(1031, ResponseInfoType.INFO.name(), null, ApplicationConstants.STATUS_SUCCESS, null);
			responseStatusList.add(responseStatus);
			return ResponseWrapper.getResponseEntityWithoutPagination(null, null, ApplicationConstants.STATUS_SUCCESS, responseStatusList, list, null, ApplicationConstants.OPERATION_MODE_READ, HttpStatus.OK);
		}else {
			throw new ApplicationException("User Not found in HRCDF Application", null, null);
		}
		
	}
}
