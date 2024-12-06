package lesser.brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import levy.brickbreaker.Ball;
import levy.brickbreaker.Paddle;
import levy.brickbreaker.Brick;
import reiff.brickbreaker.Controller;
import reiff.brickbreaker.Simulation;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BrickBreakerFrame extends JFrame {

    private final Ball ball = new Ball(390, 510, 20, 20, 1, 45);

    private final Paddle paddle = new Paddle(350, 550, 100, 10, 20);
    private final List<Brick> bricks = new ArrayList<>();

    private final BrickBreakerComponent view = new BrickBreakerComponent(ball, paddle, bricks);
    // private boolean ballMoving = false;
    private final Controller controller = new Controller(ball, paddle, bricks, view);


   /*
    public BrickBreakerFrame() {
        setSize(800, 600);
        setTitle("Brick Breaker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(view);
        view.setBounds(0, 0, 800, 600);
        setVisible(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                controller.handleKeyEvent(e.getKeyCode());
            }
        });

        setFocusable(true);
        requestFocusInWindow();

        controller.initializeBricks();

        Timer gameTimer = new Timer(10, e -> {
            if (!controller.isGameStopped()) {
                controller.updateBallPosition();
            }

            view.repaint();
        });
        gameTimer.start();

    }

    */

    public BrickBreakerFrame(NeuralNetwork bestNw) {
        setSize(800, 600);
        setTitle("Brick Breaker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(view);
        view.setBounds(0, 0, 800, 600);
        setVisible(true);

        setFocusable(true);
        requestFocusInWindow();

        ///controller.initializeBricks();
        //long seed = 1810913262880541491L;
        long seed = new Random().nextLong();
        Simulation simulation = new Simulation(bestNw, seed, ball, paddle, getWidth(), getHeight());

        if (bestNw != null) {

            Timer gameTimer = new Timer(1, e -> {
                if (!simulation.isGameStopped()) {
                    simulation.advance();
                    view.repaint();
                }
            });
            gameTimer.start();
        }
    }
}