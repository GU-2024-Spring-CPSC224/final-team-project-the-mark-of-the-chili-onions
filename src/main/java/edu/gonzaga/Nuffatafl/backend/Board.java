package edu.gonzaga.Nuffatafl.backend;

public class Board {
    private Piece[][] tiles;

    private final int SIZE;

    static final int DEFAULT_SIZE = 9;

    public Board(int size) {
        tiles = new Piece[size][size];
        SIZE = size;

        fillWithEmpty();
    }

    public Board() {
        this(DEFAULT_SIZE);
        setupTablutBoard();
    }

    public int getSize() {return SIZE;}

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
                        setPiece(currentPos, new Soldier(Team.ATTACKER));
                        break;
                    case 2:
                        setPiece(currentPos, new Soldier(Team.DEFENDER));
                        break;
                    case 3:
                        setPiece(currentPos, new King());
                        break;
                }
            }
        }
    }

    /**
     * Checks if a position exists on the board.
     * @param pos The position to check.
     * @return True if the position exists.
     */
    public boolean isPositionOnBoard(Position pos) {
        return ((pos.getX() < SIZE) && (pos.getX() >= 0)) && ((pos.getY() < SIZE) && (pos.getY() >= 0));
    }

    /**
     * Checks if a position is at center. Only intended for odd-spaced boards.
     * @param pos The position to check.
     * @return True if the position is at the center.
     */
    public boolean isPositionCenter(Position pos) {
        return new Position(SIZE/2, SIZE/2).equals(pos);
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
    public boolean movePiece(Position from, Position to, Team team) {
        if (canMoveWithTeam(from, to, team)) {
            swapPieces(from, to);
            checkForAndHandleCapture(to);
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
    public boolean canMoveWithTeam(Position from, Position to, Team team) {
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
        if (!isPositionOnBoard(to) || isPositionCenter(to)) {
            return false;
        }

        if (from.getX() == to.getX() ^ from.getY() == to.getY()) { //XOR; only one coordinate should change
            if (from.getX() != to.getX()) {
                boolean output = xPathIsValid(from.getX(), to.getX(), from.getY());
                return output;
            }

            if (from.getY() != to.getY()) {
                boolean output = yPathIsValid(from.getY(), to.getY(), from.getX());
                return output;
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
    private boolean xPathIsValid(int from, int to, int y) {
        int start, end;
        
        if (from > to) {
            start = to;
            end = from - 1;
        } else {
            start = from + 1;
            end = to;
        }
        
        for (int x = start; x <= end; x++) {
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
    private boolean yPathIsValid(int from, int to, int x) {
        int start, end;

        if (from > to) {
            start = to;
            end = from - 1;
        } else {
            start = from + 1;
            end = to;
        }
        
        for (int y = start; y <= end; y++) {
            Piece pieceAtCoord = getPieceAtPosition(new Position(x, y));

            if (!pieceAtCoord.isEmpty()) {
                return false;
            }
        }

        return true;
    }
    
    /**
     * Checks if the position is both at the center of the board and empty
     * @param position The position to check
     * @return True if the position is empty and at the center of the board
     */
    private boolean isPositionCenterAndEmpty(Position position) {
        if (isPositionOnBoard(position)) {
            return isPositionCenter(position) && getPieceAtPosition(position).isEmpty();
        } else {
            return false;
        }
    }
    
    /**
     * Checks for and handles a capture in the up, down, left, or right direction
     * @param movedPosition The position to check from
     * @param x 1 for right, -1 for left,  0 for up, 0 for down
     * @param y 0 for right,  0 for left, -1 for up, 1 for down
     */
    private void checkForAndHandleCaptureInDirection(Position movedPosition, int x, int y) {
        if (x != 0 && y != 0) { return; }

        Position oneOver = movedPosition.add(x, y);
        Position twoOver = movedPosition.add(2 * x, 2 * y);

        if (!isPositionOnBoard(movedPosition) || !isPositionOnBoard(oneOver) || !isPositionOnBoard(twoOver)) {
            return;
        }
        
        Piece movedPiece = getPieceAtPosition(movedPosition);
        Piece oneOverPiece = getPieceAtPosition(oneOver);
        Piece twoOverPiece = getPieceAtPosition(twoOver);

        if (!movedPiece.isEnemyOf(oneOverPiece) || oneOverPiece.getType().equals(Piece.Type.KING)) { return; }
        
        if (movedPiece.isAllyOf(twoOverPiece) || isPositionCenterAndEmpty(twoOver)) {
            setPiece(oneOver, new NonePiece());
        }
    }
    
    /**
     * Checks to see if pieces should be captured, and if so, captures them.
     * @param pos The position a piece has been moved to.
     */
    private void checkForAndHandleCapture(Position pos) {
        /*
        This will be yucky, but I feel that there's no true loop method for this.
        Basic Plan:
            Check if it's even a valid spot
            If it is, check to see if there's an allied piece.
            If there is, check to see if there's an opposing piece between.
            If yes, delete.
         */

        if (!isPositionOnBoard(pos)) {
            return;
        }
        
        checkForAndHandleCaptureInDirection(pos, -1, 0);
        checkForAndHandleCaptureInDirection(pos, 1, 0);
        checkForAndHandleCaptureInDirection(pos, 0, -1);
        checkForAndHandleCaptureInDirection(pos, 0, 1);
        
        /* 
        Piece self = getPieceAtPosition(pos);        
        
        //Up direction
        if (isPositionOnBoard(pos.add(0, 2))) {
            Piece ally = getPieceAtPosition(pos.add(0, 2));
            Piece enemy = getPieceAtPosition(pos.add(0, 1));
            if ((ally.sameTeam(self.getTeam()) || isPositionCenter(pos.add(0, 2))) && !enemy.sameTeam(self.getTeam()) && !(enemy.getType() == Piece.Type.KING)) {
                setPiece(pos.add(0, 1), new NonePiece());
            }
        }

        //Down direction
        if (isPositionOnBoard(pos.add(0, -2))) {
            Piece ally = getPieceAtPosition(pos.add(0, -2));
            Piece enemy = getPieceAtPosition(pos.add(0, -1));
            if ((ally.sameTeam(self.getTeam()) || isPositionCenter(pos.add(0, -2))) && !enemy.sameTeam(self.getTeam()) && !(enemy.getType() == Piece.Type.KING)) {
                setPiece(pos.add(0, -1), new NonePiece());
            }
        }

        //Right direction
        if (isPositionOnBoard(pos.add(2, 0))) {
            Piece ally = getPieceAtPosition(pos.add(2, 0));
            Piece enemy = getPieceAtPosition(pos.add(1, 0));
            if ((ally.sameTeam(self.getTeam()) || isPositionCenter(pos.add(2, 0))) && !enemy.sameTeam(self.getTeam()) && !(enemy.getType() == Piece.Type.KING)) {
                setPiece(pos.add(1, 0), new NonePiece());
            }
        }

        //Left direction
        if (isPositionOnBoard(pos.add(-2, 0))) {
            Piece ally = getPieceAtPosition(pos.add(-2, 0));
            Piece enemy = getPieceAtPosition(pos.add(-1, 0));
            if ((ally.sameTeam(self.getTeam()) || isPositionCenter(pos.add(-2, 0))) && !enemy.sameTeam(self.getTeam()) && !(enemy.getType() == Piece.Type.KING)) {
                setPiece(pos.add(-1, 0), new NonePiece());
            }
        }
        */
    }

    /**
     * Checks if the piece at the given position causes a win for the Defenders.
     * @param pos The position to check.
     * @return True if there is a win.
     */
    public boolean isDefenderWin(Position pos) {
        Piece pieceAtPos = getPieceAtPosition(pos);

        if (pieceAtPos.getType() != Piece.Type.KING) {
            return false;
        }

        if (
                (pos.getX() == 0 || pos.getX() == SIZE - 1) || //If on x border or
                (pos.getY() == 0 || pos.getY() == SIZE - 1) // If on y border
        ) {
            return true;
        }

        return false;
    }

    /**
     * Checks the whole board to see if the King is surrounded by Attackers.
     * @return True if the Attackers have won.
     */
    public boolean isAttackerWin() {
        // We must find the king... thus, we embark on a quest...
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                Position pos = new Position(x, y);

                Piece pieceAtPos = getPieceAtPosition(pos);

                if (pieceAtPos.getType() == Piece.Type.KING) { //Hurrah! We have found the king!
                    Position up = pos.add(new Position(0, 1));
                    Position down = pos.add(new Position(0, -1));
                    Position left = pos.add(new Position(-1, 0));
                    Position right = pos.add(new Position(1, 0));

                    //If any of these are off the board, then the Attackers can't win.
                    if(!isPositionOnBoard(up) || !isPositionOnBoard(down) || !isPositionOnBoard(left) || !isPositionOnBoard(right)) {
                        return false;
                    }

                    //If all of these are Attackers, we're done!
                    if(
                            isPositionCenterOrSameSide(up, Team.ATTACKER) &&
                            isPositionCenterOrSameSide(down, Team.ATTACKER) &&
                            isPositionCenterOrSameSide(left, Team.ATTACKER) &&
                            isPositionCenterOrSameSide(right, Team.ATTACKER)
                    ) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Helper function for isAttackerWin.
     * @param pos Position to check.
     * @param team Team to check.
     * @return True if piece at pos is same team or pos is center.
     */
    public boolean isPositionCenterOrSameSide(Position pos, Team team) {
        return getPieceAtPosition(pos).sameTeam(team) || isPositionCenter(pos);
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
