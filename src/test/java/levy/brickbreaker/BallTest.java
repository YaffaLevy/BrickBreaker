package levy.brickbreaker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BallTest {

    @Test
    void move() {

        //give
        Ball ball = new Ball(390, 510, 20, 20, 20, 5, 45);
        ball.dx = 5;
        ball.dy = 3;
        //when

        ball.move();
        //then
        assertEquals(ball.getX(), 395);
        assertEquals(ball.getY(), 513);
    }

    @Test
    void collides() {
        //given

        Ball ball = new Ball(350.5, 550, 20, 20, 20, 5, 45);
        Paddle paddle = new Paddle(350, 550, 100, 10, 20);

        //when
        boolean collied =ball.collides(paddle);

        //then
        assertTrue(collied);
    }

    @Test
    void collidesRightWall() {

        //given
        Ball ball = new Ball(390, 510, 20, 20, 20, 5, 45);
        ball.dx = 5;

        //when
        ball.collidesRightWall();


        //then
        assertEquals(-5, ball.dx);
    }

    @Test
    void collidesLeftWall() {
        //given
        Ball ball = new Ball(390, 510, 20, 20, 20, 5, 45);
        ball.dx = -4;

        //when
        ball.collidesLeftWall();

        //then
        assertEquals(4, ball.dx);
    }

    @Test
    void collidesTopWall() {

        //given
        Ball ball = new Ball(390, 510, 20, 20, 20, 5, 45);
        ball.dy = 3;
        //when
        ball.collidesTopWall();

        //then
        assertEquals(-3, ball.dy);
    }
}