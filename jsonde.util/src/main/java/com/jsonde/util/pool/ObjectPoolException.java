package com.jsonde.util.pool;
/**
 * 
 * @author admin
 *
 */
public class ObjectPoolException extends Exception {

    public ObjectPoolException() {
    }

    public ObjectPoolException(String message) {
        super(message);
    }

    public ObjectPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectPoolException(Throwable cause) {
        super(cause);
    }

}
