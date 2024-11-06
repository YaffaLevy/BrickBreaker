package levy.brickBreaker;

public class Bricks {
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean destroyed;

    public Bricks(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.destroyed = false;
    }

    // Getters and setters only
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isDestroyed() { return !destroyed; }
    public void setDestroyed(boolean destroyed) { this.destroyed = destroyed; }
}