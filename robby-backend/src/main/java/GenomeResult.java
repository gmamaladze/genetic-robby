public class GenomeResult {
    private final Genome genome;
    private final double fitness;

    public GenomeResult(Genome genome, double fitness) {

        this.genome = genome;
        this.fitness = fitness;
    }

    public Genome getGenome() {
        return genome;
    }

    public double getFitness() {
        return fitness;
    }
}
