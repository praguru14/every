package com.epps.framework.domain.exception;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.el.ELException;
import javax.naming.AuthenticationException;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.websocket.EncodeException;
import javax.xml.bind.JAXBException;
import javax.xml.ws.WebServiceException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate5.HibernateJdbcException;
import org.springframework.transaction.TransactionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.epps.framework.application.constant.ApplicationConstants;
import com.epps.framework.application.threads.CustomThreadLocal;
import com.epps.framework.application.util.date.DateHelper;
import com.epps.framework.application.util.locale.LocaleMessageUtility;
import com.epps.framework.application.util.logger.ApplicationLogger;
import com.epps.framework.interfaces.responses.ResponseInfo;
import com.epps.framework.interfaces.responses.dtos.ResponseWrapper;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@ControllerAdvice
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler {

	
	private static final ApplicationLogger logger = new ApplicationLogger(GlobalExceptionAdvice.class);

	@Autowired
	private LocaleMessageUtility localeMessageUtility;

	/**
	 * Custom Exception
	 * @param request
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<?> handleAllApplicationException(HttpServletRequest request,ApplicationException exception) {
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = exception.getErrorCode();
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = null;
		
		if(null != exception.getErrorType()) {
			errorName = exception.getErrorType().name();
		}
		logger.error("Application exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Custom Exception
	 * @param request
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(ProcedureException.class)
	public ResponseEntity<?> handleAllProcedureException(HttpServletRequest request,ProcedureException exception) {
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = exception.getErrorCode();
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("ProcedureException exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<?> handleAllDatabaseException(HttpServletRequest request,DatabaseException exception) {
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.BASE_DB_ERROR;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("DatabaseException exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleAllValidationExceptionException(HttpServletRequest request,ValidationException exception) {
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = exception.getErrorCode();
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.VALIDATION.name();
		
		logger.error("ValidationException exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(HibernateException.class)
	public ResponseEntity<?> handleAllHibernateException(HttpServletRequest request,HibernateException exception){
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.BASE_DB_ERROR;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("HibernateException exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(HibernateJdbcException.class)
	public ResponseEntity<?> handleAllHibernateJdbcException(HttpServletRequest request,HibernateJdbcException exception){
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.BASE_DB_ERROR;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("Application exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(SessionException.class)
	public ResponseEntity<?> handleAllSessionException(HttpServletRequest request,SessionException exception){
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.BASE_DB_ERROR;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("Application exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(TransactionException.class)
	public ResponseEntity<?> handleAllTransactionException(HttpServletRequest request,TransactionException exception){
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.BASE_DB_ERROR;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("Application exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	
	/**
	 * Exception Handling for Unhandled JSON and Parse Exception
	 * @param request
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value= {ParseException.class,JsonParseException.class,JsonMappingException.class, JsonGenerationException.class})
	public ResponseEntity<?> parseExceptionExceptionHandler(HttpServletRequest request , Exception exception) {
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getCause().getCause().getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.PARSE_EXCEPTION;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("Application exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> webSrvcRequestAuthenticationExceptionHandler(HttpServletRequest request , ConstraintViolationException exception , WebRequest reuest) {
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		 Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
		 
		 if(null != constraintViolations && constraintViolations.size() > 0){
			 for (ConstraintViolation<?> violation : constraintViolations) {
				 ResponseInfo response = new ResponseInfo();
				 response.setMsgCode(ErrorCode.VALIDATION_FAILED.getCodeId());
				 //response.setUserMessage(StringUtils.substringAfterLast(violation.getPropertyPath().toString(), ".") + ": " + violation.getMessage() + " For value " + violation.getInvalidValue());
				 
				 if(null != violation.getInvalidValue() && ! violation.getInvalidValue().toString().contains("com.epps")) {
					 response.setUserMessage(violation.getPropertyPath() + ": " + violation.getMessage() + " For value " + violation.getInvalidValue());
				 }else {
					 response.setUserMessage(violation.getPropertyPath() + ": " + violation.getMessage());
				 }
				 response.setMsgType(ResponseInfoType.VALIDATION.name());
				 //response.setInternalMessage(ExceptionUtils.getStackTrace(exception));
				 responses.add(response);
			    }
		 }
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.BAD_REQUEST);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request
			) {
		UUID uuid=UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		logger.error("Exception:uuid="+uuid+":",exception);
		for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
				 ResponseInfo response = new ResponseInfo();
				 response.setMsgCode(ErrorCode.VALIDATION_FAILED.getCodeId());
				 response.setUserMessage(fieldError.getField() + " " +  fieldError.getDefaultMessage());
				 response.setMsgType(ResponseInfoType.VALIDATION.name());
				 //response.setInternalMessage(ExceptionUtils.getStackTrace(exception));
				 responses.add(response);
		 }
		return (ResponseEntity<Object>) ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * For Web Service
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<?> webSrvcRequestAuthenticationExceptionHandler(HttpServletRequest request , AuthenticationException exception) {
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.BASE_DB_ERROR;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.VALIDATION.name();
		
		logger.error("Application exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		@SuppressWarnings("unused")
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	/**
	 * For Web Service
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ELException.class)
	public ResponseEntity<?> webSrvctokenExpiryExceptionHandler(HttpServletRequest request , ELException exception) {
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.BASE_DB_ERROR;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("Application exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<?> handleUncategorizedSQLException(HttpServletRequest request,DataAccessException exception) {	
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getCause().getCause().getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.BASE_DB_ERROR;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("DataAccessException exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(BeansException.class)
	public ResponseEntity<?> handleBeansExceptionException(HttpServletRequest request,BeansException exception) {	
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getCause().getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.BASE_DB_ERROR;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("BeansException exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<?> handleSQLException(HttpServletRequest request,SQLException exception) {	
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.BASE_DB_ERROR;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("SQLException exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> argumentException(HttpServletRequest request,IllegalArgumentException exception) {	
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getCause().getCause().getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.BASE_DB_ERROR;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("IllegalArgumentException exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<?> illegalStateException(HttpServletRequest request,IllegalStateException exception) {	
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getCause().getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.BASE_DB_ERROR;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("IllegalStateException exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(NamingException.class)
	public ResponseEntity<?> namingException(HttpServletRequest request,NamingException exception) {	
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getCause().getCause().getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.BASE_DB_ERROR;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("NamingException exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<?> ioException(HttpServletRequest request,IOException exception) {	
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getCause().getCause().getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.BASE_DB_ERROR;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("IOException exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(JAXBException.class)
	public ResponseEntity<?> jaxbException(HttpServletRequest request,JAXBException exception) {	
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getCause().getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.BASE_DB_ERROR;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("JAXBException exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(WebServiceException.class)
	public ResponseEntity<?> webServiceException(HttpServletRequest request,WebServiceException exception) {	
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getCause().getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.BASE_DB_ERROR;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("JAXBException exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	
	
	@ExceptionHandler(EncodeException.class)
	public ResponseEntity<?> securityException(HttpServletRequest request,EncodeException exception) {	
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getCause().getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.BASE_DB_ERROR;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("EncodeException exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(value= { NoSuchFieldException.class, NoSuchMethodException.class, InvocationTargetException.class, 
			ClassNotFoundException.class, InstantiationException.class,	IllegalAccessException.class})
	public ResponseEntity<?> argumentException(HttpServletRequest request,ReflectiveOperationException exception) {	
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getCause().getCause().getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.BASE_DB_ERROR;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("ReflectiveOperationException exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleAllException(HttpServletRequest request,Exception exception) {
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getCause().getCause().getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.BASE_DB_ERROR;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("ReflectiveOperationException exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		exception.printStackTrace();
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(NestedRuntimeException.class)
	public ResponseEntity<?> handleAllNestedRuntimeException(HttpServletRequest request,NestedRuntimeException exception) {
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getCause().getCause().getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.BASE_DB_ERROR;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("NestedRuntimeException exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleAllNullPointerException(HttpServletRequest request,NullPointerException exception){
		UUID uuid = UUID.randomUUID();
		List<ResponseInfo> responses = new ArrayList<ResponseInfo>(1);
		String userErrorMessage = exception.getCause().getCause().getMessage();
		String internalErrorMessage = ExceptionUtils.getStackTrace(exception);
		ErrorCode errorCode = ErrorCode.BASE_DB_ERROR;
		Integer errorCodeId = errorCode.getCodeId();
		String errorName = ResponseInfoType.ERROR.name();
		
		logger.error("NullPointerException exception:uuid="+uuid+": " + (errorCode.getCodeId() > 0 ? "" :  this.localeMessageUtility.getErrorMessage(errorCodeId)) , exception);
		ResponseInfo response = getErrorResponseFromExceptionObject(request, userErrorMessage, errorCodeId, internalErrorMessage ,uuid , errorCode,errorName);
		responses.add(response); 
		return ResponseWrapper.getResponseEntityWithoutPagination(UUID.randomUUID().toString(), DateHelper.getFormattedDateString(new Date(),DateHelper.EPPS_FORMAT_yyyyMMddHHmmss),ApplicationConstants.STATUS_FAILED, 
				responses, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	private ResponseInfo getErrorResponseFromExceptionObject(HttpServletRequest request, String userErrorMessage, Integer errorCodeId ,	
			String internalErrorMessage,UUID uuid , ErrorCode errorCode, String errorName) {
		ResponseInfo response = new ResponseInfo();
		clearCustomThreadLocalOnException(request);
		
		if(null != errorCode) {
			response.setMsgCode(errorCodeId);
		}
		
		if(null != userErrorMessage && null != errorCode) {
			response.setUserMessage(this.localeMessageUtility.getErrorMessage(errorCodeId) == null ?userErrorMessage:this.localeMessageUtility.getErrorMessage(errorCodeId));
		}else if(null != userErrorMessage){
			response.setUserMessage(userErrorMessage);
		}
		
		if(null != errorName) {
			response.setMsgType(errorName);
		}
		
		response.setInternalMessage(internalErrorMessage);
		return response;
	}
	
	private void clearCustomThreadLocalOnException(HttpServletRequest request) {
		if(CustomThreadLocal.get() != null){
			if(!CustomThreadLocal.get().isConnected() || !CustomThreadLocal.get().isOpen()){
				HttpSession httpSession = request.getSession();
				Session hibernateSession = (Session) httpSession.getAttribute(ApplicationConstants.HIBERNATE_SESSION);
				if(hibernateSession!=null){
					hibernateSession.close();
					httpSession.setAttribute(ApplicationConstants.HIBERNATE_SESSION,null);
				}
			}
			CustomThreadLocal.set(null);
		}
	}
		
}
