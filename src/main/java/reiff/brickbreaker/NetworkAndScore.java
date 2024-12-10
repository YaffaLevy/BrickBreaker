package reiff.brickbreaker;

import basicneuralnetwork.NeuralNetwork;

public class NetworkAndScore {

    private final NeuralNetwork network;
    private final int score;

    private final long seed;

    public NetworkAndScore(NeuralNetwork network, int score, long seed) {
        this.network = network;
        this.score = score;
        this.seed = seed;
    }

    public NeuralNetwork getNetwork() {
        return network;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "NetworkAndScore{"
                +
                "network=" + network
                +
                ", score=" + score
                +
                ", seed=" + seed
                +
                '}';
    }
}
