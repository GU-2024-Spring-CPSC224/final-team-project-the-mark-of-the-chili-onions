/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo
 * @version v1.0.0 03/28/2024
 */

package edu.gonzaga.Nuffatafl.viewsScreens;

import edu.gonzaga.Nuffatafl.backend.GameManager;
import edu.gonzaga.Nuffatafl.backend.Team;
import edu.gonzaga.Nuffatafl.viewHelpers.Theme;
import edu.gonzaga.Nuffatafl.viewHelpers.ThemeComponent;
import edu.gonzaga.Nuffatafl.viewNavigation.Screen;
import edu.gonzaga.Nuffatafl.viewNavigation.StateController;
import edu.gonzaga.Nuffatafl.views.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyChangeEvent;

/** JPanel that contains the UI for the Gameplay screen */
public class GameplayScreen extends JPanel {
    /* Height for the top and bottom bar */
    private static final int BAR_HEIGHT = 50;
    private final StateController stateController;
    private final GameManager game;
    private final AfterGameDialog afterGameDialog;
    private BoardView boardView;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel capturedPiecesView;
    private TurnHistoryView turnHistoryView;
    private PlayerLabel attackerLabel;
    private PlayerLabel defenderLabel;
    private ThemeLabel victoryLabel;

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
            attackerLabel.update(game.getAttacker());
            handleTeamSwitch();
        });

        game.onDefenderChange(event -> {
            defenderLabel.update(game.getDefender());
            handleTeamSwitch();
        });

        updateSidePanelsVisibility();

        // Update side panel visibility every time the focus mode changes
        stateController.addFocusModeListener(event -> updateSidePanelsVisibility());

        // Update highlighting when the highlighting mode changes
        stateController.addHighlightingModeListener(event -> boardView.setHighlighting(event));

        Theme.setBackgroundFor(this, ThemeComponent.background);

        addComponentListener(componentListener);

        afterGameDialog = new AfterGameDialog(this, stateController);
    }

    // Shows or hides the side panels based on the focus mode
    private void updateSidePanelsVisibility() {
        boolean showPanels = !stateController.getFocusMode();
        capturedPiecesView.setVisible(showPanels);
        turnHistoryView.setVisible(showPanels);
    }

    private void setupLayout() {
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(0);
        borderLayout.setVgap(0);
        setLayout(borderLayout);
    }

    private void setupTopPanel() {
        topPanel = new JPanel(new FlowLayout());
        topPanel.setSize(topPanel.getWidth(), GameplayScreen.BAR_HEIGHT);
        add(topPanel, BorderLayout.NORTH);

        attackerLabel = new PlayerLabel(game.getAttacker(), true);
        Theme.setBackgroundFor(attackerLabel, ThemeComponent.background2);
        topPanel.add(attackerLabel);

        defenderLabel = new PlayerLabel(game.getDefender(), true);
        Theme.setBackgroundFor(defenderLabel, ThemeComponent.background2);
        topPanel.add(defenderLabel);

        victoryLabel = new ThemeLabel("");
        topPanel.add(victoryLabel);

        handleTeamSwitch();

        // Rules button
        ThemeButton rulesButton = new ThemeButton("Rules", ImageLoading.rulesIcon(Theme.ICON_SIZE), label -> {
            stateController.showRules();
        });
        topPanel.add(rulesButton);

        // Settings button
        ThemeButton settingsButton = new ThemeButton("Settings", ImageLoading.settingsIcon(Theme.ICON_SIZE), label -> {
            stateController.showSettings();
        });
        topPanel.add(settingsButton);

        Theme.setBackgroundFor(topPanel, ThemeComponent.background);
    }

    private void setupBottomPanel() {
        // Setup panel and add to view
        bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setSize(bottomPanel.getWidth(), GameplayScreen.BAR_HEIGHT);
        add(bottomPanel, BorderLayout.SOUTH);

        ThemeButton endGameButton = new ThemeButton("End Game", label -> {
            stateController.showWelcomeScreen();
        });

        // End game button

        bottomPanel.add(endGameButton, FlowLayout.LEFT);

        stateController.gameManager.onVictory(event -> {
            endGameButton.button.setText("Start New Game");
        });

        stateController.onScreenChange(event -> {
            Screen state = stateController.getCurrentScreen();
            Screen prev = stateController.getPreviousScreen();
            if (state.equals(Screen.gameplay) && !prev.equals(Screen.gameplay)) {
                endGameButton.button.setText("End Game");
            }
        });

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
        capturedPiecesView = new CapturedPiecesView(stateController);
        add(capturedPiecesView, BorderLayout.WEST);
        Theme.setBackgroundFor(capturedPiecesView, ThemeComponent.background);
    }

    private void setupTurnHistoryView() {
        turnHistoryView = new TurnHistoryView(stateController);
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

        checkResetWinState();
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

        victoryLabel.label.setText("Winner: " + winnerName);
        boardView.deselectPieces();
    }

    /**
     * Handles the victory label needing to be reset when the game resets.
     * This is a little hacky because it runs every time the turn changes, but it makes us not need an extra property
     * change support for the game resetting when it would only be used by this. Also, performance is not an issue as
     * of right now.
     */
    private void checkResetWinState() {
        int turnCount = game.getTurnHistory().size();
        if (turnCount == 0) victoryLabel.label.setText("");
    }

    /** Handles changes to the window sie */
    private final ComponentListener componentListener = new ComponentListener() {
        /**
         * Called when the size of the window  changes
         * Sets focus mode based on size if isAutoFocusModeEnabled is true
         */
        @Override
        public void componentResized(ComponentEvent event) {
            int width = getWidth();

            if (stateController.isAutoFocusModeEnabled) {
                if (width < 800) {
                    stateController.setFocusMode(true);
                } else if (width > 850) {
                    stateController.setFocusMode(false);
                }
            }
        }

        // Unused
        @Override
        public void componentMoved(ComponentEvent e) {
        }

        @Override
        public void componentShown(ComponentEvent e) {
        }

        @Override
        public void componentHidden(ComponentEvent e) {
        }
    };
}
