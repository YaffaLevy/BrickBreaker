package reiff.brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import lesser.brickbreaker.BrickBreakerFrame;
import levy.brickbreaker.Ball;
import levy.brickbreaker.Brick;
import levy.brickbreaker.Paddle;
import lesser.brickbreaker.BrickBreakerComponent;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

public class Controller {
    private final Ball ball;
    private final Paddle paddle;
    private final List<Brick> bricks;
    private final BrickBreakerComponent view;
    private boolean isGameRunning = false;
    private int paddleHit = 0;
    private static final int COLS = 10;
    private static final int ROWS = 5;
    private static final int BRICK_WIDTH = 60;
    private static final int BRICK_HEIGHT = 20;
    private static final int SPACING = 10;
    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;
    public int won = 0;

    public Controller(Ball ball, Paddle paddle, List<Brick> bricks, BrickBreakerComponent view) {
        this.ball = ball;
        this.paddle = paddle;
        this.bricks = bricks;
        this.view = view;

    }

    public void handleKeyEvent(int keyCode) {
        int paddleSpeed = paddle.getSpeed();

        // Move paddle left or right
        if (keyCode == KeyEvent.VK_LEFT) {
            paddle.x = Math.max(0, paddle.x - paddleSpeed);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            paddle.x = Math.min(GAME_WIDTH - paddle.width, paddle.x + paddleSpeed);
        }

        // Start the game when the UP arrow key is pressed and the game isn't already running
        if (keyCode == KeyEvent.VK_UP && !isGameRunning) {
            startGame();
        }

        view.repaint();
    }

    public void updateBallPosition() {
        if (!isGameRunning) {
            return;
        }

               double radians = Math.toRadians(ball.getDirectionDegrees());
        double dx = Math.cos(radians) * ball.getSpeed();
        double dy = Math.sin(radians) * ball.getSpeed();

        ball.setX(ball.getX() + dx);
        ball.setY(ball.getY() + dy);

        checkCollisions();
        view.repaint();
    }

    private void checkCollisions() {
        checkWallCollisions();
        checkPaddleCollision();
        checkBrickCollisions();
    }

    private void checkWallCollisions() {
        if (ball.getX() <= 0 || ball.getX() >= GAME_WIDTH - ball.getDiameter()) {
            ball.setDirectionDegrees(180 - ball.getDirectionDegrees());
        } else if (ball.getY() <= 0) {
            ball.setDirectionDegrees(-ball.getDirectionDegrees());
        } else if (ball.getY() >= GAME_HEIGHT) {
            stopGame();
        }
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
        resetBricks();
        paddleHit = 0;
    }

    public void startGame() {
        isGameRunning = true;
    }

    public boolean isGameStopped() {
        return !isGameRunning;
    }

    public boolean won() {
        return won == 1;
    }

    public void resetBricks() {
        bricks.clear();
        initializeBricks();
        view.repaint();
        paddleHit = 0;
    }


    public void initializeBricks() {
        Random random = new Random();
        int xoffset = (view.getWidth() - (COLS * (BRICK_WIDTH + SPACING) - SPACING)) / 2;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (random.nextBoolean()) {
                    int x = xoffset + col * (BRICK_WIDTH + SPACING);
                    int y = 50 + row * (BRICK_HEIGHT + SPACING);
                    bricks.add(new Brick(x, y, BRICK_WIDTH, BRICK_HEIGHT));
                }
            }
        }
    }

    public void startGameForNw(NeuralNetwork bestNw){
        Timer gameTimer = new Timer(50, e -> {

            startGame();
            handleKeyEvent(KeyEvent.VK_UP);

            int round = 0;
            while (round < 10000 && !isGameStopped()) {


                updateBallPosition();

                double[] input = new double[2];
                input[0] = ball.x;
                input[1] = paddle.x;

                double[] answer = bestNw.guess(input);

                double leftConfidence = answer[0];
                double rightConfidence = answer[1];


                // Simulate key presses based on the neural network's confidence in the choice
                if (leftConfidence > rightConfidence) {
                    handleKeyEvent(KeyEvent.VK_LEFT);
                } else {
                    handleKeyEvent(KeyEvent.VK_RIGHT);
                }

                round++;
            }
        });
        gameTimer.start();
    }

    private void checkPaddleCollision() {
        if (ball.getY() + ball.getDiameter() >= paddle.getY()
                && ball.getX() >= paddle.getX()
                && ball.getX() <= paddle.getX() + paddle.getWidth()) {

            double paddleCenterX = paddle.getCenterX();
            double edgeZoneWidth = paddle.getWidth() * 0.1;

            double leftEdgeEnd = paddle.getX() + edgeZoneWidth;
            double rightEdgeStart = paddle.getX() + paddle.getWidth() - edgeZoneWidth;

            double ballAngle = ball.getDirectionDegrees();

            /*
            if (ball.getX() >= leftEdgeEnd
                    && ball.getX() <= paddleCenterX
                    && (ballAngle > 270 || ballAngle < 90)) {
                ball.setDirectionDegrees((180 + ball.getDirectionDegrees()) % 360);
            } else if (ball.getX() <= rightEdgeStart
                    && ball.getX() >= paddleCenterX
                    && (ballAngle > 90 && ballAngle < 270)) {
                ball.setDirectionDegrees((180 + ball.getDirectionDegrees()) % 360);
            } else if ((ball.getX() <= leftEdgeEnd && ballAngle > 270)
                    || (ball.getX() >= rightEdgeStart && ballAngle < 90)) {
                ball.setDirectionDegrees(-ball.getDirectionDegrees());
            } else if (ball.getX() >= paddleCenterX - 2 && ball.getX() <= paddleCenterX + 2) {
                ball.setDirectionDegrees(270);
            } else {
                ball.setDirectionDegrees(-ball.getDirectionDegrees());
            }

             */

           // ballAngle = ((ball.getDirectionDegrees() % 360) + 360) % 360;
            if (ballAngle == 135){
                ball.setDirectionDegrees(225);
            } else if (ballAngle == 45) {
                ball.setDirectionDegrees(315);
            } else {
               // System.out.println("Ball going at angle: "+ ballAngle);
                return; //maybe there is another angle that the ball can go at?
            }

            paddleHit++;
        }
    }

    public int getPaddleHit(){
        return paddleHit;
    }


    private void checkBrickCollisions() {

        for (Brick brick : bricks) {
            if (!brick.isDestroyed()
                    && ball.getX() + ball.getDiameter() >= brick.getX()
                    && ball.getX() <= brick.getX() + brick.getWidth()
                    && ball.getY() + ball.getDiameter() >= brick.getY()
                    && ball.getY() <= brick.getY() + brick.getHeight()) {

                brick.setDestroyed(true);
                bricks.remove(brick);
                ball.setDirectionDegrees(-ball.getDirectionDegrees());
                break;
            }
        }

        if (bricks.isEmpty()) {
            isGameRunning = false;
            won = 1;
            stopGame();
        }
    }

}