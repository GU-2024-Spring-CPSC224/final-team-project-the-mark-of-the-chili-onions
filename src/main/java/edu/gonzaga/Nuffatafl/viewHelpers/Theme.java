package edu.gonzaga.Nuffatafl.viewHelpers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.JComponent;

public class Theme {
    private String name;
    private ThemeColorKey colorKey;
    
    public Theme(String name, ThemeColorKey colorKey) {
        this.name = name;
        this.colorKey = colorKey;
        Theme.themes.add(this);
    }
    
    public String getName() {
        return name;
    }
    
    public Color colorForKey(ThemeComponent themeKey) {
        return colorKey.colorForKey(themeKey);
    }
    
    public static ArrayList<Theme> themes = new ArrayList<Theme>();
        
    public static Theme defaultTheme = new Theme("Default", themeKey -> {
        return switch (themeKey) {
            case accent         -> new Color(104, 204, 227);
            case accent2        -> new Color(255, 240, 145);
            case background     -> Color.white;
            case background2    -> new Color(240, 240, 240);
            case chekcerOff     -> Color.white;
            case checkerOn      -> new Color(220, 220, 220);
            case text           -> Color.black;
            case text2          -> new Color(61, 68, 69);
        };
    });
    
    public static Theme midnight = new Theme("Midnight", themeKey -> {
        return switch (themeKey) {
            case accent -> new Color(214, 154, 191);
            case accent2 -> new Color(108, 171, 128);
            case background -> new Color(29, 33, 41);
            case background2 -> new Color(45, 55, 69);
            case chekcerOff -> new Color(29, 33, 41);
            case checkerOn -> new Color(17, 22, 33);
            case text -> Color.white;
            case text2 -> new Color(196, 209, 204);
        };
    });
        
    public static Theme from(String name) {
        for (Theme theme : themes) {
            if (theme.name == name) {
                return theme;
            }
        }

        return Theme.defaultTheme;
    }
    
    private static Observable<Theme> current = new Observable<Theme>(defaultTheme);
    private static Preferences preferences = Preferences.userNodeForPackage(Theme.class);
    private static String savedThemeKey = "savedTheme";
    private static ArrayList<ThemeObserver> backgroundColorObservers = new ArrayList<ThemeObserver>();
    private static ArrayList<ThemeObserver> foregroundColorObservers = new ArrayList<ThemeObserver>();
    
    public static void setTheme(Theme theme) {
        if (theme != null) {current.set(theme); }
    }

    public static void setBackgroundFor(JComponent component, ThemeComponent themeKey) {
        component.setBackground(current.getValue().colorForKey(themeKey));
        backgroundColorObservers.removeIf(observer -> observer.jComponent == component);
        backgroundColorObservers.add(new ThemeObserver(component, themeKey));
    }
    
    public static void setForegroundFor(JComponent component, ThemeComponent themeKey) {
        component.setForeground(current.getValue().colorForKey(themeKey));
        foregroundColorObservers.removeIf(observer -> observer.jComponent == component);
        foregroundColorObservers.add(new ThemeObserver(component, themeKey));
    }
    
    public static void loadSavedThemePreference() {
        String savedThemeString = preferences.get(savedThemeKey, defaultTheme.name);
        Theme savedTheme = Theme.from(savedThemeString);
        
        if (savedTheme != null) { current.set(savedTheme); }
        
        current.onChange(newTheme -> {
            preferences.put(savedThemeKey, newTheme.name);
            
            backgroundColorObservers.forEach(observer -> {
                observer.jComponent.setBackground(current.getValue().colorForKey(observer.themeComponent));
            });
            
            foregroundColorObservers.forEach(observer -> {
                observer.jComponent.setForeground(current.getValue().colorForKey(observer.themeComponent));
            });
        });
    }
}
