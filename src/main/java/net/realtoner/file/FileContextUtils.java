package net.realtoner.file;

import java.io.File;

/**
 *
 * @author RyuIkHan
 */
public class FileContextUtils {

    /**
     *
     * */
    public static File toFile(FileManager fileManager , FileContext fileContext){

        String rootPath = fileManager.getRootPath().trim();
        String relativePath = fileContext.getPathWithFullFileName().trim();

        if(!rootPath.endsWith("/"))
            rootPath += "/";

        if(relativePath.startsWith("/"))
            relativePath = relativePath.substring(1);

        return new File(rootPath + relativePath);
    }

    /**
     *
     * */
    public static File toFile(FileManager fileManager , String path , String fileName){

        String rootPath = fileManager.getRootPath().trim();
        path = path.trim();
        fileName = fileName.trim();

        if(!rootPath.endsWith("/"))
            rootPath += "/";

        if(path.startsWith("/"))
            path = path.substring(1);

        if(!path.endsWith("/"))
            path += "/";

        if(fileName.startsWith("/"))
            fileName = fileName.substring(1);

        return new File(rootPath + path + fileName);
    }
}
