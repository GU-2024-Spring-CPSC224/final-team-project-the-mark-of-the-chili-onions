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
    private JLabel currentTeamLabel;

    public GameplayScreen(StateController stateController) {
        super();
        
        // Set up the board view the first time this screen is shown
        this.stateController = stateController;
        this.stateController.onScreenChange(event -> {
            if (event.getNewValue() == Screen.gameplay && boardView == null) {
                setupBoardView();
            }
        });

        // Set up callbakcs for when team is switched or game is won
        game = stateController.gameManager;
        game.onTeamSwitch(event -> handleTeamSwitch(event));
        game.onVictory(event -> handleVictory(event));

        // Set up the UI
        setupLayout();
        setupTopPanel();
        setupBottomPanel();
        setupCapturedPeicesView();
        setupTurnHistoryView();  
         
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

        // Current Team
        currentTeamLabel = new JLabel();
        Theme.setForegroundFor(currentTeamLabel, ThemeComponent.text);
        setCurrentTeamText();
        topPanel.add(currentTeamLabel);

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

    private void setupCapturedPeicesView() {
        capturedPiecesView = new JPanel();
        capturedPiecesView.setLayout(new BoxLayout(capturedPiecesView, BoxLayout.Y_AXIS));
        add(capturedPiecesView, BorderLayout.WEST);
        
        for (Theme theme : Theme.themes) {
            capturedPiecesView.add(
                new ThemeButton(theme.getName(), label -> {
                    Theme.setTheme(Theme.from(label.getText()));
                })
            );
        }
        
        Theme.setBackgroundFor(capturedPiecesView, ThemeComponent.background);
    }

    private void setupTurnHistoryView() {
        turnHistoryView = new JPanel();
        add(turnHistoryView, BorderLayout.EAST);
        
        Theme.setBackgroundFor(turnHistoryView, ThemeComponent.background);
    }

    private void handleTeamSwitch(PropertyChangeEvent event) {
        setCurrentTeamText();
    }

    private void handleVictory(PropertyChangeEvent event) {
        currentTeamLabel.setText("Winner: " + (Team) event.getNewValue());
        boardView.deselectPieces();
    }

    private void setCurrentTeamText() {
        currentTeamLabel.setText(game.getCurrentTeam().name() + "'s turn");
    }
}
