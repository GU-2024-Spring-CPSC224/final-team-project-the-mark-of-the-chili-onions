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

import edu.gonzaga.Nuffatafl.viewNavigation.ImageLoading;
import edu.gonzaga.Nuffatafl.viewNavigation.ScreenChange;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JPanel that contains the UI for the Welcome screen
 */
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

        JButton showRulesScreenButton = new JButton("Rules");
        showRulesScreenButton.addActionListener(ScreenChange.toRulesScreen);
        topPanel.add(showRulesScreenButton);

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
        JPanel middlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        JLabel label = new JLabel("Nuffatafl");
        label.setFont(new Font("Serif", Font.PLAIN, 60));
        middlePanel.add(label, gbc);

        gbc.gridy++;
        JLabel player1Label = new JLabel("Player 1 Name");
        middlePanel.add(player1Label, gbc);
        gbc.gridx++;
        JTextField player1Dialog = new JTextField("Player 1");
        middlePanel.add(player1Dialog, gbc);
        gbc.gridx--;

        gbc.gridy++;
        JLabel player2Label = new JLabel("Player 2 Name");
        middlePanel.add(player2Label, gbc);
        gbc.gridx++;
        JTextField player2Dialog = new JTextField("Player 2");
        middlePanel.add(player2Dialog, gbc);
        gbc.gridx--;

        gbc.gridy++;

        JButton showGameplayScreenButton = new JButton("Start Game (Show Gameplay screen)");
        showGameplayScreenButton.addActionListener(ScreenChange.toGameplayScreen);
        showGameplayScreenButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Call something to set names
            }
        });
        middlePanel.add(showGameplayScreenButton, gbc);


        return middlePanel;
    }
}
