package edu.gonzaga.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.backend.Player;
import edu.gonzaga.Nuffatafl.viewHelpers.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/** Displays a player's emoji and name */
public class PlayerLabel extends JPanel {
    public ThemeLabel emojiLabel;
    public ThemeLabel nameLabel;

    /** @param padding indicates if label should have padding or not */
    public PlayerLabel(Player player, boolean padding) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        emojiLabel = new ThemeLabel(player.emoji);
        add(emojiLabel);
        nameLabel = new ThemeLabel(player.name);
        add(nameLabel);

        int pad = padding ? Theme.PADDING_M : 0;
        emojiLabel.setBorder(new EmptyBorder(pad, pad, pad, pad));
        nameLabel.setBorder(new EmptyBorder(pad, 0, pad, pad));
    }

    /** Updates the emoji and name being displayed for the player */
    public void update(Player newPlayer) {
        emojiLabel.label.setText(newPlayer.emoji);
        nameLabel.label.setText(newPlayer.name);
    }
}