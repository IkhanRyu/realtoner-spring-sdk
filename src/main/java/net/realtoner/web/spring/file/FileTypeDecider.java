package net.realtoner.web.spring.file;

import net.realtoner.file.FileContext;

/**
 *
 * @author RyuIkHan
 */
public interface FileTypeDecider {

    /**
     * @param fileContext
     * @return
     * */
    String decideType(FileContext fileContext);
}
