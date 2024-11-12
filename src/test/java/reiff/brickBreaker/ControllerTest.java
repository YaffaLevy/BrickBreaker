package reiff.brickBreaker;

import levy.brickbreaker.Ball;
import levy.brickbreaker.Bricks;
import levy.brickbreaker.Paddle;
import lesser.brickBuilder.BrickBreakerComponent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class ControllerTest {

    @Test
    void testBallMovesToNewPosition() {
        Ball ball = mock();
        Paddle paddle = mock();
        List<Bricks> bricks = new ArrayList<>();
        BrickBreakerComponent view = mock();
        Controller controller = new Controller(ball, paddle, bricks, view);

        doReturn(800).when(view).getWidth();
        doReturn(600).when(view).getHeight();
        doReturn(100.0).when(ball).getX();
        doReturn(100.0).when(ball).getY();
        doReturn(5.0).when(ball).getSpeed();
        doReturn(45.0).when(ball).getDirectionDegrees();

        controller.startGame();
        controller.updateBallPosition();

        verify(ball).setX(anyDouble());
        verify(ball).setY(anyDouble());
        verify(view).repaint();
    }

    @Test
    void testBallChangesDirectionOnPaddleHit() {
        Ball ball = mock();
        Paddle paddle = mock();
        List<Bricks> bricks = new ArrayList<>();
        BrickBreakerComponent view = mock();
        Controller controller = new Controller(ball, paddle, bricks, view);

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

        verify(ball).setDirectionDegrees(anyDouble());
        verify(view).repaint();
    }

    @Test
    void testBallBouncesOffWallCorrectly() {
        Ball ball = mock();
        Paddle paddle = mock();
        List<Bricks> bricks = new ArrayList<>();
        BrickBreakerComponent view = mock();
        Controller controller = new Controller(ball, paddle, bricks, view);

        doReturn(800).when(view).getWidth();
        doReturn(600).when(view).getHeight();
        doReturn(790.0).when(ball).getX();
        doReturn(300.0).when(ball).getY();
        doReturn(10.0).when(ball).getDiameter();
        doReturn(0.0).when(ball).getDirectionDegrees();

        controller.startGame();
        controller.updateBallPosition();

        verify(ball).setDirectionDegrees(anyDouble());
        verify(view).repaint();
    }

    @Test
    void testBallDestroysBrickAndBounces() {
        Ball ball = mock();
        Paddle paddle = mock();
        List<Bricks> bricks = new ArrayList<>();
        BrickBreakerComponent view = mock();
        Controller controller = new Controller(ball, paddle, bricks, view);

        Bricks brick = mock(Bricks.class);
        bricks.add(brick);

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

        verify(brick).setDestroyed(true);
        verify(ball).setDirectionDegrees(anyDouble());
        verify(view).repaint();
    }
}
