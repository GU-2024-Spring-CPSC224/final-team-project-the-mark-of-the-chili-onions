package edu.gonzaga.Nuffatafl.views;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import edu.gonzaga.Nuffatafl.viewHelpers.EventCallback;
import edu.gonzaga.Nuffatafl.viewHelpers.Theme;
import edu.gonzaga.Nuffatafl.viewHelpers.ThemeComponent;

public class ThemeButton extends JPanel implements MouseListener {
    public ThemeButton(EventCallback<JLabel> onClick) {
        this(new JLabel(), onClick);
    }
    
    public ThemeButton(String text, EventCallback<JLabel> onClick) {
        this(new JLabel(text), onClick);
    }
    
    public ThemeButton(ImageIcon icon, EventCallback<JLabel> onClick) {
        this(new JLabel(icon), onClick);
    }
    
    public ThemeButton(String text, ImageIcon icon, EventCallback<JLabel> onClick) {
        super();
        label = new JLabel();
        label.setText(text);
        label.setIcon(icon);
        this.onClick = onClick;
        add(label);
        addMouseListener(this);
        label.addMouseListener(this);
        Theme.setForegroundFor(this, ThemeComponent.text);
        Theme.setBackgroundFor(this, ThemeComponent.background2);
    }
    
    public ThemeButton(JLabel label, EventCallback<JLabel> onClick) {
        super();
        this.label = label;
        this.onClick = onClick;
        add(label);
        addMouseListener(this);
        label.addMouseListener(this);
        Theme.setForegroundFor(this, ThemeComponent.text);
        Theme.setBackgroundFor(this, ThemeComponent.background2);
    }
    
    public JLabel label;
    public EventCallback<JLabel> onClick;
    
    @Override
    public void mouseClicked(MouseEvent e) {
        onClick.action(label);
    }

    // Unused
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
