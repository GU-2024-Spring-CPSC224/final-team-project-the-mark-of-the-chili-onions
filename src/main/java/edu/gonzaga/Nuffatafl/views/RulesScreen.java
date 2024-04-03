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

/** JPanel that contains the UI for the Rules screen */
public class RulesScreen extends JPanel {
    public RulesScreen() {
        super();

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        JLabel label = new JLabel("Rules Screen");
        this.add(label);

        JButton goToPreviousViewButton = new JButton("Hide Rules Screen");
        goToPreviousViewButton.addActionListener(ScreenChange.toPreviousScreen);
        this.add(goToPreviousViewButton);
    }
}
