package edu.gonzaga.Nuffatafl.views.Nuffatafl.viewHelpers;

import edu.gonzaga.Nuffatafl.views.Nuffatafl.views.WelcomeScreen;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PlayerChangeRelay implements PropertyChangeListener {
    private PropertyChangeSupport playerChangeSupport;

    public PlayerChangeRelay(WelcomeScreen welcomeScreen) {
        playerChangeSupport = new PropertyChangeSupport(this);
        welcomeScreen.addPlayerListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("PlayerChangeRelay propertyChanged " + evt.getPropertyName());
        playerChangeSupport.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
    }

    public void addPlayerListener(PropertyChangeListener playerListener) {
        playerChangeSupport.addPropertyChangeListener(playerListener);
    }

}
