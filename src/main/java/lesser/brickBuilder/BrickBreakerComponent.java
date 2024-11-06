package lesser.brickBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BrickBreakerComponent extends JPanel {
    private final int windowWidth = 800;
    private final int windowHeight = 600;
    private final int paddleWidth = 100;
    private final int paddleHeight = 10;
    private final int ballDiameter = 20;
    private final int brickWidth = 60;
    private final int brickHeight = 20;
    private final int numBricksPerRow = 10;
    private final int padding = 10;
    private int paddleX = (windowWidth - paddleWidth) / 2;
    private int ballX = windowWidth / 2 - ballDiameter / 2;
    private int ballY = windowHeight - 100;
    private int[] brickXPositions;
    private int[] brickYPositions;
    private Color[] brickColors;

    public BrickBreakerComponent() {
        initializeBricks();
        setPreferredSize(new Dimension(windowWidth, windowHeight));
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
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRoundRect(paddleX, windowHeight - 50, paddleWidth, paddleHeight, 20, 20);

        // Draw ball with glow effect
        g2d.setColor(Color.WHITE);
        g2d.fillOval(ballX, ballY, ballDiameter, ballDiameter);

        // Draw bricks with colors
        for (int i = 0; i < brickXPositions.length; i++) {
            g2d.setColor(brickColors[i]);
            g2d.fillRect(brickXPositions[i], brickYPositions[i], brickWidth, brickHeight);
        }
    }

    public void updateBallPosition(int x, int y) {
        ballX = x;
        ballY = y;
        repaint();
    }

    public void updatePaddlePosition(int x) {
        paddleX = x;
        repaint();
    }
}
