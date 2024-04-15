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
import edu.gonzaga.Nuffatafl.viewHelpers.ClickHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;

public class TileView extends JPanel {
    private GameManager game;
    private Position position;
    private PieceImages pieceImages;
    private JButton button;
    private JLabel xLabel;
    private ClickHandler<Position> clickHandler;
    private ClickHandler<Position> doubleClickHandler;

    public TileView(GameManager gameManager, Position position, PieceImages pieceImages) {
        super();

        game = gameManager;
        game.onBoardChange(event -> handleBoardChange(event));

        this.position = position;
        this.pieceImages = pieceImages;

        setupLayout();
        setupCheckerColoring();
        setupButtonAndXLabel();
    }

    public void resizeImage(PieceImages newImages) {
        pieceImages = newImages;
        updatePieceImage();
    }

    public void onClick(ClickHandler<Position> clickHandler) {
        this.clickHandler = clickHandler;
    }

    public void onDoubleClick(ClickHandler<Position> clickHandler) {
        doubleClickHandler = clickHandler;
    }

    public void updateSelected(Position primary, Position secondary) {
        if (position.equals(primary)) {
            setBackground(new Color(160, 225, 255));
        } else if (position.equals(secondary)) {
            setBackground(new Color(255, 250, 160));
        } else {
            setupCheckerColoring();
        }
    }

    private void setupLayout() {
        setLayout(new OverlayLayout(this));
    }

    private void setupCheckerColoring() {
        if (position.getX() % 2 == position.getY() % 2) {
            setBackground(new Color(210, 210, 210));
        } else {
            setBackground(Color.white);
        }
    }

    private void setupButtonAndXLabel() {
        xLabel = new JLabel();
        button = new JButton();
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setSize(getSize());
        button.addMouseListener(mouseListener);
        button.setFocusPainted(false);
        updatePieceImage();
        add(button);
        add(xLabel);

    }
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

    private void handleBoardChange(PropertyChangeEvent event) {
        if (event.getOldValue().getClass() != event.getNewValue().getClass()) {
            updatePieceImage();
        }
    }

    private final MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 1) {
                clickHandler.onClick(position);
            } else {
                doubleClickHandler.onClick(position);
            }
        }

        // Unused
        @Override public void mousePressed(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e) {}
    };
}
