/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo
 * @version v0.1.0 04/08/2024
 */

package edu.gonzaga.Nuffatafl;

import edu.gonzaga.Nuffatafl.viewNavigation.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class StateControllerTest {
    @Test
    public void showWelcomeScreenTest() {
        StateController controller = new StateController();
        controller.showWelcomeScreen();
        Assertions.assertEquals(Screen.welcome, controller.getCurrentScreen());
    }

    @Test
    public void startGameTest() {
        StateController controller = new StateController();
        controller.startGame();
        Assertions.assertEquals(Screen.gameplay, controller.getCurrentScreen());
    }

    @Test
    public void showRulesTest() {
        StateController controller = new StateController();
        controller.showRules();
        Assertions.assertEquals(Screen.rules, controller.getCurrentScreen());
    }

    @Test
    public void showSettingsTest() {
        StateController controller = new StateController();
        controller.showSettings();
        Assertions.assertEquals(Screen.settings, controller.getCurrentScreen());
    }

    @Test
    public void goToPreviousStateTestFromSettings() {
        StateController controller = new StateController();
        controller.showWelcomeScreen();
        controller.showSettings();
        controller.goToPreviousScreen();
        Assertions.assertEquals(Screen.welcome, controller.getCurrentScreen());
        Assertions.assertEquals(Screen.settings, controller.getPreviousScreen());
    }

    @Test
    public void goToPreviousStateTestFromRules() {
        StateController controller = new StateController();
        controller.showWelcomeScreen();
        controller.showRules();
        controller.goToPreviousScreen();
        Assertions.assertEquals(Screen.welcome, controller.getCurrentScreen());
        Assertions.assertEquals(Screen.rules, controller.getPreviousScreen());

    }
}