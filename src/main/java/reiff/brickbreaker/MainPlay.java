package reiff.brickbreaker;

import lesser.brickbreaker.BrickBreakerComponent;
import levy.brickbreaker.Ball;
import levy.brickbreaker.Brick;
import levy.brickbreaker.Paddle;
import basicneuralnetwork.NeuralNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainPlay {
    public static void main(String[] args) {

        Ball ball = new Ball(390, 510, 20, 20, 20, 5, 45);
        Paddle paddle = new Paddle(350, 550, 100, 10, 60);
        List<Brick> bricks = new ArrayList<>();
        BrickBreakerComponent view = new BrickBreakerComponent(ball, paddle, bricks);
        Controller controller = new Controller(ball, paddle, bricks, view);

        // Create ManyNetworks instance to handle neural network operations
        ManyNetworks manyNetworks = new ManyNetworks(ball, paddle, controller, view);

        // Step 1: Generate the first generation of networks
        List<NeuralNetwork> currentGeneration = manyNetworks.generateNetworks();

        // Step 2: Let the first generation play and get the top 10 networks with scores
        List<NetworkAndScore> topPerformingWithScores = manyNetworks.getTop10NetworksWithScores(currentGeneration);

        // Step 3: Evolve networks over 5 generations
        for (int generation = 0; generation < 5; generation++) {
            System.out.println("Generation: " + (generation + 1));

            // Extract just the networks for the next generation
            List<NeuralNetwork> topPerformingNetworks = topPerformingWithScores.stream()
                    .map(NetworkAndScore::getNetwork)
                    .collect(Collectors.toList());

            // Create the next generation based on the top-performing networks
            currentGeneration = manyNetworks.createNextGeneration(topPerformingNetworks);

            // Let the new generation play and determine the top 10 networks with scores
            topPerformingWithScores = manyNetworks.getTop10NetworksWithScores(currentGeneration);

            // Print the scores for the top 10 networks
            System.out.println("Top 10 Scores for Generation " + (generation + 1) + ":");
            for (int i = 0; i < topPerformingWithScores.size(); i++) {
                NetworkAndScore entry = topPerformingWithScores.get(i);
                System.out.println("Network " + (i + 1) + " Score: " + entry.getScore());
            }
        }

        // Step 4: Output the final generation of networks
        System.out.println("Final generation of networks created.");
        System.out.println("Top 10 Networks from Final Generation:");
        for (int i = 0; i < topPerformingWithScores.size(); i++) {
            NetworkAndScore entry = topPerformingWithScores.get(i);
            System.out.println("Network " + (i + 1) + " Score: " + entry.getScore());
        }
    }
}
