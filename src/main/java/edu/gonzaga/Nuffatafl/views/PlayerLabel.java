package edu.gonzaga.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.backend.Player;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/** Displays a player's emoji and name */
public class PlayerLabel extends JPanel {
    public ThemeLabel emojiLabel;
    public ThemeLabel nameLabel;

    public PlayerLabel(Player player) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        emojiLabel = new ThemeLabel(player.emoji);
        emojiLabel.setBorder(new EmptyBorder(13, 13, 13, 13));
        add(emojiLabel);
        nameLabel = new ThemeLabel(player.name);
        nameLabel.setBorder(new EmptyBorder(13, 0, 13, 13));
        add(nameLabel);
    }

    /** Updates the emoji and name being displayed for the player */
    public void update(Player newPlayer) {
        emojiLabel.label.setText(newPlayer.emoji);
        nameLabel.label.setText(newPlayer.name);
    }
}