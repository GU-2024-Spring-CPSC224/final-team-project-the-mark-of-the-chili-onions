package edu.gonzaga.Nuffatafl.viewHelpers;

import javax.swing.JComponent;

/** Keeps track of a JComponent and a ThemeComponent for observation in Theme */
public class ThemeObserver {
    public JComponent jComponent;
    public ThemeComponent themeComponent;
    
    public ThemeObserver(JComponent jComponent, ThemeComponent themeComponent) {
        this.jComponent = jComponent;
        this.themeComponent = themeComponent;
    }
}
