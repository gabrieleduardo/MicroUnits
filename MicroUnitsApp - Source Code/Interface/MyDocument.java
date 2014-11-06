/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

/**
 *
 * @author Gabriel Ed
 */
public interface MyDocument {
    
    /*
     * File Type
     */
    final int BOTH = 0;
    final int LINEARPROTOCOL = 1;
    final int MICROUNITSWITHFIXATIONS = 2;

    /**
     * Interface for Documents
     * 
     * @param path
     * @param file
     * @param pause
     * @param type
     * @throws java.lang.Exception
     */
    public void create(String path, String file, Integer pause, Integer type) throws Exception;
}
