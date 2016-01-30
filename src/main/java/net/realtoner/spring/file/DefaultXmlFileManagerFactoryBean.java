package net.realtoner.spring.file;

import net.realtoner.file.DefaultXmlFileAdapterFactory;
import net.realtoner.file.XmlFileManagerFactory;

/**
 *
 * @author RyuIkHan
 */
public class DefaultXmlFileManagerFactoryBean extends FileManagerFactoryBean {

    @Override
    protected Class<? extends XmlFileManagerFactory> getFileManagerFactoryClass() {
        return DefaultXmlFileAdapterFactory.class;
    }
}
