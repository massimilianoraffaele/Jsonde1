package com.jsonde.gui.reports;
/**
 * 
 * @author admin
 *
 */
public class ReportException extends Exception {

	/**
	 * ReportException
	 */
    public ReportException() {
    }

    public ReportException(String message) {
        super(message);
    }

    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportException(Throwable cause) {
        super(cause);
    }
    
}
