import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Genome {

    public static final int length = (int) Math.pow(Content.values().length, Offset.values().length);
    public static final Random random = new Random(System.currentTimeMillis());
    private static final int POSSIBLE_ACTION_COUNT = Action.values().length;
    private static final AtomicLong uniqueCounter = new AtomicLong();

    private final long uniqueId;
    private final int[] dna;

    @JsonCreator
    Genome(@JsonProperty("id") long uniqueId,
           @JsonProperty("dna") int[] dna) {
        this.uniqueId = uniqueId;
        this.dna = dna;
    }

    public static Genome getRandom() {
        int[] dna = IntStream.range(0, length).map(i -> Genome.getRandomAction()).toArray();
        long uniqueId = uniqueCounter.incrementAndGet();
        return new Genome(uniqueId, dna);
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

    public static Genome crossbreed(Genome a, Genome b, double mutationProbability) {
        int[] merged = merge(a, b);
        mutate(merged, mutationProbability);
        return new Genome(uniqueCounter.incrementAndGet(), merged);
    }

    private static void mutate(int[] merged, double mutationProbability) {
        int mutationCount = (int) Math.round(length * mutationProbability);
        for (int i = 0; i < mutationCount; i++) {
            int mutationIndex = random.nextInt(length);
            merged[mutationIndex] = getRandomAction(merged[i]);
        }
    }

    private static int[] merge(Genome a, Genome b) {
        int[] merged = new int[length];
        final int maxSegmentSize = length / 3;
        final int minSegmentSize = length / 5;
        int nextIndex = 0;
        var current = a.dna;
        for (int i = 0; i < length; i++) {
            if (i == nextIndex) {
                nextIndex = i + minSegmentSize + random.nextInt(maxSegmentSize - minSegmentSize);
                current = current == a.dna ? b.dna : a.dna;
            }
            merged[i] = current[i];
        }
        return merged;
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
        Genome genome = (Genome) obj;
        return genome.uniqueId == this.uniqueId;
    }

    @SuppressWarnings({"CloneDoesntDeclareCloneNotSupportedException", "MethodDoesntCallSuperMethod"})
    @Override
    protected Genome clone() {
        long uniqueId = uniqueCounter.incrementAndGet();
        return new Genome(uniqueId, this.dna.clone());
    }
}


