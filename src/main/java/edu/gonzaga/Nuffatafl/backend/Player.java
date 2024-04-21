package edu.gonzaga.Nuffatafl.backend;

import javax.swing.*;
import java.awt.*;

public class Player {
    public String name;
    public Character emoji;
    public Color color;
    public Team team;

    public JPanel image() {
        JLabel label = new JLabel(String.valueOf(emoji));
        label.setBackground(color);

        JPanel circle = new JPanel() {
            public void paintComponent(Graphics2D g) {
                super.paintComponent(g);
                int size = Math.min(getWidth(), getHeight());
                g.drawOval(getWidth() / 2, getHeight() / 2, size, size);
            }
        };

        circle.setSize(50, 50);
        circle.add(label);
        return circle;
    }

    public JPanel label() {
        JPanel label = new JPanel();
        label.setLayout(new BoxLayout(label, BoxLayout.X_AXIS));
        label.add(image());
        label.add(new JLabel(name));
        return label;
    }
}