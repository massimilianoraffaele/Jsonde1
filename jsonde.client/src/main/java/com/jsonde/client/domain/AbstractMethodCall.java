package com.jsonde.client.domain;
/**
 * 
 * @author admin
 *
 */
public abstract class AbstractMethodCall extends DomainObject {

	/**
	 * callerId
	 */
    protected Long callerId;
    protected Long methodId;

    /**
     * 
     * @return
     */
    public Long getCallerId() {
        return callerId;
    }

    public void setCallerId(Long callerId) {
        this.callerId = callerId;
    }

    public Long getMethodId() {
        return methodId;
    }

    public void setMethodId(Long methodId) {
        this.methodId = methodId;
    }

    @Override
    public String toString() {
        return "AbstractMethodCall{" +
                "callerId=" + callerId +
                ", methodId=" + methodId +
                "} " + super.toString();
    }
}
