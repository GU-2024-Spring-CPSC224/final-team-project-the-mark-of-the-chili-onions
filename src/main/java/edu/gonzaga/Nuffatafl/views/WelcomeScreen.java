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

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * JPanel that contains the UI for the Welcome screen
 */
public class WelcomeScreen extends JPanel {
    public WelcomeScreen() {
        super(new BorderLayout());


        this.add(topPanel(), BorderLayout.PAGE_START);
        this.add(middlePanel(), BorderLayout.CENTER);
    }

    private JPanel topPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton showRulesScreenButton = new JButton("Rules");
        showRulesScreenButton.addActionListener(ScreenChange.toRulesScreen);
        topPanel.add(showRulesScreenButton);

        JButton showSettingsScreenButton = new JButton("Settings");
        try {
            showSettingsScreenButton.setIcon(new ImageIcon("./src/main/resources/settings.png"));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        showSettingsScreenButton.addActionListener(ScreenChange.toSettingsScreen);
        topPanel.add(showSettingsScreenButton);

        return topPanel;
    }

    private JPanel middlePanel() {
        JPanel middlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel label = new JLabel("Welcome Screen");
        middlePanel.add(label, gbc);

        gbc.gridy++;
        JButton showGameplayScreenButton = new JButton("Start Game (Show Gameplay screen)");
        showGameplayScreenButton.addActionListener(ScreenChange.toGameplayScreen);
        middlePanel.add(showGameplayScreenButton, gbc);


        middlePanel.setBackground(Color.blue);

        return middlePanel;
    }
}
