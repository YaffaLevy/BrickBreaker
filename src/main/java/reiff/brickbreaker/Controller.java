package reiff.brickbreaker;

import lesser.brickbreaker.BrickBreakerFrame;
import levy.brickbreaker.Ball;
import levy.brickbreaker.Brick;
import levy.brickbreaker.Paddle;
import lesser.brickbreaker.BrickBreakerComponent;

import javax.swing.*;
import java.util.List;

public class Controller {
    private final Ball ball;
    private final Paddle paddle;
    private final List<Brick> bricks;
    private final BrickBreakerComponent view;
    private boolean isGameRunning = false;

    public int won = 0;

    public Controller(Ball ball, Paddle paddle, List<Brick> bricks, BrickBreakerComponent view) {
        this.ball = ball;
        this.paddle = paddle;
        this.bricks = bricks;
        this.view = view;

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
        if (ball.getX() <= 0 || ball.getX() >= view.getWidth() - ball.getDiameter()) {
            ball.setDirectionDegrees(180 - ball.getDirectionDegrees());
        } else if (ball.getY() <= 0) {
            ball.setDirectionDegrees(-ball.getDirectionDegrees());
        } else if (ball.getY() >= view.getHeight()) {
            resetGame();
        }
    }

    private void resetGame() {
        ball.setX(390);
        ball.setY(510);
        ball.setDirectionDegrees(45);
        paddle.setX(350);
        paddle.setY(550);
        isGameRunning = false;
        ((BrickBreakerFrame) SwingUtilities.getWindowAncestor(view)).resetBricks();
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

    private void checkPaddleCollision() {
        if (ball.getY() + ball.getDiameter() >= paddle.getY()
                && ball.getX() >= paddle.getX()
                && ball.getX() <= paddle.getX() + paddle.getWidth()) {

            double paddleCenterX = paddle.getX() + paddle.getWidth() / 2;
            double edgeZoneWidth = paddle.getWidth() * 0.1;

            double leftEdgeEnd = paddle.getX() + edgeZoneWidth;
            double rightEdgeStart = paddle.getX() + paddle.getWidth() - edgeZoneWidth;

            double ballAngle = ball.getDirectionDegrees();

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
        }
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
            resetGame();
        }
    }

}