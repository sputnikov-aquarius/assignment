package com.allianz.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.allianz.assignment.domain.Authentication;
import com.allianz.assignment.domain.common.response.ErrorResponse;
import com.allianz.assignment.domain.common.response.GeneralResponse;
import com.allianz.assignment.domain.request.AuthenticationRequest;
import com.allianz.assignment.service.AuthenticationService;
import com.allianz.assignment.util.CommonUtils;
import com.allianz.assignment.util.ValidateUtils;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1")
public class AuthenticationController {
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private CommonUtils commonUtils;
	@Autowired
	private ValidateUtils validateUtils;
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "authenticate", notes = "mocker user=admin, pwd=nimda only")
	@PostMapping("/authenticate")
	public ResponseEntity<GeneralResponse<Authentication>> authenticate(@RequestBody AuthenticationRequest request) {
		log.info("start authenticate");
		GeneralResponse<Authentication> resp = new GeneralResponse<>();
		try {
			List<ErrorResponse> errors = validateUtils.validateCredential(request);
			if (errors.isEmpty()) {
				resp.setData(authenticationService.authenticate(request));
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
