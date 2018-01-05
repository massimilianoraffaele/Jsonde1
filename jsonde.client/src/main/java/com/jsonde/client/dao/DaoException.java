package com.jsonde.client.dao;
/**
 * 
 * @author admin
 *
 */
public class DaoException extends Exception {

	/**
	 * DaoException
	 */
    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
