package com.jsonde.client.network;
/**
 * 
 * @author admin
 *
 */
public class NetworkClientException extends Exception {

	/**
	 * NetworkClientException
	 */
    public NetworkClientException() {
    }

    public NetworkClientException(String message) {
        super(message);
    }

    public NetworkClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public NetworkClientException(Throwable cause) {
        super(cause);
    }

}
