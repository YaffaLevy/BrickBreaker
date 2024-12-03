package levy.brickbreaker;

import java.awt.geom.Ellipse2D;

public class Ball extends Ellipse2D.Double {
    private double diameter;
    private double directionDegrees;
    private double speed;
    public double dx;
    public double dy;

    public Ball(double x, double y, double height, double width,
                double diameter, double speed, double directionDegree) {

        super(x, y, width, height);
        this.diameter = diameter;
        this.speed = speed;
        this.directionDegrees = directionDegree;
        initializeVelocity();

    }

    public void move() {

        x += dx;
        y += dy;
    }

    public boolean collides(Paddle paddle) {
        if (getY() + getDiameter() >= paddle.getY()
                && getX() >= paddle.getX()
                && getX() <= paddle.getX() + paddle.getWidth()) {

            return true;
        }
        return false;
    }

    public void collidesRightWall() {

        dx = -dx;
    }

    public void collidesLeftWall() {
        dx = -dx;
    }

    public void collidesTopWall() {
        dy = -dy;
    }


    public double getDirectionDegrees() {
        return directionDegrees;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDiameter() {

        return diameter;
    }

    public void setDirectionDegrees(double directionDegrees) {

        this.directionDegrees = directionDegrees;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void initializeVelocity() {
        double radians = Math.toRadians(directionDegrees);
        dx = speed * Math.cos(radians);
        dy = speed * Math.sin(radians);
    }

    @Override
    public String toString() {
        return "Ball{"
                +
                "directionDegrees=" + directionDegrees
                +
                ", x=" + x
                +
                ", y=" + y
                +
                '}';
    }
}