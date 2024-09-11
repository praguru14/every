package com.epps.framework.application.constant;

public class ApplicationConstants {

	// Hibernate Constancts
	public static final String HIBERNATE_SESSION = "hibernateSession";

	public static final String IsEmptyString = "";

	/** genericDao Constant **/
	public static final String EmptyString = "";
	public static final String FIELD_NAME_SERIAL_VERSION_UID = "serialVersionUID";
	public static final String SELECT = "SELECT ";
	public static final String FROM = " FROM ";
	public static final String AND = " AND ";
	public static final String AS_ENTITY_ALIAS = " as entityAlias ";
	public static final String WHERE = " WHERE ";
	public static final String SESSIONID = "sessionId";
	public static final String IPADDRESS = "ipAddress";
	public static final String DELETE = " DELETE FROM ";
	public static final StringBuilder ORDER_BY = new StringBuilder(" order by ");
	public static final StringBuilder SUB_QUERY_CLOSE_TAG_WITH_AS = new StringBuilder("  ) as ");
	public static final StringBuilder COMMA_SEPERATOR = new StringBuilder(" , ");
	public static final String COUNT = "Count ";
	public static final String TOTAL = "Total";

	public static final String OPERATION_MODE_INSERT = "A";
	public static final String OPERATION_MODE_UPDATE = "U";
	public static final String OPERATION_MODE_DELETE = "D";
	public static final String OPERATION_MODE_READ = "R";

	public static final String STATUS_SUCCESS = "Success";
	public static final String STATUS_FAILED = "Failed";
	public static final String STATUS_VALIDATION_FAILED = "Validation_Failed";
	public static final String STATUS_UPLOADED = "Uploaded Successfully";
	public static final String DATA_NOT_FOUND = "DATA NOT FOUND";

	public static final String FLAG_YES = "Y";
	public static final String FLAG_NO = "N";

	public static final String COMPANY_CODE = " Company code ";
	public static final String DIVISION_CODE = " Division Code ";

	public static final String PREVIUS_PAGE_ID = "previouspage";

	public static final String FREE_FLAG = "F";

	public static final String BILLED_FLAG = "B";
	public static final Integer ZERO_INTEGER_TYPE = 0;
	public static final Integer ONE_INTEGER_TYPE = 1;

	// Global Exception
	public static final String FORM_MSG = "formResultMsg";
	public static final String DATA_SAVE_SUCCESSFULLY = " Data Saved Successfully ";
	public static final String DATA_SAVE_FAILED = " Data Saved Failed ";
	public static final String NOT_FOUND = " Not Found ";
	public static final String SMS = "sms";
	public static final String EMAIL = "email";
	public static final String SERVER = "server";

	public static final String CUT = "cut";
	public static final String COPY = "copy";

	public static final String RESOURCE_CREATED_SUCCESSFULLY = "RESOURCE CREATED SUCCESSFULLY";
	public static final String RESOURCE_UPDATED_SUCCESSFULLY = "RESOURCE UPDATED SUCCESSFULLY";
	public static final String RESOURCE_RETRIVED_SUCCESSFULLY = "RESOURCE RETRIVED SUCCESSFULLY";
	public static final String RESOURCE_DELETED_SUCCESSFULLY = "RESOURCE DELETED SUCCESSFULLY";
	
	public static final String NOT_APPLICABLE = "NA";

	public static final String DOCUMENTS_UPLOAD_FOLDER_NAME_FOR_JASPER = "Uploaded Files";
	public static final String RESOURCE_CREATED_UNSUCCESSFULL = "RESOURCE CREATION UNSUCCESSFULL";
	public static final String RESOURCE_UPDATED_UNSUCCESSFULL = "RESOURCE UPDATION UNSUCCESSFULL";
	public static final String RESOURCE_RETRIEVED_UNSUCCESSFULL = "RESOURCE RETRIEVED UNSUCCESSFULL";
	
	public static final String DATA_NOT_INSERT_IN_STAGING = "DATA NOT INSERT IN STAGING TABLE";
	public static final String ORG_POSTGRESQL_UTIL_PSQLEXCEPTION="org.postgresql.util.PSQLException";
	
	public static final String CACHE_DELIMITER = "@#";
	public static final String CACHE_BUCKET_BILLING ="billing";
	public static final String CACHE_BUCKET_BUCKET ="bucket";
	public static final String CACHE_BUCKET_CORE ="core";
	public static final String CACHE_BUCKET_FAS ="fas";
	public static final String CACHE_BUCKET_NOTIFICATION ="notification";
	public static final String CACHE_BUCKET_REPORTS ="reports";
	public static final String CACHE_BUCKET_WEB ="web";
	
	public static final String ORDER_BY_DESC ="desc";
	public static final String ORDER_BY_ASC ="asc";
	
	public static final String DEPENDENT_DETAILS ="dependent";
	public static final String QUALIFICATION_DETAILS ="qualification";
	public static final String SKILLS_DETAILS ="skill";
	public static final String DECORATION_DETAILS ="decoration";
	public static final String MEDICAL_DETAILS ="medical";
	public static final String ADDRESS_DETAILS ="address";
	public static final String SERVICE_DETAILS ="service";
	public static final String TENURE_DETAILS ="tenure";
	public static final String MARRIAGE_DETAILS ="marriage";
	public static final String AWARD_DETAILS ="award";
	
	public static final String DEFAULT_IP_ADDRESS="0.0.0.0";

	public static final String USER_TYPE_OFFICER_CODE = "OFFIC";
	public static final String USER_TYPE_CIVILIAN_CODE = "CIVIL";
	public static final String USER_TYPE_SAILORS_CODE = "SAIL";
	
	public static final String SOURCE_APP_DOP = "DOP";
	public static final String SOURCE_APP_CABS = "CABS";
	public static final String SOURCE_APP_NCIMS = "NCIMS";
	
	public static final Integer PERSONNEL_WORKFLOW_STAGE_DRAFT =10;
	
			

}
