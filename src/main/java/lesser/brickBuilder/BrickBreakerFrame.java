package lesser.brickBuilder;

import levy.brickBreaker.Ball;
import levy.brickBreaker.Bricks;
import levy.brickBreaker.Paddle;
import levy.brickBreaker.Wall;
import reiff.brickBreaker.Controller;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class BrickBreakerFrame extends JFrame {

    private Ball ball;
    private Paddle paddle;
    private List<Bricks> bricks;
    private List<Wall> walls;
    private BrickBreakerComponent view;
    private Controller controller;

    public BrickBreakerFrame() {
        // Set up the frame properties
        setSize(800, 600);
        setTitle("Brick Breaker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize the game components
        ball = new Ball(400, 500, 20, 5, 45);  // Example initial position, speed, and angle
        paddle = new Paddle(350, 550, 100, 10, 10); // Example paddle position
       // bricks = new ArrayList<>();  // Initialize with your bricks

        // Fill bricks and walls with instances as needed here

        // Create the view component
        view = new BrickBreakerComponent();
        add(view);

        // Initialize and assign the controller
        controller = new Controller(ball, paddle, bricks, walls, view);

        // Mouse listener to trigger ball movement on mouse click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Debugging output to check if mouse is clicked
                System.out.println("Mouse clicked at: " + e.getX() + ", " + e.getY());
                // Trigger the ball movement when mouse is pressed
                controller.updateBallPosition();
            }
        });

        // Mouse motion listener to move the paddle with the mouse drag
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Debugging output to check mouse position
                System.out.println("Mouse dragged at: " + e.getX() + ", " + e.getY());
                // Move the paddle horizontally based on mouse position
                int newPaddleX = e.getX() - paddle.getWidth() / 2; // Keep the paddle centered on the mouse
                // Constrain within frame
                paddle.setX(Math.max(0, Math.min(newPaddleX, getWidth() - paddle.getWidth())));
                System.out.println("Paddle position updated to: " + paddle.getX()); // Debugging paddle position
                view.repaint(); // Repaint the view to reflect the paddle movement
            }
        });

        // Set focus for key events and make the frame visible
        setFocusable(true);
        pack();
        setVisible(true);
    }
}
