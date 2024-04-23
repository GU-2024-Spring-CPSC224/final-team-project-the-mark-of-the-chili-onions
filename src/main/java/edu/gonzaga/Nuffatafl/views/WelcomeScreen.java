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

import edu.gonzaga.Nuffatafl.backend.Player;
import edu.gonzaga.Nuffatafl.backend.Team;
import edu.gonzaga.Nuffatafl.viewHelpers.Theme;
import edu.gonzaga.Nuffatafl.viewHelpers.ThemeComponent;
import edu.gonzaga.Nuffatafl.viewNavigation.StateController;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

/** JPanel that contains the UI for the Welcome screen */
public class WelcomeScreen extends JPanel {

    private final StateController stateController;

    Player p1;
    Player p2;


    public WelcomeScreen(StateController stateController) {
        super(new BorderLayout());
        this.stateController = stateController;

        p1 = new Player("Blinky", ":)", Color.red, Team.ATTACKER);
        p2 = new Player("Pinky", ";[", Color.pink, Team.DEFENDER);

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

        gbc.gridy++;
        middlePanel.add(playersPanel(), gbc);

        // Who goes first question
        gbc.gridy++;
        ThemeLabel whoGoesFirst = new ThemeLabel("  Who goes first?");
        middlePanel.add(whoGoesFirst, gbc);

        // Who goes first buttons
        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ThemeButton player1GoesFirst = new ThemeButton("Player 1", event -> {});
        ThemeButton player2GoesFirst = new ThemeButton("Player 2", event -> {});
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
            stateController.gameManager.setAttackerPlayer(p1);
            stateController.gameManager.setAttackerPlayer(p2);
            // Something to set who goes first
        });
        middlePanel.add(showGameplayScreenButton, gbc);


        Theme.setBackgroundFor(middlePanel, ThemeComponent.background);
        return middlePanel;
    }

    private JPanel playersPanel() {
        JPanel playersPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        playersPanel.add(setupPlayer(p1, 1));
        playersPanel.add(setupPlayer(p2, 2));

        Theme.setBackgroundFor(playersPanel, ThemeComponent.background);
        return playersPanel;
    }

    private JPanel setupPlayer(Player player, int playerNumber) {
        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);

        // Label for which player it is
        ThemeLabel label = new ThemeLabel("Player #" + playerNumber);
        panel.add(label);

        // Text field to set the player name
        JTextField nameField = new JTextField(player.name);
        Theme.setForegroundFor(nameField, ThemeComponent.text);
        Theme.setBackgroundFor(nameField, ThemeComponent.background);
        panel.add(nameField);

        // Panel to choose the color
        JPanel colorChooserPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        ColorSquare colorSquare = new ColorSquare(player.color, 20);
        colorSquare.setPreferredSize(new Dimension(20, 20));

        ThemeButton player2ColorButton = new ThemeButton("Pick a color", event -> {
            Color color = JColorChooser.showDialog(
                    this,
                    "Select a color",
                    player.color);
            player.color = color;
            colorSquare.setColor(color);
        });

        colorChooserPanel.add(player2ColorButton);
        colorChooserPanel.add(colorSquare);

        Theme.setBackgroundFor(colorChooserPanel, ThemeComponent.background);
        panel.add(colorChooserPanel);

        // Return
        Theme.setBackgroundFor(panel, ThemeComponent.background);
        return panel;
    }
}