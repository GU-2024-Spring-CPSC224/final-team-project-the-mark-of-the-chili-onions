package edu.gonzaga.Nuffatafl;

public class Board {
    private Piece[][] tiles;

    private int SIZE;

    Board(int size) {
        tiles = new Piece[size][size];
        SIZE = size;

        fillWithEmpty();
    }

    Board() {
        this(9);
        setupTablutBoard();
    }

    /**
     * Fills the board with NonePieces.
     */
    private void fillWithEmpty() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                Position currentPos = new Position(x, y);
                setPiece(currentPos, new NonePiece());
            }
        }
    }

    /**
     * Fills the board with a Tablut layout. Requires a board of size 9+.
     */
    public void setupTablutBoard() {
        int[][] template = {
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 2, 0, 0, 0, 0},
                {1, 0, 0, 0, 2, 0, 0, 0, 1},
                {1, 1, 2, 2, 3, 2, 2, 1, 1},
                {1, 0, 0, 0, 2, 0, 0, 0, 1},
                {0, 0, 0, 0, 2, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
        };

        /*
        Key:
            0 = None
            1 = Attacker
            2 = Defender
            3 = King
         */

        /*
        Important note: in the 2d array, things are (y,x), inverse of usual notation
         */
        for (int y = 0; y < template.length; y++) {
            for (int x = 0; x < template[y].length; x++) {
                Position currentPos = new Position(x, y);
                switch (template[y][x]) {
                    case 0:
                        setPiece(currentPos, new NonePiece());
                        break;
                    case 1:
                        setPiece(currentPos, new Soldier(Piece.Team.ATTACKER));
                        break;
                    case 2:
                        setPiece(currentPos, new Soldier(Piece.Team.DEFENDER));
                        break;
                    case 3:
                        setPiece(currentPos, new King());
                        break;
                }
            }
        }
    }

    /**
     * Add a piece to the board at a given position.
     * @param pos The position to add it to.
     * @param piece The piece to add.
     */
    public void setPiece(Position pos, Piece piece) {
        tiles[pos.getY()][pos.getX()] = piece;
    }

    /**
     * Swaps the two pieces at position from and to.
     * @param from The first position.
     * @param to The second position.
     */
    public void swapPieces(Position from, Position to) {
        Piece fromPiece = getPieceAtPosition(from);
        Piece toPiece = getPieceAtPosition(to);

        setPiece(to, fromPiece);
        setPiece(from, toPiece);
    }

    /**
     * Returns the piece at a given position.
     * @param pos The position to check.
     * @return The piece at the position.
     */
    public Piece getPieceAtPosition(Position pos) {
        return tiles[pos.getY()][pos.getX()];
    }

    /**
     * Moves a piece from to if possible. Returns false if not possible.
     * @param from The position to move from.
     * @param to The position to move to.
     * @param team The team of the attempted move.
     * @return True on success, false on failure.
     */
    public boolean movePiece(Position from, Position to, Piece.Team team) {
        if (canMoveWithTeam(from, to, team)) {
            swapPieces(from, to);
            return true;
        }

        return false;
    }

    /**
     * Checks if movement from to is possible, taking into account team.
     * @param from The position to move from.
     * @param to The position to move to.
     * @param team The team of the attempted move.
     * @return True if movement is possible.
     */
    public boolean canMoveWithTeam(Position from, Position to, Piece.Team team) {
        boolean isCorrectTeam = getPieceAtPosition(from).getTeam() == team;
        return (isCorrectTeam && canMove(from, to));
    }

    /**
     * Checks if movement from to is possible.
     * @param from The position to move from.
     * @param to The position to move to.
     * @return True if movement is possible.
     */
    public boolean canMove(Position from, Position to) {
        if (to.getX() >= SIZE || to.getY() >= SIZE) {
            return false;
        }

        if (from.getX() == to.getX() ^ from.getY() == to.getY()) { //XOR; only one coordinate should change

            if (from.getX() != to.getX()) {
                return isThereCollisionAlongX(from.getX(), to.getX(), from.getY());
            }

            if (from.getY() != to.getY()) {
                return isThereCollisionAlongY(from.getY(), to.getY(), from.getX());
            }
        }

        return false;
    }

    /**
     * @param from The starting position.
     * @param to The ending position.
     * @param y The y coordinate to work within.
     * @return True if there is nothing to collide with between from and to along y.
     */
    private boolean isThereCollisionAlongX(int from, int to, int y) {
        if (from > to) {
            int temp = to;
            to = from;
            from = to;
            to = temp;
        }

        for (int x = from + 1; x <= to; x++) {
            Piece pieceAtCoord = getPieceAtPosition(new Position(x, y));
            if (!pieceAtCoord.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    /**
     * @param from The starting position.
     * @param to The ending position.
     * @param x The x coordinate to work within.
     * @return True if there is nothing to collide with between from and to along x.
     */
    private boolean isThereCollisionAlongY(int from, int to, int x) {
        if (from > to) {
            int temp = to;
            to = from;
            from = to;
            to = temp;
        }

        for (int y = from + 1; y <= to; y++) {
            Piece pieceAtCoord = getPieceAtPosition(new Position(x, y));
            if (!pieceAtCoord.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        String result = "";

        for (Piece[] pieces : tiles) {
            for (Piece piece : pieces) {
                result += piece.toString();
            }
            result += "\n";
        }

        return result;
    }
}
