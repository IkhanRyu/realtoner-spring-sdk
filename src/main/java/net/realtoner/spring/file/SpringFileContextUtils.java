package net.realtoner.spring.file;

import net.realtoner.file.FileContext;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author RyuIkHan
 */
public class SpringFileContextUtils {

    /**
     * @param path
     * @param fileName
     * @param multipartFile
     */
    public static FileContext createFileContext(String path, String fileName, MultipartFile multipartFile) {
        return FileContext.createFileContext(path, fileName, multipartFile.getOriginalFilename(),
                multipartFile.getSize());
    }
}
