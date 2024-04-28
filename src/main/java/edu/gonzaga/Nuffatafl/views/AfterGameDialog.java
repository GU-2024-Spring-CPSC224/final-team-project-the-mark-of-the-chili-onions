package edu.gonzaga.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.backend.Player;
import edu.gonzaga.Nuffatafl.backend.Team;
import edu.gonzaga.Nuffatafl.backend.Turn;
import edu.gonzaga.Nuffatafl.viewHelpers.Theme;
import edu.gonzaga.Nuffatafl.viewHelpers.ThemeComponent;
import edu.gonzaga.Nuffatafl.viewNavigation.StateController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class AfterGameDialog extends JDialog {
    private final JPanel parentPanel;
    private final StateController stateController;

    public AfterGameDialog(JPanel panel, StateController stateController) {
        super(SwingUtilities.windowForComponent(panel), "Game Over");
        setVisible(false);
        this.parentPanel = panel;
        this.stateController = stateController;

        // Display the dialog when the game is won
        this.stateController.gameManager.onVictory(event -> displayEndGameDialog());
        setMinimumSize(new Dimension(460, 300));
        setPreferredSize(new Dimension(500, 300));
    }

    // Display the dialog
    public void displayEndGameDialog() {
        JPanel panel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayout);

        panel.add(winInfoPanel());
        panel.add(buttonPanel());

        setContentPane(panel);

        pack();
        setLocationRelativeTo(SwingUtilities.windowForComponent(parentPanel));

        Theme.setBackgroundFor(panel, ThemeComponent.background);

        panel.setBorder(new EmptyBorder(Theme.PADDING_L, Theme.PADDING_L, Theme.PADDING_L, Theme.PADDING_L));
        setVisible(true);
    }

    private JPanel winInfoPanel() {
        JPanel panel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayout);

        // Note that these p1 and p2 Players do not correspond to which player is an attacker or defender,
        // and only serve to hold two players
        Player p1;
        Player p2;
        String p1TeamString;
        String p2TeamString;

        // Handle which player should be which so that we display the winner right.
        // Use the first option if no one won because putting p1 first makes the most sense.
        if (stateController.gameManager.getWinner() == Team.ATTACKER || stateController.gameManager.getWinner() == Team.NONE) {
            p1 = stateController.gameManager.getAttacker();
            p2 = stateController.gameManager.getDefender();
            p1TeamString = "Attacker";
            p2TeamString = "Defender";
        } else {
            p1 = stateController.gameManager.getDefender();
            p2 = stateController.gameManager.getAttacker();
            p1TeamString = "Defender";
            p2TeamString = "Attacker";
        }
        int winningTeamCaptureCount = 0;
        int losingTeamCaptureCount = 0;

        // Get how many pieces each player captured
        ArrayList<Turn> turns = stateController.gameManager.getTurnHistory();
        for (Turn turn : turns) {
            Team team = turn.player.team;

            if (team.equals(Team.DEFENDER)) {
                winningTeamCaptureCount += turn.capturedPieceCount;
            } else if (team.equals(Team.ATTACKER)) {
                losingTeamCaptureCount += turn.capturedPieceCount;
            }
        }

        // Use text like "won" and "lost" if there was actually a winner, otherwise just used "played"
        String winnerInfoText;
        String loserInfoText;
        if (stateController.gameManager.getWinner() != Team.NONE) {
            winnerInfoText = p1.name + " won as the " + p1TeamString + " with " + winningTeamCaptureCount + " captures!";
            loserInfoText = p2.name + " lost as the " + p2TeamString + " with " + losingTeamCaptureCount + " captures.";
        } else {
            winnerInfoText = p1.name + " played as the " + p1TeamString + " with " + winningTeamCaptureCount + " captures!";
            loserInfoText = p2.name + " played as the " + p2TeamString + " with " + losingTeamCaptureCount + " captures!";
        }

        ThemeLabel p1Label = new ThemeLabel(winnerInfoText);
        panel.add(p1Label);
        ThemeLabel p2Label = new ThemeLabel(loserInfoText);
        panel.add(p2Label);
        Theme.setForegroundFor(panel, ThemeComponent.text);
        Theme.setBackgroundFor(panel, ThemeComponent.background);

        panel.setBorder(new EmptyBorder(Theme.PADDING_L, Theme.PADDING_L, Theme.PADDING_L, Theme.PADDING_L));
        return panel;
    }


    private JPanel buttonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        setContentPane(panel);

        ThemeButton mainMenuButton = new ThemeButton("Return to Main Menu", event -> {
            setVisible(false);
            stateController.showWelcomeScreen();
        });
        panel.add(mainMenuButton);

        ThemeButton quitButton = new ThemeButton("Quit Program", event -> stateController.endProgram());
        panel.add(quitButton);

        ThemeButton okButton = new ThemeButton("Dismiss", event -> setVisible(false));
        panel.add(okButton);

        Theme.setBackgroundFor(panel, ThemeComponent.background);
        panel.setBorder(new EmptyBorder(Theme.PADDING_L, Theme.PADDING_L, Theme.PADDING_L, Theme.PADDING_L));

        return panel;
    }
}
