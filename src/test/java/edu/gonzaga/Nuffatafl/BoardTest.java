package edu.gonzaga.Nuffatafl;

import edu.gonzaga.Nuffatafl.Position;
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
    void canMovePieceToEdgeY() {
        Board board = new Board(9);
        Soldier soldier = new Soldier(Piece.Team.ATTACKER);

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
        Soldier soldier = new Soldier(Piece.Team.ATTACKER);

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
        Soldier soldier = new Soldier(Piece.Team.ATTACKER);

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
        Soldier soldier = new Soldier(Piece.Team.ATTACKER);

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
        Soldier soldier = new Soldier(Piece.Team.ATTACKER);

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
        Soldier soldier = new Soldier(Piece.Team.ATTACKER);

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
        Soldier soldier = new Soldier(Piece.Team.ATTACKER);

        Position from = new Position(0, 0);
        Position to = new Position(1, 1);

        board.setPiece(from, soldier);

        boolean expected = false;

        boolean actual = board.canMove(from, to);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void successfullySwappedPieces() {
        Board board = new Board(9);
        Soldier soldier = new Soldier(Piece.Team.ATTACKER);

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
        Soldier soldier = new Soldier(Piece.Team.ATTACKER);

        Position from = new Position(0, 0);
        Position to = new Position(0, 1);

        board.setPiece(from, soldier);

        boolean expected = true;

        boolean actual = board.canMoveWithTeam(from, to, Piece.Team.ATTACKER);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void cannotMovePieceOnOtherTeam() {
        Board board = new Board(9);
        Soldier soldier = new Soldier(Piece.Team.ATTACKER);

        Position from = new Position(0, 0);
        Position to = new Position(0, 1);

        board.setPiece(from, soldier);

        boolean expected = false;

        boolean actual = board.canMoveWithTeam(from, to, Piece.Team.DEFENDER);

        Assertions.assertEquals(actual, expected);
    }
}
