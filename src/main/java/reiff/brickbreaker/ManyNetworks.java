
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
    public NeuralNetwork [] generateNetworks() {

        NeuralNetwork[] neuralNetworksArray = new NeuralNetwork[1000];

        for (int i = 0; i < 1000; i++) {
            neuralNetworksArray[i] = new NeuralNetwork(2, 2, 4, 2);
        }

        return neuralNetworksArray;
    }
    //now we have an array of 1000 network, one in each position.

    public NeuralNetwork[] createNextGeneration(NeuralNetwork[] topPerformingNetworks) {
        NeuralNetwork[] nextGeneration = new NeuralNetwork[1000];

        int index = 0;

        for (int i = 0; i < topPerformingNetworks.length; i++) {

            for (int j = 0; j < topPerformingNetworks.length; j++) {
                if (i != j) {  // Don't merge the network with itself
                    NeuralNetwork parent1 = topPerformingNetworks[i];
                    NeuralNetwork parent2 = topPerformingNetworks[j];

                    NeuralNetwork child = parent1.merge(parent2);
                    child.mutate(0.1);

                    nextGeneration[index] = child;
                    index++;


                    if (index >= nextGeneration.length) {
                        return nextGeneration;
                    }
                }
            }
        }

        return nextGeneration;
    }

    //We need to figure out now, where are we letting these new networks play and so on...

    //Here we are going to let each network play
    public Map<NeuralNetwork, Double> networksPlay(NeuralNetwork[] neuralNetworksArray) {

        Map<NeuralNetwork, Double> performanceMap = new HashMap<>();
        long maxDurationMillis = 120 * 1000; // 120 seconds max duration

        for (NeuralNetwork neuralNetwork : neuralNetworksArray) {

            controller.resetGame();
            controller.startGame();

            long startTime = System.currentTimeMillis();
            while (!controller.isGameStopped()) {
                if (System.currentTimeMillis() - startTime > maxDurationMillis) {
                    System.out.println("Time-out reached, stopping the game.");
                    break;
                }

                controller.updateBallPosition();

                double[] input = new double[1];
                input[0] = calculateAngle();
                double[] answer = neuralNetwork.guess(input);

                double leftConfidence = answer[0];
                double rightConfidence = answer[1];


                int maxPresses = 12;
                int numPressesLeft = (int) (leftConfidence * maxPresses);
                int numPressesRight = (int) (rightConfidence * maxPresses);

                // Simulate key presses based on the neural network's confidence in the choice
                if (leftConfidence > rightConfidence) {
                    movePaddleLeft(numPressesLeft);
                } else {
                    movePaddleRight(numPressesRight);
                }
            }

            double timeSurvived = (System.currentTimeMillis() - startTime) / 1000.0;
            if (controller.isGameStopped()) {
                performanceMap.put(neuralNetwork, timeSurvived);
            } else {
                performanceMap.put(neuralNetwork, maxDurationMillis / 1000.0);
            }
        }

        return performanceMap;
    }


    public List<Map.Entry<NeuralNetwork, Double>> getTopPerformingNetworks(Map<NeuralNetwork, Double> performanceMap) {
        return performanceMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())  // Sort by value (time survived)
                .limit(10)
                .collect(Collectors.toList());
    }

    public NeuralNetwork[] getTop10Networks(NeuralNetwork[] neuralNetworksArray) {
        Map<NeuralNetwork, Double> performanceMap = networksPlay(neuralNetworksArray);
        List<Map.Entry<NeuralNetwork, Double>> topNetworks = getTopPerformingNetworks(performanceMap);

        NeuralNetwork[] top10 = new NeuralNetwork[topNetworks.size()];
        for (int i = 0; i < topNetworks.size(); i++) {
            top10[i] = topNetworks.get(i).getKey();
        }

        return top10;
    }

        private double calculateAngle() {
            double paddleCenterX = paddle.getX() + paddle.getWidth() / 2;
            double deltaX = ball.getX() - paddleCenterX;
            double deltaY = ball.getY() - paddle.getY();
            return Math.toDegrees(Math.atan2(deltaY, deltaX));
        }

    private void movePaddleLeft(double movementSpeed) {
        for (int i = 0; i < movementSpeed; i++) {
            controller.handleKeyEvent(KeyEvent.VK_LEFT);
        }
    }

    private void movePaddleRight(double movementSpeed) {
        for (int i = 0; i < movementSpeed; i++) {
            controller.handleKeyEvent(KeyEvent.VK_RIGHT);
        }
    }
}




