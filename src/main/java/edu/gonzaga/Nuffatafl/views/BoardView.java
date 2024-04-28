/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo
 * @version v1.0.0 03/28/2024
 */

package edu.gonzaga.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.backend.GameManager;
import edu.gonzaga.Nuffatafl.backend.Position;
import edu.gonzaga.Nuffatafl.backend.Team;
import edu.gonzaga.Nuffatafl.viewHelpers.Theme;
import edu.gonzaga.Nuffatafl.viewHelpers.ThemeComponent;
import edu.gonzaga.Nuffatafl.viewNavigation.KeyCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/** Board JPanel that goes inside the gameplay screen */
public class BoardView extends JPanel {
    private final GameManager game;
    private final PropertyChangeSupport sourcePositionChangeSupport;
    private final PropertyChangeSupport destinationPositionChangeSupport;
    private final PropertyChangeSupport validSpotHighlightChangeSupport;
    private ArrayList<TileView> tileViews;
    /** Keeps images for each type of piece */
    private PieceImages pieceImages;
    private PropertyChangeSupport pieceImagesChange;
    /**
     * Maintains square aspect ratio of game board and loads new images of the correct size
     * for the tileViews when this JFrame is resized
     */
    private final ComponentListener componentListener = new ComponentListener() {
        /**
         * Called when the size of the board view changes
         * Makes sure board view maintains square aspect ratio
         * Resizes board piece images
         */
        @Override
        public void componentResized(ComponentEvent event) {
            int width = getWidth();
            int height = getHeight();

            // Keep square aspect ratio for game board
            if (height > width) {
                int padding = (height - width) / 2;
                setBorder(BorderFactory.createEmptyBorder(padding, 0, padding, 0));
            } else {
                int padding = (width - height) / 2;
                setBorder(BorderFactory.createEmptyBorder(0, padding, 0, padding));
            }

            // Resize images and notify tileViews of change
            int size = Math.min(getWidth(), getHeight()) / game.getBoard().getSize();
            pieceImages.resize(size);
            pieceImagesChange.firePropertyChange("pieceImages", null, pieceImages);
        }

        // Unused
        @Override
        public void componentMoved(ComponentEvent e) {
        }

        @Override
        public void componentShown(ComponentEvent e) {
        }

        @Override
        public void componentHidden(ComponentEvent e) {
        }
    };
    /** Keeps track of the position of the piece to move */
    private Position sourcePosition;
    /** Keeps track of the position to move the moveFrom piece to */
    private Position destinationPosition;
    /**
     * Keeps track of where to highlight for possible movement.
     */
    private Position highlightPosition;

    public BoardView(GameManager gameManager) {
        super();

        // Set up manager and default values for pieces to move
        // Can't use null for moveFrom and moveTo because PropertyChangeSupport can't take a null object
        // Instead use Position.none
        game = gameManager;
        sourcePosition = Position.none;
        sourcePositionChangeSupport = new PropertyChangeSupport(sourcePosition);
        destinationPosition = Position.none;
        destinationPositionChangeSupport = new PropertyChangeSupport(sourcePosition);
        highlightPosition = Position.none;
        validSpotHighlightChangeSupport = new PropertyChangeSupport(highlightPosition);

        // Setup UI
        setupLayout();
        setupTileViews();

        // Handle changes to the size of this JPanel
        addComponentListener(componentListener);

        // Handle relevant key presses
        setKeyBindings();

        Theme.setBackgroundFor(this, ThemeComponent.background);
    }

    public Position getSourcePosition() {
        return sourcePosition;
    }

    /** Sets the sourcePosition if input position is valid and game is not over, updates observers */
    public void setSourcePosition(Position position) {
        Position old = sourcePosition;
        sourcePosition = position;
        sourcePositionChangeSupport.firePropertyChange("primarySelection", old, position);
    }

    public Position getDestinationPosition() {
        return destinationPosition;
    }

    /** Sets the destinationPosition if input position is valid and game is not over, updates observers */
    public void setDestinationPosition(Position position) {
        Position old = destinationPosition;
        destinationPosition = position;
        destinationPositionChangeSupport.firePropertyChange("secondarySelection", old, position);
    }

    /** Moves piece from sourcePosition to destinationPosition if that is a valid move */
    public void attemptMove() {
        if (!game.getWinner().equals(Team.NONE)) {
            return;
        }
        if (sourcePosition.isNone() || destinationPosition.isNone()) {
            return;
        }

        if (game.canAttemptMove(sourcePosition) && game.getBoard().canMove(sourcePosition, destinationPosition)) {
            game.movePiece(sourcePosition, destinationPosition);
            deselectPieces();
        }
    }

    /** Set the source and destination positions to none */
    public void deselectPieces() {
        setSourcePosition(Position.none);
        setDestinationPosition(Position.none);
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
                // Make a nw tile view at the given position
                TileView tileView = new TileView(game, new Position(x, y), pieceImages);
                tileView.setSize(tileSize, tileSize);

                // Handle when user clicks or double-clicks on tile
                tileView.onClick(position -> handleClick(position));

                // Inform tileView when piece images or source or destination pieces change
                pieceImagesChange.addPropertyChangeListener(event -> {
                    tileView.resizeImage((PieceImages) event.getNewValue());
                });

                sourcePositionChangeSupport.addPropertyChangeListener(event -> {
                    tileView.updateSelected(sourcePosition, destinationPosition);
                });

                destinationPositionChangeSupport.addPropertyChangeListener(event -> {
                    tileView.updateSelected(sourcePosition, destinationPosition);
                });

                validSpotHighlightChangeSupport.addPropertyChangeListener(event -> {
                    tileView.highlight(highlightPosition);
                });

                // Add the tile view and keep track of it
                tileViews.add(tileView);
                add(tileView);
            }
        }
    }

    /**
     * Highlights all valid move positions on the board.
     *
     * @param position The position to move from.
     */
    public void highlightValidPositions(Position position) {
        ArrayList<Position> validSpots = game.getValidMoveSpots(position);
        for (Position newPosition : validSpots) {
            highlightPosition = newPosition;
            validSpotHighlightChangeSupport.firePropertyChange("secondarySelection", position, highlightPosition);
        }
    }

    /** handles when a user clicks on one of the tileViews */
    public void handleClick(Position position) {
        // If the game is over, do nothing
        // If no sourcePosition, set sourcePosition to position
        // If sourcePosition == position, deselect it
        // If there is a sourcePosition but no destinationPosition, set the destinationPosition to position
        // If destinationPosition == position, deselect it

        if (!game.getWinner().equals(Team.NONE) || !game.getBoard().isPositionOnBoard(position)) {
        } else if (sourcePosition.isNone()) {
            if (game.canAttemptMove(position)) {
                setSourcePosition(position);
                highlightValidPositions(position);
            }
        } else if (position.equals(sourcePosition)) {
            setSourcePosition(Position.none);
            setDestinationPosition(Position.none);
        } else if (destinationPosition.isNone()) {
            if (game.getBoard().canMove(sourcePosition, position)) {
                setDestinationPosition(position);
                attemptMove(); //I think this was all that is needed for on-click movement
            }
        } else if (position.equals(destinationPosition)) {
            setDestinationPosition(Position.none);
        }
    }

    /** Handle when a user presses certain keys on the keyboard (WASD, arrow keys, number pad arrow keys, esc, enter) */
    private void setKeyBindings() {
        ActionMap actionMap = getActionMap();
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        mapActionsToKeys(actionMap, inputMap, KeyCode.up, KeyEvent.VK_UP, KeyEvent.VK_KP_UP, KeyEvent.VK_W);
        mapActionsToKeys(actionMap, inputMap, KeyCode.down, KeyEvent.VK_DOWN, KeyEvent.VK_KP_DOWN, KeyEvent.VK_S);
        mapActionsToKeys(actionMap, inputMap, KeyCode.left, KeyEvent.VK_LEFT, KeyEvent.VK_KP_LEFT, KeyEvent.VK_A);
        mapActionsToKeys(actionMap, inputMap, KeyCode.right, KeyEvent.VK_RIGHT, KeyEvent.VK_KP_RIGHT, KeyEvent.VK_D);
        mapActionsToKeys(actionMap, inputMap, KeyCode.enter, KeyEvent.VK_ENTER);
        mapActionsToKeys(actionMap, inputMap, KeyCode.esc, KeyEvent.VK_ESCAPE);
    }

    /** Sets up a key binding (helper function for setKeyBindings) */
    private void mapActionsToKeys(ActionMap actionMap, InputMap inputMap, KeyCode keyCode, int... vals) {
        for (int val : vals) {
            inputMap.put(KeyStroke.getKeyStroke(val, 0), keyCode);
        }

        actionMap.put(keyCode, new KeyAction(keyCode.name()));
    }

    /** Handles a keypress */
    private class KeyAction extends AbstractAction {
        public KeyAction(String actionCommand) {
            putValue(ACTION_COMMAND_KEY, actionCommand);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvt) {
            // Deselect source and destination tiles on esc
            // Attempt to move source to destination on enter
            // Modify position of destination on arrow keys/WASD

            KeyCode keyCode = KeyCode.valueOf(actionEvt.getActionCommand());

            if (keyCode == KeyCode.esc) {
                deselectPieces();
                return;
            } else if (keyCode == KeyCode.enter) {
                attemptMove();
                return;
            }

            if (sourcePosition.isNone() || !game.canAttemptMove(sourcePosition)) {
                return;
            }

            Position newDestinationPosition = destinationPosition;
            if (newDestinationPosition.isNone()) {
                newDestinationPosition = sourcePosition;
            }

            switch (keyCode) {
                case up -> newDestinationPosition = newDestinationPosition.add(0, -1);
                case down -> newDestinationPosition = newDestinationPosition.add(0, 1);
                case left -> newDestinationPosition = newDestinationPosition.add(-1, 0);
                case right -> newDestinationPosition = newDestinationPosition.add(1, 0);
                default -> {
                    return;
                }
            }

            if (sourcePosition.equals(newDestinationPosition)) {
                setDestinationPosition(Position.none);
            } else if (game.getBoard().canMove(sourcePosition, newDestinationPosition)) {
                setDestinationPosition(newDestinationPosition);
            }
        }
    }
}
