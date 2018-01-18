package com.jsonde.api.function.heap;

import java.io.Serializable;
/**
 * 
 * @author admin
 *
 */
public class ClassHeapDataDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long constructorId;
    private long createCounter;
    private long collectCounter;
    private long totalCurrentSize;

    /**
     * 
     * @param constructorId
     * @param createCounter
     * @param collectCounter
     * @param totalCurrentSize
     */
    public ClassHeapDataDto(long constructorId, long createCounter, long collectCounter, long totalCurrentSize) {
        this.constructorId = constructorId;
        this.createCounter = createCounter;
        this.collectCounter = collectCounter;
        this.totalCurrentSize = totalCurrentSize;
    }

    /**
     * 
     * @return
     */
    public long getConstructorId() {
        return constructorId;
    }

    public void setConstructorId(long constructorId) {
        this.constructorId = constructorId;
    }

    public long getCreateCounter() {
        return createCounter;
    }

    public void setCreateCounter(long createCounter) {
        this.createCounter = createCounter;
    }

    public long getCollectCounter() {
        return collectCounter;
    }

    public void setCollectCounter(long collectCounter) {
        this.collectCounter = collectCounter;
    }

    public long getTotalCurrentSize() {
        return totalCurrentSize;
    }

    public void setTotalCurrentSize(long totalCurrentSize) {
        this.totalCurrentSize = totalCurrentSize;
    }

}
