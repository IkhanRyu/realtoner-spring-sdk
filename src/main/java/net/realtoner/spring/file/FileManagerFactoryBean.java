package net.realtoner.spring.file;

import net.realtoner.file.FileManager;
import net.realtoner.file.XmlFileManagerFactory;
import net.realtoner.utils.CheckUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

/**
 *
 * @author RyuIkHan
 */
public abstract class FileManagerFactoryBean implements FactoryBean<FileManager> , InitializingBean{

    private String rootPath = null;
    private Resource schema = null;

    public void setRootPath(String rootPath){
        this.rootPath = rootPath;
    }

    public void setSchema(Resource schema){
        this.schema = schema;
    }

    @Override
    public FileManager getObject() throws Exception {

        XmlFileManagerFactory fileAdapterFactory = getFileManagerFactoryClass().newInstance();

        fileAdapterFactory.setRootPath(rootPath);
        fileAdapterFactory.setSchemaFile(schema.getFile());

        return fileAdapterFactory.build();
    }

    protected abstract Class<? extends XmlFileManagerFactory> getFileManagerFactoryClass();

    @Override
    public Class<?> getObjectType() {
        return FileManager.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        if(CheckUtils.isEmptyString(rootPath))
            throw new NullPointerException("Root path must be set.");

        if(schema == null)
            throw new NullPointerException("Schema must be set");
    }
}
