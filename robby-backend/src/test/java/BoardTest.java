import com.gma.robby.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BoardTest {

    @Mock
    Robby robot = mock(Robby.class);

    @Test
    public void testAllWalls() {
        Board board = Board.getRandom(1, 1, 0);
        Situation situation = board.getSituationAt(new Position(0, 0));
        Content[] actual = situation.getContents();
        Content[] expected = {Content.WALL, Content.WALL, Content.WALL, Content.WALL, Content.EMPTY};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testAllWallsAndCan() {
        Board board = Board.getRandom(1, 1, 1);
        Situation situation = board.getSituationAt(new Position(0, 0));
        Content[] actual = situation.getContents();
        Content[] expected = {Content.WALL, Content.WALL, Content.WALL, Content.WALL, Content.CAN};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testCorners() {
        Board board = Board.getRandom(2, 2, 0);

        validateContent(board, 0, 0,
                new Content[]{Content.WALL, Content.EMPTY, Content.EMPTY, Content.WALL, Content.EMPTY});

        validateContent(board, 1, 0,
                new Content[]{Content.WALL, Content.EMPTY, Content.WALL, Content.EMPTY, Content.EMPTY});

        validateContent(board, 1, 1,
                new Content[]{Content.EMPTY, Content.WALL, Content.WALL, Content.EMPTY, Content.EMPTY});

        validateContent(board, 0, 1,
                new Content[]{Content.EMPTY, Content.WALL, Content.EMPTY, Content.WALL, Content.EMPTY});

        board = Board.getRandom(2, 2, 4);

        validateContent(board, 0, 0,
                new Content[]{Content.WALL, Content.CAN, Content.CAN, Content.WALL, Content.CAN});

        validateContent(board, 1, 0,
                new Content[]{Content.WALL, Content.CAN, Content.WALL, Content.CAN, Content.CAN});

        validateContent(board, 1, 1,
                new Content[]{Content.CAN, Content.WALL, Content.WALL, Content.CAN, Content.CAN});

        validateContent(board, 0, 1,
                new Content[]{Content.CAN, Content.WALL, Content.CAN, Content.WALL, Content.CAN});

        var result = board.tryRemoveCan(new Position(0, 0));
        assertTrue(result);

        validateContent(board, 0, 0,
                new Content[]{Content.WALL, Content.CAN, Content.CAN, Content.WALL, Content.EMPTY});

        validateContent(board, 1, 0,
                new Content[]{Content.WALL, Content.CAN, Content.WALL, Content.EMPTY, Content.CAN});

        validateContent(board, 1, 1,
                new Content[]{Content.CAN, Content.WALL, Content.WALL, Content.CAN, Content.CAN});

        validateContent(board, 0, 1,
                new Content[]{Content.EMPTY, Content.WALL, Content.CAN, Content.WALL, Content.CAN});

        var result2 = board.tryRemoveCan(new Position(0, 0));
        assertFalse(result2);

        var result3 = board.tryRemoveCan(new Position(1, 1));
        assertTrue(result3);

        validateContent(board, 0, 0,
                new Content[]{Content.WALL, Content.CAN, Content.CAN, Content.WALL, Content.EMPTY});

        validateContent(board, 1, 0,
                new Content[]{Content.WALL, Content.EMPTY, Content.WALL, Content.EMPTY, Content.CAN});

        validateContent(board, 1, 1,
                new Content[]{Content.CAN, Content.WALL, Content.WALL, Content.CAN, Content.EMPTY});

        validateContent(board, 0, 1,
                new Content[]{Content.EMPTY, Content.WALL, Content.EMPTY, Content.WALL, Content.CAN});

    }

    @Test
    public void allSituationsAreUnique() {
        HashSet<String> setOfHashCodes = new HashSet<>();
        for (int i = 0; i < Genome.length; i++) {
            Situation situation = new Situation(i);
            Content[] contents = situation.getContents();
            String text = Arrays.toString(contents);
            setOfHashCodes.add(text);
            System.out.println(text);
        }
        assertEquals(Genome.length, setOfHashCodes.size());
    }

    @Test
    public void testScore() {
        Genome genome = mock(Genome.class);

        when(genome.getAction(any(Situation.class))).thenReturn(Action.MoveEast);
        Board board = Board.getRandom(10, 10, 100);
        Robby robby = new Robby(genome);

        for (int i = 0; i < 9; i++) {
            robby.move(board);
        }

        assertEquals(0, robby.getPointCount());
        assertEquals(new Position(9, 0), robby.getPosition());

        robby.move(board);

        assertEquals(-5, robby.getPointCount());
        assertEquals(new Position(9, 0), robby.getPosition());

        when(genome.getAction(any(Situation.class))).thenReturn(Action.MoveSouth);
        for (int i = 0; i < 9; i++) {
            robby.move(board);
        }

        assertEquals(-5, robby.getPointCount());
        assertEquals(new Position(9, 9), robby.getPosition());

        robby.move(board);

        assertEquals(-10, robby.getPointCount());
        assertEquals(new Position(9, 9), robby.getPosition());

        //----------------
        when(genome.getAction(any(Situation.class))).thenReturn(Action.MoveWest);
        for (int i = 0; i < 9; i++) {
            robby.move(board);
        }

        assertEquals(-10, robby.getPointCount());
        assertEquals(new Position(0, 9), robby.getPosition());

        robby.move(board);

        assertEquals(-15, robby.getPointCount());
        assertEquals(new Position(0, 9), robby.getPosition());

        //----------------
        when(genome.getAction(any(Situation.class))).thenReturn(Action.MoveNorth);
        for (int i = 0; i < 9; i++) {
            robby.move(board);
        }

        assertEquals(-15, robby.getPointCount());
        assertEquals(new Position(0, 0), robby.getPosition());

        robby.move(board);

        assertEquals(-20, robby.getPointCount());
        assertEquals(new Position(0, 0), robby.getPosition());
    }


    @Test
    public void testPickUp() {
        Genome genome = mock(Genome.class);

        when(genome.getAction(any(Situation.class))).thenReturn(Action.MoveEast);
        Board board = Board.getRandom(10, 10, 100);
        Robby robby = new Robby(genome);

        for (int i = 0; i < 9; i++) {

            when(genome.getAction(any(Situation.class))).thenReturn(Action.PickUpCan);
            robby.move(board);

            when(genome.getAction(any(Situation.class))).thenReturn(Action.MoveEast);
            robby.move(board);
        }

        assertEquals(90, robby.getPointCount());
        assertEquals(new Position(9, 0), robby.getPosition());

        when(genome.getAction(any(Situation.class))).thenReturn(Action.PickUpCan);
        robby.move(board);

        assertEquals(100, robby.getPointCount());
        assertEquals(new Position(9, 0), robby.getPosition());

        when(genome.getAction(any(Situation.class))).thenReturn(Action.PickUpCan);
        robby.move(board);

        assertEquals(99, robby.getPointCount());
        assertEquals(new Position(9, 0), robby.getPosition());
    }


    private void validateContent(Board board, int x, int y, Content[] expected) {
        when(robot.getPosition()).thenReturn(new Position(x, y));
        Situation situation = board.getSituationAt(robot.getPosition());
        Content[] actual = situation.getContents();
        assertArrayEquals(expected, actual);
    }

}
