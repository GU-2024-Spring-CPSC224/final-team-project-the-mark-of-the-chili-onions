package edu.gonzaga;

public class King extends Piece {
    King() {
        team = Team.DEFENDER;
        type = Type.KING;
    }

    @Override
    boolean isEmpty() {
        return false;
    }

    @Override
    public String toString() {
        return "K";
    }
}
