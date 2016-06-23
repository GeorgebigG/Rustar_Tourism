package com.rustaronline.mobile.rustartourism.WSDL;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * Created by gio on 6/23/16.
 */
public class ParseXml {

    public static String[] xmlAsString(String xmlRecords, String node, String element) {
        try {

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource input = new InputSource();
            input.setCharacterStream(new StringReader(xmlRecords));

            NodeList nodeList = builder.parse(input).getElementsByTagName(node);
            Element[] results = new Element[nodeList.getLength()];
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element body = (Element) nodeList.item(i);

                nodeList = body.getElementsByTagName(element);
                Element result = (Element) nodeList.item(0);

                results[i] = result;
            }
            return getCharacterFromElement(results);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new String[] { "Error..." };
        }
    }

    public static String[] getCharacterFromElement(Element[] elemensts) {
        String[] results = new String[elemensts.length];
        for (int i = 0; i < elemensts.length; i++) {
            Node child = elemensts[i].getFirstChild();
            if (child instanceof CharacterData) {
                results[i] = (((CharacterData) child).getData());
            }
        }
        if (results != null && results.length != 0)
            return results;

        return new String[] { "Error..." };
    }
}
