package com.github.bajagit.notey.exception;

/**
 * Wrapper for {@link lotus.domino.NotesException} as unchecked Exception.
 * 
 * Largely NotesExceptions can not really be handled by the caller,
 * so declaring them is mostly useless.
 *
 */
public class NoteyRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoteyRuntimeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NoteyRuntimeException(Throwable arg0) {
		super(arg0);
	}


	public NoteyRuntimeException(String arg0) {
		super(arg0);
	}
}
