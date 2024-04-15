package edu.gonzaga.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.backend.Piece;
import edu.gonzaga.Nuffatafl.backend.Team;

import javax.swing.*;

public class PieceImages {
    private ImageIcon darkPiece;
    private ImageIcon lightPiece;
    private ImageIcon kingPiece;
    private ImageIcon noPiece;
    private ImageIcon xTile;

    public PieceImages(int size) {
        resize(size);
    }

    public void resize(int size) {
        int safeSize = Math.max(1, size);
        darkPiece = ImageLoading.darkPiece(safeSize);
        lightPiece = ImageLoading.lightPiece(safeSize);
        kingPiece = ImageLoading.kingPiece(safeSize);
        noPiece = ImageLoading.clear(safeSize);
        xTile = ImageLoading.xTile(safeSize);
    }

    public ImageIcon imageFor(Piece piece) {
        Piece.Type pieceType = piece.getType();

        if (pieceType == Piece.Type.KING) {
            return kingPiece;
        } else if (pieceType == Piece.Type.SOLDIER) {
            Team team = piece.getTeam();

            if (team == Team.ATTACKER) {
                return lightPiece;
            } else if (team == Team.DEFENDER) {
                return darkPiece;
            }
        }

        return noPiece;
    }

    public ImageIcon getXTile() {
        return xTile;
    }
}
