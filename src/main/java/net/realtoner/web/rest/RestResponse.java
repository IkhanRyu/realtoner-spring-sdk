package net.realtoner.web.rest;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author RyuIkHan
 */
public class RestResponse {

    private int responseCode = RestResponseCodes.OK;
    private String responseMessage = null;

    private Map<String , Object> resultMap = new HashMap<>();

    public RestResponse(){

    }

    public RestResponse(int responseCode , String responseMessage){
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public Map<String , Object> getResult(){
        return resultMap;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public void put(String key , Object value){
        resultMap.put(key , value);
    }
}
