package reiff.brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import levy.brickbreaker.Ball;
import levy.brickbreaker.Paddle;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Simulation {

    private final long seed;
    private Random random;

    private NeuralNetwork neuralNetwork;
    private Ball ball;
    private Paddle paddle;
    private int width;
    private int height;
    private boolean isGameRunning = true;
    private int paddleHit = 0;
    private boolean inPaddle = false;

    public Simulation(NeuralNetwork neuralNetwork, long seed, Ball ball, Paddle paddle, int width, int height) {
        this.seed = seed;
        this.random = new Random(seed);
        this.neuralNetwork = neuralNetwork;
        this.ball = ball;
        this.paddle = paddle;
        this.width = width;
        this.height = height;
        resetGame();
    }

    public boolean advance() {

        ball.move();

        double[] input = new double[2];
        input[0] = ball.getCenterX();
        input[1] = paddle.getCenterX();

        double[] answer = neuralNetwork.guess(input);
        double leftConfidence = answer[0];
        double rightConfidence = answer[1];

        if (leftConfidence > rightConfidence) {
            movePaddleLeft();
        } else {
            movePaddleRight();
        }

        checkCollisions();

        return !(ball.getY() >= height);

    }

    public void checkCollisions() {
        wallCollisions();
        topCollision();
        brickCollision();
        paddleCollision();
    }

    public void wallCollisions() {

        if (ball.getX() <= 0) {
            ball.collidesLeftWall();
        } else if (ball.getX() >= width - ball.getWidth()) {
            ball.collidesRightWall();
        } else if (ball.getY() >= height) {
            stopGame();
        }
    }

    public void topCollision() {
        if (ball.getY() <= 0) {
            ball.collidesTopWall();
        }
    }

    public void brickCollision(){
    }

    public void paddleCollision() {

        if (ball.collides(paddle)) {
            if (!inPaddle) {
                ball.dy = -Math.abs(ball.dy);
                //ball.dx = ((paddle.getCenterX() - ball.getCenterX()) / (paddle.width) / 2);
                paddleHit++;
                System.out.println("Score: " + paddleHit);
                inPaddle = true;
            }
            return;
        }
        inPaddle = false;
    }

    private void movePaddleLeft() {

        paddle.x = Math.max(0, paddle.x - paddle.getSpeed());

    }

    private void movePaddleRight() {

        paddle.x = Math.min(width - paddle.width, paddle.x + paddle.getSpeed());

    }

    public void stopGame() {
        isGameRunning = false;
    }

    public void resetGame() {
        ball.setX(random.nextInt(800));
        ball.setY(510);
        ball.setDirectionDegrees(45);
        ball.initializeVelocity();
        paddle.setX(random.nextInt(800));
        paddle.setY(550);
        isGameRunning = true;
        inPaddle = false;
        // resetBricks();
        paddleHit = 0;
    }

    public boolean isGameStopped() {
        return !isGameRunning;
    }


    public int getScore() {
        return paddleHit;
    }

    public long getSeed() {
        return seed;
    }
}
