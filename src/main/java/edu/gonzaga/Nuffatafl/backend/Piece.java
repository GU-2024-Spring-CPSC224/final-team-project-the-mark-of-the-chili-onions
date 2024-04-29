/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Cash Hilstad
 * @version v1.0.0 04/28/2024
 */

package edu.gonzaga.Nuffatafl.backend;

/** Abstract piece used for King, Soldier, and NonePiece */
public abstract class Piece {

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

    /** Returns true if the given piece is the same team as this piece */
    public boolean isAllyOf(Piece other) {
        return sameTeam(other.team);
    }

    /** Returns true if the given piece is the opposite team of this piece */
    public boolean isEnemyOf(Piece other) {
        return team != Team.NONE && other.team != Team.NONE && team != other.team;
    }

    public Team getTeam() {
        return team;
    }

    public abstract boolean isEmpty();

    public enum Type {KING, SOLDIER, NONE}
}
