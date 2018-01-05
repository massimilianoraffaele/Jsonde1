package com.jsonde.api.function.echo;

import com.jsonde.api.function.FunctionResponse;
/**
 * 
 * @author admin
 *
 */
public class EchoFunctionResponse extends FunctionResponse<EchoFunctionRequest> {

    private String message;

    /**
     * 
     * @param request
     */
    public EchoFunctionResponse(EchoFunctionRequest request) {
        super(request);
        message = request.getMessage();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
