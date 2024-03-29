package edu.gonzaga.Nuffatafl;

public abstract class Piece {
    public enum Team {ATTACKER, DEFENDER, NONE}

    public enum Type {KING, SOLDIER, NONE}

    protected Type type;

    protected Team team;

    public Type getType() {
        return type;
    }

    public Team getTeam() {
        return team;
    }

    abstract boolean isEmpty();
}
