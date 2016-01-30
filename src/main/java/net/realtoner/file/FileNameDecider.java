package net.realtoner.file;

/**
 *
 * @author RyuIkHan
 * */
public interface FileNameDecider {

    String decideFileName(FileContext  fileContext);
    String decideFileExtension(FileContext fileContext);
}
