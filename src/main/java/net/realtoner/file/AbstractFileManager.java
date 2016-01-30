package net.realtoner.file;

import net.realtoner.file.exception.SchemaInitializingException;
import net.realtoner.file.schema.FileManagerFolder;
import net.realtoner.file.schema.FileManagerSchema;
import net.realtoner.utils.CheckUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * {@inheritDoc}
 * <p/>
 * <br/>
 * <p>Control access to file system With {@link FileManagerFolder}.</p>
 * <br/>
 * <p>This class has folder map which consists of FileAdapterFolder. Each FileAdapterFolder has child
 * FileAdapterFolder and FileAdapterAuthority. From these things, it control access to file system.
 * </p>
 *
 * @author RyuIkHan
 * @see AbstractFileManagerFactory
 * @see XmlFileManagerFactory
 */
public abstract class AbstractFileManager implements FileManager {

    /*
    * must start with '/' and end with '/'
    * */
    private String rootPath = null;

    private int order = -1;

    private boolean init = false;

    private FileManagerFolder rootFolder = null;

    private final Map<String, FileManagerFolder> idFolderMap = new HashMap<>();

    @Override
    public final void init(FileManagerSchema fileAdapterSchema) throws SchemaInitializingException {

        //initialize adapter information
        rootPath = makeParentPath(fileAdapterSchema.getRootPath());
        order = fileAdapterSchema.getOrder();

        File rootFile = new File(rootPath);

        //check whether root folder is available
        if (rootFile.exists() && !rootFile.isDirectory())
            throw new SchemaInitializingException("Root file must be a directory.");

        if (!rootFile.exists() && !rootFile.mkdirs())
            throw new SchemaInitializingException("Root file does not exists and fail to create root folder. " +
                    "Given root path : " + rootPath);

        if (!canDoAllOperations(rootFile))
            throw new SchemaInitializingException("For use FileAdapter, " +
                    "This application must have read , write and execute authority.");

        //build file structure
        rootFolder = fileAdapterSchema.getRootFolder();
        idFolderMap.putAll(fileAdapterSchema.getIdFolderMap());
        rootFolder.setName(rootFile.getName());

        String rootParentPath = makeParentPath(rootFile.getParentFile().getAbsolutePath());

        buildFolderStructureRecursively(rootParentPath, rootFolder);

        //mark this adapter is initialized
        init = true;
    }

    @Override
    public String getRootPath(){
        return rootPath;
    }

    /**
     *
     * */
    private void buildFolderStructureRecursively(String parentPath, FileManagerFolder folder)
            throws SchemaInitializingException {

        buildFolderStructureRecursively(parentPath, folder, folder.getName());
    }

    /**
     *
     * */
    private void buildFolderStructureRecursively(String parentPath, FileManagerFolder folder, String folderName)
            throws SchemaInitializingException {

        String tempParentPath = makeParentPath(parentPath) + folderName + "/";

        File folderFile = new File(tempParentPath);

        try {
            if (folderFile.exists() && !folderFile.isDirectory())
                throw new SchemaInitializingException("\"" + folderFile.getAbsolutePath() + "\" must be directory.");

            if (!folderFile.exists() && !folderFile.mkdir())
                throw new SchemaInitializingException("Fail to make folder on " + folderFile.getAbsolutePath() + ".");

        } catch (SecurityException e) {
            throw new SchemaInitializingException(e);
        }

        if (!canDoAllOperations(folderFile))
            throw new SchemaInitializingException("This application does not have authority on " +
                    folderFile.getAbsolutePath() + ".");

        if (folder.hasChild()) {
            Set<Map.Entry<String, FileManagerFolder>> entrySet = folder.getChildMap().entrySet();

            for (Map.Entry<String, FileManagerFolder> entry : entrySet)
                buildFolderStructureRecursively(tempParentPath, entry.getValue());
        }
    }

    protected String makePath(String parentPath, String childPath) {
        return makeParentPath(parentPath) + makeChildPath(childPath);
    }

    protected String makePath(String childPath){
        return makePath(rootPath , childPath);
    }


    /**
     * make path of parent proper to be used in FileManager. The path of child muststart with "/" and end with "/".
     *
     * @return the properly modified path of parent.
     */
    private String makeParentPath(String parentPath) {

        parentPath = parentPath.trim();

        if (!parentPath.startsWith("/"))
            parentPath = "/" + parentPath;

        if (!parentPath.endsWith("/"))
            parentPath += "/";

        return parentPath;
    }

    /**
     * make path of child proper to be used in FileManager. The path of child must not start with "/" and not end
     * with "/".
     *
     * @reutrn the properly modified path of child.
     */
    private String makeChildPath(String childPath) {

        childPath = childPath.trim();

        if (childPath.startsWith("/"))
            childPath = childPath.substring(1, childPath.length());

        if (childPath.endsWith("/"))
            childPath = childPath.substring(0, childPath.length() - 1);

        return childPath;
    }

    /**
     * @param file
     * @return
     */
    protected boolean canDoAllOperations(File file) {
        return file.canExecute() && file.canRead() && file.canWrite();
    }

    protected File createFile(FileContext fileContext) {
        return createFile(fileContext.getPath(), fileContext.getFullFileName());
    }

    protected File createFile(String path) {
        return new File(rootPath, path);
    }

    protected File createFile(String parentPath, String childPath) {
        return new File(rootPath, makePath(parentPath, childPath));
    }

    /**
     * @return
     */
    private String createFolderNameUsingCurrentTime() {

        long currentTime = System.currentTimeMillis();

        return String.valueOf(currentTime);
    }

    @Override
    public String[] getGroupNames() {
        return new String[0];
    }

    @Override
    public int getGroupOrder(String groupName) {
        return 0;
    }

    @Override
    public void createFolderUsingFolderId(String path, String id) throws IOException, SchemaInitializingException {

        FileManagerFolder fileManagerFolder = idFolderMap.get(id);

        if (fileManagerFolder == null)
            throw new SchemaInitializingException("There is such id. given id : " + id);

        File pathFile = new File(makePath(rootPath, path));

        if (!pathFile.exists() && !pathFile.mkdirs())
            throw new SchemaInitializingException("Fail to make directories");

        String parentPath = makePath(path) + "/";

        String fileName = CheckUtils.isEmptyString(fileManagerFolder.getName()) ?
                createFolderNameUsingCurrentTime() : fileManagerFolder.getName();

        buildFolderStructureRecursively(parentPath, fileManagerFolder, fileName);
    }

    @Override
    public void createFolderUsingFolderId(String path, String id, String folderName)
            throws IOException, SchemaInitializingException {

        FileManagerFolder fileManagerFolder = idFolderMap.get(id);

        if (fileManagerFolder == null)
            throw new SchemaInitializingException("There is such id. given id : " + id);

        File pathFile = new File(makePath(rootPath, path));

        if (!pathFile.exists() && !pathFile.mkdirs())
            throw new SchemaInitializingException("Fail to make directories");

        String parentPath = makePath(path) + "/";

        buildFolderStructureRecursively(parentPath, fileManagerFolder, folderName);
    }


    public boolean isInit() {
        return init;
    }

    @Override
    public FileContext getFileContext(String path, String fileName) {

        File targetFile = createFile(path , fileName);

        if(!targetFile.exists())
            return null;

        String originalFileName = targetFile.getName();
        String parentPath = makeParentPath(path);
        long fileSize = targetFile.length();

        return FileContext.createFileContext(parentPath , originalFileName , originalFileName , fileSize);
    }

    @Override
    public boolean exists(FileContext fileContext) {

        String path = makeChildPath(fileContext.getPathWithFullFileName());

        return createFile(path).exists();
    }

    @Override
    public boolean existsPath(FileContext fileContext) {

        String path = fileContext.getPath();

        return createFile(path).exists();
    }

    @Override
    public boolean isDirectory(FileContext fileContext) {

        File file = createFile(fileContext);

        return file.isDirectory();
    }

    @Override
    public int getOrder() {
        return order;
    }
}