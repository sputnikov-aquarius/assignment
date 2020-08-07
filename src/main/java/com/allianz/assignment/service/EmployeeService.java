package com.allianz.assignment.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.allianz.assignment.config.MessageConfig;
import com.allianz.assignment.constant.Constant;
import com.allianz.assignment.domain.Employee;
import com.allianz.assignment.domain.common.response.StandardResponse;
import com.allianz.assignment.exception.AssignmentException;
import com.allianz.assignment.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private MessageConfig messageConfig;

    public  List<Employee> retrieveEmployee(final String id) throws AssignmentException {
    	try {
    		if (StringUtils.isEmpty(id)) {
    			return (List<Employee>) employeeRepository.findAll();
    		} else {
    			return Arrays.asList(employeeRepository.findById(id).orElse(new Employee()));
    		}
    	} catch (Exception e) {
			log.error("error while retrieving employee", e);
    		throw new AssignmentException("error while retrieving employee", e);
		}
    }
    
    public StandardResponse createEmployee(Employee employee) throws AssignmentException {
    	try {
    		employeeRepository.save(employee);
    		return StandardResponse.builder()
    				.responseCode(Constant.SUCCESS_CODE)
    				.responseMessage(messageConfig.getCreateSuccessMsg())
    				.build();
    	} catch (Exception e) {
    		log.error("error while creating employee", e);
    		throw new AssignmentException("error while creating employee", e);
    	}
    }
    
    public StandardResponse updateEmployee(Employee employee) throws AssignmentException {
    	try {
    		Employee tmpEmployee = employeeRepository.findById(employee.getId()).orElse(null);
    		if (tmpEmployee != null) {
    			employeeRepository.save(employee);
    			return StandardResponse.builder()
        				.responseCode(Constant.SUCCESS_CODE)
        				.responseMessage(messageConfig.getUpdateSuccessMsg())
        				.build();
    		} else {
    			return StandardResponse.builder()
        				.responseCode(Constant.FAIL_CODE)
        				.responseMessage(messageConfig.getEmployeeNotExist())
        				.build();
    		}
    	} catch (Exception e) {
    		log.error("error while updating employee", e);
    		throw new AssignmentException("error while updating employee", e);
    	}
    }
    
    public StandardResponse deleteEmployeeById(final String id) throws AssignmentException {
        try {
        	employeeRepository.deleteById(id);
            return StandardResponse.builder()
            		.responseCode(Constant.SUCCESS_CODE)
            		.responseMessage(messageConfig.getDeleteSuccessMsg())
            		.build();
        } catch (Exception e) {
        	log.error("cannot deleting employee :", e);
        	throw new AssignmentException("error while deleting employee", e);
        }
    }
}
