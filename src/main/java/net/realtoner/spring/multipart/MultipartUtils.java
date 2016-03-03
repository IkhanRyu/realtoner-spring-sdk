package net.realtoner.spring.multipart;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author RyuIkHan
 */
public class MultipartUtils {

    public static boolean isImage(MultipartFile multipartFile){
        return multipartFile.getContentType().trim().startsWith("image");
    }
}
