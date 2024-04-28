package edu.gonzaga.Nuffatafl.viewHelpers;

import java.awt.*;

/**
 * An interface for a lamda expression that takes a ThemeComponent and
 * returns the Color that matches that ThemeComponent for a theme
 */
public interface ThemeColorKey {
    Color colorForKey(ThemeComponent key);
}
