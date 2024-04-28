/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Orion Hess
 * @version v0.1.0 04/22/2024
 */

package edu.gonzaga.Nuffatafl.views;

import javax.swing.*;
import java.awt.*;

/**
 * Small Class to just draw a colored square
 */
public class ColorSquare extends JComponent {
    private Color color;
    private int squareSize = 20; // Default square size

    public ColorSquare(Color color) {
        this.color = color;
        setOpaque(true); // Make the component opaque to see the square
    }

    public ColorSquare(Color color, int squareSize) {
        this.color = color;
        this.squareSize = squareSize;
        setOpaque(true);
    }

    public void setSquareSize(int size) {
        this.squareSize = size;
    }

    public void setColor(Color color) {
        this.color = color;
        repaint();
    }

    /**
     * This is where the magic happens,
     * overriding the JComponent method paintComponent lets us paint whatever we want
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0, 0, squareSize, squareSize);
    }
}