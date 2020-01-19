import com.gma.robby.Action;
import com.gma.robby.Genome;
import com.gma.robby.Situation;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GenomeTest {

    @Test
    public void testX() {
        int[] dna = new int[Genome.length];
        Genome genome = new Genome(0, dna);

        for (int i = 0; i < Genome.length; i++) {
            dna[i] = Action.values()[i % Action.values().length].getCode();
        }

        for (int i = 0; i < Genome.length; i++) {
            Situation situation = new Situation(i);
            Action actual = genome.getAction(situation);
            Action expected = Action.values()[i % Action.values().length];
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testY() {
        int[] dnaA = new int[Genome.length];
        Arrays.fill(dnaA, Action.MoveWest.getCode());
        Genome a = new Genome(0, dnaA);

        int[] dnaB = new int[Genome.length];
        Arrays.fill(dnaB, Action.MoveSouth.getCode());
        Genome b = new Genome(0, dnaB);

        Genome c = Genome.crossbreed(a, b, 0);

        int aCount = 0;
        for (int i = 0; i < Genome.length; i++) {
            Situation situation = new Situation(i);
            Action action = c.getAction(situation);
            if (action.equals(Action.MoveWest)) {
                aCount++;
            } else {
                assertEquals(Action.MoveSouth, action);
            }
        }
        assertTrue((double) aCount / Genome.length < 0.7);
    }

    @Test
    public void testZ() {
        int[] dnaA = new int[Genome.length];
        Arrays.fill(dnaA, Action.MoveWest.getCode());
        Genome a = new Genome(0, dnaA);
        Genome c = Genome.crossbreed(a, a, 0.05);

        int aCount = 0;
        for (int i = 0; i < Genome.length; i++) {
            Situation situation = new Situation(i);
            Action action = c.getAction(situation);
            if (!action.equals(Action.MoveWest)) {
                aCount++;
                System.out.println(action);
            }
        }

        System.out.println(aCount);

        assertTrue((double) aCount / Genome.length < 0.055);
        assertTrue((double) aCount / Genome.length > 0.045);

    }

}
