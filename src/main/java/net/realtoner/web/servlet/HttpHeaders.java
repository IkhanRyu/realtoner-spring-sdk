package net.realtoner.web.servlet;

import net.realtoner.utils.CheckUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class HttpHeaders {

    /*
    * Header keys
    * */
    private static final String HOST = "host";
    private static final String ACCEPT = "accept";
    private static final String COOKIE = "cookie";
    private static final String CONTENT_TYPE = "content-type";
    private static final String CONTENT_LENGTH = "content-length";

    /**
     *
     * @param request
     * @return
     * */
    public static HttpHeaders createHttpHeaders(HttpServletRequest request){

        Enumeration<String> keyEnum = request.getHeaderNames();

        HttpHeaders httpHeaders = new HttpHeaders();

        while(keyEnum.hasMoreElements()){
            String key = keyEnum.nextElement();
            String value = request.getHeader(key);

            httpHeaders.putHeader(key.toLowerCase() , value);
        }

        String cookie = httpHeaders.getValue(COOKIE);

        //extract cookie
        if(!CheckUtils.isEmptyString(cookie)){
            String[] cookieEntryArr = cookie.split(";");

            if(cookieEntryArr.length > 0)
                for(String cookieEntry : cookieEntryArr){
                    String[] tempCookie = cookieEntry.split("=");

                    String tempCookieKey = tempCookie[0].trim();
                    String tempCookieValue = tempCookie.length == 2 ? tempCookie[1].trim() : "";

                    httpHeaders.cookieMap.put(tempCookieKey , tempCookieValue);
                }
        }

        return httpHeaders;
    }

    /**
     *
     * @param response
     * @return
     * */
    public static HttpHeaders createHttpHeaders(HttpServletResponse response){

        Collection<String> headerCollection = response.getHeaderNames();

        HttpHeaders httpHeaders = new HttpHeaders();

        for(String key : headerCollection)
            httpHeaders.putHeader(key.toLowerCase() , response.getHeader(key));

        return httpHeaders;
    }


    private final Map<String , String> headerMap = new HashMap<>();
    private final Map<String , String> cookieMap = new HashMap<>();

    /*
    *
    * */
    public static final int STATUS_OK = 200;

    public static final int STATUS_NOT_FOUND = 404;

    private int statusCode = STATUS_OK;

    public HttpHeaders(){

    }

    public HttpHeaders(int statusCode){
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void putHeader(String key , String value){
        headerMap.put(key , value);
    }

    public String getValue(String key){
        return headerMap.get(key);
    }

    public boolean contains(String key){
        return headerMap.containsKey(key);
    }

    public Set<Map.Entry<String , String>> entrySet(){
        return headerMap.entrySet();
    }

    public Set<Map.Entry<String , String>> cookieEntrySet(){
        return cookieMap.entrySet();
    }

    /*
    * setters
    * */
    public void setContentType(String contentType){
        headerMap.put(CONTENT_TYPE , contentType);
    }

    public void setContentLength(long contentLength){
        headerMap.put(CONTENT_LENGTH, String.valueOf(contentLength));
    }

    public void addCookie(String key , String value){
        cookieMap.put(key , value);
    }

    /*
    *
    * */
    public String getHost(){
        return headerMap.get(HOST);
    }

    public String getCookie(String key){
        return cookieMap.get(key.toLowerCase());
    }

    public String getContentType(){
        return headerMap.get(CONTENT_TYPE);
    }

    public String getContentLength(){
        return headerMap.get(CONTENT_LENGTH);
    }

    public String getAccept(){
        return headerMap.get(ACCEPT);
    }
}
