package com.jsonde.api.methodCall;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author admin
 *
 */
public class MethodCallSummaryDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<Long, MethodCallSummaryDto> callees = new HashMap<Long, MethodCallSummaryDto>();
	/**
	 * gffdg
	 */
    public MethodCallSummaryDto caller;

    /**
     * long
     */
    public long methodId;
    /**
     * long
     */
    public long executionTime;
    /**
     * long
     */
    public transient long startTime;
    /**
     * int
     */
    public int exceptionCount;
    /**
     * int
     */
    public int invocationCount;

    /**
     * 
     * @param callee
     */
    public void addCallee(MethodCallSummaryDto callee) {
        callee.caller = this;
        callees.put(callee.methodId, callee);
    }

    public MethodCallSummaryDto getCallee(long methodId3) {

        if (!callees.containsKey(methodId)) {
            MethodCallSummaryDto callee = new MethodCallSummaryDto();
            callee.methodId = methodId;
            addCallee(callee);
        }

        return callees.get(methodId);
    }

    public Collection<MethodCallSummaryDto> getCallees() {
        return callees.values();
    }

    @Override
    public String toString() {
        return "MethodCallSummaryDto{" +
                "methodId=" + methodId +
                ", executionTime=" + executionTime +
                ", exceptionCount=" + exceptionCount +
                ", invocationCount=" + invocationCount +
                ", callees=" + callees +
                '}';
    }
}