package com.allianz.assignment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.allianz.assignment.config.MessageConfig;
import com.allianz.assignment.constant.Constant;
import com.allianz.assignment.domain.Employee;
import com.allianz.assignment.domain.common.response.StandardResponse;
import com.allianz.assignment.exception.AssignmentException;
import com.allianz.assignment.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
	@InjectMocks
	private EmployeeService employeeService;
	@Mock
	private EmployeeRepository employeeRepository;
	@Mock
	private MessageConfig messageConfig;
	
	@BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void shouldSuccess_WhenGetAllEmployee() throws AssignmentException {
		Mockito.when(employeeRepository.findAll()).thenReturn(mockEmployeeList());
		List<Employee> result = employeeService.retrieveEmployee(null);
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(2, result.size());
	}
	
	@Test
	public void shouldSuccess_WhenGetEmployeeById() throws AssignmentException {
		Mockito.when(employeeRepository.findById(Mockito.anyString())).thenReturn(Optional.of(mockEmployeeList().get(0)));
		List<Employee> result = employeeService.retrieveEmployee("1");
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
		assertEquals("1", result.get(0).getId());
	}
	
	@Test
	public void shouldThrowException_WhenGetAllEmployee() {
		Mockito.when(employeeRepository.findAll()).thenThrow(new IllegalArgumentException("test exception"));
		assertThrows(AssignmentException.class, () -> employeeService.retrieveEmployee(null));
	}
	
	@Test
	public void shouldThrowException_WhenGetEmployeeById() {
		Mockito.when(employeeRepository.findById(Mockito.anyString())).thenThrow(new IllegalArgumentException("test exception"));
		assertThrows(AssignmentException.class, () -> employeeService.retrieveEmployee("2"));
	}
	
	@Test
	public void shouldSuccess_WhenCreateEmployee() throws AssignmentException {
		Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(mockEmployeeList().get(0));
		Mockito.when(messageConfig.getCreateSuccessMsg()).thenReturn("create employee successfully");
		Employee em1 = mockEmployeeList().get(0);
		StandardResponse resp = employeeService.createEmployee(em1);
		assertNotNull(resp);
		assertEquals(Constant.SUCCESS_CODE, resp.getResponseCode());
	}
	
	@Test
	public void shouldThrowException_WhenCreateEmployee() throws AssignmentException  {
		Mockito.when(employeeService.createEmployee(Mockito.any(Employee.class)))
			.thenThrow(new IllegalArgumentException("test exp"));
		assertThrows(AssignmentException.class, 
				() -> employeeService.createEmployee(mockEmployeeList().get(0)));
	}
	
	@Test
	public void shouldSuccess_WhenUpdateEmployee() throws AssignmentException {
		Mockito.when(employeeRepository.findById(Mockito.anyString()))
			.thenReturn(Optional.of(mockEmployeeList().get(0)));
		Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(mockEmployeeList().get(0));
		Employee em1 = mockEmployeeList().get(0);
		StandardResponse resp = employeeService.updateEmployee(em1);
		assertNotNull(resp);
		assertEquals(Constant.SUCCESS_CODE, resp.getResponseCode());
	}
	
	@Test
	public void shouldFail_WhenUpdateEmployee() throws AssignmentException {
		Mockito.when(employeeRepository.findById(Mockito.anyString()))
			.thenReturn(Optional.ofNullable(null));
		StandardResponse resp = employeeService.updateEmployee(mockEmployeeList().get(0));
		assertNotNull(resp);
		assertEquals(Constant.FAIL_CODE, resp.getResponseCode());
	}
	
	@Test
	public void shouldThrowException_WhenUpdateEmployee() throws AssignmentException {
		Mockito.when(employeeRepository.findById(Mockito.anyString()))
			.thenReturn(Optional.of(mockEmployeeList().get(0)));
		Mockito.when(employeeRepository.save(Mockito.any(Employee.class)))
			.thenThrow(new IllegalArgumentException("test exp"));
		assertThrows(AssignmentException.class, 
			() -> employeeService.updateEmployee(mockEmployeeList().get(0)));
	}
	
	@Test
	public void shouldSuccess_WhenDeleteEmployee() throws AssignmentException {
		StandardResponse resp = employeeService.deleteEmployeeById("1");
		assertNotNull(resp);
		assertEquals(Constant.SUCCESS_CODE, resp.getResponseCode());
	}
	
	@Test
	public void shouldThrowException_WhenDeleteEmployee() throws AssignmentException {
		Mockito.doThrow(new IllegalArgumentException(""))
			.when(employeeRepository).deleteById(Mockito.anyString());
		assertThrows(AssignmentException.class, 
				() -> employeeService.deleteEmployeeById("1"));
	}
	
	private List<Employee> mockEmployeeList() {
		List<Employee> list = new ArrayList<>();
		list.add(new Employee("1", "test1", "Manager", "Accounting", "Active", "3"));
		list.add(new Employee("2", "test2", "Staff", "Marketing", "Active", "1"));
		return list;
	}
}
