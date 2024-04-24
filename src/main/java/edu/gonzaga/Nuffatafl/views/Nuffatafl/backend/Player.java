package edu.gonzaga.Nuffatafl.views.Nuffatafl.backend;

import javax.swing.*;
import java.awt.*;

public class Player {
    public Player(String name, String emoji, Color color, Team team) {
        this.name = name;
        this.emoji = emoji;
        this.color = color;
        this.team = team;
    }

    public String name;
    public String emoji;
    public Color color;
    public Team team;

    public JPanel label() {
        JPanel label = new JPanel();
        label.setLayout(new BoxLayout(label, BoxLayout.X_AXIS));
        label.add(new JLabel(emoji));
        label.add(new JLabel(name));
        return label;
    }
}