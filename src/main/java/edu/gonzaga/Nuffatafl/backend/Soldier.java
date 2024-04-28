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
