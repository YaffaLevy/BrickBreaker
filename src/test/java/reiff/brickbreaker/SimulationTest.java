package reiff.brickbreaker;

import levy.brickbreaker.Ball;
import levy.brickbreaker.Paddle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void advance() {
        //given

        //when

        //then
    }

    @Test
    void wallCollisions() {
        //given


        //when

        //then
    }

    @Test
    void topCollision() {
        //given

        //when

        //then
    }

    @Test
    void paddleCollision() {
        //given
        Ball ball = new Ball(390, 510, 20, 20, 20, 5, 45);
        Paddle paddle = new Paddle(350, 550, 100, 10, 20);

        //when


        //then
    }
}