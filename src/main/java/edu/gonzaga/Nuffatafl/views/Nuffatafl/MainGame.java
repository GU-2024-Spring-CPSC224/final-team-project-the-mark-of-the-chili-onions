/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo
 * @version v0.1.0 03/28/2024
 */
package edu.gonzaga.Nuffatafl.views.Nuffatafl;

import edu.gonzaga.Nuffatafl.views.Nuffatafl.views.MainView;

/** Class that contains the main entry point for program */
public class MainGame {
    /**
     * Main entry point for program
     * Creates a StateController and shows the WelcomeScreen for the game
     */
    public static void main(String[] args) {
        System.out.println("Program starting");

        javax.swing.SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();
            mainView.showGUI();
       });
    }
}