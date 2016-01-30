package net.realtoner.http;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author RyuIkHan
 */
public class HttpResponse {

    private int statusCode;

    private String content = null;

    private Map<String ,String> headerMap = new HashMap<>();

    public HttpResponse(){

    }

    void addHeader(String key , String value){
        headerMap.put(key , value);
    }

    public String getHeader(String name){

        return headerMap.get(name);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
