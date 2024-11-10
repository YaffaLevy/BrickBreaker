package lesser.brickBuilder;

import levy.brickBreaker.Ball;
import levy.brickBreaker.Paddle;
import levy.brickBreaker.Bricks;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BrickBreakerFrame extends JFrame {
    private static final int COLS = 10; // Number of columns of bricks
    private static final int ROWS = 5;  // Number of rows of bricks
    private static final int BRICK_WIDTH = 60;  // Width of each brick
    private static final int BRICK_HEIGHT = 20; // Height of each brick
    private static final int SPACING = 10; // Space between bricks

    private final Ball ball = new Ball(390, 530, 20, 20, 20, 5, 45); // Ball(x, y, width, height, diameter, speed, directionDegrees)
    private final Paddle paddle = new Paddle(350, 550, 100, 10, 20); // Paddle(x, y, width, height, speed)
    private final List<Bricks> bricks = new ArrayList<>();
    private final BrickBreakerComponent view = new BrickBreakerComponent(ball, paddle, bricks);
    private boolean ballMoving = false; // Track ball state locally

    public BrickBreakerFrame() {
        // Set up the frame properties
        setSize(800, 600);
        setTitle("Brick Breaker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Add the game view to the frame
        add(view);
        view.setBounds(0, 0, 800, 600);

        setFocusable(true);
        requestFocusInWindow();

        initializeBricks();

        // Add KeyListener for paddle movement and launching the ball
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                int paddleSpeed = paddle.getSpeed();

                // Move paddle left or right
                if (keyCode == KeyEvent.VK_LEFT) {
                    paddle.x = Math.max(0, paddle.x - paddleSpeed);
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    paddle.x = Math.min(view.getWidth() - paddle.width, paddle.x + paddleSpeed);
                }

                // Launch the ball
                if (keyCode == KeyEvent.VK_UP && !ballMoving) {
                    ballMoving = true;
                }

                view.repaint();
            }
        });

        // Start a game loop with a timer
        Timer gameTimer = new Timer(10, e -> {
            if (ballMoving) {
                updateBallPosition();
            }
            view.repaint();
        });
        gameTimer.start();

        setVisible(true);
    }

    private void initializeBricks() {
        Random random = new Random();
        int xOffset = (getWidth() - (COLS * (BRICK_WIDTH + SPACING) - SPACING)) / 2;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (random.nextBoolean()) { // Randomly decide whether to place a brick
                    int x = xOffset + col * (BRICK_WIDTH + SPACING);
                    int y = 50 + row * (BRICK_HEIGHT + SPACING); // Start at y=50 for top position
                    bricks.add(new Bricks(x, y, BRICK_WIDTH, BRICK_HEIGHT));
                }
            }
        }
    }

    private void updateBallPosition() {
        // Convert direction to radians for calculation
        double radians = Math.toRadians(ball.getDirectionDegrees());
        double dx = ball.getSpeed() * Math.cos(radians);
        double dy = ball.getSpeed() * Math.sin(radians);

        // Update ball position
        ball.setX(ball.getX() + dx);
        ball.setY(ball.getY() - dy); // Subtract dy because y decreases as the ball moves upward

        // Ball collision with walls
        if (ball.getX() <= 0 || ball.getX() + ball.getDiameter() >= view.getWidth()) {
            ball.setDirectionDegrees(180 - ball.getDirectionDegrees());
        }
        if (ball.getY() <= 0) {
            ball.setDirectionDegrees(-ball.getDirectionDegrees());
        }

        // Ball collision with paddle
        if (ball.intersects(paddle)) {
            ball.setDirectionDegrees(-ball.getDirectionDegrees());
        }

        // Ball collision with bricks
        for (Bricks brick : bricks) {
            if (!brick.isDestroyed() && ball.intersects(brick)) {
                brick.setDestroyed(true);
                ball.setDirectionDegrees(-ball.getDirectionDegrees());
                break;
            }
        }

        // Ball falls below the screen
        if (ball.getY() >= view.getHeight()) {
            ballMoving = false; // Stop the ball
            ball.setX(390); // Reset ball position
            ball.setY(530);
            ball.setDirectionDegrees(45); // Reset direction
        }
    }
}
