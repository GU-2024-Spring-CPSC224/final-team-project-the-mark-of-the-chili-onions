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
import edu.gonzaga.Nuffatafl.viewNavigation.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** JPanel that contains the UI for the Settings screen */
public class SettingsScreen extends JPanel {
    public SettingsScreen() {
        super(new GridBagLayout());
        /*BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);*/
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        // Settings screen
        JLabel label = new JLabel("Settings Screen");
        this.add(label, gbc);
        gbc.gridy++;

        // Go back from settings screen
        JButton goToPreviousViewButton = new JButton("Hide Settings Screen");
        goToPreviousViewButton.addActionListener(ScreenChange.toPreviousScreen);
        this.add(goToPreviousViewButton, gbc);
        gbc.gridy++;

        // Themes
        JLabel themeLabel = new JLabel("Theme Settings");
        this.add(themeLabel, gbc);
        gbc.gridy++;

        // Theme selection
        String[] themeChoices = {"Light", "Dark", "Purple", "Fruity"};
        //String currentTheme = getCurrentTheme();
        //String[] themeChoices = getThemeOptions();
        JComboBox<String> themeComboBox = new JComboBox<>(themeChoices);
        //themeComboBox.setSelectedIndex(getCurrentThemeIndex());
        themeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //setThemeOptions
            }
        });
        this.add(themeComboBox, gbc);
        gbc.gridy++;

        // Focus Mode
        JCheckBox focusModeCheckBox = new JCheckBox("Focus Mode");
        //focusModeCheckBox.setSelected(getFocusMode());
        focusModeCheckBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //focusModeCheckBox.isSelected() ? setFocusMode(true) : setFocusMode(false);
            }
        });
        this.add(focusModeCheckBox, gbc);
        gbc.gridy++;

        // Start new game
        JButton startNewGame = new JButton("Start New Game");
        startNewGame.addActionListener(ScreenChange.toWelcomeScreen);
        this.add(startNewGame, gbc);
        gbc.gridy++;
    }
}
