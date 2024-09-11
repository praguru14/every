package com.epps.framework.interfaces.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.portable.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping(value = "/healthCheck")
@Hidden
public class HealthCheckController {

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> defaultMethod(ModelMap model,HttpServletRequest request,HttpServletResponse response) throws ApplicationException{
		 return new ResponseEntity<String>(HttpStatus.OK);
	}
}