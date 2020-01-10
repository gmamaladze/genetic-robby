import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

import java.util.LinkedList;
import java.util.OptionalDouble;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestX {

    @Test
    void visitAllCellsWithoutMistakesGathers200Points() {
        Board board = Board.getRandom(10, 10, 20);

        Queue<Action> fifo = new LinkedList<>();
        Action xDirection = Action.MoveWest;
        Position current = new Position(0,0);
        for (int i = 0; i < 10; i++) {
            if (xDirection==Action.MoveWest) xDirection=Action.MoveEast;
            else xDirection=Action.MoveWest;
            for (int j = 0; j < 10; j++) {
                if (board.hasCan(current)) {
                    fifo.add(Action.PickUpCan);
                }
                if (j!=10-1) {
                    fifo.add(xDirection);
                    current = xDirection == Action.MoveWest
                            ? Offset.WEST.from(current)
                            : Offset.EAST.from(current);
                }
            }
            if (i != 10 - 1) {
                fifo.add(Action.MoveSouth);
                current = Offset.SOUTH.from(current);
            }
        }

        Genome fakeGenome = mock(Genome.class);
        when(fakeGenome.getAction(any(Situation.class)))
                .thenAnswer((Answer<Action>) invocationOnMock -> fifo.remove());

        Robby robby = new Robby(fakeGenome);
        int moveCount = fifo.size();
        for (int i = 0; i < moveCount; i++) {
            ActionResult position = robby.move(board);
            System.out.println(position);
            System.out.println(robby.getPointCount());
        }
        assertEquals(200, robby.getPointCount());
    }

    @Test
    void actionsAreEvenlyDistributedInARandomDNA() {
        Genome genome = Genome.getRandom();

        double average = (double) Genome.length / Action.values().length;

        OptionalDouble deviation = IntStream
                .range(0, Genome.length)
                .mapToObj(Situation::new)
                .collect(Collectors.groupingBy(
                        genome::getAction,
                        Collectors.counting()))
                .values().stream()
                .mapToDouble(aLong -> aLong)
                .map(a -> Math.abs(a-average) / average)
                .average();

        assertTrue(deviation.isPresent());
        assertTrue(deviation.getAsDouble()<.2);
    }

    @Test
    void actionsAreEvenlyDistributedAfterCrossbreeding() {
        Genome c = Genome.getRandom();

        for (int i = 0; i < 100; i++) {
            Genome b = Genome.getRandom();
            c = Genome.crossbreed(c, b, 0.1);
        }

        double average = (double) Genome.length / Action.values().length;

        OptionalDouble deviation = IntStream
                .range(0, Genome.length)
                .mapToObj(Situation::new)
                .collect(Collectors.groupingBy(
                        c::getAction,
                        Collectors.counting()))
                .values().stream()
                .mapToDouble(aLong -> aLong)
                .map(aDouble -> Math.abs(aDouble-average) / average)
                .average();

        assertTrue(deviation.isPresent());
        assertTrue(deviation.getAsDouble()<.2);
    }

    @Test
    void testSituation() {
        Board board = Board.getRandom(10, 10, 20);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Position current = new Position(i, j);
                Situation situation = board.getSituationAt(current);

                int code = situation.getCode();
                Content[] contents = Situation.decode(code);
                if (i==0) assertEquals(Content.WALL, contents[3]);
                if (i==9) assertEquals(Content.WALL, contents[1]);
                if (j==0) assertEquals(Content.WALL, contents[0]);
                if (j==9) assertEquals(Content.WALL, contents[2]);
                if (board.hasCan(current)) assertEquals(Content.CAN,contents[4]); else assertEquals(Content.EMPTY, contents[4]);
            }
        }
    }


/*    @Test
    void testMutation() {
        int diffCounter = 0;
        final int cycles = 100;
        double mutationProbability = 0.00823;

        for (int i = 0; i < cycles; i++) {
            Genome a = Genome.getRandom();
            Genome b = Genome.mutate(a, mutationProbability);
            for (int j = 0; j < Genome.length; j++) {
                Situation situation = new Situation(j);
                Action aAction = a.getAction(situation);
                Action bAction = b.getAction(situation);
                diffCounter += aAction.equals(bAction) ? 0 : 1;
            }
        }

        double mutationRate = (double) diffCounter / (cycles * Genome.length);
        double deviation = 1 - Math.abs(mutationRate - mutationProbability / mutationProbability);
        assertTrue(deviation < 0.01);
    }*/

    @Test
    void deserializeDNA() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Genome genome = Genome.getRandom();
        String json = om.writeValueAsString(genome);
        System.out.println(json);
    }
}