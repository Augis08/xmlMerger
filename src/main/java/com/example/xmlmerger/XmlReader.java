package com.example.xmlmerger;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class XmlReader {

    public static Document readXml (File xml){
        Document document = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(xml);
            document.getDocumentElement().normalize();

        } catch (Exception ex){
            ex.printStackTrace();
        }
        return document;
    }

    public static void saveXml(Document document, String path) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(document);
            transformer.transform(source, result);
            Writer output = new BufferedWriter(new FileWriter(path));

            String xmlOutput = result.getWriter().toString();
            output.write(xmlOutput);
            output.close();
        } catch (TransformerException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
