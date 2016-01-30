package net.realtoner.file;

import net.realtoner.file.exception.SchemaInitializingException;
import net.realtoner.file.schema.FileManagerFolder;
import net.realtoner.file.schema.FileManagerSchema;

/**
 *
 * <pre>
 *     example
 *     {@code
 *
 *     }
 * </pre>
 *
 * @author RyuIkHan
 */
public abstract class AbstractFileManagerFactory {

    private String rootPath = null;

    public AbstractFileManagerFactory(){

    }

    /**
     *
     * @return
     * */
    public final FileManager build() throws SchemaInitializingException{

        beforeBuild();

        FileManagerSchema schema = getSchema();

        if(schema == null){
            schema = new FileManagerSchema();

            //set base information of schema
            schema.setOrder(getOrder() == -1 ? 0 : getOrder());

            FileManagerFolder rootFolder = createFileManagerFolder("");
            buildFolderStructure(rootFolder);

            schema.setRootFolder(rootFolder);
        }

        schema.setRootPath(rootPath);

        FileManager manager;

        try {
            manager = createFileManagerInstance();
        } catch(Exception e) {
            throw new SchemaInitializingException(e);
        }

        manager.init(schema);

        afterBuild();

        return manager;
    }

    /**
     *
     * */
    protected void beforeBuild() throws SchemaInitializingException{

    }

    /**
     *
     **/
    protected void afterBuild() throws SchemaInitializingException{

    }

    /**
     *
     * @return
     * */
    protected FileManagerSchema getSchema() throws SchemaInitializingException{

        return null;
    }

    public void setRootPath(String rootPath){
        this.rootPath = rootPath;
    }

    public String getRootPath(){
        return rootPath;
    }

    protected final FileManagerFolder createFileManagerFolder(String name){

        return new FileManagerFolder(name);
    }

    protected abstract void buildFolderStructure(FileManagerFolder rootFolder);

    protected abstract int getOrder();

    /**
     *
     * @return
     * */
    protected abstract Class<? extends FileManager> getFileAdapterClass();

    protected FileManager createFileManagerInstance() throws IllegalAccessException, InstantiationException {

        return getFileAdapterClass().newInstance();
    }
}
