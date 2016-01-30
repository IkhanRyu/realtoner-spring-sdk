package net.realtoner.web.servlet;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 *
 * @author RyuIkHan
 * */
public class ServletHandlerProxyImpl implements ServletHandleProxy{

    @Override
    public void handle(HttpHeaders headers, HttpServletResponse httpServletResponse , InputStream inputStream)
            throws IOException {

        httpServletResponse.setStatus(headers.getStatusCode());

        //add headers
        for(Map.Entry<String , String> entry : headers.entrySet())
            httpServletResponse.addHeader(entry.getKey() , entry.getValue());

        //add cookies
        for(Map.Entry<String , String> cookieEntry : headers.cookieEntrySet())
            httpServletResponse.addCookie(new Cookie(cookieEntry.getKey() , cookieEntry.getValue()));

        IOUtils.copy(inputStream , httpServletResponse.getOutputStream());
    }
}
