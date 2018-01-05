package com.jsonde.client.network;

import com.jsonde.api.Message;
import com.jsonde.api.MessageListener;
import com.jsonde.api.function.FunctionRequest;
import com.jsonde.api.function.FunctionResponse;
/**
 * 
 * @author admin
 *
 */
public interface NetworkClient {

	/**
	 * 
	 * @throws NetworkClientException
	 */
    void start() throws NetworkClientException;

    void stop() throws NetworkClientException;

    void sendMessage(Message message);

    //FunctionResponse invokeFunction(final FunctionRequest functionRequest) throws InterruptedException;
    
    /**
     * 
     * @param functionRequest
     * @return
     * @throws InterruptedException
     */
    <T extends FunctionResponse> T invokeFunction(final FunctionRequest<T> functionRequest) throws InterruptedException;

    void addMessageListener(MessageListener messageListener);

    void removeMessageListener(MessageListener messageListener);

}
