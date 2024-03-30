/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo
 * @version v0.1.0 03/28/2024
 */

package edu.gonzaga.viewNavigation;

import edu.gonzaga.views.*;

import javax.swing.*;

/** Controls the main view for program's UI and handles changing which view is displayed */
public class ViewController extends JFrame {
    /**
     * Singleton instance of ViewController to make it easier for ActionListeners to change views
     * without having to pass an instance of the class throughout every GUI element of the program
     */
    public static ViewController main = new ViewController();

    /** Creates a 1000 x 1000 window titled "Nuffatafl" but does not yet make it visible */
    private ViewController() {
        super();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setTitle("Nuffatafl");
        this.setVisible(false);
    }

    /** Makes the window visible and displays the welcome screen. Call this function to start the game */
    public void startGame() {
        this.showScreen(this.welcomeScreen);
        this.setVisible(true);
    }

    /** Subclassed JPanel that displays UI content for the welcome screen */
    private final WelcomeScreen welcomeScreen = new WelcomeScreen();

    /** Subclassed JPanel that displays UI content for the gameplay screen */
    private final GameplayScreen gameplayScreen = new GameplayScreen();

    /** Subclassed JPanel that displays UI content for the after game screen */
    private final AfterGameScreen afterGameScreen = new AfterGameScreen();

    /** Subclassed JPanel that displays UI content for the rules screen */
    private final RulesScreen rulesScreen = new RulesScreen();

    /** Subclassed JPanel that displays UI content for the settings screen */
    private final SettingsScreen settingsScreen = new SettingsScreen();

    /**
     * The JPanel that was previously displayed.
     * Stored to allow the user to go back a screen.
     * Set to null by default.
     */
    private JPanel previousScreen = null;

    /**
     * The JPanel that is currently being displayed.
     * Stored to keep track of which screen will become the previous screen when the screen is next changed.
     * Set to null by default.
     */
    private JPanel currentScreen = null;

    /**
     * Changes the screen currently being displayed
     * @param action The UIActionType describing which screen should be displayed next
     */
    public void changeScreen(UIActionType action) {
        switch (action) {
            case showWelcomeScreen:     this.showScreen(this.welcomeScreen); break;
            case showGameplayScreen:    this.showScreen(this.gameplayScreen); break;
            case showAfterGameScreen:   this.showScreen(this.afterGameScreen); break;
            case showRulesScreen:       this.showScreen(this.rulesScreen); break;
            case showSettingsScreen:    this.showScreen(this.settingsScreen); break;
            case goToPreviousScreen:    this.goToPreviousView(); break;
        }
    }

    /**
     * Handles screen changes to a new screen
     * @param screen The JPanel to show next
     */
    private void showScreen(JPanel screen) {
        //Sets the previous screen to the current screen to keep track of which screen was previously displayed
        this.previousScreen = this.currentScreen;

        //If there is a previous screen, make it no longer visible
        if (this.previousScreen != null) {
            this.previousScreen.setVisible(false);
        }

        //Set the currrent screen to the screen that will be displayed next to keep track of which screen is displayed
        this.currentScreen = screen;

        //Make the new screen visible
        this.currentScreen.setVisible(true);

        //Add the new screen to the JFrame
        this.add(screen);
    }

    /**
     * Shows the previously displayed screen.
     * If the previously displayed screen is null, displays the game play screen by default.
     */
    private void goToPreviousView() {
        if (this.previousScreen != null) {
            this.showScreen(this.previousScreen);
        } else {
            this.showScreen(this.gameplayScreen);
        }
    }
}