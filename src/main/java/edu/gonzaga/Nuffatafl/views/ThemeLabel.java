package edu.gonzaga.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.viewHelpers.Theme;
import edu.gonzaga.Nuffatafl.viewHelpers.ThemeComponent;

import javax.swing.*;

/** A convenience view that makes creating a label that follows the theme colors easier */
public class ThemeLabel extends JPanel {
    /** The actual label that does the heavy lifting */
    public JLabel label;


    /**
     * Create a label with a text label
     *
     * @param text    Text to display on label
     */
    public ThemeLabel(String text) {
        this(text, null);
    }

    /**
     * Create a label with an image icon
     *
     * @param icon    The image to display on the label
     */
    public ThemeLabel(ImageIcon icon) {
        this(null, icon);
    }

    /**
     * Create a label with a text label and an image icon
     *
     * @param text    Text to display on label
     * @param icon    The image to display on the label
     */
    public ThemeLabel(String text, ImageIcon icon) {
        super();
        label = new JLabel();
        label.setOpaque(false);
        if (text != null) { label.setText(text); }
        if (icon != null) { label.setIcon(icon); }
        add(label);
        Theme.setForegroundFor(label, ThemeComponent.text);
        Theme.setBackgroundFor(this, ThemeComponent.background);
    }
}
