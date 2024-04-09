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

/** JPanel that contains the UI for the after game screen */
public class AfterGameScreen extends JPanel {
    private final StateController stateController;

    public AfterGameScreen(StateController stateController) {
        super();
        this.stateController = stateController;

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        JLabel label = new JLabel("After Game Screen");
        this.add(label);

        JButton showGameplayScreenButton = new JButton("Start New Game");
        showGameplayScreenButton.addActionListener(event -> stateController.showWelcomeScreen());
        this.add(showGameplayScreenButton);

        JButton showRulesScreenButton = new JButton("Rules");
        showRulesScreenButton.addActionListener(event -> stateController.showRules());
        this.add(showRulesScreenButton);

        JButton showSettingsScreenButton = new JButton("Settings");
        showSettingsScreenButton.addActionListener(event -> stateController.showSettings());
        this.add(showSettingsScreenButton);

        JButton exitProgramButton = new JButton("Quit Program");
        exitProgramButton.addActionListener(event -> stateController.endProgram());
        this.add(exitProgramButton);
    }
}
