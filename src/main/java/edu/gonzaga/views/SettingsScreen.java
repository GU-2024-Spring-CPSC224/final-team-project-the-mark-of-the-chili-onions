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

/** JPanel that contains the UI for the Settings screen */
public class SettingsScreen extends JPanel {
    public SettingsScreen() {
        super();

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        JLabel label = new JLabel("Settings Screen");
        this.add(label);

        JButton goToPreviousViewButton = new JButton("Hide Settings Screen");
        goToPreviousViewButton.addActionListener(ScreenChange.toPreviousScreen);
        this.add(goToPreviousViewButton);
    }
}
