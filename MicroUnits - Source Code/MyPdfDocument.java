/**
 * MicroUnits - Pauses Analysis in XML files generated by Translog II software.
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

package MicroUnits;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gabriel Ed. da Silva
 */

public class MyPdfDocument {

    /*
     * Troque o caminho da variavel fontPath para caminho da fonte desejada
     */
    private static final String fontPath = "/resources/MSMHei.ttf";

    /*
     * Declaração de fontes. Altere os itens abaixo para mudar o tamanho da fonte,
     * formatação e coloração.
     */
    private static final Font titulo = new Font(getUnicodeFont(), 16,
            Font.BOLD, BaseColor.BLACK);

    private static final Font azul = new Font(getUnicodeFont(), 14,
            Font.NORMAL, BaseColor.BLUE);

    private static final Font verde = new Font(getUnicodeFont(), 14,
            Font.NORMAL, BaseColor.GREEN);

    private static final Font vermelho = new Font(getUnicodeFont(), 14,
            Font.NORMAL, BaseColor.RED);

    private static final Font preto = new Font(getUnicodeFont(), 14,
            Font.NORMAL, BaseColor.BLACK);

    /**
     * Cria um arquivo PDF da analise de pausas
     *
     * @param path - Diretório dos Arquivos XML
     * @param arquivo - Nome do Arquivo PDf a ser gerado
     * @param pausas - Tempo de Pausa
     */
    public static void create(String path, String arquivo, Integer pausas) {

        File dir = new File(path);
        String[] nomeArquivos = dir.list();
        ArrayList<ArrayList<String>> stList = new ArrayList<>();

        if (nomeArquivos == null) {
            return;
        }

        System.out.println("Arquivos no Diretório: " + nomeArquivos.length);

        for (String st : nomeArquivos) {
            if (st.endsWith(".xml")) {
                stList.add(Key.analisarPausas(path + "/" + st, pausas));
                System.out.println("Analisando: " + path + "/" + st);
            }
        }

        if (!stList.isEmpty()) {
            write(stList, arquivo);
        } else {
            System.out.println("Nenhum arquivo XML no diretório");
        }
    }

    /*
     * Escreve os dados recuperados em um arquivo PDF.
     */
    private static void write(ArrayList<ArrayList<String>> stList, String filename) {
        Document document = null;

        try {
            // Prepara o Documento
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            // Variaveis
            Font font;

            //Adiciona Caracteres
            for (ArrayList<String> stL : stList) {
                //Adiciona Título

                document.add(new Paragraph(stL.remove(0), titulo));

                Paragraph paragraph = new Paragraph();
                addEmptyLine(paragraph, 1);

                for (String st : stL) {
                    font = getFont(st);

                    //Caso seja um comando, faz substituição por símbolos.
                    if (st.startsWith("[")) {
                        st = replace(st);
                    }

                    paragraph.add(new Chunk(st, font));
                }

                document.add(paragraph);
                document.newPage();
            }

        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(MyPdfDocument.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }

    /*
     * Determina a cor da fonte, baseado no tipo do Dado
     * Dados de produção = azul
     * Dados de eliminação = verde
     * Dados de navegação = preto
     * Dados de pausa = vermelho
     */
    private static Font getFont(String st) {

        if (st.contentEquals("[Delete]") || st.contentEquals("[Back]") || st.contentEquals("[Ctrl+X]")) {
            return verde;
        }

        if (st.endsWith("Up]") || st.endsWith("Down]") || st.endsWith("Left]") || st.endsWith("Right]")) {
            return preto;
        }

        if (st.startsWith("(")) {
            return vermelho;
        }

        return azul;
    }

    // Substituição de Dados por caracteres unicode
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
     * Método addEmptyLine retirado de: 
     * http://www.vogella.com/tutorials/JavaPDF/article.html
     */
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    /*
     * Instância a fonte a ser utilizada no pdf. Caso deseje utilizar fontes
     * presentes no iText, substitua o campo fontPath por BaseFont.<Fonte desejada>
     */
    private static BaseFont getUnicodeFont() {
        try {
            return BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(MyPdfDocument.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
