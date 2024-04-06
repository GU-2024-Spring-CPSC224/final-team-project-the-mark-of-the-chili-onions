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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** JPanel that contains the UI for the Welcome screen */
public class WelcomeScreen extends JPanel {
    public WelcomeScreen() {
        super(new BorderLayout());

        this.add(topPanel(), BorderLayout.PAGE_START);
        this.add(middlePanel(), BorderLayout.CENTER);
    }

    /**
     * Set up the top panel of the welcome screen
     * @return JPanel top panel
     */
    private JPanel topPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Rules screen
        JButton showRulesScreenButton = new JButton("Rules");
        showRulesScreenButton.addActionListener(ScreenChange.toRulesScreen);
        topPanel.add(showRulesScreenButton);

        // Settings Screen
        JButton showSettingsScreenButton = new JButton("Settings");
        showSettingsScreenButton.setIcon(ImageLoading.getImage("./src/main/resources/settings.png", 20, 20));
        showSettingsScreenButton.addActionListener(ScreenChange.toSettingsScreen);
        topPanel.add(showSettingsScreenButton);

        return topPanel;
    }

    /**
     * Set up the middle panel of the welcome screen
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
        JLabel player1Label = new JLabel("Player 1 Name");
        p1NamePanel.add(player1Label);
        JTextField player1Dialog = new JTextField("Player 1");
        player1Dialog.setPreferredSize(new Dimension(100, 20));
        p1NamePanel.add(player1Dialog);
        middlePanel.add(p1NamePanel, gbc);

        // Set P2 name
        gbc.gridy++;
        JPanel p2NamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel player2Label = new JLabel("Player 2 Name");
        p2NamePanel.add(player2Label);
        JTextField player2Dialog = new JTextField("Player 2");
        player2Dialog.setPreferredSize(new Dimension(100, 20));
        p2NamePanel.add(player2Dialog);
        middlePanel.add(p2NamePanel, gbc);

        // Who goes first question
        gbc.gridy++;
        JLabel whoGoesFirst = new JLabel("  Who goes first?");
        middlePanel.add(whoGoesFirst, gbc);

        // Who goes first buttons
        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JToggleButton player1GoesFirst = new JToggleButton("Player 1");
        JToggleButton player2GoesFirst = new JToggleButton("Player 2");
        player1GoesFirst.setSelected(true);
        player1GoesFirst.addActionListener(actionEvent -> player2GoesFirst.setSelected(false));
        player2GoesFirst.addActionListener(actionEvent -> player1GoesFirst.setSelected(false));
        buttonPanel.add(player1GoesFirst);
        buttonPanel.add(player2GoesFirst);
        middlePanel.add(buttonPanel, gbc);

        // Go to gameplay screen
        gbc.gridy++;
        JButton showGameplayScreenButton = new JButton("Start Game (Show Gameplay screen)");
        showGameplayScreenButton.addActionListener(ScreenChange.toGameplayScreen);
        showGameplayScreenButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Call something to set names
                // Call something to set who goes first
            }
        });
        middlePanel.add(showGameplayScreenButton, gbc);


        return middlePanel;
    }
}
