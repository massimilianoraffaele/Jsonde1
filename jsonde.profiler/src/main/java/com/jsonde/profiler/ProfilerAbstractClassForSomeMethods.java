package com.jsonde.profiler;

import com.jsonde.api.Message;

/**
 * 
 * @author admin
 *
 */
public abstract class ProfilerAbstractClassForSomeMethods {

	/**
	 * 
	 * @param classId
	 * @param clazz
	 */
    public abstract void describeRedefinableClass(long classId, Class clazz);
    /**
     * 
     * @param registerMethodMessage
     */
    public abstract void sendMessage(Message registerMethodMessage);
    /**
     * 
     * @param classLoader
     * @param className
     * @return
     */
    public abstract long generateClassId(ClassLoader classLoader, String className);
    /**
     * 
     * @param clazz
     * @return
     */
    public abstract long generateClassIdAndRegisterIfAbsent(Class clazz);
	
    /**
     * nonAbstract
     */
    public void nonAbstract() {
		
	}
}
