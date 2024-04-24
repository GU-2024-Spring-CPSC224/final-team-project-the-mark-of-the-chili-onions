package edu.gonzaga.Nuffatafl.views.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.views.Nuffatafl.backend.Player;
import edu.gonzaga.Nuffatafl.views.Nuffatafl.backend.Team;
import edu.gonzaga.Nuffatafl.views.Nuffatafl.viewNavigation.StateController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PlayerLabel extends JPanel implements PropertyChangeListener {
    private StateController stateController;
    boolean attacker;
    Player player;

    public PlayerLabel(StateController stateController, boolean attacker) {
        super();
        if (attacker) {
            player = new Player("Temp", "🥸", Color.black, Team.ATTACKER);
            stateController.gameManager.setAttackerPlayer(player);

        } else {
            player = new Player("Temp", "🥸", Color.black, Team.DEFENDER);
            stateController.gameManager.setDefenderPlayer(player);
        }
        this.stateController = stateController;
        this.attacker = attacker;
        this.add(player.label());
        stateController.playerChangeRelay.addPlayerListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName().equals("attackerChange") && attacker) {
            player = (Player)propertyChangeEvent.getNewValue();
            stateController.gameManager.setAttackerPlayer(player);
            this.removeAll();
            this.add(player.label());
            this.revalidate();
            System.out.println("Attacker Changed");
        } else if (propertyChangeEvent.getPropertyName().equals("defenderChange") && !attacker) {
            player = (Player)propertyChangeEvent.getNewValue();
            stateController.gameManager.setDefenderPlayer(player);
            this.removeAll();
            this.add(player.label());
            this.revalidate();
            System.out.println("Defender Changed");
        }
    }
}
