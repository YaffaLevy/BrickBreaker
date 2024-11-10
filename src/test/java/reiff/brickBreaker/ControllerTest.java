/*
package reiff.brickBreaker;

import levy.brickBreaker.Ball;
import levy.brickBreaker.Bricks;
import levy.brickBreaker.Paddle;
import levy.brickBreaker.Wall;
import lesser.brickBuilder.BrickBreakerComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reiff.brickBreaker.Controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private Ball ball;
    private List<Bricks> bricks;
    private BrickBreakerComponent view;
    private Controller controller;

    @BeforeEach
    void setUp() {
        // Initialize the actual objects, no mocking
        ball = new Ball(100.0, 100.0, 5.0, 45.0, 20.0);
        Paddle paddle = new Paddle((int) 350.0, (int) 550.0, (int) 100.0, (int) 20.0, 20);
        bricks = new ArrayList<>();
        bricks.add(new Bricks((int) 90.0, (int) 90.0, 60, 20));
        List<Wall> walls = new ArrayList<>();
        view = new BrickBreakerComponent(ball, paddle, bricks);
        controller = new Controller(ball, paddle, bricks, view);
    }

    @Test
    void testUpdateBallPosition() {
        // Given: Ball starts at (100, 100) moving at 5 speed at 45 degrees.
        double initialX = ball.getX();
        double initialY = ball.getY();

        // When: Update ball position
        controller.updateBallPosition();

        // Then: Ball's position should be updated
        assertNotEquals(initialX, ball.getX(), "Ball's X position should have changed");
        assertNotEquals(initialY, ball.getY(), "Ball's Y position should have changed");
    }
}

 */