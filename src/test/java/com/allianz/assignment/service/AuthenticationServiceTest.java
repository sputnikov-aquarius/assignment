package com.allianz.assignment.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.StringUtils;

import com.allianz.assignment.domain.Authentication;
import com.allianz.assignment.domain.request.AuthenticationRequest;
import com.allianz.assignment.util.JwtTokenUtil;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
	@InjectMocks
	private AuthenticationService authenticationService;
	@Mock
	private JwtTokenUtil jwtTokenUtil;
	
	@BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void shouldPass_WhenAuthenticate() {
		Mockito.when(jwtTokenUtil.generateToken(Mockito.anyString()))
			.thenReturn("xxxxx");
		Authentication auth = authenticationService.authenticate(mockPassRequest());
		assertNotNull(auth);
		assertTrue(auth.isAuthenticate());
		assertFalse(StringUtils.isEmpty(auth.getAccessToken()));
	}
	
	@Test
	public void shouldFail_WhenAuthenticate() {
		Authentication auth = authenticationService.authenticate(mockFailRequest());
		assertNotNull(auth);
		assertFalse(auth.isAuthenticate());
		assertTrue(StringUtils.isEmpty(auth.getAccessToken()));
	}
	
	private AuthenticationRequest mockPassRequest() {
		return new AuthenticationRequest("admin", "nimda");
	}
	
	private AuthenticationRequest mockFailRequest() {
		return new AuthenticationRequest("test", "test");
	}
}
