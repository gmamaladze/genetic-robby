
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static java.util.Collections.reverseOrder;

public class Runner {

	private final ExecutorService executor;
	private Configuration configuration;

	public Runner(Configuration configuration) {
		this.executor = Executors.newFixedThreadPool(configuration.getNumberOfThreads());
		this.configuration = configuration;
	}

	public void runCivilization() throws InterruptedException {

		DNA[] population = Stream
			.generate(DNA::getRandom)
			.limit(configuration.getPopulationSize())
			.toArray(DNA[]::new);

		for(long generationId = 0; generationId< configuration.getNumberOfGenerations(); generationId++) {

			Board[] boardBlueprints = getBoardBlueprints();

			GenerationResult result = runGeneration(
					generationId,
					boardBlueprints,
					population,
					executor);

			save(result);

			int descendantCount = configuration.getPopulationSize() - result.survivors.length;
			int pairsCount = result.survivors.length / 2;

			Stream<DNA> descendants = IntStream
					.range(0, descendantCount)
					.map(descendantIndex -> descendantIndex % pairsCount)
					.mapToObj(pairIndex -> DNA.crossbreed(
							result.survivors[pairIndex],
							result.survivors[pairsCount + pairIndex],
							configuration.getMutationProbability()))
					;

			population = Stream.concat(
					Arrays.stream(result.survivors),
					descendants)
				.toArray(DNA[]::new);
		}
	}

	private Board[] getBoardBlueprints() {
		return Stream
				.generate(() -> Board.getRandom(configuration.getBoardWidth(), configuration.getBoardHeight(), configuration.getNumberOfCans()))
				.limit(configuration.getNumberOfBoards())
				.toArray(Board[]::new);
	}

	private GenerationResult runGeneration(long generationId, Board[] boardBlueprints, DNA[] strategies, ExecutorService executor) throws InterruptedException {
		List<Callable<LifeResult>> lives = new ArrayList<>(boardBlueprints.length * strategies.length);
		for (Board boardBlueprint: boardBlueprints) {
			for (DNA DNA : strategies) {
				Board board = boardBlueprint.clone();
				Robby robot = new Robby(DNA);
				lives.add(() -> liveOne(board, robot));
			}
		}

		int survivorCount = (int)Math.round(strategies.length * configuration.getSurvivorQuota());
		LinkedHashMap<DNA, Double> scoredSurvivors = executor
				.invokeAll(lives)
				.stream()
				.map(future -> {
					try {
						return future.get();
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
						throw new IllegalStateException(e);
					}
				})
				.collect(
						Collectors.groupingBy(
								r -> r.DNA,
								Collectors.averagingDouble(r -> r.score)))
				.entrySet()
				.stream()
				.sorted(reverseOrder(Map.Entry.comparingByValue()))
				.limit(survivorCount)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		Double[] scores = scoredSurvivors.values().toArray(Double[]::new);

		DNA[] survivors = scoredSurvivors.keySet().toArray(DNA[]::new);
		double maxScore =
				Arrays.stream(scores).mapToDouble(d -> d)
						.max()
						.orElse(Double.NaN);

		double avgScore =
				Arrays.stream(scores).mapToDouble(d -> d)
						.average()
						.orElse(Double.NaN);

		return new GenerationResult(generationId, maxScore, avgScore, survivors);
	}

	private void save(GenerationResult generationResult) {
		System.out.println(generationResult);
	}

	private LifeResult liveOne(Board board, Robby robot) {
		for (int moveCount = 0; moveCount < this.configuration.getNumberOfMoves(); moveCount++) {
			ActionResult result = robot.move(board);
			//System.out.println(result);
		}
		return new LifeResult(
				robot.getDNA(),
				robot.getPointCount()
		);
	}

	public void shutdown() {
		executor.shutdown();
	}

	static class LifeResult {
		public int score;
		public DNA DNA;

		public LifeResult(DNA DNA, int score) {

			this.DNA = DNA;
			this.score = score;
		}
	}

	static class GenerationResult {
		public double avgScore;
		public long generationId;
		public double maxScore;
		public DNA[] survivors;

		public GenerationResult(long generationId, double maxScore, double avgScore, DNA[] survivors) {
			this.generationId = generationId;
			this.maxScore = maxScore;
			this.avgScore = avgScore;
			this.survivors = survivors;
		}

		@Override
		public String toString() {
			return String.format("GenerationResult{ " +
					"generationId=%5d " +
					", maxScore=%10.4f " +
					", avgScore=%10.4f " +
					", survivorsCount=%4d " +
					'}', generationId, maxScore, avgScore, survivors.length);
		}
	}


}
