package com.allianz.assignment.util;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.allianz.assignment.constant.ErrorConstant;
import com.allianz.assignment.domain.common.response.ErrorResponse;
import com.allianz.assignment.domain.common.response.GeneralResponse;

@Component
public class CommonUtils {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GeneralResponse createErrorResponse(GeneralResponse resp, Throwable th) {
		resp.setErrors(new ArrayList<>());
		resp.getErrors().add(
				ErrorResponse.builder()
				.code(ErrorConstant.GENERAL_ERROR_CODE)
				.message(th.getMessage())
				.build());
		return resp;
	}
	
	
}
