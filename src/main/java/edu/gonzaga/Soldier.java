package edu.gonzaga;

public class Soldier extends Piece {
    Soldier(Team team) {
        this.team = team;
        type = Type.SOLDIER;
    }

    @Override
    boolean isEmpty() {
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
