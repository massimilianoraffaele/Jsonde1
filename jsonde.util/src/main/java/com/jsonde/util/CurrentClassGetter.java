package com.jsonde.util;
/**
 * 
 * @author admin
 *
 */
public class CurrentClassGetter extends SecurityManager {

    public Class getCallerClass(int depth) {
        return getClassContext()[depth + 1];
    }

}
