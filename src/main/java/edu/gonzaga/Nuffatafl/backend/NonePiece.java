package edu.gonzaga.Nuffatafl.backend;

public class NonePiece extends Piece {
    NonePiece() {
        team = Team.NONE;
        type = Type.NONE;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public String toString() {
        return "-";
    }
}
