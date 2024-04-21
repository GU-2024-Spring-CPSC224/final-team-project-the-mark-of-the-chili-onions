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
        JLabel label = new JLabel("Settings Screen");
        this.add(label, gbc);
        gbc.gridy++;

        // Go back from settings screen
        JButton goToPreviousViewButton = new JButton("Hide Settings Screen");
        goToPreviousViewButton.addActionListener(event -> stateController.goToPreviousScreen());
        this.add(goToPreviousViewButton, gbc);
        gbc.gridy++;

        // Themes
        JLabel themeLabel = new JLabel("Theme Settings");
        this.add(themeLabel, gbc);
        gbc.gridy++;

        // Theme selection
        ArrayList<String> themeStringOptions = new ArrayList<>();
        JComboBox<String> themeComboBox = new JComboBox<>();
        for (Theme theme : Theme.themes) {
            themeComboBox.addItem(theme.getName());
            themeStringOptions.add(theme.getName());
        }
        String currentTheme = Theme.getCurrentTheme().getName();
        themeComboBox.setSelectedIndex(themeStringOptions.indexOf(currentTheme));
        themeComboBox.addActionListener(actionEvent -> {
            String selected = themeStringOptions.get(themeComboBox.getSelectedIndex());
            Theme.setTheme(Theme.from(selected));
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
        startNewGame.addActionListener(event -> stateController.showWelcomeScreen());
        this.add(startNewGame, gbc);
        gbc.gridy++;

        JButton exitProgramButton = new JButton("Quit Program");
        exitProgramButton.addActionListener(event -> stateController.endProgram());
        this.add(exitProgramButton, gbc);
    }
}
