package net.realtoner.file;

/**
 * @author RyuIkHan
 */
public class DefaultXmlFileAdapterFactory extends XmlFileManagerFactory {

    public DefaultXmlFileAdapterFactory(){

    }

    @Override
    protected Class<? extends FileManager> getFileAdapterClass() {
        return DefaultFileManager.class;
    }
}
