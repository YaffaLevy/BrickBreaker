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
    private Wall wall;

    public Controller(Ball ball, Paddle paddle, List<Bricks> bricks, BrickBreakerComponent view) {
        this.ball = ball;
        this.paddle = paddle;
        this.bricks = bricks;
        this.view = view;

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
        // Check for collision with left or right wall (horizontal reflection)
        if (ball.getX() <= 0 || ball.getX() >= view.getWidth() - ball.getDiameter()) {
            ball.setDirectionDegrees((180 - ball.getDirectionDegrees() + 360) % 360);
        }

        // Check for collision with top or bottom wall (vertical reflection)
        if (ball.getY() <= 0 || ball.getY() >= view.getHeight() - ball.getDiameter()) {
            ball.setDirectionDegrees((360 - ball.getDirectionDegrees()) % 360);
        }
    }


    private void checkPaddleCollision() {
        if (ball.getY() + ball.getDiameter() >= paddle.getY() &&
                ball.getX() >= paddle.getX() &&
                ball.getX() <= paddle.getX() + paddle.getWidth()) {

            // Define key positions on the paddle
            double paddleCenterX = paddle.getX() + paddle.getWidth() / 2;
            double edgeZoneWidth = paddle.getWidth() * 0.1;

            double leftEdgeEnd = paddle.getX() + edgeZoneWidth;
            double leftHalfEnd = paddleCenterX;
            double rightHalfStart = paddleCenterX;
            double rightEdgeStart = paddle.getX() + paddle.getWidth() - edgeZoneWidth;

            double ballAngle = ball.getDirectionDegrees();

            // Check and respond to collisions with specific paddle sections
            if (ball.getX() >= leftEdgeEnd &&
                    ball.getX() <= leftHalfEnd &&
                    (ballAngle > 270 || ballAngle < 90)) {
                // Ball hits left half from the right side
                ball.setDirectionDegrees((180 - ballAngle + 360) % 360);
            }
            else if (ball.getX() <= rightEdgeStart &&
                    ball.getX() >= rightHalfStart &&
                    (ballAngle > 90 && ballAngle < 270)) {
                // Ball hits right half from the left side
                ball.setDirectionDegrees((180 - ballAngle + 360) % 360);
            }
            else if ((ball.getX() <= leftEdgeEnd && ballAngle > 270) ||
                    (ball.getX() >= rightEdgeStart && ballAngle < 90)) {
                // Ball hits edge zones
                ball.setDirectionDegrees((360 - ballAngle) % 360);
            }
            else if (ball.getX() >= paddleCenterX - 2 && ball.getX() <= paddleCenterX + 2) {
                // Ball hits the center of the paddle
                ball.setDirectionDegrees(270); // Bounces straight up
            }
            else {
                // Default vertical bounce if no other case matches
                ball.setDirectionDegrees((360 - ballAngle) % 360);
            }
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
