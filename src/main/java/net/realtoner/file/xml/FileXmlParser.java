package net.realtoner.file.xml;

import net.realtoner.file.exception.SchemaInitializingException;
import net.realtoner.file.schema.FileManagerFolder;
import net.realtoner.file.schema.FileManagerSchema;
import net.realtoner.utils.CheckUtils;
import net.realtoner.utils.XmlUtils;
import net.realtoner.utils.graph.AdjacentListGraph;
import net.realtoner.utils.graph.DirectedGraph;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author RyuIkHan
 */
public class FileXmlParser {

    private FileManagerSchema schema = null;

    private int order = -1;

    private Element rootElement = null;

    private Element rootFolderElement = null;
    private FileManagerFolder rootFolder = null;

    private final Map<String, Element> idFolderElementMap = new HashMap<>();
    private final Map<String, FileManagerFolder> idFileManagerFolderMap = new HashMap<>();

    private final DirectedGraph folderStructureGraph = new AdjacentListGraph();

    public FileXmlParser(File schemaFile) throws IOException, ParserConfigurationException, SAXException {

        init(new FileInputStream(schemaFile));
    }

    public FileXmlParser(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {

        init(inputStream);
    }

    private void init(InputStream is) throws IOException, ParserConfigurationException, SAXException {

        rootElement = XmlUtils.parse(is);
    }

    /**
     * build {@link FileManagerSchema} using given file structure xml file. <br />
     * <br />
     * Methods which start with "initialize" initialize information.
     */
    public FileManagerSchema build() throws SchemaInitializingException {

        if (schema != null)
            return schema;

        initializeRoot();
        initializeRootFolder();
        initializeFolder();

        checkFolderStructureIsValid();

        buildFolders();
        buildRootFolder();

        createSchema();

        return schema;
    }

    /**
     *
     * */
    private void createSchema() {

        schema = new FileManagerSchema();

        schema.setOrder(order);
        schema.setRootFolder(rootFolder);
        schema.setIdFolderMap(idFileManagerFolderMap);
    }

    /**
     * initialize information using root tag ("Schema" tag). Xml file which describes file structure must
     * have "Schema" tag as root tag. Optionally, "Schema" tag has "order" attribute.
     */
    private void initializeRoot() throws SchemaInitializingException {

        if (!rootElement.getTagName().equals(FileXmlConstants.TAG_SCHEMA))
            throw new SchemaInitializingException("Root tag name must be \"" + FileXmlConstants.TAG_SCHEMA + "\".");

        String order = rootElement.getAttribute(FileXmlConstants.ATTR_ROOT_ORDER);

        if (!CheckUtils.isEmptyString(order))
            this.order = Integer.parseInt(order);
    }

    /**
     *
     * */
    private void initializeRootFolder() throws SchemaInitializingException {

        List<Element> rootFolderList =
                XmlUtils.getChildElementsByTagName(rootElement, FileXmlConstants.TAG_ROOT_FOLDER);

        if (CheckUtils.isEmptyList(rootFolderList))
            throw new SchemaInitializingException("There is must a \"" + FileXmlConstants.TAG_ROOT_FOLDER + "\" tag.");

        if (rootFolderList.size() != 1)
            throw new SchemaInitializingException("There is must a \"" + FileXmlConstants.TAG_ROOT_FOLDER + "\" tag.");

        rootFolderElement = rootFolderList.get(0);

        rootFolder = new FileManagerFolder();

        folderStructureGraph.addVertex("root");

        List<Element> folderList = XmlUtils.getChildElementsByTagName(rootElement, FileXmlConstants.TAG_FOLDER);

        if (!CheckUtils.isEmptyList(folderList))
            for (Element folderElement : folderList) {
                String folderId = getAttributeWithNullCheck(folderElement, FileXmlConstants.ATTR_FOLDER_ID, "");

                folderStructureGraph.addVertex(folderId);
                folderStructureGraph.addEdge("root", folderId);
            }
    }

    /**
     *
     * */
    private void initializeFolder() throws SchemaInitializingException {

        List<Element> folderElementList =
                XmlUtils.getChildElementsByTagName(rootElement, FileXmlConstants.TAG_FOLDER);

        if (!CheckUtils.isEmptyList(folderElementList))
            for (Element folderElement : folderElementList) {
                String folderId = getAttributeWithNullCheck(folderElement, FileXmlConstants.ATTR_FOLDER_ID, "");

                idFolderElementMap.put(folderId, folderElement);

                findDependencyUsingChildren(folderElement , folderId);
            }
    }

    /**
     *
     * @param element
     * @param folderId
     * */
    private void findDependencyUsingChildren(Element element , String folderId) throws SchemaInitializingException {

        List<Element> folderElementList =
                XmlUtils.getChildElementsByTagName(element , FileXmlConstants.TAG_FOLDER);

        if (!CheckUtils.isEmptyList(folderElementList))
            for (Element tempFolderElement : folderElementList) {
                String tempFolderId =
                        getAttributeWithNullCheck(tempFolderElement, FileXmlConstants.ATTR_FOLDER_ID, "");

                folderStructureGraph.addEdge(folderId, tempFolderId);
            }

        folderElementList =
                XmlUtils.getChildElementsByTagName(element , FileXmlConstants.TAG_CHILD_FOLDER);

        if(!CheckUtils.isEmptyList(folderElementList))
            for(Element tempFolderElement : folderElementList)
                findDependencyUsingChildren(tempFolderElement , folderId);
    }

    /**
     * @return
     */
    private void checkFolderStructureIsValid() throws SchemaInitializingException {

        //check cycle
        if (folderStructureGraph.hasCycle())
            throw new SchemaInitializingException("Current folder structure has cycle.");

        //check duplicate folder name
    }

    /**
     *
     * */
    private void buildFolders() throws SchemaInitializingException {

        for (Map.Entry<String, Element> entry : idFolderElementMap.entrySet()) {
            String folderId = entry.getKey();
            Element element = entry.getValue();

            if (idFileManagerFolderMap.get(folderId) == null)
                idFileManagerFolderMap.put(folderId, createFolder(element));
        }


    }

    private void buildRootFolder() throws SchemaInitializingException {

        rootFolderElement.setAttribute(FileXmlConstants.ATTR_FOLDER_NAME, "___root___");
        rootFolderElement.setAttribute(FileXmlConstants.ATTR_FOLDER_ID, "___root___");

        rootFolder = createFolder(rootFolderElement);
    }

    /**
     * @param folderElement
     * @return
     */
    private FileManagerFolder createFolder(Element folderElement) throws SchemaInitializingException {

        String folderId = folderElement.getAttribute(FileXmlConstants.ATTR_FOLDER_ID);

        if (CheckUtils.isEmptyString(folderId))
            throwSchemaInitializingException("Tag \"Folder\" must have \"id\" attribute.");

        String folderName = folderElement.getAttribute(FileXmlConstants.ATTR_FOLDER_NAME);

        FileManagerFolder fileManagerFolder = new FileManagerFolder(folderName);

        List<Element> folderElementList =
                XmlUtils.getChildElementsByTagName(folderElement, FileXmlConstants.TAG_CHILD_FOLDER);

        if (!CheckUtils.isEmptyList(folderElementList))
            for (Element tempFolderElement : folderElementList)
                fileManagerFolder.putFolder(createChildFolder(tempFolderElement));

        folderElementList =
                XmlUtils.getChildElementsByTagName(folderElement, FileXmlConstants.TAG_FOLDER);

        if (!CheckUtils.isEmptyList(folderElementList))
            for (Element tempFolderElement : folderElementList) {
                String tempFolderId = tempFolderElement.getAttribute(FileXmlConstants.ATTR_FOLDER_ID);

                if (CheckUtils.isEmptyString(tempFolderId))
                    throw new SchemaInitializingException("");

                FileManagerFolder tempFolder = idFileManagerFolderMap.get(tempFolderId);

                if (tempFolder == null) {
                    Element element = idFolderElementMap.get(tempFolderId);

                    if (element == null)
                        throw new SchemaInitializingException("");

                    //create new folder and put this map
                    tempFolder = createFolder(element);
                    idFileManagerFolderMap.put(tempFolderId, tempFolder);
                }

                String tempFolderName = tempFolderElement.getAttribute(FileXmlConstants.ATTR_FOLDER_NAME);

                if (!CheckUtils.isEmptyString(tempFolderName))
                    tempFolder = new FileManagerFolder(tempFolder , tempFolderName);

                fileManagerFolder.putFolder(tempFolder);
            }

        return fileManagerFolder;
    }

    /**
     * @param childFolderElement
     * @return
     */
    private FileManagerFolder createChildFolder(Element childFolderElement)
            throws SchemaInitializingException {

        String folderName = childFolderElement.getAttribute(FileXmlConstants.ATTR_FOLDER_NAME);

        if (CheckUtils.isEmptyString(folderName))
            throw new SchemaInitializingException("\"" + FileXmlConstants.TAG_CHILD_FOLDER + "\" tag must have \"" +
                    FileXmlConstants.ATTR_FOLDER_NAME + "\" attribute.");

        FileManagerFolder fileManagerFolder = new FileManagerFolder(folderName);

        List<Element> folderElementList =
                XmlUtils.getChildElementsByTagName(childFolderElement, FileXmlConstants.TAG_CHILD_FOLDER);

        if (!CheckUtils.isEmptyList(folderElementList))
            for (Element tempFolderElement : folderElementList)
                fileManagerFolder.putFolder(createChildFolder(tempFolderElement));

        folderElementList =
                XmlUtils.getChildElementsByTagName(childFolderElement, FileXmlConstants.TAG_FOLDER);

        if (!CheckUtils.isEmptyList(folderElementList))
            for (Element tempFolderElement : folderElementList) {
                String tempFolderId = tempFolderElement.getAttribute(FileXmlConstants.ATTR_FOLDER_ID);

                if (CheckUtils.isEmptyString(tempFolderId))
                    throw new SchemaInitializingException("");

                FileManagerFolder tempFolder = idFileManagerFolderMap.get(tempFolderId);

                if (tempFolder == null) {
                    Element element = idFolderElementMap.get(tempFolderId);

                    if (element == null)
                        throw new SchemaInitializingException("");

                    //create new folder and put this map
                    tempFolder = createFolder(element);
                    idFileManagerFolderMap.put(tempFolderId, tempFolder);
                }

                fileManagerFolder.putFolder(tempFolder);
            }

        return fileManagerFolder;
    }

    private String getAttributeWithNullCheck(Element element, String attributeName, String message)
            throws SchemaInitializingException {

        String value = element.getAttribute(attributeName);

        if (CheckUtils.isEmptyString(value))
            throwSchemaInitializingException(message);

        return value;
    }

    private void throwSchemaInitializingException(String message) throws SchemaInitializingException {
        throw new SchemaInitializingException(message);
    }

    public FileManagerSchema getFileManagerSchema() {
        return schema;
    }
}