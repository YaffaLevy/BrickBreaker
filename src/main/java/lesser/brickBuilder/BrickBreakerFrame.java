package lesser.brickBuilder;

import levy.brickBreaker.Ball;
import levy.brickBreaker.Paddle;
import levy.brickBreaker.Bricks;
import levy.brickBreaker.Wall;
import reiff.brickBreaker.Controller;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class BrickBreakerFrame extends JFrame {
    private final Ball ball = new Ball(400, 500, 20, 20, 20, 15, 45);
    private final Paddle paddle = new Paddle(350, 550, 100, 10, 10);
    private final List<Bricks> bricks = new ArrayList<>();
    private final List<Wall> walls = new ArrayList<>();
    private final BrickBreakerComponent view = new BrickBreakerComponent(ball, paddle, bricks);
    private final Controller controller = new Controller(ball, paddle, bricks, view);

    public BrickBreakerFrame() {
        // Set up the frame properties
        setSize(800, 600);
        setTitle("Brick Breaker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //sets the frame to the center of the screen

        initializeBricks();

        // Mouse listener to trigger ball movement on mouse click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.updateBallPosition();
            }
        });

        // Uses the keyboard arrows for the paddle
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                int paddleSpeed = 10;

                if (keyCode == KeyEvent.VK_LEFT) {
                    double newPaddleX = Math.max(0, paddle.getX() - paddleSpeed);
                    paddle.setRect(newPaddleX, (int) paddle.getY(), paddle.getWidth(), paddle.getHeight());
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    double newPaddleX = Math.min(getWidth() - paddle.getWidth(), paddle.getX() + paddleSpeed);
                    paddle.setRect(newPaddleX, (int) paddle.getY(), paddle.getWidth(), paddle.getHeight());
                }

                view.repaint();
            }
        });

        setFocusable(true); //this uses focus to get the input from the keyboard
        requestFocusInWindow(); //makes sure that focus can be used in the frame
        setVisible(true); //ensures the frame is visible when the program runs

        // Start a game loop with a timer
        Timer gameTimer = new Timer(10, e -> {
            controller.updateBallPosition();
        });
        gameTimer.start();
    }

    private void initializeBricks() {
        int brickWidth = 60;
        int brickHeight = 20;
        int numRows = 5;

        // Calculate how many bricks can fit in the width of the window
        int numBricksPerRow = getWidth() / brickWidth;

        // Calculate the starting x position to center the bricks
        int horizontalPadding = (getWidth() - numBricksPerRow * brickWidth) / 2;

        // Randomly scatter bricks within window bounds, centered horizontally
        for (int row = 0; row < numRows; row++) {
            int y = 100 + row * (brickHeight + 10);

            // Create bricks in the current row
            for (int i = 0; i < numBricksPerRow; i++) {
                int x = horizontalPadding + i * brickWidth;

                // Create and add the brick to the list
                bricks.add(new Bricks(x, y, brickWidth, brickHeight));
            }
        }
    }
}