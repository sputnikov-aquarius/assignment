package com.allianz.assignment.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.allianz.assignment.constant.ErrorConstant;
import com.allianz.assignment.domain.Authentication;
import com.allianz.assignment.domain.common.response.ErrorResponse;
import com.allianz.assignment.domain.common.response.GeneralResponse;
import com.allianz.assignment.domain.request.AuthenticationRequest;
import com.allianz.assignment.service.AuthenticationService;
import com.allianz.assignment.util.CommonUtils;
import com.allianz.assignment.util.ValidateUtils;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {
	@InjectMocks
	private AuthenticationController authenticationController;
	@Mock
	private AuthenticationService authenticationService;
	@Mock
	private CommonUtils commonUtils;
	@Mock
	private ValidateUtils valiateUtils;
	
	@Test
	public void shouldOK_WhenAuthenticate() {
		Mockito.when(valiateUtils.validateCredential(Mockito.any(AuthenticationRequest.class)))
			.thenReturn(new ArrayList<ErrorResponse>());
		Mockito.when(authenticationService.authenticate(Mockito.any(AuthenticationRequest.class)))
			.thenReturn(mockPassAuthen());
		ResponseEntity<GeneralResponse<Authentication>> resp = 
				authenticationController.authenticate(new AuthenticationRequest("test", "test"));
		assertNotNull(resp);
		assertEquals(HttpStatus.OK, resp.getStatusCode());
		assertNotNull(resp.getBody());
		assertNotNull(resp.getBody().getData());
		assertNotNull(resp.getBody().getData().getAccessToken());
		assertTrue(resp.getBody().getData().isAuthenticate());
		verify(authenticationService, times(1)).authenticate(new AuthenticationRequest("test", "test"));
	}
	
	@Test
	public void shouldFail_WhenAuthenticate() {
		Mockito.when(valiateUtils.validateCredential(Mockito.any(AuthenticationRequest.class)))
			.thenReturn(new ArrayList<ErrorResponse>());
		Mockito.when(authenticationService.authenticate(Mockito.any(AuthenticationRequest.class)))
			.thenReturn(mockFailAuthen());
		ResponseEntity<GeneralResponse<Authentication>> resp = 
				authenticationController.authenticate(new AuthenticationRequest("test", "test"));
		assertNotNull(resp);
		assertEquals(HttpStatus.OK, resp.getStatusCode());
		assertNotNull(resp.getBody());
		assertNotNull(resp.getBody().getData());
		assertNull(resp.getBody().getData().getAccessToken());
		assertFalse(resp.getBody().getData().isAuthenticate());
		verify(authenticationService, times(1)).authenticate(new AuthenticationRequest("test", "test"));
	}
	
	@Test
	public void shouldFailOnValidate_WhenAuthenticate() {
		Mockito.when(valiateUtils.validateCredential(Mockito.any(AuthenticationRequest.class)))
			.thenReturn(mockErrorResponses());
		ResponseEntity<GeneralResponse<Authentication>> resp = 
				authenticationController.authenticate(new AuthenticationRequest("test", "test"));
		assertNotNull(resp);
		assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
		assertNotNull(resp.getBody());
		assertNotNull(resp.getBody().getErrors());
		assertFalse(resp.getBody().getErrors().isEmpty());
		verify(authenticationService, times(0)).authenticate(new AuthenticationRequest("test", "test"));
	}
	
	private Authentication mockPassAuthen() {
		return Authentication.builder()
				.authenticate(true)
				.accessToken("xxxxx")
				.build();
	}
	
	private Authentication mockFailAuthen() {
		return Authentication.builder()
				.authenticate(false)
				.accessToken(null)
				.build();
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
