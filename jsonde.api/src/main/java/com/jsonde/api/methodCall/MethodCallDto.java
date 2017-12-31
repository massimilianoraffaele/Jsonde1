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
	private static final long serialVersionUID = 6140934579333095367L;
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

    public byte flags;

    public long methodCallId;
    public long callerId;

    public long methodId;
    public long actualClassId;

    public long executionTime;

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