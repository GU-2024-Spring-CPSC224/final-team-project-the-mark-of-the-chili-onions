package edu.gonzaga.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.backend.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PlayerLabel extends JPanel {
    public JLabel emojiLabel;
    public JLabel nameLabel;

    public PlayerLabel(Player player) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        emojiLabel = new JLabel(player.emoji);
        emojiLabel.setBorder(new EmptyBorder(13, 13, 13, 13));
        add(emojiLabel);
        nameLabel = new JLabel(player.name);
        nameLabel.setBorder(new EmptyBorder(13, 0, 13, 13));
        add(nameLabel);
    }

    public void update(Player newPlayer) {
        emojiLabel.setText(newPlayer.emoji);
        nameLabel.setText(newPlayer.name);
    }
}