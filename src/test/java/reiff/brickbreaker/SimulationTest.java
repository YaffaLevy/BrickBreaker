package reiff.brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import levy.brickbreaker.Ball;
import levy.brickbreaker.Paddle;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {
        @Test
        void ballFallsBelowScreen() {
            // given
            Ball ball = new Ball(100, 590, 20, 20, 20, 5, 45);
            ball.dy = 10;
            Paddle paddle = new Paddle(50, 550, 100, 20, 10);
            NeuralNetwork neuralNetwork = mock(NeuralNetwork.class);
            when(neuralNetwork.guess(new double[]{100, 50})).thenReturn(new double[]{0.5, 0.5});

            Simulation simulation = new Simulation(neuralNetwork, ball, paddle, 800, 600);

            // when
            boolean result = simulation.advance();

            // then
            assertFalse(result);
        }

        @Test
        void paddleMovesRight() {
            // given
            Ball ball = new Ball(100, 100, 20, 20, 20, 5, 45);
            Paddle paddle = new Paddle(50, 550, 100, 20, 10);
            NeuralNetwork neuralNetwork = mock(NeuralNetwork.class);
            when(neuralNetwork.guess(new double[]{100, 50})).thenReturn(new double[]{0.3, 0.7});

            Simulation simulation = new Simulation(neuralNetwork, ball, paddle, 800, 600);

            // when
            simulation.advance();

            // then
            assertTrue(paddle.getX() > 50); // Paddle moved right
        }



    @Test
    void paddleCollision() {
        //given

        NeuralNetwork neuralNetwork = new NeuralNetwork(2, 2, 4, 2);
        Ball ball = new Ball(350.5, 550, 20, 20, 20, 5, 45);
        Paddle paddle = new Paddle(350, 550, 100, 10, 20);
        Simulation simulation = new Simulation(neuralNetwork, ball, paddle, 800, 600);

        ball.dx = 5;
        ball.dy = 3;

        //when

        simulation.paddleCollision();


        //then

        assertEquals(-3, ball.dy);
        assertEquals(((paddle.getCenterX() - ball.getCenterX()) / (paddle.width) / 2), ball.dx);
    }

    @Test
    void paddleCollisionNoCollision() {
        //given

        NeuralNetwork neuralNetwork = new NeuralNetwork(2, 2, 4, 2);
        Ball ball = new Ball(390, 510, 20, 20, 20, 5, 45);
        Paddle paddle = new Paddle(350, 550, 100, 10, 20);
        Simulation simulation = new Simulation(neuralNetwork, ball, paddle, 800, 600);

        ball.dx = 5;
        ball.dy = 3;

        //when

        simulation.paddleCollision();


        //then

        assertTrue(simulation.isGameStopped());
    }
}