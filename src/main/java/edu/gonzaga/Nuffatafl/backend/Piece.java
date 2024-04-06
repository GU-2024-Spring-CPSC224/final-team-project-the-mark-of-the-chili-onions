package edu.gonzaga.Nuffatafl.backend;

public abstract class Piece {

    public enum Type {KING, SOLDIER, NONE}

    protected Type type;

    protected Team team;

    public Type getType() {
        return type;
    }

    public boolean sameTeam(Team team) {
        if (team == Team.NONE) {
            return false;
        }

        return team == this.team;
    }

    public Team getTeam() {
        return team;
    }

    public abstract boolean isEmpty();
}
