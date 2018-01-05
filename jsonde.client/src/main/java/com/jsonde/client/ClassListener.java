package com.jsonde.client;

import com.jsonde.client.domain.Clazz;

import java.util.EventListener;
/**
 * 
 * @author admin
 *
 */
public interface ClassListener extends EventListener {

	/**
	 * 
	 * @param clazz
	 * onRegisterClass
	 */	
    void onRegisterClass(Clazz clazz);

}