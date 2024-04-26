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

import edu.gonzaga.Nuffatafl.backend.*;
import edu.gonzaga.Nuffatafl.viewHelpers.*;
import edu.gonzaga.Nuffatafl.viewNavigation.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;

/** JPanel that contains the UI for the Gameplay screen */
public class GameplayScreen extends JPanel {
    private final StateController stateController;
    private final GameManager game;
    private BoardView boardView;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel capturedPiecesView;
    private JPanel turnHistoryView;
    private PlayerLabel attackerLabel;
    private PlayerLabel defenderLabel;
    private JLabel victoryLabel;

    public GameplayScreen(StateController stateController) {
        super();
        
        // Set up the board view the first time this screen is shown
        this.stateController = stateController;
        this.stateController.onScreenChange(event -> {
            if (event.getNewValue() == Screen.gameplay && boardView == null) {
                setupBoardView();
            }
        });

        // Set up callbacks for when team is switched or game is won
        game = stateController.gameManager;
        game.onTeamSwitch(event -> handleTeamSwitch());
        game.onVictory(event -> handleVictory(event));

        // Set up the UI
        setupLayout();
        setupTopPanel();
        setupBottomPanel();
        setupCapturedPiecesView();
        setupTurnHistoryView();

        game.onAttackerChange(event -> {
            System.out.println("attacker change in gameplay: " + game.getAttacker().name);
            attackerLabel.update(game.getAttacker());
            handleTeamSwitch();
        });

        game.onDefenderChange(event -> {
            System.out.println("defender change in gameplay: " + game.getDefender().name);
            defenderLabel.update(game.getDefender());
            handleTeamSwitch();
        });
         
        Theme.setBackgroundFor(this, ThemeComponent.background);     
    }
    
    private void setupLayout() {
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(0);
        borderLayout.setVgap(0);
        setLayout(borderLayout);
    }

    private void setupTopPanel() {
        topPanel = new JPanel(new FlowLayout());
        topPanel.setSize(topPanel.getWidth(), 50);
        add(topPanel, BorderLayout.NORTH);

        attackerLabel = new PlayerLabel(game.getAttacker());
        Theme.setBackgroundFor(attackerLabel, ThemeComponent.background2);
        topPanel.add(attackerLabel);

        defenderLabel = new PlayerLabel(game.getDefender());
        Theme.setBackgroundFor(defenderLabel, ThemeComponent.background2);
        topPanel.add(defenderLabel);

        handleTeamSwitch();

        victoryLabel = new JLabel("");
        topPanel.add(victoryLabel);

        // Rules button
        ThemeButton rulesButton = new ThemeButton("Rules", ImageLoading.rulesIcon(20), label -> {
            stateController.showRules();
        });
        topPanel.add(rulesButton);

        // Settings button
        ThemeButton settingsButton = new ThemeButton("Settings", ImageLoading.settingsIcon(20), label -> {
            stateController.showSettings();
        });
        topPanel.add(settingsButton);   
        
        Theme.setBackgroundFor(topPanel, ThemeComponent.background);
    }
    
    private void setupBottomPanel() {
        // Setup panel and add to view
        bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setSize(bottomPanel.getWidth(), 50);
        add(bottomPanel, BorderLayout.SOUTH);

        // End game button
        bottomPanel.add(
            new ThemeButton("End Game", label -> {
                stateController.endGame();
            }), 
            FlowLayout.LEFT
        );

        // Deselect pieces button
        bottomPanel.add(
            new ThemeButton("Deselect Pieces", label -> {
                boardView.deselectPieces();
            })
        );

        // End game button
        bottomPanel.add(
            new ThemeButton("Move Piece", label -> {
                boardView.attemptMove();
            })
        );
        
        Theme.setBackgroundFor(bottomPanel, ThemeComponent.background);
    }

    private void setupBoardView() {
        boardView = new BoardView(game);
        add(boardView, BorderLayout.CENTER);
        
        Theme.setBackgroundFor(boardView, ThemeComponent.background);
    }

    private void setupCapturedPiecesView() {
        capturedPiecesView = new JPanel();
        capturedPiecesView.setLayout(new BoxLayout(capturedPiecesView, BoxLayout.Y_AXIS));
        add(capturedPiecesView, BorderLayout.WEST);

        
        Theme.setBackgroundFor(capturedPiecesView, ThemeComponent.background);
    }

    private void setupTurnHistoryView() {
        turnHistoryView = new JPanel();
        add(turnHistoryView, BorderLayout.EAST);
        
        Theme.setBackgroundFor(turnHistoryView, ThemeComponent.background);
    }

    /** Put a border around the label of the current player and not on the other one */
    private void handleTeamSwitch() {
        Color attackerColor = stateController.gameManager.getAttacker().color;
        Color defenderColor = stateController.gameManager.getDefender().color;

        switch (stateController.gameManager.getCurrentTeam()) {
            case ATTACKER -> {
                attackerLabel.setBorder(new LineBorder(attackerColor, 2));
                defenderLabel.setBorder(new EmptyBorder(0, 0, 0, 0));
            }
            case DEFENDER -> {
                defenderLabel.setBorder(new LineBorder(defenderColor, 2));
                attackerLabel.setBorder(new EmptyBorder(0, 0, 0, 0));
            }
            default -> {
                attackerLabel.setBorder(new EmptyBorder(0, 0, 0, 0));
                defenderLabel.setBorder(new EmptyBorder(0, 0, 0, 0));
            }
        }
    }

    /** Display a notice that the game has been won and disable all the pieces on the board */
    private void handleVictory(PropertyChangeEvent event) {
        Team winningTeam = game.getWinner();
        String winnerName = winningTeam.name();

        if (winningTeam.equals(Team.ATTACKER)) {
            winnerName = game.getAttacker().name;
        } else if (winningTeam.equals(Team.DEFENDER)) {
            winnerName = game.getAttacker().name;
        }

        victoryLabel.setText("Winner: " + winnerName);
        boardView.deselectPieces();
    }
}
