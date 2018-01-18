package com.jsonde.profiler.heap;
/**
 * 
 * @author admin
 *
 */
public class ClassHeapData {

    private long createCounter;
    private long collectCounter;
    private long totalCurrentSize;

    /**
     * 
     * @param objectSize
     */
    public void newObject(long objectSize) {
    	synchronized (this){
    	totalCurrentSize += objectSize;
        createCounter++;
    	}
    }
    public void collectObject(long objectSize) {
    	synchronized (this){
    	totalCurrentSize -= objectSize;
        collectCounter++;
    	}
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

    @Override
    public String toString() {
        return "ClassHeapData{" +
                "createCounter=" + createCounter +
                ", collectCounter=" + collectCounter +
                ", totalCurrentSize=" + totalCurrentSize +
                '}';
    }

}
