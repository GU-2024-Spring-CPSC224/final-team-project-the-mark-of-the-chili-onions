package edu.gonzaga.Nuffatafl.views.Nuffatafl.viewHelpers;

import java.awt.*;

/** 
 * An interface for a lamda expression that takes a ThemeComponent and 
 * returns the Color that matches that ThemeComponent for a theme 
 */
public interface ThemeColorKey {
    public Color colorForKey(ThemeComponent key);
}
