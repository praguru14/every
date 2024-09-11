/**
 * 
 */
package com.epps.module.staging.hrcdf.application.command.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.epps.module.master.personnel.interfaces.dtos.PersonnelDetail;
import com.epps.module.staging.hrcdf.application.command.service.ICDFStagingCommandService;
import com.epps.module.staging.hrcdf.domain.model.entities.EppsStagingPersonnelDetails;
import com.epps.module.staging.hrcdf.infrastructure.repositories.dao.ICDFStagingDao;

/**
 * @author Shubham Goliwar
 *
 */

@Service("cdfStagingCommandServiceImpl")
public class CDFStagingCommandServiceImpl implements ICDFStagingCommandService {

	@Autowired
	private ICDFStagingDao cdfStagingDaoImpl;

	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void saveDopStagingPersonnelReleaseData(List<PersonnelDetail> list) {
		Set<EppsStagingPersonnelDetails> details = new HashSet<>();
		if (null!=list && list.size()>0) {
			for(PersonnelDetail personnelDetailDto : list) {
				EppsStagingPersonnelDetails personalDetailPersist= new EppsStagingPersonnelDetails();
				BeanUtils.copyProperties(personnelDetailDto, personalDetailPersist);
				populatePersonnelDetails(personalDetailPersist,personnelDetailDto);
				cdfStagingDaoImpl.replaceObjectBlankValueWithNull(personalDetailPersist);
				details.add(personalDetailPersist);
			}
			cdfStagingDaoImpl.saveOrUpdateAll(details);
		}

	}

	private void populatePersonnelDetails(EppsStagingPersonnelDetails personalDetailPersist,
			PersonnelDetail personnelDetailDto) {
		if (null!=personnelDetailDto.getEmployeeGender()) {personalDetailPersist.setEmployeeGender(personnelDetailDto.getEmployeeGender());}
		if (null!=personnelDetailDto.getGender()) {personalDetailPersist.setEmployeeGender(personnelDetailDto.getGender());}
		personalDetailPersist.setStage(1);
		cdfStagingDaoImpl.replaceObjectBlankValueWithNull(personalDetailPersist);

	}

	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void callHRCDFStagingProcedure(Integer sessionId) {
		cdfStagingDaoImpl.callHRCDFStagingProcedure(sessionId);
	}

	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void callHRCDFStagingDeleteProcedure(Integer sessionId) {
		cdfStagingDaoImpl.callHRCDFStagingDeleteProcedure(sessionId);
	}

}
