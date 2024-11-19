package reiff.brickbreaker;

import lesser.brickbreaker.BrickBreakerComponent;
import lesser.brickbreaker.BrickBreakerFrame;
import levy.brickbreaker.Ball;
import levy.brickbreaker.Brick;
import levy.brickbreaker.Paddle;
import basicneuralnetwork.NeuralNetwork;

import java.util.ArrayList;
import java.util.List;

public class MainPlay {
    public static void main(String[] args) {

        // Initialize the necessary components
        Ball ball = new Ball(390, 510, 20, 20, 20, 5, 45);

        Paddle paddle = new Paddle(350, 550, 100, 10, 60);

        List<Brick> bricks = new ArrayList<>();

        BrickBreakerComponent view = new BrickBreakerComponent(ball, paddle, bricks );

        Controller controller = new Controller(ball, paddle, bricks, view);

        //BrickBreakerFrame.initializeBricks();

        // Create ManyNetworks instance to handle neural network operations
        ManyNetworks manyNetworks = new ManyNetworks(ball, paddle, controller, view);

        // Step 1: Generate the first generation of networks
        NeuralNetwork[] currentGeneration = manyNetworks.generateNetworks();

        // Step 2: Let the first generation play and get the top 10
        NeuralNetwork[] topPerformingNetworks = manyNetworks.getTop10Networks(currentGeneration);

        // Step 3: Evolve networks over 10 generations
        for (int generation = 0; generation < 10; generation++) {
            System.out.println("Generation: " + (generation + 1));

            // Create the next generation based on the top-performing networks
            currentGeneration = manyNetworks.createNextGeneration(topPerformingNetworks);

            // Let the new generation play and determine the top 10 networks
            topPerformingNetworks = manyNetworks.getTop10Networks(currentGeneration);
        }

        // Step 4: Output the final generation of networks
        System.out.println("Final generation of networks created.");

        NeuralNetwork[] finalTop10 = topPerformingNetworks;
        System.out.println("Top 10 Networks from Final Generation:");
        for (int i = 0; i < finalTop10.length; i++) {
            System.out.println("Network " + (i + 1) + ": " + finalTop10[i]);
        }
    }
}
