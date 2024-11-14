package lesser.brickbreaker;

import levy.brickbreaker.Ball;
import levy.brickbreaker.Paddle;
import levy.brickbreaker.Brick;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrickBreakerComponent extends JComponent {
    private final Ball ball;
    private final Paddle paddle;
    private final List<Brick> bricks;

    private final Map<Brick, Color> brickColors = new HashMap<>();


    public BrickBreakerComponent(Ball ball, Paddle paddle, List<Brick> bricks) {
        this.ball = ball;
        this.paddle = paddle;
        this.bricks = bricks;


        for (Brick brick : bricks) {

            brickColors.put(brick, new Color((int) (Math.random() * 0xFFFFFF)));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Set background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw paddle
        g.setColor(Color.LIGHT_GRAY);
        g2.fill(paddle);

        // Draw ball
        g.setColor(Color.WHITE);
        g2.fillOval((int) ball.getX(), (int) ball.getY(), (int) ball.getDiameter(), (int) ball.getDiameter());

        // Draw bricks
        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                g.setColor(brickColors.getOrDefault(brick, Color.GREEN));
                g2.fill(brick);
            }
        }
    }
}
