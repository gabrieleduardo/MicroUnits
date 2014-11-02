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

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * @author Gabriel Ed. da Silva
 */
public class MyPdfDocument {

    /*
     * PDF File Type
     */
    private static final int BOTH = 0;
    private static final int LINEARPROTOCOL = 1;
    private static final int MICROUNITSWITHFIXATIONS = 2;

    /**
     * Create a pdf file
     *
     * @param path - Directory to Parse
     * @param file - PDF File name
     * @param pause - Time between pauses
     * @param type - 0 - Both, 1 - Linear Protocol, 2 - fixation
     * @throws java.lang.Exception - Exception
     */
    public static void create(String path, String file, Integer pause, Integer type) throws Exception {
        try {

            File dir = new File(path);
            String[] filename = dir.list();

            if (filename == null) {
                return;
            }

            switch (type) {
                case BOTH:
                    create(filename, path, file, pause);
                    break;
                case LINEARPROTOCOL:
                    createLinearProtocol(filename, path, file, pause);
                    break;
                case MICROUNITSWITHFIXATIONS:
                    createfixation(filename, path, file, pause);
                    break;
                default:
                    throw new Exception("Invalid type");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
    private static void create(String[] filename, String path, String file, Integer pause) throws Exception {

        ArrayList<ArrayList<String>> stList1 = new ArrayList<>(); // MicroUnits with Fixation
        ArrayList<ArrayList<String>> stList2 = new ArrayList<>(); // Linear Protocol
        String docName;

        /*
         * Gets all xml files in a directory and parses them
         * Each file is writing in a different pdf file
         */
        for (String st : filename) {
            if (st.endsWith(".xml")) {
                MyDoc myDoc = getMyDoc(path, st);

                // Clears the list to always have the title in first position
                stList1.clear();

                stList1.add(myDoc.pausefixationAnalysis(pause));

                /*
                 * Removes the file extensions and Concatenates the pdf title 
                 * with xml file name,  the final name will be a concatenation 
                 * of them plus ".pdf". Example: 
                 *
                 * file = "mypdf.pdf"
                 * st = "Example12345.xml"
                 * final = "mypdfExample12345.pdf"
                 */
                docName = removeExtensionsPdf(file) + removeExtensionsXml(st).toUpperCase() + ".pdf";
                // Writes a pdf file - MicroUnits with PDF type.
                stList1.get(0).set(0, removeExtensionsXml(st));
                write(stList1, docName);
                
                // Temp is used to change file title
                ArrayList<String> temp = myDoc.pauseAnalysis(pause);
                temp.set(0, removeExtensionsXml(st));

                stList2.add(temp);
            }
        }
        
        // Write the PDF - Linear Protocol Type
        if (!stList2.isEmpty()) {
            write(stList2, file);
        }
    }

    /**
     * Create a basic pdf file with Linear Protocol only
     */
    private static void createLinearProtocol(String[] filename, String path, String file, Integer pause)
            throws Exception {
        ArrayList<ArrayList<String>> stList = new ArrayList<>();

        // Gets all xml files in a directory and parses them
        for (String st : filename) {
            if (st.endsWith(".xml")) {
                MyDoc myDoc = getMyDoc(path, st);

                // Temp is used to change file title
                ArrayList<String> temp = myDoc.pauseAnalysis(pause);
                temp.set(0, removeExtensionsXml(st));

                stList.add(temp);
            }
        }

        // Write the PDF
        if (!stList.isEmpty()) {
            write(stList, file);
        }
    }

    /**
     * Create a pdf file with fixation parsing
     */
    private static void createfixation(String[] filename, String path, String file, Integer pause) throws Exception {

        ArrayList<ArrayList<String>> stList = new ArrayList<>();
        String docName;

        /*
         * Gets all xml files in a directory and parses them
         * Each file is writing in a different pdf file
         */
        for (String st : filename) {
            if (st.endsWith(".xml")) {
                MyDoc myDoc = getMyDoc(path, st);

                // Clears the list to always have the title in first position
                stList.clear();

                stList.add(myDoc.pausefixationAnalysis(pause));

                /*
                 * Removes the file extensions and Concatenates the pdf title 
                 * with xml file name,  the final name will be a concatenation 
                 * of them plus ".pdf". Example: 
                 *
                 * file = "mypdf.pdf"
                 * st = "Example12345.xml"
                 * final = "mypdfExample12345.pdf"
                 */
                docName = removeExtensionsPdf(file) + removeExtensionsXml(st).toUpperCase() + ".pdf";
                // Writes a pdf file
                stList.get(0).set(0, removeExtensionsXml(st));
                write(stList, docName);
            }
        }
        
    }
    
    /*
     * Write the pdf file
     */

    private static void write(ArrayList<ArrayList<String>> stList, String filename) throws DocumentException, FileNotFoundException, UnsupportedEncodingException {
        Document document = null;

        try {
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            MyFontSelector myFontSelector = MyFontSelector.getInstance();
            FontSelector fontSelector;
            Phrase phrase;

            for (ArrayList<String> stL : stList) {

                fontSelector = myFontSelector.getFontSelector("TITLE");
                phrase = fontSelector.process(stL.remove(0));
                document.add(new Paragraph(phrase));

                Paragraph paragraph = new Paragraph();
                addEmptyLine(paragraph, 1);

                for (String st : stL) {
                    fontSelector = myFontSelector.getFontSelector(getFontString(st));

                    /*
                     * In Windows we have some problems with Latin characters
                     * and we need to forces the encoding to UFT8.
                     */
                    if (isWindows()) {
                        st = new String(st.getBytes(), "UTF8");
                    }
                    
                    st = replace(st);

                    phrase = fontSelector.process(st);
                    paragraph.add(phrase);
                }

                document.add(paragraph);
                document.newPage();
            }

        } catch (DocumentException ex) {
            throw new DocumentException("Read error");
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("File [" + filename + "] not found");
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }

    /*
     * Gets the font color name
     * Production data = BLUE
     * Elimination data = GREEN
     * Navigationdata = BLACK
     * Pause data = RED
     */
    private static String getFontString(String st) {

        if (st.contentEquals("[Delete]") || st.contentEquals("[Back]") || st.contentEquals("[Ctrl+X]")) {
            return "GREEN";
        }

        if (st.endsWith("Up]") || st.endsWith("Down]") || st.endsWith("Left]") || st.endsWith("Right]")) {
            return "BLACK";
        }

        if (st.startsWith("(")) {
            return "RED";
        }

        return "BLUE";
    }

    /*
     * Replace some characters to unicode symbols
     */
    private static String replace(String st) {

        if (st.length() <= 8) {
            st = replaceSmall(st);
        } else if (st.length() >= 9 && st.length() <= 17) {
            st = replaceBig(st);
        } else {
            st = replaceSmall(st);
            st = replaceBig(st);
        }

        return st;
    }

    private static String replaceSmall(String st) {
        st = st.replaceAll("\\[Left\\]", "\u2190 ");
        st = st.replaceAll("\\[Up\\]", "\u2191 ");
        st = st.replaceAll("\\[Right\\]", "\u2192 ");
        st = st.replaceAll("\\[Down\\]", "\u2193 ");

        return st;
    }

    private static String replaceBig(String st) {
        st = st.replaceAll("\\[Shift\\+Left\\]", "[Shift+\u2190] ");
        st = st.replaceAll("\\[Shift\\+Up\\]", "[Shift+\u2191] ");
        st = st.replaceAll("\\[Shift\\+Right\\]", "[Shift+\u2192] ");
        st = st.replaceAll("\\[Shift\\+Down\\]", "[Shift+\u2193] ");
        st = st.replaceAll("\\[Ctrl\\+Left\\]", "[Ctrl+\u2190] ");
        st = st.replaceAll("\\[Ctrl\\+Up\\]", "[Ctrl+\u2191] ");
        st = st.replaceAll("\\[Ctrl\\+Right\\]", "[Ctrl+\u2192] ");
        st = st.replaceAll("\\[Ctrl\\+Down\\]", "[Ctrl+\u2193] ");
        st = st.replaceAll("\\[Ctrl\\+Shift\\+Left\\]", "[Ctrl+Shift+\u2190] ");
        st = st.replaceAll("\\[Ctrl\\+Shift\\+Up\\]", "[Ctrl+Shift+\u2191] ");
        st = st.replaceAll("\\[Ctrl\\+Shift\\+Right\\]", "[Ctrl+Shift+\u2192] ");
        st = st.replaceAll("\\[Ctrl\\+Shift\\+Down\\]", "[Ctrl+Shift+\u2193] ");

        return st;
    }

    /*
     * Method addEmptyLine as seen in: http://www.vogella.com/tutorials/JavaPDF/article.html
     */
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    /*
     * Gets the proper slash to the operating system
     */
    private static String getSlash() {
        //Microsoft Windows
        if (System.getProperty("os.name").startsWith("Windows")) {
            return "\\";
        }
        //Linux and MacOs
        return "/";
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    private static String removeExtensionsPdf(String st) {
        st = st.replaceAll(".pdf", "");
        return st;
    }

    private static String removeExtensionsXml(String st) {
        st = st.replaceAll(".xml", "");
        return st;
    }
    
    private static MyDoc getMyDoc(String path,String st) throws Exception{
        System.out.println("Parsing FIle: " + path + getSlash() + st);
        return AnotherParse.parseDocument(path + getSlash() + st);
    }
}
