package reiff.brickBreaker;

import levy.brickBreaker.Ball;
import levy.brickBreaker.Bricks;
import levy.brickBreaker.Paddle;
import levy.brickBreaker.Wall;
import lesser.brickBuilder.BrickBreakerComponent;
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

    }

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
        else if (ball.getY() <= 0) {
            ball.setDirectionDegrees(-ball.getDirectionDegrees());
        }
    }




    private void checkPaddleCollision() {
        if (ball.getY() + ball.getDiameter() >= paddle.getY() &&
                ball.getX() >= paddle.getX() &&
                ball.getX() <= paddle.getX() + paddle.getWidth()) {

            double paddleCenterX = paddle.getX() + paddle.getWidth() / 2;
            double edgeZoneWidth = paddle.getWidth() * 0.1;

            double leftEdgeEnd = paddle.getX() + edgeZoneWidth;
            double rightEdgeStart = paddle.getX() + paddle.getWidth() - edgeZoneWidth;

            double ballAngle = ball.getDirectionDegrees();

            if (ball.getX() >= leftEdgeEnd &&
                    ball.getX() <= paddleCenterX &&
                    (ballAngle > 270 || ballAngle < 90)) {
                ball.setDirectionDegrees((180 + ball.getDirectionDegrees()) % 360);
            } else if (ball.getX() <= rightEdgeStart &&
                    ball.getX() >= paddleCenterX &&
                    (ballAngle > 90 && ballAngle < 270)) {
                ball.setDirectionDegrees((180 + ball.getDirectionDegrees()) % 360);
            } else if ((ball.getX() <= leftEdgeEnd && ballAngle > 270) ||
                    (ball.getX() >= rightEdgeStart && ballAngle < 90)) {
                ball.setDirectionDegrees(-ball.getDirectionDegrees());
            } else if (ball.getX() >= paddleCenterX - 2 && ball.getX() <= paddleCenterX + 2) {
                ball.setDirectionDegrees(270);
            } else {
                ball.setDirectionDegrees(-ball.getDirectionDegrees());
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