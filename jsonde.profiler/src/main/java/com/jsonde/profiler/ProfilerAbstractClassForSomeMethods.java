package com.jsonde.profiler;

import com.jsonde.api.Message;

public abstract class ProfilerAbstractClassForSomeMethods {

    public abstract void describeRedefinableClass(long classId, Class clazz);
    public abstract void sendMessage(Message registerMethodMessage);
    public abstract long generateClassId(ClassLoader classLoader, String className);
    public abstract long generateClassIdAndRegisterIfAbsent(Class clazz);
	public void NonAbstract() {
		
	}
}
