/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo
 * @version v0.1.0 03/28/2024
 */

package edu.gonzaga.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.viewHelpers.Theme;
import edu.gonzaga.Nuffatafl.viewNavigation.Screen;
import edu.gonzaga.Nuffatafl.viewNavigation.StateController;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/** The main view for the program UI */
public class MainView extends JFrame {
    /**
     * Creates a 1000 x 1000 window titled "Nuffatafl" and makes it visible,
     * initializes sub-views and property change listening for StateController
     */
    public MainView() {
        super();
        stateController = new StateController();
        stateController.onScreenChange(event -> handleScreenChange(event));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Sets the size to the available screen size cause we're greedy and we want all them pixels
        setSize(Toolkit.getDefaultToolkit().getScreenSize());

        // Keeps the user from making this program too small - it's for their own good
        // I tested smaller sizes and this is the smallest size I would recommend (any smaller and things break)
        setMinimumSize(new Dimension(480, 480));

        setTitle("Nuffatafl");
        setVisible(true);

        welcomeScreen = new WelcomeScreen(this.stateController);
        gameplayScreen = new GameplayScreen(this.stateController);
        rulesScreen = new RulesScreen(this.stateController);
        settingsScreen = new SettingsScreen(this.stateController);

        setIconImage(new ImageIcon("src/main/resources/icon.png").getImage());
    }

    /** Shows the welcome screen */
    public void showGUI() {
        this.stateController.showWelcomeScreen();
    }

    /** Controls the state of which screen should be shown */
    private final StateController stateController;

    /** Subclassed JPanel that displays UI content for the welcome screen */
    private final WelcomeScreen welcomeScreen;

    /** Subclassed JPanel that displays UI content for the gameplay screen */
    private final GameplayScreen gameplayScreen;

    /** Subclassed JPanel that displays UI content for the rules screen */
    private final RulesScreen rulesScreen;

    /** Subclassed JPanel that displays UI content for the settings screen */
    private final SettingsScreen settingsScreen;

    /**
     * Handles displaying the correct view when the StateController state changes
     * @param event the object containing the old and new state
     */
    private void handleScreenChange(PropertyChangeEvent event) {
        //Get and typecast the new ProgramState from the event, get the matching screen, make visible
        Screen newState = (Screen) event.getNewValue();
        JPanel newScreen = screenForState(newState);
        showScreen(newScreen);

        //Get and typecast the old ProgramState from the event, get the matching screen and hide if
        //not null and not none
        Screen oldState = (Screen) event.getOldValue();
        if (oldState != null && oldState != Screen.none) {
            JPanel oldScreen = screenForState(oldState);
            hideScreen(oldScreen);
        }
    }

    /**
     * Gets the screen that should be displayed for a given ProgramState
     * @param state the program state to find a corresponding screen for
     * @return Screen that corresponds to the state
     */
    private JPanel screenForState(Screen state) {
        return switch (state) {
            case gameplay   -> gameplayScreen;
            case rules      -> rulesScreen;
            case settings   -> settingsScreen;
            default         -> welcomeScreen;
        };
    }

    /**
     * Displays the given JPanel in the main view
     * @param screen the JPanel to show
     */
    private void showScreen(JPanel screen) {
        this.add(screen);
        screen.setVisible(true);
    }

    /**
     * Hides the given JPanel from the main view
     * @param screen the JPanel to hide
     */
    private void hideScreen(JPanel screen) {
        screen.setVisible(false);
    }
}