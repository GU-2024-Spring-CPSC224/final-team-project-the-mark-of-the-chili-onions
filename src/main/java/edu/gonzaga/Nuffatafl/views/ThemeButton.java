package edu.gonzaga.Nuffatafl.views;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import edu.gonzaga.Nuffatafl.viewHelpers.EventCallback;
import edu.gonzaga.Nuffatafl.viewHelpers.Theme;
import edu.gonzaga.Nuffatafl.viewHelpers.ThemeComponent;

public class ThemeButton extends JPanel {
    public ThemeButton(EventCallback<JButton> onClick) {
        this(null, null, onClick);
    }
    
    public ThemeButton(String text, EventCallback<JButton> onClick) {
        this(text, null, onClick);
    }
    
    public ThemeButton(ImageIcon icon, EventCallback<JButton> onClick) {
        this(null, icon, onClick);
    }
    
    public ThemeButton(String text, ImageIcon icon, EventCallback<JButton> onClick) {
        super();
        button = new JButton();
        button.setOpaque(false);
        button.setBorderPainted(false);
        if (text != null) { button.setText(text); }
        if (icon != null) { button.setIcon(icon); }
        button.addActionListener(event -> {
            onClick.action(button);
        });
        add(button);
        Theme.setForegroundFor(this, ThemeComponent.text);
        Theme.setBackgroundFor(this, ThemeComponent.background2);
    }
        
    public JButton button;
    public EventCallback<JButton> onClick;
}
