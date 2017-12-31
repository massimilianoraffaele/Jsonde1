package com.jsonde.client;

import com.jsonde.api.methodCall.MethodCallDto;
import com.jsonde.api.methodCall.MethodCallMessage;
import com.jsonde.api.methodCall.MethodCallSummaryDto;
import com.jsonde.client.dao.DaoException;
import com.jsonde.client.dao.DaoFactory;
import com.jsonde.client.domain.MethodCall;
import com.jsonde.client.domain.TelemetryData;
/**
 * 
 * @author admin
 *
 */
public class MethodCallMessageHandler implements MessageHandler<MethodCallMessage> {
	
	public void onMessage(MethodCallMessage message){

        MethodCallMessage methodCallMessage =
                (MethodCallMessage) message;

        boolean complete = methodCallMessage.isComplete();

        MethodCallDto[] methodCallDtos =
                methodCallMessage.getMethodCallDtos();

        MethodCall methodCall = null;

        try {
            methodCall = DaoFactory.getMethodCallDao().
                    persistMethodCallDtos(methodCallDtos);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        MethodCallSummaryDto methodCallSummaryDto = methodCallMessage.getMethodCallSummaryDto();

        try {
            DaoFactory.getMethodCallSummaryDao().processMethodCallSummaryDto(methodCallSummaryDto);
       
        } catch (DaoException e) {
            e.printStackTrace();
        }

        /*if (complete || message instanceof TelemetryDataMessage) {
            createTopMethodCall(methodCall);
        }

        TelemetryDataMessage telemetryDataMessage =
                (TelemetryDataMessage) message;

        TelemetryDataDto dto = telemetryDataMessage.getTelemetryData();

        TelemetryData telemetryData = new TelemetryData();

        telemetryData.setId(telemetryDataIdGenerator.getAndIncrement());
        telemetryData.setTime(dto.time);

        {
            // memory
            telemetryData.setFreeMemory(dto.freeMemory);
            telemetryData.setMaxMemory(dto.maxMemory);
            telemetryData.setTotalMemory(dto.totalMemory);
        }

        {
            // class loading
            telemetryData.setLoadedClassCount(dto.loadedClassCount);
            telemetryData.setClassCount(dto.classCount);
            telemetryData.setUnloadedClassCount(dto.unloadedClassCount);
        }

        {
            // compilation
            telemetryData.setTotalCompilationTime(dto.totalCompilationTime);
        }

        try {
            DaoFactory.getTelemetryDataDao().insert(telemetryData);
        } catch (DaoException e) {
            e.printStackTrace();
        }
		*/

	}

}
