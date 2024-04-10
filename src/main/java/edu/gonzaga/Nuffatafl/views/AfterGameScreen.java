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
import java.awt.*;

/** JPanel that contains the UI for the after game screen */
public class AfterGameScreen extends JPanel {

    private final StateController stateController;

    public AfterGameScreen(StateController stateController) {
        super(new BorderLayout());
        this.stateController = stateController;

        this.add(topPanel(), BorderLayout.PAGE_START);
        this.add(middlePanel(), BorderLayout.CENTER);
    }

    /**
     * Set up the top panel of the welcome screen
     * @return JPanel top panel
     */
    private JPanel topPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton showRulesScreenButton = new JButton("Rules");
        showRulesScreenButton.addActionListener(event -> stateController.showRules());
        topPanel.add(showRulesScreenButton);

        JButton showSettingsScreenButton = new JButton("Settings");
        showSettingsScreenButton.setIcon(ImageLoading.getImage("./src/main/resources/settings.png", 20, 20));

        showSettingsScreenButton.addActionListener(event -> stateController.showSettings());
        topPanel.add(showSettingsScreenButton);

        return topPanel;
    }

    /**
     * Set up the middle panel of the welcome screen
     * @return JPanel middle panel
     */
    private JPanel middlePanel() {
        JPanel middlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        JLabel label = new JLabel("After Game Screen");
        middlePanel.add(label, gbc);

        gbc.gridy++;
        JButton showGameplayScreenButton = new JButton("Show Welcome Screen");
        showGameplayScreenButton.addActionListener(event -> stateController.showWelcomeScreen());
        middlePanel.add(showGameplayScreenButton, gbc);

        gbc.gridy++;
        JLabel afterGameStats = new JLabel("After Game Stats Stuff (Placeholder)");
        middlePanel.add(afterGameStats, gbc);



        JButton exitProgramButton = new JButton("Quit Program");
        exitProgramButton.addActionListener(event -> stateController.endProgram());
        this.add(exitProgramButton);
        return middlePanel;
    }
}
