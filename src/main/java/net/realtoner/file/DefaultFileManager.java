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

    }
}
