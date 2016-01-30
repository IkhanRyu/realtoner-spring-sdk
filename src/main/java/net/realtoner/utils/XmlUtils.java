package net.realtoner.utils;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RyuIkHan
 */
public class XmlUtils {

    public static List<Element> getChildElementsByTagName(Element element, String tagName){

        NodeList nodeList = element.getChildNodes();

        if(nodeList == null)
            return null;

        List<Element> childElementList = new ArrayList<>();

        for(int i = 0; i < nodeList.getLength(); i++){

            if(nodeList.item(i).getNodeName().equals(tagName))
                childElementList.add((Element)nodeList.item(i));
        }

        return childElementList;
    }

    public static Element parse(File schemaFile) throws ParserConfigurationException, IOException, SAXException {

        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(schemaFile).getDocumentElement();
    }

    public static Element parse(InputStream inputStream) throws ParserConfigurationException, IOException,
            SAXException {

        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream).getDocumentElement();
    }
}
