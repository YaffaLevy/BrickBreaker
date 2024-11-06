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
    private final Paddle paddle;
    private final List<Bricks> bricks;
    private final BrickBreakerComponent view;
    private final Controller controller;

    public BrickBreakerFrame() {
        // Set up the frame properties
        setSize(800, 600);
        setTitle("Brick Breaker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize the game components
        Ball ball = new Ball(400, 500, 20, 5, 45);  // Example initial position, speed, and angle
        paddle = new Paddle(350, 550, 100, 10, 10); // Example paddle position
        bricks = new ArrayList<>();  // Initialize with bricks
        List<Wall> walls = new ArrayList<>();   // Initialize with walls if needed

        // Populate bricks
        initializeBricks();

        // Create the view component
        view = new BrickBreakerComponent(ball, paddle, bricks);
        add(view);

        // Initialize and assign the controller
        controller = new Controller(ball, paddle, bricks, view);

        // Mouse listener to trigger ball movement on mouse click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Start the game when the mouse is pressed
                controller.updateBallPosition();
            }
        });

        // Mouse motion listener to move the paddle with the mouse drag
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Move the paddle horizontally based on mouse position
                int newPaddleX = e.getX() - paddle.getWidth() / 2; // Keep the paddle centered on the mouse
                // Constrain within frame
                paddle.setX(Math.max(0, Math.min(newPaddleX, getWidth() - paddle.getWidth())));
                view.repaint(); // Repaint the view to reflect the paddle movement
            }
        });

        // Set focus for key events and make the frame visible
        setFocusable(true);
        pack();
        setVisible(true);

        // Start a game loop with a timer
        Timer gameTimer = new Timer(10, e -> {
            controller.updateBallPosition();
        });
        gameTimer.start();
    }

    private void initializeBricks() {
        // Add some bricks to the game
        for (int i = 0; i < 10; i++) {
            bricks.add(new Bricks(60 * i + 40, 100, 60, 20));
        }
    }
}