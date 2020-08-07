package com.allianz.assignment.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.allianz.assignment.constant.Constant;
import com.allianz.assignment.constant.ErrorConstant;
import com.allianz.assignment.domain.Employee;
import com.allianz.assignment.domain.common.response.ErrorResponse;
import com.allianz.assignment.domain.common.response.GeneralResponse;
import com.allianz.assignment.domain.common.response.StandardResponse;
import com.allianz.assignment.exception.AssignmentException;
import com.allianz.assignment.service.EmployeeService;
import com.allianz.assignment.util.CommonUtils;
import com.allianz.assignment.util.ValidateUtils;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {
	@InjectMocks
	private EmployeeController employeeController;
	@Mock
	private EmployeeService employeeService;
	@Mock
	private CommonUtils commonUtils;
	@Mock
	private ValidateUtils valiateUtils;
	
	@Test
	public void shouldOK_WhenCreateEmployee() throws AssignmentException {
		Mockito.when(valiateUtils.validate(Mockito.anyMap(), Mockito.any(Employee.class)))
			.thenReturn(new ArrayList<ErrorResponse>());
		Mockito.when(employeeService.createEmployee(Mockito.any(Employee.class)))
			.thenReturn(mockSuccessStandardResponse());
		Map<String, String> headers = new HashMap<String, String>();
		ResponseEntity<GeneralResponse<StandardResponse>> resp = 
				employeeController.createEmployee(headers, mockEmployeeList().get(0));
		assertNotNull(resp);
		assertEquals(HttpStatus.OK, resp.getStatusCode());
		assertNotNull(resp.getBody());
		assertNotNull(resp.getBody().getData());
		assertEquals(Constant.SUCCESS_CODE, resp.getBody().getData().getResponseCode());
		verify(employeeService, times(1)).createEmployee(mockEmployeeList().get(0));
	}
	
	@Test
	public void shouldError_WhenCreateEmployee() throws AssignmentException {
		Mockito.when(valiateUtils.validate(Mockito.anyMap(), Mockito.any(Employee.class)))
			.thenReturn(new ArrayList<ErrorResponse>());
		Mockito.when(commonUtils.createErrorResponse(Mockito.any(GeneralResponse.class), Mockito.any(Throwable.class)))
			.thenReturn(mockErrorOnException());
		Mockito.when(employeeService.createEmployee(Mockito.any(Employee.class)))
			.thenThrow(new AssignmentException("test exp"));
		Map<String, String> headers = new HashMap<String, String>();
		ResponseEntity<GeneralResponse<StandardResponse>> resp = 
				employeeController.createEmployee(headers, mockEmployeeList().get(0));
		assertNotNull(resp);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resp.getStatusCode());
		assertNotNull(resp.getBody());
		assertNotNull(resp.getBody().getErrors());
		assertFalse(resp.getBody().getErrors().isEmpty());
		verify(employeeService, times(1)).createEmployee(mockEmployeeList().get(0));
	}
	
	@Test
	public void shouldValidateFailed_WhenCreateEmployee() throws AssignmentException {
		Mockito.when(valiateUtils.validate(Mockito.anyMap(), Mockito.any(Employee.class)))
			.thenReturn(mockErrorResponses());
		Map<String, String> headers = new HashMap<String, String>();
		ResponseEntity<GeneralResponse<StandardResponse>> resp = 
				employeeController.createEmployee(headers, mockEmployeeList().get(0));
		assertNotNull(resp);
		assertNotNull(resp.getBody());
		assertNotNull(resp.getBody().getErrors());
		assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
	}
	
	@Test
	public void shouldOK_WhenUpdateEmployee() throws AssignmentException {
		Mockito.when(valiateUtils.validate(Mockito.anyMap(), Mockito.any(Employee.class)))
			.thenReturn(new ArrayList<ErrorResponse>());
		Mockito.when(employeeService.updateEmployee(Mockito.any(Employee.class)))
			.thenReturn(mockSuccessStandardResponse());
		Map<String, String> headers = new HashMap<String, String>();
		ResponseEntity<GeneralResponse<StandardResponse>> resp = 
				employeeController.updateEmployee(headers, mockEmployeeList().get(0));
		assertNotNull(resp);
		assertEquals(HttpStatus.OK, resp.getStatusCode());
		assertNotNull(resp.getBody());
		assertNotNull(resp.getBody().getData());
		assertEquals(Constant.SUCCESS_CODE, resp.getBody().getData().getResponseCode());
		verify(employeeService, times(1)).updateEmployee(mockEmployeeList().get(0));
	}
	
	@Test
	public void shouldError_WhenUpdateEmployee() throws AssignmentException {
		Mockito.when(valiateUtils.validate(Mockito.anyMap(), Mockito.any(Employee.class)))
			.thenReturn(new ArrayList<ErrorResponse>());
		Mockito.when(commonUtils.createErrorResponse(Mockito.any(GeneralResponse.class), Mockito.any(Throwable.class)))
			.thenReturn(mockErrorOnException());
		Mockito.when(employeeService.updateEmployee(Mockito.any(Employee.class)))
			.thenThrow(new AssignmentException("test exp"));
		Map<String, String> headers = new HashMap<String, String>();
		ResponseEntity<GeneralResponse<StandardResponse>> resp = 
				employeeController.updateEmployee(headers, mockEmployeeList().get(0));
		assertNotNull(resp);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resp.getStatusCode());
		assertNotNull(resp.getBody());
		assertNotNull(resp.getBody().getErrors());
		assertFalse(resp.getBody().getErrors().isEmpty());
		verify(employeeService, times(1)).updateEmployee(mockEmployeeList().get(0));
	}
	
	@Test
	public void shouldValidateFailed_WhenUpdateEmployee() throws AssignmentException {
		Mockito.when(valiateUtils.validate(Mockito.anyMap(), Mockito.any(Employee.class)))
			.thenReturn(mockErrorResponses());
		Map<String, String> headers = new HashMap<String, String>();
		ResponseEntity<GeneralResponse<StandardResponse>> resp = 
				employeeController.updateEmployee(headers, mockEmployeeList().get(0));
		assertNotNull(resp);
		assertNotNull(resp.getBody());
		assertNotNull(resp.getBody().getErrors());
		assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
	}
	
	@Test
	public void shouldOK_WhenDeleteEmployee() throws AssignmentException {
		Mockito.when(valiateUtils.validate(Mockito.anyMap(), Mockito.anyString()))
			.thenReturn(new ArrayList<ErrorResponse>());
		Mockito.when(employeeService.deleteEmployeeById(Mockito.anyString()))
			.thenReturn(mockSuccessStandardResponse());
		Map<String, String> headers = new HashMap<String, String>();
		ResponseEntity<GeneralResponse<StandardResponse>> resp = 
				employeeController.deleteEmployee(headers, "1");
		assertNotNull(resp);
		assertEquals(HttpStatus.OK, resp.getStatusCode());
		assertNotNull(resp.getBody());
		assertNotNull(resp.getBody().getData());
		assertEquals(Constant.SUCCESS_CODE, resp.getBody().getData().getResponseCode());
		verify(employeeService, times(1)).deleteEmployeeById("1");
	}
	
	@Test
	public void shouldError_WhenDeleteEmployee() throws AssignmentException {
		Mockito.when(valiateUtils.validate(Mockito.anyMap(), Mockito.any(Employee.class)))
			.thenReturn(new ArrayList<ErrorResponse>());
		Mockito.when(commonUtils.createErrorResponse(Mockito.any(GeneralResponse.class), Mockito.any(Throwable.class)))
			.thenReturn(mockErrorOnException());
		Mockito.when(employeeService.deleteEmployeeById(Mockito.anyString()))
			.thenThrow(new AssignmentException("test exp"));
		Map<String, String> headers = new HashMap<String, String>();
		ResponseEntity<GeneralResponse<StandardResponse>> resp = 
				employeeController.deleteEmployee(headers, "1");
		assertNotNull(resp);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resp.getStatusCode());
		assertNotNull(resp.getBody());
		assertNotNull(resp.getBody().getErrors());
		assertFalse(resp.getBody().getErrors().isEmpty());
	}
	
	@Test
	public void shouldValidateFailed_WhenDeleteEmployee() throws AssignmentException {
		Mockito.when(valiateUtils.validate(Mockito.anyMap(), Mockito.anyString()))
			.thenReturn(mockErrorResponses());
		Map<String, String> headers = new HashMap<String, String>();
		ResponseEntity<GeneralResponse<StandardResponse>> resp = 
				employeeController.deleteEmployee(headers, "1");
		assertNotNull(resp);
		assertNotNull(resp.getBody());
		assertNotNull(resp.getBody().getErrors());
		assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
	}
	
	@Test
	public void shouldOK_WhenGetEmployee() throws AssignmentException {
		Mockito.when(valiateUtils.validateToken(Mockito.anyMap()))
			.thenReturn(new ArrayList<ErrorResponse>());
		Mockito.when(employeeService.retrieveEmployee(Mockito.anyString()))
			.thenReturn(mockEmployeeList());
		Map<String, String> headers = new HashMap<String, String>();
		ResponseEntity<GeneralResponse<ArrayList<Employee>>> resp =
				employeeController.getEmployee(headers, "1");
		assertNotNull(resp);
		assertEquals(HttpStatus.OK, resp.getStatusCode());
		assertNotNull(resp.getBody());
		assertNotNull(resp.getBody().getData());
		assertFalse(resp.getBody().getData().isEmpty());
		verify(employeeService, times(1)).retrieveEmployee("1");
	}
	
	@Test
	public void shouldError_WhenGetEmployee() throws AssignmentException {
		Mockito.when(commonUtils.createErrorResponse(Mockito.any(GeneralResponse.class), Mockito.any(Throwable.class)))
			.thenReturn(mockErrorOnException());
		Mockito.when(employeeService.retrieveEmployee(Mockito.anyString()))
			.thenThrow(new AssignmentException("test exp"));
		Map<String, String> headers = new HashMap<String, String>();
		ResponseEntity<GeneralResponse<ArrayList<Employee>>> resp = 
				employeeController.getEmployee(headers, "1");
		assertNotNull(resp);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resp.getStatusCode());
		assertNotNull(resp.getBody());
		assertNotNull(resp.getBody().getErrors());
		assertFalse(resp.getBody().getErrors().isEmpty());
		verify(employeeService, times(1)).retrieveEmployee("1");
	}
	
	@Test
	public void shouldValidateFailed_WhenGetEmployee() throws AssignmentException {
		Mockito.when(valiateUtils.validateToken(Mockito.anyMap()))
			.thenReturn(mockErrorResponses());
		Map<String, String> headers = new HashMap<String, String>();
		ResponseEntity<GeneralResponse<ArrayList<Employee>>> resp = 
				employeeController.getEmployee(headers, "1");
		assertNotNull(resp);
		assertNotNull(resp.getBody());
		assertNotNull(resp.getBody().getErrors());
		assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
	}
	
	private StandardResponse mockSuccessStandardResponse() {
		return StandardResponse.builder()
				.responseCode(Constant.SUCCESS_CODE)
				.responseMessage("success")
				.build();
	}
	
	private GeneralResponse<?> mockErrorOnException() {
		GeneralResponse<?> resp = new GeneralResponse<>(); 
		resp.setErrors(new ArrayList<>());
		resp.getErrors().add(
				ErrorResponse.builder()
				.code(ErrorConstant.GENERAL_ERROR_CODE)
				.message("exception occurs")
				.build());
		return resp;
	}
	
	private List<Employee> mockEmployeeList() {
		List<Employee> list = new ArrayList<>();
		list.add(new Employee("1", "test1", "Manager", "Accounting", "Active", "3"));
		list.add(new Employee("2", "test2", "Staff", "Marketing", "Active", "1"));
		return list;
	}
	
	private List<ErrorResponse> mockErrorResponses() {
		final List<ErrorResponse> errors = new ArrayList<>();
		errors.add(ErrorResponse.builder()
				.code(ErrorConstant.VALIDATION_ERROR_CODE)
				.message("mandatory field is missing !")
				.build());
		return errors;
	}
}
