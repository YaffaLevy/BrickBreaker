package reiff.brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import levy.brickbreaker.Ball;
import levy.brickbreaker.Brick;
import levy.brickbreaker.Paddle;
import java.util.Random;

public class Simulation {

    private final long seed;
    private final Random random;

    private final NeuralNetwork neuralNetwork;
    private final Ball ball;
    private final Paddle paddle;
    private final int width;
    private final int height;
    private Brick brick;
    private boolean isGameRunning = true;
    private boolean inPaddle = false;
    private boolean hitBrick = false;
    private boolean hitPaddle = false;
    private int score = 0;
    private final BrickFactory brickFactory;


    public Simulation(NeuralNetwork neuralNetwork, long seed, Ball ball, Paddle paddle, int width, int height, BrickFactory brickFactory) {
        this.seed = seed;
        this.random = new Random(seed);
        this.neuralNetwork = neuralNetwork;
        this.ball = ball;
        this.paddle = paddle;
        this.width = width;
        this.height = height;
        this.brickFactory = brickFactory;
        this.brick = brickFactory.newBrick();
        resetGame();

    }


    public boolean advance() {

        ball.move();

        double[] input = new double[4];
        input[0] = ball.getCenterX();
        input[1] = paddle.getCenterX();
        input[2] = brick.getCenterX();
        input[3] = brick.getCenterY();

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

    public void brickCollision() {
        if (!brick.isDestroyed()
                && ball.getX() + ball.getWidth() >= brick.getX()
                && ball.getX() <= brick.getX() + brick.getWidth()
                && ball.getY() + ball.getHeight() >= brick.getY()
                && ball.getY() <= brick.getY() + brick.getHeight()) {

            brick.setDestroyed(true);
            ball.collidesBrick();
            hitBrick = true;

            if (hitPaddle) {
                score++;
                hitPaddle = false;
            }

            brick = brickFactory.newBrick();
        }
    }

    public void paddleCollision() {

        if (ball.collides(paddle)) {
            if (!inPaddle) {
                ball.dy = -Math.abs(ball.dy);
                ball.dx = ((paddle.getCenterX() - ball.getCenterX()) / (paddle.width) / 2);
                inPaddle = true;

                if (hitBrick) {
                    score++;
                    hitBrick = false;
                }
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
        hitBrick = false;
        hitPaddle = false;
    }

    public boolean isGameStopped() {
        return !isGameRunning;
    }


    public int getScore() {
        return score;
    }

    public long getSeed() {
        return seed;
    }

    public Brick getBrick(){
        return this.brick;
    }
}


