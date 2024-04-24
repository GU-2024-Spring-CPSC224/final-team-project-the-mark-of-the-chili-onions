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


import edu.gonzaga.Nuffatafl.viewHelpers.Theme;
import edu.gonzaga.Nuffatafl.viewHelpers.ThemeComponent;
import edu.gonzaga.Nuffatafl.viewNavigation.StateController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

/** JPanel that contains the UI for the Settings screen */
public class SettingsScreen extends JPanel {
    private final StateController stateController;

    public SettingsScreen(StateController stateController) {
        super(new GridBagLayout());
        this.stateController = stateController;

        /*BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);*/
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        // Settings screen
        ThemeLabel label = new ThemeLabel("Settings Screen");
        this.add(label, gbc);
        gbc.gridy++;

        // Go back from settings screen
        ThemeButton goToPreviousViewButton = new ThemeButton("Hide Settings Screen", event -> stateController.goToPreviousScreen());
        this.add(goToPreviousViewButton, gbc);
        gbc.gridy++;

        // Themes
        ThemeLabel themeLabel = new ThemeLabel("Theme Settings");
        this.add(themeLabel, gbc);
        gbc.gridy++;

        // Theme selection
        this.add(themeComboBox(), gbc);
        gbc.gridy++;

        // Focus Mode
        this.add(focusModeCheckBox(stateController), gbc);
        gbc.gridy++;

        // Start new game
        ThemeButton startNewGame = new ThemeButton("Start New Game", event -> stateController.showWelcomeScreen());
        this.add(startNewGame, gbc);
        gbc.gridy++;

        ThemeButton exitProgramButton = new ThemeButton("Quit Program", event -> stateController.endProgram());
        this.add(exitProgramButton, gbc);

        Theme.setBackgroundFor(this, ThemeComponent.background);
    }

    JComboBox<String> themeComboBox() {
        // String array for getting indexes of string names
        ArrayList<String> themeStringOptions = new ArrayList<>();
        JComboBox<String> themeComboBox = new JComboBox<>();

        // Add all the available themes to both the combo box and the array of strings
        for (Theme theme : Theme.themes) {
            themeComboBox.addItem(theme.getName());
            themeStringOptions.add(theme.getName());
        }

        // Set the current theme in the combo box
        String currentTheme = Theme.getCurrentTheme().getName();
        themeComboBox.setSelectedIndex(themeStringOptions.indexOf(currentTheme));

        // Set up action event
        themeComboBox.addActionListener(actionEvent -> {
            String selected = themeStringOptions.get(themeComboBox.getSelectedIndex());
            Theme.setTheme(Theme.from(selected));
        });

        // Theming
        themeComboBox.setOpaque(false);
        Theme.setForegroundFor(themeComboBox, ThemeComponent.text);
        Theme.setBackgroundFor(themeComboBox, ThemeComponent.background);

        return themeComboBox;
    }

    JCheckBox focusModeCheckBox(StateController stateController) {
        JCheckBox focusModeCheckBox = new JCheckBox("Focus Mode");
        focusModeCheckBox.addActionListener(actionEvent -> {
            stateController.setFocusMode(focusModeCheckBox.isSelected());
        });

        focusModeCheckBox.setOpaque(false);
        focusModeCheckBox.setFocusPainted(false);
        focusModeCheckBox.setBorderPainted(false);
        focusModeCheckBox.setContentAreaFilled(false);
        Theme.setForegroundFor(focusModeCheckBox, ThemeComponent.text);
        Theme.setBackgroundFor(focusModeCheckBox, ThemeComponent.background);
        return focusModeCheckBox;
    }
}
