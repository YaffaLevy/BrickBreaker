
package reiff.brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import lesser.brickbreaker.BrickBreakerComponent;
import levy.brickbreaker.Ball;
import levy.brickbreaker.Paddle;

import java.awt.event.KeyEvent;
import java.util.*;
import java.util.stream.Collectors;


public class ManyNetworks {

    private final Ball ball;
    private final Paddle paddle;
    private final Controller controller;
    private final BrickBreakerComponent view;

    public ManyNetworks(Ball ball, Paddle paddle, Controller controller, BrickBreakerComponent view) {
        this.ball = ball;
        this.paddle = paddle;
        this.controller = controller;
        this.view = view;
    }

    //Here we are creating 1000 networks and storing them in an array.
    public List<NeuralNetwork> generateNetworks() {

        List<NeuralNetwork> neuralNetworkList = new ArrayList<>(1000);

        for (int i = 0; i < 1000; i++) {
            neuralNetworkList.add(new NeuralNetwork(1, 2, 4, 2));
        }

        return neuralNetworkList;
    }
    //now we have a list of 1000 network, one in each position.

    public List<NeuralNetwork> createNextGeneration(List<NeuralNetwork> topPerformingNetworks) {

        List<NeuralNetwork> nextGeneration = new ArrayList<>(1000);
        int index = 0;

        for (int i = 0; i < topPerformingNetworks.size(); i++) {

            for (int j = 0; j < topPerformingNetworks.size(); j++) {
                if (i != j) {  // Don't merge the network with itself
                    NeuralNetwork parent1 = topPerformingNetworks.get(i);
                    NeuralNetwork parent2 = topPerformingNetworks.get(j);

                    NeuralNetwork child = parent1.merge(parent2);
                    child.mutate(0.1);

                    nextGeneration.add(index, child);
                    index++;

                }
            }
        }

        return nextGeneration;
    }

    //We need to figure out now, where are we letting these new networks play and so on...

    //Here we are going to let each network play
    public List<NetworkAndScore> networksPlay(List<NeuralNetwork> neuralNetworksArray) {

        List<NetworkAndScore> performanceList = new ArrayList<>();

        for (NeuralNetwork neuralNetwork : neuralNetworksArray) {

            controller.resetGame();
            controller.startGame();

            int round = 0;
            int score = 0;
            while (round < 10000 && !controller.isGameStopped()) {

                controller.updateBallPosition();
                //System.out.println("Ball position " + ball.getX() +", " + ball.getY());
                double[] input = new double[1];
                input[0] = calculateAngle();
                double[] answer = neuralNetwork.guess(input);

                double leftConfidence = answer[0];
                double rightConfidence = answer[1];


                // Simulate key presses based on the neural network's confidence in the choice
                if (leftConfidence > rightConfidence) {
                    //System.out.println("paddle position before moving left x: " +paddle.getX() +" Y: "+paddle.getY() );
                    movePaddleLeft();
                    //System.out.println("Move left paddle position " + paddle.getX() +", " + paddle.getY());
                   // System.out.println("paddle position after moving left x: " +paddle.getX() +" Y: "+paddle.getY() );
                } else {
                  //  System.out.println("paddle position before moving right x: " +paddle.getX() +" Y: "+paddle.getY() );
                    movePaddleRight();
                    // System.out.println("Move right paddle position " + paddle.getX() +", " + paddle.getY());
                   // System.out.println("paddle position before moving right x: " +paddle.getX() +" Y: "+paddle.getY() );
                }
                round++;

                if (controller.paddleHit()) {
                    score++;
                }
            }

            NetworkAndScore neuralandscore = new NetworkAndScore(neuralNetwork, score);
            performanceList.add(neuralandscore);

        }

        return performanceList;
    }


    public List<NetworkAndScore> getTop10NetworksWithScores(List<NeuralNetwork> neuralNetworks) {
        List<NetworkAndScore> performanceList = networksPlay(neuralNetworks); // Calculate scores
        return performanceList.stream()
                .sorted(Comparator.comparingInt(NetworkAndScore::getScore).reversed()) // Sort by score
                .limit(10) // Get the top 10
                .collect(Collectors.toList());
    }



    private double calculateAngle() {
            double paddleCenterX = paddle.getCenterX();
            double deltaX = ball.getX() - paddleCenterX;
            double deltaY = ball.getY() - paddle.getCenterY();
            return Math.toDegrees(Math.atan2(deltaY, deltaX));
        }

    private void movePaddleLeft() {
            controller.handleKeyEvent(KeyEvent.VK_LEFT);

    }

    private void movePaddleRight() {
    controller.handleKeyEvent(KeyEvent.VK_RIGHT);
    }
}




