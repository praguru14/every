/**
 * 
 */
package com.epps.module.staging.hrcdf.application.command.service;

import java.util.List;

import com.epps.module.master.personnel.interfaces.dtos.PersonnelDetail;

/**
 * @author Shubham Goliwar
 *
 */

public interface ICDFStagingCommandService {

	void saveDopStagingPersonnelReleaseData(List<PersonnelDetail> list);

	void callHRCDFStagingProcedure(Integer sessionId);

	void callHRCDFStagingDeleteProcedure(Integer sessionId);

}
