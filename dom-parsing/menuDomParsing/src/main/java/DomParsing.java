/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Val Wong
 */
public class DomParsing {

    public List<MenuLine> parseMenuLines() {

        List<MenuLine> menuLines = new ArrayList<>();

        try {
            File xmlDocument = Paths.get("../resources/menu.xml").toFile();

            // DOM Factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Parsing the document
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlDocument);

            NodeList foodLinesNode = document.getElementsByTagName("food");
            for (int i = 0; i < foodLinesNode.getLength(); i++) {
                Element foodLineNode = (Element) foodLinesNode.item(i);
                MenuLine menuLine = new MenuLine();

                Node nameNode = foodLineNode.getChildNodes().item(1);
                menuLine.setName(nameNode.getFirstChild().getNodeValue());
                                
                Node priceNode = foodLineNode.getChildNodes().item(3);
                menuLine.setPrice(priceNode.getFirstChild().getNodeValue());

                Node descriptionNode = foodLineNode.getChildNodes().item(5);
                menuLine.setDescription(descriptionNode.getFirstChild().getNodeValue());

                Node caloriesNode = foodLineNode.getChildNodes().item(7);
                menuLine.setCalories(Integer.valueOf(caloriesNode.getFirstChild().getNodeValue()));
                
                menuLines.add(menuLine);
            }

        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return menuLines;
    }

    public static void main(String[] args) {

        DomParsing parser = new DomParsing();
        List<MenuLine> list = parser.parseMenuLines();

        for (MenuLine menu : list) {
            System.out.println("Name: " + menu.getName() + "\nPrice: " + menu.getPrice() + "\nDescription: " + menu.getDescription() + "\nCalories: " + menu.getCalories() + "\n- - - -");
        }

    }

}