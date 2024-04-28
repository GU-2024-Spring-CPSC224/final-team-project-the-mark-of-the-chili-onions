/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo
 * @version v1.0.0 04/28/2024
 */

package edu.gonzaga.Nuffatafl.viewHelpers;

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
