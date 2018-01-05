package com.jsonde.client;

import com.jsonde.client.domain.MethodCall;

import java.util.EventListener;
/**
 * 
 * @author admin
 *
 */
public interface MethodCallListener extends EventListener {

	/**
	 * 
	 * @param methodCall
	 */	
    void onMethodCall(MethodCall methodCall);

}
