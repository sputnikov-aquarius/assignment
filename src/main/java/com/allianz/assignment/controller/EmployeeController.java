package com.allianz.assignment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.allianz.assignment.domain.Employee;
import com.allianz.assignment.domain.common.response.ErrorResponse;
import com.allianz.assignment.domain.common.response.GeneralResponse;
import com.allianz.assignment.domain.common.response.StandardResponse;
import com.allianz.assignment.exception.AssignmentException;
import com.allianz.assignment.service.EmployeeService;
import com.allianz.assignment.util.CommonUtils;
import com.allianz.assignment.util.ValidateUtils;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CommonUtils commonUtils;
	@Autowired
	private ValidateUtils valiateUtils;
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Create Employee", notes = "employee id, employee name are mandatory ones")
	@PostMapping("/employee")
	public ResponseEntity<GeneralResponse<StandardResponse>> createEmployee(@RequestHeader Map<String, String> headers,
			@RequestBody Employee request) {
		log.info("creating employee : {}", request);
		GeneralResponse<StandardResponse> resp = new GeneralResponse<>();
		try {
			List<ErrorResponse> errors = valiateUtils.validate(headers, request);
			if (errors.isEmpty()) {
				resp.setData(employeeService.createEmployee(request));
				return ResponseEntity.ok(resp);
			} else {
				resp.setErrors(errors);
				return ResponseEntity.badRequest().body(resp);
			}
		} catch (AssignmentException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(commonUtils.createErrorResponse(resp, e));
		}
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "update employee", notes = "employee id, employee name are mandatory ones, id cannot be updated")
	@PutMapping("/employee")
	public ResponseEntity<GeneralResponse<StandardResponse>> updateEmployee(@RequestHeader Map<String, String> headers,
			@RequestBody Employee request) {
		log.info("updating employee : {}", request);
		GeneralResponse<StandardResponse> resp = new GeneralResponse<>();
		try {
			List<ErrorResponse> errors = valiateUtils.validate(headers, request);
			if (errors.isEmpty()) {
				resp.setData(employeeService.updateEmployee(request));
				return ResponseEntity.ok(resp);
			} else {
				resp.setErrors(errors);
				return ResponseEntity.badRequest().body(resp);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(commonUtils.createErrorResponse(resp, e));
		}
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "delete employee", notes = "delete by employee id. therefore, employee id is neccessary")
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<GeneralResponse<StandardResponse>> deleteEmployee(@RequestHeader Map<String, String> headers,
			@PathVariable(required = true, name="id") String employeeId) {
		log.info("deleting employee with id : {}", employeeId);
		GeneralResponse<StandardResponse> resp = new GeneralResponse<>();
		try {
			List<ErrorResponse> errors = valiateUtils.validate(headers, employeeId);
			if (errors.isEmpty()) {
				resp.setData(employeeService.deleteEmployeeById(employeeId));
				return ResponseEntity.ok(resp);
			} else {
				resp.setErrors(errors);
				return ResponseEntity.badRequest().body(resp);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(commonUtils.createErrorResponse(resp, e));
		}
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "get employee", notes = "could be retrieve by id; if id isn't provice, all employee would be returned")
	@GetMapping(value = {"/employee", "/employee/{id}"})
	public ResponseEntity<GeneralResponse<ArrayList<Employee>>> getEmployee(@RequestHeader Map<String, String> headers,
			@PathVariable(required = false, name="id") String employeeId) {
		log.info("retrieving employee with id : {}", employeeId);
		GeneralResponse<ArrayList<Employee>> resp = new GeneralResponse<>();
		try {
			List<ErrorResponse> errors = valiateUtils.validateToken(headers);
			if (errors.isEmpty()) {
				resp.setData((ArrayList<Employee>) employeeService.retrieveEmployee(employeeId));
				return ResponseEntity.ok(resp);
			} else {
				resp.setErrors(errors);
				return ResponseEntity.badRequest().body(resp);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(commonUtils.createErrorResponse(resp, e));
		}
	}
}
