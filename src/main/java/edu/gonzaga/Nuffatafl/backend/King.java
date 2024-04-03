package edu.gonzaga.Nuffatafl.backend;

public class King extends Piece {
    King() {
        team = Team.DEFENDER;
        type = Type.KING;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public String toString() {
        return "K";
    }
}
