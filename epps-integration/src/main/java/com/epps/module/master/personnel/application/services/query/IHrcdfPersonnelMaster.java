package com.epps.module.master.personnel.application.services.query;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


import com.epps.framework.interfaces.responses.dtos.PaginationDTO;
import com.epps.framework.interfaces.responses.dtos.SearchDTO;
import com.epps.module.master.personnel.interfaces.dtos.CommandMasterDTO;
import com.epps.module.master.personnel.interfaces.dtos.MastersCommonQueryParameterObject;
import com.epps.module.master.personnel.interfaces.dtos.PersonnelDetail;
import com.epps.module.master.personnel.interfaces.dtos.RankMasterDTO;
import com.epps.module.master.personnel.interfaces.dtos.UnitDTO;

public interface IHrcdfPersonnelMaster {
	
	public List<PersonnelDetail> getPersonnelInfoFromHrcdf(List<String> personnelNo, List<String> unitTypes, Integer activeFlag, List<String> unitCodes,
			List<String> commandCodes , List<String> userTypes, Integer designationRequiredFlag,Integer profileImageYn) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException;

	public List<CommandMasterDTO> getCommandMasterData(List<String> commandCode, Integer commandId, SearchDTO searchVO,	PaginationDTO pagination) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException;

	public List<UnitDTO> getUnitMasterData(MastersCommonQueryParameterObject unitMasterQueryObjet, SearchDTO searchVO,PaginationDTO pagination) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException;

	public List<RankMasterDTO> getRankMstData(Integer rankId, List<String> rankCode, SearchDTO searchVO, PaginationDTO pagination) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException;

	public List<RankMasterDTO> getRankMstData(Integer rankId, List<String> rankCode, SearchDTO searchVO, PaginationDTO pagination, List<String> userTypeCodes) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException;

}
