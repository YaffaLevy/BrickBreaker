package reiff.brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import lesser.brickbreaker.BrickBreakerFrame;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        new BrickBreakerFrame().setVisible(true);
        NeuralNetwork.readFromFile("BestNW");
    }

}