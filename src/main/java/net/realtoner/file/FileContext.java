package net.realtoner.file;

import net.realtoner.utils.CheckUtils;
import net.realtoner.utils.MatchUtils;

/**
 * contains information of file to be retrieved or stored.
 *
 * @author RyuIkHan
 */
public class FileContext {

    /**
     * create file context using path , name of file , original name of file.
     *
     * @param path path of newly created context related to
     * @param fileName name of file of newly created context related to
     * @param originalFileName  original name of file of newly created context related to
     * @param size size of file of newly created context related to
     * @return
     * */
    public static FileContext createFileContext(String path , String fileName , String originalFileName , long size){

        path = path.trim();
        fileName = fileName.trim();

        String _fileName = fileName;

        fileName = MatchUtils.removeExtensionFromFileName(fileName);
        String fileExtension = MatchUtils.extractExtensionFromFileName(_fileName);

        return new FileContext(originalFileName , path , fileName , fileExtension , size);
    }

    /**
     *
     * @param path
     * @param fileName
     * @return
     * */
    public static FileContext createFileContext(String path , String fileName){
        return createFileContext(path , fileName , null , 0);
    }

    /*
    * The original name of file. It has file name and extension itself.
    * ex) picture.png , 2015_11_25_image.jpeg ...
    * */
    private String originalFileName = null;

    private String path = null;

    /*
    * The name of file. It must not have extension , just file name.
    * ex) picture , 2015_11_25_image ...
    * */
    private String fileName = null;

    /*
    * The extension of file. It must not have "." notation.
    * ex) png , jpeg ...
    * */
    private String fileExtension = null;

    private String groupName = null;

    private long size;

    public FileContext(String originalFileName , String filePath, String fileName , String fileExtension , long size){

        this.originalFileName = originalFileName;
        this.path = filePath;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.size = size;
    }

    /*
    * Normal getters and setters
    * */
    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getPath(){
        return path;
    }

    public void setPath(String path){
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getGroupName(){
        return groupName;
    }

    public void setGroupName(String groupName){
        this.groupName = groupName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPathWithFullFileName(){
        return path + getFullFileName();
    }

    /**
     * full name is contains fileName and file extension. If current file context does not have extension
     * just return fileName.
     *
     * @return the full name of file
     * */
    public String getFullFileName(){
        return fileName + (CheckUtils.isEmptyString(fileExtension) ? "" : "."  + fileExtension);
    }
}
