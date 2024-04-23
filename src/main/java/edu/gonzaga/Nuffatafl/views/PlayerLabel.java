package edu.gonzaga.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.backend.Player;
import edu.gonzaga.Nuffatafl.backend.Team;
import edu.gonzaga.Nuffatafl.viewNavigation.StateController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PlayerLabel extends JPanel implements PropertyChangeListener {
    boolean attacker;
    Player player;

    public PlayerLabel(StateController stateController, boolean attacker) {
        super();
        if (attacker) {
            player = new Player("Temp", "🥸", Color.black, Team.ATTACKER);

        } else {
            player = new Player("Temp", "🥸", Color.black, Team.DEFENDER);
        }
        this.attacker = attacker;
        this.add(player.label());

        stateController.playerChangeRelay.addPlayerListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName().equals("attackerChange") && attacker) {
            player = (Player)propertyChangeEvent.getNewValue();
            this.removeAll();
            this.add(player.label());
            this.revalidate();
            System.out.println("Attacker Changed");
        } else if (propertyChangeEvent.getPropertyName().equals("defenderChange") && !attacker) {
            player = (Player)propertyChangeEvent.getNewValue();
            this.removeAll();
            this.add(player.label());
            this.revalidate();
            System.out.println("Defender Changed");
        }
    }
}
