package levy.brickBreaker;

import java.util.List;

public class GameModel {
    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;
    private List<Wall> walls;

    public GameModel(Ball ball, Paddle paddle, List<Brick> bricks, List<Wall> walls) {
        this.ball = ball;
        this.paddle = paddle;
        this.bricks = bricks;
        this.walls = walls;
    }
    public Ball getBall() { return ball; }
    public Paddle getPaddle() { return paddle; }
    public List<Brick> getBricks() { return bricks; }
    public List<Wall> getWalls() { return walls; }

    public void updateBallPosition(double x, double y) {
        ball.setX(x);
        ball.setY(y);
    }
}
