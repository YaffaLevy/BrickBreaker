package reiff.brickbreaker;

import levy.brickbreaker.Ball;
import levy.brickbreaker.Paddle;
import basicneuralnetwork.NeuralNetwork;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class TrainAi {


    public static void main(String[] args) {

        Ball ball = new Ball(390, 510, 20, 20, 1, 45);
        Paddle paddle = new Paddle(350, 550, 100, 10, 20);
        Random random = new Random();

        ManyNetworks manyNetworks = new ManyNetworks();
        BrickFactory brickFactory = new BrickFactory(800, 600, 60, 20);

        List<NeuralNetwork> currentGeneration = manyNetworks.generateNetworks();
        List<NetworkAndScore> trained = play(random, currentGeneration, ball, paddle, brickFactory);
        List<NetworkAndScore> topPerformingWithScores = manyNetworks.getTop10NetworksWithScores(trained);


        for (int generation = 0; generation < 100; generation++) {
            System.out.println("Generation: " + (generation + 1));

            List<NeuralNetwork> topPerformingNetworks = topPerformingWithScores.stream()
                    .map(NetworkAndScore::getNetwork)
                    .collect(Collectors.toList());

            currentGeneration = manyNetworks.createNextGeneration(topPerformingNetworks);

            trained = play(random, currentGeneration, ball, paddle, brickFactory);
            topPerformingWithScores = manyNetworks.getTop10NetworksWithScores(trained);

            System.out.println("Top 10 Scores for Generation " + (generation + 1) + ":");
            for (int i = 0; i < topPerformingWithScores.size(); i++) {
                NetworkAndScore entry = topPerformingWithScores.get(i);
                System.out.println("Network " + (i + 1) + " Score: " + entry.getScore());
            }
        }

        NetworkAndScore bestNetworkAndScore = topPerformingWithScores.stream()
                .max(Comparator.comparingInt(NetworkAndScore::getScore)) // Find the highest score
                .orElseThrow(() -> new IllegalStateException("No networks available"));

        System.out.println(bestNetworkAndScore);
        NeuralNetwork bestNetwork = bestNetworkAndScore.getNetwork();

        bestNetwork.writeToFile("BestNW");
        System.out.println("Best Network's Score: " + bestNetworkAndScore.getScore());

    }

    private static List<NetworkAndScore> play(Random random, List<NeuralNetwork> currentGeneration,
                                              Ball ball, Paddle paddle, BrickFactory brickFactory) {
        List<NetworkAndScore> performanceList = new ArrayList<>();

        for (NeuralNetwork neuralNetwork : currentGeneration) {

            Simulation simulation = new Simulation(neuralNetwork, random.nextLong(), ball, paddle, 800, 600, brickFactory);

            int round = 0;
            while (round < 10000 && simulation.advance()) {
                round++;
            }
            int score = simulation.getScore();
            NetworkAndScore neuralandscore = new NetworkAndScore(neuralNetwork, score, simulation.getSeed());
            performanceList.add(neuralandscore);

        }
        return performanceList;
    }

}
