package com.allianz.assignment.domain.common.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
public class GeneralResponse<T extends Serializable> implements Serializable {
	@JsonProperty("errors")
	private List<ErrorResponse> errors;
	@JsonProperty("data")
	private T data;
}
