package com.epps.module.master.personnel.application.services.query.impl;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.epps.framework.interfaces.controller.RestTemplateController;
import com.epps.framework.interfaces.responses.dtos.PaginationDTO;
import com.epps.framework.interfaces.responses.dtos.SearchDTO;
import com.epps.module.master.personnel.application.services.query.IHrcdfPersonnelMaster;
import com.epps.module.master.personnel.interfaces.dtos.CommandMasterDTO;
import com.epps.module.master.personnel.interfaces.dtos.MastersCommonQueryParameterObject;
import com.epps.module.master.personnel.interfaces.dtos.PersonnelDetail;
import com.epps.module.master.personnel.interfaces.dtos.RankMasterDTO;
import com.epps.module.master.personnel.interfaces.dtos.UnitDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("hrcdfPersonnelMasterImpl")
public class HrcdfPersonnelMasterImpl  implements IHrcdfPersonnelMaster{
	
	@Autowired
	Environment environment;
	
	@Autowired
	RestTemplateController restTemplate;

	@Override
	public List<PersonnelDetail> getPersonnelInfoFromHrcdf(List<String> personnels, List<String> unitTypes, Integer activeFlag,
			List<String> unitCodes, List<String> commandCodes, List<String> userTypes, Integer designationRequiredFlag,Integer profileImageYn) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException{
		
		ObjectMapper mapper = new ObjectMapper();
		String uri = null;
		List<PersonnelDetail> objects  = null;
		ResponseEntity<Object> response = null;
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		uri  =  environment.getProperty("app.external.hrcdfurl") +"epps-cdf"+"/personnelMaster" + "/v1/personnel/lite?";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
		
		if(null != activeFlag) {
			uri =	uri.concat("activeFlag="+activeFlag);
			//builder.queryParam("activeFlag", activeFlag);
		}
		
		if(null != personnels && !personnels.isEmpty()) {
			uri =	uri.concat("&personnels="+String.join(",", personnels));
		}
		
		if(null != commandCodes && !commandCodes.isEmpty()) {
			uri =	uri.concat("&commandCodes="+commandCodes);
		}
		
		if(null != unitCodes && !unitCodes.isEmpty()) {
			uri =	uri.concat("&unitCodes="+unitCodes);
		}
		
		if(null != userTypes && !userTypes.isEmpty()) {
			uri =	uri.concat("&userTypes="+String.join(",", userTypes));
		}
		
		if(null != unitTypes && !unitTypes.isEmpty()) {
			uri =	uri.concat("&unitTypes="+unitTypes);
		}
		
		if(null != designationRequiredFlag) {
			uri =	uri.concat("&designationRequiredFlag="+designationRequiredFlag);
		}
		
		if(null != profileImageYn) {
			uri =	uri.concat("&profileImageYn="+profileImageYn);
		}

		response = restTemplate.callRestTemplate(null,uri,"GET",null);

		System.out.println(uri);
		
		if(null != response && null != response.getBody() ) {
			PersonnelDetail responseBody = mapper.convertValue(response.getBody(),  PersonnelDetail.class);
			objects = mapper.convertValue(responseBody.getData(),  new TypeReference<List<PersonnelDetail>>(){});
		}
		return objects;
	}

	
	
	@Override
	public List<CommandMasterDTO> getCommandMasterData(List<String> commandCodes, Integer commandId, SearchDTO searchVO,
			PaginationDTO pagination) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		ObjectMapper mapper = new ObjectMapper();
		String uri = null;
		List<CommandMasterDTO> objects  = null;
		ResponseEntity<Object> response = null;
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		uri  =  environment.getProperty("app.external.hrcdfurl") +"epps-cdf"+"/command" + "/v1?";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
		
		if(null != commandId) {
			uri =	uri.concat("commandId="+commandId);
		}
		
		if(null != commandCodes && !commandCodes.isEmpty()) {
			uri =	uri.concat("&personnels="+String.join(",", commandCodes));
		}
		
		response = restTemplate.callRestTemplate(null,uri,"GET",null);
		System.out.println(uri);
		if(null != response && null != response.getBody() ) {
			CommandMasterDTO responseBody = mapper.convertValue(response.getBody(),  CommandMasterDTO.class);
			objects = mapper.convertValue(responseBody.getData(),  new TypeReference<List<CommandMasterDTO>>(){});
		}
		return objects;
	}



	@Override
	public List<UnitDTO> getUnitMasterData(MastersCommonQueryParameterObject queryObject, SearchDTO searchVO,PaginationDTO pagination) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		ObjectMapper mapper = new ObjectMapper();
		String uri = null;
		List<UnitDTO> objects  = null;
		ResponseEntity<Object> response = null;
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		uri  =  environment.getProperty("app.external.hrcdfurl") +"epps-cdf"+"/unit" + "/v1?";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
		
		if(null != queryObject.getCommandCodes() && !queryObject.getCommandCodes().isEmpty()) {
			uri =	uri.concat("&commandCode="+String.join(",", queryObject.getCommandCodes()));
		}
		
		if (StringUtils.isNotBlank(queryObject.getUnitType())) {
			uri =	uri.concat("unitType="+queryObject.getUnitType());
		}

		if (null!=queryObject.getUnitCode()) {
			uri =	uri.concat("unitCode="+queryObject.getUnitCode());
		}

		if (StringUtils.isNotBlank(queryObject.getStationCode())) {
			uri =	uri.concat("stationCode="+queryObject.getStationCode());
		}

		if(queryObject.getIsActive() != null) {
			uri =	uri.concat("isActive="+queryObject.getIsActive());
		}

		if(queryObject.getIsAffloatAshore() != null) {
			uri =	uri.concat("isAffloatAshore="+queryObject.getIsAffloatAshore());
		}
		
		
		if(null != queryObject.getCommandId()) {
			uri =	uri.concat("commandId="+queryObject.getCommandId());
		}
		
		
		response = restTemplate.callRestTemplate(null,uri,"GET",null);
		System.out.println(uri);
		if(null != response && null != response.getBody() ) {
			UnitDTO responseBody = mapper.convertValue(response.getBody(),  UnitDTO.class);
			objects = mapper.convertValue(responseBody.getData(),  new TypeReference<List<UnitDTO>>(){});
		}
		return objects;
	}



	@Override
	public List<RankMasterDTO> getRankMstData(Integer rankId, List<String> rankCodes, SearchDTO searchVO,PaginationDTO pagination) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		return getRankMstData(rankId, rankCodes, searchVO, pagination, null);
	}



	@Override
	public List<RankMasterDTO> getRankMstData(Integer rankId, List<String> rankCodes, SearchDTO searchVO,PaginationDTO pagination, List<String> userTypeCodes) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {ObjectMapper mapper = new ObjectMapper();
			String uri = null;
			List<RankMasterDTO> objects  = null;
			ResponseEntity<Object> response = null;
			mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			uri  =  environment.getProperty("app.external.hrcdfurl") +"epps-cdf"+"/rank" + "/v1?";
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
			
			if(null != rankId) {
				uri =	uri.concat("rankId="+rankId);
			}
			
			if(null != rankCodes && !rankCodes.isEmpty()) {
				uri =	uri.concat("&rankCodes="+String.join(",", rankCodes));
			}
			
			if(null != userTypeCodes && !userTypeCodes.isEmpty()) {
				uri =	uri.concat("&userTypeCodes="+String.join(",", userTypeCodes));
			}
			
			response = restTemplate.callRestTemplate(null,uri,"GET",null);
			System.out.println(uri);
			if(null != response && null != response.getBody() ) {
				RankMasterDTO responseBody = mapper.convertValue(response.getBody(),  RankMasterDTO.class);
				objects = mapper.convertValue(responseBody.getData(),  new TypeReference<List<RankMasterDTO>>(){});
			}
			return objects;}


	


}
