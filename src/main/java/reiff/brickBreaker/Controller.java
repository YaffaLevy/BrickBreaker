package reiff.brickBreaker;

import levy.brickBreaker.Ball;
import levy.brickBreaker.Bricks;
import levy.brickBreaker.Paddle;
import levy.brickBreaker.Wall;
import lesser.brickBuilder.BrickBreakerComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Controller {
    private final Ball ball;
    private final Paddle paddle;
    private final List<Bricks> bricks;
    private final BrickBreakerComponent view;

    public Controller(Ball ball, Paddle paddle, List<Bricks> bricks, BrickBreakerComponent view) {
        this.ball = ball;
        this.paddle = paddle;
        this.bricks = bricks;
        this.view = view;

        Timer timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBallPosition();
            }
        });
        timer.start();
    }
// check which timer to use
    public void updateBallPosition() {
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
        for (Bricks brick : bricks) {
            if (brick.isDestroyed() &&
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
