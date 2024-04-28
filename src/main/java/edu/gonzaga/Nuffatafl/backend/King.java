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

public class King extends Piece {
    public King() {
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
