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

/** JPanel that contains the UI for the Welcome screen */
public class WelcomeScreen extends JPanel {
    private final StateController stateController;

    public WelcomeScreen(StateController stateController) {
        super();
        this.stateController = stateController;

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        JLabel label = new JLabel("Welcome Screen");
        this.add(label);

        JButton showGameplayScreenButton = new JButton("Start Game");
        showGameplayScreenButton.addActionListener(event -> stateController.startGame());
        this.add(showGameplayScreenButton);

        JButton showRulesScreenButton = new JButton("Rules");
        showRulesScreenButton.addActionListener(event -> stateController.showRules());
        this.add(showRulesScreenButton);

        JButton showSettingsScreenButton = new JButton("Settings");
        showSettingsScreenButton.addActionListener(event -> stateController.showSettings());
        this.add(showSettingsScreenButton);
    }
}
