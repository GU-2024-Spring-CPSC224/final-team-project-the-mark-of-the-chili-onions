package edu.gonzaga.Nuffatafl.views.Nuffatafl.viewHelpers;

import javax.swing.*;

/** Keeps track of a JComponent and a ThemeComponent for observation in Theme */
public class ThemeObserver {
    public JComponent jComponent;
    public ThemeComponent themeComponent;
    
    public ThemeObserver(JComponent jComponent, ThemeComponent themeComponent) {
        this.jComponent = jComponent;
        this.themeComponent = themeComponent;
    }
}
