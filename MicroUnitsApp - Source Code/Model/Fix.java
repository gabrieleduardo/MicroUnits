/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Gabriel Ed
 */
public class Fix {
    private Integer time;
    private Integer win;

    /**
     * Fix Class Constructor
     * Construtor da classe Fix
     * 
     * @param time Fixation time
     * @param win Window
     */
    public Fix(Integer time, Integer win) {
        this.time = time;
        this.win = win;
    }

    /**
     * Get the fix time
     * Recupera o tempo da fixação
     * 
     * @return The fix time
     */
    public Integer getTime() {
        return time;
    }

    /**
     * Set the fix time
     * Atualiza o tempo da fixação
     * 
     * @param time The new time value
     */
    public void setTime(Integer time) {
        this.time = time;
    }

    /**
     * Get the fix window
     * Recupera a janela da fixação
     * 
     * @return The Window
     */
    public Integer getWin() {
        return win;
    }

    /**
     * Set the fix window
     * Atualiza a janela da fixação
     * 
     * @param win Window
     */
    public void setWin(Integer win) {
        this.win = win;
    }
    
}
