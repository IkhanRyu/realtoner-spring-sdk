package net.realtoner.http;

import net.realtoner.utils.CheckUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author RyuIkHan
 */
public class HttpResponse {

    private int statusCode;

    private InputStream inputStream = null;
    private String stringContent = "";

    private Map<String, String> headerMap = new HashMap<>();

    /*
    * Header fields
    * */
    private static final String CONTENT_TYPE = "Content-Type";

    public HttpResponse() {

    }

    void addHeader(String key, String value) {
        headerMap.put(key, value);
    }

    public String getHeader(String name) {
        return headerMap.get(name);
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStringContent() {
        return stringContent;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setStringContent(String stringContent) {
        this.stringContent = stringContent;
    }

    public String getContentType() {

        String contentType = headerMap.get(CONTENT_TYPE);

        if (CheckUtils.isEmptyString(contentType)) {
            contentType = headerMap.get(CONTENT_TYPE.toLowerCase());
        }

        return contentType;
    }

    public void setContentType(String contentType) {
        headerMap.put(CONTENT_TYPE, contentType);
    }

    public boolean isImage() {
        return getContentType().startsWith("image");
    }

    public boolean isText() {
        return false;
    }
}
