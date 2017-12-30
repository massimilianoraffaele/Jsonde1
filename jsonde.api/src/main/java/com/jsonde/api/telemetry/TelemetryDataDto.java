package com.jsonde.api.telemetry;

import java.io.Serializable;
/**
 * 
 * @author admin
 *
 */
public class TelemetryDataDto implements Serializable {

    private static final long serialVersionUID = -4872627251132927940L;

    /**
     * gfdfg
     */
    public long time;
    public long freeMemory;
    public long maxMemory;
    public long totalMemory;

    public int loadedClassCount;
    public long classCount;
    public long unloadedClassCount;

    public long totalCompilationTime;

}