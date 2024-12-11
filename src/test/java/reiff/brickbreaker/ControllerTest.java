/*
package reiff.brickbreaker;

import levy.brickbreaker.Ball;
import levy.brickbreaker.Brick;
import levy.brickbreaker.Paddle;
import lesser.brickbreaker.BrickBreakerComponent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class ControllerTest {

    @Test
    void ballMovesToNewPosition() {
        // Given
        Ball ball = mock();
        Paddle paddle = mock();
        List<Brick> bricks = new ArrayList<>();
        BrickBreakerComponent view = mock();
        final Controller controller = new Controller(ball, paddle, bricks, view);
        //When
        doReturn(800).when(view).getWidth();
        doReturn(600).when(view).getHeight();
        doReturn(100.0).when(ball).getX();
        doReturn(100.0).when(ball).getY();
        doReturn(5.0).when(ball).getSpeed();
        doReturn(45.0).when(ball).getDirectionDegrees();

        controller.startGame();
        controller.updateBallPosition();
        // then
        verify(ball).setX(anyDouble());
        verify(ball).setY(anyDouble());
        verify(view).repaint();
    }

    @Test
    void ballChangesDirectionOnPaddleHit() {
        //Given
        Ball ball = mock();
        Paddle paddle = mock();
        List<Brick> bricks = new ArrayList<>();
        BrickBreakerComponent view = mock();
        final Controller controller = new Controller(ball, paddle, bricks, view);
        //when
        doReturn(800).when(view).getWidth();
        doReturn(600).when(view).getHeight();
        doReturn(120.0).when(ball).getX();
        doReturn(550.0).when(ball).getY();
        doReturn(10.0).when(ball).getDiameter();
        doReturn(45.0).when(ball).getDirectionDegrees();
        doReturn(100.0).when(paddle).getX();
        doReturn(560.0).when(paddle).getY();
        doReturn(100.0).when(paddle).getWidth();

        controller.startGame();
        controller.updateBallPosition();
        //then
        verify(ball).setDirectionDegrees(anyDouble());
        verify(view).repaint();
    }

    @Test
    void ballBouncesOffWallCorrectly() {
        //Given
        Ball ball = mock();
        Paddle paddle = mock();
        List<Brick> bricks = new ArrayList<>();
        BrickBreakerComponent view = mock();
        final Controller controller = new Controller(ball, paddle, bricks, view);
        //When
        doReturn(800).when(view).getWidth();
        doReturn(600).when(view).getHeight();
        doReturn(790.0).when(ball).getX();
        doReturn(300.0).when(ball).getY();
        doReturn(10.0).when(ball).getDiameter();
        doReturn(0.0).when(ball).getDirectionDegrees();

        controller.startGame();
        controller.updateBallPosition();
        //then
        verify(ball).setDirectionDegrees(anyDouble());
        verify(view).repaint();
    }

    @Test
    void ballDestroysBrickAndBounces() {
        //Given
        Ball ball = mock();
        Paddle paddle = mock();
        List<Brick> bricks = new ArrayList<>();
        BrickBreakerComponent view = mock();
        final Controller controller = new Controller(ball, paddle, bricks, view);

        Brick brick = mock(Brick.class);
        bricks.add(brick);
        //when
        doReturn(800).when(view).getWidth();
        doReturn(600).when(view).getHeight();
        doReturn(100.0).when(brick).getX();
        doReturn(100.0).when(brick).getY();
        doReturn(50.0).when(brick).getWidth();
        doReturn(20.0).when(brick).getHeight();
        doReturn(false).when(brick).isDestroyed();
        doReturn(125.0).when(ball).getX();
        doReturn(115.0).when(ball).getY();
        doReturn(10.0).when(ball).getDiameter();
        doReturn(180.0).when(ball).getDirectionDegrees();

        controller.startGame();
        controller.updateBallPosition();
        //then
        verify(brick).setDestroyed(true);
        verify(ball).setDirectionDegrees(anyDouble());
        verify(view).repaint();
    }
}


 */