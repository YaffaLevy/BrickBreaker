package levy.brickbreaker;

import java.awt.geom.Rectangle2D;

public class Paddle extends Rectangle2D.Double {

    private int speed;

    public Paddle(double x, double y, double width, double height, int speed) {

        super(x, y, width, height);
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Paddle{"
                +
                "x=" + x
                +
                ", width=" + width
                +
                '}';
    }
}
