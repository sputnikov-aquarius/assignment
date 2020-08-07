package com.allianz.assignment.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.allianz.assignment.config.MessageConfig;
import com.allianz.assignment.constant.ErrorConstant;
import com.allianz.assignment.domain.Employee;
import com.allianz.assignment.domain.common.response.ErrorResponse;
import com.allianz.assignment.domain.request.AuthenticationRequest;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class ValidateUtils {
	@Autowired
	private MessageConfig messagesConfig;
	@Autowired JwtTokenUtil jwtTokenUtils;
	
	public List<ErrorResponse> validate(Map<String, String> headers, Employee e) {
		List<ErrorResponse> errors = new ArrayList<>();
		errors = validateToken(headers);
		if (StringUtils.isEmpty(e.getId())) 
			errors.add(ErrorResponse.builder()
					.code(ErrorConstant.VALIDATION_ERROR_CODE)
					.message(messagesConfig.getEmployeeIdMissing())
					.build());
		if (StringUtils.isEmpty(e.getName()))
			errors.add(ErrorResponse.builder()
					.code(ErrorConstant.VALIDATION_ERROR_CODE)
					.message(messagesConfig.getEmployeeNameMissing())
					.build());
		
		return errors;
	}
	
	public List<ErrorResponse> validateToken(Map<String, String> headers) {
		List<ErrorResponse> errors = new ArrayList<>();
		final String requestTokenHeader = headers.get("authorization");
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			final String token = requestTokenHeader.substring(7);
			try {
				final String username = jwtTokenUtils.getUsernameFromToken(token);
				if (!jwtTokenUtils.validateToken(token, username)) {
					errors.add(ErrorResponse.builder()
							.code(ErrorConstant.VALIDATION_ERROR_CODE)
							.message(messagesConfig.getUnauthorize())
							.build());
				}
			} catch (IllegalArgumentException | ExpiredJwtException e) {
				if (e instanceof IllegalArgumentException)
					errors.add(ErrorResponse.builder()
							.code(ErrorConstant.VALIDATION_ERROR_CODE)
							.message(messagesConfig.getTokenOutOfReach())
							.build());
				if (e instanceof ExpiredJwtException)
					errors.add(ErrorResponse.builder()
							.code(ErrorConstant.VALIDATION_ERROR_CODE)
							.message(messagesConfig.getTokenExpired())
							.build());
			}
		} else {
			errors.add(ErrorResponse.builder()
					.code(ErrorConstant.VALIDATION_ERROR_CODE)
					.message(messagesConfig.getUnauthorize())
					.build());
		}
		
		return errors;
	}
	
	public List<ErrorResponse> validate(Map<String, String> headers, final String id) {
		List<ErrorResponse> errors = new ArrayList<>();
		errors = validateToken(headers);
		if (StringUtils.isEmpty(id)) 
			errors.add(ErrorResponse.builder()
					.code(ErrorConstant.VALIDATION_ERROR_CODE)
					.message(messagesConfig.getEmployeeIdMissing())
					.build());
		
		return errors;
	}
	
	public List<ErrorResponse> validateCredential(AuthenticationRequest req) {
		List<ErrorResponse> errors = new ArrayList<>();
		if (StringUtils.isEmpty(req.getUserName()))
			errors.add(ErrorResponse.builder()
					.code(ErrorConstant.VALIDATION_ERROR_CODE)
					.message(messagesConfig.getUserNameMissing())
					.build());
		if (StringUtils.isEmpty(req.getPassword())) 
			errors.add(ErrorResponse.builder()
					.code(ErrorConstant.VALIDATION_ERROR_CODE)
					.message(messagesConfig.getPwdMissing())
					.build());
		return errors;
	}
	
	
}
