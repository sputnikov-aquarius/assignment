package com.allianz.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allianz.assignment.domain.Authentication;
import com.allianz.assignment.domain.request.AuthenticationRequest;
import com.allianz.assignment.util.JwtTokenUtil;

@Service
public class AuthenticationService {
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public Authentication authenticate(AuthenticationRequest req) {
			if (!("admin".equalsIgnoreCase(req.getUserName()) && "nimda".equalsIgnoreCase(req.getPassword()))) {
				return Authentication.builder()
						.authenticate(false)
						.accessToken(null)
						.build();
			}
			return Authentication.builder()
					.authenticate(true)
					.accessToken(jwtTokenUtil.generateToken(req.getUserName()))
					.build();
	}

		
}
