/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo
 * @version v1.0.0 04/28/2024
 */

package edu.gonzaga.Nuffatafl.backend;

/** Info about an individual turn, used in turnHistory */
public class Turn {
    public Player player;
    public Position from;
    public Position to;
    public Integer capturedPieceCount;

    public Turn(Player player, Position from, Position to, Integer capturedPieceCount) {
        this.player = player;
        this.from = from;
        this.to = to;
        this.capturedPieceCount = capturedPieceCount;
    }
}
