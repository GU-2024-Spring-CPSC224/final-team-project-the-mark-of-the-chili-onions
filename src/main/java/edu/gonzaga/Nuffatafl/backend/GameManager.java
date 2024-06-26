/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Cash Hilstad, Mark Reggiardo
 * @version v1.0.0 04/28/2024
 */

package edu.gonzaga.Nuffatafl.backend;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/** Manages the information for the gameplay */
public class GameManager {
    private static final Team DEFAULT_STARTING_TEAM = Team.ATTACKER;
    private final ArrayList<Turn> turnHistory = new ArrayList<Turn>();
    private Board board;
    private Team currentTeam;
    private Player attacker = new Player("Player 1", "🥸", Color.red, Team.ATTACKER);
    private Player defender = new Player("Player 2", "🥺", Color.blue, Team.DEFENDER);
    private PropertyChangeSupport attackerChange;
    private PropertyChangeSupport defenderChange;
    /** Tracks which team has won the game, necessary for PropertyChangeSupport to notify UI of victory */
    private Team winner = Team.NONE;
    /** Handles updating observers when the board changes (i.e. when a piece is moved) */
    private PropertyChangeSupport boardChangeManager;
    /** Handles updating observers when the current team changes */
    private PropertyChangeSupport currentTeamChangeManager;
    /** Handles updating observers when a team wins the game */
    private PropertyChangeSupport winnerChangeManager;

    public GameManager(int size) {
        board = new Board(size);
        currentTeam = DEFAULT_STARTING_TEAM;
        this.setupChangeManagers();
    }

    public GameManager() {
        board = new Board();
        currentTeam = DEFAULT_STARTING_TEAM;
        this.setupChangeManagers();
    }

    /**
     * Resets the game, bringing the starting team back to default,
     * clearing the turn history, clearing captured pieces, and resetting the board.
     */
    public void reset() {
        //Reset starting team
        currentTeam = DEFAULT_STARTING_TEAM;

        //Reset turn history
        turnHistory.clear();

        // Reset winner
        winner = Team.NONE;

        //Reset board
        Board oldBoard = this.board;
        this.board = new Board();
        this.boardChangeManager.firePropertyChange("board", oldBoard, this.board);
    }

    /**
     * Checks if a piece is on the current team and can therefore attempt a move.
     *
     * @param pos The position of the piece to check.
     * @return True if the piece can be moved.
     */
    public boolean canAttemptMove(Position pos) {
        return board.getPieceAtPosition(pos).sameTeam(currentTeam);
    }

    /**
     * Takes a position and returns an ArrayList holding all valid move spots.
     *
     * @param pos The position to check from.
     * @return An ArrayList of valid spots.
     */
    public ArrayList<Position> getValidMoveSpots(Position pos) {
        ArrayList<Position> spots = new ArrayList<Position>();

        for (int y = 0; y < board.getSize(); y++) {
            for (int x = 0; x < board.getSize(); x++) {
                Position currentPos = new Position(x, y);
                if (board.canMoveWithTeam(pos, currentPos, currentTeam)) {
                    spots.add(currentPos);
                }
            }
        }

        return spots;
    }

    /**
     * Attempts to move a piece and returns if successful or not.
     *
     * @param from Position to move from.
     * @param to   Position to move to.
     * @return The number of pieces captured, -1 if move was invalid.
     */
    public Integer movePiece(Position from, Position to) {
        //Saves the old board for the PropertyChangeEvent oldValue
        Board oldBoard = this.board;

        Integer result = board.movePiece(from, to, currentTeam);

        if (result < 0) {
            return -1;
        }
        storeTurn(currentTeam, from, to, result);
        switchCurrentTeam();

        if (defenderWinFromPieceCount()) {
            this.handleWin(Team.DEFENDER);
        }
        if (board.isDefenderWin(to)) {
            this.handleWin(Team.DEFENDER);
        }
        if (board.isAttackerWin()) {
            this.handleWin(Team.ATTACKER);
        }

        // Publishes that the board was changed to observers of the board
        // new value is not the same type as old value so that the property change will always fire
        this.boardChangeManager.firePropertyChange("board", oldBoard, result);
        return result;
    }

    /**
     * Handles storing turns with their data into turnHistory
     *
     * @param team         Team to store
     * @param from         Where they moved from
     * @param to           Where they moved to
     * @param captureCount How many pieces they captured with the move
     * @return Unused
     */
    private boolean storeTurn(Team team, Position from, Position to, Integer captureCount) {
        Player player = switch (team) {
            case ATTACKER -> attacker;
            case DEFENDER -> defender;
            default -> null;
        };

        if (player == null) {
            return false;
        }

        Turn turn = new Turn(player, from, to, captureCount);
        turnHistory.add(0, turn);
        return false;
    }

    /** Flips the current team. */
    private void switchCurrentTeam() {
        Team oldTeam = this.currentTeam;

        if (currentTeam == Team.DEFENDER) {
            currentTeam = Team.ATTACKER;
        } else {
            currentTeam = Team.DEFENDER;
        }

        //Publishes that the current team was changed to observers of currentTeam
        this.currentTeamChangeManager.firePropertyChange("currentTeam", oldTeam, this.currentTeam);
    }

    /** Handles the publishing of a win state */
    public void handleWin(Team team) {
        this.winner = team;

        //Publishes that a team won the game to observers of winner
        this.winnerChangeManager.firePropertyChange("winner", Team.NONE, team);
    }

    public Team getWinner() {
        return winner;
    }

    public Board getBoard() {
        return board;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }

    /**
     * Adds an observer to be notified when the board is changed
     *
     * @param listener Code to execute when board changes
     */
    public void onBoardChange(PropertyChangeListener listener) {
        this.boardChangeManager.addPropertyChangeListener(listener);
    }

    /**
     * Adds an observer to be notified when the current team is changed
     *
     * @param listener Code to execute when current team changes
     */
    public void onTeamSwitch(PropertyChangeListener listener) {
        this.currentTeamChangeManager.addPropertyChangeListener(listener);
    }

    /**
     * Adds an observer to be notified when a team wins the game
     *
     * @param listener Code to execute when a team wins the game
     */
    public void onVictory(PropertyChangeListener listener) {
        winnerChangeManager.addPropertyChangeListener(listener);
    }

    public Player getAttacker() {
        return attacker;
    }

    public void setAttacker(Player player) {
        attacker = player;
        attackerChange.firePropertyChange("attacker", attacker, 1);
    }

    public void onAttackerChange(PropertyChangeListener listener) {
        attackerChange.addPropertyChangeListener(listener);
    }

    public Player getDefender() {
        return defender;
    }

    public void setDefender(Player player) {
        defender = player;
        defenderChange.firePropertyChange("defender", defender, 1);
    }

    public void onDefenderChange(PropertyChangeListener listener) {
        defenderChange.addPropertyChangeListener(listener);
    }

    public ArrayList<Turn> getTurnHistory() {
        return turnHistory;
    }

    /**
     * Checks if the defender wins due to capturing all the attacker's pieces
     * @return boolean of whether the defender won
     */
    private boolean defenderWinFromPieceCount() {
        ArrayList<Turn> turns = getTurnHistory();

        int capturedAttackers = 0;

        final int startingAttackers = 16;

        for (Turn turn : turns) {
            Team team = turn.player.team;

            if (team.equals(Team.DEFENDER)) {
                capturedAttackers += turn.capturedPieceCount;
            }
        }

        // Return true if captured attackers is equal to the starting number, false otherwise
        return capturedAttackers == startingAttackers;
    }

    /** Sets up PropertyChangeSupport objects for the board, currentTeam, and winner */
    private void setupChangeManagers() {
        boardChangeManager = new PropertyChangeSupport(this.board);
        currentTeamChangeManager = new PropertyChangeSupport(this.currentTeam);
        winnerChangeManager = new PropertyChangeSupport(this.winner);
        attackerChange = new PropertyChangeSupport(attacker);
        defenderChange = new PropertyChangeSupport(defender);
    }
}
