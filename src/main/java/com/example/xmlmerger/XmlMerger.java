package com.example.xmlmerger;

import lombok.Data;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class XmlMerger {

    private Document document1;  // after all becomes merged document;
    private Document document2;  // after all becomes document with duplicates;

    public XmlMerger(Document document1, Document document2) {
        this.document1 = document1;
        this.document2 = document2;
    }

    public void mergeDocuments(String tagName) {
        NodeList nodes1 = document1.getElementsByTagName(tagName);
        NodeList nodes2 = document2.getElementsByTagName(tagName);

        List<String> nodesIdList = getNodesIdList(nodes1);
        int count = nodes2.getLength();

        for (int i = nodes2.getLength() - 1; i >= 0; i--) {
            Node checkingNode = nodes2.item(i);
            if (!isDuplicate(nodesIdList, checkingNode)) {
                Node node = document1.importNode(checkingNode, true);
                nodes1.item(0).getParentNode().appendChild(node);

                checkingNode.getParentNode().removeChild(checkingNode);
            }
            System.out.println(count + ": " + i);
        }
    }

    private static boolean isDuplicate(List<String> nodesId, Node node) {
        return nodesId.contains(node
                .getAttributes()
                .getNamedItem("id")
                .toString());
    }

    public List<String> getNodesIdList(NodeList nodeList) {
        return IntStream
                .range(0, nodeList.getLength())
                .mapToObj(i -> nodeList
                        .item(i)
                        .getAttributes()
                        .getNamedItem("id")
                        .toString())
                .collect(Collectors.toList());
    }
}
