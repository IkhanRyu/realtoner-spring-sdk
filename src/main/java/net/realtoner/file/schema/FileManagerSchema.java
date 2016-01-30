package net.realtoner.file.schema;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author RyuIkHan
 */
public class FileManagerSchema {

    /*
    *
    * */
    private String rootPath = null;

    /*
    *
    * */
    private FileManagerFolder rootFolder = null;

    /*
    *
    * */
    private int order;

    /*
    *
    * */
    private Map<String , FileManagerFolder> idFolderMap = null;

    public String getRootPath(){
        return rootPath;
    }

    public void setRootPath(String rootPath){
        this.rootPath = rootPath;
    }

    public void setRootFolder(FileManagerFolder rootFolder) {
        this.rootFolder = rootFolder;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public FileManagerFolder getRootFolder(){
        return rootFolder;
    }

    public Map<String, FileManagerFolder> getIdFolderMap() {
        return idFolderMap;
    }

    public void setIdFolderMap(Map<String, FileManagerFolder> idFolderMap) {
        this.idFolderMap = idFolderMap;
    }
}
