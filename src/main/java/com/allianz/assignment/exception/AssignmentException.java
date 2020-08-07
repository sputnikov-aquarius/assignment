package com.allianz.assignment.exception;

@SuppressWarnings("serial")
public class AssignmentException extends Exception {
	public AssignmentException() {
		super();
	}
	
	public AssignmentException(final String error) {
		super(error);
	}
	
	public AssignmentException(final String message, final Throwable th) {
		super(message, th);
	}
}
