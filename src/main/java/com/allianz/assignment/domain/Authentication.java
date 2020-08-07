package com.allianz.assignment.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class Authentication implements Serializable {
	@JsonProperty("authenticate")
	private boolean authenticate;
	@JsonProperty("access-token")
	private String accessToken;
}
