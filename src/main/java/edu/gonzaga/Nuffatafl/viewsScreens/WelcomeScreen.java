/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Orion Hess, Mark Reggiardo
 * @version v1.0.0 03/28/2024
 */

package edu.gonzaga.Nuffatafl.viewsScreens;

import edu.gonzaga.Nuffatafl.backend.Player;
import edu.gonzaga.Nuffatafl.backend.Team;
import edu.gonzaga.Nuffatafl.viewHelpers.Theme;
import edu.gonzaga.Nuffatafl.viewHelpers.ThemeComponent;
import edu.gonzaga.Nuffatafl.viewNavigation.StateController;
import edu.gonzaga.Nuffatafl.views.ImageLoading;
import edu.gonzaga.Nuffatafl.views.PlayerCustomizationPanel;
import edu.gonzaga.Nuffatafl.views.ThemeButton;
import edu.gonzaga.Nuffatafl.views.ThemeLabel;

import javax.swing.*;
import java.awt.*;

/** JPanel that contains the UI for the Welcome screen */
public class WelcomeScreen extends JPanel {
    private final StateController stateController;
    /** The view to customize the color, emoji, and name for Player 1 */
    private final PlayerCustomizationPanel player1Panel;
    /** The view to customize the color, emoji, and name for Player 2 */
    private final PlayerCustomizationPanel player2Panel;
    /** Keeps track of which player is going first */
    private boolean player1FirstBool;

    public WelcomeScreen(StateController stateController) {
        super(new BorderLayout());
        this.stateController = stateController;

        player1Panel = new PlayerCustomizationPanel(stateController.gameManager.getAttacker(), 1);
        player2Panel = new PlayerCustomizationPanel(stateController.gameManager.getDefender(), 2);

        player1FirstBool = true;

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
        ThemeLabel label = new ThemeLabel(" Nuffatafl");
        label.label.setFont(new Font("Serif", Font.PLAIN, 60));
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
            player1FirstBool = true;
            player2GoesFirst.button.setSelected(false);
            Theme.setBackgroundFor(player1GoesFirst, ThemeComponent.background2);
            Theme.setBackgroundFor(player2GoesFirst, ThemeComponent.background);
        });
        player2GoesFirst.button.addActionListener(actionEvent -> {
            player1FirstBool = false;
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
        ThemeButton showGameplayScreenButton = new ThemeButton("Start Game", event -> {
            stateController.startGame();
            if (player1FirstBool) {
                Player player1 = player1Panel.getPlayer();
                player1.team = Team.ATTACKER;

                Player player2 = player2Panel.getPlayer();
                player2.team = Team.DEFENDER;

                stateController.gameManager.setAttacker(player1);
                stateController.gameManager.setDefender(player2);
            } else {
                Player player1 = player1Panel.getPlayer();
                player1.team = Team.DEFENDER;

                Player player2 = player2Panel.getPlayer();
                player2.team = Team.ATTACKER;

                stateController.gameManager.setAttacker(player2);
                stateController.gameManager.setDefender(player1);
            }
        });
        middlePanel.add(showGameplayScreenButton, gbc);

        Theme.setBackgroundFor(middlePanel, ThemeComponent.background);
        return middlePanel;
    }

    /** Contains the customization views for both players */
    private JPanel playersPanel() {
        JPanel playersPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        playersPanel.add(player1Panel);
        playersPanel.add(player2Panel);

        Theme.setBackgroundFor(playersPanel, ThemeComponent.background);
        return playersPanel;
    }
}