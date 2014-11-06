/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Interface.MyDocument;

/**
 *
 * @author Gabriel Ed
 */
public class MyXlsDocument implements MyDocument {

    private MyXlsDocument() {
    }

    public static MyXlsDocument getInstance() {
        return MyXlsDocumentHolder.INSTANCE;
    }

    @Override
    public void create(String path, String file, Integer pause, Integer type) throws Exception {
        throw new Exception("Não implementado!!");
    }

    private static class MyXlsDocumentHolder {

        private static final MyXlsDocument INSTANCE = new MyXlsDocument();
    }
}
