package com.jsonde.client;

import java.sql.SQLException;

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
 *massimo
 */
public class MethodCallMessageHandler implements MessageHandler<MethodCallMessage> {
	
	/**
	 * 
	 * @param message
	 */
	public void onMessage(MethodCallMessage message){

        MethodCallMessage methodCallMessage =
                (MethodCallMessage) message;


        MethodCallDto[] methodCallDtos =
                methodCallMessage.getMethodCallDtos();

        MethodCall methodCall = null;

        try {
            try {
				methodCall = DaoFactory.getMethodCallDao().
				        persistMethodCallDtos(methodCallDtos);
			} catch (SQLException e) {
			}
        } catch (DaoException e) {
        	System.out.println("Something was wrong");
        }

        MethodCallSummaryDto methodCallSummaryDto = methodCallMessage.getMethodCallSummaryDto();

        try {
            try {
				DaoFactory.getMethodCallSummaryDao().processMethodCallSummaryDto(methodCallSummaryDto);
			} catch (SQLException e) {
			}
       
        } catch (DaoException e) {
        	System.out.println("Something was wrong");
        }

	}

}
