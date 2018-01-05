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
    public abstract void sendMessage(Message registerMethodMessage);
    public abstract long generateClassId(ClassLoader classLoader, String className);
    public abstract long generateClassIdAndRegisterIfAbsent(Class clazz);
	
    /**
     * nonAbstract
     */
    public void nonAbstract() {
		
	}
}
