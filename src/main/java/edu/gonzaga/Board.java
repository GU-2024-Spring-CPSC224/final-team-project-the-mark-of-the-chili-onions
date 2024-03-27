package edu.gonzaga;

public class Board {
    private Piece[][] tiles;

    private int SIZE;

    Board(int size) {
        tiles = new Piece[size][size];
        SIZE = size;
    }

    Board() {
        this(9);
    }

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

        for (int x = 0; x < template.length; x++) {
            for (int y = 0; y < template[x].length; y++) {
                Position currentPos = new Position(x, y);
                switch (template[x][y]) {
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

    public void setPiece(Position pos, Piece piece) {
        tiles[pos.getX()][pos.getY()] = piece;
    }

    public Piece getPieceAtPosition(Position pos) {
        return tiles[pos.getX()][pos.getY()];
    }

    public boolean canMove(Position from, Position to) {
        if (from.getX() == to.getX() ^ from.getY() == to.getY()) { //XOR; only one coordinate should change
            //TODO: implement logic to check X or Y
        }

        return false;
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
