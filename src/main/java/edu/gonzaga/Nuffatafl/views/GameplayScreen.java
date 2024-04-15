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

import edu.gonzaga.Nuffatafl.backend.GameManager;
import edu.gonzaga.Nuffatafl.backend.Team;
import edu.gonzaga.Nuffatafl.viewNavigation.Screen;
import edu.gonzaga.Nuffatafl.viewNavigation.StateController;

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
    private JLabel titleLabel;
    private JLabel currentTeamLabel;

    public GameplayScreen(StateController stateController) {
        super();

        this.stateController = stateController;
        this.stateController.onScreenChange(event -> {
            if (event.getNewValue() == Screen.gameplay && boardView == null) {
                setupBoardView();
            }
        });

        game = stateController.gameManager;
        game.onTeamSwitch(event -> handleTeamSwitch(event));
        game.onVictory(event -> handleVictory(event));

        setupLayout();
        setupTopPanel();
        setupBottomPanel();
        setupCapturedPeicesView();
        setupTurnHistoryView();
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

        // Window Title
        titleLabel = new JLabel("Gameplay Screen: ");
        topPanel.add(titleLabel);

        // Current Team
        currentTeamLabel = new JLabel();
        setCurrentTeamText();
        topPanel.add(currentTeamLabel);

        // Rules button
        JButton showRulesScreenButton = new JButton("Rules");
        showRulesScreenButton.addActionListener(event -> stateController.showRules());
        topPanel.add(showRulesScreenButton);

        // Settings button
        JButton settingsButton = new JButton();
        settingsButton.setIcon(ImageLoading.settingsIcon(20, 20));
        settingsButton.setText("Settings");
        settingsButton.addActionListener(event -> stateController.showSettings());
        topPanel.add(settingsButton);
    }
    
    private void setupBottomPanel() {
        // Setup panel and add to view
        bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setSize(bottomPanel.getWidth(), 50);
        add(bottomPanel, BorderLayout.SOUTH);

        // End game button
        JButton showWelcomeScreenButton = new JButton("End Game");
        showWelcomeScreenButton.addActionListener(event -> stateController.endGame());
        bottomPanel.add(showWelcomeScreenButton);

        // Deselect pieces button
        JButton deselectPiecesButton = new JButton("Deselect Pieces");
        deselectPiecesButton.addActionListener(event -> {
            boardView.deselectPieces();
        });
        bottomPanel.add(deselectPiecesButton);

        // End game button
        JButton movePieceButton = new JButton("Move Piece");
        movePieceButton.addActionListener(event -> {
            boardView.attemptMove();
        });
        bottomPanel.add(movePieceButton);
    }

    private void setupBoardView() {
        boardView = new BoardView(game);
        add(boardView, BorderLayout.CENTER);
    }

    private void setupCapturedPeicesView() {
        capturedPiecesView = new JPanel();
        add(capturedPiecesView, BorderLayout.WEST);
    }

    private void setupTurnHistoryView() {
        turnHistoryView = new JPanel();
        add(turnHistoryView, BorderLayout.EAST);
    }

    private void handleTeamSwitch(PropertyChangeEvent event) {
        setCurrentTeamText();
    }

    private void handleVictory(PropertyChangeEvent event) {
        titleLabel.setText("Winner: " + (Team) event.getNewValue());
        currentTeamLabel.setText("");
    }

    private void setCurrentTeamText() {
        currentTeamLabel.setText(game.getCurrentTeam().name() + "'s turn");
    }
}
