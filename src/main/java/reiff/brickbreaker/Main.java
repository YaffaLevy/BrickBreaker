package reiff.brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import lesser.brickbreaker.BrickBreakerFrame;

import java.io.IOException;

import static basicneuralnetwork.NeuralNetwork.readFromFile;

public class Main {

    public static void main(String[] args) throws IOException {
        NeuralNetwork topNW = MainPlay.bestNetwork.readFromFile("BestNW.json");
        new BrickBreakerFrame(topNW).setVisible(true);

    }

}