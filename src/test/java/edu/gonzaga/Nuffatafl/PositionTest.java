package edu.gonzaga.Nuffatafl;

import edu.gonzaga.Nuffatafl.backend.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class PositionTest {
    @Test
    void equalPositionsAreEqual() {
        Position a = new Position(1,1);
        Position b = new Position(1,1);

        Assertions.assertEquals(a, b);
    }

    @Test
    void inequalPositionsAreInequal() {
        Position a = new Position(-1,-1);
        Position b = new Position(1,1);

        Assertions.assertTrue(!a.equals(b));
    }

    @Test
    void stringRepresentationWorks() {
        Position a = new Position(10,7);
        String expected = "(10,7)";

        String actual = a.toString();

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void copyCreatesEqual() {
        Position a = new Position(1,1);
        Position b = new Position();

        b.setEqualTo(a);

        Assertions.assertEquals(a, b);
    }
}

