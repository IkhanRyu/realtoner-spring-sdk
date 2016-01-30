package net.realtoner.file.schema;

import net.realtoner.utils.CheckUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author RyuIkHan
 */
public class FileManagerFolder {

    private String name = null;

    private final Map<String , FileManagerFolder> fileManagerFolderMap = new HashMap<>();

    public FileManagerFolder(){

    }

    public FileManagerFolder(String name){
        this.name = name;
    }

    public FileManagerFolder(FileManagerFolder fileManagerFolder , String name){
        this.name = name;
        fileManagerFolderMap.putAll(fileManagerFolder.getChildMap());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void putFolder(FileManagerFolder folder){

        fileManagerFolderMap.put(folder.getName(), folder);
    }

    public Map<String , FileManagerFolder> getChildMap(){
        return fileManagerFolderMap;
    }

    public FileManagerFolder getFolder(String folderName){

        return fileManagerFolderMap.get(folderName);
    }

    public boolean hasChild(){

        return !fileManagerFolderMap.isEmpty();
    }
}
