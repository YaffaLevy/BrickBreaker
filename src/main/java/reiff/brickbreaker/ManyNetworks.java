
package reiff.brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import java.util.*;
import java.util.stream.Collectors;


public class ManyNetworks {
    public List<NeuralNetwork> generateNetworks() {

        List<NeuralNetwork> neuralNetworkList = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            neuralNetworkList.add(new NeuralNetwork(4, 2, 4, 2));
        }

        return neuralNetworkList;
    }

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


    public List<NetworkAndScore> getTop10NetworksWithScores(List<NetworkAndScore> performanceList) {
        return performanceList.stream()
                .sorted(Comparator.comparingInt(NetworkAndScore::getScore).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

}




