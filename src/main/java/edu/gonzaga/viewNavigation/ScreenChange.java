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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//See below ScreenChange class definition for an example on how to use ScreenChange ActionListeners
//to change the screen that is currently being displayed

/** Provides the action listeners to change which screen is displayed as static member variables for ease of use */
public class ScreenChange implements ActionListener {
    public ScreenChange(UIActionType actionType) {
        this.actionType = actionType;
    }

    /** The UIActionType that corresponds to the screen that should be displayed next */
    private final UIActionType actionType;

    /**
     * The override function that executes code when the ActionListener is triggered.
     * Calls on ViewController.main to change screens to the screen indicated by the stored UIActionType
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        ViewController.main.changeScreen(this.actionType);
    }

    /** An action listener that changes the screen to the welcome screen when triggered */
    public static ScreenChange toWelcomeScreen = new ScreenChange(UIActionType.showWelcomeScreen);

    /** An action listener that changes the screen to the gameplay screen when triggered */
    public static ScreenChange toGameplayScreen = new ScreenChange(UIActionType.showGameplayScreen);

    /** An action listener that changes the screen to the after game screen when triggered */
    public static ScreenChange toAfterGameScreen = new ScreenChange(UIActionType.showAfterGameScreen);

    /** An action listener that changes the screen to the rules screen when triggered */
    public static ScreenChange toRulesScreen = new ScreenChange(UIActionType.showRulesScreen);

    /** An action listener that changes the screen to the settings screen when triggered */
    public static ScreenChange toSettingsScreen = new ScreenChange(UIActionType.showSettingsScreen);

    /** An action listener that changes the screen to the previously displayed screen when triggered */
    public static ScreenChange toPreviousScreen = new ScreenChange(UIActionType.goToPreviousScreen);
}

/*
Examples on how to use a ScreenChange ActionListener in a JButton:

1. This example creates a button that changes the screen currently being displayed to the settings screen when pressed
To do this, use the addActionListener method on the JButton and pass in the ScreenChange.toSettingsScreen static member

```
    JButton showSettingsScreenButton = new JButton("Show Settings Screen");
    showSettingsScreenButton.addActionListener(ScreenChange.toSettingsScreen);
```

2. This example creates a button that changes the screen to the previously displayed screen when pressed
For this, add the ScreenChange.toPreviousScreen ActionListener

```
    JButton goToPreviousViewButton = new JButton("Hide Rules Screen");
    goToPreviousViewButton.addActionListener(ScreenChange.toPreviousScreen);
```
 */