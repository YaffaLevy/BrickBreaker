package reiff.brickbreaker;

import levy.brickbreaker.Brick;

import java.util.Random;

public class BrickFactory {
    private final int screenWidth;
    private final int screenHeight;
    private final int brickWidth;
    private final int brickHeight;

    public BrickFactory(int screenWidth, int screenHeight, int brickWidth, int brickHeight) {

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight / 2;
        this.brickWidth = brickWidth;
        this.brickHeight = brickHeight;
    }

    public Brick newBrick() {
        Random random = new Random();
        int cols = screenWidth / (brickWidth + 10);
        int rows = screenHeight / (brickHeight + 10);
        int xOffset = (screenWidth - (cols * (brickWidth + 10) - 10)) / 2;

        int col = random.nextInt(cols);
        int row = random.nextInt(rows);

        int x = xOffset + col * (brickWidth + 10);
        int y = 50 + row * (brickHeight + 10);

        return new Brick(x, y, brickWidth, brickHeight);
    }
}

