package com.allianz.assignment.constant;

public class ErrorConstant {
	private ErrorConstant() {
		throw new IllegalStateException("Constant class");
	}
	public static final String VALIDATION_ERROR_CODE = "400";
	public static final String GENERAL_ERROR_CODE = "500";
	public static final String ERROR_DELIMITER = "##";
}
