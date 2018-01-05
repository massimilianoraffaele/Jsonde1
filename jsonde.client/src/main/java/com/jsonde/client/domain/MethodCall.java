package com.jsonde.client.domain;
/**
 * 
 * @author admin
 *
 */
public class MethodCall extends AbstractMethodCall {

    private Long actualClassId;
    

    /**
     * 
     * @return
     */
    public Long getActualClassId() {
        return actualClassId;
    }

    public void setActualClassId(Long actualClassId) {
        this.actualClassId = actualClassId;
    }

    @Override
    public String toString() {
        return "MethodCall{" +
                "actualClassId=" + actualClassId +
                "} " + super.toString();
    }
}
