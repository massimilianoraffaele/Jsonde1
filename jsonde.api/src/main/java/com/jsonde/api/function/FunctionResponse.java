package com.jsonde.api.function;

import com.jsonde.api.Message;
/**
 * 
 * @author admin
 *
 * @param <T>
 */
public abstract class FunctionResponse<T extends FunctionRequest> extends Message {

    private final long requestId;

    protected FunctionResponse(long responseId) {
        this.requestId = responseId;
    }

    protected FunctionResponse(FunctionRequest request) {
        requestId = request.getRequestId();
    }

    /**
     * 
     * @return
     */
    public long getRequestId() {
        return requestId;
    }

}