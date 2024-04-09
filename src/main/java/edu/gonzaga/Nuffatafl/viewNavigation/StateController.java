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
        this.state = ProgramState.welcome;
        this.prevState = null;
        this.changeSupport = new PropertyChangeSupport(this.state);
        this.mainView = new MainView(this);
        this.gameManager = new GameManager();
    }

    private ProgramState state;
    private ProgramState prevState;
    private MainView mainView;
    private GameManager gameManager;
    private PropertyChangeSupport changeSupport;

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
        if (this.prevState != null) {
            this.changeState(this.prevState);
        } else {
            this.changeState(ProgramState.welcome);
        }
    }

    public void endProgram() {
        System.exit(0);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.changeSupport.removePropertyChangeListener(listener);
    }

    private void changeState(ProgramState newState) {
        ProgramState oldState = this.prevState;
        this.prevState = this.state;
        this.state = newState;
        this.changeSupport.firePropertyChange("state", oldState, newState);
    }
}