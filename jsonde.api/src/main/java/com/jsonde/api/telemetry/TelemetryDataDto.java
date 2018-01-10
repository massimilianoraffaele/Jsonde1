package com.jsonde.api.telemetry;

import java.io.Serializable;
/**
 * 
 * @author admin
 *
 */
public class TelemetryDataDto implements Serializable {

	public TelemetryDataDto(){
		
	}
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * gfdfg
     */
    public long time;
    /**
     * long
     */
    public long freeMemory;
/**
 * long
 */
    public long maxMemory;
    /**long
     * 
     */
    public long totalMemory;
/**
 * int
 */
    public int loadedClassCount;
   /**
    * long
    */
    public long classCount;
    /**
     * long
     */
    public long unloadedClassCount;
/**
 * long
 */
    public long totalCompilationTime;

}