/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;

/**
 *
 * @author Gabriel Ed
 */
public class StringTreatment {
    
    /**
     * Gets the proper slash to the operating system
     * Recupera a barra própria para o sistema operacional
     * 
     * @return File Separator string
     */
    public static String getSlash() {
        return File.separator;
    }
    
    /**
     * Remove the pdf extension
     * Remove a extensão do arquivo pdf
     * 
     * @param st File Name
     * @return File name without .pdf extension
     */
    
    public static String removeExtensionsPdf(String st) {
        st = st.replaceAll(".pdf", "");
        return st;
    }
    
    /**
     * Remove the xml extension
     * Remove a extensão do arquivo xml
     * 
     * @param st File Name
     * @return File name without .xml extesion
     */

    public static String removeExtensionsXml(String st) {
        st = st.replaceAll(".xml", "");
        return st;
    }
    
    /**
     * Gets the font color name
     * Recupera o nome da cor de fonte
     * 
     * Production data = BLUE
     * Dados de produção = Azul
     * 
     * Elimination data = GREEN
     * Dados de Eliminação = Verde
     * 
     * Navigation data = BLACK
     * Dados de Navegação = Preto
     * 
     * Pause data = RED
     * Dados de pausa = Vermelho
     * 
     * @param st String com um campo xml
     * @return File color name
     */
    public static String getFontString(String st) {

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
    
    /**
     * Replace all white spaces in a string
     * Substituí todos os espaços em branco
     * 
     * @param st String with white spaces
     * @return String without white spaces
     */
    public static String replaceWhiteSpaces(String st){
        st = st.replaceAll(" ","_");
        return st;
        
    }

    /**
     * Replace some characters to unicode symbols
     * Substituí os valores de uma string
     * 
     * @param st String to be replaced
     * @return Replaced string
     */
    public static String replace(String st) {

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
        //st = st.replaceAll("\\[Back\\]", "");
        //st = st.replaceAll("\\[Return\\]", "");
        //st = st.replaceAll("\\[Delete\\]", "");

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
    
    /**
     * Format a Number with 2 cases
     * Formata um número com duas casas decimais.
     * 
     * @param n A Number
     * @return The Number with 2 cases.
     */
    
    public static String format2f(Number n) {
        String st = String.format("%.2f", n);
        return st.replaceAll(",",".");
    }
    
}
