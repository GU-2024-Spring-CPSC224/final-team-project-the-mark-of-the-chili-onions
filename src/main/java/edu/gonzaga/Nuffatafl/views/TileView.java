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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;

/** View for a single tile on the gameboard */
public class TileView extends JPanel implements MouseListener {
    private GameManager game;
    private Position position;
    private PieceImages pieceImages;

    /** Displays the piece on this tile, if any */
    private JButton button;

    /** Displays an x if on a corner tile */
    private JLabel xLabel;

    /** Called when a click occurs on this tile */
    private EventCallback<Position> clickHandler;

    /** Called when this tile is double clicked */
    private EventCallback<Position> doubleClickHandler;

    public TileView(GameManager gameManager, Position position, PieceImages pieceImages) {
        super();

        game = gameManager;
        game.onBoardChange(event -> handleBoardChange(event));

        this.position = position;
        this.pieceImages = pieceImages;

        setupLayout();
        setupCheckerColoring();
        setupButtonAndXLabel();  
        
        addMouseListener(this);
    }

    /** Gets resied images and updates the piece and x image */
    public void resizeImage(PieceImages newImages) {
        pieceImages = newImages;
        updatePieceImage();
    }

    /** Sets the action to be performed when this tile is clicked */
    public void onClick(EventCallback<Position> clickHandler) {
        this.clickHandler = clickHandler;
    }

    /** Sets the action to be performed when this tile is double clicked */
    public void onDoubleClick(EventCallback<Position> clickHandler) {
        doubleClickHandler = clickHandler;
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
        xLabel = new JLabel();

        button = new JButton();
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setSize(getSize());
        button.addMouseListener(this);
        button.setFocusPainted(false);

        updatePieceImage();

        add(button);
        add(xLabel);
        xLabel.addMouseListener(this);
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

    /** Updates image when the information for the tile at this position changes */
    private void handleBoardChange(PropertyChangeEvent event) {
        if (event.getOldValue().getClass() != event.getNewValue().getClass()) {
            updatePieceImage();
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1) {
            clickHandler.action(position);
        } else {
            doubleClickHandler.action(position);
        }
    }

    // Unused
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
