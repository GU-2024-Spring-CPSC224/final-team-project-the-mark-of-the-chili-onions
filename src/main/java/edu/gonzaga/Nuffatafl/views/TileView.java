/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo
 * @version v0.1.0 03/28/2024
 */

package edu.gonzaga.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.backend.*;
import edu.gonzaga.Nuffatafl.viewHelpers.EventCallback;
import edu.gonzaga.Nuffatafl.viewHelpers.Theme;
import edu.gonzaga.Nuffatafl.viewHelpers.ThemeComponent;

import javax.swing.*;

/** View for a single tile on the gameboard */
public class TileView extends JPanel {
    private GameManager game;
    private Position position;
    private PieceImages pieceImages;

    /** Displays the piece on this tile, if any */
    private JButton button;

    /** Displays an x if on a corner tile */
    private JLabel xLabel;

    public TileView(GameManager gameManager, Position position, PieceImages pieceImages) {
        super();

        game = gameManager;
        game.onBoardChange(event -> updatePieceImage());

        this.position = position;
        this.pieceImages = pieceImages;

        setupLayout();
        setupCheckerColoring();
        setupButtonAndXLabel();
    }

    /** Gets resied images and updates the piece and x image */
    public void resizeImage(PieceImages newImages) {
        pieceImages = newImages;
        updatePieceImage();
    }

    /** Sets the action to be performed when this tile is clicked */
    public void onClick(EventCallback<Position> clickHandler) {
        this.button.addActionListener(event -> {
            clickHandler.action(position);
        });
    }

    /** Updates the background color when the boardVIew's source or destination positions are changed */
    public void updateSelected(Position source, Position destination) {
        if (position.equals(source)) {
            Theme.setBackgroundFor(this, ThemeComponent.accent);
        } else if (position.equals(destination)) {
            Theme.setBackgroundFor(this, ThemeComponent.accent2);
        } else {
            setupCheckerColoring();
        }
    }

    private void setupLayout() {
        setLayout(new OverlayLayout(this));
    }

    /** Colors the background of the square according to its corresponding color on a checkerboard */
    private void setupCheckerColoring() {
        if (position.getX() % 2 == position.getY() % 2) {
            Theme.setBackgroundFor(this, ThemeComponent.checkerOn);
        } else {
            Theme.setBackgroundFor(this, ThemeComponent.chekcerOff);
        }
    }

    /** Sets up the piece image and the x for corner pieces */
    private void setupButtonAndXLabel() {
        button = new JButton();
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setSize(getSize());
        button.setContentAreaFilled(false);
        
        xLabel = new JLabel();
        add(button);
        add(xLabel);

        updatePieceImage();
    }

    /** Updates the image for the piece and x from the current image assets */
    private void updatePieceImage() {
        button.setIcon(pieceImages.imageFor(game.getBoard().getPieceAtPosition(position)));
        if (isCornerPiece()) { xLabel.setIcon(pieceImages.getXTile()); }
    }

    private boolean isCornerPiece() {
        int x = position.getX();
        int y = position.getY();
        int size = game.getBoard().getSize() - 1;
        return (x == 0 && y ==  0) || (x == 0 && y == size) || (x == size && y ==  0) || (x == size && y == size);
    }
}
