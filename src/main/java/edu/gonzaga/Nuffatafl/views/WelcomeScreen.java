/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo, Orion Hess
 * @version v0.1.0 03/28/2024
 */

package edu.gonzaga.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.viewHelpers.Theme;
import edu.gonzaga.Nuffatafl.viewHelpers.ThemeComponent;
import edu.gonzaga.Nuffatafl.viewNavigation.StateController;
import javax.swing.*;
import java.awt.*;

/** JPanel that contains the UI for the Welcome screen */
public class WelcomeScreen extends JPanel {

    private final StateController stateController;

    private PlayerCustomizationPanel player1Panel;
    private PlayerCustomizationPanel player2Panel;

    public WelcomeScreen(StateController stateController) {
        super(new BorderLayout());
        this.stateController = stateController;

        player1Panel = new PlayerCustomizationPanel(stateController.gameManager.getAttacker(), 1);
        player2Panel = new PlayerCustomizationPanel(stateController.gameManager.getDefender(), 2);

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
        JLabel label = new JLabel(" Nuffatafl");
        label.setFont(new Font("Serif", Font.PLAIN, 60));
        middlePanel.add(label, gbc);

        gbc.gridy++;
        middlePanel.add(playersPanel(), gbc);

        // Who goes first question
        gbc.gridy++;
        ThemeLabel whoGoesFirst = new ThemeLabel("  Who goes first (Attacker)?");
        middlePanel.add(whoGoesFirst, gbc);

        // Who goes first buttons
        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ThemeButton player1GoesFirst = new ThemeButton("Player 1", event -> {
        });
        ThemeButton player2GoesFirst = new ThemeButton("Player 2", event -> {
        });
        player1GoesFirst.button.addActionListener(actionEvent -> {
            player2GoesFirst.button.setSelected(false);
            Theme.setBackgroundFor(player1GoesFirst, ThemeComponent.background2);
            Theme.setBackgroundFor(player2GoesFirst, ThemeComponent.background);
        });
        player2GoesFirst.button.addActionListener(actionEvent -> {
            player1GoesFirst.button.setSelected(false);
            Theme.setBackgroundFor(player2GoesFirst, ThemeComponent.background2);
            Theme.setBackgroundFor(player1GoesFirst, ThemeComponent.background);
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
            if (player1GoesFirst.button.isSelected()) {
                stateController.gameManager.setAttacker(player1Panel.getPlayer());
                stateController.gameManager.setDefender(player2Panel.getPlayer());
            } else {
                stateController.gameManager.setAttacker(player2Panel.getPlayer());
                stateController.gameManager.setDefender(player1Panel.getPlayer());
            }
        });
        middlePanel.add(showGameplayScreenButton, gbc);

        Theme.setBackgroundFor(middlePanel, ThemeComponent.background);
        return middlePanel;
    }

    private JPanel playersPanel() {
        JPanel playersPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        playersPanel.add(player1Panel);
        playersPanel.add(player2Panel);

        Theme.setBackgroundFor(playersPanel, ThemeComponent.background);
        return playersPanel;
    }
}