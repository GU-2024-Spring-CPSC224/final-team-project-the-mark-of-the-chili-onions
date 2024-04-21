package edu.gonzaga.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.viewHelpers.EventCallback;
import edu.gonzaga.Nuffatafl.viewHelpers.Theme;
import edu.gonzaga.Nuffatafl.viewHelpers.ThemeComponent;

import javax.swing.*;

/** A convenience view that makes creating a button that follows the theme colors easier */
public class ThemeToggleButton extends JPanel{
    /** The actual button that does the heavy lifting */
    public JButton button;

    /**
     * Create a blank button with a given
     * @param onClick lamda expression to be called when clicked
     */
    public ThemeToggleButton(EventCallback<JButton> onClick) {
        this(null, null, onClick);
    }

    /**
     * Create a button with a text label
     *
     * @param text    Text to display on button
     * @param onClick lamda expression to be called when clicked
     */
    public ThemeToggleButton(String text, EventCallback<JButton> onClick) {
        this(text, null, onClick);
    }

    /**
     * Create a button with an image icon
     *
     * @param icon    The image to display on the button
     * @param onClick lamda expression to be called when clicked
     */
    public ThemeToggleButton(ImageIcon icon, EventCallback<JButton> onClick) {
        this(null, icon, onClick);
    }

    /**
     * Create a button with a text label and an image icon
     *
     * @param text    Text to display on button
     * @param icon    The image to display on the button
     * @param onClick lamda expression to be called when clicked
     */
    public ThemeToggleButton(String text, ImageIcon icon, EventCallback<JButton> onClick) {
        super();
        button = new JButton();
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        if (text != null) { button.setText(text); }
        if (icon != null) { button.setIcon(icon); }
        button.addActionListener(event -> {
            onClick.action(button);
        });
        add(button);
        Theme.setForegroundFor(button, ThemeComponent.text);
        Theme.setBackgroundFor(this, ThemeComponent.background2);
    }
}