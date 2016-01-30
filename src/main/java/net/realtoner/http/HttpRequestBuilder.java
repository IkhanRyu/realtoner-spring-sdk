package net.realtoner.http;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author RyuIkHan
 */
public class HttpRequestBuilder {

    private HttpRequest.Method method = HttpRequest.Method.GET;

    private String protocol = null;
    private String host = null;
    private String port = null;
    private String path = null;

    private String url = null;

    private String stringContent = null;
    private Map<String ,Object> parameterMap = new HashMap<>();

    private String contentType = "application/x-www-form-urlencoded; charset=UTF-8";
    private Map<String , String> headerMap = new HashMap<>();

    private HttpRequestBuilder(){

    }

    public HttpRequestBuilder setMethod(HttpRequest.Method method){

        this.method = method;

        return this;
    }

    public HttpRequestBuilder setProtocol(String protocol) {
        this.protocol = protocol;

        return this;
    }

    public HttpRequestBuilder setHost(String host) {
        this.host = host;

        return this;
    }

    public HttpRequestBuilder setPort(String port) {
        this.port = port;

        return this;
    }

    public HttpRequestBuilder setPath(String path){
        this.path = path;

        return this;
    }

    public HttpRequestBuilder setUrl(String url) {
        this.url = url;

        return this;
    }

    public HttpRequestBuilder setStringContent(String content){
        this.stringContent = content;

        return this;
    }

    public HttpRequestBuilder setContentType(String contentType){
        this.contentType = contentType;

        return this;
    }

    public HttpRequestBuilder addParameter(String key , String value){

        parameterMap.put(key, value);

        return this;
    }

    public HttpRequestBuilder addHeader(String contentType , String value){

        headerMap.put(contentType , value);

        return this;
    }

    public static HttpRequestBuilder newInstance(){

        return new HttpRequestBuilder();
    }

    public HttpRequest build(){

        HttpRequest request = new HttpRequest();

        if(url != null && !url.trim().equalsIgnoreCase("")){
            request.setUrl(url);
        }else{
            request.setUrl(makeUrl());
        }

        request.setMethod(method);
        request.setParameterMap(parameterMap);
        request.setStringContent(stringContent);

        request.setContentType(contentType);
        request.setHeaderMap(headerMap);

        return request;
    }

    private String makeUrl(){

        String url = "";

        url += protocol + "://";
        url += host;

        if(port != null && !port.trim().equalsIgnoreCase(""))
            url += ":"+port;

        url += "/";
        url += path;

        return url;
    }
}
