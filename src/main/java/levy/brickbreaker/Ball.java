package levy.brickbreaker;

import java.awt.geom.Ellipse2D;

public class Ball extends Ellipse2D.Double {
    private double directionDegrees;
    private double speed;
    public double dx = 1;
    public double dy = -1;

    public Ball(double x, double y, double height, double width, double speed, double directionDegree) {

        super(x, y, width, height);
        this.speed = speed;
        this.directionDegrees = directionDegree;
        //initializeVelocity();

    }

    public void move() {

        x += dx;
        y += dy;
    }

    public boolean collides(Paddle paddle) {
        return this.getBounds().intersects(paddle);
        /*
        if (getY() + getDiameter() >= paddle.getY()
                && getX() >= paddle.getX()
                && getX() <= paddle.getX() + paddle.getWidth()) {

            return true;
        }
        return false;

         */
    }

    public void collidesRightWall() {

        dx = -dx;
    }

    public void collidesLeftWall() {
        dx = -dx;
    }

    public void collidesTopWall() {
        dy = Math.abs(dy);
    }


    public double getDirectionDegrees() {
        return directionDegrees;
    }

    public double getSpeed() {
        return speed;
    }

    public void setDirectionDegrees(double directionDegrees) {

        this.directionDegrees = directionDegrees;
        initializeVelocity();
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void initializeVelocity() {
//        double radians = Math.toRadians(directionDegrees);
//        dx = speed * Math.cos(radians);
//        dy = speed * Math.sin(radians);
        dx = 1;
        dy = -1;
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