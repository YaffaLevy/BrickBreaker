package lesser.brickBuilder;

import levy.brickBreaker.Paddle;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BrickBreakerComponent extends JPanel {
    private final int windowWidth = 800;
    private final int windowHeight = 600;
    private final int ballDiameter = 20;
    private final int brickWidth = 60;
    private final int brickHeight = 20;
    private final int numBricksPerRow = 10;
    private final int padding = 10;

    private Paddle paddle;
    private int ballX = windowWidth / 2 - ballDiameter / 2;
    private int ballY = windowHeight - 100;

    private int[] brickXPositions;
    private int[] brickYPositions;
    private Color[] brickColors;

    public BrickBreakerComponent() {
        initializeBricks();
        setPreferredSize(new Dimension(windowWidth, windowHeight));

        // Initialize paddle
        int paddleWidth = 100;
        int paddleHeight = 10;
        int paddleSpeed = 10;
        paddle = new Paddle((windowWidth - paddleWidth) / 2, windowHeight - 50, paddleWidth, paddleHeight, paddleSpeed);
    }

    private void initializeBricks() {
        brickXPositions = new int[numBricksPerRow * 2];
        brickYPositions = new int[numBricksPerRow * 2];
        brickColors = new Color[numBricksPerRow * 2];

        int startX = (windowWidth - (brickWidth + padding) * numBricksPerRow + padding) / 2;
        int y = 50;

        Random rand = new Random();
        Color[] possibleColors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA};

        for (int row = 0; row < 2; row++) {
            int x = startX;
            for (int col = 0; col < numBricksPerRow; col++) {
                int index = row * numBricksPerRow + col;
                brickXPositions[index] = x;
                brickYPositions[index] = y;
                brickColors[index] = possibleColors[rand.nextInt(possibleColors.length)];
                x += brickWidth + padding;
            }
            y += brickHeight + padding;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background
        setBackground(new Color(15, 15, 30));  // Dark background

        // Draw paddle
        g.setColor(Color.LIGHT_GRAY);
        g.fillRoundRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight(), 20, 20);

        // Draw ball with glow effect
        g.setColor(Color.WHITE);
        g.fillOval(ballX, ballY, ballDiameter, ballDiameter);

        // Draw bricks with colors
        for (int i = 0; i < brickXPositions.length; i++) {
            g.setColor(brickColors[i]);
            g.fillRect(brickXPositions[i], brickYPositions[i], brickWidth, brickHeight);
        }
    }

}
