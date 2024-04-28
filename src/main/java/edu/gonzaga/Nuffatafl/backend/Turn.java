package edu.gonzaga.Nuffatafl.backend;

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
