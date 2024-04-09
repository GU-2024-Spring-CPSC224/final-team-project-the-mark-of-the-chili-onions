package edu.gonzaga.Nuffatafl.backend;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GameManager {
    private Board board;
    private Team currentTeam;
    private Team winner = Team.NONE;

    private PropertyChangeSupport boardChangeManager;
    private PropertyChangeSupport currentTeamChangeManager;
    private PropertyChangeSupport winnerChangeManager;

    public GameManager(int size) {
        board = new Board(size);
        currentTeam = Team.ATTACKER;
        this.setupChangeManagers();
    }

    public GameManager() {
        board = new Board();
        currentTeam = Team.DEFENDER;
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
     * @return The success of the move.
     */
    public boolean movePiece(Position from, Position to) {
        Board oldBoard = this.board;

        boolean result = board.movePiece(from, to, currentTeam);
        switchCurrentTeam();

        if (board.isPositionDefenderWin(to)) { this.handleWin(Team.DEFENDER); }
        if (board.doAttackersWin()) { this.handleWin(Team.ATTACKER); }

        this.boardChangeManager.firePropertyChange("board", oldBoard, this.board);
        return result;
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

        this.currentTeamChangeManager.firePropertyChange("currentTeam", oldTeam, this.currentTeam);
    }

    public void handleWin(Team team) {
        this.winner = team;
        this.winnerChangeManager.firePropertyChange("winner", Team.NONE, team);
    }

    public Board getBoard() {return board;}

    public Team getCurrentTeam() {return currentTeam;}

    public void onBoardChange(PropertyChangeListener listener) {
        this.boardChangeManager.addPropertyChangeListener(listener);
    }

    public void onTeamSwitch(PropertyChangeListener listener) {
        this.currentTeamChangeManager.addPropertyChangeListener(listener);
    }

    public void onVictory(PropertyChangeListener listener) {
        this.winnerChangeManager.addPropertyChangeListener(listener);
    }

    private void setupChangeManagers() {
        this.boardChangeManager = new PropertyChangeSupport(this.board);
        this.currentTeamChangeManager = new PropertyChangeSupport(this.currentTeam);
        this.winnerChangeManager = new PropertyChangeSupport(this.winner);
    }
}
