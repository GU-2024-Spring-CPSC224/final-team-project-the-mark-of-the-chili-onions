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
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** JPanel that contains the UI for the Welcome screen */
public class WelcomeScreen extends JPanel {

    private final StateController stateController;

    public WelcomeScreen(StateController stateController) {
        super(new BorderLayout());
        this.stateController = stateController;

        this.add(topPanel(), BorderLayout.PAGE_START);
        this.add(middlePanel(), BorderLayout.CENTER);

        Theme.setBackgroundFor(this, ThemeComponent.background);
    }

    /**
     * Set up the top panel of the welcome screen
     *
     * @return JPanel top panel
     */
    private JPanel topPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Rules screen
        ThemeButton showRulesScreenButton = new ThemeButton("Rules", ImageLoading.rulesIcon(20), event -> stateController.showRules());
        topPanel.add(showRulesScreenButton);

        // Settings Screen
        ThemeButton showSettingsScreenButton = new ThemeButton("Settings", ImageLoading.settingsIcon(20), event -> stateController.showSettings());
        topPanel.add(showSettingsScreenButton);

        Theme.setBackgroundFor(topPanel, ThemeComponent.background);
        return topPanel;
    }

    /**
     * Set up the middle panel of the welcome screen
     *
     * @return JPanel middle panel
     */
    private JPanel middlePanel() {
        // Setup GridBag for fancy centering
        JPanel middlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        // Title text
        JLabel label = new JLabel("Nuffatafl");
        label.setFont(new Font("Serif", Font.PLAIN, 60));
        middlePanel.add(label, gbc);

        // Set P1 name
        gbc.gridy++;
        JPanel p1NamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ThemeLabel player1Label = new ThemeLabel("Player 1 Name");
        p1NamePanel.add(player1Label);
        JTextField player1Dialog = new JTextField("Player 1");
        player1Dialog.setPreferredSize(new Dimension(100, 20));
        Theme.setForegroundFor(player1Dialog, ThemeComponent.text);
        Theme.setBackgroundFor(player1Dialog, ThemeComponent.background2);
        p1NamePanel.add(player1Dialog);

        Theme.setBackgroundFor(p1NamePanel, ThemeComponent.background);
        middlePanel.add(p1NamePanel, gbc);

        // Set P2 name
        gbc.gridy++;
        JPanel p2NamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ThemeLabel player2Label = new ThemeLabel("Player 2 Name");
        p2NamePanel.add(player2Label);
        JTextField player2Dialog = new JTextField("Player 2");
        player2Dialog.setPreferredSize(new Dimension(100, 20));
        Theme.setForegroundFor(player2Dialog, ThemeComponent.text);
        Theme.setBackgroundFor(player2Dialog, ThemeComponent.background2);
        p2NamePanel.add(player2Dialog);

        Theme.setBackgroundFor(p2NamePanel, ThemeComponent.background);
        middlePanel.add(p2NamePanel, gbc);

        // Who goes first question
        gbc.gridy++;
        ThemeLabel whoGoesFirst = new ThemeLabel("  Who goes first?");
        middlePanel.add(whoGoesFirst, gbc);

        // Who goes first buttons
        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ThemeToggleButton player1GoesFirst = new ThemeToggleButton("Player 1", event -> {});
        ThemeToggleButton player2GoesFirst = new ThemeToggleButton("Player 2", event -> {});
        player1GoesFirst.button.addActionListener(actionEvent -> {
            player2GoesFirst.button.setSelected(false);
            player1GoesFirst.button.setBackground(Theme.getCurrentTheme().colorForKey(ThemeComponent.background));
            player2GoesFirst.button.setBackground(Theme.getCurrentTheme().colorForKey(ThemeComponent.background2));
        });
        player2GoesFirst.button.addActionListener(actionEvent -> {
            player1GoesFirst.button.setSelected(false);
            player2GoesFirst.button.setBackground(Theme.getCurrentTheme().colorForKey(ThemeComponent.background));
            player1GoesFirst.button.setBackground(Theme.getCurrentTheme().colorForKey(ThemeComponent.background2));
        });
        player1GoesFirst.button.doClick();
        buttonPanel.add(player1GoesFirst);
        buttonPanel.add(player2GoesFirst);
        Theme.setBackgroundFor(buttonPanel, ThemeComponent.background);
        middlePanel.add(buttonPanel, gbc);

        // Go to gameplay screen
        gbc.gridy++;
        ThemeButton showGameplayScreenButton = new ThemeButton("Start Game (Show Gameplay screen)", event -> {
            stateController.startGame();
            // Something to set names
            // Something to set who goes first
        });
        middlePanel.add(showGameplayScreenButton, gbc);


        Theme.setBackgroundFor(middlePanel, ThemeComponent.background);
        return middlePanel;
    }
}