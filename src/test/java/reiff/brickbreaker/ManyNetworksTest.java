/*
package reiff.brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import lesser.brickbreaker.BrickBreakerComponent;
import levy.brickbreaker.Ball;
import levy.brickbreaker.Brick;
import levy.brickbreaker.Paddle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManyNetworksTest {

    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;
    private BrickBreakerComponent view;
    private Controller controller;
    private ManyNetworks manyNetworks;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        ball = mock(Ball.class);
        paddle = mock(Paddle.class);
        view = mock(BrickBreakerComponent.class);
        bricks = new ArrayList<>();

        // Initialize controller with mocks
        controller = new Controller(ball, paddle, bricks, view);

        // Initialize ManyNetworks
        manyNetworks = new ManyNetworks(ball, paddle, controller, view);
    }

    @Test
    void testNetworksPlay_ExecutesWithoutCrashing() {
        // Act & Assert: Ensure no exception is thrown
        assertDoesNotThrow(() -> manyNetworks.networksPlay(manyNetworks.generateNetworks()),
                "networksPlay should execute without crashing.");
    }
}
*/