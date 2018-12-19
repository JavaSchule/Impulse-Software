package net.impulse.daemon.screen;

import java.util.ArrayList;

/**
 * @author Mike
 */
public class ScreenHandler{

    /**
     * Load an save a screen
     */
    public ArrayList<String> screen = new ArrayList<>();

    /**
     * Clear a screen on linux
     */
    public void clearScreen(){
        System.out.print("\033[H\033[2J");
    }

}
