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

/** JPanel that contains the UI for the after game screen */
public class AfterGameScreen extends JPanel {

    private final StateController stateController;

    public AfterGameScreen(StateController stateController) {
        super(new BorderLayout());
        this.stateController = stateController;

        this.add(topPanel(), BorderLayout.PAGE_START);
        this.add(middlePanel(), BorderLayout.CENTER);

        Theme.setBackgroundFor(this, ThemeComponent.background);
    }

    /**
     * Set up the top panel of the welcome screen
     * @return JPanel top panel
     */
    private JPanel topPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        ThemeButton showRulesScreenButton = new ThemeButton("Rules", ImageLoading.rulesIcon(20), event -> stateController.showRules());
        topPanel.add(showRulesScreenButton);

        ThemeButton showSettingsScreenButton = new ThemeButton("Settings", ImageLoading.settingsIcon(20), event -> stateController.showSettings());
        topPanel.add(showSettingsScreenButton);

        Theme.setBackgroundFor(topPanel, ThemeComponent.background);
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
        Theme.setBackgroundFor(label, ThemeComponent.background);
        middlePanel.add(label, gbc);

        gbc.gridy++;
        ThemeButton showGameplayScreenButton = new ThemeButton("Show Welcome Screen", event -> stateController.showWelcomeScreen());
        middlePanel.add(showGameplayScreenButton, gbc);

        gbc.gridy++;
        JLabel afterGameStats = new JLabel("After Game Stats Stuff (Placeholder)");
        Theme.setBackgroundFor(afterGameStats, ThemeComponent.background);
        middlePanel.add(afterGameStats, gbc);


        gbc.gridy++;
        ThemeButton exitProgramButton = new ThemeButton("Quit Program", event -> stateController.endProgram());
        middlePanel.add(exitProgramButton, gbc);

        Theme.setBackgroundFor(middlePanel, ThemeComponent.background);
        return middlePanel;
    }
}
