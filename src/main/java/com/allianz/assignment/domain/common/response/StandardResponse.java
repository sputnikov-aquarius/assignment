package com.allianz.assignment.domain.common.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
public class StandardResponse implements Serializable {
	private String responseCode;
	private String responseMessage;
}
