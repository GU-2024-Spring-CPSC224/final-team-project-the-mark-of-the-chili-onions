package edu.gonzaga.Nuffatafl.backend;

/**
 * @author Cash Hilstad
 * A class that represents a two-dimensional position.
 */
public class Position {
    private int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position() {
        this(0, 0);
    }

    /**
     * @return The position's x value.
     */
    public int getX() {
        return x;
    }

    /**
     * @return The position's y value.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets x to the passed value.
     * @param x The value to set x to.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets y to the passed value.
     * @param y The value to set y to.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Copies the value of another Position.
     * @param pos The Position to copy from.
     */
    public void setEqualTo(Position pos) {
        setX(pos.getX());
        setY(pos.getY());
    }

    /**
     * @return String representation of the Position.
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    /**
     * @param o The object to check for equality.
     * @return True if and only if this Position and the object have the same values.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (getClass() != o.getClass()) {
            return false;
        }

        Position position = (Position) o;

        return x == position.x && y == position.y;
    }
}
