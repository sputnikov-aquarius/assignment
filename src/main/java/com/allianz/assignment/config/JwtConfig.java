package com.allianz.assignment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Getter
@Configuration
public class JwtConfig {
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.token.ttl}")
	private Long tokenTTL;
}
