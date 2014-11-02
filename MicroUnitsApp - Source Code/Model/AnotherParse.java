/**
 * MicroUnits - Pauses Analysis of XML files generated by Translog II software.
 * For Translog II details See <http://bridge.cbs.dk/platform/?q=Translog-II>
 *
 * Copyright (C) 2014 Gabriel Ed. da Silva
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author Gabriel Ed
 */
public class AnotherParse {

    /**
     * XPath expression to gets all the Key fields
     */
    private static final String expressionKey = "//Key";

    /**
     * XPath expression to gets all the fixation fields
     */
    private static final String expressionfixation = "//Fix";

    /**
     * XPath expression to gets the stop time
     */
    private static final String expressionFinalTime = "//System[@Value='STOP']";

    /**
     * Parses a XML file generated by Translog II.
     *
     * @param filename - File path
     * @return MyDoc Object
     * @throws FileNotFoundException - File was not found or can not be read.
     * @throws IOException - I/O Error
     * @throws Exception - File parsing error
     */
    public static MyDoc parseDocument(String filename) throws FileNotFoundException, IOException, Exception {
        try {
            MyDoc myDoc = new MyDoc(filename);
            File arq = new File(filename);
            FileReader fr = new FileReader(arq);
            BufferedReader br = new BufferedReader(fr);
            String line;

            //Parses each file line
            while (br.ready()) {
                line = br.readLine();
                if (line.contains("<fixation")) {
                    myDoc.addfixation(parsefixation(line));
                } else if (line.contains("<Key Time")) {
                    myDoc.addKey(parseKey(line));
                } else if (line.contains("<System") && line.contains("STOP")) {
                    myDoc.setFinalTime(parseFinalTime(line));
                }
            }

            return myDoc;

        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("File [" + filename + "] was not found or can not be read.");
        } catch (IOException ex) {
            throw new IOException("I/O Error");
        } catch (Exception ex) {
            throw new Exception("File parsing error");
        }
    }

    // Gets all the fixation fields
    private static Integer parsefixation(String st) throws Exception {
        org.w3c.dom.Document xmlDocument = loadXMLFromString(st);

        XPath xPath;
        xPath = XPathFactory.newInstance().newXPath();

        Node node = (Node) xPath.compile(expressionfixation).evaluate(xmlDocument, XPathConstants.NODE);

        return Integer.parseInt(node.getAttributes().getNamedItem("Time").getNodeValue());
    }

    // Gets all the Key fields
    private static Key parseKey(String st) throws Exception {
        org.w3c.dom.Document xmlDocument = loadXMLFromString(st);
        XPath xPath;
        xPath = XPathFactory.newInstance().newXPath();
        Node node = (Node) xPath.compile(expressionKey).evaluate(xmlDocument, XPathConstants.NODE);

        Integer time = Integer.parseInt(node.getAttributes().getNamedItem("Time").getNodeValue());
        String value = node.getAttributes().getNamedItem("Value").getNodeValue();
        return new Key(time, value);
    }

    // Gets all the stop time
    private static Integer parseFinalTime(String st) throws Exception {
        org.w3c.dom.Document xmlDocument = loadXMLFromString(st);
        XPath xPath = XPathFactory.newInstance().newXPath();

        NodeList nodeList = (NodeList) xPath.compile(expressionFinalTime).evaluate(xmlDocument, XPathConstants.NODESET);
        Integer retorno = Integer.parseInt(nodeList.item(0).getAttributes().getNamedItem("Time").getNodeValue());
        return retorno;
    }

    /*
     * Creates a Document from a String
     * As seen in: http://stackoverflow.com/questions/562160/in-java-how-do-i-parse-xml-as-a-string-instead-of-a-file
     */
    private static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }
}