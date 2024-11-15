
package reiff.brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import levy.brickbreaker.Ball;
import levy.brickbreaker.Paddle;

public class ManyNetwroks {

    private final Ball ball;
    private final Paddle paddle;

    public ManyNetwroks(Ball ball, Paddle paddle) {
        this.ball = ball;
        this.paddle = paddle;
    }

    public NeuralNetwork [] generateNetworks() {

        NeuralNetwork[] neuralNetworksArray = new NeuralNetwork[1000];

        for (int i = 0; i < 1000; i++) {
            neuralNetworksArray[i] = new NeuralNetwork(2, 2, 4, 2);
        }

        return neuralNetworksArray;
    }
    //now we have an array of 1000 netwrok, one in each position.

    public void networksPlay(NeuralNetwork[] neuralNetworksArray){

        for (int i = 0; i < neuralNetworksArray.length; i++) {

            double[] input = new double[1];
            //Ilana: make a formula that calculates the angles from the ball to the paddle
            input[0] = // angle from the ball to the paddle
            double[] answer = neuralNetworksArray[i].guess(input);

           //we have to have it do this* until it hits the else (it died, game over), and somehow measure how long
            //did it take to get loose,
            double movementSpeed = Math.abs(answer[0] - answer[1]);
            if (answer[0] > answer[1]) {
                movePaddleLeft(movementSpeed); //Ilana implement
            } else {
                movePaddleRight(movementSpeed); //Ilana implement
            }

            if (//there was a paddle colision){
            //it kept playing, good
        } else {
            //it died, game over
        }

        //*this is until here
        //At some point we can check if controller.won is true, if so, how long did it take it to get there. Based on
        //that we'll give each neuro netwrok a grade (array of grades, the higher the better, it should be doubles)
        // We'll choose the top 10 from there.
        //who won the fastest, this means he knows how to aim.

        //Stepts

    }
}



