package com.jsonde.api.telemetry;

import com.jsonde.api.Message;
/**
 * 
 * @author admin
 *
 */
public class TelemetryDataMessage extends Message {

    private TelemetryDataDto telemetryDataDto;

    /**
     * 
     * @param telemetryDataDto
     */
    public TelemetryDataMessage(TelemetryDataDto telemetryDataDto) {
        this.telemetryDataDto = telemetryDataDto;
    }

    public TelemetryDataDto getTelemetryData() {
        return telemetryDataDto;
    }

}
