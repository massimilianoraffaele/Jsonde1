package com.jsonde.api;

import java.util.EventListener;
/**
 * 
 * @author admin
 *
 */
public interface MessageListener extends EventListener {
	
	/**
	 * 
	 * @param message
	 * onMessage
	 */
    void onMessage(Message message);

}
