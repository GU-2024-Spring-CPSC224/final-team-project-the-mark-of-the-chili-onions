package edu.gonzaga.Nuffatafl;

import edu.gonzaga.Nuffatafl.backend.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {
    @Test
    void canMovePieceToEmptyTile() {
        Board board = new Board();
        Position from = new Position(0, 3);
        Position to = new Position(3, 3);

        boolean expected = true;

        boolean actual = board.canMove(from, to);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void cannotMovePieceToOccupiedTile() {
        Board board = new Board();
        Position from = new Position(0, 3);
        Position to = new Position(4, 3);

        boolean expected = false;

        boolean actual = board.canMove(from, to);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void positionIsOnCenter() {
        Board board = new Board(9);

        Position pos = new Position(4, 4);

        boolean expected = true;

        boolean actual = board.isPositionCenter(pos);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void positionIsNotOnCenter() {
        Board board = new Board(9);

        Position pos = new Position(3, 4);

        boolean expected = false;

        boolean actual = board.isPositionCenter(pos);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void positionIsOnBoardMin() {
        Board board = new Board(9);

        Position pos = new Position(0, 0);

        boolean expected = true;

        boolean actual = board.isPositionOnBoard(pos);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void positionIsOnBoardMax() {
        Board board = new Board(9);

        Position pos = new Position(8, 8);

        boolean expected = true;

        boolean actual = board.isPositionOnBoard(pos);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void positionIsOffBoardMinX() {
        Board board = new Board(9);

        Position pos = new Position(-1, 0);

        boolean expected = false;

        boolean actual = board.isPositionOnBoard(pos);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void positionIsOffBoardMinY() {
        Board board = new Board(9);

        Position pos = new Position(0, -1);

        boolean expected = false;

        boolean actual = board.isPositionOnBoard(pos);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void positionIsOffBoardMaxX() {
        Board board = new Board(9);

        Position pos = new Position(9, 0);

        boolean expected = false;

        boolean actual = board.isPositionOnBoard(pos);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void positionIsOffBoardMaxY() {
        Board board = new Board(9);

        Position pos = new Position(0, 9);

        boolean expected = false;

        boolean actual = board.isPositionOnBoard(pos);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void canMovePieceToEdgeY() {
        Board board = new Board(9);
        Soldier soldier = new Soldier(Team.ATTACKER);

        Position from = new Position(0, 0);
        Position to = new Position(0, 8);

        board.setPiece(from, soldier);

        boolean expected = true;

        boolean actual = board.canMove(from, to);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void canMovePieceFromEdgeY() {
        Board board = new Board(9);
        Soldier soldier = new Soldier(Team.ATTACKER);

        Position from = new Position(0, 8);
        Position to = new Position(0, 0);

        board.setPiece(from, soldier);

        boolean expected = true;

        boolean actual = board.canMove(from, to);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void canMovePieceToEdgeX() {
        Board board = new Board(9);
        Soldier soldier = new Soldier(Team.ATTACKER);

        Position from = new Position(0, 0);
        Position to = new Position(8, 0);

        board.setPiece(from, soldier);

        boolean expected = true;

        boolean actual = board.canMove(from, to);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void canMovePieceFromEdgeX() {
        Board board = new Board(9);
        Soldier soldier = new Soldier(Team.ATTACKER);

        Position from = new Position(8, 0);
        Position to = new Position(0, 0);

        board.setPiece(from, soldier);

        boolean expected = true;

        boolean actual = board.canMove(from, to);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void cannotMovePieceBeyondEdgeY() {
        Board board = new Board(9);
        Soldier soldier = new Soldier(Team.ATTACKER);

        Position from = new Position(0, 0);
        Position to = new Position(0, 9);

        board.setPiece(from, soldier);

        boolean expected = false;

        boolean actual = board.canMove(from, to);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void cannotMovePieceBeyondEdgeX() {
        Board board = new Board(9);
        Soldier soldier = new Soldier(Team.ATTACKER);

        Position from = new Position(0, 0);
        Position to = new Position(9, 0);

        board.setPiece(from, soldier);

        boolean expected = false;

        boolean actual = board.canMove(from, to);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void cannotMovePieceDiagonal() {
        Board board = new Board(9);
        Soldier soldier = new Soldier(Team.ATTACKER);

        Position from = new Position(0, 0);
        Position to = new Position(1, 1);

        board.setPiece(from, soldier);

        boolean expected = false;

        boolean actual = board.canMove(from, to);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void cannotMovePieceToCenter() {
        Board board = new Board(9);
        Soldier soldier = new Soldier(Team.ATTACKER);

        Position from = new Position(4, 0);
        Position to = new Position(4, 4);

        board.setPiece(from, soldier);

        boolean expected = false;

        boolean actual = board.canMove(from, to);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void successfullySwappedPieces() {
        Board board = new Board(9);
        Soldier soldier = new Soldier(Team.ATTACKER);

        Position from = new Position(0, 0);
        Position to = new Position(1, 1);

        board.setPiece(from, soldier);

        board.swapPieces(from, to);

        boolean expected = true;

        boolean actual = board.getPieceAtPosition(from).isEmpty() && !board.getPieceAtPosition(to).isEmpty();

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void canMovePieceOnTeam() {
        Board board = new Board(9);
        Soldier soldier = new Soldier(Team.ATTACKER);

        Position from = new Position(0, 0);
        Position to = new Position(0, 1);

        board.setPiece(from, soldier);

        boolean expected = true;

        boolean actual = board.canMoveWithTeam(from, to, Team.ATTACKER);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void cannotMovePieceOnOtherTeam() {
        Board board = new Board(9);
        Soldier soldier = new Soldier(Team.ATTACKER);

        Position from = new Position(0, 0);
        Position to = new Position(0, 1);

        board.setPiece(from, soldier);

        boolean expected = false;

        boolean actual = board.canMoveWithTeam(from, to, Team.DEFENDER);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void pieceIsCaptured() {
        Board board = new Board(3);
        Soldier soldier1 = new Soldier(Team.ATTACKER);
        Soldier soldier2 = new Soldier(Team.ATTACKER);
        Soldier soldier3 = new Soldier(Team.DEFENDER);

        Position soldier2Pos = new Position(0, 2);
        Position soldier3Pos = new Position(1, 2);
        Position from = new Position(2, 0);
        Position to = new Position(2, 2);

        board.setPiece(from, soldier1);
        board.setPiece(soldier2Pos, soldier2);
        board.setPiece(soldier3Pos, soldier3);

        boolean success = board.movePiece(from, to, Team.ATTACKER);

        boolean expected = true;

        boolean actual = board.getPieceAtPosition(soldier3Pos).isEmpty();

        Assertions.assertEquals(actual, expected);
    }


    @Test
    void pieceIsNotCaptured() {
        Board board = new Board(3);
        Soldier soldier1 = new Soldier(Team.ATTACKER);
        Soldier soldier2 = new Soldier(Team.ATTACKER);
        King soldier3 = new King();

        Position soldier2Pos = new Position(0, 2);
        Position soldier3Pos = new Position(1, 2);
        Position from = new Position(2, 0);
        Position to = new Position(2, 2);

        board.setPiece(from, soldier1);
        board.setPiece(soldier2Pos, soldier2);
        board.setPiece(soldier3Pos, soldier3);

        boolean success = board.movePiece(from, to, Team.ATTACKER);

        boolean expected = false;

        boolean actual = board.getPieceAtPosition(soldier3Pos).isEmpty();

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void defenderWinOnLeft() {
        Board board = new Board(9);
        Piece king = new King();
        Position pos = new Position(0, 4);

        board.setPiece(pos, king);

        boolean expected = true;

        boolean actual = board.isDefenderWin(pos);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void defenderWinOnRight() {
        Board board = new Board(9);
        Piece king = new King();
        Position pos = new Position(8, 4);

        board.setPiece(pos, king);

        boolean expected = true;

        boolean actual = board.isDefenderWin(pos);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void defenderWinOnTop() {
        Board board = new Board(9);
        Piece king = new King();
        Position pos = new Position(4, 0);

        board.setPiece(pos, king);

        boolean expected = true;

        boolean actual = board.isDefenderWin(pos);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void defenderWinOnBottom() {
        Board board = new Board(9);
        Piece king = new King();
        Position pos = new Position(4, 8);

        board.setPiece(pos, king);

        boolean expected = true;

        boolean actual = board.isDefenderWin(pos);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void defenderNotWinOnBottomWrongSide() {
        Board board = new Board(9);
        Piece king = new Soldier(Team.ATTACKER);
        Position pos = new Position(4, 8);

        board.setPiece(pos, king);

        boolean expected = false;

        boolean actual = board.isDefenderWin(pos);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void defenderNotWinWrongSpot() {
        Board board = new Board(9);
        Piece king = new King();
        Position pos = new Position(4, 4);

        board.setPiece(pos, king);

        boolean expected = false;

        boolean actual = board.isDefenderWin(pos);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void attackersDontWinSoloKing() {
        Board board = new Board(3);
        Piece king = new King();
        Position pos = new Position(1, 1);

        board.setPiece(pos, king);

        boolean expected = false;

        boolean actual = board.isAttackerWin();

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void attackersDontWinKingSurroundedByDefender() {
        Board board = new Board(3);
        Piece king = new King();
        Position pos = new Position(1, 1);
        Team team = Team.DEFENDER;

        Position soldierPos1 = new Position(0,1);
        Position soldierPos2 = new Position(2,1);
        Position soldierPos3 = new Position(1,0);
        Position soldierPos4 = new Position(1,2);

        Soldier soldier1 = new Soldier(team);
        Soldier soldier2 = new Soldier(team);
        Soldier soldier3 = new Soldier(team);
        Soldier soldier4 = new Soldier(team);

        board.setPiece(pos, king);
        board.setPiece(soldierPos1, soldier1);
        board.setPiece(soldierPos2, soldier2);
        board.setPiece(soldierPos3, soldier3);
        board.setPiece(soldierPos4, soldier4);

        boolean expected = false;

        boolean actual = board.isAttackerWin();

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void attackersWin() {
        Board board = new Board(3);
        Piece king = new King();
        Position pos = new Position(1, 1);
        Team team = Team.ATTACKER;

        Position soldierPos1 = new Position(0,1);
        Position soldierPos2 = new Position(2,1);
        Position soldierPos3 = new Position(1,0);
        Position soldierPos4 = new Position(1,2);

        Soldier soldier1 = new Soldier(team);
        Soldier soldier2 = new Soldier(team);
        Soldier soldier3 = new Soldier(team);
        Soldier soldier4 = new Soldier(team);

        board.setPiece(pos, king);
        board.setPiece(soldierPos1, soldier1);
        board.setPiece(soldierPos2, soldier2);
        board.setPiece(soldierPos3, soldier3);
        board.setPiece(soldierPos4, soldier4);

        boolean expected = true;

        boolean actual = board.isAttackerWin();

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void attackersDontWinMissingSoldier() {
        Board board = new Board(3);
        Piece king = new King();
        Position pos = new Position(1, 1);
        Team team = Team.ATTACKER;

        Position soldierPos1 = new Position(0,1);
        Position soldierPos2 = new Position(2,1);
        Position soldierPos3 = new Position(1,0);
        Position soldierPos4 = new Position(1,2);

        Soldier soldier1 = new Soldier(team);
        Soldier soldier2 = new Soldier(team);
        Soldier soldier3 = new Soldier(team);
        Soldier soldier4 = new Soldier(team);

        board.setPiece(pos, king);
        board.setPiece(soldierPos1, soldier1);
        board.setPiece(soldierPos2, soldier2);
        board.setPiece(soldierPos3, soldier3);

        boolean expected = false;

        boolean actual = board.isAttackerWin();

        Assertions.assertEquals(actual, expected);
    }

}
