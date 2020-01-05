import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
public class DNA {

    public static final int length = (int)Math.pow(Content.values().length, PositionOffset.values().length);
    public static final Random random = new Random(System.currentTimeMillis());
    private static final int POSSIBLE_ACTION_COUNT = Action.values().length;
    private static final AtomicLong uniqueCounter = new AtomicLong();

    private final long uniqueId;
    private final int[] dna;

    @JsonCreator
    DNA(@JsonProperty("id") long uniqueId,
        @JsonProperty("dna") int[] dna) {
        this.uniqueId = uniqueId;
        this.dna = dna;
    }

    public static DNA getRandom() {
        int[] dna = IntStream.range(0, length).map( i -> DNA.getRandomAction()).toArray();
        long uniqueId = uniqueCounter.incrementAndGet();
        return new DNA(uniqueId, dna);
    }

    public static DNA mutate(DNA dna, double mutationProbability) {
        dna = dna.clone();
        double[] chance = random.doubles(length).toArray();
        for (int i=0; i<length; i++) {
            boolean doMutate = chance[i] <= mutationProbability;
            dna.dna[i] = doMutate ? getRandomAction(dna.dna[i]) : dna.dna[i];
        }
        return dna;
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
        DNA merged = merge(a, b, chance);
        merged = DNA.mutate(merged, mutationProbability);
        return merged;
    }

    public static DNA merge(DNA a, DNA b, double[] chance) {
        var c = new int[length];
        for (int i=0; i<length; i++) {
            c[i] = chance[i]>0.5 ? a.dna[i] : b.dna[i];
        }
        long uniqueId = uniqueCounter.incrementAndGet();
        return new DNA(uniqueId, c);
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
        long uniqueId = uniqueCounter.incrementAndGet();
        return new DNA(uniqueId, this.dna.clone());
    }

    @Override
    public String toString() {
        return "DNA{" +
                "dna=" + Arrays.toString(dna) +
                '}';
    }
}


