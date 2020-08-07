package com.allianz.assignment.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allianz.assignment.config.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@SuppressWarnings("serial")
@Component
public class JwtTokenUtil implements Serializable {
	@Autowired
	private JwtConfig jwtConfig;

	/**
	 * get token expiration
	 * @param token
	 * @return
	 */
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	/**
	 * generate token along with user
	 * @param userName
	 * @return
	 */
	public String generateToken(final String userName) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userName);
	}
	

	/**
	 * validate token
	 * @param token
	 * @param userName
	 * @return
	 */
	public Boolean validateToken(String token, final String userName) {
		final String user = getUsernameFromToken(token);
		return (user.equals(userName) && !isTokenExpired(token));
	}
	

	/**
	 * get userName from token
	 * @param token
	 * @return
	 */
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	/**
	 *  retrieve information from token which need a secret key
	 * @param token
	 * @return
	 */
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token).getBody();
	}
	
	/**
	 * checking that token is expired or not
	 * @param token
	 * @return
	 */
	private Boolean isTokenExpired(String token) {
		return getExpirationDateFromToken(token).before(new Date());
	}
	
	/**
	 * Define claims of the token with Issuer, Expiration and userName
	 * Sign the JWT by using the HS512 and secret key
	 * @param claims
	 * @param subject
	 * @return
	 */
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getTokenTTL()))
				.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret()).compact();
	}

}
