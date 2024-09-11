package com.epps.framework.domain.exception;

import java.util.NoSuchElementException;
/**
 * Set of error Codes that are used for indicating specific error scenarios
 * 
 * @author 
 *
 */
public enum ErrorCode {

	// Internal Java Processing Error - (1100 -1199)
	INTERNAL_SERVER_ERROR(1015),GENERIC_ERROR(1016), UNEXPECTED_ERROR(1107),PARSE_EXCEPTION(1006),
	
	// Database Operation Error Processing - (1200 -1299)
	BASE_DB_ERROR(1201),  
	STALE_OBJECT_STATE_EXCEPTION(1221),
	PROCEDURE_ERROR(1251), 
	
	// Screen VALIDATION (2000 -3000)
	ATLEAST_ONE_DIVISION_MUST_ACTIVE(1059),NEED_ATLEST_ONE_DEFAULT_RECORD(2000),DEFAULT_RECORD_CAN_NOT_BE_DELETED(2001),
	DUPLICATE_RECORD_FOUND(1019),DATA_NOT_FOUND(1035), RECORDS_NOT_FOUND(1034),MULTIPLE_RECORDS_FOUND(1092), 
	
	
	//Reports Error
	REPORTS_ERROR(1026),
	
	//File Error
	FILE_UPLOAD_TYPE(1045),
	FILE_NOT_FOUND(1066),

	VALIDATION_FAILED(1310),
	SALARY_FLAG_ALREADY_ACTIVATED(1069), SALARY_FLAG_ERROR(1037), RECORD_NOT_FOUND(1034),;
	private final int codeId;

	private ErrorCode(final int codeId) {
		this.codeId = codeId;
	}

	public int getCodeId() {
		return this.codeId;
	}

	/**
	 * Converts an int value into an ErrorCode
	 * 
	 * @param errorCode
	 * @return {@link ErrorCode}
	 */
	public static ErrorCode getExceptionCode(final int errorCode) {

		ErrorCode eErrorCode = null;
		for (final ErrorCode status : ErrorCode.values()) {
			if (status.getCodeId() == errorCode) {
				eErrorCode = status;
				break;
			}
		}
		if (null == eErrorCode) {
			throw new NoSuchElementException("The received code " + errorCode + " is not valid !!!");
		} else {
			return eErrorCode;
		}

	}

}
