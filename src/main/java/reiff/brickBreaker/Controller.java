package reiff.brickBreaker;

import lesser.brickBuilder.BrickBreakerComponent;
import levy.brickBreaker.Ball;
import levy.brickBreaker.Brick;
import levy.brickBreaker.Paddle;
import levy.brickBreaker.Wall;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Controller {
    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;
    private List<Wall> walls;
    private BrickBreakerComponent view;
    private Timer timer;

    public Controller(Ball ball, Paddle paddle, List<Brick> bricks, List<Wall> walls, BrickBreakerComponent view) {
        this.ball = ball;
        this.paddle = paddle;
        this.bricks = bricks;
        this.walls = walls;
        this.view = view;

    }

    public void updateBallPosition() {
        double radians = Math.toRadians(ball.getDirectionDegrees());
        double dx = Math.cos(radians) * ball.getSpeed();
        double dy = Math.sin(radians) * ball.getSpeed();

        ball.setX(ball.getX() + dx);
        ball.setY(ball.getY() + dy);

        checkCollisions();
        view.repaint();  // Trigger view refresh to show updated positions
    }

    public void movePaddleLeft() {
        int newX = paddle.getX() - paddle.getSpeed();
        paddle.setX(Math.max(0, newX));
        view.repaint();
    }

    public void movePaddleRight() {
        int newX = paddle.getX() + paddle.getSpeed();
        paddle.setX(Math.min(newX, view.getWidth() - paddle.getWidth()));
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
        }
        if (ball.getY() <= 0) {
            ball.setDirectionDegrees(-ball.getDirectionDegrees());
        }
    }

    private void checkPaddleCollision() {
        if (ball.getY() + ball.getDiameter() >= paddle.getY() &&
                ball.getX() >= paddle.getX() &&
                ball.getX() <= paddle.getX() + paddle.getWidth()) {
            ball.setDirectionDegrees(-ball.getDirectionDegrees());
        }
    }

    private void checkBrickCollisions() {
        for (Brick brick : bricks) {
            if (!brick.isDestroyed() &&
                    ball.getX() + ball.getDiameter() >= brick.getX() &&
                    ball.getX() <= brick.getX() + brick.getWidth() &&
                    ball.getY() + ball.getDiameter() >= brick.getY() &&
                    ball.getY() <= brick.getY() + brick.getHeight()) {

                brick.setDestroyed(true);
                ball.setDirectionDegrees(-ball.getDirectionDegrees());
                break;
            }
        }
    }
}
