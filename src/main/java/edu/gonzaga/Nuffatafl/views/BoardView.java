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

import edu.gonzaga.Nuffatafl.backend.GameManager;
import edu.gonzaga.Nuffatafl.backend.Piece;
import edu.gonzaga.Nuffatafl.backend.Position;
import edu.gonzaga.Nuffatafl.backend.Team;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class BoardView extends JPanel {
    private GameManager game;
    private ArrayList<TileView> tileViews;
    private PieceImages pieceImages;
    private PropertyChangeSupport pieceImagesChange;
    private Position primarySelection;
    private PropertyChangeSupport primarySelectionChange;
    private Position secondarySelection;
    private PropertyChangeSupport secondarySelectionChange;

    public BoardView(GameManager gameManager) {
        super();

        game = gameManager;
        primarySelection = Position.none;
        primarySelectionChange = new PropertyChangeSupport(primarySelection);
        secondarySelection = Position.none;
        secondarySelectionChange = new PropertyChangeSupport(primarySelection);

        setupLayout();
        setupTileViews();

        addComponentListener(componentListener);
        setKeyBindings();
    }

    public void attemptMove() {
        if (!game.getWinner().equals(Team.NONE)) { return; }
        if (primarySelection.isNone() || secondarySelection.isNone()) { return; }

        if (game.canAttemptMove(primarySelection) && game.getBoard().canMove(primarySelection, secondarySelection)) {
            game.movePiece(primarySelection, secondarySelection);
            deselectPieces();
        }
    }

    public void deselectPieces() {
        setPrimarySelection(Position.none);
        setSecondarySelection(Position.none);
    }

    private void setupLayout() {
        int boardSize = game.getBoard().getSize();
        GridLayout gridLayout = new GridLayout(boardSize, boardSize);
        gridLayout.setHgap(0);
        gridLayout.setVgap(0);
        setLayout(gridLayout);
    }

    private void setupTileViews() {
        int boardSize = game.getBoard().getSize();
        tileViews = new ArrayList<TileView>();
        int tileSize = Math.min(getWidth(), getHeight()) / boardSize;
        pieceImages = new PieceImages(tileSize);
        pieceImagesChange = new PropertyChangeSupport(pieceImages);

        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                TileView tileView = new TileView(game, new Position(x, y), pieceImages);
                tileView.setSize(tileSize, tileSize);

                tileView.onClick(position -> handleClick(position));
                tileView.onDoubleClick(position -> handleDoubleClick(position));

                pieceImagesChange.addPropertyChangeListener(event -> tileView.resizeImage((PieceImages) event.getNewValue()));
                primarySelectionChange.addPropertyChangeListener(event -> tileView.updateSelected(primarySelection, secondarySelection));
                secondarySelectionChange.addPropertyChangeListener(event -> tileView.updateSelected(primarySelection, secondarySelection));

                tileViews.add(tileView);
                add(tileView);
            }
        }
    }

    private void setPrimarySelection(Position position) {
        if (!game.getWinner().equals(Team.NONE)) { return; }
        Position old = primarySelection;
        primarySelection = position;
        primarySelectionChange.firePropertyChange("primarySelection", old, position);
    }

    private void setSecondarySelection(Position position) {
        if (!game.getWinner().equals(Team.NONE)) { return; }
        Position old = secondarySelection;
        secondarySelection = position;
        secondarySelectionChange.firePropertyChange("secondarySelection", old, position);
    }

    private void handleClick(Position position) {
        if (!game.getWinner().equals(Team.NONE)) { return; }
        if (primarySelection.isNone()) {
            if (game.canAttemptMove(position)) { setPrimarySelection(position); }
        } else if (game.getBoard().canMove(primarySelection, position)) {
            setSecondarySelection(position);
        }
    }

    private void handleDoubleClick(Position position) {
        if (!game.getWinner().equals(Team.NONE)) { return; }
        if (!primarySelection.isNone() && !secondarySelection.isNone() && !primarySelection.equals(position)) {
            attemptMove();
        }
    }

    private void setKeyBindings() {
        ActionMap actionMap = getActionMap();
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        String up = "up";
        String down = "down";
        String left = "left";
        String right = "right";
        String esc = "esc";
        String enter = "enter";

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), up);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_KP_UP, 0), up);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), up);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), down);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_KP_DOWN, 0), down);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), down);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), left);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_KP_LEFT, 0), left);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), left);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), right);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_KP_RIGHT, 0), right);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), right);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), enter);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), esc);


        actionMap.put(up, new KeyAction(up));
        actionMap.put(down, new KeyAction(down));
        actionMap.put(left, new KeyAction(left));
        actionMap.put(right, new KeyAction(right));
        actionMap.put(esc, new KeyAction(esc));
        actionMap.put(enter, new KeyAction(enter));
    }

    private class KeyAction extends AbstractAction {
        public KeyAction(String actionCommand) {
            putValue(ACTION_COMMAND_KEY, actionCommand);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvt) {
            String keyCode = actionEvt.getActionCommand();

            if (keyCode == "esc") {
                deselectPieces();
                return;
            } else if (keyCode == "enter") {
                attemptMove();
                return;
            }

            if (primarySelection.isNone() || !game.canAttemptMove(primarySelection)) { return; }

            Position newPosition = Position.none;
            Position initialPosition = primarySelection;
            if (!secondarySelection.isNone()) { initialPosition = secondarySelection; }

            switch (keyCode) {
                case "up":
                    newPosition = initialPosition.add(0, -1);
                    break;
                case "left":
                    newPosition = initialPosition.add(-1, 0);
                    break;
                case "down":
                    newPosition = initialPosition.add(0, 1);
                    break;
                case "right":
                    newPosition = initialPosition.add(1, 0);
                    break;
                default:
                    return;
            }

            if (primarySelection.equals(newPosition)) {
                setSecondarySelection(Position.none);
            } else if (game.getBoard().canMove(primarySelection, newPosition)) {
                setSecondarySelection(newPosition);
            }
        }
    }

    private final ComponentListener componentListener =  new ComponentListener() {
        /**
         * Called when the size of the board view changes
         * Makes sure board view maintains square aspect ratio
         * Resizes board piece images
         */
        @Override
        public void componentResized(ComponentEvent event) {
            int width = getWidth();
            int height = getHeight();

            if (height > width) {
                int padding = (height - width) / 2;
                setBorder(BorderFactory.createEmptyBorder(padding, 0, padding, 0));
            } else {
                int padding = (width - height) / 2;
                setBorder(BorderFactory.createEmptyBorder(0, padding, 0, padding));
            }

            int size = Math.min(getWidth(), getHeight()) / game.getBoard().getSize();
            pieceImages.resize(size);
            pieceImagesChange.firePropertyChange("pieceImages", null, pieceImages);
        }

        // Unused
        @Override public void componentMoved(ComponentEvent e) {}
        @Override public void componentShown(ComponentEvent e) {}
        @Override public void componentHidden(ComponentEvent e) {}
    };
}
