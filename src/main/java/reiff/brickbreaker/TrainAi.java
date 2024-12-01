package reiff.brickbreaker;

import levy.brickbreaker.Ball;
import levy.brickbreaker.Brick;
import levy.brickbreaker.Paddle;
import basicneuralnetwork.NeuralNetwork;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class TrainAi {


    public static void main(String[] args) {

        Ball ball = new Ball(390, 510, 20, 20, 20, 5, 45);
        Paddle paddle = new Paddle(350, 550, 100, 10, 20);
        List<Brick> bricks = new ArrayList<>();

        ManyNetworks manyNetworks = new ManyNetworks();

        List<NeuralNetwork> currentGeneration = manyNetworks.generateNetworks();

        List<NetworkAndScore> trained = play(currentGeneration, ball, paddle);

        List<NetworkAndScore> topPerformingWithScores = manyNetworks.getTop10NetworksWithScores(trained);


        for (int generation = 0; generation < 5; generation++) {
            System.out.println("Generation: " + (generation + 1));

            // Extract just the networks for the next generation (to play we need a list of neural networks)
            List<NeuralNetwork> topPerformingNetworks = topPerformingWithScores.stream()
                    .map(NetworkAndScore::getNetwork)
                    .collect(Collectors.toList());

            // Create the next generation based on the top-performing networks
            currentGeneration = manyNetworks.createNextGeneration(topPerformingNetworks);

            trained = play(currentGeneration, ball, paddle);
            // Let the new generation play and determine the top 10 networks with scores
            topPerformingWithScores = manyNetworks.getTop10NetworksWithScores(trained);

            // Print the scores for the top 10 networks
            System.out.println("Top 10 Scores for Generation " + (generation + 1) + ":");
            for (int i = 0; i < topPerformingWithScores.size(); i++) {
                NetworkAndScore entry = topPerformingWithScores.get(i);
                System.out.println("Network " + (i + 1) + " Score: " + entry.getScore());
            }
        }

        // Step 4: Find and store the best network
        NetworkAndScore bestNetworkAndScore = topPerformingWithScores.stream()
                .max(Comparator.comparingInt(NetworkAndScore::getScore)) // Find the highest score
                .orElseThrow(() -> new IllegalStateException("No networks available")); // Handle empty list case

       NeuralNetwork bestNetwork = bestNetworkAndScore.getNetwork(); // Extract the best network
        bestNetwork.writeToFile("BestNW");
// Output the best network's details
        System.out.println("Best Network's Score: " + bestNetworkAndScore.getScore());

    }

    private static List<NetworkAndScore> play(List<NeuralNetwork> currentGeneration, Ball ball, Paddle paddle) {
        List<NetworkAndScore> performanceList = new ArrayList<>();

        for (NeuralNetwork neuralNetwork : currentGeneration) {

            Simulation simulation = new Simulation(neuralNetwork, ball, paddle, 800, 600);
           simulation.resetGame();
            simulation.startGame();

            int round = 0;
            int score = 0;
            while (round < 10000 && !simulation.isGameStopped()) {

                simulation.advance();
                score = simulation.getScore();
                round++;
            }

          NetworkAndScore neuralandscore = new NetworkAndScore(neuralNetwork, score);
          performanceList.add(neuralandscore);

        }
        return performanceList;
    }
}
