/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo
 * @version v0.1.0 04/06/2024
 */

package edu.gonzaga.Nuffatafl.viewNavigation;

import edu.gonzaga.Nuffatafl.backend.GameManager;
import edu.gonzaga.Nuffatafl.viewHelpers.Theme;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Manages the state of the overall program (which view is displayed),
 * Contains a MainView and GameManager to keep all relevant objects in one place
 */
public class StateController {
    /** Initializes with screen state of none, creates MainView and PropertyManager, sets up property change support */
    public StateController() {
        super();
        this.screen = Screen.none;
        this.previousScreen = Screen.none;
        this.screenChangeManager = new PropertyChangeSupport(this.screen);
        this.focusModeObservable = new PropertyChangeSupport(this.screen);
        this.highlightingModeObservable = new PropertyChangeSupport(this.screen);
        this.gameManager = new GameManager();

        this.settings = new Properties();
        loadProperties();
        Theme.setTheme(Theme.from(getProperty("theme", "Default")));
        setFocusMode(Boolean.parseBoolean(getProperty("focusMode", "false")));
        isAutoFocusModeEnabled = !focusMode; // Autofocus should be off at the start if focus mode is already selected, and on if it's not
    }

    /** The GameManager that handles game logic, included here to keep everything together */
    public GameManager gameManager;

    /** Current screen the program's UI should show */
    private Screen screen;

    /** Previous screen that was displayed, used to go back to previous screen */
    private Screen previousScreen;

    /** Handles updating observers when new value change is published for screen */
    private final PropertyChangeSupport screenChangeManager;

    /** Changes program state to cause UI to show welcome screen */
    public void showWelcomeScreen() {
        this.changeState(Screen.welcome);
    }

    /** Changes program state to cause UI to show gameplay screen */
    public void startGame() {
        this.gameManager.reset();
        this.changeState(Screen.gameplay);
    }

    /** Changes program state to cause UI to show afterGame screen */
    public void endGame() {
        this.changeState(Screen.afterGame);
    }

    /** Changes program state to cause UI to show rules screen */
    public void showRules() {
        this.changeState(Screen.rules);
    }

    /** Changes program state to cause UI to show settings screen */
    public void showSettings() {
        this.changeState(Screen.settings);
    }

    /** Changes program state to cause UI to show previous screen */
    public void goToPreviousScreen() {
        if (this.previousScreen != null) {
            this.changeState(this.previousScreen);
        } else {
            this.changeState(Screen.welcome);
        }
    }

    /** Exits program (unnecessary but good to have if we want to add things to do on exit later) */
    public void endProgram() {
        System.exit(0);
    }

    /**
     * Adds an observer to be notified when the current screen is changed
     * @param listener Code to execute when current screen changes
     */
    public void onScreenChange(PropertyChangeListener listener) {
        this.screenChangeManager.addPropertyChangeListener(listener);
    }

    /** Gets the Screen representing the current screen that should be displayed */
    public Screen getCurrentScreen() {
        return this.screen;
    }

    /** Gets the Screen representing the screen that was previously displayed */
    public Screen getPreviousScreen() {
        return this.previousScreen;
    }

    /**
     * Changes the currently displayed screen and publishes the change to observers
     * @param newScreen the new screen to display
     */
    private void changeState(Screen newScreen) {
        this.previousScreen = this.screen;
        this.screen = newScreen;
        this.screenChangeManager.firePropertyChange("screen", this.previousScreen, this.screen);
    }


    /** Boolean for storing focus mode state */
    private boolean focusMode;

    /** Handles updating observers when new value change is published for focus mode */
    private final PropertyChangeSupport focusModeObservable;

    /**
     * Adds an observer to be notified when focus mode is changed
     * @param listener Code to execute when focus mode changes
     */
    public void addFocusModeListener(PropertyChangeListener listener) {
        this.focusModeObservable.addPropertyChangeListener(listener);
    }

    /**
     * Changes the current focus mode and publishes the change to observers
     * @param focusMode the new focus mode boolean
     */
    public void setFocusMode(boolean focusMode) {
        boolean previousMode = this.focusMode;
        this.focusMode = focusMode;
        this.focusModeObservable.firePropertyChange("focusMode", previousMode, this.focusMode);
    }

    /** True if focus mdoe is enabled, false otherwise */
    public boolean getFocusMode() {
        return focusMode;
    }

    /** Allows game to turn on/off focus mode based on size, set to false if user changes focus mode manually */
    public boolean isAutoFocusModeEnabled = true;

    /** Boolean for storing highlighting mode state */
    private boolean highlightingMode = true;

    /** Handles updating observers when new value change is published for highlighting mode */
    private final PropertyChangeSupport highlightingModeObservable;

    /**
     * Adds an observer to be notified when highlighting mode is changed
     * @param listener Code to execute when highlighting mode changes
     */
    public void addHighlightingModeListener(PropertyChangeListener listener) {
        this.highlightingModeObservable.addPropertyChangeListener(listener);
    }

    /**
     * Changes the current highlighting mode and publishes the change to observers
     * @param highlightingMode the new highlighting mode boolean
     */
    public void setHighlightingMode(boolean highlightingMode) {
        boolean previousMode = this.highlightingMode;
        this.highlightingMode = highlightingMode;
        this.highlightingModeObservable.firePropertyChange("highlightingMode", previousMode, this.highlightingMode);
    }

    public boolean getHighlightingMode() {
        return highlightingMode;
    }

    /** Properties object for setting and getting settings from a file */
    private Properties settings;

    public String getProperty(String key) {
        return this.settings.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return this.settings.getProperty(key, defaultValue);
    }

    public void setProperty(String key, String value) {
        this.settings.setProperty(key, value);
        storeProperties();
    }

    private void storeProperties() {
        try {
            this.settings.storeToXML(new FileOutputStream("settings.xml"), "");
        } catch (IOException e) {
            System.out.println("Failed to save settings to file");
            throw new RuntimeException(e);
        }
    }

    private void loadProperties() {
        try {
            this.settings.loadFromXML(new FileInputStream("settings.xml"));
        } catch (IOException e) {
            System.out.println("Failed to load settings from settings.xml");
            System.out.println("Likely, the file does not exist yet. It will be created when you set a setting");
            System.out.println(e);
        }
    }
}