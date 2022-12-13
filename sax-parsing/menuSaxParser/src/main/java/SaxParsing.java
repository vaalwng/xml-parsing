/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Val Wong
 */
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Val Wong
 */
public class SaxParsing extends DefaultHandler {

    private List<MenuLine> menuLines = new ArrayList<>();
    private MenuLine menuLine;
    private Boolean dealingWithContent = false;
    private StringBuffer content;

    public SaxParsing() {
        menuLine = new MenuLine();
        content = new StringBuffer();
    }
    
    public List<MenuLine> parseMenuLines() {
        try {
            File xmlDocument = Paths.get("../resources/menu.xml").toFile();

            // SAX Factory
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            // Parsing the document
            saxParser.parse(xmlDocument, this);
            
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return menuLines;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qualifiedName, Attributes attrs) throws SAXException {       
        switch (qualifiedName) {
            case "food":
                menuLine = new MenuLine();
                break;
            case "name":
                dealingWithContent = true;
                content = new StringBuffer();
                break;
            case "price":
                dealingWithContent = true;
                content = new StringBuffer();
                break;
            case "description":
                dealingWithContent = true;
                content = new StringBuffer();
                break;
            case "calories":
                dealingWithContent = true;
                content = new StringBuffer();
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (dealingWithContent) {
            content.append(ch, start, length);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qualifiedName) throws SAXException {
        switch (qualifiedName) {
            case "food":
                menuLines.add(menuLine);
                break;
            case "name":
                menuLine.setName(content.toString());
                dealingWithContent = false;
                break;
            case "price":
                menuLine.setPrice(content.toString());
                dealingWithContent = false;
                break;
            case "description":
                menuLine.setDescription(content.toString());
                dealingWithContent = false;
                break;
            case "calories":
                menuLine.setCalories(Integer.valueOf(content.toString()));
                dealingWithContent = false;
                break;
        }
    }

    public static void main(String[] args) {
        SaxParsing parser = new SaxParsing();
        List<MenuLine> list = parser.parseMenuLines();

        for (MenuLine menu : list) {
            System.out.println("Name: " + menu.getName() + "\nPrice: " + menu.getPrice() + "\nDescription: " + menu.getDescription() + "\nCalories: " + menu.getCalories() + "\n- - - -");
        }
    }
    
}