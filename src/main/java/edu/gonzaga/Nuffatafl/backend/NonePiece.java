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

public class NonePiece extends Piece {
    NonePiece() {
        team = Team.NONE;
        type = Type.NONE;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public String toString() {
        return "-";
    }
}
