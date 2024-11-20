package reiff.brickbreaker;

import lesser.brickbreaker.BrickBreakerComponent;
import lesser.brickbreaker.BrickBreakerFrame;
import levy.brickbreaker.Ball;
import levy.brickbreaker.Brick;
import levy.brickbreaker.Paddle;
import basicneuralnetwork.NeuralNetwork;

import java.util.ArrayList;
import java.util.List;

//1. We need to do many things: first make sure that our things are initlized correctly --> bricks
//2. Once we have that we can run the code probably, if not we'll have to debug, maybe we should do tests for out methods
//In many networks
// 3. once we have this runnign correctly we can track the progress and understand if its actually progressing or not
//4. If the time is getting longer and longer it means that it lasts for longer, thus it did not loose.
//5. After all of this and we see that in fact everything is working nicely, We'll try and see if we can have them play with
// the gui
public class MainPlay {
    public static void main(String[] args) {


        // Initialize the necessary components
        Ball ball = new Ball(390, 510, 20, 20, 20, 5, 45);

        Paddle paddle = new Paddle(350, 550, 100, 10, 60);

        List<Brick> bricks = initializeBricks(10, 5, 60, 20, 10, 800);

        BrickBreakerComponent view = new BrickBreakerComponent(ball, paddle, bricks );

        Controller controller = new Controller(ball, paddle, bricks, view);

        // Create ManyNetworks instance to handle neural network operations
        ManyNetworks manyNetworks = new ManyNetworks(ball, paddle, controller, view);

        // Step 1:Generate the first generation of networks
        NeuralNetwork[] currentGeneration = manyNetworks.generateNetworks();

        manyNetworks.networksPlay(currentGeneration);

        // Step 2: Let the first generation play and get the top 10
        NeuralNetwork[] topPerformingNetworks = manyNetworks.getTop10Networks(currentGeneration);

        // Step 3: Evolve networks over 50 generations
        for (int generation = 0; generation < 50; generation++) {
            System.out.println("Generation: " + (generation + 1));

            // Create the next generation based on the top-performing networks
            currentGeneration = manyNetworks.createNextGeneration(topPerformingNetworks);

            for (int i = 0; i < currentGeneration.length; i++) {
                System.out.println("This us generation: " + (generation +1) + " Network " + (i + 1) + ": " + currentGeneration[i]);
            }

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

    private static List<Brick> initializeBricks(int cols, int rows, int brickWidth, int brickHeight, int spacing, int frameWidth) {
        List<Brick> bricks = new ArrayList<>();
        int xoffset = (frameWidth - (cols * (brickWidth + spacing) - spacing)) / 2;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = xoffset + col * (brickWidth + spacing);
                int y = 50 + row * (brickHeight + spacing);
                bricks.add(new Brick(x, y, brickWidth, brickHeight));
            }
        }

        return bricks;
    }

}
