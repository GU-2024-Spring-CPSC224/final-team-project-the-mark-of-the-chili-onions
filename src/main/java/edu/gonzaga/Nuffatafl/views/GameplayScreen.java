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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** JPanel that contains the UI for the Gameplay screen */
public class GameplayScreen extends JPanel {
    private final StateController stateController;

    public GameplayScreen(StateController stateController) {
        super();
        this.stateController = stateController;

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        JLabel label = new JLabel("Gameplay Screen");
        this.add(label);

        JButton showWelcomeScreenButton = new JButton("End Game");
        showWelcomeScreenButton.addActionListener(event -> stateController.endGame());
        this.add(showWelcomeScreenButton);

        JButton showRulesScreenButton = new JButton("Rules");
        showRulesScreenButton.addActionListener(event -> stateController.showRules());
        this.add(showRulesScreenButton);

        JButton showSettingsScreenButton = new JButton("Settings");
        showSettingsScreenButton.addActionListener(event -> stateController.showSettings());
        this.add(showSettingsScreenButton);
    }
}
