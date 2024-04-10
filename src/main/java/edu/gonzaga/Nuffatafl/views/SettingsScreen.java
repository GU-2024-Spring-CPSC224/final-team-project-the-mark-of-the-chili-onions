/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo
 * @version v0.1.0 03/28/2024
 */

package edu.gonzaga.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.viewNavigation.StateController;

import javax.swing.*;

/** JPanel that contains the UI for the Settings screen */
public class SettingsScreen extends JPanel {
    private final StateController stateController;

    public SettingsScreen(StateController stateController) {
        super();
        this.stateController = stateController;

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        JLabel label = new JLabel("Settings");
        this.add(label);

        JButton goToPreviousViewButton = new JButton("Back");
        goToPreviousViewButton.addActionListener(event -> stateController.goToPreviousScreen());
        this.add(goToPreviousViewButton);

        JButton exitProgramButton = new JButton("Quit Program");
        exitProgramButton.addActionListener(event -> stateController.endProgram());
        this.add(exitProgramButton);
    }
}
