package edu.gonzaga.Nuffatafl.viewHelpers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.JComponent;

/** Keeps track of colors in a theme and handles theme changes for you so colors will update when the theme is changed */
public class Theme {
    /** Name of the theme to display to the user */
    private String name;
    
    /** The lamda expression that returns the right color for each ThemeComponent in this theme */
    private ThemeColorKey colorKey;

    /** The available themes to choose from */
    public static ArrayList<Theme> themes = new ArrayList<Theme>();

    /** 
     * Creates a theme with a name and a lamda expression that returns the right color for each ThemeComponent.
     * Adds the theme to Theme.themes
     */
    public Theme(String name, ThemeColorKey colorKey) {
        this.name = name;
        this.colorKey = colorKey;
        Theme.themes.add(this);
    }

    /** Returns the name of the theme to display to the user */
    public String getName() {
        return name;
    }

    /** Returns the color this theme specifies for the input ThemeComponent */
    public Color colorForKey(ThemeComponent themeKey) {
        return colorKey.colorForKey(themeKey);
    }


    /** Default theme (light) */
    public static Theme defaultTheme = new Theme("Default", themeKey -> {
        return switch (themeKey) {
            case accent         -> new Color(104, 204, 227);
            case accent2        -> new Color(255, 240, 145);
            case accent3        -> new Color(225, 210, 115);
            case background     -> Color.white;
            case background2    -> new Color(240, 240, 240);
            case checkerOff     -> Color.white;
            case checkerOn      -> new Color(220, 220, 220);
            case text           -> Color.black;
            case text2          -> new Color(61, 68, 69);
        };
    });

    /** Midnight theme (dark) */
    public static Theme midnightTheme = new Theme("Midnight", themeKey -> {
        return switch (themeKey) {
            case accent         -> new Color(214, 154, 191);
            case accent2        -> new Color(108, 171, 128);
            case accent3        -> new Color(74, 128, 109);
            case background     -> new Color(29, 33, 41);
            case background2    -> new Color(45, 55, 69);
            case checkerOff     -> new Color(29, 33, 41);
            case checkerOn      -> new Color(17, 22, 33);
            case text           -> Color.white;
            case text2          -> new Color(196, 209, 204);
        };
    });

    public static Theme sillyGooseTheme = new Theme(" Silly Goose", themeKey -> {
        return switch (themeKey) {
            case accent         -> new Color(60, 147, 92);
            case accent2        -> new Color(242, 171, 55);
            case accent3        -> new Color(205, 95, 42);
            case background     -> new Color(250, 245, 216);
            case background2    -> new Color(216, 174, 139);
            case checkerOn     -> new Color(216, 174, 139);
            case checkerOff      -> new Color(250, 245, 216);
            case text           -> new Color(28, 37, 54);
            case text2          -> new Color(32, 39, 45);
        };
    });
        
    /** Gets a theme from its name */
    public static Theme from(String name) {
        for (Theme theme : themes) {
            if (Objects.equals(theme.name, name)) {
                return theme;
            }
        }
        System.out.println("Theme not found with name \"" + name + "\", using default theme");
        return Theme.defaultTheme;
    }
    
    /** Keeps track of the current theme and notifies observers when it changes */
    private static Theme current = defaultTheme;
    
    /** Observers to change the background color of when the theme changes */
    private static ArrayList<ThemeObserver> backgroundColorObservers = new ArrayList<ThemeObserver>();
    
    /** Observers to change the foreground color of when the theme changes */
    private static ArrayList<ThemeObserver> foregroundColorObservers = new ArrayList<ThemeObserver>();
    
    /** Set the current theme to be a new theme */
    public static void setTheme(Theme theme) {
        if (theme == null) {
            System.out.println("Warning: Cannot set theme to a null value");
            return;
        }

        current = theme;

        backgroundColorObservers.forEach(observer -> {
            observer.jComponent.setBackground(current.colorForKey(observer.themeComponent));
        });

        foregroundColorObservers.forEach(observer -> {
            observer.jComponent.setForeground(current.colorForKey(observer.themeComponent));
        });

        themeChangeObservers.forEach(observer -> {
            observer.action(current);
        });
    }

    public static Theme getCurrentTheme() {
        return current;
    }

    /**
     * Sets the background color for a JComponent and updates it when the theme changes
     * @param component The JComponent (JPanel, JButton, etc.) to set and change the background color of
     * @param themeKey Specifies which color to set for each theme (background, text, etc.)
     */
    public static void setBackgroundFor(JComponent component, ThemeComponent themeKey) {
        // Set the background color
        component.setBackground(current.colorForKey(themeKey));
        // Remove any previous observer for the same JComponent
        backgroundColorObservers.removeIf(observer -> observer.jComponent == component);
        // Set up a new observer
        backgroundColorObservers.add(new ThemeObserver(component, themeKey));
    }
    
    /**
     * Sets the foreground color for a JComponent and updates it when the theme
     * changes
     * 
     * @param component The JComponent (JPanel, JButton, etc.) to set and change the
     *                  foreground color of
     * @param themeKey  Specifies which color to set for each theme (foreground,
     *                  text, etc.)
     */
    public static void setForegroundFor(JComponent component, ThemeComponent themeKey) {
        // Set the foreground color
        component.setForeground(current.colorForKey(themeKey));
        // Remove any previous observer for the same JComponent
        foregroundColorObservers.removeIf(observer -> observer.jComponent == component);
        // Set up a new observer
        foregroundColorObservers.add(new ThemeObserver(component, themeKey));
    }

    /** Observers for when the current theme is changed */
    private static final ArrayList<EventCallback<Theme>> themeChangeObservers = new ArrayList<>();

    /** Adds an observer to be called when the current theme is changed */
    public static void onChange(EventCallback<Theme> observer) {
        themeChangeObservers.add(observer);
    }

    /** Padding to use for empty borders when you want more space */
    public static final int PADDING_L = 13;

    /** Padding to use for empty borders when you want the standard amount of space */
    public static final int PADDING_M = 10;

    /** Padding to use for empty borders when you want less space */
    public static final int PADDING_S = 8;

    /** The default size for icons */
    public static final int ICON_SIZE = 20;
}
