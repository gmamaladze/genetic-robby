package com.gma.robby;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Runner {

    private final ExecutorService executor;
    private final RankSelector rankSelector;
    private Configuration configuration;
    private ResultWriter writer;

    public Runner(Configuration configuration) {
        this.executor = Executors.newFixedThreadPool(configuration.getNumberOfThreads());
        this.configuration = configuration;
        int genPoolSize = (int) Math.round(configuration.getPopulationSize() * configuration.getGenPoolQuota());
        this.rankSelector = new RankSelector(genPoolSize);
    }

    public void runCivilization(ResultWriter writer) throws InterruptedException, IOException {
        this.writer = writer;

        Genome[] population = Stream
                .generate(Genome::getRandom)
                .limit(configuration.getPopulationSize())
                .toArray(Genome[]::new);

        Queue<Board> environments = new LinkedList<>();
        createEnvironments(environments);
        writer.write(environments);

        for (long generationId = 0; generationId < configuration.getNumberOfGenerations(); generationId++) {

            createEnvironments(environments);

            Board[] boardBlueprints = environments.toArray(Board[]::new);
            GenerationResult result = runGeneration(
                    generationId,
                    boardBlueprints,
                    population,
                    executor);

            save(result);
            Genome[] pool = Arrays.stream(result.getGenomeResults())
                    .map(GenomeResult::getGenome)
                    .toArray(Genome[]::new);
            population = createNewPopulation(pool);
        }
    }

    private void createEnvironments(Queue<Board> environments) {
        for (int i = 0; i < configuration.getNumberOfBoards() * configuration.getNewBoardQuota(); i++) {
            if (environments.poll() == null) break;
        }

        while (environments.size() < configuration.getNumberOfBoards()) {
            environments.add(
                    Board.getRandom(
                            configuration.getBoardWidth(),
                            configuration.getBoardHeight(),
                            configuration.getNumberOfCans()));
        }
    }

    private Genome[] createNewPopulation(Genome[] pool) {
        int size = configuration.getPopulationSize();
        int survivorCount = (int) Math.round(size * configuration.getSurvivorQuota());
        int descendantsCount = size - survivorCount;
        int ancestorsCount = this.rankSelector.getSize();

        Stream<Genome> survivors = Arrays.stream(pool, size - survivorCount, size);
        Genome[] ancestors = Arrays.stream(pool, size - ancestorsCount, size).toArray(Genome[]::new);

        Stream<Genome> descendants = IntStream
                .range(0, descendantsCount)
                .mapToObj(i -> {
                    int indexA = this.rankSelector.getRandomIndex();
                    Genome a = ancestors[indexA];
                    int indexB = this.rankSelector.getRandomIndex();
                    Genome b = ancestors[indexB];
                    return Genome.crossbreed(a, b, configuration.getMutationProbability());
                });

        return Stream.concat(survivors, descendants).toArray(Genome[]::new);
    }


    private GenerationResult runGeneration(long generationId, Board[] boardBlueprints, Genome[] strategies, ExecutorService executor) throws InterruptedException {
        List<Callable<SessionResult>> sessions = new ArrayList<>(boardBlueprints.length * strategies.length);
        for (Board boardBlueprint : boardBlueprints) {
            for (Genome Genome : strategies) {
                Board board = boardBlueprint.clone();
                Robby robot = new Robby(Genome);
                sessions.add(() -> runSession(board, robot));
            }
        }

        GenomeResult[] scoredGenomes = executor
                .invokeAll(sessions)
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
                                SessionResult::getGenome,
                                Collectors.summingDouble(SessionResult::getScore)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(e -> new GenomeResult(e.getKey(), e.getValue() / configuration.getNumberOfBoards()))
                .toArray(GenomeResult[]::new);

        return new GenerationResult(generationId, scoredGenomes);
    }

    private void save(GenerationResult generationResult) throws IOException {

        System.out.println(generationResult);
        writer.write(generationResult);
    }

    private SessionResult runSession(Board board, Robby robot) {
        for (int moveCount = 0; moveCount < this.configuration.getNumberOfMoves(); moveCount++) {
            robot.move(board);
        }
        return new SessionResult(
                robot.getGenome(),
                robot.getPointCount()
        );
    }

    public void shutdown() {
        executor.shutdown();
    }


}
