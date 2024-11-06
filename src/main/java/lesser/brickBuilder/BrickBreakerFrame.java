package lesser.brickBuilder;

import levy.brickBreaker.Ball;
import levy.brickBreaker.Brick;
import levy.brickBreaker.Paddle;
import levy.brickBreaker.Wall;
import reiff.brickBreaker.Controller;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

public class BrickBreakerFrame extends JFrame {

    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;
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
        bricks = new ArrayList<>();  // Add your bricks to this list
        walls = new ArrayList<>();    // Add your walls to this list

        // Create the view component
        view = new BrickBreakerComponent();
        Controller controller = new Controller(ball, paddle, bricks,walls, view);

        // Add the view to the frame
        add(view);
        pack();
        c
        // Set the frame visible
        setVisible(true);
    }
}
