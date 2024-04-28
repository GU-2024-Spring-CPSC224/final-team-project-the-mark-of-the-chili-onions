package edu.gonzaga.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.backend.Player;
import edu.gonzaga.Nuffatafl.viewHelpers.Theme;
import edu.gonzaga.Nuffatafl.viewHelpers.ThemeComponent;

import javax.swing.*;
import java.awt.*;

public class PlayerCustomizationPanel extends JPanel {
    private final Player player;
    private final JTextField nameField;

    public PlayerCustomizationPanel(Player player, int playerNumber) {
        super();
        this.player = player;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Label for which player it is
        ThemeLabel label = new ThemeLabel("Player #" + playerNumber);
        add(label);

        // Text field to set the player name
        nameField = new JTextField(player.name);
        Theme.setForegroundFor(nameField, ThemeComponent.text);
        Theme.setBackgroundFor(nameField, ThemeComponent.background);
        add(nameField);

        // Panel to choose the color
        JPanel colorChooserPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        ColorSquare colorSquare = new ColorSquare(player.color, 20);
        colorSquare.setPreferredSize(new Dimension(20, 20));

        ThemeButton player2ColorButton = new ThemeButton("Pick a color", event -> {
            Color color = JColorChooser.showDialog(
                    this,
                    "Select a color",
                    player.color
            );
            player.color = color;
            colorSquare.setColor(color);
        });

        colorChooserPanel.add(player2ColorButton);
        colorChooserPanel.add(colorSquare);

        Theme.setBackgroundFor(colorChooserPanel, ThemeComponent.background);
        add(colorChooserPanel);

        // Emoji Picker
        String[] emojis = {
                // Faces
                "😀", "🙃", "😆", "😅", "😂", "🥺", "😳", "😈", "😇", "😡", "🤬", "😱", "😛", "😘", "😎", "🤓", "🥸",
                // Weird stuff
                "👾", "👻", "🤡", "💩", "💀", "👽", "😻", "😼",
                // Animals
                "🐶", "🙈", "🐣", "🦄", "🐛", "🐙", "🐋",
                // Hearts
                "🩷", "❤️", "🧡", "💛", "💚", "🩵", "💙", "💜", "🖤", "🩶", "🤍", "🤎"
        };
        JComboBox<String> emojiComboBox = new JComboBox<>(emojis);
        emojiComboBox.setSelectedIndex(0);
        emojiComboBox.addActionListener(actionEvent -> player.emoji = emojiComboBox.getSelectedItem().toString());
        add(emojiComboBox);
        emojiComboBox.setOpaque(false);
        Theme.setForegroundFor(emojiComboBox, ThemeComponent.text);
        Theme.setBackgroundFor(emojiComboBox, ThemeComponent.background);

        // Return
        Theme.setBackgroundFor(this, ThemeComponent.background);
    }

    public Player getPlayer() {
        player.name = nameField.getText();
        return player;
    }
}
