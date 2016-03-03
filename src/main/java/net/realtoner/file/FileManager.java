package net.realtoner.file;

import net.realtoner.file.exception.DuplicateFileException;
import net.realtoner.file.exception.SchemaInitializingException;
import net.realtoner.file.schema.FileManagerSchema;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Support CRD(Create , Retrieve , Delete) operation for file system.
 * Takes {@link FileContext} and get information about current file from FileContext.
 *
 * @author RyuIkHan
 * @see AbstractFileManager
 * @see AbstractFileManagerFactory
 * @see FileContext
 */
public interface FileManager{

    /**
    *
    * @param fileAdapterSchema
    * **/
    void init(FileManagerSchema fileAdapterSchema) throws SchemaInitializingException;

    /**
     *
     * @return
     * */
    String getRootPath();

    /**
     *
     * @param fileContext information of current file
     * @retrn
     * */
    boolean exists(FileContext fileContext);

    /**
     *
     * @param fileContext information of current file
     * @return
     * */
    boolean existsPath(FileContext fileContext);

    /**
     *
     * @param fileContext
     * @return
     * */
    boolean isDirectory(FileContext fileContext);

    /**
     *
     * @return
     * */
    String[] getGroupNames();

    /**
     *
     * @return
     * */
    int getOrder();

    /**
     *
     * @param groupName
     * @return
     * */
    int getGroupOrder(String groupName);

    /**
     * get {@link FileContext} using path nad file name.
     *
     * @param path path of file
     * @param fileName name of file
     * @return correlated FileContext. If there is such file , return null.
     * */
    FileContext getFileContext(String path , String fileName);

    /**
     *
     * @param fileContext information of current file
     * @return
     * */
    InputStream retrieveFile(FileContext fileContext) throws IOException;

    /**
     *
     * @param path
     * */
    void createFolder(String path) throws IOException;

    /**
     *
     * @param path
     * @param id
     * */
    void createFolderUsingFolderId(String path , String id) throws IOException , SchemaInitializingException;

    /**
     * @param path
     * @param id
     * @param folderName
     * */
    void createFolderUsingFolderId(String path , String id , String folderName)
            throws IOException , SchemaInitializingException;

    /**
     *
     * @param fileContext information of current file
     * @param inputStream
     * @throws DuplicateFileException
     * */
    void storeFile(FileContext fileContext, InputStream inputStream)
            throws IOException , DuplicateFileException;


    /**
     *
     * @param fileContext information of current file
     * @param inputStream
     * */
    void storeFileIgnoreDuplicate(FileContext fileContext, InputStream inputStream) throws IOException;

    /**
     *
     * @param fileContext information of current file
     * */
    void deleteFile(FileContext fileContext) throws IOException;

    /**
     *
     * @param fileContext
     * @return
     * */
    File toFile(FileContext fileContext);

    /**
     *
     * @param path
     * @param fileName
     * @return
     * */
    File toFile(String path , String fileName);
}
