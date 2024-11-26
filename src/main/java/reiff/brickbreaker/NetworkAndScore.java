package reiff.brickbreaker;

import basicneuralnetwork.NeuralNetwork;

public class NetworkAndScore {
    private NeuralNetwork network;
    private int score;
    public NetworkAndScore(NeuralNetwork network, int score) {
        this.network = network;
        this.score = score;
    }

    public NeuralNetwork getNetwork() {
        return network;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "NetworkAndScore{" +
                "network=" + network +
                ", score=" + score +
                '}';
    }
}
