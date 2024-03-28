/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo
 * @version v0.1.0 03/28/2024
 */

package edu.gonzaga.views;

import edu.gonzaga.viewNavigation.ScreenChange;

import javax.swing.*;

/** JPanel that contains the UI for the Welcome screen */
public class WelcomeScreen extends JPanel {
    public WelcomeScreen() {
        super();

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        JLabel label = new JLabel("Welcome Screen");
        this.add(label);

        JButton showGameplayScreenButton = new JButton("Show Gameplay Screen");
        showGameplayScreenButton.addActionListener(ScreenChange.toGameplayScreen);
        this.add(showGameplayScreenButton);

        JButton showRulesScreenButton = new JButton("Show Rules Screen");
        showRulesScreenButton.addActionListener(ScreenChange.toRulesScreen);
        this.add(showRulesScreenButton);

        JButton showSettingsScreenButton = new JButton("Show Settings Screen");
        showSettingsScreenButton.addActionListener(ScreenChange.toSettingsScreen);
        this.add(showSettingsScreenButton);
    }
}
