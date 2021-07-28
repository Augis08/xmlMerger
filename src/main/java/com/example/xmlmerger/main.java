package com.example.xmlmerger;

import org.w3c.dom.Document;

import java.io.File;

public class main {

    public static void main(String[] args) {

        part1_MergeXmlFiles();

        part2_fixTranslations();

    }

    private static void part2_fixTranslations() {
        String pathToDoc = "C:/Users/Augis/Desktop/Translation/Translations 5.xml";
        String pathToErrors = "C:/Users/Augis/Desktop/Translation/WrongTranslations.txt";
        String pathToFixed = "C:/Users/Augis/Desktop/Translation/FixedTranslations.xml";
        String tagName = "trans-unit";

        Document translations = XmlReader.readXml(new File(pathToDoc));

        XmlEditor xmlEditor = new XmlEditor(translations);

        xmlEditor.fixDocument(tagName);
        XmlReader.saveXml(xmlEditor.getDocument(), pathToFixed);
        xmlEditor.writeToFile(xmlEditor.getWrongTranslations(), pathToErrors);
    }

    private static void part1_MergeXmlFiles() {
        String pathToDoc1 = "C:/Users/Augis/Desktop/Translation/Translations 3.xml";
        String pathToDoc2 = "C:/Users/Augis/Desktop/Translation/Translations 4.xml";
        String pathToMergedXml = "C:/Users/Augis/Desktop/Translation/MergedTranslations 2.xml";
        String pathToDuplicates = "C:/Users/Augis/Desktop/Translation/Duplicates 2.xml";
        String tagName = "trans-unit";

        Document transl1 = XmlReader.readXml(new File(pathToDoc1));
        Document transl2 = XmlReader.readXml(new File(pathToDoc2));

        XmlMerger xmlMerger = new XmlMerger(transl1, transl2);

        xmlMerger.mergeDocuments(tagName);
        XmlReader.saveXml(xmlMerger.getDocument1(), pathToMergedXml);
        XmlReader.saveXml(xmlMerger.getDocument2(), pathToDuplicates);

        //        System.out.println("doc1:   " + XmlReader.readXml(new File(pathToDoc1)).getElementsByTagName(tagName).getLength());
//        System.out.println("doc2:   " + XmlReader.readXml(new File(pathToDoc2)).getElementsByTagName(tagName).getLength());
//        System.out.println("merged: " + XmlReader.readXml(new File(pathToMergedXml)).getElementsByTagName(tagName).getLength());
//        System.out.println("dupl:   " + XmlReader.readXml(new File(pathToDuplicates)).getElementsByTagName(tagName).getLength());
    }
}
