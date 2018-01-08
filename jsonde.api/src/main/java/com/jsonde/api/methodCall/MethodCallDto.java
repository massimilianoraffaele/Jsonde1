package com.jsonde.api.methodCall;

import java.io.Serializable;
/**
 * 
 * @author admin
 *
 */
public class MethodCallDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * dsad
	 */
	public static final byte RETURN_VALUE_FLAG = 0;
	/**
	 * throw
	 */
    public static final byte THROW_EXCEPTION_FLAG = 1;
    /**
     * caller
     */
    public static final byte CALLER_ID_SET_FLAG = 2;
    /**
     * actual
     */
    public static final byte ACTUAL_CLASS_ID_SET_FLAG = 3;
/**
 * flags
 */
    public byte flags;

    /**
     * long
     */
    public long methodCallId;
    /**
     * long
     */
    public long callerId;
/**
 * long
 */
    public long methodId;
    /**
     * long
     */
    public long actualClassId;
/**
 * long
 */
    public long executionTime;

    /**
     * returnToPool
     */
    public void returnToPool() {
        MethodCallDtoFactory.returnMethodCallDtoToPool(this);
    }

    @Override
    public String toString() {
        return "MethodCallDto{" +
                "flags=" + flags +
                ", methodCallId=" + methodCallId +
                ", callerId=" + callerId +
                ", methodId=" + methodId +
                ", actualClassId=" + actualClassId +
                ", executionTime=" + executionTime +
                '}';
    }
}