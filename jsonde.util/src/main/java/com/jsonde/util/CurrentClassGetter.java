package com.jsonde.util;
/**
 * 
 * @author admin
 *
 */
public class CurrentClassGetter extends SecurityManager {

	/**
	 * 
	 * @param depth
	 * @return
	 */
    public Class getCallerClass(int depth) {
        return getClassContext()[depth + 1];
    }

}
