/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo, Cash Hilstad, Orion Hess
 * @version v1.0.0 03/28/2024
 */

package edu.gonzaga.Nuffatafl;

/** Class that contains the main entry point for program */
public class MainGame {

    // Keep track of main JFrame for adding JDialogs to it
    public static MainView mainView;

    /**
     * Main entry point for program
     * Creates a StateController and shows the WelcomeScreen for the game
     */
    public static void main(String[] args) {
        System.out.println("Program starting");

        javax.swing.SwingUtilities.invokeLater(() -> {
            mainView = new MainView();
            mainView.showGUI();
        });
    }
}