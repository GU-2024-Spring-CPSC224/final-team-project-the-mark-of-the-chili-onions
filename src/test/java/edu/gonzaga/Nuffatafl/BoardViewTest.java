/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo
 * @version v0.1.0 04/08/2024
 */

package edu.gonzaga.Nuffatafl;

import edu.gonzaga.Nuffatafl.backend.GameManager;
import edu.gonzaga.Nuffatafl.backend.Position;
import edu.gonzaga.Nuffatafl.backend.Team;
import edu.gonzaga.Nuffatafl.views.BoardView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardViewTest {
    @Test
    public void setSourcePositionTest() {
        BoardView view = new BoardView(new GameManager());
        Position position = new Position(1, 4);
        view.setSourcePosition(position);
        Assertions.assertTrue(view.getSourcePosition().equals(position));
    }
    
    @Test
    public void setDestinationPositionTest() {
        BoardView view = new BoardView(new GameManager());
        Position position = new Position(1, 4);
        view.setDestinationPosition(position);
        Assertions.assertTrue(view.getDestinationPosition().equals(position));
    }
    
    @Test
    public void handleClickSetSourcePositionTest() {
        BoardView view = new BoardView(new GameManager());
        Position position = new Position(1, 4);
        view.handleClick(position);
        Assertions.assertTrue(view.getSourcePosition().equals(position));
        Assertions.assertTrue(view.getDestinationPosition().isNone());
    }
    
    @Test
    public void handleClickSetInvalidSourcePositionTest() {
        BoardView view = new BoardView(new GameManager());
        Position position = new Position(4, 4);
        view.handleClick(position);
        Assertions.assertTrue(view.getSourcePosition().isNone());
        Assertions.assertTrue(view.getDestinationPosition().isNone());
    }
    
    @Test
    public void handleClickSetOutOfBoundsSourcePositionTest() {
        BoardView view = new BoardView(new GameManager());
        Position position = new Position(-4, -4);
        view.handleClick(position);
        Assertions.assertTrue(view.getSourcePosition().isNone());
        Assertions.assertTrue(view.getDestinationPosition().isNone());
    }
    
    @Test
    public void handleClickDeselectSourcePositionTest() {
        BoardView view = new BoardView(new GameManager());
        Position position = new Position(1, 4);
        view.handleClick(position);
        view.handleClick(position);
        Assertions.assertTrue(view.getSourcePosition().isNone());
        Assertions.assertTrue(view.getDestinationPosition().isNone());
    }
    
    /*@Test
    public void handleClickSetDestinationPositionTest() {
        BoardView view = new BoardView(new GameManager());
        Position position1 = new Position(1, 4);
        Position position2 = new Position(1, 0);
        view.handleClick(position1);
        view.handleClick(position2);
        Assertions.assertTrue(view.getSourcePosition().equals(position1));
        Assertions.assertTrue(view.getDestinationPosition().equals(position2));
    }*/
    
    @Test
    public void handleClickSetInvalidDestinationPositionTest() {
        BoardView view = new BoardView(new GameManager());
        Position position1 = new Position(1, 4);
        Position position2 = new Position(4, 4);
        view.handleClick(position1);
        view.handleClick(position2);
        Assertions.assertTrue(view.getSourcePosition().equals(position1));
        Assertions.assertTrue(view.getDestinationPosition().isNone());
    }
    
    @Test
    public void handleClickSetOutOfBoundsDestinationPositionTest() {
        BoardView view = new BoardView(new GameManager());
        Position position1 = new Position(1, 4);
        Position position2 = new Position(-4, -4);
        view.handleClick(position1);
        view.handleClick(position2);
        Assertions.assertTrue(view.getSourcePosition().equals(position1));
        Assertions.assertTrue(view.getDestinationPosition().isNone());
    }
    
    /*@Test
    public void handleClickDeselectDestinationPositionTest() {
        BoardView view = new BoardView(new GameManager());
        Position position1 = new Position(1, 4);
        Position position2 = new Position(1, 0);
        view.handleClick(position1);
        view.handleClick(position2);
        view.handleClick(position2);
        Assertions.assertTrue(view.getSourcePosition().equals(position1));
        Assertions.assertTrue(view.getDestinationPosition().isNone());
    }*/
    
    @Test
    public void attemptMoveTest() {
        GameManager g = new GameManager();
        BoardView view = new BoardView(g);
        Position from = new Position(1, 4);
        Position to = new Position(1, 0);
        view.handleClick(from);
        view.handleClick(to);
        view.attemptMove();
        Assertions.assertTrue(g.getBoard().getPieceAtPosition(from).isEmpty());
        Assertions.assertTrue(g.getBoard().getPieceAtPosition(to).sameTeam(Team.ATTACKER));
    }
}