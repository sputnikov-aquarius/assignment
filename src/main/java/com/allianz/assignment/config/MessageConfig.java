package com.allianz.assignment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Getter
@Configuration
public class MessageConfig {
	@Value("${employee.create.success}")
	private String createSuccessMsg;
	@Value("${employee.update.success}")
	private String updateSuccessMsg;
	@Value("${employee.delete.success}")
	private String deleteSuccessMsg;
	
	@Value("${employee.create.fail}")
	private String createFailMsg;
	@Value("${employee.update.fail}")
	private String updateFailMsg;
	@Value("${employee.delete.fail}")
	private String deleteFailMsg;
	
	@Value("${employee.not.exist}")
	private String employeeNotExist;
	@Value("${employee.id.missing}")
	private String employeeIdMissing;
	@Value("${employee.name.missing}")
	private String employeeNameMissing;
	
	@Value("${user.missing}")
	private String userNameMissing;
	@Value("${pwd.missing}")
	private String pwdMissing;
	
	@Value("${token.unauthorize}")
	private String unauthorize;
	@Value("${token.expired}")
	private String tokenExpired;
	@Value("${token.outofreach}")
	private String tokenOutOfReach;
}
