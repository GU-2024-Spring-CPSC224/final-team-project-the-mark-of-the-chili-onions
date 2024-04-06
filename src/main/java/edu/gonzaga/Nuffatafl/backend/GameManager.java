package edu.gonzaga.Nuffatafl.backend;
public class GameManager {
    private Board board;
    private Team currentTeam;

    public GameManager(int size) {
        board = new Board(size);
        currentTeam = Team.ATTACKER;
    }

    public GameManager() {
        board = new Board();
        currentTeam = Team.DEFENDER;
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
        boolean result = board.movePiece(from, to, currentTeam);
        switchCurrentTeam();

        if (board.isPositionDefenderWin(to)) {
            defenderWin();
        }

        if (board.doAttackersWin()) {
            attackerWin();
        }

        return result;
    }

    /**
     * Flips the current team.
     */
    private void switchCurrentTeam() {
        if (currentTeam == Team.DEFENDER) {
            currentTeam = Team.ATTACKER;
        } else {
            currentTeam = Team.DEFENDER;
        }
    }

    public void attackerWin() {
        //Do stuff here!
    }

    public void defenderWin() {
        //Do stuff here!
    }

    public Board getBoard() {return board;}

    public Team getCurrentTeam() {return currentTeam;}
}
