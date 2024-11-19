
package reiff.brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import levy.brickbreaker.Ball;
import levy.brickbreaker.Paddle;

import java.util.*;
import java.util.stream.Collectors;


public class ManyNetworks {

    private final Ball ball;
    private final Paddle paddle;
    private final Controller controller;
    private Random random;

    public ManyNetworks(Ball ball, Paddle paddle, Controller controller) {
        this.ball = ball;
        this.paddle = paddle;
        this.controller = controller;
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

        for (NeuralNetwork neuralNetwork : neuralNetworksArray) {

            controller.startGame();

            long startTime = 0;
            while (!controller.isGameStopped()) {

                startTime = System.nanoTime();

                controller.updateBallPosition();

                double[] input = new double[1];
                input[0] = calculateAngle(ball.getX(), ball.getY(), paddle.getX(), paddle.getWidth());
                double[] answer = neuralNetwork.guess(input);

                // answer[0] corresponds to probability of going left and answer[1] of going right
                double movementSpeed = Math.abs(answer[0] - answer[1]);
                if (answer[0] > answer[1]) {
                    movePaddleLeft(movementSpeed); //Ilana implement
                } else {
                    movePaddleRight(movementSpeed); //Ilana implement
                }

            }

            long endTime = System.currentTimeMillis();
            double timeSurvived = (endTime - startTime) / 1000.0;

            if (controller.won()) {
                performanceMap.put(neuralNetwork, timeSurvived);
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

        private double calculateAngle(double x, double y, double x1, double width) {
            double paddleCenterX = paddle.getX() + paddle.getWidth() / 2;
            double deltaX = ball.getX() - paddleCenterX;
            double deltaY = ball.getY() - paddle.getY();
            return Math.toDegrees(Math.atan2(deltaY, deltaX));
        }
}




