package reiff.brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import lesser.brickbreaker.BrickBreakerFrame;

import java.io.IOException;

import static basicneuralnetwork.NeuralNetwork.readFromFile;

public class Main {

    public static void main(String[] args) throws IOException {
        new BrickBreakerFrame().setVisible(true);
        readFromFile("BestNW.json");
    }

}