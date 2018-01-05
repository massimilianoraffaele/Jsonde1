package com.jsonde.profiler.network;
/**
 * 
 * @author admin
 *
 */
public class NetworkServerException extends Exception {

	/**
	 * NetworkServerException
	 */
    public NetworkServerException() {
    }

    public NetworkServerException(String message) {
        super(message);
    }

    public NetworkServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public NetworkServerException(Throwable cause) {
        super(cause);
    }
}
