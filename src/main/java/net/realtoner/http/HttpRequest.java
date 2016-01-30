package net.realtoner.http;


import com.squareup.okhttp.*;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @author RyuIkHan
 */
public class HttpRequest {

    /**
    *
    */
    public enum Method {
        POST , GET , DELETE , PUT
    }

    private String url = null;

    private Method method = Method.GET;

    private String stringContent = null;

    private String contentType = null;

    private Map<String , Object> parameterMap = null;
    private Map<String , String> headerMap = null;

    /*
    * Constants
    * */
    private static final String HEADER_CONTENT_TYPE = "Content-Type";

    /**
     *
     * @return
     * */
    public HttpResponse process() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request.Builder builder = new Request.Builder();

        builder.url(makeUrl());

        switch(method){

            default:
            case GET:

                builder.get();
                break;

            case POST:

                builder.post(makeRequestBody());
                break;

            case DELETE:

                builder.delete();
                break;
        }

        addHeaders(builder);

        Response response = client.newCall(builder.build()).execute();

        HttpResponse httpResponse = new HttpResponse();

        httpResponse.setStatusCode(response.code());
        httpResponse.setContent(response.body().string());

        Headers headers = response.headers();

        for(int i = 0; i < headers.size(); i++){
            httpResponse.addHeader(headers.name(i) , headers.value(i));
        }

        return httpResponse;
    }

    /*
    * Noraml getters and setters
    * */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public String getStringContent() {
        return stringContent;
    }

    public void setStringContent(String stringContent) {
        this.stringContent = stringContent;
    }

    public void setContentType(String contentType){
        this.contentType = contentType;
    }

    public String getContentType(){
        return contentType;
    }

    /*
    *
    * */



    /**
     *
     * */
    public void addHeaders(Request.Builder builder) {

        for (Map.Entry<String, String> entity : headerMap.entrySet()) {

            builder.addHeader(entity.getKey() , entity.getValue());
        }

        if(contentType != null && !contentType.trim().equals("")){

            builder.addHeader(HEADER_CONTENT_TYPE , contentType);
        }
    }

    /**
     *
     * @return
     * */
    private String makeParameterStr() {

        String str = "";

        if (parameterMap.size() < 1) {
            return "";
        }

        for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {

            str += "&" + entry.getKey() + "=" + entry.getValue();
        }

        return "?" + str.substring(1);
    }

    private String makeUrl(){

        return url += makeParameterStr();
    }

    private String makeContent(){

        String content;

        if(stringContent != null && !stringContent.trim().equals("")){
            content = stringContent;
        }else{
            content = makeParameterStr();
        }

        return content;
    }

    private RequestBody makeRequestBody(){

        String content = makeContent();

        MediaType mediaType = null;

        if(contentType != null && !contentType.trim().equals("")){
            mediaType = MediaType.parse(contentType);
        }

        return RequestBody.create(mediaType , content);
    }
}