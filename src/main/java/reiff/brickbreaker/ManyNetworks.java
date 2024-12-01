
package reiff.brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import lesser.brickbreaker.BrickBreakerComponent;
import levy.brickbreaker.Ball;
import levy.brickbreaker.Paddle;

import java.awt.event.KeyEvent;
import java.util.*;
import java.util.stream.Collectors;


public class ManyNetworks {
    //Here we are creating 1000 networks and storing them in an array.
    public List<NeuralNetwork> generateNetworks() {

        List<NeuralNetwork> neuralNetworkList = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            neuralNetworkList.add(new NeuralNetwork(2, 2, 4, 2));
        }

        return neuralNetworkList;
    }
    //now we have a list of 1000 network, one in each position.

    public List<NeuralNetwork> createNextGeneration(List<NeuralNetwork> topPerformingNetworks) {

        List<NeuralNetwork> nextGeneration = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < 1000; i++) {
            int index1 = rand.nextInt(topPerformingNetworks.size());
            int index2;

            do {
                index2 = rand.nextInt(topPerformingNetworks.size());
            } while (index1 == index2);

            NeuralNetwork parent1 = topPerformingNetworks.get(index1);
            NeuralNetwork parent2 = topPerformingNetworks.get(index2);

            NeuralNetwork child = parent1.merge(parent2);
            child.mutate(0.1);

            nextGeneration.add(child);
        }

        return nextGeneration;
    }

    //Here we are going to let each network play

//    public List<NetworkAndScore> networksPlay(List<NeuralNetwork> neuralNetworksArray) {
//
//        List<NetworkAndScore> performanceList = new ArrayList<>();
//
//        for (NeuralNetwork neuralNetwork : neuralNetworksArray) {
//
//            controller.resetGame();
//            controller.startGame();
//
//            int round = 0;
//            int score = 0;
//            while (round < 10000 && !controller.isGameStopped()) {
//
//
//                controller.updateBallPosition();
//
//                double[] input = new double[2];
//                input[0] = ball.x;
//                input[1] = paddle.x;
//
//                double[] answer = neuralNetwork.guess(input);
//
//                double leftConfidence = answer[0];
//                double rightConfidence = answer[1];
//
//
//                // Simulate key presses based on the neural network's confidence in the choice
//                if (leftConfidence > rightConfidence) {
//                    movePaddleLeft();
//                } else {
//                    movePaddleRight();
//                }
//
//                score = controller.getPaddleHit();
//                round++;
//            }
//
//            NetworkAndScore neuralandscore = new NetworkAndScore(neuralNetwork, score);
//            performanceList.add(neuralandscore);
//
//        }
//
//        return performanceList;
//    }


    public List<NetworkAndScore> getTop10NetworksWithScores(List<NetworkAndScore> performanceList) {
        //List<NetworkAndScore> performanceList = networksPlay(neuralNetworks); // Calculate scores
        return performanceList.stream()
                .sorted(Comparator.comparingInt(NetworkAndScore::getScore).reversed()) // Sort by score
                .limit(10) // Get the top 10
                .collect(Collectors.toList());
    }

}




