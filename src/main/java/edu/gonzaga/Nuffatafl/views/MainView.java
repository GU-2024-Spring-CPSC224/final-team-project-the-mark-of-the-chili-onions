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

import edu.gonzaga.Nuffatafl.viewNavigation.ProgramState;
import edu.gonzaga.Nuffatafl.viewNavigation.StateController;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

/** The main view for the program UI */
public class MainView extends JFrame {
    /** Creates a 1000 x 1000 window titled "Nuffatafl" but does not yet make it visible */
    public MainView(StateController stateController) {
        super();
        this.stateController = stateController;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setTitle("Nuffatafl");
        this.setVisible(true);

        this.welcomeScreen = new WelcomeScreen(this.stateController);
        this.gameplayScreen = new GameplayScreen(this.stateController);
        this.afterGameScreen = new AfterGameScreen(this.stateController);
        this.rulesScreen = new RulesScreen(this.stateController);
        this.settingsScreen = new SettingsScreen(this.stateController);

        this.stateController.onScreenChange(event -> handleScreenChange(event));
    }

    private void handleScreenChange(PropertyChangeEvent event) {
        ProgramState newState = (ProgramState) event.getNewValue();
        JPanel newScreen = screenForState(newState);
        showScreen(newScreen);

        ProgramState oldState = (ProgramState) event.getOldValue();
        if (oldState != null && oldState != ProgramState.none) {
            JPanel oldScreen = screenForState(oldState);
            hideScreen(oldScreen);
        }
    }

    private JPanel screenForState(ProgramState state) {
        return switch (state) {
            case gameplay -> this.gameplayScreen;
            case afterGame -> this.afterGameScreen;
            case rules -> this.rulesScreen;
            case settings -> this.settingsScreen;
            default -> this.welcomeScreen;
        };
    }

    private void showScreen(JPanel screen) {
        this.add(screen);
        screen.setVisible(true);
    }

    private void hideScreen(JPanel screen) {
        screen.setVisible(false);
    }

    private final StateController stateController;

    /** Subclassed JPanel that displays UI content for the welcome screen */
    private final WelcomeScreen welcomeScreen;

    /** Subclassed JPanel that displays UI content for the gameplay screen */
    private final GameplayScreen gameplayScreen;

    /** Subclassed JPanel that displays UI content for the after game screen */
    private final AfterGameScreen afterGameScreen;

    /** Subclassed JPanel that displays UI content for the rules screen */
    private final RulesScreen rulesScreen;

    /** Subclassed JPanel that displays UI content for the settings screen */
    private final SettingsScreen settingsScreen;
}