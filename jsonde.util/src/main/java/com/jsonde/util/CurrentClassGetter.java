package com.jsonde.util;
/**
 * 
 * @author admin
 *
 */
public class CurrentClassGetter {

	/**
	 * 
	 * @param depth
	 * @return
	 */
    public Class getCallerClass(int depth) {
        return this.getClass();
    }

}
