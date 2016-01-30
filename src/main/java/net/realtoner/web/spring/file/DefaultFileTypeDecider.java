package net.realtoner.web.spring.file;

import net.realtoner.file.FileContext;
import net.realtoner.utils.CheckUtils;

/**
 *
 * @author RyuIkHan
 */
public class DefaultFileTypeDecider implements FileTypeDecider{

    private static final String DEFAULT_CONTENT_TYPE = "application/unknown";

    @Override
    public String decideType(FileContext fileContext) {

        String fileType = fileContext.getFileExtension();

        if(CheckUtils.isEmptyString(fileType))
            return DEFAULT_CONTENT_TYPE;

        fileType = fileType.toLowerCase();

        switch(fileType){

            case "png":
            case "jpg":
            case "jpeg":
            case "gif":

                return fileType + "/image";

            case "xml":
            case "json":
            case "pdf":

                return fileType + "/application";

            case "doc":

                return "application/msword";

            case "mp3":
            case "mpeg":

                return "audio/mpeg";

            case "wav":

                return "audio/x-wav";

            case "html":
            case "js":
            case "css":

                return "text/" + fileType;

            case "txt":

                return "text/plain";

            default:

                return DEFAULT_CONTENT_TYPE;
        }
    }
}
