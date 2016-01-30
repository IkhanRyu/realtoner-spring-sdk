package net.realtoner.web.servlet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author RyuIkHan
 */
public interface ServletHandleProxy {

    /**
     *
     * @param headers
     * @param httpServletResponse
     * @param inputStream
     * */
    void handle(HttpHeaders headers , HttpServletResponse httpServletResponse , InputStream inputStream)
            throws IOException;

}
