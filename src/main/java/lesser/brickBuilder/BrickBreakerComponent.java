package lesser.brickBuilder;

import levy.brickBreaker.Ball;
import levy.brickBreaker.Paddle;
import levy.brickBreaker.Bricks;
import levy.brickBreaker.Wall;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BrickBreakerComponent extends JPanel {
    private final Ball ball;
    private final Paddle paddle;
    private final List<Bricks> bricks;

    public BrickBreakerComponent(Ball ball, Paddle paddle, List<Bricks> bricks) {
        this.ball = ball;
        this.paddle = paddle;
        this.bricks = bricks;
        int windowWidth = 800;
        int windowHeight = 600;
        setPreferredSize(new Dimension(windowWidth, windowHeight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        setBackground(new Color(15, 15, 30));

        g.setColor(Color.LIGHT_GRAY);
        g2.fill(paddle);
        //g.fillRoundRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight(), 20, 20);

        g.setColor(Color.WHITE);
        g2.fill(ball);
        //g.fillOval((int)ball.getX(), (int)ball.getY(), (int) ball.getDiameter(), (int) ball.getDiameter());


        for (Bricks brick : bricks) {
            if (!brick.isDestroyed()) {
                g.setColor(Color.CYAN);
                g2.fill(brick);
                //g.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
            }
        }
    }

}