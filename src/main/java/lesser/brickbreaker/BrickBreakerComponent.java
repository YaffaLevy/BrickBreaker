package lesser.brickbreaker;

import levy.brickbreaker.Ball;
import levy.brickbreaker.Paddle;
import levy.brickbreaker.Brick;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class BrickBreakerComponent extends JComponent {
    private final Ball ball;
    private final Paddle paddle;
    private final List<Brick> bricks;

    public BrickBreakerComponent(Ball ball, Paddle paddle, List<Brick> bricks) {
        this.ball = ball;
        this.paddle = paddle;
        this.bricks = bricks;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Set background
        g2.setColor(new Color(0x0A0E28));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Draw paddle
        g2.setColor(Color.LIGHT_GRAY);
        g2.fill(paddle);

        // Draw ball
        g2.setColor(Color.WHITE);
        g2.fillOval((int) ball.getX(), (int) ball.getY(), (int) ball.getDiameter(), (int) ball.getDiameter());

        // Draw bricks
        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                g2.setColor(brick.getColor());
                g2.fill(brick);
            }
        }
    }
}
