/**
 * 
 */
package com.epps.module.staging.hrcdf.infrastructure.repositories.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import com.epps.framework.common.domain.model.entities.EppsModuleMaster;
import com.epps.framework.common.domain.model.entities.EppsModuleMasterPK;
import com.epps.framework.domain.exception.ErrorCode;
import com.epps.framework.domain.exception.ProcedureException;
import com.epps.framework.domain.exception.ResponseInfoType;
import com.epps.framework.infrastructure.repositories.generic.dao.impl.GenericDAO;
import com.epps.module.staging.hrcdf.infrastructure.repositories.dao.ICDFStagingDao;

/**
 * @author Shubham Goliwar
 *
 */

@Repository("cdfStagingDaoImpl")
public class CDFStagingDaoImpl extends GenericDAO<EppsModuleMaster, EppsModuleMasterPK> implements ICDFStagingDao {

	public CDFStagingDaoImpl() {
		super(EppsModuleMaster.class);
	}

	@Override
	public void callHRCDFStagingProcedure(Integer sessionId) {
		try {
			getCrntSession().doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement call = connection.prepareCall("{call dopstaging.func_personal_data_transformation(?)}"); //call hrcdf truncate procedure
					call.setInt(1, sessionId);
					call.execute();
				}
			});
		} catch (HibernateException e) {
			throw new ProcedureException(e.getMessage(), ErrorCode.PROCEDURE_ERROR,ResponseInfoType.ERROR);
		} catch (Exception e) {
			throw new ProcedureException(e.getMessage(), ErrorCode.PROCEDURE_ERROR,ResponseInfoType.ERROR);
		}
	
	}

	@Override
	public void callHRCDFStagingDeleteProcedure(Integer sessionId) {
		try {
			getCrntSession().doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement call = connection.prepareCall("{call dopstaging.func_personal_data_transformation(?)}"); //call hrcdf transformation procedure
					call.setInt(1, sessionId);
					call.execute();
				}
			});
		} catch (HibernateException e) {
			throw new ProcedureException(e.getMessage(), ErrorCode.PROCEDURE_ERROR,ResponseInfoType.ERROR);
		} catch (Exception e) {
			throw new ProcedureException(e.getMessage(), ErrorCode.PROCEDURE_ERROR,ResponseInfoType.ERROR);
		}
	}

}
