package edu.gonzaga.Nuffatafl.views.Nuffatafl.backend;

public class Soldier extends Piece {
    public Soldier(Team team) {
        this.team = team;
        type = Type.SOLDIER;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public String toString() {
        if (team == Team.DEFENDER) {
            return "D";
        }

        if (team == Team.ATTACKER) {
            return "A";
        }

        return "S"; //no team
    }
}
