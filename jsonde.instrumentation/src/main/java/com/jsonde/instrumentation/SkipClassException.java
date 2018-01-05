package com.jsonde.instrumentation;
/**
 * 
 * @author admin
 *
 */
public class SkipClassException extends Exception {

	/**
	 * SkipClassException
	 */
    public SkipClassException() {
    }

    public SkipClassException(String message) {
        super(message);
    }

    public SkipClassException(String message, Throwable cause) {
        super(message, cause);
    }

    public SkipClassException(Throwable cause) {
        super(cause);
    }
}
