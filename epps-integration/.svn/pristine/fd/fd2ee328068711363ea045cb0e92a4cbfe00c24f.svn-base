package com.epps.framework.notification.mail.infrastructure.repositories.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.jdbc.Work;
import org.json.JSONObject;
import org.postgresql.util.PGobject;
import org.springframework.stereotype.Repository;

import com.epps.framework.common.domain.model.entities.EppsModuleMaster;
import com.epps.framework.common.domain.model.entities.EppsModuleMasterPK;
import com.epps.framework.domain.exception.ErrorCode;
import com.epps.framework.domain.exception.ProcedureException;
import com.epps.framework.domain.exception.ResponseInfoType;
import com.epps.framework.infrastructure.repositories.generic.dao.impl.GenericDAO;
import com.epps.framework.notification.mail.infrastructure.repositories.dao.IEmailNotificationDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository("emailNotificationDaoImpl")
public class EmailNotificationDaoImpl extends GenericDAO<EppsModuleMaster, EppsModuleMasterPK> implements IEmailNotificationDao{

	public EmailNotificationDaoImpl() {
		super(EppsModuleMaster.class);
	}

	@Override
	public List<Object> getEmailNotificationData(JSONObject inputRequiredJson) {
		final List<Object> result = new ArrayList<Object>();
		try {
			getCrntSession().doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement call =null;
					try {
						call = connection.prepareCall("{ call inayu.proc_email_notification_dtl(?,?) }"); // procedure call 
						if(null!= inputRequiredJson) {
							call.setObject(1, inputRequiredJson, Types.OTHER);
						}else {
							call.setNull(1, Types.NULL);
						}
						call.registerOutParameter(2, Types.OTHER);
						call.execute();
						if(null != call.getObject(2)) {
							ObjectMapper mapper = new  ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
							PGobject pGobject = (PGobject) call.getObject(2);
							List<Object> dataJson1 = new ArrayList<Object>();
							try {
								dataJson1 = mapper.readValue(pGobject.getValue(),new TypeReference<List<Object>>(){});
							} catch (JsonProcessingException e) {
								e.printStackTrace();
							}
							result.addAll(dataJson1);
						}
					}catch(Exception e){
						throw new ProcedureException(e.getMessage(), ErrorCode.PROCEDURE_ERROR,ResponseInfoType.ERROR);
					}finally{
						if(call != null){
							call.close();
						}
					}
				}
			});
		}catch (HibernateException e) {
			throw new ProcedureException(e.getMessage(),ErrorCode.PROCEDURE_ERROR,ResponseInfoType.ERROR);
		}catch (Exception e) {
			throw new ProcedureException(e.getMessage(),ErrorCode.PROCEDURE_ERROR,ResponseInfoType.ERROR);
		}

		return result;
	}


}
