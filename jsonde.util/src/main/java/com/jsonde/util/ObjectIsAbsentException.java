package com.jsonde.util;
/**
 * 
 * @author admin
 *
 */
public class ObjectIsAbsentException extends Exception {

	/**
	 * ObjectIsAbsentException
	 */
    public ObjectIsAbsentException() {
    }

    public ObjectIsAbsentException(String message) {
        super(message);
    }

    public ObjectIsAbsentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectIsAbsentException(Throwable cause) {
        super(cause);
    }

}
