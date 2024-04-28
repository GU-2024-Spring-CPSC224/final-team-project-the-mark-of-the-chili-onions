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

import java.awt.*;

/**
 * An interface for a lambda expression that takes a ThemeComponent and
 * returns the Color that matches that ThemeComponent for a theme
 */
public interface ThemeColorKey {
    Color colorForKey(ThemeComponent key);
}
