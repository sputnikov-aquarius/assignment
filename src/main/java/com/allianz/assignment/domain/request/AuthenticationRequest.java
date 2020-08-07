package com.allianz.assignment.domain.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class AuthenticationRequest implements Serializable {
	@JsonProperty("username")
	private String userName;
	@JsonProperty("password")
	private String password;
}
