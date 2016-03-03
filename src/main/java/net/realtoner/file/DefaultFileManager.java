package net.realtoner.file;

import net.realtoner.file.exception.DuplicateFileException;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 *
 * @author RyuIkHan
 */
public class DefaultFileManager extends AbstractFileManager {

    @Override
    public InputStream retrieveFile(FileContext fileContext) throws IOException{

        File targetFile = createFile(fileContext);

        fileContext.setSize(targetFile.length());

        return new FileInputStream(targetFile);
    }

    @Override
    public void createFolder(String path) throws IOException {

        File file = createFile(path);

        if(!file.mkdirs())
            throw new IOException("fail to make directory.");
    }

    @Override
    public void storeFile(FileContext fileContext, InputStream inputStream) throws IOException, DuplicateFileException {

        File file  = createFile(fileContext);

        if(file.exists())
            throw new DuplicateFileException("");

        IOUtils.copy(inputStream , new FileOutputStream(file));
    }

    @Override
    public void storeFileIgnoreDuplicate(FileContext fileContext, InputStream inputStream) throws IOException {

        File file = createFile(fileContext);

        IOUtils.copy(inputStream , new FileOutputStream(file));
    }

    @Override
    public void deleteFile(FileContext fileContext) throws IOException {

        File file = createFile(fileContext);

        if(!file.delete()){
            throw new IOException("file to delete file.");
        }
    }

    @Override
    public File toFile(FileContext fileContext) {

        String path = fileContext.getPathWithFullFileName().trim();

        if(path.startsWith("/")){
            path = path.substring(1);
        }

        return new File(getRootPath() + path);
    }

    @Override
    public File toFile(String path, String fileName) {

        path = path.trim();
        fileName = fileName.trim();

        if(path.startsWith("/"))
            path = path.substring(1);

        if(!path.endsWith("/"))
            path += "/";

        if(fileName.startsWith("/"))
            fileName = fileName.substring(1);

        return new File(getRootPath() + path + fileName);
    }
}
