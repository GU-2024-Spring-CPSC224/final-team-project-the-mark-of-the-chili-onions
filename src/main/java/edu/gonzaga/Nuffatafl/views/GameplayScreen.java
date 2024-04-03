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

import edu.gonzaga.Nuffatafl.viewNavigation.ScreenChange;

import javax.swing.*;

/** JPanel that contains the UI for the Gameplay screen */
public class GameplayScreen extends JPanel {
    public GameplayScreen() {
        super();

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        JLabel label = new JLabel("Gameplay Screen");
        this.add(label);

        JButton showWelcomeScreenButton = new JButton("Show After Game Screen");
        showWelcomeScreenButton.addActionListener(ScreenChange.toAfterGameScreen);
        this.add(showWelcomeScreenButton);

        JButton showRulesScreenButton = new JButton("Show Rules Screen");
        showRulesScreenButton.addActionListener(ScreenChange.toRulesScreen);
        this.add(showRulesScreenButton);

        JButton showSettingsScreenButton = new JButton("Show Settings Screen");
        showSettingsScreenButton.addActionListener(ScreenChange.toSettingsScreen);
        this.add(showSettingsScreenButton);
    }
}
