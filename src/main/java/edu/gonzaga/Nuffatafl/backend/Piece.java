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
    
    public boolean isAllyOf(Piece other) {
        return sameTeam(other.team);
    }
    
    public boolean isEnemyOf(Piece other) {
        return team != Team.NONE && other.team != Team.NONE && team != other.team; 
    }

    public Team getTeam() {
        return team;
    }

    public abstract boolean isEmpty();
}
