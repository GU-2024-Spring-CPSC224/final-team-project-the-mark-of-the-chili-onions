package edu.gonzaga.Nuffatafl;

import edu.gonzaga.Nuffatafl.viewNavigation.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class StateChangeTest {
    @Test
    public void showWelcomeScreenTest() {
        StateController controller = new StateController();
        controller.showWelcomeScreen();
        Assertions.assertEquals(ProgramState.welcome, controller.getState());
    }

    @Test
    public void startGameTest() {
        StateController controller = new StateController();
        controller.startGame();
        Assertions.assertEquals(ProgramState.gameplay, controller.getState());
    }

    @Test
    public void endGameTest() {
        StateController controller = new StateController();
        controller.endGame();
        Assertions.assertEquals(ProgramState.afterGame, controller.getState());
    }

    @Test
    public void showRulesTest() {
        StateController controller = new StateController();
        controller.showRules();
        Assertions.assertEquals(ProgramState.rules, controller.getState());
    }

    @Test
    public void showSettingsTest() {
        StateController controller = new StateController();
        controller.showSettings();
        Assertions.assertEquals(ProgramState.settings, controller.getState());
    }

    @Test
    public void goToPreviousStateTestFromSettings() {
        StateController controller = new StateController();
        controller.showWelcomeScreen();
        controller.showSettings();
        controller.goToPreviousState();
        Assertions.assertEquals(ProgramState.welcome, controller.getState());
        Assertions.assertEquals(ProgramState.settings, controller.getPrevState());
    }

    @Test
    public void goToPreviousStateTestFromRules() {
        StateController controller = new StateController();
        controller.showWelcomeScreen();
        controller.showRules();
        controller.goToPreviousState();
        Assertions.assertEquals(ProgramState.welcome, controller.getState());
        Assertions.assertEquals(ProgramState.rules, controller.getPrevState());

    }
}