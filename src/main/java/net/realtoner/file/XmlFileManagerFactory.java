package net.realtoner.file;

import net.realtoner.file.exception.SchemaInitializingException;
import net.realtoner.file.schema.FileManagerFolder;
import net.realtoner.file.schema.FileManagerSchema;
import net.realtoner.file.xml.FileXmlParser;
import net.realtoner.utils.CheckUtils;
import net.realtoner.utils.XmlUtils;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 *
 *
 *     <p>Parsing xml and build folder structure has phases.</p>
 *      <br />
 *
 *      <p>
 *     1. init root phase <br />
 *     2. init authority phase <br />
 *     3. init folder phase <br />
 *     4. build folder phase <br />
 *     5. build root folder phase <br />
 *     </p>
 *
 *      <br />
 *
 *      <p>Basically, each phase checks whether tag name is correct and
 *      required attributes exist. If not , throw {@link SchemaInitializingException}.
 *      </p>
 *
 *      <br />
 *
 *      Below, the detail of each phase. <br /><br />
 *
 *      <p>
 *      1. init root phase.<br />
 *
 *      Check whether there is "Schema" tag and get information about current schema.
 *      "Schema" tag has "order" attribute.
 *      </p>
 *
 *      <p>
 *      2. init authority phase.<br />
 *
 *      </p>
 *      3. init folder phase.
 *      4. build folder phase.
 *      5. build root folder phase.
 * </p>
 *
 * @author RyuIkHan
 * @see AbstractFileManagerFactory
 */
public abstract class XmlFileManagerFactory extends AbstractFileManagerFactory {

    private File schemaFile = null;

    private FileXmlParser fileXmlParser = null;

    public void setSchemaFile(File schemaFile){
        this.schemaFile = schemaFile;
    }

    @Override
    protected void beforeBuild() throws SchemaInitializingException {

        try {
            fileXmlParser = new FileXmlParser(schemaFile);
            fileXmlParser.build();

        } catch(Exception e){
            throw new SchemaInitializingException(e);
        }
    }

    @Override
    protected FileManagerSchema getSchema() throws SchemaInitializingException{
        return fileXmlParser.getFileManagerSchema();
    }

    /**
     *
     * */
    @Override
    protected void buildFolderStructure(FileManagerFolder rootFolder) {
        //do nothing
    }

    @Override
    protected int getOrder() {
        return fileXmlParser.getFileManagerSchema().getOrder();
    }
}