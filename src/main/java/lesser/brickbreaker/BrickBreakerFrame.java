package lesser.brickbreaker;

import basicneuralnetwork.NeuralNetwork;
import levy.brickbreaker.Ball;
import levy.brickbreaker.Paddle;
import levy.brickbreaker.Brick;
import reiff.brickbreaker.BrickFactory;
import reiff.brickbreaker.Simulation;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BrickBreakerFrame extends JFrame {

    private final BrickBreakerComponent view;
    private final JLabel scoreLabel;
    private final JLabel gameOverLabel;

    public BrickBreakerFrame(NeuralNetwork bestNw) {
        setSize(800, 600);
        setTitle("Brick Breaker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        scoreLabel = new JLabel("Score: ");
        gameOverLabel = new JLabel("Game Over");

        scoreLabel.setBounds(650, 10, 100, 30);
        gameOverLabel.setBounds(350, 285, 100, 30);


        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gameOverLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        scoreLabel.setForeground(Color.yellow);
        gameOverLabel.setForeground(Color.yellow);

        add(scoreLabel);
        gameOverLabel.setVisible(false);
        add(gameOverLabel);


        BrickFactory brickFactory = new BrickFactory(800, 600, 60, 20);
        long seed = new Random().nextLong();
        Ball ball = new Ball(390, 510, 20, 20, 1, 45);
        Paddle paddle = new Paddle(350, 550, 100, 10, 20);
        Simulation simulation = new Simulation(bestNw, seed, ball, paddle, getWidth(), getHeight(), brickFactory);
        Brick brick = simulation.getBrick();

        view = new BrickBreakerComponent(ball, paddle, brick);
        add(view);
        view.setBounds(0, 0, 800, 600);

        setVisible(true);
        setFocusable(true);
        requestFocusInWindow();

        if (bestNw != null) {

            Timer gameTimer = new Timer(1, e -> {
                if (!simulation.isGameStopped()) {
                    simulation.advance();
                    view.setBrick(simulation.getBrick());
                    view.repaint();

                    scoreLabel.setText("Score: " + simulation.getScore());
                } else {
                    gameOverLabel.setVisible(true);
                }
            });
            gameTimer.start();
        }
    }

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
}