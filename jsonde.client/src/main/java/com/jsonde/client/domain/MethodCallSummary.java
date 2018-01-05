package com.jsonde.client.domain;
/**
 * 
 * @author admin
 *
 */
public class MethodCallSummary extends AbstractMethodCall {

	/**
	 * gfgd
	 */
    public Long executionTime;
    /**
     * long
     */
    public Long throwExceptionCounter;
    /**
     * long
     */
    public Long invocationCount;

    /**
     * 
     * @return
     */
    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }

    public Long getThrowExceptionCounter() {
        return throwExceptionCounter;
    }

    public void setThrowExceptionCounter(Long throwExceptionCounter) {
        this.throwExceptionCounter = throwExceptionCounter;
    }

    public Long getInvocationCount() {
        return invocationCount;
    }

    public void setInvocationCount(Long invocationCount) {
        this.invocationCount = invocationCount;
    }

    @Override
    public String toString() {
        return "MethodCallSummary{" +
                "executionTime=" + executionTime +
                ", throwExceptionCounter=" + throwExceptionCounter +
                ", invocationCount=" + invocationCount +
                "} " + super.toString();
    }
}