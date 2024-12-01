package reiff.brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import levy.brickbreaker.Ball;
import levy.brickbreaker.Paddle;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Simulation {

    private NeuralNetwork neuralNetwork;
    private Ball ball;
    private Paddle paddle;
    private int width;
    private int height;
    private boolean isGameRunning = false;
    private int paddleHit = 0;

    public Simulation (NeuralNetwork neuralNetwork, Ball ball, Paddle paddle, int width, int height){
        this.neuralNetwork = neuralNetwork;
        this.ball = ball;
        this.paddle = paddle;
        this.width = width;
        this.height = height;
    }

    public boolean advance(){

                ball.move();
                checkCollisions();

                double[] input = new double[2];
                input[0] = ball.x;
                input[1] = paddle.x;

                double[] answer = neuralNetwork.guess(input);

                double leftConfidence = answer[0];
                double rightConfidence = answer[1];


                // Simulate key presses based on the neural network's confidence in the choice
                if (leftConfidence > rightConfidence) {
                    movePaddleLeft();
                } else {
                    movePaddleRight();
                }


        return !(ball.getY() >= height);

    }

    public void checkCollisions(){
        wallCollisions();
        topCollision();
        brickCollision();
        paddleCollision();
    }

    public void wallCollisions(){

        if (ball.getX() <= 0) {
            ball.collidesLeftWall();
        }
        else if(ball.getX() >= width - ball.getDiameter()) {
            ball.collidesRightWall();
        }
    }

    public void topCollision(){
        if (ball.getY() <= 0){
            ball.collidesTopWall();
        }
    }

    public void brickCollision(){
        return;
    }

    public void paddleCollision(){

        if(ball.collides(paddle)){
            ball.dy = -ball.dy;
            ball.dx = ((paddle.getCenterX() - ball.getCenterX()) / (paddle.width)/2);
            paddleHit++;
        } else if (ball.getY() >= height) {
            stopGame();
        }


    }

    private void movePaddleLeft(){

            paddle.x = Math.max(0, paddle.x - paddle.getSpeed());


        }

    private void movePaddleRight(){

        paddle.x = Math.min(width - paddle.width, paddle.x + paddle.getSpeed());

    }

    public void startGame() {
        isGameRunning = true;
    }

    public void stopGame() {
        isGameRunning = false;
    }

    public void resetGame() {
        ball.setX(390);
        ball.setY(510);
        ball.setDirectionDegrees(45);
        paddle.setX(350);
        paddle.setY(550);
        isGameRunning = false;
       // resetBricks();
        paddleHit = 0;
    }

    public boolean isGameStopped() {
        return !isGameRunning;
    }


    public int getScore(){
        return paddleHit;
    }

}
