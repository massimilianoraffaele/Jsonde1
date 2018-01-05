package com.jsonde.instrumentation;
/**
 * 
 * @author admin
 *
 */
public class ByteCodeTransformException extends Exception {

	/**
	 * ByteCodeTransformException
	 */
    public ByteCodeTransformException() {
    }

    public ByteCodeTransformException(String message) {
        super(message);
    }

    public ByteCodeTransformException(String message, Throwable cause) {
        super(message, cause);
    }

    public ByteCodeTransformException(Throwable cause) {
        super(cause);
    }

}
