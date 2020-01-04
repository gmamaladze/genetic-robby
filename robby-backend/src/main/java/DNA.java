import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DNA {

    public static final int length = (int)Math.pow(Content.values().length, PositionOffset.values().length);
    public static final Random random = new Random(System.currentTimeMillis());

    private static final int POSSIBLE_ACTION_COUNT = Action.values().length;

    private final int[] dna;

    private static final AtomicLong uniqueCounter = new AtomicLong();
    private final long uniqueId;

    DNA(int[] dna) {
        this.uniqueId = uniqueCounter.incrementAndGet();
        this.dna = dna;
    }

    public static DNA getRandom() {
        int[] dna = IntStream.range(0, length).map( i -> DNA.getRandomAction()).toArray();
        return new DNA(dna);
    }

    public DNA mutate(double mutationProbability) {
        double[] chance = random.doubles(length).toArray();
        for (int i=0; i<length; i++) {
            boolean doMutate = chance[i] <= mutationProbability;
            this.dna[i] = doMutate ? getRandomAction(this.dna[i]) : this.dna[i];
        }
        return this;
    }

    private static int getRandomAction() {
        return getRandomAction(-1);
    }
    private static int getRandomAction(int differentFrom) {
        int candidate;
        do {
            candidate = random.nextInt(POSSIBLE_ACTION_COUNT);
        } while (candidate == differentFrom);
        return candidate;
    }

    public static DNA crossbreed(DNA a, DNA b, double mutationProbability) {
        double[] chance = random.doubles(length).toArray();

        DNA DNA = merge(a, b, chance);
        DNA.mutate(mutationProbability);
        return DNA;
    }

    public static DNA merge(DNA a, DNA b, double[] chance) {
        int[] c = new int[length];
        for (int i=0; i<length; i++) {
            c[i] = chance[i]>0.5 ? a.dna[i] : b.dna[i];
        }
        return new DNA(c);
    }

    public Action getAction(Situation situation) {
        int actionCode = dna[situation.getCode()];
        return Action.values()[actionCode];
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.uniqueId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DNA dna = (DNA) obj;
        return dna.uniqueId == this.uniqueId;
    }

    @Override
    protected DNA clone() {
        return new DNA(this.dna.clone());
    }

    @Override
    public String toString() {
        return "DNA{" +
                "dna=" + Arrays.toString(dna) +
                '}';
    }
}


