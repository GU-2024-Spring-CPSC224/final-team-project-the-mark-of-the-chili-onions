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
import edu.gonzaga.Nuffatafl.viewHelpers.PlayerChangeRelay;
import edu.gonzaga.Nuffatafl.viewHelpers.Theme;
import edu.gonzaga.Nuffatafl.viewHelpers.ThemeComponent;
import edu.gonzaga.Nuffatafl.viewNavigation.Screen;
import edu.gonzaga.Nuffatafl.viewNavigation.StateController;

import javax.naming.event.ObjectChangeListener;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.atomic.AtomicReference;

/** JPanel that contains the UI for the Welcome screen */
public class WelcomeScreen extends JPanel {

    private final StateController stateController;
    private final PropertyChangeSupport playerChangeSupport;

    Player p1;
    Player p2;


    public WelcomeScreen(StateController stateController) {
        super(new BorderLayout());
        this.stateController = stateController;

        playerChangeSupport = new PropertyChangeSupport(this);
        // Instatiates the playerChangeRelay once WelcomeScreen exists, this bridges the gap of MVC without having
        // to pass MainView everywhere to connect GameplayScreen and WelcomeScreen
        stateController.playerChangeRelay = new PlayerChangeRelay(this);

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
            if (player1GoesFirst.button.isSelected()) {
                playerChange(p1, true);
                playerChange(p2, false);
            } else {
                playerChange(p1, false);
                playerChange(p2, true);
            }
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

        // Emoji Picker
        String[] emojis = {"😀", "😃", "😄", "😁", "😆", "😅", "🤣", "😂", "🙂", "🙃"};
        JComboBox<String> emojiComboBox = new JComboBox<>(emojis);
        emojiComboBox.setSelectedIndex(0);
        emojiComboBox.addActionListener(actionEvent -> player.emoji = emojiComboBox.getSelectedItem().toString());
        panel.add(emojiComboBox);
        emojiComboBox.setOpaque(false);
        Theme.setForegroundFor(emojiComboBox, ThemeComponent.text);
        Theme.setBackgroundFor(emojiComboBox, ThemeComponent.background);


        // Return
        Theme.setBackgroundFor(panel, ThemeComponent.background);
        return panel;
    }

    /**
     * Adds an observer to be notified when a player is changed
     * @param listener Code to execute when the player changes
     */
    public void addPlayerListener(PropertyChangeListener listener) {
        playerChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Notifies listeners of a change to a player and changes the value for the stateController.gameManager
     * @param player the new player that changed
     * @param attacker whether the player that changed was an attacker;
     */
    private void playerChange(Player player, boolean attacker) {
        if (attacker) {
            player.team = Team.ATTACKER;
            stateController.gameManager.setAttackerPlayer(player);
            playerChangeSupport.firePropertyChange("attackerChange", player, player);
        } else {
            player.team = Team.DEFENDER;
            stateController.gameManager.setAttackerPlayer(player);
            playerChangeSupport.firePropertyChange("defenderChange", player, player);
        }
    }
}