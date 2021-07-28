package com.example.xmlmerger;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Data
public class XmlEditor {
    private Document document;
    private List<String> wrongTranslations;

    public XmlEditor(Document document) {
        this.document = document;
        this.wrongTranslations = new ArrayList<>();
    }

    public void fixDocument(String tagName) {
        NodeList nodes = document.getElementsByTagName(tagName);
        String[] errors = new String[]{"Ą", "ą", "Č", "č", "Ę", "ę", "Ė", "ė", "Į", "į", "Š", "š", "Ų", "ų", "Ū", "ū", "Ž", "ž"};
        String[] replace = new String[]{"A", "a", "C", "c", "E", "e", "E", "e", "I", "i", "S", "s", "U", "u", "U", "u", "Z", "z"};

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            String text = node.getChildNodes().item(1).getTextContent();

            if (!text.matches("[\\s\\w\\p{Punct}]+")) {
                String newText = StringUtils.replaceEach(text, errors, replace);
                node.getChildNodes().item(1).setTextContent(newText);
                wrongTranslations.add(text);
            }
        }
    }

    public void writeToFile(List<String> strings, String path) {
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(path), true);
            strings.forEach(printWriter::println);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
