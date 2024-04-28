package edu.gonzaga.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.viewHelpers.Theme;
import edu.gonzaga.Nuffatafl.viewHelpers.ThemeComponent;

import javax.swing.*;

/**
 * A convenience view that makes creating a label that follows the theme colors easier
 * Foreground: ThemeComponent.text2
 * Background: ThemeComponent.background2
 */
public class ThemeLabel2 extends JPanel {
    /** The actual label that does the heavy lifting */
    public JLabel label;


    /**
     * Create a label with a text label
     *
     * @param text Text to display on label
     */
    public ThemeLabel2(String text) {
        this(text, null);
    }

    /**
     * Create a label with an image icon
     *
     * @param icon The image to display on the label
     */
    public ThemeLabel2(ImageIcon icon) {
        this(null, icon);
    }

    /**
     * Create a label with a text label and an image icon
     *
     * @param text Text to display on label
     * @param icon The image to display on the label
     */
    public ThemeLabel2(String text, ImageIcon icon) {
        super();
        label = new JLabel();
        label.setOpaque(false);
        if (text != null) {
            label.setText(text);
        }
        if (icon != null) {
            label.setIcon(icon);
        }
        add(label);
        Theme.setForegroundFor(label, ThemeComponent.text2);
        Theme.setBackgroundFor(this, ThemeComponent.background2);
    }
}
