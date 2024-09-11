package com.epps.framework.interfaces.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.portable.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@Hidden
public class GenericController {
	
	@RequestMapping(method = RequestMethod.GET,value="/")
	public ResponseEntity<String> defaultMethod(ModelMap model,HttpServletRequest request,HttpServletResponse response) throws ApplicationException{
		 return new ResponseEntity<String>("Core Application",HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/home")
	public ResponseEntity<String> home(@RequestParam ModelMap model,HttpServletRequest request,HttpServletResponse response) throws ApplicationException{
		 return new ResponseEntity<String>("Core Application",HttpStatus.OK);
	}
}