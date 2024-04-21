package edu.gonzaga.Nuffatafl.backend;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class GameManager {
    private Board board;
    private Team currentTeam;

    private Player attackerPlayer;
    private Player defenderPlayer;

    private ArrayList<Turn> turnHitory = new ArrayList<Turn>();

    /** Tracks which team has won the game, necessary for PropertyChangeSupport to notify UI of victory */
    private Team winner = Team.NONE;

    /** Handles updating observers when the board changes (i.e. when a piece is moved) */
    private PropertyChangeSupport boardChangeManager;

    /** Handles updating observers when the current team changes */
    private PropertyChangeSupport currentTeamChangeManager;

    /** Handles updating observers when a team wins the game */
    private PropertyChangeSupport winnerChangeManager;

    private PropertyChangeSupport turnHistoryChangeManager;

    private static final Team DEFAULT_STARTING_TEAM = Team.ATTACKER;

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
     * Checks if a piece is on the current team and can therefore attempt a move.
     * @param pos The position of the piece to check.
     * @return True if the piece can be moved.
     */
    public boolean canAttemptMove(Position pos) {
        return board.getPieceAtPosition(pos).sameTeam(currentTeam);
    }

    /**
     *  Attempts to move a piece and returns if successful or not.
     * @param from Position to move from.
     * @param to Position to move to.
     * @return The number of pieces captured, -1 if move was invalid.
     */
    public Integer movePiece(Position from, Position to) {
        //Saves the old board for the PropertyChangeEvent oldValue
        Board oldBoard = this.board;

        Integer result = board.movePiece(from, to, currentTeam);
        if (result < 0) { return -1; }
        storeTurn(currentTeam, from, to, result);
        switchCurrentTeam();

        if (board.isDefenderWin(to)) { this.handleWin(Team.DEFENDER); }
        if (board.isAttackerWin()) { this.handleWin(Team.ATTACKER); }

        //Publishes that the board was changed to observers of the board
        this.boardChangeManager.firePropertyChange("board", oldBoard, getBoard());
        return result;
    }

    private boolean storeTurn(Team team, Position from, Position to, Integer captureCount) {
        Player player = switch (team) {
            case ATTACKER -> attackerPlayer;
            case DEFENDER -> defenderPlayer;
            default -> null;
        };

        if (player == null) { return false; }

        Turn turn = new Turn(player, from, to, captureCount);
        turnHitory.add(0, turn);
        return false;
    }

    /**
     * Flips the current team.
     */
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

    public void handleWin(Team team) {
        this.winner = team;

        //Publishes that a team won the game to observers of winner
        this.winnerChangeManager.firePropertyChange("winner", Team.NONE, team);
    }

    public Team getWinner() {
        return winner;
    }

    public Board getBoard() {return board;}

    public Team getCurrentTeam() {return currentTeam;}

    /**
     * Adds an observer to be notified when the board is changed
     * @param listener Code to execute when board changes
     */
    public void onBoardChange(PropertyChangeListener listener) {
        this.boardChangeManager.addPropertyChangeListener(listener);
    }

    /**
     * Adds an observer to be notified when the current team is changed
     * @param listener Code to execute when current team changes
     */
    public void onTeamSwitch(PropertyChangeListener listener) {
        this.currentTeamChangeManager.addPropertyChangeListener(listener);
    }

    /**
     * Adds an observer to be notified when a team wins the game
     * @param listener Code to execute when a team wins the game
     */
    public void onVictory(PropertyChangeListener listener) {
        winnerChangeManager.addPropertyChangeListener(listener);
    }

    public void setAttackerPlayer(Player player) {
        attackerPlayer = player;
    }

    public Player getAttackerPlayer(Player player) {
        return attackerPlayer;
    }

    public void setDefenderPlayer(Player player) {
        defenderPlayer = player;
    }

    public Player getDefenderPlayer(Player player) {
        return defenderPlayer;
    }

    public ArrayList<Turn> getTurnHistory() {
        return turnHitory;
    }

    public void onTurnHistoryChange(PropertyChangeListener listener) {
        turnHistoryChangeManager.addPropertyChangeListener(listener);
    }

    /** Sets up PropertyChangeSupport objects for the board, currentTeam, and winner */
    private void setupChangeManagers() {
        boardChangeManager = new PropertyChangeSupport(this.board);
        currentTeamChangeManager = new PropertyChangeSupport(this.currentTeam);
        winnerChangeManager = new PropertyChangeSupport(this.winner);
        turnHistoryChangeManager = new PropertyChangeSupport(turnHitory);
    }
}
