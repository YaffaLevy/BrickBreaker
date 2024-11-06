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
        Paddle paddle = new Paddle((int)350.0, (int)550.0, (int)100.0, (int)20.0, 20);
        bricks = new ArrayList<>();
        bricks.add(new Bricks((int)90.0, (int)90.0, 60, 20));
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

    @Test
    void testCheckWallCollisions() {
        // Given: Ball is near the left wall
        ball.setX(5.0);  // Ball near the left boundary
        ball.setDiameter(20.0);
        //view.setWidth(800);  // View width is 800px

        // When: Update ball position (simulate wall collision)
        controller.updateBallPosition();

        // Then: Ball's direction should be inverted after collision
        assertEquals(135.0, ball.getDirectionDegrees(), "Ball's direction should be 135 degrees after hitting left wall");
    }

    @Test
    void testCheckPaddleCollision() {
        // Given: Ball is near the paddle
        ball.setX(350.0);
        ball.setY(540.0);
        ball.setDiameter(20.0);

        // When: Update ball position (simulate collision with paddle)
        controller.updateBallPosition();

        // Then: Ball's direction should be inverted after hitting the paddle
        assertEquals(-45.0, ball.getDirectionDegrees(), "Ball's direction should be inverted after hitting the paddle");
    }

    @Test
    void testCheckBrickCollisions() {
        // Given: Ball is near the brick and not yet destroyed
        ball.setX(100.0);
        ball.setY(100.0);
        ball.setDiameter(20.0);

        // When: Update ball position (simulate collision with brick)
        controller.updateBallPosition();

        // Then: The brick should be destroyed, and the ball's direction should be updated
        Bricks hitBrick = bricks.get(0);
        assertTrue(hitBrick.isDestroyed(), "Brick should be destroyed after collision");
        assertEquals(135.0, ball.getDirectionDegrees(), "Ball's direction should be inverted after hitting the brick");
    }
}
