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

        this.stateController.addPropertyChangeListener(event -> {
            ProgramState state = (ProgramState) event.getNewValue();
            System.out.println("STATE: " + state);
            showViewForState(state);
        });
    }

    public void showViewForState(ProgramState state) {
        System.out.println("SHOW STATE: " + state);
        switch (state) {
            case welcome    -> this.add(this.welcomeScreen);
            case gameplay   -> this.add(this.gameplayScreen);
            case afterGame  -> this.add(this.afterGameScreen);
            case rules      -> this.add(this.rulesScreen);
            case settings   -> this.add(this.settingsScreen);
        }
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