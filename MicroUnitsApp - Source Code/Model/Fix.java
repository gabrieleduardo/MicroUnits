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

    public Fix(Integer time, Integer win) {
        this.time = time;
        this.win = win;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getWin() {
        return win;
    }

    public void setWin(Integer win) {
        this.win = win;
    }
    
}
