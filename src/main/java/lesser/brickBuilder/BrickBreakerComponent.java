package lesser.brickBuilder;

import levy.brickBreaker.Ball;
import levy.brickBreaker.Paddle;
import levy.brickBreaker.Bricks;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrickBreakerComponent extends JComponent {
    private final Ball ball;
    private final Paddle paddle;
    private final List<Bricks> bricks;

    // Map to store colors for each brick
    private final Map<Bricks, Color> brickColors = new HashMap<>();

    public BrickBreakerComponent(Ball ball, Paddle paddle, List<Bricks> bricks) {
        this.ball = ball;
        this.paddle = paddle;
        this.bricks = bricks;

        // Assign random colors to bricks
        for (Bricks brick : bricks) {
            brickColors.put(brick, new Color((int) (Math.random() * 0xFFFFFF)));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Set background
        g.setColor(new Color(173, 216, 230));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw paddle
        g.setColor(Color.LIGHT_GRAY);
        g2.fill(paddle);

        // Draw ball
        g.setColor(Color.WHITE);
        g2.fillOval((int) ball.getX(), (int) ball.getY(), (int) ball.getDiameter(), (int) ball.getDiameter());

        // Draw bricks
        for (Bricks brick : bricks) {
            if (!brick.isDestroyed()) {
                g.setColor(brickColors.getOrDefault(brick, Color.RED)); // Default to red if no color
                g2.fill(brick);
            }
        }
    }
}
