package edu.gonzaga;

public class NonePiece extends Piece {
    NonePiece() {
        team = Team.NONE;
        type = Type.NONE;
    }

    @Override
    boolean isEmpty() {
        return true;
    }

    @Override
    public String toString() {
        return "-";
    }
}
