/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo
 * @version v0.1.0 03/28/2024
 */
package edu.gonzaga.Nuffatafl;

import edu.gonzaga.Nuffatafl.viewNavigation.StateController;

import javax.swing.plaf.nimbus.State;

/** Class that contains the main entry point for program */
public class MainGame {
    /**
     * Main entry point for program
     * Starts a new game on the singletoon ViewController.main
     */
    public static void main(String[] args) {
        System.out.println("Program starting");

        javax.swing.SwingUtilities.invokeLater(() -> {
           StateController stateController = new StateController();
           stateController.showWelcomeScreen();
       });
    }
}