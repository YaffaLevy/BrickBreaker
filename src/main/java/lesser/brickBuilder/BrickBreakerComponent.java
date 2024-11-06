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

        // Draw background
        setBackground(new Color(15, 15, 30));  // Dark background

        // Draw paddle
        g.setColor(Color.LIGHT_GRAY);
        g.fillRoundRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight(), 20, 20);

        // Draw ball
        g.setColor(Color.WHITE);
        g.fillOval((int)ball.getX(), (int)ball.getY(), (int) ball.getDiameter(), (int) ball.getDiameter());

        // Draw bricks
        for (Bricks brick : bricks) {
            if (brick.isDestroyed()) {
                g.setColor(Color.CYAN); // You can use brick.getColor() for more variety
                g.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
            }
        }
    }

}