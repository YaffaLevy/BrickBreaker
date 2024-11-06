package levy.brickBreaker;

public class Paddle {
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;

    public Paddle(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }

}
