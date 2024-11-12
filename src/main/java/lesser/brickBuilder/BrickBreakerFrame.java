package lesser.brickBuilder;

import levy.brickBreaker.Ball;
import levy.brickBreaker.Paddle;
import levy.brickBreaker.Bricks;
import reiff.brickBreaker.Controller;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BrickBreakerFrame extends JFrame {
    private static final int COLS = 10;
    private static final int ROWS = 5;
    private static final int BRICK_WIDTH = 60;
    private static final int BRICK_HEIGHT = 20;
    private static final int SPACING = 10;

    private final Ball ball = new Ball(390, 530, 20, 20, 20, 5, 45);
    private final Paddle paddle = new Paddle(350, 550, 100, 10, 60);
    private final List<Bricks> bricks = new ArrayList<>();
    private final BrickBreakerComponent view = new BrickBreakerComponent(ball, paddle, bricks);
    private boolean ballMoving = false;
    private final Controller controller = new Controller(ball, paddle, bricks, view);

    public BrickBreakerFrame() {
        setSize(800, 600);
        setTitle("Brick Breaker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(view);
        view.setBounds(0, 0, 800, 600);

        setFocusable(true);
        requestFocusInWindow();

        initializeBricks();

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

                if (keyCode == KeyEvent.VK_UP && !ballMoving) {
                    ballMoving = true;
                    controller.startGame();
                }

                view.repaint();
            }
        });

        Timer gameTimer = new Timer(10, e -> {
            if (ballMoving) {
                controller.updateBallPosition();
            }
            if (controller.isGameStopped()) {
                ballMoving = false;
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
                if (random.nextBoolean()) {
                    int x = xOffset + col * (BRICK_WIDTH + SPACING);
                    int y = 50 + row * (BRICK_HEIGHT + SPACING);
                    bricks.add(new Bricks(x, y, BRICK_WIDTH, BRICK_HEIGHT));
                }
            }
        }
    }

}