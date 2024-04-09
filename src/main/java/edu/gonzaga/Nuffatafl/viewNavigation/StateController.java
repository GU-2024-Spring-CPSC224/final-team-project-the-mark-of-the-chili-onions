/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo
 * @version v0.1.0 03/28/2024
 */

package edu.gonzaga.Nuffatafl.viewNavigation;

import edu.gonzaga.Nuffatafl.backend.GameManager;
import edu.gonzaga.Nuffatafl.views.MainView;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class StateController {
    public StateController() {
        super();
        this.state = ProgramState.none;
        this.prevState = ProgramState.none;
        this.screenChangeManager = new PropertyChangeSupport(this.state);
        this.mainView = new MainView(this);
        this.gameManager = new GameManager();
    }

    public GameManager gameManager;

    private ProgramState state;
    private ProgramState prevState;
    private final MainView mainView;
    private final PropertyChangeSupport screenChangeManager;

    public void showWelcomeScreen() {
        this.changeState(ProgramState.welcome);
    }

    public void startGame() {
        this.changeState(ProgramState.gameplay);
    }

    public void endGame() {
        this.changeState(ProgramState.afterGame);
    }

    public void showRules() {
        this.changeState(ProgramState.rules);
    }

    public void showSettings() {
        this.changeState(ProgramState.settings);
    }

    public void goToPreviousState() {
        System.out.println("GO TO PREV: " + this.prevState + ", " + this.state);
        if (this.prevState != null) {
            System.out.println("PREV NOT NULL");
            this.changeState(this.prevState);
        } else {
            System.out.println("PREV NULL!");
            this.changeState(ProgramState.welcome);
        }
    }

    public void endProgram() {
        System.exit(0);
    }

    public void onScreenChange(PropertyChangeListener listener) {
        this.screenChangeManager.addPropertyChangeListener(listener);
    }

    public ProgramState getState() {
        return this.state;
    }

    public ProgramState getPrevState() {
        return this.prevState;
    }

    private void changeState(ProgramState newState) {
        this.prevState = this.state;
        this.state = newState;
        System.out.println("CHANGE STATE: NEW: " + this.state + ", OLD: " + this.prevState);
        this.screenChangeManager.firePropertyChange("state", this.prevState, this.state);
    }
}