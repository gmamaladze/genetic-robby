import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class RankSelector {

    private final Random random;
    private final int[] indices;
    private int stretch;
    private int size;

    public RankSelector(int size) {
        this.stretch = size * 30;
        this.size = size;
        this.random = new Random(System.currentTimeMillis());

        double[] fitness = IntStream.range(0, size).mapToDouble(n -> (double) n / size).toArray();
        double totalFitness = Arrays.stream(fitness).sum();
        var roulette = new double[size];

        double previousProbability = 0.0;
        for (int i = 0; i < size; i++) {
            roulette[i] = previousProbability + (fitness[i] / totalFitness);
            previousProbability = roulette[i];
        }

        this.indices = new int[stretch];
        int j = 0;
        for (int i = 0; i < stretch; i++) {
            double threshold = (double) i / stretch;
            while (roulette[j] <= threshold) {
                j++;
            }
            this.indices[i] = j;
        }
    }

    public int getRandomIndex() {
        int rnd = this.random.nextInt(stretch);
        return this.indices[rnd];
    }


    public int getSize() {
        return size;
    }
}
