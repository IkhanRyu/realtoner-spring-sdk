package net.realtoner.file.xml;

/**
 *
 * @author RyuIkHan
 */
interface FileXmlConstants {

    /*
    * Tag names
    * */
    String TAG_SCHEMA = "Schema";
    String TAG_ROOT_FOLDER = "RootFolder";
    String TAG_FOLDER = "Folder";
    String TAG_CHILD_FOLDER = "ChildFolder";
    String TAG_AUTHORITY = "Authority";
    String TAG_AUTHORITY_SET = "AuthoritySet";

    /*
    * "Schema" attributes
    * */
    String ATTR_ROOT_ORDER = "order";

    /*
    * "AuthoritySet" attribute
    * */
    String ATTR_AUTHORITY_SET_ID = "id";

    /*
    * "Authority" attributes
    * */
    String ATTR_AUTHORITY_ID = "id";
    String ATTR_AUTHORITY_NAME = "name";
    String ATTR_AUTHORITY_VALUE = "value";

    /*
    * "Folder" attribute
    * */
    String ATTR_FOLDER_ID = "id";
    String ATTR_FOLDER_NAME = "name";
    String ATTR_IS_ROOT = "___is__root__";
    String ATTR_FOLDER_AUTHORITY_SET = "authoritySet";

}
