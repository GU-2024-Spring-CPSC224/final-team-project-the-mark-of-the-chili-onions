/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo
 * @version v1.0.0 04/28/2024
 */

package edu.gonzaga.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.backend.Piece;
import edu.gonzaga.Nuffatafl.backend.Team;

import javax.swing.*;

/**
 * Keeps a set of images for each piece and the x for corner pieces.
 * By keeping one copy in the same place, each image only needs to be reloaded once when the view is resized
 * as opposed to loading a new image of the correct size for each tileView.
 */
public class PieceImages {
    private ImageIcon darkPiece;
    private ImageIcon lightPiece;
    private ImageIcon kingPiece;
    private ImageIcon noPiece;
    private ImageIcon xTile;

    public PieceImages(int size) {
        resize(size);
    }

    /** Updates images to be of the input size (square aspect ratio) */
    public void resize(int size) {
        int safeSize = Math.max(1, size);
        darkPiece = ImageLoading.darkPiece(safeSize);
        lightPiece = ImageLoading.lightPiece(safeSize);
        kingPiece = ImageLoading.kingPiece(safeSize);
        noPiece = ImageLoading.clear(safeSize);
        xTile = ImageLoading.xTile(safeSize);
    }

    /** Gets the correct image for the input piece */
    public ImageIcon imageFor(Piece piece) {
        Piece.Type pieceType = piece.getType();

        if (pieceType == Piece.Type.KING) {
            return kingPiece;
        } else if (pieceType == Piece.Type.SOLDIER) {
            Team team = piece.getTeam();

            if (team == Team.ATTACKER) {
                return darkPiece;
            } else if (team == Team.DEFENDER) {
                return lightPiece;
            }
        }

        return noPiece;
    }

    /** Gets the x image for corner pieces */
    public ImageIcon getXTile() {
        return xTile;
    }
}
